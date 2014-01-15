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
package gov.samhsa.acs.common.dto;

/**
 * The Class XacmlRequest.
 */
public class XacmlRequest {

	/** The recepient subject npi. */
	private String recepientSubjectNPI;

	/** The intermediary subject npi. */
	private String intermediarySubjectNPI;

	/** The purpose of use. */
	private String purposeOfUse;

	/** The patient id. */
	private String patientId;

	/** The patient unique id. */
	private String patientUniqueId;

	/** The home community id. */
	private String homeCommunityId;

	/** The message id. */
	private String messageId;

	/**
	 * Gets the recepient subject npi.
	 *
	 * @return the recepient subject npi
	 */
	public String getRecepientSubjectNPI() {
		return recepientSubjectNPI;
	}

	/**
	 * Sets the recepient subject npi.
	 *
	 * @param recepientSubjectNPI the new recepient subject npi
	 */
	public void setRecepientSubjectNPI(String recepientSubjectNPI) {
		this.recepientSubjectNPI = recepientSubjectNPI;
	}

	/**
	 * Gets the intermediary subject npi.
	 *
	 * @return the intermediary subject npi
	 */
	public String getIntermediarySubjectNPI() {
		return intermediarySubjectNPI;
	}

	/**
	 * Sets the intermediary subject npi.
	 *
	 * @param intermediarySubjectNPI the new intermediary subject npi
	 */
	public void setIntermediarySubjectNPI(String intermediarySubjectNPI) {
		this.intermediarySubjectNPI = intermediarySubjectNPI;
	}

	/**
	 * Gets the purpose of use.
	 *
	 * @return the purpose of use
	 */
	public String getPurposeOfUse() {
		return purposeOfUse;
	}

	/**
	 * Sets the purpose of use.
	 *
	 * @param purposeOfUse the new purpose of use
	 */
	public void setPurposeOfUse(String purposeOfUse) {
		this.purposeOfUse = purposeOfUse;
	}

	/**
	 * Gets the patient id.
	 *
	 * @return the patient id
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 *
	 * @param patientId the new patient id
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * Gets the patient unique id.
	 *
	 * @return the patient unique id
	 */
	public String getPatientUniqueId() {
		return patientUniqueId;
	}

	/**
	 * Sets the patient unique id.
	 *
	 * @param patientUniqueId the new patient unique id
	 */
	public void setPatientUniqueId(String patientUniqueId) {
		this.patientUniqueId = patientUniqueId;
	}

	/**
	 * Gets the home community id.
	 *
	 * @return the home community id
	 */
	public String getHomeCommunityId() {
		return homeCommunityId;
	}

	/**
	 * Sets the home community id.
	 *
	 * @param homeCommunityId the new home community id
	 */
	public void setHomeCommunityId(String homeCommunityId) {
		this.homeCommunityId = homeCommunityId;
	}

	/**
	 * Gets the message id.
	 *
	 * @return the message id
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * Sets the message id.
	 *
	 * @param messageId the new message id
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}	
}
