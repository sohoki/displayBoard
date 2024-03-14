package com.display.backoffice.sym.agent.web;


import egovframework.com.cmm.AdminLoginVO;
import com.display.backoffice.sym.agent.models.AgentInfo;
import com.display.backoffice.sym.agent.models.AgentInfoVO;
import com.display.backoffice.sym.agent.service.AgentInfoManageService;



import com.display.backoffice.sym.monter.service.DetailPageInfoManageService;
import com.display.backoffice.sym.monter.models.DetailPageInfoVO;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;
import com.display.backoffice.sts.report.models.ReportPageInfo;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;
import com.display.backoffice.uat.models.UniUtilInfo;
import com.display.backoffice.uat.service.UniUtilManageService;
import com.display.backoffice.uat.web.UniUtilManageController;


import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import com.display.backoffice.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springmodules.validation.commons.DefaultBeanValidator;

@RestController
@RequestMapping("/backoffice/contentManage/")
public class AgentRestController {

     private static final Logger LOGGER = LoggerFactory.getLogger(AgentRestController.class);
 	
	
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
	@Resource(name = "AgentInfoManageService")
    protected AgentInfoManageService agentService;
	
	@Resource(name = "CmmnDetailCodeManageService")
	private EgovCcmCmmnDetailCodeManageService cmmnDetailService;
	
	@Resource(name="DisplayPageInfoManageService")
	private DisplayPageInfoManageService disService;
	
	@Resource(name="egovEqupIdGnrService")
	private EgovIdGnrService egovEqupIdGnrService;
	
	/*@Resource(name="DetailPageInfoManageService")
	private DetailPageInfoManageService detailService;
	*/
	@Autowired 
	private DetailPageInfoManageService detailService;
	
	@Resource(name="UniUtilManageService")
	private UniUtilManageService utilService;
	
	
	
}
