package com.display.backoffice.sts.sch.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.sts.sch.service.ContentScheduleManagerService;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.util.service.UtilInfoService;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import com.display.backoffice.sts.sch.service.AgentGroupInfoManagerService;
import com.display.backoffice.sts.sch.models.AgentGroupInfoVO;
import com.display.backoffice.sts.sch.models.ContentScheduleInfo;
import com.display.backoffice.sts.sch.models.ContentScheduleInfoVO;

@Api(tags = {"콘텐츠 스케줄 관리 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/contentManage/contentSchedule")
public class ContentScheduleManagerController {
	
	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
    
    
    
    @Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
	private ContentScheduleManagerService contSchedule;
	
	@Autowired
    protected DisplayPageInfoManageService displayService;	
	
	@Autowired
    protected AgentGroupInfoManagerService agentGroup;
	
	@Autowired
	private UniUtilManageService utilService;
	
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	@PostMapping(value="conSchInfoList.do")
	public ModelAndView selectConSchInfoListInfo(@RequestBody ContentScheduleInfoVO searchVO
				                                 , HttpServletRequest request) throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try{
			
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		//여기 부분 수정 
        		String[] userInfo = jwtVerification.getTokenUserInfo(request);
        		searchVO.setAdminLevel(userInfo[2]);
        		searchVO.setPartId(userInfo[2]);
        		searchVO.setDisplayGubun("DispalyGubun_2");
        	}
			
			
			searchVO.setPageUnit(UtilInfoService.NVL(searchVO.getPageUnit(), pageUnitSetting) );
			searchVO.setPageSize(UtilInfoService.NVL(searchVO.getPageSize(), pageSizeSetting) );
			searchVO.setPageIndex(UtilInfoService.NVL(searchVO.getPageIndex(), 1) );
	    	
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			List<ContentScheduleInfoVO> conschList = contSchedule.selectContentSchduleInfoManageListByPagination(searchVO); 
			model.addObject(Globals.JSON_RETURN_RESULT_LIST,   conschList);
		    
		    int totCnt =  conschList.size() > 0 ? conschList.get(0).getTotalRecordCount() : 0;       
			paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject(Globals.PAGE_INFO, paginationInfo);
		    model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
		    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    model.addObject(Globals.STATUS_REGINFO, searchVO);
		    //부서, 전광판화면, 콘텐츠 구분 
			
		}catch (NullPointerException e){
			log.error("selectContentSchList ERROR: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch (Exception e){
			log.error("selectContentSchList ERROR: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
	    return model;
	}
	
	@PostMapping(value="conPopSchView.do")
	public ModelAndView selectContentSchList(@RequestBody ContentScheduleInfoVO searchVO
											, HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			
		    if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
		    
		   
		    String displaySeq = request.getParameter("displaySeq") != null ? request.getParameter("displaySeq") : "";	
		    if (!displaySeq.equals(""))
		    	searchVO.setDisplaySeq(displaySeq);
		    model.addObject(Globals.JSON_RETURN_RESULT_LIST, contSchedule.selectContentSchduleInfoManageListByPagination(searchVO));
		    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    
		    
		}catch (NullPointerException e){
			log.error("selectContentSchList ERROR: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
			model.addObject(Globals.STATUS,  Globals.STATUS_FAIL);
		}catch (Exception e){
			log.error("selectContentSchList ERROR: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
			model.addObject(Globals.STATUS,  Globals.STATUS_FAIL);
		}
	    return model;
	}
	@PostMapping(value="conSchInfoView.do")
	public ModelAndView selectConSchInfoView(@RequestBody ContentScheduleInfo vo
			                                 , HttpServletRequest request) throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			model.addObject("conSchInfo", contSchedule.selectConetntSchduleInfoManageView(vo.getConschCode()));
		}catch(NullPointerException e){
			log.debug("selectConSchInfoView error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));		
		}catch(Exception e){
			log.debug("selectConSchInfoView error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));		
		}
		return model;
	}
	@PostMapping (value="scheduleReset.do")		
	public ModelAndView scheduleReset(@RequestBody AgentGroupInfoVO vo
									 , HttpServletRequest request) throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
			int ret = agentGroup.updateAgentResetUpdateContent(vo.getConschCode());
			
			if (ret > 0){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.update"));
			}else {
				model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.content"));	
			}
		}catch(NullPointerException e){
			log.debug("selectGroupInfoList error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		} catch(Exception e){
			log.debug("selectGroupInfoList error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		} 	
		return model;
	}
	
	@PostMapping ("agentContentList.do")
	public ModelAndView selectagentContentList(@RequestBody AgentGroupInfoVO searchVO
											, HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		  //공용 확인 하기 
        
        try{
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
        	searchVO.setPageUnit(UtilInfoService.NVL(searchVO.getPageUnit(), pageUnitSetting) );
			searchVO.setPageSize(UtilInfoService.NVL(searchVO.getPageSize(), pageSizeSetting) );
			
  	           
  	   		PaginationInfo paginationInfo = new PaginationInfo();
  	   		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
  	   		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
  	   		paginationInfo.setPageSize(searchVO.getPageSize());

  	   		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
  	   		searchVO.setLastIndex(paginationInfo.getFirstRecordIndex() + searchVO.getPageSize());
  	   		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

  	   		List<AgentGroupInfoVO> list = agentGroup.selectAgentContentListInfo(searchVO);
  	   		
  	   		int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
  	      
  	   		paginationInfo.setTotalRecordCount(totCnt);
  	   		model.addObject(Globals.JSON_RETURN_RESULT_LIST, list);
  	   		model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
  	   		model.addObject(Globals.PAGE_INFO, paginationInfo);
  	   		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
  	      
        }catch(NullPointerException e){
        	log.debug("selectagentContentList error: " + e.toString());
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
        }catch(Exception e){
        	log.debug("selectagentContentList error: " + e.toString());
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
        }
	      
	    return model;
		
	}
	
	@RequestMapping (value="conSchDeleteInfo.do")
	public ModelAndView deleteSchInfoManage(@RequestBody ContentScheduleInfo vo
											, HttpServletRequest request) throws Exception{
		
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		
		UniUtilInfo utilInfo = new UniUtilInfo();
		utilInfo.setInTable("TB_CONTENTSCHEDULE");
		utilInfo.setInCondition("CONSCH_CODE=["+vo.getConschCode()+"[");
		try{
			
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
			int ret = 	utilService.deleteUniStatement(utilInfo);	
		    if (ret > 0 ) {		    	  
		    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
		    	model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    }else {
		    	throw new Exception();		    	  
		    }
		}catch (NullPointerException e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
	    	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch (Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
	    	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	@PostMapping (value="agentGroupInfo.do")		
	public ModelAndView selectGroupInfoList(@RequestBody AgentGroupInfoVO searchVO
					                        , HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		//여기 부분 수정 
        		String[] userInfo = jwtVerification.getTokenUserInfo(request);
        		searchVO.setAdminLevel(userInfo[2]);
        		searchVO.setPartId(userInfo[2]);
        	}
			searchVO.setPageUnit(UtilInfoService.NVL(searchVO.getPageUnit(), pageUnitSetting) );
			searchVO.setPageSize(UtilInfoService.NVL(searchVO.getPageSize(), pageSizeSetting) );
			
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			List<AgentGroupInfoVO> leftAgentGroup = agentGroup.selectAgentGroupInfoListByPagination(searchVO);
			
		    int totCnt =  leftAgentGroup.size() > 0 ? leftAgentGroup.get(0).getTotalRecordCount() : 0;  
			paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject(Globals.PAGE_INFO, paginationInfo);
		    model.addObject(Globals.JSON_RETURN_RESULT_LIST,   leftAgentGroup);
		    leftAgentGroup = null;
			model.addObject("resultRight",   agentGroup.selectAgentGroupInfoRightListByPagination(searchVO));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(NullPointerException e){
			log.debug("selectGroupInfoList error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		} catch(Exception e){
			log.debug("selectGroupInfoList error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		} 	
		return model;
	}
	
	@PostMapping (value="agentUpdateInfo.do")		
	public ModelAndView agentUpdateInfo(@RequestBody AgentGroupInfoVO searchVO
							            , HttpServletRequest request) throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		//여기 부분 수정 
        		String[] userInfo = jwtVerification.getTokenUserInfo(request);
        		searchVO.setAdminLevel(userInfo[2]);
        		searchVO.setPartId(userInfo[2]);
        	}
			
			String returnTxt  = agentGroup.changedeleteAgentGroupInfo(searchVO);
			log.debug("returnTxt:" + returnTxt);
			
			if (!returnTxt.equals(Globals.STATUS_FAIL) &&  !returnTxt.equals(Globals.STATUS_UNIQUE)){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			}else {
				model.addObject(Globals.STATUS, returnTxt);
				String message = returnTxt.equals(Globals.STATUS_UNIQUE) ? "fail.request.notUnique" : "fail.request.msg" ; 
				model.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage(message));	
			}
		}catch(NullPointerException e){
			log.debug("selectGroupInfoList error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		}catch(Exception e){
			log.debug("selectGroupInfoList error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		}  	
		return model;
		
	}
	
    @PostMapping (value="conSchInfoUpdate.do")
	public ModelAndView updateContentSchUpdateInfoManage( @ModelAttribute("loginVO") AdminLoginVO loginVO
                                                         , @RequestBody ContentScheduleInfo vo
				                                         , HttpServletRequest request                         				 
														 , BindingResult result) throws Exception{
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	model.addObject("regist", vo);
    	try{
    		if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		//여기 부분 수정 
        		String[] userInfo = jwtVerification.getTokenUserInfo(request);
        		vo.setUserId(userInfo[0]);
        		
        	}
			AdminLoginVO user = (AdminLoginVO) request.getSession().getAttribute("AdminLoginVO");	
			
			String meesage = vo.getMode().equals("Ins") ? "sucess.common.insert" : "sucess.common.update";
			int ret = contSchedule.updateContentSchduleInfoManage(vo);
			if (ret > 0){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));
			}else {
				throw new Exception();
			}
    	}catch (NullPointerException e){
    		log.debug("updateequpInfoManage error:" + e.toString());
    		String meesage = vo.getMode().equals("Ins") ? "fail.common.insert" : "fail.common.update";
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));		
    	}catch (Exception e){
    		log.debug("updateequpInfoManage error:" + e.toString());
    		String meesage = vo.getMode().equals("Ins") ? "fail.common.insert" : "fail.common.update";
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));		
    	}
    	return model;
    }
}

