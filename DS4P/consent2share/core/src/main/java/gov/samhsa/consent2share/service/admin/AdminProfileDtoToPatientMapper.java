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
package gov.samhsa.consent2share.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCode;
import gov.samhsa.consent2share.domain.reference.AdministrativeGenderCodeRepository;
import gov.samhsa.consent2share.domain.reference.CountryCodeRepository;
import gov.samhsa.consent2share.domain.reference.LanguageCodeRepository;
import gov.samhsa.consent2share.domain.reference.MaritalStatusCodeRepository;
import gov.samhsa.consent2share.domain.reference.RaceCodeRepository;
import gov.samhsa.consent2share.domain.reference.ReligiousAffiliationCodeRepository;
import gov.samhsa.consent2share.domain.reference.StateCodeRepository;
import gov.samhsa.consent2share.domain.valueobject.Address;
import gov.samhsa.consent2share.domain.valueobject.Telephone;
import gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;

/**
 * The Class PatientProfileDtoToPatientMapper.
 */
@Component
public class AdminProfileDtoToPatientMapper implements
		DtoToDomainEntityMapper<AdminProfileDto, Patient> {

	/** The patient repository. */
	private PatientRepository patientRepository;
	
	/** The state code repository. */
	private StateCodeRepository stateCodeRepository;
	
	/** The country code repository. */
	private CountryCodeRepository countryCodeRepository;
	
	/** The administrative gender code repository. */
	private AdministrativeGenderCodeRepository administrativeGenderCodeRepository;
	
	/** The language code repository. */
	private LanguageCodeRepository languageCodeRepository;
	
	/** The marital status code repository. */
	private MaritalStatusCodeRepository maritalStatusCodeRepository;
	
	/** The race code repository. */
	private RaceCodeRepository raceCodeRepository;
	
	/** The religious affiliation code repository. */
	private ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository;

	/**
	 * Instantiates a new patient profile dto to patient mapper.
	 *
	 * @param patientRepository the patient repository
	 * @param stateCodeRepository the state code repository
	 * @param countryCodeRepository the country code repository
	 * @param administrativeGenderCodeRepository the administrative gender code repository
	 * @param languageCodeRepository the language code repository
	 * @param maritalStatusCodeRepository the marital status code repository
	 * @param raceCodeRepository the race code repository
	 * @param religiousAffiliationCodeRepository the religious affiliation code repository
	 */
	@Autowired
	public AdminProfileDtoToPatientMapper(
			PatientRepository patientRepository,
			StateCodeRepository stateCodeRepository,
			CountryCodeRepository countryCodeRepository,
			AdministrativeGenderCodeRepository administrativeGenderCodeRepository,
			LanguageCodeRepository languageCodeRepository,
			MaritalStatusCodeRepository maritalStatusCodeRepository,
			RaceCodeRepository raceCodeRepository,
			ReligiousAffiliationCodeRepository religiousAffiliationCodeRepository) {
		super();
		this.patientRepository = patientRepository;
		this.stateCodeRepository = stateCodeRepository;
		this.countryCodeRepository = countryCodeRepository;
		this.administrativeGenderCodeRepository = administrativeGenderCodeRepository;
		this.languageCodeRepository = languageCodeRepository;
		this.maritalStatusCodeRepository = maritalStatusCodeRepository;
		this.raceCodeRepository = raceCodeRepository;
		this.religiousAffiliationCodeRepository = religiousAffiliationCodeRepository;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.DtoToDomainEntityMapper#map(java.lang.Object)
	 */
	@Override
	public Patient map(AdminProfileDto adminProfileDto) {
		Patient patient = null;

		// Since username is not required, so need to check if it is null
		if (adminProfileDto.getUsername() == null) {
			if(adminProfileDto.getId() != null) {
				patient = patientRepository.findOne(adminProfileDto.getId());
			}
			else {
				patient = new Patient();
			}
		} else {
			patient = patientRepository
					.findByUsername(adminProfileDto.getUsername());
		}
		patient.setFirstName(adminProfileDto.getFirstName());
		patient.setLastName(adminProfileDto.getLastName());
		patient.setEmail(adminProfileDto.getEmail());
		patient.setEmployeeID(adminProfileDto.getEmployeeId());
		


		if (StringUtils.hasText(adminProfileDto.getAdministrativeGenderCode())) {
			AdministrativeGenderCode administrativeGenderCode = administrativeGenderCodeRepository
					.findByCode(adminProfileDto.getAdministrativeGenderCode());
			patient.setAdministrativeGenderCode(administrativeGenderCode);
		} else {
			patient.setAdministrativeGenderCode(null);
		}


		return patient;
	}

}
