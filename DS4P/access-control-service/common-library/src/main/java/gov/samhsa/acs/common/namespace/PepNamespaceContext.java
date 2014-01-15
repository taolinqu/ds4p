/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.common.namespace;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

/**
 * The Class PepNamespaceContext.
 */
public class PepNamespaceContext implements NamespaceContext {

	/** The Constant HL7_PREFIX. */
	public static final String HL7_PREFIX = "hl7";

	/** The Constant HL7_URI. */
	public static final String HL7_URI = "urn:hl7-org:v3";

	/** The Constant XENC_PREFIX. */
	public static final String XENC_PREFIX = "xenc";

	/** The Constant XENC_URI. */
	public static final String XENC_URI = "http://www.w3.org/2001/04/xmlenc#";

	/** The Constant RIM_PREFIX. */
	public static final String RIM_PREFIX = "rim";

	/** The Constant RIM_URI. */
	public static final String RIM_URI = "urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0";

	/** The Constant QUERY_PREFIX. */
	public static final String QUERY_PREFIX = "query";

	/** The Constant QUERY_URI. */
	public static final String QUERY_URI = "urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0";

	/** The Constant IHE_ITI_XDSB_2007_PREFIX. */
	public static final String IHE_ITI_XDSB_2007_PREFIX = "xdsb";

	/** The Constant IHE_ITI_XDSB_2007_URI. */
	public static final String IHE_ITI_XDSB_2007_URI = "urn:ihe:iti:xds-b:2007";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
	 */
	@Override
	public String getNamespaceURI(String prefix) {
		String uri;
		if (prefix.equals(HL7_PREFIX))
			uri = HL7_URI;
		else if (prefix.equals(XENC_PREFIX))
			uri = XENC_URI;
		else if (prefix.equals(RIM_PREFIX))
			uri = RIM_URI;
		else if (prefix.equals(QUERY_PREFIX))
			uri = QUERY_URI;
		else if (prefix.equals(IHE_ITI_XDSB_2007_PREFIX))
			uri = IHE_ITI_XDSB_2007_URI;
		else
			uri = null;
		return uri;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Iterator getPrefixes(String val) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
	 */
	@Override
	public String getPrefix(String uri) {
		throw new UnsupportedOperationException();
	}
}
