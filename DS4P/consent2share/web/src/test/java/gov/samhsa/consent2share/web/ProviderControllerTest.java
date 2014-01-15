package gov.samhsa.consent2share.web;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.PatientConnectionDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientLegalRepresentativeAssociationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import gov.samhsa.consent2share.service.provider.ProviderSearchLookupService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ProviderControllerTest {

	@InjectMocks
	ProviderController providerController = new ProviderController();
	
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
	NotificationService notificationService;

	@Mock
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	@Mock
	StateCodeService stateCodeService;
	
	@Mock
	LegalRepresentativeTypeCodeService legalRepresentativeTypeCodeService;
	
	@Mock
	PatientLegalRepresentativeAssociationService patientLegalRepresentativeAssociationService;
	
	@Mock
	ProviderSearchLookupService providerSearchLookupService;

	@Mock
	UserContext userContext;
	
	@Mock
	private FieldValidator fieldValidator;
	
	@Mock
	private MessageSource messageProperties; 
	
	@Mock
	AuthenticatedUser authenticatedUser;
	
	@Mock
	HashMap<String,String> Result;
	
	MockMvc mockMvc;
	
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.providerController).build();
		PatientConnectionDto patientConnectionDto = mock(PatientConnectionDto.class);
		Set<IndividualProviderDto> individualProviders = new HashSet<IndividualProviderDto>();
		Set<OrganizationalProviderDto> organizationalProviders = new HashSet<OrganizationalProviderDto>();
		for(int i=0;i<3;i++) {
			individualProviders.add(mock(IndividualProviderDto.class));
			organizationalProviders.add(mock(OrganizationalProviderDto.class));
			
		}
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);
		when(authenticatedUser.getUsername()).thenReturn("albert.smith");
		when(patientService.findPatientConnectionByUsername(anyString())).thenReturn(patientConnectionDto);
		when(patientConnectionDto.getIndividualProviders()).thenReturn(individualProviders);
		when(patientConnectionDto.getOrganizationalProviders()).thenReturn(organizationalProviders);
	}
	
	@Test
	public void testConnectionMain() throws Exception {
		mockMvc.perform(get("/patients/connectionMain.html"))
			.andExpect(view().name("views/patients/connectionMain"));
	}
	
	
	@Test
	public void testAjaxProviderSearch_Checked_Status_Is_OK() throws Exception {
		 when(providerSearchLookupService.isValidatedSearch(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
		 when(providerSearchLookupService.providerSearch(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn("artifitial JSON");
		 mockMvc.perform(get("/patients/providerSearch.html"))
         	.andExpect(status().isOk())
         	.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testDeleteIndividualProvider() throws Exception {
		IndividualProviderDto individualProviderDto = mock(IndividualProviderDto.class);
		when(individualProviderService.findIndividualProviderDto(anyLong())).thenReturn(individualProviderDto);
		mockMvc.perform(post("/patients/connectionMain/deleteOrganizationalProvider").param("organizationalProviderid", "10"))
			.andExpect(redirectedUrl("/patients/connectionMain.html"));
	}

	@Test
	public void testDeleteOrganizationalProvider() throws Exception {
		OrganizationalProviderDto organizationalProviderDto = mock(OrganizationalProviderDto.class);
		when(organizationalProviderService.findOrganizationalProviderDto(anyLong())).thenReturn(organizationalProviderDto);
		mockMvc.perform(post("/patients/connectionMain/deleteOrganizationalProvider").param("organizationalProviderid", "10"))
			.andExpect(redirectedUrl("/patients/connectionMain.html"));
	}


	@Test
	public void testProviderSearchModel() throws Exception {
		PatientProfileDto patientProfileDto = mock(PatientProfileDto.class);
		when(patientService.findPatientProfileByUsername(anyString())).thenReturn(patientProfileDto);
		mockMvc.perform(get("/patients/connectionProviderAdd.html"))
			.andExpect(view().name("views/patients/connectionProviderAdd"));
	}

	@Test
	public void testProviderSearchString() throws Exception {
		when(Result.get("entityType")).thenReturn("entityTypeValue");
		when(Result.get("providerOrganizationName")).thenReturn("providerOrganizationNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialTitleorPosition")).thenReturn("authorizedOfficialTitleorPositionValue");
		when(Result.get("authorizedOfficialNamePrefixText")).thenReturn("authorizedOfficialNamePrefixTextValue");
		when(Result.get("authorizedOfficialTelephoneNumber")).thenReturn("authorizedOfficialTelephoneNumberValue");
		mockMvc.perform(post("/patients/connectionProviderAdd.html").param("querySent", "{\"npi\":\"1114252178\",\"entityType\":\"Individual\",\"replacementNpi\":\"\",\"employerIdentificationNumber\":\"\",\"isSoleProprietor\":false,\"isOrganizationSubpart\":false,\"parentOrganizationLbn\":\"\",\"parentOrganizationTin\":\"\",\"providerOrganizationName\":\"\",\"providerLastName\":\"MORGAN\",\"providerFirstName\":\"TERRENCE\",\"providerMiddleName\":\"\",\"providerNamePrefixText\":\"MR.\",\"providerNameSuffixText\":\"\",\"providerCredentialText\":\"LGSW, CSC-AD\",\"providerFirstLineBusinessMailingAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessMailingAddress\":\"\",\"providerBusinessMailingAddressCityName\":\"BALTIMORE\",\"providerBusinessMailingAddressStateName\":\"MD\",\"providerBusinessMailingAddressPostalCode\":\"212373903\",\"providerBusinessMailingAddressCountryCode\":\"US\",\"providerBusinessMailingAddressTelephoneNumber\":\"4108876465\",\"providerBusinessMailingAddressFaxNumber\":\"4106876005\",\"providerFirstLineBusinessPracticeLocationAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessPracticeLocationAddress\":\"\",\"providerBusinessPracticeLocationAddressCityName\":\"BALTIMORE\",\"providerBusinessPracticeLocationAddressStateName\":\"MD\",\"providerBusinessPracticeLocationAddressPostalCode\":\"212373903\",\"providerBusinessPracticeLocationAddressCountryCode\":\"US\",\"providerBusinessPracticeLocationAddressTelephoneNumber\":\"4108876465\",\"providerBusinessPracticeLocationAddressFaxNumber\":\"4106876005\",\"providerEnumerationDate\":\"10/08/2009\",\"lastUpdateDate\":\"10/08/2009\",\"npideactivationReasonCode\":\"\",\"npideactivationReason\":\"\",\"npideactivationDate\":\"\",\"npireactivationDate\":\"\",\"providerGenderCode\":\"M\",\"providerGender\":\"Male\",\"authorizedOfficialLastName\":\"\",\"authorizedOfficialFirstName\":\"\",\"authorizedOfficialMiddleName\":\"\",\"authorizedOfficialTitleorPosition\":\"\",\"authorizedOfficialNamePrefixText\":\"\",\"authorizedOfficialNameSuffixText\":\"\",\"authorizedOfficialCredentialText\":\"\",\"authorizedOfficialTelephoneNumber\":\"\",\"healthcareProviderTaxonomyCode_1\":\"101YM0800X\",\"providerLicenseNumber_1\":\"14742\",\"providerLicenseNumberStateCode_1\":\"MD\",\"healthcareProviderTaxonomy_1\":\"Mental Health\"}"))
			.andExpect(redirectedUrl("/patients/connectionMain.html"));
		
	}

}
