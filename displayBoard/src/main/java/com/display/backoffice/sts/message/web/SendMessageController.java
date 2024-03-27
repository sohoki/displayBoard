package com.display.backoffice.sts.message.web;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.display.backoffice.sts.cnt.web.FileUpladController;
import com.display.backoffice.sts.message.models.SendMessageInfo;
import com.display.backoffice.sts.message.models.SendMessageInfoVO;
import com.display.backoffice.sts.message.service.SendMessageInfoManageService;
import com.display.backoffice.sts.xml.models.SendMsgInfoVO;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.UtilInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;



@Api(tags = {"파일 업로드 연동 API"})
@Slf4j
@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성하여, 빈을 생성자로 주입받게 한다.
@RestController
@RequestMapping("/api/backoffice/sys/displayMessage")
public class SendMessageController {

	
	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
	
	@Autowired
	private SendMessageInfoManageService sendService;
	
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	 
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	@Autowired
	private UniUtilManageService utilService;
	 
	@ApiOperation(value="에이전트 메세지 스케줄 리스트", notes = "성공시 에이전트 메세지 스케줄를 반환 합니다.")
	@PostMapping("schAgentList.do")
	public ModelAndView selectSchAgentCode(@RequestBody SendMessageInfoVO searchVO,
											HttpServletRequest request) throws Exception {
		 
		 
			ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
			try{
				if (!jwtVerification.isVerification(request)) {
		    		ResultVO resultVO = new ResultVO();
					return jwtVerification.handleAuthError(resultVO); // 토큰 확인
		    	}else {
		    		String[] userInfo = jwtVerification.getTokenUserInfo(request);
		    		searchVO.setAdminLevel(userInfo[2]);
		    		searchVO.setPartId(userInfo[3]);
		    	}
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.JSON_RETURN_RESULT_LIST, sendService.selectSendMessageAgentList(searchVO));
			}catch(NullPointerException e){
				log.debug("selectGroupInfoList error:" + e.toString());
				model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			} 	catch(Exception e){
				log.debug("selectGroupInfoList error:" + e.toString());
				model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			} 	
			return model;
	}
	
	@ApiOperation(value="에이전트 메세지 업데이트", notes = "성공시 에이전트 메세지 업데이트를 반환 합니다.")
	@GetMapping("schAgentUpdate.do")
	public ModelAndView updateSchAgentCode (@RequestParam Map<String, Object> amqp, 
			 								HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try{
			String schCode = UtilInfoService.NVLObj( amqp.get("schCode"), "");
			String agentCodes = UtilInfoService.NVLObj( amqp.get("agentCodes"), "");
			List<String> agentCodeLst =  Arrays.asList(agentCodes.split("\\s*,\\s*"));
			 
			 
			 
			SendMessageInfoVO vo = new SendMessageInfoVO();
			vo.setSchCode(schCode);
			vo.setAgentCodeLst(agentCodeLst);
			int ret = sendService.insertSendMessage(vo);
			
			String status = ret > 0 ?
			 			Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
			String message = status.equals( Globals.STATUS_SUCCESS) ?
					 	egovMessageSource.getMessage("success.request.msg") :
						egovMessageSource.getMessage("fail.request.msg") ;
			model.addObject(Globals.STATUS, status);
	   		model.addObject(Globals.STATUS_MESSAGE, message);
		}catch(NullPointerException e){
			log.debug("selectGroupInfoList error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));	
		}catch(Exception e){
			log.error("updateCmmnDetailCode error:" + e.toString());
  	    	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));	
		}	
		return model;
	}
	@PostMapping ("pop_msgLst.do")
	public ModelAndView selectPopMsgResult(@RequestBody SendMessageInfoVO searchVO, 
											HttpServletRequest request, 
											BindingResult bindingResult) throws Exception {
			
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}  
	  		
			
    		
	  		searchVO.setPageUnit(pageUnitSetting); 
	  		searchVO.setPageSize(pageSizeSetting);
	  	    
	  	    
	  	    /** pageing */       
	  	   	PaginationInfo paginationInfo = new PaginationInfo();
	  		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
	  		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
	  		paginationInfo.setPageSize(searchVO.getPageSize());

	  		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	  		searchVO.setLastIndex(paginationInfo.getFirstRecordIndex() + searchVO.getPageSize());
	  		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	  		List<SendMessageInfoVO> list = sendService.selectSendMessageAgentHistoryList(searchVO);
	  	   
	  	    int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
	  	    
	  		paginationInfo.setTotalRecordCount(totCnt);
	  		model.addObject(Globals.JSON_RETURN_RESULT_LIST, list);
	  	    model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
	  	    model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
	  	    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
	      
		}catch(NullPointerException e){
			log.debug("selectPopMsgResult error: " + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg")+ e.toString());	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.debug("selectPopMsgResult error: " + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg")+ e.toString());	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}   
		return model;
	}
}
