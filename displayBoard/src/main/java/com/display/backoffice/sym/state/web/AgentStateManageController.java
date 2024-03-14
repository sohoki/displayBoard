package com.display.backoffice.sym.state.web;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.display.backoffice.uat.service.UniUtilManageService;
import egovframework.com.cmm.AdminLoginVO;
import com.display.backoffice.sym.state.models.AgentStateInfoVO;
import com.display.backoffice.sym.state.service.AgentstateInfoManageServie;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/backoffice/basicManage")
public class AgentStateManageController {

	
	 private static final Logger LOGGER = LoggerFactory.getLogger(AgentStateManageController.class);
		
		
	 @Resource(name="egovMessageSource")
	 protected EgovMessageSource egovMessageSource;
	
	 @Resource(name = "propertiesService")
     protected EgovPropertyService propertiesService;
	 
	 
	 @Resource(name="UniUtilManageService")
	 private UniUtilManageService utilService;
	 
	 @Resource(name="AgentstateInfoManageServie")
	 private AgentstateInfoManageServie stateService;
	 
	 SimpleDateFormat  formtoDay = new SimpleDateFormat("yyyyMMdd");
	 
	 
	 @RequestMapping(value="agentStateAjax.do")
	 public ModelAndView selectagentStateAjax(@ModelAttribute("loginVO") AdminLoginVO loginVO,
			                                  @RequestBody AgentStateInfoVO searchVO
			                                  , HttpServletRequest request
                                              , BindingResult bindingResult) throws Exception {
		 
		 
		 ModelAndView model = new 	ModelAndView("jsonView");
		 try{
			 
			 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		     if(!isAuthenticated) {
		    	    model.addObject(Globals.STATUS, "LOGIN FAIL");
		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		return model;
		     }
		     LOGGER.debug("vo:" + searchVO.getAgentCode());
			 String searchDay = searchVO.getSearchDay() != "" ? searchVO.getSearchDay() : formtoDay.format(new Date());
			 searchVO.setSearchDay(searchDay);
			 searchVO.setSeachAgentCode(searchVO.getAgentCode());
		     List<AgentStateInfoVO>  items=  stateService.selectAgentStateOnyDayChart(searchVO);
			 String str ="[";
			 int num =0;
			 for(AgentStateInfoVO  dto : items){
					str +="[\""+dto.getAgentTime()+"\","+ dto.getAgentErrCnt()+"]";
					num ++;
					if(num<items.size()){
						str +=",";
					}		
			 }
			 str += "]";
			 LOGGER.debug("str:" + str);
			 
			 model.addObject("agentState", str);
			 model.addObject("status", "SUCCESS");
		     
		}catch (NullPointerException e){
  	    	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
  	    }catch(Exception e){
			 model.addObject(Globals.STATUS, "FAIL");
			 model.addObject(Globals.STATUS_MESSAGE, "ERROR:" + e.toString());
		}
		
	     //model.addObject("agentState", stateService.selectAgentStateDay(searchVO));
		 return model;
	 }
	 
	 @RequestMapping(value="agentState.do")
	 public ModelAndView selectAgentStateInfoPage( HttpServletRequest request) throws Exception{
		 
		 ModelAndView model = new ModelAndView("/backoffice/popup/agentStateChart");
		 
		 
		 AgentStateInfoVO searchInfo = new AgentStateInfoVO();
		 String agentCode = request.getParameter("agentCode") != null ? request.getParameter("agentCode") : "";
		 String searchDay = request.getParameter("searchDay") != null ? request.getParameter("searchDay") : formtoDay.format(new Date());
		 
		 searchInfo.setSeachAgentCode(agentCode);
		 searchInfo.setSearchDay(searchDay);
		 
		 try{
			    
				List<AgentStateInfoVO>  items=  stateService.selectAgentStateOnyDayChart(searchInfo);
				
				String str ="[";
				int num =0;
				for(AgentStateInfoVO  dto : items){
					str +="['";
					str += dto.getAgentTime();
					str +="' , ";
					str += dto.getAgentErrCnt();
					str +=" ]";
					num ++;
					if(num<items.size()){
						str +=",";
					}		
				}
				str += "]";
				model.addObject("agentState", str);
				
			}catch (NullPointerException e){
				LOGGER.error("NullPointerException ERROR:" + e.toString() );
	  	    }catch(Exception e){
				LOGGER.error("selectagentStateOnDayAjax ERROR:" + e.toString() );
				
			}
			return model;
	 }
	 
}
