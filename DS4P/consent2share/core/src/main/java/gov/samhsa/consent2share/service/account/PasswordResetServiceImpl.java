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

import java.util.Date;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import gov.samhsa.consent2share.domain.account.EmailToken;
import gov.samhsa.consent2share.domain.account.EmailTokenRepository;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.account.TokenType;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.infrastructure.EmailType;
import gov.samhsa.consent2share.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.consent2share.infrastructure.security.TokenExpiredException;
import gov.samhsa.consent2share.infrastructure.security.TokenNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UsernameNotExistException;
import gov.samhsa.consent2share.service.dto.PasswordResetDto;
import gov.samhsa.consent2share.service.dto.PasswordChangeDto;

/**
 * The Class PasswordResetServiceImpl.
 */
@Component
public class PasswordResetServiceImpl implements PasswordResetService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** The users repository. */
	private UsersRepository usersRepository;
	
	/** The patient repository. */
	private PatientRepository patientRepository;
	
	/** The token generator. */
	private TokenGenerator tokenGenerator;
	
	/** The password reset token expire in hours. */
	private Integer passwordResetTokenExpireInHours;
	
	/** The password reset token repository. */
	private EmailTokenRepository passwordResetTokenRepository;
	
	/** The email sender. */
	private EmailSender emailSender;
	
	/** The password encoder. */
	private PasswordEncoder passwordEncoder;

	
	/**
	 * Instantiates a new password reset service impl.
	 *
	 * @param usersRepository the users repository
	 * @param patientRepository the patient repository
	 * @param tokenGenerator the token generator
	 * @param passwordResetTokenExpireInHours the password reset token expire in hours
	 * @param passwordResetTokenRepository the password reset token repository
	 * @param emailSender the email sender
	 * @param passwordEncoder the password encoder
	 */
	@Autowired
	public PasswordResetServiceImpl(
			UsersRepository usersRepository,
			PatientRepository patientRepository,
			TokenGenerator tokenGenerator,
			@Value("${passwordResetTokenExpireInHours}") Integer passwordResetTokenExpireInHours,
			EmailTokenRepository passwordResetTokenRepository,
			EmailSender emailSender, PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.patientRepository = patientRepository;
		this.tokenGenerator = tokenGenerator;
		this.passwordResetTokenExpireInHours = passwordResetTokenExpireInHours;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.emailSender = emailSender;
		this.passwordEncoder = passwordEncoder;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.account.PasswordResetService#createPasswordResetToken(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void createPasswordResetToken(String username, String emailAddress,
			String linkUrl) throws UsernameNotExistException,
			EmailAddressNotExistException, MessagingException {

		if (!StringUtils.hasText(username)) {
			throw new IllegalArgumentException("Username is required.");
		}

		if (!StringUtils.hasText(emailAddress)) {
			throw new IllegalArgumentException("Email Address is required.");
		}

		if (!StringUtils.hasText(linkUrl)) {
			throw new IllegalArgumentException("Email link is required.");
		}

		try {
			usersRepository.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			logger.warn(e.getMessage(), e);
			throw new UsernameNotExistException(e.getMessage());
		}

		Patient patient = patientRepository.findByUsername(username);
		String patientEmailAddress = patient.getEmail();
		if (!patientEmailAddress.equalsIgnoreCase(emailAddress)) {
			String message = String.format(
					"Email address %s doesn't exist for username %s.",
					emailAddress, username);
			logger.warn(message);
			throw new EmailAddressNotExistException(message);
		}

		EmailToken passwordResetToken = new EmailToken();
		passwordResetToken.setExpireInHours(passwordResetTokenExpireInHours);
		passwordResetToken.setRequestDateTime(new Date());
		String token = tokenGenerator.generateToken();
		passwordResetToken.setUsername(username);
		passwordResetToken.setToken(token);
		passwordResetToken.setIsTokenUsed(false);
		passwordResetToken.setTokenType(TokenType.PASSWORD_RESET);

		passwordResetTokenRepository.save(passwordResetToken);		

		emailSender.sendMessage(
				patient.getFirstName() + " " + patient.getLastName(),
				emailAddress, EmailType.PASSWORD_RESET_REQUEST, linkUrl, token);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.account.PasswordResetService#isPasswordResetTokenExpired(java.lang.String)
	 */
	@Override
	public Boolean isPasswordResetTokenExpired(String token)
			throws TokenNotExistException {
		if (!StringUtils.hasText(token)) {
			throw new IllegalArgumentException(
					"Password reset token is required.");
		}

		EmailToken passwordResetToken = findPasswordResetToken(token);
		Boolean isExpired = passwordResetToken.isTokenExpired();

		return isExpired;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.account.PasswordResetService#resetPassword(gov.samhsa.consent2share.service.dto.PasswordResetDto, java.lang.String)
	 */
	@Override
	public void resetPassword(PasswordResetDto passwordResetDto, String linkUrl)
			throws TokenNotExistException, TokenExpiredException,
			UsernameNotExistException, MessagingException {
		if (passwordResetDto == null) {
			throw new IllegalArgumentException(
					"Password reset dto is required.");
		}

		String token = passwordResetDto.getToken();

		EmailToken passwordResetToken = findPasswordResetToken(token);

		Boolean isExpired = passwordResetToken.isTokenExpired();
		if (isExpired) {
			throw new TokenExpiredException("Password reset token is expired.");
		}
		
		passwordResetToken.setIsTokenUsed(true);
		passwordResetTokenRepository.save(passwordResetToken);

		String username = passwordResetToken.getUsername();

		Users user = null;
		try {
			user = usersRepository.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			// TODO: Log here
			throw new UsernameNotExistException(e.getMessage());
		}

		String encodedPassword = passwordEncoder.encode(passwordResetDto
				.getPassword());

		Users updatedUser = null;
		
		updatedUser = new Users(user.getFailedLoginAttempts(), 
				username, 
				user.getPassword(), 
				user.isEnabled(), 
				user.isAccountNonExpired(), 
				user.isCredentialsNonExpired(), 
				user.getAuthorities());

//		if (user.isEnabled()) {
//
//			updatedUser = new User(username, encodedPassword,
//					user.getAuthorities());
//		} else {
//			updatedUser = new User(username, encodedPassword, false,
//					true, true, true, user.getAuthorities());
//		}

		usersRepository.updateUser(updatedUser);

		Patient patient = patientRepository.findByUsername(username);
		
		emailSender.sendMessage(
				patient.getFirstName() + " " + patient.getLastName(),
				patient.getEmail(), EmailType.PASSWORD_CONFIRMATION, linkUrl, null);
	}
	
	@Override
	public boolean changePassword(PasswordChangeDto passwordChangeDto)
			throws UsernameNotExistException, MessagingException {
		if (passwordChangeDto == null) {
			throw new IllegalArgumentException(
					"Password change dto is required.");
		}

		String username = passwordChangeDto.getUsername();
		
		Users user = null;
		try {
			user = usersRepository.loadUserByUsername(username);
		} catch (UsernameNotFoundException e) {
			// TODO: Log here
			throw new UsernameNotExistException(e.getMessage());
		}
	
		String encodedOldPassword = passwordEncoder.encode(passwordChangeDto.getOldPassword());
		String encodedNewPassword = passwordEncoder.encode(passwordChangeDto.getNewPassword());
		
		if(passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword()) == true){
			usersRepository.changePassword(encodedOldPassword, encodedNewPassword);
			return true;
		}else{
			return false;
		}
		
	}


	/**
	 * Find password reset token.
	 *
	 * @param token the token
	 * @return the email token
	 * @throws TokenNotExistException the token not exist exception
	 */
	private EmailToken findPasswordResetToken(String token)
			throws TokenNotExistException {
		EmailToken passwordResetToken = passwordResetTokenRepository
				.findByToken(token);

		if (passwordResetToken == null) {
			throw new TokenNotExistException(
					"Password reset token doesn't exist.");
		}

		return passwordResetToken;
	}
}
