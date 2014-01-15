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
package gov.samhsa.consent2share.pixclient.service;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201304UV02;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.openhie.openpixpdq.services.PIXManagerPortType;
import org.openhie.openpixpdq.services.PIXManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * The Class PixManagerServiceImpl.
 */
public class PixManagerServiceImpl implements PixManagerService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The endpoint address. */
	private String endpointAddress;

	/** The wsdl url. */
	final URL wsdlURL = this.getClass().getClassLoader()
			.getResource("PIXPDQManager.wsdl");

	/** The service name. */
	final QName serviceName = new QName("urn:org:openhie:openpixpdq:services",
			"PIXManager_Service");

	/** The port. */
	private PIXManagerPortType port;

	/**
	 * Instantiates a new pix manager service impl.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public PixManagerServiceImpl(String endpointAddress) {
		this.endpointAddress = endpointAddress;
		this.port = createPort();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201301UV02 (org.hl7.v3.types.PRPAIN201301UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201301UV02(PRPAIN201301UV02 body) {
		return this.port.pixManagerPRPAIN201301UV02(body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201302UV02 (org.hl7.v3.types.PRPAIN201302UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201302UV02(PRPAIN201302UV02 body) {
		return this.port.pixManagerPRPAIN201302UV02(body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201304UV02 (org.hl7.v3.types.PRPAIN201304UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201304UV02(PRPAIN201304UV02 body) {
		return this.port.pixManagerPRPAIN201304UV02(body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201309UV02 (org.hl7.v3.types.PRPAIN201309UV02)
	 */
	@Override
	public PRPAIN201310UV02 pixManagerPRPAIN201309UV02(PRPAIN201309UV02 body) {
		return this.port.pixManagerPRPAIN201309UV02(body);
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	PIXManagerPortType getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 * 
	 * @param port
	 *            the new port
	 */
	void setPort(PIXManagerPortType port) {
		this.port = port;
	}

	/**
	 * Creates the port.
	 * 
	 * @return the pIX manager port type
	 */
	private PIXManagerPortType createPort() {
		PIXManagerPortType port = new PIXManagerService(wsdlURL, serviceName)
				.getPIXManagerPortSoap12();
		if (StringUtils.hasText(this.endpointAddress)) {
			BindingProvider bp = (BindingProvider) port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					this.endpointAddress);
		}

		return port;
	}
}
