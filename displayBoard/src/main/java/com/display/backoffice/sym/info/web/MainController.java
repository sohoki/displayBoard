package com.display.backoffice.sym.info.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.EgovMessageSource;
import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import com.display.backoffice.sym.log.annotation.NoLogging;


@RestController
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	
  
	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /** TRACE */
    @Resource(name="leaveaTrace")
    LeaveaTrace leaveaTrace;
	
	// include 파일 정리 
    @NoLogging
	@RequestMapping(value="/backoffice/inc/top_inc.do")   
	public ModelAndView nhisTop() throws Exception{	
		ModelAndView mav = new ModelAndView("/backoffice/inc/top_inc");
		return mav;
	}
    @NoLogging
	@RequestMapping(value="/backoffice/inc/bottom_inc.do")	
	public ModelAndView nhisBottom() throws Exception{				
		ModelAndView mav = new ModelAndView("/backoffice/inc/bottom_inc");
		return mav;
	}
}
