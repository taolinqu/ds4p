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
package gov.samhsa.acs.documentsegmentation.tools;

import gov.samhsa.acs.common.bean.RuleExecutionContainer;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.util.EncryptTool;
import gov.va.ds4p.cas.RuleExecutionResponse;

import java.security.Key;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.KeyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Class DocumentEncrypter.
 */
public class DocumentEncrypterImpl implements DocumentEncrypter {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.util
	 * .DocumentEncrypter#encryptDocument(java.security.Key, java.lang.String,
	 * gov.samhsa.acs.common.bean.RuleExecutionContainer)
	 */
	@Override
	public String encryptDocument(Key deSedeEncryptKey, String document,
			RuleExecutionContainer ruleExecutionContainer) {
		Document xmlDocument = null;
		String xmlString = null;
		boolean encryptDoc = false;

		for (RuleExecutionResponse response : ruleExecutionContainer
				.getExecutionResponseList()) {
			if (response.getDocumentObligationPolicy().equals("ENCRYPT")) {
				encryptDoc = true;
				break;
			}
		}

		if (encryptDoc) {

			try {
				xmlDocument = documentXmlConverter.loadDocument(document);

				/*
				 * Get a key to be used for encrypting the element. Here we are
				 * generating an AES key.
				 */
				Key aesSymmetricKey = EncryptTool.generateDataEncryptionKey();

				String algorithmURI = XMLCipher.TRIPLEDES_KeyWrap;

				XMLCipher keyCipher = XMLCipher.getInstance(algorithmURI);
				keyCipher.init(XMLCipher.WRAP_MODE, deSedeEncryptKey);
				EncryptedKey encryptedKey = keyCipher.encryptKey(xmlDocument,
						aesSymmetricKey);

				// encrypt the contents of the document element
				Element rootElement = xmlDocument.getDocumentElement();

				encryptElement(xmlDocument, aesSymmetricKey, encryptedKey,
						rootElement);

				// Output encrypted doc to file
				// FileHelper.writeDocToFile(xmlDocument, "Encrypted_C32.xml");

				xmlString = documentXmlConverter.convertXmlDocToString(xmlDocument);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new DS4PException(e.toString(), e);
			}
		}
		return xmlString;
	}

	/**
	 * Gets the document xml converter.
	 * 
	 * @return the document xml converter
	 */
	public DocumentXmlConverter getDocumentXmlConverter() {
		return documentXmlConverter;
	}

	/**
	 * Sets the document xml converter.
	 * 
	 * @param documentXmlConverter
	 *            the new document xml converter
	 */
	public void setDocumentXmlConverter(
			DocumentXmlConverter documentXmlConverter) {
		this.documentXmlConverter = documentXmlConverter;
	}

	/**
	 * Encrypt element.
	 * 
	 * @param xmlDocument
	 *            the xml document
	 * @param encryptSymmetricKey
	 *            the encrypt symmetric key
	 * @param encryptedKey
	 *            the encrypted key
	 * @param element
	 *            the element
	 * @throws XMLEncryptionException
	 *             the xML encryption exception
	 * @throws Exception
	 *             the exception
	 */
	void encryptElement(Document xmlDocument, Key encryptSymmetricKey,
			EncryptedKey encryptedKey, Element element)
			throws XMLEncryptionException, Exception {

		String algorithmURI = XMLCipher.AES_128;

		XMLCipher xmlCipher = XMLCipher.getInstance(algorithmURI);
		xmlCipher.init(XMLCipher.ENCRYPT_MODE, encryptSymmetricKey);

		/*
		 * Setting keyinfo inside the encrypted data being prepared.
		 */
		EncryptedData encryptedData = xmlCipher.getEncryptedData();
		KeyInfo keyInfo = new KeyInfo(xmlDocument);
		keyInfo.add(encryptedKey);
		encryptedData.setKeyInfo(keyInfo);

		xmlCipher.doFinal(xmlDocument, element, true);
	}
}
