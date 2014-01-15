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

import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Class HomeController.
 */
@Controller
public class HomeController {
	
	/** The user context. */
	@Autowired
	private UserContext userContext;
	
	@Autowired
	private UsersRepository usersRepository;

	/**
	 * Home.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
	public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        if (!(auth instanceof AnonymousAuthenticationToken)) { /*The user is logged in :)*/ 
                        return "redirect:/defaultLoginPage.html";
        }
		return "views/index";
	}	
	
	/**
	 * Index.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "index.html", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        if (!(auth instanceof AnonymousAuthenticationToken)) { /*The user is logged in :)*/ 
                        return "redirect:/patients/home.html";
        }

		if (request.getParameter("expired") != null) {
			model.addAttribute("expired", true);

		}

		return "views/index";
	}
	
	@RequestMapping(value = "defaultLoginPage.html", method = RequestMethod.GET)
	public String roleDispatcher(Model model, HttpServletRequest request) {
		AuthenticatedUser currentUser = userContext.getCurrentUser();
		String notify = request.getParameter("notify");
		if (notify!=null)
			notify="?notify="+notify;
		else
			notify="";
		if (!currentUser.getUsername().equals("anonymousUser")){
			Users users=usersRepository.loadUserByUsername(currentUser.getUsername());
			if(users!=null){
				if(users.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
					return "redirect:/Administrator/adminHome.html"+notify;
				else if (users.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")))
					return "redirect:/patients/home.html"+notify;
			}
		}
		return "redirect:/index.html";
	}

	/**
	 * Error page.
	 *
	 * @param request the request
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "error.html")
	public String error(HttpServletRequest request, Model model) {
		model.addAttribute(
				"errorCode",
				"Error "
						+ request
								.getAttribute("javax.servlet.error.status_code"));
		Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("<ul>");
		while (throwable != null) {
			errorMessage.append("<li>")
					.append(escapeTags(throwable.getMessage())).append("</li>");
			throwable = throwable.getCause();
		}
		errorMessage.append("</ul>");
		model.addAttribute("errorMessage", errorMessage.toString());
		return "WEB-INF/views/error.html";
	}

	/**
	 * Substitute 'less than' and 'greater than' symbols by its HTML entities.
	 *
	 * @param text the text
	 * @return the string
	 */
	private String escapeTags(String text) {
		if (text == null) {
			return null;
		}
		return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
