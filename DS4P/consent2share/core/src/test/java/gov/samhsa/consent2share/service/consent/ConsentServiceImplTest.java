package gov.samhsa.consent2share.service.consent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsent;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsentRevocation;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentSectionTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.consent2share.domain.reference.SensitivityPolicyCodeRepository;
import gov.samhsa.consent2share.infrastructure.EchoSignSignatureService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsentServiceImplTest {
	
	/** The cst. */
	@InjectMocks
	ConsentService cst=new ConsentServiceImpl();

	/** The consent repository. */
	@Mock
    ConsentRepository consentRepository;
	
	/** The consent pdf generator. */
	@Mock
	ConsentPdfGenerator consentPdfGenerator;
	
	/** The patient repository. */
	@Mock
	private PatientRepository patientRepository;
	
	/** The individual provider repository. */
	@Mock
	IndividualProviderRepository individualProviderRepository;
	
	/** The organizational provider repository. */
	@Mock
	OrganizationalProviderRepository organizationalProviderRepository;
	
	/** The clinical document type code repository. */
	@Mock
	ClinicalDocumentTypeCodeRepository clinicalDocumentTypeCodeRepository;
	
	/** The clinical document section type code repository. */
	@Mock
	ClinicalDocumentSectionTypeCodeRepository clinicalDocumentSectionTypeCodeRepository;
	
	/** The sensitivity policy code repository. */
	@Mock
	SensitivityPolicyCodeRepository sensitivityPolicyCodeRepository;
	
	/** The purpose of use code repository. */
	@Mock
	PurposeOfUseCodeRepository purposeOfUseCodeRepository;
	
	/** The echo sign signature service. */
	@Mock
	EchoSignSignatureService echoSignSignatureService;
	
	/** The user context. */
	@Mock
	UserContext userContext;
	
	@Mock
	ConsentExportService consentExportService;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		when(patientRepository.findOne(anyLong())).thenReturn(patient);
		
		
		
	}
	
	/**
	 * Test saveConsent. Check if repository is called.
	 */
	@Test
	public void testSaveConsent_Check_if_Repository_is_Called(){
		cst.saveConsent(mock(Consent.class));
		verify(consentRepository).save(any(Consent.class));
	}
	
	/**
	 * Test updateConsent. Check if repository is called.
	 */
	@Test
	public void testUpdateConsent_Check_if_Repository_is_Called(){
		cst.updateConsent(mock(Consent.class));
		verify(consentRepository).save(any(Consent.class));
	}
	
	/**
	 * Test deleteConsent. Check if repository is called.
	 */
	@Test
	public void testDeleteConsent_Check_if_Repository_is_Called(){
		cst.deleteConsent(mock(Consent.class));
		verify(consentRepository).delete(any(Consent.class));
	}
	
	/**
	 * Test deleteConsent. Check if repository and findConsent is called.
	 */
	@Test
	public void testDeleteConsent_Check_if_Repository_and_findConsent_is_called(){
		ConsentService cstspy=spy(cst);
		Consent consent=mock(Consent.class);
		when(cstspy.findConsent((long)1)).thenReturn(consent);
		when(consent.getSignedPdfConsent()).thenReturn(null);
		cstspy.deleteConsent((long)1);
		verify(consentRepository).delete(any(Consent.class));
	}
	
	/**
	 * Test countAllConsents. Check if repository is called.
	 */
	@Test
	public void testCountAllConsents_Check_if_Repository_is_Called(){
		when(consentRepository.count()).thenReturn((long)321);
		cst.countAllConsents();
		verify(consentRepository).count();
	}
	
	/**
	 * Test findConsent. Check if repository is called.
	 */
	@Test
	public void testFindConsent_Check_if_Repository_is_Called(){
		Consent consent=mock(Consent.class);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		cst.findConsent((long)321);
		verify(consentRepository).findOne(anyLong());
	}
	
	/**
	 * Test findAllConsents. Check if repository is called.
	 */
	@Test
	public void testFindAllConsents_Check_if_Repository_is_Called(){
		@SuppressWarnings("unchecked")
		List<Consent> consentList=(List<Consent>)mock(List.class);
		when(consentRepository.findAll()).thenReturn(consentList);
		cst.findAllConsents();
		verify(consentRepository).findAll();
	}
	
	/**
	 * Test signConsent. Check if necessary domain bindings are set and repository is called.
	 */
	@Test
	public void testSignConsent_Check_if_Necessary_Domain_Bindings_are_Set_and_Repository_is_Called(){
		Consent consent=mock(Consent.class);
		AuthenticatedUser user=mock(AuthenticatedUser.class); 
		when(userContext.getCurrentUser()).thenReturn(user);
		
		when(user.getUsername()).thenReturn("mockedUser");
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		
		ConsentService spy=spy(cst);
		ConsentPdfDto consentPdfDto=mock(ConsentPdfDto.class);
		SignedPDFConsent signedPdfConsent=mock(SignedPDFConsent.class);
		when(spy.makeSignedPdfConsent()).thenReturn(signedPdfConsent);
		spy.signConsent(consentPdfDto);
		
		//all the anystring() should be replaced after we've decided what to put there
		verify(signedPdfConsent).setDocumentId(anyString());
		verify(signedPdfConsent).setDocumentNameBySender(anyString());
		verify(signedPdfConsent).setDocumentMessageBySender(anyString());
		verify(signedPdfConsent).setSignerEmail(anyString());
		verify(signedPdfConsent).setDocumentSignedStatus(anyString());
		verify(consent).setSignedPdfConsent(signedPdfConsent);
		verify(consentRepository).save(any(Consent.class));
	}
	
	@Test
	public void testSignConsentRevokation(){
		Consent consent=mock(Consent.class);
		Patient patient=mock(Patient.class);
		when(consent.getPatient()).thenReturn(patient);
		when(patient.getFirstName()).thenReturn("John");
		when(patient.getLastName()).thenReturn("Doe");
		
		AuthenticatedUser user=mock(AuthenticatedUser.class); 
		when(userContext.getCurrentUser()).thenReturn(user);
		
		when(user.getUsername()).thenReturn("mockedUser");
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		
		ConsentService spy=spy(cst);
		ConsentRevokationPdfDto consentRevokationPdfDto=mock(ConsentRevokationPdfDto.class);
		when(consentRevokationPdfDto.getRevokationType()).thenReturn("NO NEVER");
		SignedPDFConsentRevocation signedPDFConsentRevocation=mock(SignedPDFConsentRevocation.class);
		when(spy.makeSignedPDFConsentRevocation()).thenReturn(signedPDFConsentRevocation);
		spy.signConsentRevokation(consentRevokationPdfDto);
		
		verify(signedPDFConsentRevocation).setDocumentId(anyString());
		verify(signedPDFConsentRevocation).setDocumentNameBySender(anyString());
		verify(signedPDFConsentRevocation).setDocumentMessageBySender(anyString());
		verify(signedPDFConsentRevocation).setSignerEmail(anyString());
		verify(signedPDFConsentRevocation).setDocumentSignedStatus(anyString());
		verify(signedPDFConsentRevocation).setDocumentCreatedBy("Doe, John");
		verify(signedPDFConsentRevocation).setDocumentSentOutForSignatureDateTime(any(Date.class));
		verify(consent).setSignedPdfConsentRevoke(signedPDFConsentRevocation);
		verify(consentRepository).save(any(Consent.class));
	}
	
	/**
	 * Test findAllConsentsDtoByPatient .Verify consentListDtos add exact times as the number of consentListdto in the list.
	 */
	@Test
	public void testFindAllConsentsDtoByPatient_Verify_consentListDtos_add_exact_times_as_the_number_of_consentlistDto_in_the_List(){
		List<Consent> consents=new ArrayList<Consent>();
		List<Consent> spyConsents=spy(consents);
		for (int i=0;i<3;i++){
			spyConsents.add(mock(Consent.class));
		}
//		for (Consent consent:spyConsents){
//			Set<ConsentIndividualProviderDisclosureIsMadeTo> consentIndividualProviderDisclosureIsMadeToSet=
//					new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();
//			ConsentIndividualProviderDisclosureIsMadeTo consentIndividualProviderDisclosureIsMadeTo=mock(ConsentIndividualProviderDisclosureIsMadeTo.class);
//			when(consentIndividualProviderDisclosureIsMadeTo.getIndividualProvider().getFirstName()).thenReturn("John");
//			when(consentIndividualProviderDisclosureIsMadeTo.getIndividualProvider().getLastName()).thenReturn("Doe");
//			consentIndividualProviderDisclosureIsMadeToSet.add(consentIndividualProviderDisclosureIsMadeTo);
//			when(consent.getProvidersDisclosureIsMadeTo()).thenReturn(consentIndividualProviderDisclosureIsMadeToSet);
//			
//			Set<ConsentOrganizationalProviderDisclosureIsMadeTo> consentOrganizationalProviderDisclosureIsMadeToSet=
//					new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();
//			ConsentOrganizationalProviderDisclosureIsMadeTo consentOrganizationalProviderDisclosureIsMadeTo=
//					mock(ConsentOrganizationalProviderDisclosureIsMadeTo.class);
//			when(consentOrganizationalProviderDisclosureIsMadeTo.getOrganizationalProvider().getOrgName()).thenReturn("abc company");
//			consentOrganizationalProviderDisclosureIsMadeToSet.add(consentOrganizationalProviderDisclosureIsMadeTo);
//			when(consent.getOrganizationalProvidersDisclosureIsMadeTo()).thenReturn(consentOrganizationalProviderDisclosureIsMadeToSet);
//			
//		}
		
		when (consentRepository.findByPatient(any(Patient.class))).thenReturn(spyConsents);
		ConsentService spy=spy(cst);
		@SuppressWarnings("unchecked")
		ArrayList<ConsentListDto> consentListDtos=(ArrayList<ConsentListDto>)mock(ArrayList.class);
		when(spy.makeConsentListDtos()).thenReturn(consentListDtos);
		spy.findAllConsentsDtoByPatient((long)123);
		verify (consentListDtos,times(3)).add(any(ConsentListDto.class));
	}
	
	/**
	 * Test findConsentPdfDto when consent is signed.
	 */
	@Test
	public void testFindConsentPdfDto_when_Consent_is_Signed(){
		Consent consent=mock(Consent.class);
		SignedPDFConsent signedPDFConsent=mock(SignedPDFConsent.class);
		ConsentPdfDto consentPdfDto=mock(ConsentPdfDto.class);
		Patient patient=mock(Patient.class);
		byte[] signedPdfConsentContent=new byte[]{1,2,3};
		byte[] unsignedPdfConsentContent=new byte[]{4,5,6};
		
		when(patient.getFirstName()).thenReturn("John");
		when(patient.getLastName()).thenReturn("Doe");
		when(signedPDFConsent.getSignedPdfConsentContent()).thenReturn(signedPdfConsentContent);
		when(consent.getPatient()).thenReturn(patient);
		when(consent.getSignedPdfConsent()).thenReturn(signedPDFConsent);
		when(consent.getUnsignedPdfConsent()).thenReturn(unsignedPdfConsentContent);
		when(consent.getName()).thenReturn("A regular consent");
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		ConsentService cstSpy=spy(cst);
		when(cstSpy.makeConsentPdfDto()).thenReturn(consentPdfDto);
		
		cstSpy.findConsentPdfDto((long)2);
		
		verify(consentPdfDto).setContent(signedPdfConsentContent);
		verify(consentPdfDto,never()).setContent(unsignedPdfConsentContent);
		verify(consentPdfDto).setFilename(anyString());
		verify(consentPdfDto).setConsentName("A regular consent");
		verify(consentPdfDto).setId((long)2);
	}

	/**
	 * Test find consentPdfDto when consent is unsigned.
	 */
	@Test
	public void testFindConsentPdfDto_when_Consent_is_Unsigned(){
		Consent consent=mock(Consent.class);
		SignedPDFConsent signedPDFConsent=mock(SignedPDFConsent.class);
		ConsentPdfDto consentPdfDto=mock(ConsentPdfDto.class);
		Patient patient=mock(Patient.class);
		byte[] signedPdfConsentContent=null;
		byte[] unsignedPdfConsentContent=new byte[]{4,5,6};
		
		when(patient.getFirstName()).thenReturn("John");
		when(patient.getLastName()).thenReturn("Doe");
		when(signedPDFConsent.getSignedPdfConsentContent()).thenReturn(signedPdfConsentContent);
		when(consent.getPatient()).thenReturn(patient);
		when(consent.getSignedPdfConsent()).thenReturn(signedPDFConsent);
		when(consent.getUnsignedPdfConsent()).thenReturn(unsignedPdfConsentContent);
		when(consent.getName()).thenReturn("A regular consent");
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		ConsentService cstSpy=spy(cst);
		when(cstSpy.makeConsentPdfDto()).thenReturn(consentPdfDto);
		
		cstSpy.findConsentPdfDto((long)2);
		
		verify(consentPdfDto,never()).setContent(signedPdfConsentContent);
		verify(consentPdfDto).setContent(unsignedPdfConsentContent);
		verify(consentPdfDto).setFilename(anyString());
		verify(consentPdfDto).setConsentName("A regular consent");
		verify(consentPdfDto).setId((long)2);
	}
	
	/**
	 * Test isConsentBelongToThisUser when succeeds.
	 */
	@Test
	public void testIsConsentBelongToThisUser_when_succeeds(){
		Consent consent=mock(Consent.class);
		Patient patient=mock(Patient.class);
		
		when(patientRepository.findOne(anyLong())).thenReturn(patient);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		when(consent.getPatient()).thenReturn(patient);
		Boolean result=cst.isConsentBelongToThisUser((long)1, (long)2);
		assertEquals(true,result);
	}
	
	/**
	 * Test isConsentBelongToThisUser when fails.
	 */
	@Test
	public void testIsConsentBelongToThisUser_when_fails(){
		Consent consent=mock(Consent.class);
		Patient patient=mock(Patient.class);
		Patient patient2=mock(Patient.class);
		when(patientRepository.findOne(anyLong())).thenReturn(patient);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		when(consent.getPatient()).thenReturn(patient2);
		Boolean result=cst.isConsentBelongToThisUser((long)1, (long)2);
		assertEquals(false,result);
	}
	
	/**
	 * Test save consent.
	 * @throws ConsentGenException 
	 */
	@Test
	public void testSaveConsent() throws ConsentGenException{
		ConsentService cstSpy=spy(cst);
		Consent consent=mock(Consent.class);
		when(cstSpy.makeConsent()).thenReturn(consent);
		when(consentExportService.exportConsent2XACML(any(Consent.class))).thenReturn("mockXACML");
		ConsentDto consentDto=mock(ConsentDto.class);
		cstSpy.saveConsent(consentDto);
		verify(consentRepository).save(consent);
	}
	
	/**
	 * Test if makeSignedPdfConsent return correct class.
	 */
	@Test
	public void testMakeSignedPdfConsent_return_correct_class(){
		Object object=cst.makeSignedPdfConsent();
		String className=object.getClass().getName();
		assertEquals("gov.samhsa.consent2share.domain.consent.SignedPDFConsent",className);
	}
	
	/**
	 * Test if makeConsentPdfDto return correct class.
	 */
	@Test
	public void testMakeConsentPdfDto_return_correct_class(){
		Object object=cst.makeConsentPdfDto();
		String className=object.getClass().getName();
		assertEquals("gov.samhsa.consent2share.service.dto.ConsentPdfDto",className);
	}
	
	/**
	 * Test if makeConsentListDtos return correct class.
	 */
	@Test
	public void testMakeConsentListDtos_return_correct_class(){
		Object object=cst.makeConsentListDtos();
		String className=object.getClass().getName();
		assertEquals("java.util.ArrayList",className);
	}
	
	/**
	 * Test find consent entries.
	 */
	@Test
	public void testFindConsentEntries(){
		@SuppressWarnings("unchecked")
		Page<Consent> consentList=(Page<Consent>)mock(Page.class);
		when(consentRepository.findAll(any(org.springframework.data.domain.PageRequest.class))).thenReturn(consentList);
		Object object=cst.findConsentEntries(0, 5);
		verify(consentRepository).findAll(any(org.springframework.data.domain.PageRequest.class));
		assertEquals("java.util.LinkedList",object.getClass().getName());
	}
	
	/**
	 * Test if makeConsent return correct class.
	 */
	@Test
	public void testMakeConsent_return_correct_class(){
		Object object=cst.makeConsent();
		String className=object.getClass().getName();
		assertEquals("gov.samhsa.consent2share.domain.consent.Consent",className);
	}
	
	/**
	 * Test make consent dto_return_correct_class.
	 */
	@Test
	public void testMakeConsentDto_return_correct_class(){
		Object object=cst.makeConsentDto();
		assertEquals("gov.samhsa.consent2share.service.dto.ConsentDto",object.getClass().getName());
	}
	
	/**
	 * Test are there duplicates_when_there_are_duplicates.
	 */
	@Test
	public void testAreThereDuplicates_when_there_are_duplicates(){
		Set<String> set1=new HashSet<String>();
		Set<String> set2=new HashSet<String>();
		
		String a="abc";
		String b="efg";
		String c="hij";
		String d="klm";
		
		set1.add(a);
		set1.add(b);
		set1.add(c);
		set2.add(c);
		set2.add(d);
		
		assertEquals(true,cst.areThereDuplicatesInTwoSets(set1, set2));
	}
	
	/**
	 * Test are there duplicates_when_there_are_no_duplicates.
	 */
	@Test
	public void testAreThereDuplicates_when_there_are_no_duplicates(){
		Set<String> set1=new HashSet<String>();
		Set<String> set2=new HashSet<String>();
		
		String a="abc";
		String b="efg";
		String c="hij";
		String d="klm";
		
		set1.add(a);
		set1.add(b);
		set2.add(c);
		set2.add(d);
		
		assertEquals(false,cst.areThereDuplicatesInTwoSets(set1, set2));
	}
	
	@Test
	public void testFindConsentRevokationPdfDto(){
		ConsentService consentService=spy(cst);
		ConsentRevokationPdfDto consentRevokationPdfDto=mock(ConsentRevokationPdfDto.class);
		Consent consent=mock(Consent.class);
		SignedPDFConsentRevocation signedPDFConsentRevocation=mock(SignedPDFConsentRevocation.class);
		Patient patient=mock(Patient.class);
		
		when(consentService.makeConsentRevokationPdfDto()).thenReturn(consentRevokationPdfDto);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);
		when(consent.getSignedPdfConsentRevoke()).thenReturn(signedPDFConsentRevocation);
		when(consent.getPatient()).thenReturn(patient);
		when(patient.getFirstName()).thenReturn("John");
		when(patient.getLastName()).thenReturn("Doe");
		when(signedPDFConsentRevocation.getContent()).thenReturn(new byte[]{1,2,3});
		
		consentService.findConsentRevokationPdfDto((long)1);
		verify(consentRevokationPdfDto).setFilename(anyString());
		verify(consentRevokationPdfDto).setConsentName(anyString());
		verify(consentRevokationPdfDto).setId(anyLong());
		
	}
	
	
}
