package com.display.backoffice.sym.state.web;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.AdminLoginVO;

import com.display.backoffice.sts.report.web.ReportPageInfoManageController;
import com.display.backoffice.sym.state.models.AgentStateInfoVO;
import com.display.backoffice.sym.state.service.AgentstateInfoManageServie;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.UtilInfoService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = {"에이전트 정보 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/basicManage/agent")
public class AgentStateManageController {

		
		
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	 @Resource(name = "propertiesService")
     protected EgovPropertyService propertiesService;
	 
	 
	 @Resource(name="UniUtilManageService")
	 private UniUtilManageService utilService;
	 
	 @Resource(name="AgentstateInfoManageServie")
	 private AgentstateInfoManageServie stateService;
	 
	 SimpleDateFormat  formtoDay = new SimpleDateFormat("yyyyMMdd");
	 
	 
	 /** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	 
	 
	@PostMapping(value="agentStateAjax.do")
	public ModelAndView selectagentStateAjax(@RequestBody AgentStateInfoVO searchVO
			                                  , HttpServletRequest request
                                              , BindingResult bindingResult) throws Exception {
		 
		 
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			 
			 if (!jwtVerification.isVerification(request)) {
	        		ResultVO resultVO = new ResultVO();
	    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
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
			log.debug("str:" + str);
			
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
	 
	 @GetMapping(value="agentState.do")
	 public ModelAndView selectAgentStateInfoPage(@RequestParam Map<String, Object> commandMap,
			 									 HttpServletRequest request) throws Exception{
		 
		 ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		 
		 
		 AgentStateInfoVO searchInfo = new AgentStateInfoVO();
		 
		 
		 searchInfo.setSeachAgentCode(UtilInfoService.NVLObj(commandMap.get("agentCode"), "")   );
		 searchInfo.setSearchDay(UtilInfoService.NVLObj(commandMap.get("searchDay"), formtoDay.format(new Date())));
		 
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
				log.error("NullPointerException ERROR:" + e.toString() );
	  	    }catch(Exception e){
				log.error("selectagentStateOnDayAjax ERROR:" + e.toString() );
				
			}
			return model;
	 }
	 
}
