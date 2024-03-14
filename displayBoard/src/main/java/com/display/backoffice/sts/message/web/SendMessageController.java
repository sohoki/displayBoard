package com.display.backoffice.sts.message.web;


import java.util.Arrays;
import java.util.List;

import com.display.backoffice.sts.message.models.SendMessageInfo;
import com.display.backoffice.sts.message.models.SendMessageInfoVO;
import com.display.backoffice.sts.message.service.SendMessageInfoManageService;
import com.display.backoffice.sts.xml.models.SendMsgInfoVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.display.backoffice.uat.service.UniUtilManageService;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@RestController
@RequestMapping("/backoffice")
public class SendMessageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageController.class);
	
	
	@Resource(name="SendMessageInfoManageService")
	private SendMessageInfoManageService sendService;
	
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	 
	 
	@Resource(name="UniUtilManageService")
	private UniUtilManageService utilService;
	 
	
	@RequestMapping("schAgentList.do")
	public ModelAndView selectSchAgentCode(@ModelAttribute("loginVO") AdminLoginVO loginVO,
											@ModelAttribute("searchVO") SendMessageInfoVO searchVO,
                                            HttpServletRequest request		) throws Exception {
		 
		 
		 ModelAndView model = new 	ModelAndView("jsonView");
		 String schCode =  request.getParameter("schCode") != null ? request.getParameter("schCode") : "";
		 try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			if(!isAuthenticated) {
		    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    	model.addObject("status", "LOGIN FAIL");
		    	return model;
			}else {
				HttpSession httpSession = request.getSession(true);
				loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
			  	searchVO.setAdminLevel(loginVO.getAdminLevel());
			    searchVO.setPartId(loginVO.getPartId());
			}
			model.addObject("schState", sendService.selectSendMessageAgentList(searchVO));
		 }catch(NullPointerException e){
				LOGGER.debug("selectGroupInfoList error:" + e.toString());
				model.addObject("status", Globals.STATUS_FAIL);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		 } 	catch(Exception e){
			LOGGER.debug("selectGroupInfoList error:" + e.toString());
			model.addObject("status", Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		 } 	
			
		 
		 return model;
	 }
	 @RequestMapping("schAgentUpdate.do")
	 @ResponseBody
	 public String updateSchAgentCode (@ModelAttribute("loginVO") AdminLoginVO loginVO,
                                            HttpServletRequest request		) throws Exception {
		
		 try{
			 String schCode =  request.getParameter("schCode") != null ? request.getParameter("schCode") : "";
			 String agentCodes =  request.getParameter("agentCodes") != null ? request.getParameter("agentCodes") : "";
			 
			 List<String> agentCodeLst =  Arrays.asList(agentCodes.split("\\s*,\\s*"));
			 
			 
			 
			 SendMessageInfoVO vo = new SendMessageInfoVO();
			 vo.setSchCode(schCode);
			 vo.setAgentCodeLst(agentCodeLst);
			 int ret = sendService.insertSendMessage(vo);
			 
			 return "O";
		 }catch(NullPointerException e){
			 return "F";
		 }catch(Exception e){
			 return "F";
		 }		 
	 }
	 @RequestMapping ("pop_msgLst.do")
		public ModelAndView selectPopMsgResult(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												, @RequestBody SendMessageInfoVO searchVO
												, HttpServletRequest request
												, BindingResult bindingResult) throws Exception {
			
			ModelAndView model = new ModelAndView("jsonView");
			  //공용 확인 하기 
	        
	        try{
	      	  Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	  	      if(!isAuthenticated) {
		  	    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject("status", "LOGIN FAIL");
	  	    		return model;
	  	      }
	  		      
	  		  
	  		  searchVO.setPageUnit(propertiesService.getInt("pageUnit")); 
	  		  searchVO.setPageSize(propertiesService.getInt("pageSize"));
	  	      
	  	      LOGGER.debug("searchVO:" + searchVO.getAgentCode());
	  	      /** pageing */       
	  	   	  PaginationInfo paginationInfo = new PaginationInfo();
	  		  paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
	  		  paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
	  		  paginationInfo.setPageSize(searchVO.getPageSize());

	  		  searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	  		  searchVO.setLastIndex(paginationInfo.getFirstRecordIndex() + searchVO.getPageSize());
	  		  searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	  		  List<SendMessageInfoVO> list = sendService.selectSendMessageAgentHistoryList(searchVO);
	  	      model.addObject("msgLst", list);
	  	      int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
	  	      
	  		  paginationInfo.setTotalRecordCount(totCnt);
	  	      model.addObject("totalCnt", totCnt);
	  	      model.addObject("paginationInfo", paginationInfo);
	  	      model.addObject("status", "SUCCESS");
	  	      
	        }catch(NullPointerException e){
		      	  LOGGER.debug("selectPopMsgResult error: " + e.toString());
	        }catch(Exception e){
	      	  LOGGER.debug("selectPopMsgResult error: " + e.toString());
	        }
		      
		    return model;
			
		}
	  
}
