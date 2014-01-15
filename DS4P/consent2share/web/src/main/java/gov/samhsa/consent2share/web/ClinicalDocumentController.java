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

import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.consent2share.service.dto.ClinicalDocumentDto;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class ClinicalDocumentController.
 */
@Controller
@RequestMapping("/patients")
public class ClinicalDocumentController {

	/** The clinical document service. */
	@Autowired
    ClinicalDocumentService clinicalDocumentService;
	
	/** The patient service. */
	@Autowired
    PatientService patientService;

	/** The clinical document type code service. */
	@Autowired
    ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	/** The user context. */
	@Autowired
	UserContext userContext;
	
	/**
	 * Document home.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/medicalinfo.html")
	public String documentHome(Model model) {
		return "views/clinicaldocuments/mymedicalinfo";
	}
	
	/**
	 * Show clinical documents.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping("/clinicaldocuments.html")
    public String showClinicalDocuments(Model model) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String username = currentUser.getUsername();
		PatientProfileDto patientDto = patientService.findPatientProfileByUsername(username);
		
        List<ClinicalDocumentDto> clinicaldocumentDtos = clinicalDocumentService.findDtoByPatientDto(patientDto);
        for(ClinicalDocumentDto dto : clinicaldocumentDtos) {
        	if(dto.getClinicalDocumentTypeCode()==null) {
        		LookupDto typeCode = new LookupDto();
        		typeCode.setDisplayName("Not Specified");
        		dto.setClinicalDocumentTypeCode(typeCode);
        	}
        }
        List<LookupDto> allDocumentTypeCodes = clinicalDocumentTypeCodeService.findAllClinicalDocumentTypeCodes();
        model.addAttribute("clinicaldocumentDtos", clinicaldocumentDtos);
        model.addAttribute("allDocumentTypeCodes", allDocumentTypeCodes);
        
        return "views/clinicaldocuments/clinicalDocuments";
    }

	/**
	 * Upload clinical documents.
	 *
	 * @param request the request
	 * @param clinicalDocumentDto the clinical document dto
	 * @param file the file
	 * @param documentName the document name
	 * @param description the description
	 * @param documentTypeCode the document type code
	 * @return the string
	 */
	@RequestMapping(value="/clinicaldocuments.html", method=RequestMethod.POST)
    public String uploadClinicalDocuments(HttpServletRequest request,
            @ModelAttribute("document") ClinicalDocumentDto clinicalDocumentDto,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String documentName,
            @RequestParam("description") String description,
            @RequestParam("documentType") String documentTypeCode) {

		try {
			AuthenticatedUser currentUser = userContext.getCurrentUser();
			String username = currentUser.getUsername();
			
			clinicalDocumentDto.setName(documentName);
			clinicalDocumentDto.setDescription(description);
			clinicalDocumentDto.setContent(file.getBytes());
			clinicalDocumentDto.setFilename(file.getOriginalFilename());
			clinicalDocumentDto.setContentType(file.getContentType());
			clinicalDocumentDto.setDocumentSize(file.getSize());
			clinicalDocumentDto.setPatientId(patientService.findIdByUsername(username));

			LookupDto clinicalDocumentTypeCode = new LookupDto();
			clinicalDocumentTypeCode.setCode(documentTypeCode);
			clinicalDocumentDto.setClinicalDocumentTypeCode(clinicalDocumentTypeCode);
			
			clinicalDocumentService.saveClinicalDocument(clinicalDocumentDto);
		} catch (IOException e) {
			e.printStackTrace();
		}
         
        return "redirect:/patients/clinicaldocuments.html";
    }
	
    /**
     * Download.
     *
     * @param request the request
     * @param response the response
     * @param documentId the document id
     * @return the string
     */
    @RequestMapping(value="/downloaddoc.html", method=RequestMethod.POST)
    public String download(HttpServletRequest request,
    		HttpServletResponse response,
    		@RequestParam("download_id") long documentId) {
    	
        ClinicalDocumentDto clinicalDocumentDto = clinicalDocumentService.findClinicalDocumentDto(documentId);
		
    	if(clinicalDocumentService.isDocumentBelongsToThisUser(clinicalDocumentDto)) {
	        try {
	            response.setHeader("Content-Disposition", "attachment;filename=\"" +clinicalDocumentDto.getFilename()+ "\"");
	            response.setHeader("Content-Type", "application/" + clinicalDocumentDto.getContentType());
	            OutputStream out = response.getOutputStream();
	            response.setContentType(clinicalDocumentDto.getContentType());
	            IOUtils.copy(new ByteArrayInputStream(clinicalDocumentDto.getContent()), out);
	            out.flush();
	            out.close();
	         
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}
         
        return null;
    }
    
    /**
     * Removes the.
     *
     * @param documentId the document id
     * @return the string
     */
    @RequestMapping(value="/deletedoc.html", method=RequestMethod.POST)
    public String remove(@RequestParam("delete_id") long documentId) {
    	
        ClinicalDocumentDto clinicalDocumentDto = clinicalDocumentService.findClinicalDocumentDto(documentId);

		if(clinicalDocumentService.isDocumentBelongsToThisUser(clinicalDocumentDto)) {
	    	clinicalDocumentService.deleteClinicalDocument(clinicalDocumentDto);
		}
        return "redirect:/patients/clinicaldocuments.html";
    }
	
}
