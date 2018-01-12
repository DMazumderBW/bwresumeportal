package com.bitwiseglobal.resumemgmt.controller;

import com.bitwiseglobal.resumemgmt.bd.ResumeMgmtBD;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {
	@Autowired
	ResumeMgmtBD resumeMgmtBD;

	private static final Logger logger=Logger.getLogger(LoginController.class);

	@RequestMapping(value="/search-resume",method=RequestMethod.GET)
	public ModelAndView searchResume(HttpServletRequest request) {
		final String methodName="SearchController.searchResume";
		logger.debug(methodName + " started");

		ModelAndView mav = new ModelAndView("bw-search");
		mav.addObject("skills",resumeMgmtBD.getSkills());

		String selectedSkills = request.getParameter("selectedSkills");
		if(null != selectedSkills && !"".equalsIgnoreCase(selectedSkills)) {
			mav.addObject("resumes",resumeMgmtBD.getResumeBySkills(selectedSkills));
		}

		return mav;
	}


	//Added by Aditya More <Aditya.More@bitwiseglobal.com>
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteResume(HttpServletRequest request) {
		logger.debug("Delete selected resume");
		String id = request.getParameter("key");
		//System.out.println("Delete selected resume "+id);
		resumeMgmtBD.softDeleteResume(Integer.parseInt(id));

		String referer = request.getHeader("Referer");
		return "redirect:"+ referer;

	}

	//Added by Aditya More <Aditya.More@bitwiseglobal.com>
	@RequestMapping(value="/resume-trash",method=RequestMethod.GET)
	public ModelAndView searchResumeTrash(HttpServletRequest request) {
		final String methodName="SearchController.searchResume";
		logger.debug(methodName + " started");

		ModelAndView mav = new ModelAndView("bw-resume-trash");
		//mav.addObject("skills",resumeMgmtBD.getSkills());

		mav.addObject("resumes",resumeMgmtBD.getDeletedResume());

/*		String selectedSkills = request.getParameter("selectedSkills");
		if(null != selectedSkills && !"".equalsIgnoreCase(selectedSkills)) {
			mav.addObject("resumes",resumeMgmtBD.getDeletedResumeBySkills(selectedSkills));
		}*/



		return mav;
	}

	//Added by Aditya More <Aditya.More@bitwiseglobal.com>
	@RequestMapping(value = "/restore", method = RequestMethod.GET)
	public String restoreResume(HttpServletRequest request) {
		logger.debug("Restore selected resume");
		String id = request.getParameter("key");
		//System.out.println("Restore selected resume "+id);
		resumeMgmtBD.restoreSoftDeleteResume(Integer.parseInt(id));

		String referer = request.getHeader("Referer");
		return "redirect:"+ referer;

	}

	@RequestMapping(value = "/fileListing",method = RequestMethod.GET)
	public ModelAndView showList(){
		ModelAndView mav = new ModelAndView("fileListing");
		mav.addObject("resumes",resumeMgmtBD.getDeletedResume());
		return mav;
	}

}



