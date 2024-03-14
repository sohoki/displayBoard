package com.display.backoffice.sts.xml.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.sts.xml.service.SendMsgInfoManageService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;
import com.display.backoffice.sts.xml.models.SendMsgInfoVO;


@Api(tags = {"단말기 전문 전송  API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/operManage")
public class SendMsgManageController {

	
	@Autowired
	protected EgovMessageSource egovMessageSource;
    
	@Autowired
    private EgovCcmCmmnDetailCodeManageService cmmnDetailCodeManageService;
    
    /** EgovPropertyService */
	@Autowired
    protected EgovPropertyService propertiesService;
    
    @Autowired
    private SendMsgInfoManageService sendService;
    
    /** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
    
    
    @Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
	
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@PostMapping ("popSendLst.do")
	public ModelAndView selectPopSendResult(@RequestBody SendMsgInfoVO searchVO
											, HttpServletRequest request
											, BindingResult bindingResult) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		  //공용 확인 하기 
        
        try{
        	
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		
        	}
			
			int pageUnit = searchVO.getPageUnit() > 0 ?   pageUnitSetting : searchVO.getPageUnit();
    		int pageSize = searchVO.getPageSize() > 0 ?   pageSizeSetting : searchVO.getPageSize();  
    	   
    		
    	    
        	/** pageing */
    		PaginationInfo paginationInfo = new PaginationInfo();
    		paginationInfo.setCurrentPageNo(  searchVO.getPageIndex() < 1  ?  1 : searchVO.getPageIndex());
    		paginationInfo.setRecordCountPerPage(pageUnit);
    		paginationInfo.setPageSize(pageSize);
    		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
    		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
    		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
    		
    	    List<SendMsgInfoVO> codeList = sendService.selectSendMsgInfoManageListByPagination(searchVO);
    	    int totCnt = codeList.size() > 0 ?   codeList.get(0).getTotalRecordCount() :0;
            

    		paginationInfo.setTotalRecordCount(totCnt);
    		
    		model.addObject(Globals.STATUS_REGINFO, searchVO);
    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, codeList);
    		model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
    		model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    		
    		
        }catch(NullPointerException e){
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE,"selectXmlLst error: " + e.toString());
        	log.debug("selectXmlLst error: " + e.toString());
        }catch(Exception e){
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE,"selectXmlLst error: " + e.toString());
        }
	      
	    return model;
		
	}
	@PostMapping ("sendResultList.do")
	public ModelAndView selectSendResult(@RequestBody SendMsgInfoVO searchVO
									 , HttpServletRequest request
									 , BindingResult bindingResult) throws Exception {
		//ModelAndView model = new ModelAndView("/backoffice/operManage/sendResultList");
		  //공용 확인 하기 
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
	    try{
	    	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		
        	}
			
			int pageUnit = searchVO.getPageUnit() > 0 ?   pageUnitSetting : searchVO.getPageUnit();
    		int pageSize = searchVO.getPageSize() > 0 ?   pageSizeSetting : searchVO.getPageSize();  
    	   
    		
    	    
        	/** pageing */
    		PaginationInfo paginationInfo = new PaginationInfo();
    		paginationInfo.setCurrentPageNo(  searchVO.getPageIndex() < 1  ?  1 : searchVO.getPageIndex());
    		paginationInfo.setRecordCountPerPage(pageUnit);
    		paginationInfo.setPageSize(pageSize);
    		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
    		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
    		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
    		
    	    List<SendMsgInfoVO> codeList = sendService.selectSendMsgInfoManageListByPagination(searchVO);
    	    int totCnt = codeList.size() > 0 ?   codeList.get(0).getTotalRecordCount() :0;
    	    
    	    
    	    
			model.addObject(Globals.STATUS_WORKGROUP, cmmnDetailCodeManageService.selectCmmnDetailCombo("WORKGUBUN"));
			model.addObject(Globals.JSON_RETURN_RESULT_LIST, codeList);
	    	model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
	    	model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
	    	model.addObject(Globals.STATUS_REGINFO, searchVO);
		      //model.setViewName("/backoffice/operManage/sendResultList");
	    }catch(NullPointerException e){
	    	log.debug("selectXmlLst error: " + e.toString());
	    	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	        model.addObject(Globals.STATUS_MESSAGE,"selectXmlLst error: " + e.toString());
	    } catch(Exception e){
	    	log.debug("selectXmlLst error: " + e.toString());
	    	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE,"selectXmlLst error: " + e.toString());
	    } 
	    return model;
	}
    
}
