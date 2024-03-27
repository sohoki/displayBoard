package com.display.backoffice.sym.log.web;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.sym.log.annotation.NoLogging;
import com.display.backoffice.sym.log.service.EgovSysLogService;
import com.display.backoffice.sym.log.vo.SysLog;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.egovframe.rte.fdl.property.EgovPropertyService;

@RestController
@RequestMapping("/backoffice/loginfo")
public class SysLogController {
  
	private static final Logger LOGGER = LoggerFactory.getLogger(SysLogController.class);
	
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	
	@Autowired
	protected EgovSysLogService sysLogService;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
	@NoLogging
	@RequestMapping(value="SyslogList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView selectSysLogList(@ModelAttribute("loginVO") AdminLoginVO loginVO
			                                              , @ModelAttribute("searchVO") SysLog searchVO
			                                              , HttpServletRequest request
														  , BindingResult bindingResult)throws Exception {
		
		      ModelAndView mav = new ModelAndView("SyslogList");
		      try{
		    	 /* 
		    	 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		 	     if(!isAuthenticated) {
			 	    	mav.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
			 	    	mav.setViewName("/backoffice/login");
		 	    		return mav;
		 	     }else{
		 	    	 mav =sysLogService.selectSysLogList(searchVO); 
		 	    	 mav.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		 	     }
		 	     */
		      }catch(NullPointerException e){
		    	  mav.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
		    	  mav.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		    	  LOGGER.debug("selectSysLogList error: " + e.toString());
		    	  throw e;
		      }catch(Exception e){
		    	  mav.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
		    	  mav.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		    	  LOGGER.debug("selectSysLogList error: " + e.toString());
		    	  throw e;
		      }
		      return mav;
	}
	@NoLogging
	@RequestMapping(value="SyslogInfo.do", method = {RequestMethod.POST})
	public ModelAndView selectSysLogInfo(HttpServletRequest request, 
			                                              @RequestBody SysLog log)throws Exception {
		ModelAndView mav = new ModelAndView(Globals.JSON_VIEW);
		try{
			 
			 mav.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			 //mav.addObject("sysInfo", sysLogService.selectSysLogInfo(log.getRequstId()));
		}catch(NullPointerException e){
			LOGGER.debug("selectSysLogInfo error: " + e.toString());
			mav.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("fail.common.select"));
	    	mav.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    	//throw e;
		}catch(Exception e){
			LOGGER.debug("selectSysLogInfo error: " + e.toString());
			mav.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("fail.common.select"));
	    	mav.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    	//throw e;
		}
		return mav;
	}
	
}
