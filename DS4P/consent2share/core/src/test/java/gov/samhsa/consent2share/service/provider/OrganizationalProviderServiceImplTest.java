package gov.samhsa.consent2share.service.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationalProviderServiceImplTest {
	
	@InjectMocks
	OrganizationalProviderServiceImpl organizationalProviderService = new OrganizationalProviderServiceImpl();

	@Mock
	PatientRepository patientRepository;
	
	@Mock
	ModelMapper modelMapper;

	@Mock
    OrganizationalProviderRepository organizationalProviderRepository;
	
	@Before
	public void before() {
		OrganizationalProviderDto organizationalProviderDto = mock(OrganizationalProviderDto.class);
		Patient patient = mock(Patient.class);
		when(organizationalProviderDto.getUsername()).thenReturn("albert.smith");
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
	}
	
	@Test
	public void testCountAllOrganizationalProviders() {
		organizationalProviderService.countAllOrganizationalProviders();
		verify(organizationalProviderRepository).count();
	}

	@Test
	public void testDeleteOrganizationalProvider() {
		organizationalProviderService.deleteOrganizationalProvider(mock(OrganizationalProvider.class));
		verify(organizationalProviderRepository).delete(any(OrganizationalProvider.class));
	}

	@Test
	public void testDeleteOrganizationalProviderDto() {
		OrganizationalProviderDto organizationalProviderDto = mock(OrganizationalProviderDto.class);
		organizationalProviderService.deleteOrganizationalProviderDto(mock(OrganizationalProviderDto.class));
		verify(organizationalProviderRepository).findByPatientAndNpi(patientRepository.findByUsername(organizationalProviderDto.getUsername()), organizationalProviderDto.getNpi());
	}

	@Test
	public void testFindOrganizationalProvider() {
		organizationalProviderService.findOrganizationalProvider(anyLong());
		verify(organizationalProviderRepository).findOne(anyLong());
	}

	@Test
	public void testFindOrganizationalProviderByNpi() {
		organizationalProviderService.findOrganizationalProviderByNpi(anyString());
		verify(organizationalProviderRepository).findByNpi(anyString());
	}

	@Test
	public void testFindOrganizationalProviderByPatientAndNpi() {
		Patient patient = mock(Patient.class);
		OrganizationalProviderDto organizationalProviderDto = mock(OrganizationalProviderDto.class);
		when(organizationalProviderDto.getNpi()).thenReturn("npi");
		organizationalProviderService.findOrganizationalProviderByPatientAndNpi(patient, organizationalProviderDto.getNpi());
		verify(organizationalProviderRepository).findByPatientAndNpi(any(Patient.class), anyString());
	}

	@Test
	public void testFindOrganizationalProviderDto() {
		organizationalProviderService.findOrganizationalProviderDto(anyLong());
		verify(organizationalProviderRepository).findOne(anyLong());
		verify(modelMapper).map(any(OrganizationalProvider.class), eq(OrganizationalProviderDto.class));
	}

	@Test
	public void testFindAllOrganizationalProviders() {
		organizationalProviderService.findAllOrganizationalProviders();
		verify(organizationalProviderRepository).findAll();
	}

	@Test
	public void testSaveOrganizationalProvider() {
		organizationalProviderService.saveOrganizationalProvider(mock(OrganizationalProvider.class));
		verify(organizationalProviderRepository).save(any(OrganizationalProvider.class));
	}

	@Test
	public void testUpdateOrganizationalProviderOrganizationalProvider() {
		organizationalProviderService.updateOrganizationalProvider(mock(OrganizationalProvider.class));
		verify(organizationalProviderRepository).save(any(OrganizationalProvider.class));
	}

	@Test
	public void testUpdateOrganizationalProviderOrganizationalProviderDto() {
		organizationalProviderService.updateOrganizationalProvider(mock(OrganizationalProviderDto.class));
		verify(organizationalProviderRepository).save(any(OrganizationalProvider.class));
	}

}
