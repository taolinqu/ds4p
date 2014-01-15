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
package gov.samhsa.consent2share.web;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import gov.samhsa.consent2share.dao.audit.JdbcAuditDao;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.AuthenticationFailedException;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.admin.AdminService;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.BasicPatientAccountDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.PatientAdminDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.RecentPatientDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class AdminController.
 */
@Controller
@RequestMapping("/Administrator")
public class AdminController extends AbstractController {
	
	/** The patient service. */
	@Autowired
	PatientService patientService;
	
	/** The admin service. */
	@Autowired
	AdminService adminService;
	
	/** The consent service. */
	@Autowired
	ConsentService consentService;
	
	/** The user context. */
	@Autowired
	UserContext adminUserContext;
	
	/** The jdbc audit dao. */
	@Autowired
	JdbcAuditDao jdbcAuditDao;
	
	/** The administrative gender code service. */
	@Autowired
	AdministrativeGenderCodeService administrativeGenderCodeService;
	
	/** The language code service. */
	@Autowired
	LanguageCodeService languageCodeService;

	/** The marital status code service. */
	@Autowired
	MaritalStatusCodeService maritalStatusCodeService;

	/** The race code service. */
	@Autowired
	RaceCodeService raceCodeService;
	

	/** The religious affiliation code service. */
	@Autowired
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	
	/** The state code service. */
	@Autowired
	StateCodeService stateCodeService;
	
	/** The field validator. */
	@Autowired
	private FieldValidator fieldValidator;
	
	/** The maximum number of recent patient. */
	int maximumNumberOfRecentPatient=5;
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Admin Home Page.
	 * 
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN HOME PAGE
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE
	 *
	 * @param model the model
	 * @param request the request
	 * @param recentVisits the recent visits
	 * @return the string
	 */
	@RequestMapping(value = "adminHome.html")
	public String adminHome(Model model, HttpServletRequest request, 
			@CookieValue(value = "recent_visit", required = false) List<String> recentVisits) {
		List<RecentPatientDto> recentPatientDtos=new ArrayList<RecentPatientDto>();
		if(recentVisits!=null)
			recentPatientDtos=patientService.findRecentPatientDtosById(recentVisits);
		String notify = request.getParameter("notify");
		BasicPatientAccountDto basicPatientAccountDto = new BasicPatientAccountDto();
		
		model.addAttribute("notifyevent", notify);
		model.addAttribute(basicPatientAccountDto);
		model.addAttribute("recentVisits", recentPatientDtos);
		
		return "views/Administrator/adminHome";
	}
	
	
	/**
	 * Admin Patient View Page.
	 * 
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN PATIENT VIEW PAGE.
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE
	 *
	 * @param model the model
	 * @param request the request
	 * @param response the response
	 * @param recentVisits the recent visits
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientView.html")
	public String adminPatientView(Model model, HttpServletRequest request, HttpServletResponse response,
			@CookieValue(value = "recent_visit", required = false) List<String> recentVisits) {
		if(request.getParameter("id")!=null){
			response.addCookie(new Cookie("recent_visit", buildRecentPatientsCookie(recentVisits,request.getParameter("id"))));
			long patientId=Long.parseLong(request.getParameter("id"));
			PatientProfileDto patientProfileDto=patientService.findPatient((long) patientId);
			List<ConsentListDto> consentListDto=consentService.findAllConsentsDtoByPatient((long) patientId);
							
			model.addAttribute("patientProfileDto", patientProfileDto);
			model.addAttribute("consentListDto", consentListDto);
			populateLookupCodes(model);
							
			return "views/Administrator/adminPatientView";
		}else{
			return "redirect:/Administrator/adminHome.html";
		}
	}
	
	/**
	 * Admin Patient View Create Consent Page.
	 * 
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN PATIENT VIEW CREATE CONSENT PAGE.
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE
	 *
	 * @param model the model
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping(value = "adminPatientViewCreateConsent.html")
	public String adminPatientViewCreateConsent(Model model, HttpServletRequest request) {
		if(request.getParameter("id") != null){
			//Initialize patientId variable to -1
			long patientID = -1;
			
			try{
				patientID = Long.parseLong(request.getParameter("id"));
			}catch(NumberFormatException e){
				patientID = -1;
				logger.warn("NumberFormatException caught by adminPatientViewCreateConsent function in AdminController.");
				logger.warn("	Caught NumberFormatException stack trace: ", e);
				
				//TODO: Change redirect location to a more appropriate error page
				return "redirect:/Administrator/adminHome.html";
			}
			
			model.addAttribute("patientIdLink", "?id=" + patientID);
		}else{
			model.addAttribute("patientIdLink", "");
		}
		
		return "views/Administrator/adminPatientViewCreateConsent";
	}
	
	
	/**
	 * Edits the admin profile.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "editAdminProfile.html")
	public String editAdminProfile(Model model) {
		AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
		AdminProfileDto adminProfileDto = adminService.findAdminProfileByUsername(currentUser
				.getUsername());

		model.addAttribute("adminProfileDto", adminProfileDto);
		model.addAttribute("currentUser", currentUser);
		
		return "views/Administrator/editAdminProfile";
	}
	
	/**
	 * Profile.
	 *
	 * @param patientProfileDto the patient profile dto
	 * @param bindingResult the binding result
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "editAdminProfile.html", method = RequestMethod.POST)
	public String profile(@Valid AdminProfileDto adminProfileDto,
			BindingResult bindingResult, Model model) {
		
		fieldValidator.validate(adminProfileDto, bindingResult);
		
		if (bindingResult.hasErrors()) {

			populateLookupCodes(model);
			return "views/Administrator/editAdminProfile";
		} else {
			AuthenticatedUser currentUser = adminUserContext.getCurrentUser();
			
			model.addAttribute("currentUser", currentUser);
			adminProfileDto.setUsername(currentUser.getUsername());
			try {
				adminService.updatePatient(adminProfileDto);
				model.addAttribute("updatedMessage", "Updated your profile successfully!");
			} catch (AuthenticationFailedException e) {
				model.addAttribute("updatedMessage", "Failed. Please check your username and password and try again.");
				PatientProfileDto originalAdminProfileDto=patientService.findPatientProfileByUsername(currentUser
						.getUsername());
				model.addAttribute("adminProfileDto", originalAdminProfileDto);
			}
			
			populateLookupCodes(model);

			return "views/Administrator/editAdminProfile";
		}
	}
	
	
	
	/**
	 * Download consent pdf file.
	 *
	 * @param request the request
	 * @param response the response
	 * @param consentId the consent id
	 * @return the string
	 */
	@RequestMapping(value = "/downloadPdf.html", method = RequestMethod.GET)
	public String downloadConsentPdfFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("consentId") long consentId)
			 {  	
				AbstractPdfDto pdfDto = consentService.findConsentContentDto(consentId);
				
			try {				
				OutputStream out = response.getOutputStream();
				IOUtils.copy(new ByteArrayInputStream(pdfDto.getContent()), out);
				out.flush();
				out.close();

			} catch (IOException e) {
				logger.warn("Error while reading pdf file.");
				logger.warn("The exception is: ", e);
			}
		
		return null;
	}
	
	
	/**
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO DISPLAY THE ADMIN HOME PAGE
	 * IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE.
	 *
	 * @param basicPatientAccountDto the basic patient account dto
	 * @param request the request
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "adminCreatePatientAccount.html", method = RequestMethod.POST)
	public String adminCreatePatientAccount(BasicPatientAccountDto basicPatientAccountDto, HttpServletRequest request, Model model) {
		System.out.println("FUNCTION NOT YET CREATED TO PROCESS THIS FORM");
		return "redirect:/Administrator/adminHome.html";
	}
	
	/**
	 * 
	 * NOTE: THIS FUNCTION IS A TEMPORARY FUNCTION TO PROCESS THE ADMIN EDIT PATIENT PROFILE FORM
	 *       IT MUST BE MODIFIED BEFORE IT IS INTEGREATED WITH THE BACK-END CODE
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "adminEditPatientProfile.html", method = RequestMethod.POST)
	public String adminEditPatientProfile(HttpServletRequest request, Model model) {
		//Initialize patientId variable to -1
		long patientID = -1;
		String patientIdLink = "";
		
		if(request.getParameter("id") != null){
			try{
				patientID = Long.parseLong(request.getParameter("id"));
			}catch(NumberFormatException e){
				patientID = -1;
				logger.warn("NumberFormatException caught by adminEditPatientProfile function in AdminController.");
				logger.warn("	Caught NumberFormatException stack trace: ", e);
				
				//TODO: Change redirect location to a more appropriate error page
				return "redirect:/Administrator/adminHome.html";
			}
			
			patientIdLink = "?id=" + patientID;
		}else{
			patientIdLink = "";
		}
		
		System.out.println("FUNCTION NOT YET CREATED TO PROCESS THIS FORM");
		return "redirect:/Administrator/adminPatientView.html" + patientIdLink;
	}
	
	
	/**
	 * Gets the by first and last name.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the by first and last name
	 */
	@RequestMapping("/patientlookup/query")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<PatientAdminDto> getByFirstAndLastName(@RequestParam(value="firstname",required=false) String firstName,
			@RequestParam(value="lastname",required=false) String lastName){
		if (firstName==null)
			firstName="";
		if (lastName==null)
			lastName="";
		return patientService.findAllPatientByFirstNameAndLastName(firstName, lastName);
	}
	
	//For prove of concept purpose
	/**
	 * Test read patient audit rev.
	 *
	 * @return the string
	 */
	@RequestMapping("/testjdbc/readPatientAuditRev")
	public String testReadPatientAuditRev(){
		jdbcAuditDao.readPatientAuditRev();
		return "redirect:/Administrator/adminHome.html";
	}
	
	/**
	 * Builds the recent patients cookie.
	 *
	 * @param recentVisits the recent visits
	 * @param id the id
	 * @return the string
	 */
	private String buildRecentPatientsCookie(List<String> recentVisits, String id){
		StringBuilder toBeAddedToCookie=new StringBuilder();
		if (recentVisits==null)
			recentVisits=new ArrayList<String>();
		if (recentVisits.contains(id))
			recentVisits.remove(id);
		recentVisits.add(0, id);
		toBeAddedToCookie.append(recentVisits.get(0));
		int listLength=recentVisits.size()>maximumNumberOfRecentPatient?maximumNumberOfRecentPatient:recentVisits.size();
		for(int i=1;i<listLength;i++){
			toBeAddedToCookie.append(","+recentVisits.get(i));
		}
		return toBeAddedToCookie.toString();	
	}
	
	/**
	 * Populate lookup codes.
	 *
	 * @param model the model
	 */
	private void populateLookupCodes(Model model) {

		model.addAttribute("administrativeGenderCodes",
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes());
		model.addAttribute("maritalStatusCodes",
				maritalStatusCodeService.findAllMaritalStatusCodes());
		model.addAttribute("religiousAffiliationCodes",
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes());
		model.addAttribute("raceCodes", raceCodeService.findAllRaceCodes());
		model.addAttribute("languageCodes",
				languageCodeService.findAllLanguageCodes());

		model.addAttribute("stateCodes", stateCodeService.findAllStateCodes());
	}

}
