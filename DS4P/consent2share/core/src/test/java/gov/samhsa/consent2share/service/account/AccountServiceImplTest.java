package gov.samhsa.consent2share.service.account;

import static org.mockito.Mockito.*;
import gov.samhsa.consent2share.domain.account.EmailTokenRepository;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.domain.commondomainservices.EmailSender;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.infrastructure.security.EmailAddressNotExistException;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.infrastructure.security.UsernameNotExistException;
import gov.samhsa.consent2share.service.dto.SignupDto;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountServiceImplTest {

	private AccountServiceImpl sut;
	
	private PatientRepository patientRepository;
	private TokenGenerator tokenGenerator;
	private Integer accountVerificationTokenExpireInHours;
	private EmailTokenRepository emailTokenRepository;
	private EmailSender emailSender;
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;
	private PasswordEncoder passwordEncoder;
	private UserContext userContext;
	private UsersRepository usersRepository;

	@Before
	public void setUp() {
		// Mock dependencies and create sut
		// Just to save a few lines of code for each individual test
		// But independency, clarity of the unit tests are much more important
		// than code reuse
		patientRepository = mock(PatientRepository.class);
		administrativeGenderCodeRepository = mock(AdministrativeGenderCodeRepository.class);
		tokenGenerator = mock(TokenGenerator.class);
		accountVerificationTokenExpireInHours = 8;
		emailTokenRepository = mock(EmailTokenRepository.class);
		emailSender = mock(EmailSender.class);
		userContext = mock(UserContext.class);
		passwordEncoder = mock(PasswordEncoder.class);
		usersRepository=mock(UsersRepository.class);

		sut = new AccountServiceImpl(patientRepository,
				administrativeGenderCodeRepository, passwordEncoder,
				userContext, emailSender, tokenGenerator, emailTokenRepository,usersRepository,
				accountVerificationTokenExpireInHours);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSignup_Throws_Exception_Given_Link_Url_Has_Whitespaces_Only()
			throws MessagingException, UsernameNotExistException,
			EmailAddressNotExistException, Hl7v3TransformerException {
		SignupDto signupDto = mock(SignupDto.class);
		sut.signup(signupDto, " ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSignup_Throws_Exception_Given_Valid_Link_Url_And_Given_No_Email_Address()
			throws MessagingException, UsernameNotExistException,
			EmailAddressNotExistException, Hl7v3TransformerException {
		// Arrange
		final String linkUrl = "linkUrl";
		SignupDto signupDto = mock(SignupDto.class);
		when(signupDto.getUsername()).thenReturn("username");
		
		when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
		
		// Act
		sut.signup(signupDto, linkUrl);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Username_Has_Whitespaces_Only()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createEmailToken("  ", "emailAddress");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Username_Is_Null()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {

		sut.createEmailToken(null, "emailAddress");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Email_Has_Whitespaces_Only()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createEmailToken("username", "  ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateEmailToken_Throws_Exception_When_Email_Is_Null()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		sut.createEmailToken("username", null);
	}
	
	
	@Test
	public void testCreateEmailToken()
			throws UsernameNotExistException, EmailAddressNotExistException,
			MessagingException {
		Patient patient = mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patient.getEmail()).thenReturn("emailAddress");
		sut.createEmailToken("username", "emailAddress");
	}
	
	@Test
	public void testFindByUserName() {
		Users user = mock(Users.class);
		when(usersRepository.loadUserByUsername(anyString())).thenReturn(user);
		sut.findUserByUsername("username");
	}
}
