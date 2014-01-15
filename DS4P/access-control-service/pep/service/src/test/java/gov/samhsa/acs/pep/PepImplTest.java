package gov.samhsa.acs.pep;

import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.pep.c32getter.C32Getter;
import gov.samhsa.acs.pep.xdsbregistry.XdsbRegistry;
import gov.samhsa.acs.pep.xdsbrepository.XdsbRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class PepImplTest {

	/** The permit. */
	private final String PERMIT = "Permit";

	/** The context handler. */
	@Mock
	private ContextHandler contextHandler;

	/** The C32 getter. */
	@Mock
	private C32Getter c32Getter;

	/** The document segmentation service. */
	@Mock
	private DocumentSegmentation documentSegmentation;

	/** The data handler to bytes converter. */
	@Mock
	private DataHandlerToBytesConverter dataHandlerToBytesConverter;

	/** The xdsbRepository. */
	@Mock
	private XdsbRepository xdsbRepository;

	/** The xdsbRegistry. */
	@Mock
	private XdsbRegistry xdsbRegistry;

	/** The subject purpose of use. */
	private String subjectPurposeOfUse = "TREAT";

	/** The subject locality. */
	private String subjectLocality = "2.16.840.1.113883.3.467";

	/** The organization. */
	private String organization = "SAMHSA";

	/** The organization id. */
	private String organizationId = "FEiSystems";

	/** The resource name. */
	private String resourceName = "NwHINDirectSend";

	/** The resource type. */
	private String resourceType = "C32";

	/** The resource action. */
	private String resourceAction = "Execute";

	/** The home community id. */
	private String homeCommunityId="2.16.840.1.113883.3.467";
	
	@InjectMocks
	PepImpl pep=new PepImpl(contextHandler, c32Getter, documentSegmentation, dataHandlerToBytesConverter, xdsbRepository, xdsbRegistry);

	@Test
	public void testHandleC32Request() {
	}

}
