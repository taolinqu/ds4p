package gov.samhsa.consent2share.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.LegalRepresentativeDto;
import gov.samhsa.consent2share.service.dto.PatientLegalRepresentativeAssociationDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.patient.PatientLegalRepresentativeAssociationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.LegalRepresentativeTypeCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class LegalRepresentativeControllerTest {
	
	@InjectMocks
	LegalRepresentativeController legalRepresentativeController = new LegalRepresentativeController();

	@Mock
	PatientService patientService;
	
	@Mock
	IndividualProviderService individualProviderService;
	
	@Mock
	OrganizationalProviderService organizationalProviderService;

	@Mock
	AdministrativeGenderCodeService administrativeGenderCodeService;

	@Mock
	LanguageCodeService languageCodeService;

	@Mock
	MaritalStatusCodeService maritalStatusCodeService;

	@Mock
	RaceCodeService raceCodeService;

	@Mock
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	@Mock
	StateCodeService stateCodeService;
	
	@Mock
	LegalRepresentativeTypeCodeService legalRepresentativeTypeCodeService;
	
	@Mock
	PatientLegalRepresentativeAssociationService patientLegalRepresentativeAssociationService;

	@Mock
	UserContext userContext;
	
	@Mock
	AuthenticatedUser authenticatedUser;
	
	@Mock
	PatientProfileDto patientProfileDto;
	
	@Mock
	private FieldValidator fieldValidator;
	
	@Mock
	private MessageSource messageProperties; 
	
	MockMvc mockMvc;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.legalRepresentativeController).build();
		PatientLegalRepresentativeAssociationDto patientLegalRepresentativeAssociationDto = mock(PatientLegalRepresentativeAssociationDto.class);
		PatientProfileDto legalRepDto = mock(PatientProfileDto.class);
		PatientProfileDto updatedPatientProfileDto = mock(PatientProfileDto.class);
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);
		when(authenticatedUser.getUsername()).thenReturn("albert.smith");
		when(patientService.findPatientProfileByUsername(anyString())).thenReturn(patientProfileDto);
		when(patientProfileDto.getId()).thenReturn((long) 10);
		when(patientLegalRepresentativeAssociationService.getPatientDtoFromLegalRepresentativeDto(any(LegalRepresentativeDto.class))).thenReturn(legalRepDto);
		when(patientLegalRepresentativeAssociationService.getAssociationDtoFromLegalRepresentativeDto(any(LegalRepresentativeDto.class))).thenReturn(patientLegalRepresentativeAssociationDto);
		when(patientService.savePatient(legalRepDto)).thenReturn(updatedPatientProfileDto);
		
	}
	
	@Test
	public void testAddLegalRepresentative() throws Exception {
		
		//required fields are not all filled
		mockMvc.perform(post("/patients/connectionMain.html")
				.param("firstName", "albert")
				.param("lastName", "smith")
				.param("birthDate", "01/01/2001"))
					.andExpect(status().isOk())
					.andExpect(header().string("content-type", "application/json;charset=UTF-8"));
		
		//required fields all filled
		mockMvc.perform(post("/patients/connectionMain.html")
				.param("firstName", "albert")
				.param("lastName", "smith")
				.param("administrativeGenderCode", "M")
				.param("birthDate", "01/01/2001")
				.param("email", "willsmith@example.com"))
					.andExpect(redirectedUrl("/patients/connectionMain.html"));
	}

	@Test
	public void testEditLegalRepresentative() throws Exception {
		
		//required fields are not all filled
		mockMvc.perform(post("/patients/connectionMain/editLegalRepresenttive/" + (long) 10)
				.param("firstName", "albert")
				.param("lastName", "smith")
				.param("birthDate", "01/01/2001"))
					.andExpect(status().isOk())
					.andExpect(header().string("content-type", "application/json;charset=UTF-8"));
		
		//required fields all filled
		mockMvc.perform(post("/patients/connectionMain/editLegalRepresenttive/" + (long) 10)
				.param("firstName", "albert")
				.param("lastName", "smith")
				.param("administrativeGenderCode", "M")
				.param("birthDate", "01/01/2001")
				.param("email", "willsmith@example.com"))
					.andExpect(redirectedUrl("/patients/connectionMain.html"));
	}

	@Test
	public void testDeleteLegalRep() throws Exception {
		when(patientService.isLegalRepForCurrentUser((long) 10)).thenReturn(true);
		
		//successfully deleted
		mockMvc.perform(get("/patients/connectionMain/deleteLegalRep/10"))
			.andExpect(redirectedUrl("/patients/connectionMain.html"));
		
		//failed to delete
		mockMvc.perform(get("/patients/connectionMain/deleteLegalRep/9"))
			.andExpect(view().name("views/resourceNotFound"));
	}

}
