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
package gov.samhsa.consent2share.domain.patient;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import gov.samhsa.consent2share.domain.clinicaldata.Allergy;
import gov.samhsa.consent2share.domain.clinicaldata.ClinicalDocument;
import gov.samhsa.consent2share.domain.clinicaldata.Medication;
import gov.samhsa.consent2share.domain.clinicaldata.Problem;
import gov.samhsa.consent2share.domain.clinicaldata.ProcedureObservation;
import gov.samhsa.consent2share.domain.clinicaldata.ResultObservation;
import gov.samhsa.consent2share.domain.clinicaldata.SocialHistory;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCode;
import gov.samhsa.consent2share.domain.reference.EthnicGroupCode;
import gov.samhsa.consent2share.domain.reference.LanguageCode;
import gov.samhsa.consent2share.domain.reference.MaritalStatusCode;
import gov.samhsa.consent2share.domain.reference.RaceCode;
import gov.samhsa.consent2share.domain.reference.ReligiousAffiliationCode;
import gov.samhsa.consent2share.domain.valueobject.Address;
import gov.samhsa.consent2share.domain.valueobject.Telephone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class Patient.
 */
@Entity
@XmlRootElement
@Audited
@AuditTable("PATIENT_AUDIT")
public class Patient {

	/** The first name. */
	@NotNull
	@Size(min = 2, max = 30)
	private String firstName;

	/** The last name. */
	@NotNull
	@Size(min = 2, max = 30)
	private String lastName;

	/** The prefix. */
	@Size(max = 30)
	private String prefix;

	/** The address. */
	@Embedded
	@AuditOverrides(value = {
			@AuditOverride(name = "streetAddressLine", isAudited = true),
			@AuditOverride(name = "city", isAudited = true),
			@AuditOverride(name = "stateCode", isAudited = true),
			@AuditOverride(name = "postalCode", isAudited = true) })
	private Address address;

	/** The telephone. */
	@Embedded
	@AuditOverride(name = "telephone", isAudited = true)
	private Telephone telephone;

	/** The email. */
	@Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$")
	private String email;

	/** The birth day. */
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date birthDay;

	/** The username. */
	@Size(min = 3, max = 30)
	private String username;

	/** The social security number. */
	@Pattern(regexp = "(\\d{3}-?\\d{2}-?\\d{4})*")
	private String socialSecurityNumber;

	/** The employee id. */
	private String employeeID;

	/** The administrative gender code. */
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private AdministrativeGenderCode administrativeGenderCode;

	/** The marital status code. */
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private MaritalStatusCode maritalStatusCode;

	/** The religious affiliation code. */
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private ReligiousAffiliationCode religiousAffiliationCode;

	/** The race code. */
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private RaceCode raceCode;

	/** The ethnic group code. */
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private EthnicGroupCode ethnicGroupCode;

	/** The language code. */
	@ManyToOne
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private LanguageCode languageCode;

	/** The medical record number. */
	@Size(max = 30)
	private String medicalRecordNumber;

	/** The patient id number. */
	@Size(max = 255)
	private String enterpriseIdentifier;

	/** The consents. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<Consent> consents = new HashSet<Consent>();

	/** The individual providers. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<IndividualProvider> individualProviders = new HashSet<IndividualProvider>();

	/** The organizational providers. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<OrganizationalProvider> organizationalProviders = new HashSet<OrganizationalProvider>();

	/** The clinical documents. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<ClinicalDocument> clinicalDocuments = new HashSet<ClinicalDocument>();

	/** The problems. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	@NotAudited
	private Set<Problem> problems = new HashSet<Problem>();

	/** The allergies. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	@NotAudited
	private Set<Allergy> allergies = new HashSet<Allergy>();

	/** The procedure observations. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	@NotAudited
	private Set<ProcedureObservation> procedureObservations = new HashSet<ProcedureObservation>();

	/** The social histories. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	@NotAudited
	private Set<SocialHistory> socialHistories = new HashSet<SocialHistory>();

	/** The result observations. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	@NotAudited
	private Set<ResultObservation> resultObservations = new HashSet<ResultObservation>();

	/** The medications. */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "patient")
	@NotAudited
	private Set<Medication> medications = new HashSet<Medication>();

	/** The patient legal representative associations. */
	@OneToMany(cascade = CascadeType.ALL)
	private Set<PatientLegalRepresentativeAssociation> patientLegalRepresentativeAssociations = new HashSet<PatientLegalRepresentativeAssociation>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/**
	 * To string.
	 * 
	 * @return the string
	 */
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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
	 * From json to patient.
	 * 
	 * @param json
	 *            the json
	 * @return the patient
	 */
	public static Patient fromJsonToPatient(String json) {
		return new JSONDeserializer<Patient>().use(null, Patient.class)
				.deserialize(json);
	}

	/**
	 * To json array.
	 * 
	 * @param collection
	 *            the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<Patient> collection) {
		return new JSONSerializer().exclude("*.class")
				.deepSerialize(collection);
	}

	/**
	 * From json array to patients.
	 * 
	 * @param json
	 *            the json
	 * @return the collection
	 */
	public static Collection<Patient> fromJsonArrayToPatients(String json) {
		return new JSONDeserializer<List<Patient>>().use(null, ArrayList.class)
				.use("values", Patient.class).deserialize(json);
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
	 * Gets the employee id.
	 * 
	 * @return the employee id
	 */
	public String getEmployeeID() {
		return employeeID;
	}

	/**
	 * Sets the employee id.
	 * 
	 * @param employeeID
	 *            the new employee id
	 */
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

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
	 * @param id
	 *            the new id
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
	 * @param version
	 *            the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the prefix.
	 * 
	 * @return the prefix
	 */
	public String getPrefix() {
		return this.prefix;
	}

	/**
	 * Sets the prefix.
	 * 
	 * @param prefix
	 *            the new prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets the address.
	 * 
	 * @return the address
	 */
	public Address getAddress() {
		return this.address;
	}

	/**
	 * Sets the address.
	 * 
	 * @param address
	 *            the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets the telephone.
	 * 
	 * @return the telephone
	 */
	public Telephone getTelephone() {
		return this.telephone;
	}

	/**
	 * Sets the telephone.
	 * 
	 * @param telephone
	 *            the new telephone
	 */
	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the birth day.
	 * 
	 * @return the birth day
	 */
	public Date getBirthDay() {
		return this.birthDay;
	}

	/**
	 * Sets the birth day.
	 * 
	 * @param birthDay
	 *            the new birth day
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * Gets the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Sets the username.
	 * 
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the social security number.
	 * 
	 * @return the social security number
	 */
	public String getSocialSecurityNumber() {
		return this.socialSecurityNumber;
	}

	/**
	 * Sets the social security number.
	 * 
	 * @param socialSecurityNumber
	 *            the new social security number
	 */
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	/**
	 * Gets the administrative gender code.
	 * 
	 * @return the administrative gender code
	 */
	public AdministrativeGenderCode getAdministrativeGenderCode() {
		return this.administrativeGenderCode;
	}

	/**
	 * Sets the administrative gender code.
	 * 
	 * @param administrativeGenderCode
	 *            the new administrative gender code
	 */
	public void setAdministrativeGenderCode(
			AdministrativeGenderCode administrativeGenderCode) {
		this.administrativeGenderCode = administrativeGenderCode;
	}

	/**
	 * Gets the marital status code.
	 * 
	 * @return the marital status code
	 */
	public MaritalStatusCode getMaritalStatusCode() {
		return this.maritalStatusCode;
	}

	/**
	 * Sets the marital status code.
	 * 
	 * @param maritalStatusCode
	 *            the new marital status code
	 */
	public void setMaritalStatusCode(MaritalStatusCode maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	/**
	 * Gets the religious affiliation code.
	 * 
	 * @return the religious affiliation code
	 */
	public ReligiousAffiliationCode getReligiousAffiliationCode() {
		return this.religiousAffiliationCode;
	}

	/**
	 * Sets the religious affiliation code.
	 * 
	 * @param religiousAffiliationCode
	 *            the new religious affiliation code
	 */
	public void setReligiousAffiliationCode(
			ReligiousAffiliationCode religiousAffiliationCode) {
		this.religiousAffiliationCode = religiousAffiliationCode;
	}

	/**
	 * Gets the race code.
	 * 
	 * @return the race code
	 */
	public RaceCode getRaceCode() {
		return this.raceCode;
	}

	/**
	 * Sets the race code.
	 * 
	 * @param raceCode
	 *            the new race code
	 */
	public void setRaceCode(RaceCode raceCode) {
		this.raceCode = raceCode;
	}

	/**
	 * Gets the ethnic group code.
	 * 
	 * @return the ethnic group code
	 */
	public EthnicGroupCode getEthnicGroupCode() {
		return this.ethnicGroupCode;
	}

	/**
	 * Sets the ethnic group code.
	 * 
	 * @param ethnicGroupCode
	 *            the new ethnic group code
	 */
	public void setEthnicGroupCode(EthnicGroupCode ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}

	/**
	 * Gets the language code.
	 * 
	 * @return the language code
	 */
	public LanguageCode getLanguageCode() {
		return this.languageCode;
	}

	/**
	 * Sets the language code.
	 * 
	 * @param languageCode
	 *            the new language code
	 */
	public void setLanguageCode(LanguageCode languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Gets the medical record number.
	 * 
	 * @return the medical record number
	 */
	public String getMedicalRecordNumber() {
		return this.medicalRecordNumber;
	}

	/**
	 * Sets the medical record number.
	 * 
	 * @param medicalRecordNumber
	 *            the new medical record number
	 */
	public void setMedicalRecordNumber(String medicalRecordNumber) {
		this.medicalRecordNumber = medicalRecordNumber;
	}

	/**
	 * Gets the patient id number.
	 * 
	 * @return the enterprise id number
	 */
	public String getEnterpriseIdentifier() {
		return enterpriseIdentifier;
	}

	/**
	 * Sets the patient id number.
	 * 
	 * @param enterpriseIdentifier
	 *            the new enterprise id number
	 */
	public void setEnterpriseIdentifier(String enterpriseIdentifier) {
		this.enterpriseIdentifier = enterpriseIdentifier;
	}

	/**
	 * Gets the consents.
	 * 
	 * @return the consents
	 */
	public Set<Consent> getConsents() {
		return this.consents;
	}

	/**
	 * Sets the consents.
	 * 
	 * @param consents
	 *            the new consents
	 */
	public void setConsents(Set<Consent> consents) {
		this.consents = consents;
	}

	/**
	 * Gets the clinical documents.
	 * 
	 * @return the clinical documents
	 */
	public Set<ClinicalDocument> getClinicalDocuments() {
		return this.clinicalDocuments;
	}

	/**
	 * Sets the clinical documents.
	 * 
	 * @param clinicalDocuments
	 *            the new clinical documents
	 */
	public void setClinicalDocuments(Set<ClinicalDocument> clinicalDocuments) {
		this.clinicalDocuments = clinicalDocuments;
	}

	/**
	 * Gets the problems.
	 * 
	 * @return the problems
	 */
	public Set<Problem> getProblems() {
		return this.problems;
	}

	/**
	 * Sets the problems.
	 * 
	 * @param problems
	 *            the new problems
	 */
	public void setProblems(Set<Problem> problems) {
		this.problems = problems;
	}

	/**
	 * Gets the allergies.
	 * 
	 * @return the allergies
	 */
	public Set<Allergy> getAllergies() {
		return this.allergies;
	}

	/**
	 * Sets the allergies.
	 * 
	 * @param allergies
	 *            the new allergies
	 */
	public void setAllergies(Set<Allergy> allergies) {
		this.allergies = allergies;
	}

	/**
	 * Gets the procedure observations.
	 * 
	 * @return the procedure observations
	 */
	public Set<ProcedureObservation> getProcedureObservations() {
		return this.procedureObservations;
	}

	/**
	 * Sets the procedure observations.
	 * 
	 * @param procedureObservations
	 *            the new procedure observations
	 */
	public void setProcedureObservations(
			Set<ProcedureObservation> procedureObservations) {
		this.procedureObservations = procedureObservations;
	}

	/**
	 * Gets the social histories.
	 * 
	 * @return the social histories
	 */
	public Set<SocialHistory> getSocialHistories() {
		return this.socialHistories;
	}

	/**
	 * Sets the social histories.
	 * 
	 * @param socialHistories
	 *            the new social histories
	 */
	public void setSocialHistories(Set<SocialHistory> socialHistories) {
		this.socialHistories = socialHistories;
	}

	/**
	 * Gets the result observations.
	 * 
	 * @return the result observations
	 */
	public Set<ResultObservation> getResultObservations() {
		return this.resultObservations;
	}

	/**
	 * Sets the result observations.
	 * 
	 * @param resultObservations
	 *            the new result observations
	 */
	public void setResultObservations(Set<ResultObservation> resultObservations) {
		this.resultObservations = resultObservations;
	}

	/**
	 * Gets the medications.
	 * 
	 * @return the medications
	 */
	public Set<Medication> getMedications() {
		return this.medications;
	}

	/**
	 * Sets the medications.
	 * 
	 * @param medications
	 *            the new medications
	 */
	public void setMedications(Set<Medication> medications) {
		this.medications = medications;
	}

	/**
	 * Gets the patient legal representative associations.
	 * 
	 * @return the patient legal representative associations
	 */
	public Set<PatientLegalRepresentativeAssociation> getPatientLegalRepresentativeAssociations() {
		return this.patientLegalRepresentativeAssociations;
	}

	/**
	 * Sets the patient legal representative associations.
	 * 
	 * @param patientLegalRepresentativeAssociations
	 *            the new patient legal representative associations
	 */
	public void setPatientLegalRepresentativeAssociations(
			Set<PatientLegalRepresentativeAssociation> patientLegalRepresentativeAssociations) {
		this.patientLegalRepresentativeAssociations = patientLegalRepresentativeAssociations;
	}

	/**
	 * Gets the individual providers.
	 * 
	 * @return the individual providers
	 */
	public Set<IndividualProvider> getIndividualProviders() {
		return individualProviders;
	}

	/**
	 * Sets the individual providers.
	 * 
	 * @param individualProviders
	 *            the new individual providers
	 */
	public void setIndividualProviders(
			Set<IndividualProvider> individualProviders) {
		this.individualProviders = individualProviders;
	}

	/**
	 * Gets the organizational providers.
	 * 
	 * @return the organizational providers
	 */
	public Set<OrganizationalProvider> getOrganizationalProviders() {
		return organizationalProviders;
	}

	/**
	 * Sets the organizational providers.
	 * 
	 * @param organizationalProviders
	 *            the new organizational providers
	 */
	public void setOrganizationalProviders(
			Set<OrganizationalProvider> organizationalProviders) {
		this.organizationalProviders = organizationalProviders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * Hash code.
	 * 
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * Equals.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
