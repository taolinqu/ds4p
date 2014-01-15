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
package gov.samhsa.consent2share.domain.clinicaldata;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * The Class ClinicalDocument.
 */
@Entity
@Audited
@AuditTable("Clinical_Document_AUDIT")
public class ClinicalDocument {

	/** The name. */
	@NotNull
	@Size(max = 30)
	private String name;

	/** The clinical document type code. */
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ClinicalDocumentTypeCode clinicalDocumentTypeCode;

	/** The description. */
	@Size(max = 500)
	private String description;

	/** The filename. */
	@NotNull
	private String filename;

	/** The content. */
	@NotNull
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] content;

	/** The content type. */
	@NotNull
	private String contentType;

	/** The document size. */
	@NotNull
	private Long documentSize;

	/** The document url. */
	@Size(max = 100)
	private String documentUrl;

	/** The patient. */
	@ManyToOne
	private Patient patient;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the clinical document type code.
	 *
	 * @return the clinical document type code
	 */
	public ClinicalDocumentTypeCode getClinicalDocumentTypeCode() {
		return this.clinicalDocumentTypeCode;
	}

	/**
	 * Sets the clinical document type code.
	 *
	 * @param clinicalDocumentTypeCode the new clinical document type code
	 */
	public void setClinicalDocumentTypeCode(
			ClinicalDocumentTypeCode clinicalDocumentTypeCode) {
		this.clinicalDocumentTypeCode = clinicalDocumentTypeCode;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public byte[] getContent() {
		return this.content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the document size.
	 *
	 * @return the document size
	 */
	public Long getDocumentSize() {
		return this.documentSize;
	}

	/**
	 * Sets the document size.
	 *
	 * @param documentSize the new document size
	 */
	public void setDocumentSize(Long documentSize) {
		this.documentSize = documentSize;
	}

	/**
	 * Gets the document url.
	 *
	 * @return the document url
	 */
	public String getDocumentUrl() {
		return this.documentUrl;
	}

	/**
	 * Sets the document url.
	 *
	 * @param documentUrl the new document url
	 */
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	/**
	 * Gets the patient.
	 *
	 * @return the patient
	 */
	public Patient getPatient() {
		return this.patient;
	}

	/**
	 * Sets the patient.
	 *
	 * @param patient the new patient
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
		return new JSONSerializer().exclude("*.class").deepSerialize(this);
	}

	/**
	 * From json to clinical document.
	 *
	 * @param json the json
	 * @return the clinical document
	 */
	public static ClinicalDocument fromJsonToClinicalDocument(String json) {
		return new JSONDeserializer<ClinicalDocument>().use(null,
				ClinicalDocument.class).deserialize(json);
	}

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<ClinicalDocument> collection) {
		return new JSONSerializer().exclude("*.class")
				.deepSerialize(collection);
	}

	/**
	 * From json array to clinical documents.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<ClinicalDocument> fromJsonArrayToClinicalDocuments(
			String json) {
		return new JSONDeserializer<List<ClinicalDocument>>()
				.use(null, ArrayList.class)
				.use("values", ClinicalDocument.class).deserialize(json);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/** The version. */
	@Version
	@Column(name = "version")
	private Integer version;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
}
