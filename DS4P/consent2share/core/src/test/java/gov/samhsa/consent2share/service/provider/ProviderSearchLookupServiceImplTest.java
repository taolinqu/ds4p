package gov.samhsa.consent2share.service.provider;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;
import gov.samhsa.consent2share.service.reference.StateCodeService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProviderSearchLookupServiceImplTest {
	
	
	@InjectMocks
	ProviderSearchLookupService providerSearchLookupServiceImpl = new ProviderSearchLookupServiceImpl();
	
	@Mock
	StateCodeService stateCodeService;
	
	
//	@Value("${ProviderSearchURL}") 
//	String providerSearchURL;
	
	
	@Test
	public void testGenerateProviderSearchURL_When_State_and_City_Given() {
		ProviderSearchLookupService pss=spy(providerSearchLookupServiceImpl);
		when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
		String usstate="MD";
		String city="baltimore";
		String callUrl=pss.generateProviderSearchURL(usstate,city,null,null,null,null,null,null);
		assertEquals("providerSearchUrl/usstate/MD/city/baltimore",callUrl);		
	}
	
	@Test
	public void testGenerateProviderSearchURL_When_Zipcode_Given() {
		ProviderSearchLookupService pss=spy(providerSearchLookupServiceImpl);
		when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
		String zipcode="21046";
		String callUrl=pss.generateProviderSearchURL(null,null,zipcode,null,null,null,null,null);
		assertEquals("providerSearchUrl/zipcode/21046",callUrl);		
	}
	
	
	@Test
	public void testGenerateProviderSearchURL_When_Firstname_and_Lastname_Given() {
		ProviderSearchLookupService pss=spy(providerSearchLookupServiceImpl);
		when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
		String firstname="abc";
		String lastname="cba";
		String callUrl=pss.generateProviderSearchURL(null,null,null,null,null,null,firstname,lastname);
		assertEquals("providerSearchUrl/firstname/abc/lastname/cba",callUrl);		
	}
	
	@Test
	public void testGenerateProviderSearchURL_When_Specialty_And_Gender_Given() {
		ProviderSearchLookupService pss=spy(providerSearchLookupServiceImpl);
		when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
		String gender="male";
		String specialty="dentist";
		String callUrl=pss.generateProviderSearchURL(null,null,null,gender,specialty,null,null,null);
		assertEquals("providerSearchUrl/gender/male/specialty/dentist",callUrl);		
	}
	
	@Test
	public void testGenerateProviderSearchURL_When_Phone_Given() {
		ProviderSearchLookupService pss=spy(providerSearchLookupServiceImpl);
		when(pss.getProviderSearchURL()).thenReturn("providerSearchUrl");
		String phone="410123456";
		String callUrl=pss.generateProviderSearchURL(null,null,null,null,null,phone,null,null);
		assertEquals("providerSearchUrl/phone/410123456",callUrl);		
	}
		
	@Test
	public void testIsValidatedSearch_All_Field_Is_Blank() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch(null,null,null,null,null,null,null, null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_Only_City_Given() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch(null,"columbia",null,null,null,null,null, null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_State_City_Given() {
		LookupDto stateCode = mock(LookupDto.class);
		List<LookupDto> stateCodes=new ArrayList<LookupDto>();
		stateCodes.add(stateCode);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);
		when(stateCode.getCode()).thenReturn("MD");
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch("MD","columbia",null,null,null,null,null, null);
		assertEquals(true,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_City_Length_Less_Than_Three() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch("MD","co",null,null,null,null,null, null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_Zipcode_Less_Than_Five() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch(null,null,"2104",null,null,null,null, null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_State_And_Zipcode_Given() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch("MD",null,"21046",null,null,null,null, null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_Specialty_Less_Than_Three() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch(null,null,null,null,"br",null,null, null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_Phone_Length_Not_Equal_Ten() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch(null,null,null,null,null,"410552",null, null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_Firstname_Less_Than_Two() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch(null,null,null,null,null,null,"a", null);
		assertEquals(false,validateCall);		
	}
	
	@Test
	public void testIsValidatedSearch_When_Lastname_Less_Than_Two() {
		Boolean validateCall=providerSearchLookupServiceImpl.isValidatedSearch(null,null,null,null,null,null,null,"a");
		assertEquals(false,validateCall);		
	}
}
