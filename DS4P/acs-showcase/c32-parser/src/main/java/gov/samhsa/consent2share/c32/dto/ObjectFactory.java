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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.18 at 11:05:26 AM EDT 
//


package gov.samhsa.consent2share.c32.dto;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


// TODO: Auto-generated Javadoc
/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.hl7.v3 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    /** The Constant _Provider_QNAME. */
    private final static QName _Provider_QNAME = new QName("urn:hl7-org:v3", "provider");
    
    /** The Constant _GreenCCDBodyPlanOfCarePlannedProcedure_QNAME. */
    private final static QName _GreenCCDBodyPlanOfCarePlannedProcedure_QNAME = new QName("urn:hl7-org:v3", "plannedProcedure");
    
    /** The Constant _GreenCCDBodyPlanOfCarePlannedAct_QNAME. */
    private final static QName _GreenCCDBodyPlanOfCarePlannedAct_QNAME = new QName("urn:hl7-org:v3", "plannedAct");
    
    /** The Constant _GreenCCDBodyPlanOfCarePlannedObservation_QNAME. */
    private final static QName _GreenCCDBodyPlanOfCarePlannedObservation_QNAME = new QName("urn:hl7-org:v3", "plannedObservation");
    
    /** The Constant _IvlTsHigh_QNAME. */
    private final static QName _IvlTsHigh_QNAME = new QName("urn:hl7-org:v3", "high");
    
    /** The Constant _IvlTsLow_QNAME. */
    private final static QName _IvlTsLow_QNAME = new QName("urn:hl7-org:v3", "low");
    
    /** The Constant _IvlTsCenter_QNAME. */
    private final static QName _IvlTsCenter_QNAME = new QName("urn:hl7-org:v3", "center");
    
    /** The Constant _IvlTsWidth_QNAME. */
    private final static QName _IvlTsWidth_QNAME = new QName("urn:hl7-org:v3", "width");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.hl7.v3
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Comments }.
     *
     * @return the comments
     */
    public Comments createComments() {
        return new Comments();
    }

    /**
     * Create an instance of {@link Indication }.
     *
     * @return the indication
     */
    public Indication createGreenCCDBodyMedicationsMedicationIndication() {
        return new Indication();
    }

    /**
     * Create an instance of {@link Period }.
     *
     * @return the period
     */
    public Period createPivlTsPeriod() {
        return new Period();
    }

    /**
     * Create an instance of {@link HealthcareProvider }.
     *
     * @return the healthcare provider
     */
    public HealthcareProvider createGreenCCDHeaderHealthcareProvidersHealthcareProvider() {
        return new HealthcareProvider();
    }

    /**
     * Create an instance of {@link PreferenceInd }.
     *
     * @return the preference ind
     */
    public PreferenceInd createGreenCCDHeaderLanguagesSpokenLanguageSpokenPreferenceInd() {
        return new PreferenceInd();
    }

    /**
     * Create an instance of {@link Type }.
     *
     * @return the type
     */
    public Type createGreenCCDBodyAdvanceDirectivesAdvanceDirectiveType() {
        return new Type();
    }

    /**
     * Create an instance of {@link InformationSource }.
     *
     * @return the information source
     */
    public InformationSource createInformationSource() {
        return new InformationSource();
    }

    /**
     * Create an instance of {@link Ii }.
     *
     * @return the ii
     */
    public Ii createIi() {
        return new Ii();
    }

    /**
     * Create an instance of {@link Pq }.
     *
     * @return the pq
     */
    public Pq createPq() {
        return new Pq();
    }

    /**
     * Create an instance of {@link Vehicle }.
     *
     * @return the vehicle
     */
    public Vehicle createGreenCCDBodyMedicationsMedicationVehicle() {
        return new Vehicle();
    }

    /**
     * Create an instance of {@link StatusOfMedication }.
     *
     * @return the status of medication
     */
    public StatusOfMedication createGreenCCDBodyMedicationsMedicationStatusOfMedication() {
        return new StatusOfMedication();
    }

    /**
     * Create an instance of {@link InsuranceProviders }.
     *
     * @return the insurance providers
     */
    public InsuranceProviders createGreenCCDBodyInsuranceProviders() {
        return new InsuranceProviders();
    }

    /**
     * Create an instance of {@link TypeOfMedication }.
     *
     * @return the type of medication
     */
    public TypeOfMedication createGreenCCDBodyMedicationsMedicationTypeOfMedication() {
        return new TypeOfMedication();
    }

    /**
     * Create an instance of {@link PatientInformation }.
     *
     * @return the patient information
     */
    public PatientInformation createGreenCCDHeaderPersonalInformationPatientInformation() {
        return new PatientInformation();
    }

    /**
     * Create an instance of {@link U }.
     *
     * @return the u
     */
    public U createU() {
        return new U();
    }

    /**
     * Create an instance of {@link gov.samhsa.consent2share.c32.dto.Value.Integer }
     *
     * @return the integer
     */
    public gov.samhsa.consent2share.c32.dto.Value.Integer createValueInteger() {
        return new gov.samhsa.consent2share.c32.dto.Value.Integer();
    }

    /**
     * Create an instance of {@link Img }.
     *
     * @return the img
     */
    public Img createImg() {
        return new Img();
    }

    /**
     * Create an instance of {@link Qualifier.Value }
     *
     * @return the value
     */
    public Qualifier.Value createQualifierValue() {
        return new Qualifier.Value();
    }

    /**
     * Create an instance of {@link FacilityLocation }.
     *
     * @return the facility location
     */
    public FacilityLocation createGreenCCDBodyEncountersEncounterFacilityLocation() {
        return new FacilityLocation();
    }

    /**
     * Create an instance of {@link Medication }.
     *
     * @return the medication
     */
    public Medication createGreenCCDBodyMedicationsMedication() {
        return new Medication();
    }

    /**
     * Create an instance of {@link Cd }.
     *
     * @return the cd
     */
    public Cd createCd() {
        return new Cd();
    }

    /**
     * Create an instance of {@link BirthPlace }.
     *
     * @return the birth place
     */
    public BirthPlace createGreenCCDHeaderPersonalInformationPatientInformationPersonInformationBirthPlace() {
        return new BirthPlace();
    }

    /**
     * Create an instance of {@link Immunizations }.
     *
     * @return the immunizations
     */
    public Immunizations createGreenCCDBodyImmunizations() {
        return new Immunizations();
    }

    /**
     * Create an instance of {@link OrderInformation }.
     *
     * @return the order information
     */
    public OrderInformation createGreenCCDBodyMedicationsMedicationOrderInformation() {
        return new OrderInformation();
    }

    /**
     * Create an instance of {@link AdvanceDirectives }.
     *
     * @return the advance directives
     */
    public AdvanceDirectives createGreenCCDBodyAdvanceDirectives() {
        return new AdvanceDirectives();
    }

    /**
     * Create an instance of {@link MemberInformation }.
     *
     * @return the member information
     */
    public MemberInformation createGreenCCDBodyInsuranceProvidersInsuranceProviderMemberInformation() {
        return new MemberInformation();
    }

    /**
     * Create an instance of {@link Addr }.
     *
     * @return the addr
     */
    public Addr createAddr() {
        return new Addr();
    }

    /**
     * Create an instance of {@link Version }.
     *
     * @return the version
     */
    public Version createGreenCCDHeaderVersion() {
        return new Version();
    }

    /**
     * Create an instance of {@link Allergy }.
     *
     * @return the allergy
     */
    public Allergy createGreenCCDBodyAllergiesAllergy() {
        return new Allergy();
    }

    /**
     * Create an instance of {@link Pnm }.
     *
     * @return the pnm
     */
    public Pnm createPnm() {
        return new Pnm();
    }

    /**
     * Create an instance of {@link CauseOfDeath }.
     *
     * @return the cause of death
     */
    public CauseOfDeath createGreenCCDBodyConditionsConditionCauseOfDeath() {
        return new CauseOfDeath();
    }

    /**
     * Create an instance of {@link PersonInformation }.
     *
     * @return the person information
     */
    public PersonInformation createGreenCCDHeaderPersonalInformationPatientInformationPersonInformation() {
        return new PersonInformation();
    }

    /**
     * Create an instance of {@link CodedBrandName }.
     *
     * @return the coded brand name
     */
    public CodedBrandName createMedicationInformationCodedBrandName() {
        return new CodedBrandName();
    }

    /**
     * Create an instance of {@link GuarantorInformation }.
     *
     * @return the guarantor information
     */
    public GuarantorInformation createGreenCCDBodyInsuranceProvidersInsuranceProviderGuarantorInformation() {
        return new GuarantorInformation();
    }

    /**
     * Create an instance of {@link Name }.
     *
     * @return the name
     */
    public Name createQualifierName() {
        return new Name();
    }

    /**
     * Create an instance of {@link Pregnancies }.
     *
     * @return the pregnancies
     */
    public Pregnancies createGreenCCDBodyPregnancies() {
        return new Pregnancies();
    }

    /**
     * Create an instance of {@link Header }.
     *
     * @return the header
     */
    public Header createGreenCCDHeader() {
        return new Header();
    }

    /**
     * Create an instance of {@link Conditions }.
     *
     * @return the conditions
     */
    public Conditions createGreenCCDBodyConditions() {
        return new Conditions();
    }

    /**
     * Create an instance of {@link OrganizationInformation }.
     *
     * @return the organization information
     */
    public OrganizationInformation createOrganizationInformation() {
        return new OrganizationInformation();
    }

    /**
     * Create an instance of {@link DoseRestriction }.
     *
     * @return the dose restriction
     */
    public DoseRestriction createGreenCCDBodyMedicationsMedicationDoseRestriction() {
        return new DoseRestriction();
    }

    /**
     * Create an instance of {@link ReferenceDocumentURL }.
     *
     * @return the reference document url
     */
    public ReferenceDocumentURL createInformationSourceAuthorReferenceReferenceDocumentURL() {
        return new ReferenceDocumentURL();
    }

    /**
     * Create an instance of {@link InsuranceProvider }.
     *
     * @return the insurance provider
     */
    public InsuranceProvider createGreenCCDBodyInsuranceProvidersInsuranceProvider() {
        return new InsuranceProvider();
    }

    /**
     * Create an instance of {@link SubscriberInformation }.
     *
     * @return the subscriber information
     */
    public SubscriberInformation createGreenCCDBodyInsuranceProvidersInsuranceProviderSubscriberInformation() {
        return new SubscriberInformation();
    }

    /**
     * Create an instance of {@link Procedure }.
     *
     * @return the procedure
     */
    public Procedure createGreenCCDBodyProceduresProcedure() {
        return new Procedure();
    }

    /**
     * Create an instance of {@link CodedProductName }.
     *
     * @return the coded product name
     */
    public CodedProductName createMedicationInformationCodedProductName() {
        return new CodedProductName();
    }

    /**
     * Create an instance of {@link gov.samhsa.consent2share.c32.dto.Value }
     *
     * @return the value
     */
    public gov.samhsa.consent2share.c32.dto.Value createValue() {
        return new gov.samhsa.consent2share.c32.dto.Value();
    }

    /**
     * Create an instance of {@link Tele }.
     *
     * @return the tele
     */
    public Tele createTele() {
        return new Tele();
    }

    /**
     * Create an instance of {@link B }.
     *
     * @return the b
     */
    public B createB() {
        return new B();
    }

    /**
     * Create an instance of {@link A }.
     *
     * @return the a
     */
    public A createA() {
        return new A();
    }

    /**
     * Create an instance of {@link ProcedureType }.
     *
     * @return the procedure type
     */
    public ProcedureType createGreenCCDBodyProceduresProcedureProcedureType() {
        return new ProcedureType();
    }

    /**
     * Create an instance of {@link ContactType }.
     *
     * @return the contact type
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link Reference }.
     *
     * @return the reference
     */
    public Reference createInformationSourceAuthorReference() {
        return new Reference();
    }

    /**
     * Create an instance of {@link SocialHistoryEntry }.
     *
     * @return the social history entry
     */
    public SocialHistoryEntry createSocialHistoryEntry() {
        return new SocialHistoryEntry();
    }

    /**
     * Create an instance of {@link EncounterType }.
     *
     * @return the encounter type
     */
    public EncounterType createGreenCCDBodyEncountersEncounterEncounterType() {
        return new EncounterType();
    }

    /**
     * Create an instance of {@link PlanOfCare }.
     *
     * @return the plan of care
     */
    public PlanOfCare createGreenCCDBodyPlanOfCare() {
        return new PlanOfCare();
    }

    /**
     * Create an instance of {@link Support }.
     *
     * @return the support
     */
    public Support createGreenCCDHeaderSupportsSupport() {
        return new Support();
    }

    /**
     * Create an instance of {@link GreenCCD }.
     *
     * @return the green ccd
     */
    public GreenCCD createGreenCCD() {
        return new GreenCCD();
    }

    /**
     * Create an instance of {@link I }.
     *
     * @return the i
     */
    public I createI() {
        return new I();
    }

    /**
     * Create an instance of {@link InformationSourceName }.
     *
     * @return the information source name
     */
    public InformationSourceName createInformationSourceInformationSourceName() {
        return new InformationSourceName();
    }

    /**
     * Create an instance of {@link Result }.
     *
     * @return the result
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link AdvanceDirective }.
     *
     * @return the advance directive
     */
    public AdvanceDirective createGreenCCDBodyAdvanceDirectivesAdvanceDirective() {
        return new AdvanceDirective();
    }

    /**
     * Create an instance of {@link Comment }.
     *
     * @return the comment
     */
    public Comment createComment() {
        return new Comment();
    }

    /**
     * Create an instance of {@link ProviderInformation }.
     *
     * @return the provider information
     */
    public ProviderInformation createProviderInformation() {
        return new ProviderInformation();
    }

    /**
     * Create an instance of {@link MedicationInformation }.
     *
     * @return the medication information
     */
    public MedicationInformation createMedicationInformation() {
        return new MedicationInformation();
    }

    /**
     * Create an instance of {@link Li }.
     *
     * @return the li
     */
    public Li createLi() {
        return new Li();
    }

    /**
     * Create an instance of {@link FulfillmentHistory }.
     *
     * @return the fulfillment history
     */
    public FulfillmentHistory createGreenCCDBodyMedicationsMedicationFulfillmentHistory() {
        return new FulfillmentHistory();
    }

    /**
     * Create an instance of {@link PlannedEvent }.
     *
     * @return the planned event
     */
    public PlannedEvent createPlannedEvent() {
        return new PlannedEvent();
    }

    /**
     * Create an instance of {@link TreatingProvider }.
     *
     * @return the treating provider
     */
    public TreatingProvider createGreenCCDBodyConditionsConditionTreatingProvider() {
        return new TreatingProvider();
    }

    /**
     * Create an instance of {@link Author }.
     *
     * @return the author
     */
    public Author createInformationSourceAuthor() {
        return new Author();
    }

    /**
     * Create an instance of {@link Ul }.
     *
     * @return the ul
     */
    public Ul createUl() {
        return new Ul();
    }

    /**
     * Create an instance of {@link Allergies }.
     *
     * @return the allergies
     */
    public Allergies createGreenCCDBodyAllergies() {
        return new Allergies();
    }

    /**
     * Create an instance of {@link PersonalInformation }.
     *
     * @return the personal information
     */
    public PersonalInformation createGreenCCDHeaderPersonalInformation() {
        return new PersonalInformation();
    }

    /**
     * Create an instance of {@link Encounter }.
     *
     * @return the encounter
     */
    public Encounter createGreenCCDBodyEncountersEncounter() {
        return new Encounter();
    }

    /**
     * Create an instance of {@link Condition }.
     *
     * @return the condition
     */
    public Condition createGreenCCDBodyConditionsCondition() {
        return new Condition();
    }

    /**
     * Create an instance of {@link Medications }.
     *
     * @return the medications
     */
    public Medications createGreenCCDBodyMedications() {
        return new Medications();
    }

    /**
     * Create an instance of {@link IvlTs }.
     *
     * @return the ivl ts
     */
    public IvlTs createIvlTs() {
        return new IvlTs();
    }

    /**
     * Create an instance of {@link FillNumber }.
     *
     * @return the fill number
     */
    public FillNumber createGreenCCDBodyMedicationsMedicationFulfillmentHistoryFillNumber() {
        return new FillNumber();
    }

    /**
     * Create an instance of {@link FillStatus }.
     *
     * @return the fill status
     */
    public FillStatus createGreenCCDBodyMedicationsMedicationFulfillmentHistoryFillStatus() {
        return new FillStatus();
    }

    /**
     * Create an instance of {@link Ol }.
     *
     * @return the ol
     */
    public Ol createOl() {
        return new Ol();
    }

    /**
     * Create an instance of {@link LanguageSpoken }.
     *
     * @return the language spoken
     */
    public LanguageSpoken createGreenCCDHeaderLanguagesSpokenLanguageSpoken() {
        return new LanguageSpoken();
    }

    /**
     * Create an instance of {@link Ts }.
     *
     * @return the ts
     */
    public Ts createTs() {
        return new Ts();
    }

    /**
     * Create an instance of {@link Fills }.
     *
     * @return the fills
     */
    public Fills createGreenCCDBodyMedicationsMedicationOrderInformationFills() {
        return new Fills();
    }

    /**
     * Create an instance of {@link P }.
     *
     * @return the p
     */
    public P createP() {
        return new P();
    }

    /**
     * Create an instance of {@link Encounters }.
     *
     * @return the encounters
     */
    public Encounters createGreenCCDBodyEncounters() {
        return new Encounters();
    }

    /**
     * Create an instance of {@link Qualifier }.
     *
     * @return the qualifier
     */
    public Qualifier createQualifier() {
        return new Qualifier();
    }

    /**
     * Create an instance of {@link Br }.
     *
     * @return the br
     */
    public Br createBr() {
        return new Br();
    }

    /**
     * Create an instance of {@link PivlTs }.
     *
     * @return the pivl ts
     */
    public PivlTs createPivlTs() {
        return new PivlTs();
    }

    /**
     * Create an instance of {@link Supports }.
     *
     * @return the supports
     */
    public Supports createGreenCCDHeaderSupports() {
        return new Supports();
    }

    /**
     * Create an instance of {@link Immunization }.
     *
     * @return the immunization
     */
    public Immunization createGreenCCDBodyImmunizationsImmunization() {
        return new Immunization();
    }

    /**
     * Create an instance of {@link VitalSigns }.
     *
     * @return the vital signs
     */
    public VitalSigns createGreenCCDBodyVitalSigns() {
        return new VitalSigns();
    }

    /**
     * Create an instance of {@link HealthcareProviders }.
     *
     * @return the healthcare providers
     */
    public HealthcareProviders createGreenCCDHeaderHealthcareProviders() {
        return new HealthcareProviders();
    }

    /**
     * Create an instance of {@link Results }.
     *
     * @return the results
     */
    public Results createResults() {
        return new Results();
    }

    /**
     * Create an instance of {@link Body }.
     *
     * @return the body
     */
    public Body createGreenCCDBody() {
        return new Body();
    }

    /**
     * Create an instance of {@link Cs }.
     *
     * @return the cs
     */
    public Cs createCs() {
        return new Cs();
    }

    /**
     * Create an instance of {@link Pregnancy }.
     *
     * @return the pregnancy
     */
    public Pregnancy createGreenCCDBodyPregnanciesPregnancy() {
        return new Pregnancy();
    }

    /**
     * Create an instance of {@link LanguagesSpoken }.
     *
     * @return the languages spoken
     */
    public LanguagesSpoken createGreenCCDHeaderLanguagesSpoken() {
        return new LanguagesSpoken();
    }

    /**
     * Create an instance of {@link Custodian }.
     *
     * @return the custodian
     */
    public Custodian createGreenCCDHeaderCustodian() {
        return new Custodian();
    }

    /**
     * Create an instance of {@link Onm }.
     *
     * @return the onm
     */
    public Onm createOnm() {
        return new Onm();
    }

    /**
     * Create an instance of {@link TextType }.
     *
     * @return the text type
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link Ce }.
     *
     * @return the ce
     */
    public Ce createCe() {
        return new Ce();
    }

    /**
     * Create an instance of {@link SocialHistory }.
     *
     * @return the social history
     */
    public SocialHistory createGreenCCDBodySocialHistory() {
        return new SocialHistory();
    }

    /**
     * Create an instance of {@link Mo }.
     *
     * @return the mo
     */
    public Mo createMo() {
        return new Mo();
    }

    /**
     * Create an instance of {@link ReasonForVisit }.
     *
     * @return the reason for visit
     */
    public ReasonForVisit createGreenCCDBodyEncountersEncounterReasonForVisit() {
        return new ReasonForVisit();
    }

    /**
     * Create an instance of {@link Procedures }.
     *
     * @return the procedures
     */
    public Procedures createGreenCCDBodyProcedures() {
        return new Procedures();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrganizationInformation }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< organization information>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "provider")
    public JAXBElement<OrganizationInformation> createProvider(OrganizationInformation value) {
        return new JAXBElement<OrganizationInformation>(_Provider_QNAME, OrganizationInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlannedEvent }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< planned event>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "plannedProcedure", scope = PlanOfCare.class)
    public JAXBElement<PlannedEvent> createGreenCCDBodyPlanOfCarePlannedProcedure(PlannedEvent value) {
        return new JAXBElement<PlannedEvent>(_GreenCCDBodyPlanOfCarePlannedProcedure_QNAME, PlannedEvent.class, PlanOfCare.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlannedEvent }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< planned event>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "plannedAct", scope = PlanOfCare.class)
    public JAXBElement<PlannedEvent> createGreenCCDBodyPlanOfCarePlannedAct(PlannedEvent value) {
        return new JAXBElement<PlannedEvent>(_GreenCCDBodyPlanOfCarePlannedAct_QNAME, PlannedEvent.class, PlanOfCare.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlannedEvent }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< planned event>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "plannedObservation", scope = PlanOfCare.class)
    public JAXBElement<PlannedEvent> createGreenCCDBodyPlanOfCarePlannedObservation(PlannedEvent value) {
        return new JAXBElement<PlannedEvent>(_GreenCCDBodyPlanOfCarePlannedObservation_QNAME, PlannedEvent.class, PlanOfCare.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ts }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< ts>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "high", scope = IvlTs.class)
    public JAXBElement<Ts> createIvlTsHigh(Ts value) {
        return new JAXBElement<Ts>(_IvlTsHigh_QNAME, Ts.class, IvlTs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ts }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< ts>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "low", scope = IvlTs.class)
    public JAXBElement<Ts> createIvlTsLow(Ts value) {
        return new JAXBElement<Ts>(_IvlTsLow_QNAME, Ts.class, IvlTs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ts }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< ts>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "center", scope = IvlTs.class)
    public JAXBElement<Ts> createIvlTsCenter(Ts value) {
        return new JAXBElement<Ts>(_IvlTsCenter_QNAME, Ts.class, IvlTs.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pq }{@code >}}.
     *
     * @param value the value
     * @return the JAXB element< pq>
     */
    @XmlElementDecl(namespace = "urn:hl7-org:v3", name = "width", scope = IvlTs.class)
    public JAXBElement<Pq> createIvlTsWidth(Pq value) {
        return new JAXBElement<Pq>(_IvlTsWidth_QNAME, Pq.class, IvlTs.class, value);
    }

}
