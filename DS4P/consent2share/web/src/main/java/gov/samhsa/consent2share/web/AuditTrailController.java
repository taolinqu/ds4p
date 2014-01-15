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
import gov.samhsa.consent2share.service.audit.AuditService;
import gov.samhsa.consent2share.service.dto.HistoryDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class AuditTrailController.
 */
@Controller
@RequestMapping("/patients")
public class AuditTrailController extends AbstractController {

	/** The patient audit service. */
	@Autowired
	AuditService patientAuditService;

	/** The user context. */
	@Autowired
	UserContext userContext;

	/**
	 * Activity history.
	 *
	 * @param model the model
	 * @return the string
	 * @throws ClassNotFoundException the class not found exception
	 */
	@RequestMapping(value = "activityHistory.html")
	public String activityHistory(Model model) throws ClassNotFoundException {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		List<HistoryDto> historyListDtos = patientAuditService
				.findAllHistory(currentUser.getUsername());

		model.addAttribute("historys", historyListDtos);

		return "views/patients/activityHistory";
	}

}
