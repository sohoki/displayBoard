package com.display.backoffice.sym.monter.web;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import egovframework.com.cmm.AdminLoginVO;

import com.display.backoffice.bas.cnt.web.CenterInfoManageController;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;
import com.display.backoffice.sts.report.models.ReportPageInfo;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;
import com.display.backoffice.sts.report.service.ReportPageInfoManageService;
import com.display.backoffice.sym.agent.service.AgentInfoManageService;
import com.display.backoffice.sym.agent.models.AgentInfoVO;
import com.display.backoffice.sym.agent.web.AgentInfoMangeController;
import com.display.backoffice.sym.monter.models.DispalyPageInfo;
import com.display.backoffice.sym.monter.models.DispalyPageInfoVO;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;
import com.display.backoffice.sym.monter.models.DetailPageInfo;
import com.display.backoffice.sym.monter.models.DetailPageInfoVO;
import com.display.backoffice.sym.monter.service.DetailPageInfoManageService;


import com.display.backoffice.sym.rnt.service.AuthorInfoManageService;
import com.display.backoffice.uat.uia.service.PartInfoManageService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.tags.Param;
import org.springmodules.validation.commons.DefaultBeanValidator;


@Api(tags = {"화면 구성 관리 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/content/display")
public class DisplayPageInfoManageController {

	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
    protected AgentInfoManageService agentService;
	
	@Autowired
	private EgovCcmCmmnDetailCodeManageService cmmnDetailService;
	
	@Autowired
	private DetailPageInfoManageService detailService;
	
	
	
	@Autowired
    protected DisplayPageInfoManageService displayService;
	
	@Autowired
	private ReportPageInfoManageService reportService;
	
	@Autowired
	private PartInfoManageService partService;
	
	@Autowired
	private AuthorInfoManageService authorInfoManageService;
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@PostMapping(value="displayList.do")
	public ModelAndView  selectDisplayInfoManageListByPagination( @ModelAttribute("searchVO") DispalyPageInfoVO searchVO
														, HttpServletRequest request
														, BindingResult bindingResult) throws Exception {
		 
		 //ModelAndView  model = new  ModelAndView("/backoffice/contentManage/displayList");
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		if (!jwtVerification.isVerification(request)) {
     		ResultVO resultVO = new ResultVO();
 			return jwtVerification.handleAuthError(resultVO); // 토큰 확
     	}else {
     		//loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
     		//searchVO.setAdminLevel(loginVO.getAdminLevel());
     		//searchVO.setPartId(loginVO.getPartId());
     	}
		
		if(  searchVO.getPageUnit() > 0  ){    	   
	    	   searchVO.setPageUnit(searchVO.getPageUnit());
		}else {
			   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
		}
		searchVO.setPageSize(propertiesService.getInt("pageSize"));
	       
		 
	       
	   	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		searchVO.setDisplayGubun("DispalyGubun_2");
		
		List<DispalyPageInfoVO> displayList = displayService.selectDisplayPageInfoManageListByPagination(searchVO);
	    
	    int totCnt = displayList.size() > 0 ?   displayList.get(0).getTotalRecordCount() : 0; 
      
		paginationInfo.setTotalRecordCount(totCnt);
		model.addObject(Globals.JSON_RETURN_RESULT_LIST, displayList );
	    model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
	    model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
	    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
	    
	    model.addObject(Globals.STATUS_REGINFO, searchVO);
	    model.addObject(Globals.STATUS_DISPLAYCOMBO, cmmnDetailService.selectCmmnDetailCombo("DispalyGubun"));
	     // 콘텐츠 관련 내용도 미리 넣기 
	    model.addObject(Globals.STATUS_DISPLAYCOMBO_NEXT, displayService.selectDisplayPageInfoCombo(searchVO));
	    model.addObject("selectState", authorInfoManageService.selectAuthorIInfoManageCombo());		
		model.addObject("selectGroup", partService.selectPartInfoCombo(null));
		//model.setViewName("/backoffice/contentManage/displayList");
		 
		 
		return model;	
	}
    
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@PostMapping (value="displayPageList.do")
	public ModelAndView  selectDisplayInfoManageListPagination(@ModelAttribute("searchVO") DispalyPageInfoVO searchVO
																, HttpServletRequest request
																, BindingResult bindingResult) throws Exception {
			
			ModelAndView  model = new  ModelAndView(Globals.JSON_VIEW);
			
			if (!jwtVerification.isVerification(request)) {
	     		ResultVO resultVO = new ResultVO();
	 			return jwtVerification.handleAuthError(resultVO); // 토큰 확
	     	}else {
	     		//loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
	     		//searchVO.setAdminLevel(loginVO.getAdminLevel());
	     		//searchVO.setPartId(loginVO.getPartId());
	     	}
			
			if(  searchVO.getPageUnit() > 0  ){    	   
		    	   searchVO.setPageUnit(searchVO.getPageUnit());
			}else {
				   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			}
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
		       
			 
		       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			searchVO.setDisplayGubun("DispalyGubun_2");
			
			List<DispalyPageInfoVO> displayList = displayService.selectDisplayPageInfoManageListByPagination(searchVO);
		    
		    int totCnt = displayList.size() > 0 ?   displayList.get(0).getTotalRecordCount() : 0; 
	      
			paginationInfo.setTotalRecordCount(totCnt);
			model.addObject(Globals.JSON_RETURN_RESULT_LIST, displayList );
		    model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
		    model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
		    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    
			return model;	
	}
    
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@PostMapping (value="displayDetail.do")
	public ModelAndView selecDispalyInfoManageDetail(@RequestBody  DispalyPageInfoVO vo
													, HttpServletRequest request
													, BindingResult bindingResult ) throws Exception{	
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		model.addObject(Globals.STATUS_REGINFO, vo);
		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
	    model.addObject("display", displayService.selectDisplayPageInfoManageDetail(vo.getDisplaySeq()));	
		return model;
	}
	
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
    @PostMapping(value="detailUpdate.do")
	public ModelAndView detailUpdate(@RequestBody DetailPageInfo vo,
									HttpServletRequest request) throws Exception {
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		vo.setUserId(jwtVerification.getTokenUserName(request));
	    	}
			
			
			
			int ret = detailService.insertDetailPageInfoManage(vo);
			if (ret > 0){
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("sucess.common.insert"));
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			}else {
				throw new Exception(); 
			}
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("sucess.common.insert"));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		
		}catch(NullPointerException e){
			log.debug("detailUpdate error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.debug("detailUpdate error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;	
	}
	
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@GetMapping(value="contentPreview/{displaySeq}.do")
	public String selectContentPreview(@PathVariable String displaySeq,
									  HttpServletRequest request) throws Exception{
		String jsonTest = "";
		try{	
			
			jsonTest = detailService.returnHtmlPage(displaySeq, "S");
			boolean fileCheck = detailService.ContentFileCreate(jsonTest, displaySeq, "S");
			if (fileCheck == true){
				log.debug("fileCheck:true" );
				detailService.ContentFileCreate(detailService.returnHtmlPage(displaySeq, "L"), displaySeq, "L");
			}			
		}catch(NullPointerException e){
			log.debug("selectContentPreview error" + e.toString());
		}catch(Exception e){
			log.debug("selectContentPreview error" + e.toString());
		}		
		return jsonTest;
	}
	
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@PostMapping(value="disPlayUpdate.do")
	public ModelAndView displayUpdare(@RequestBody DispalyPageInfo vo,
									HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			
			int ret = displayService.updateDisplayReSendManage(vo.getDisplaySeq());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(NullPointerException e){
			log.debug("updateSisplayUpdateChange Error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			
		}catch(Exception e){
			log.debug("updateSisplayUpdateChange Error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			
		}
		return model;
		
	}
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@GetMapping(value="disPreview.do")
	public ModelAndView  selectDisPreview(HttpServletRequest request,
										  @RequestParam Map<String, String> params) throws Exception {
		
		//ModelAndView model = new ModelAndView("/backoffice/contentManage/disPreview");
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확
    	}
    	
		String displaySeq =  request.getParameter("displaySeq") != null ? request.getParameter("displaySeq") : "";
		String detailSeq_N =  request.getParameter("detailSeq_N") != null ? request.getParameter("detailSeq_N") : "";
		
		
		DispalyPageInfoVO  vo = new DispalyPageInfoVO();
		vo.setDisplaySeq(displaySeq);
		vo.setDetailSeq_N(detailSeq_N);
		DetailPageInfoVO detailVO = new DetailPageInfoVO();
		detailVO.setDisplaySeq(displaySeq);	
		detailVO.setReplacePath(propertiesService.getString("Globals.fileStorePathReplace"));
		model.addObject(Globals.STATUS_REGINFO, vo);
		List<DetailPageInfoVO> detailList = detailService.selectDisPlayPreviewList(detailVO);
		
		int totalCnt = detailList.size() > 0 ?   detailList.get(0).getTotalRecordCount() : 0;  
		model.addObject(Globals.JSON_RETURN_RESULT_LIST,   detailList );
		model.addObject(Globals.PAGE_TOTAL_COUNT, totalCnt);
		model.addObject("detailSeq", detailList.get(0).getDetailSeq());
		return model;
	}
	
	
	
	@DeleteMapping(value="detailDelete.do")
	public ModelAndView detailDelete(@RequestBody  DetailPageInfo vo,
									HttpServletRequest request		) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			
			detailService.deleteDetailPageInfoManage(vo);
			model.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("success.common.delete"));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(NullPointerException e){
			model.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("success.common.delete"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("success.common.delete"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
			
	}
	@PostMapping(value="detailTimeUpdate.do")
	public ModelAndView detailTimeChange(@RequestBody DetailPageInfo vo,
                                         HttpServletRequest request		) throws Exception {
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		try{
			
			
		    if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
		    
		    
			int ret = detailService.updateDetailTimeChangeManage(vo);			
			
			if (ret > 0){
				DispalyPageInfoVO disInfo =  displayService.selectDisplayPageInfoManageView(vo.getDisplaySeq() );
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("sucess.common.changeTime"));
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject("totalTime", disInfo.getDisplayTotalTime());
			}else{
				throw new Exception();
			}
		}catch(NullPointerException e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.changeTime"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.changeTime"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;	
	}
	@PostMapping (value="detailOrderUpdatePage.do")
	public ModelAndView detailOrderUpdatePage (@RequestBody List<DetailPageInfo> detailLists,	
												HttpServletRequest request	) throws Exception {
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			
			int ret = detailService.updateDitailOrderUpdatePage(detailLists);
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);			
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("sucess.common.update"));
		}catch (NullPointerException e){
			log.error("updateDitailOrderUpdatePage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
		}catch (Exception e){
			log.error("updateDitailOrderUpdatePage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
		}
		return model;
		
		
		
	}
	@PostMapping (value="detailOrder.do")
	public ModelAndView detailOrderChange (@RequestBody DetailPageInfoVO vo, 
											HttpServletRequest request) throws Exception {
		
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		
		try{
			
			
			
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			
			int ret = detailService.updateDetailSortOrderInfoManage(vo);
		    if (ret > 0){
		    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));	
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    	
		    }else {
		    	throw new Exception();
		    }
		}catch(NullPointerException e){
			log.error("--------------------------------------------------------detailUpdate error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.error("--------------------------------------------------------detailUpdate error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;	
	}	
	
	@RequestMapping (value="detailTimeInfo.do")
	public ModelAndView selectDetailTimePage (@ModelAttribute("loginVO") AdminLoginVO loginVO,
			                                  @RequestBody DetailPageInfo vo		) throws Exception {
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		try{
		    
			DetailPageInfoVO detailPageInfo = (DetailPageInfoVO) detailService.selectDetailInfo(vo.getDetailSeq());        
			model.addObject("detailInfo", detailPageInfo); 
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);	
		}catch(NullPointerException e){
			log.error("selectDetailTimePage error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.changeTime"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);	
		    //System.out.println("error:" + e.toString());
		}catch(Exception e){
			log.error("selectDetailTimePage error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.changeTime"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);	
		    //System.out.println("error:" + e.toString());
		}
		return model;
	}
	
	
	
	@RequestMapping (value="displayDetailInfo/{displaySeq}.do")
	public ModelAndView selectDetailPage (@PathVariable String displaySeq,
											HttpServletRequest request		) throws Exception {
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		
		try{
			
		     DetailPageInfoVO searchVO = new DetailPageInfoVO();
		     searchVO.setDisplaySeq(displaySeq);
		     searchVO.setReplacePath(propertiesService.getString("Globals.fileStorePathReplace"));
		     model = detailService.selectDetailPageInfoManageListByPagination(displaySeq); 
			
		}catch(NullPointerException e){
			 model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			 model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			 model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			 model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	@RequestMapping(value="displayCombo/{agentContentgubun}.do")
	public ModelAndView selectDisplayCombo ( HttpServletRequest request
		                            		, BindingResult bindingResult ) throws Exception{	
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		
		String agentContentgubun = request.getParameter("agentContentgubun") != null ? request.getParameter("agentContentgubun") : "";
		DispalyPageInfoVO searchVO = new DispalyPageInfoVO();
		
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		//loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
	    		//searchVO.setAdminLevel(loginVO.getAdminLevel());
	    		//searchVO.setPartId(loginVO.getPartId());
	    	}
			
		     
			//여기 부분 나중에 수정 
		    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    if (!agentContentgubun.equals("AGENT_CONTENT_1")){
		    	searchVO.setDisplayGubun("DispalyGubun_2");
		    }
		   
		    model.addObject("disList", displayService.selectDisplayPageInfoCombo(searchVO));
		}catch(NullPointerException e){
			log.debug("selecEqupInfoManageView :" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.debug("selecEqupInfoManageView :" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	//신규 작업
	@PostMapping (value="displayView.do")
	public ModelAndView selecEqupInfoManageView(@RequestBody  DispalyPageInfoVO vo
		                                          , HttpServletRequest request
		                            			  , BindingResult bindingResult ) throws Exception{	
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		
		
	    
		try{

		    if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		//여기 부분 수정 
	    		String[] userInfo = jwtVerification.getTokenUserInfo(request);
	    		vo.setAdminLevel(userInfo[2]);
	    		vo.setPartId(userInfo[3]);
				
	    	}
			DispalyPageInfoVO disInfo = displayService.selectDisplayPageInfoManageView(vo.getDisplaySeq()); 
			model.addObject(Globals.STATUS_REGINFO , disInfo);
			
			
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(NullPointerException e){
			log.debug("selecEqupInfoManageView :" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL );
		}catch(Exception e){
			log.debug("selecEqupInfoManageView :" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL );
		}
		return model;
	}
	@GetMapping (value="ajaxDisplayView.do")
	public ModelAndView selectajaxDisplayView(@RequestParam Map<String, Object> reportMap
												, HttpServletRequest request
												, BindingResult bindingResult ) throws Exception{	


			ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
			ReportPageInfoVO searchVO = new ReportPageInfoVO();
			
			
			
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		//여기 부분 수정 
        		String[] userInfo = jwtVerification.getTokenUserInfo(request);
        		searchVO.setAdminLevel(userInfo[2]);
				searchVO.setPartId(userInfo[3]);
				
        	}
			
			
			//여기 부분 수정 
			searchVO.setPageIndex( Integer.valueOf(  reportMap.get("pageIndex").toString() ) );
			searchVO.setReportUseYn("Y");
			if (!reportMap.get("searchKeyword").equals("") ){
				searchVO.setSearchCondition(reportMap.get("searchCondition").toString());
				searchVO.setSearchKeyword(reportMap.get("searchKeyword").toString());
			}
            String displayGubun = reportMap.get("displayGubun") != null ? request.getParameter("displayGubun") : ""; 
			
            //model = reportService.selectReportPageInfoManageListByPaginationAjax(searchVO, displayGubun);
			
            return model;
	}
	//여기 구문 찾기 
	@DeleteMapping (value="displayDelete/{displaySeq}.do")
	public ModelAndView deleteEqupInfoManage(@PathVariable String displaySeq,
											 HttpServletRequest request	) throws Exception {
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		try{
			
			
		    //연동 단말기 확인 후 업으면 삭제 
		    model = displayService.deleteDisplayPageInfoManage(displaySeq);
		    
		} catch (NullPointerException e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		} catch (Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}	
		model.setViewName(Globals.JSON_VIEW); 
		return model;
	}
	
	
	@PostMapping (value="displayBasicUpdate.do")
	public ModelAndView updateequpInfoManage(@RequestBody DispalyPageInfoVO vo	
                                             , HttpServletRequest request                         				 
										     , BindingResult result) throws Exception{
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		//여기 부분도 수정 해야함 
		
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		vo.setUserId(jwtVerification.getTokenUserName(request));
	    	}
			int ret  = displayService.updateDisplayPageInfoManage(vo);
			
			if (ret >0){
				String message = vo.getMode().equals("Ins") ? egovMessageSource.getMessage("sucess.common.insert") : egovMessageSource.getMessage("sucess.common.update");
				if (vo.getMode().equals("Ins")){
					String displaySeq = displayService.selectDisplayMaxSeq();
					vo.setDisplaySeq(displaySeq);
					model.addObject(Globals.STATUS_REGINFO, vo);
				}else {
					model.addObject(Globals.STATUS_REGINFO, displayService.selectDisplayPageInfoManageView(vo.getDisplaySeq()));
				}	
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_MESSAGE, message);
			}else {
				throw new Exception();
			}
			
		}catch (Exception e){
			log.error("updateequpInfoManage ERROR:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));			
		}		
		return model;	
	}
}
