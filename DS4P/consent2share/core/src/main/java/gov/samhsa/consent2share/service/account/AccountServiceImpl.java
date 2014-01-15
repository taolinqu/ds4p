/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.service.account;

import gov.samhsa.consent2share.domain.account.EmailToken;
import gov.samhsa.consent2share.domain.account.EmailTokenRepository;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.account.TokenType;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.infrastructure.EmailType;
import gov.samhsa.consent2share.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.infrastructure.security.UsernameNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UsersAuthorityUtils;
import gov.samhsa.consent2share.service.dto.SignupDto;

import java.util.Date;
import java.util.Set;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * The Class AccountServiceImpl.
 */
@Component
public class AccountServiceImpl implements AccountService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** The patient repository. */
	private PatientRepository patientRepository;
	
	/** The administrative gender code repository. */
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;
	
	/** The password encoder. */
	private PasswordEncoder passwordEncoder;
	
	/** The user context. */
	private UserContext userContext;
	
	/** The email sender. */
	private EmailSender emailSender;	
	
	/** The token generator. */
	private TokenGenerator tokenGenerator;
	
	/** The account verification token expire in hours. */
	private Integer accountVerificationTokenExpireInHours;
	
	/** The email token repository. */
	private EmailTokenRepository emailTokenRepository;
	
	/** The users repository. */
	private UsersRepository usersRepository;

	/**
	 * Instantiates a new account service impl.
	 *
	 * @param userDetailsManager the user details manager
	 * @param patientRepository the patient repository
	 * @param administrativeGenderCodeRepository the administrative gender code repository
	 * @param passwordEncoder the password encoder
	 * @param userContext the user context
	 * @param emailSender the email sender
	 * @param tokenGenerator the token generator
	 * @param emailTokenRepository the email token repository
	 * @param accountVerificationTokenExpireInHours the account verification token expire in hours
	 */
	@Autowired
	public AccountServiceImpl(
			PatientRepository patientRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			PasswordEncoder passwordEncoder, UserContext userContext,
			EmailSender emailSender, 
			TokenGenerator tokenGenerator, EmailTokenRepository emailTokenRepository, UsersRepository usersRepository,
			@Value("${accountVerificationTokenExpireInHours}") Integer accountVerificationTokenExpireInHours) {
		this.patientRepository = patientRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.passwordEncoder = passwordEncoder;
		this.userContext = userContext;
		this.emailSender = emailSender;
		this.emailTokenRepository = emailTokenRepository;
		this.usersRepository=usersRepository;
		this.tokenGenerator = tokenGenerator;
		this.accountVerificationTokenExpireInHours = accountVerificationTokenExpireInHours;		
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.account.AccountService#signup(gov.samhsa.consent2share.service.dto.SignupDto, java.lang.String)
	 */
	@Override
	@Transactional
	public void signup(SignupDto signupDto, String linkUrl) throws MessagingException, UsernameNotExistException, EmailAddressNotExistException {

		if (!StringUtils.hasText(linkUrl)) {
			throw new IllegalArgumentException("Email link is required.");
		}
		
		String encodedPassword = passwordEncoder
				.encode(signupDto.getPassword());

		Patient patient = new Patient();
		patient.setFirstName(signupDto.getFirstName());
		patient.setLastName(signupDto.getLastName());
		patient.setUsername(signupDto.getUsername());
		patient.setEmail(signupDto.getEmail());
		patient.setBirthDay(signupDto.getBirthDate());
		if (StringUtils.hasText(signupDto.getGenderCode())) {
			patient.setAdministrativeGenderCode(administrativeGenderCodeRepository
					.findByCode(signupDto.getGenderCode()));
		}

		patientRepository.save(patient);

		Set<GrantedAuthority> authorities = UsersAuthorityUtils.createAuthoritySet("ROLE_USER");
		
		String username = signupDto.getUsername();
		
		Users user=new Users(username, encodedPassword, false, true, true, authorities);
		usersRepository.createUser(user);

		userContext.setCurrentUser(signupDto.getUsername());
		
		String token = createEmailToken(signupDto.getUsername(), signupDto.getEmail());	
		
		emailSender.sendMessage(
				signupDto.getFirstName() + " " + signupDto.getLastName(),
				signupDto.getEmail(), EmailType.SIGNUP_VERIFICATION, linkUrl, token);
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.account.AccountService#createEmailToken(java.lang.String, java.lang.String)
	 */
	@Override
	public String createEmailToken(String username, String emailAddress)
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		String emailLinkPlaceHolder = "?token=%s";
		
		if (!StringUtils.hasText(username)) {
			throw new IllegalArgumentException("Username is required.");
		}

		if (!StringUtils.hasText(emailAddress)) {
			throw new IllegalArgumentException("Email Address is required.");
		}		

		Patient patient = patientRepository.findByUsername(username);
		String patientEmailAddress = patient.getEmail();
		if (!patientEmailAddress.equalsIgnoreCase(emailAddress)) {
			String message = String.format(
					"Email address %s doesn't exist for username %s.",
					emailAddress, username);
			
			logger.info("message");
			throw new EmailAddressNotExistException(message);
		}

		EmailToken accountVerificationToken = new EmailToken();
		accountVerificationToken.setExpireInHours(accountVerificationTokenExpireInHours);
		accountVerificationToken.setRequestDateTime(new Date());
		String token = tokenGenerator.generateToken();
		accountVerificationToken.setToken(token);
		accountVerificationToken.setUsername(username);
		accountVerificationToken.setIsTokenUsed(false);
		accountVerificationToken.setTokenType(TokenType.ACCOUNT_VERIFICATION);
		emailTokenRepository.save(accountVerificationToken);

		String link = String.format(emailLinkPlaceHolder, token);

		return link;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.account.AccountService#findUserByUsername(java.lang.String)
	 */
	@Override
	public Users findUserByUsername(String username) {
		Users user = null;
		try {
			user = usersRepository.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			String message = String.format("Username %s is not found.", username);
			logger.info(message);
		}
		return user;
	}
}
