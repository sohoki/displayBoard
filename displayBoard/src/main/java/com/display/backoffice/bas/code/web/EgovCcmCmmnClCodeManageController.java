package com.display.backoffice.bas.code.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.bas.code.models.CmmnClCode;
import com.display.backoffice.bas.code.service.EgovCcmCmmnClCodeManageService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.exception.NotFoundException;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Api(tags = {"공통분류코드 정보 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/sys/cmm/clc")
public class EgovCcmCmmnClCodeManageController {

	
	@Autowired
    private EgovCcmCmmnClCodeManageService cmmnClCodeManageService;
	
    @Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
	@Autowired
	EgovMessageSource egovMessageSource;
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
    
    
	@ApiOperation(value="삭제", notes = "성공시 대 분류 코드를 삭제 합니다.")
	@ApiImplicitParam(name = "clCode", value = "공통코드 CL_CODE")
    @DeleteMapping("{clCode}.do")
	public ModelAndView deleteCmmnClCode (@PathVariable String clCode, 
										  HttpServletRequest request) throws Exception {
    	 
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {

        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	
    		cmmnClCodeManageService.deleteCmmnClCode(clCode);
	    	int ret = cmmnClCodeManageService.deleteCmmnClCode(clCode);
	    	if (ret > 0) {
	    		String status = cmmnClCodeManageService.deleteCmmnClCode(clCode) > 0 ?
   			 		 Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
			   	String message = status.equals( Globals.STATUS_SUCCESS) ?
			   			 	 egovMessageSource.getMessage("success.common.delete") :
			   				 egovMessageSource.getMessage("fail.common.delete") ;
			   	model.addObject(Globals.STATUS, status);
			   	model.addObject(Globals.STATUS_MESSAGE, message);
	    	}else {
	    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
	    	}
	    	
    	}catch(Exception e) {
    		log.debug("deleteCmmnClCode error:" + e.toString());
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
	}

	/**
	 * 공통분류코드를 등록한다.
	 * @param loginVO
	 * @param cmmnClCode
	 * @param bindingResult
	 * @return "/cmm/sym/ccm/EgovCcmCmmnClCodeRegist"
	 * @throws Exception
	 */
	@ApiOperation(value="업데이트", notes = "성공시 공통분류코드 코드를 업데이트 합니다.")
    @PostMapping("code/update.do")
	public ModelAndView updateCmmnClCode(@RequestBody CmmnClCode clCode, 
			  							 HttpServletRequest request) throws Exception {
    	
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}else {
        		clCode.setUserId(jwtVerification.getTokenUserName(request));
        	}
    		String status = cmmnClCodeManageService.updateCmmnClCode(clCode) > 0 ?
			 		 Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
			String message = status.equals( Globals.STATUS_SUCCESS) ?
					 	 egovMessageSource.getMessage("success.request.msg") :
						 egovMessageSource.getMessage("fail.request.msg") ;
			 model.addObject(Globals.STATUS, status);
	   		 model.addObject(Globals.STATUS_MESSAGE, message);
    	}catch(Exception e) {
    		log.debug("updateCmmnClCode error:" + e.toString());
	   		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	   		model.addObject(Globals.STATUS_MESSAGE, e.toString());
	   	 }
	   	 return model;
    	
    	
    }

	/**
	 * 공통분류코드 상세항목을 조회한다.
	 * @param loginVO
	 * @param cmmnClCode
	 * @param model
	 * @return "cmm/sym/ccm/EgovCcmCmmnClCodeDetail"
	 * @throws Exception
	 */
	@ApiOperation(value="상세 조회", notes = "공통분류코드 상세항목을 조회한다.")
    @GetMapping("{clCode}.do")
	public ModelAndView selectCmmnClCodeDetail(@PathVariable String clCode, 
			  									HttpServletRequest request) throws Exception {
    	
		//공용 확인 하기 
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		if (!jwtVerification.isVerification(request)) {
    			ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	CmmnClCode detail = cmmnClCodeManageService.selectCmmnClCodeDetail(clCode).orElseThrow(() 
        			-> new NotFoundException(egovMessageSource.getMessage("fial.common.info")));
    			
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    		model.addObject(Globals.JSON_RETURN_RESULT, detail);
    	}catch (Exception e){
			log.debug("selectCmmnClCodeDetail error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg")+ e.toString());	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    }
	    
        return model;
    	// 기존 세션 체크 인증에서 토큰 방식으로 변경
	}

    /**
	 * 공통분류코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model
     * @return "/cmm/sym/ccm/EgovCcmCmmnClCodeList"
     * @throws Exception
     */
	@ApiOperation(value="목록 조회", notes = "공통분류코드 목록을 조회한다.")
    @PostMapping("code/list.do")
	public ModelAndView selectCmmnClCodeList(@RequestBody Map<String, Object> searchMap, 
											HttpServletRequest request) throws Exception {
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try
    	{
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	
        	
            int pageUnit = searchMap.get(Globals.PAGE_UNIT) == null ?   pageUnitSetting : Integer.valueOf((String) searchMap.get(Globals.PAGE_UNIT));
    		int pageSize = searchMap.get(Globals.PAGE_SIZE) == null ?   pageSizeSetting : Integer.valueOf((String) searchMap.get(Globals.PAGE_SIZE));  
    	   
    	    
        	/** pageing */
        	PaginationInfo paginationInfo = new PaginationInfo();
    		paginationInfo.setCurrentPageNo( Integer.valueOf( searchMap.get(Globals.PAGE_INDEX).toString() ));
    		paginationInfo.setRecordCountPerPage(pageUnit);
    		paginationInfo.setPageSize(pageSize);

    		searchMap.put(Globals.PAGE_FIRST_INDEX, paginationInfo.getFirstRecordIndex());
    		searchMap.put(Globals.PAGE_LAST_INDEX, paginationInfo.getLastRecordIndex());
    		searchMap.put(Globals.PAGE_RECORD_PER_PAGE, paginationInfo.getRecordCountPerPage());
    	    
    	    List<Map<String, Object>> codeList = (List<Map<String, Object>>) cmmnClCodeManageService.selectCmmnClCodeListByPagination(searchMap);
    	    int totCnt = codeList.size() > 0 ?  Integer.valueOf( codeList.get(0).get(Globals.PAGE_TOTAL_COUNT).toString().replace("-", "") ) :0;
            

    		paginationInfo.setTotalRecordCount(totCnt);
    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, codeList);
    		model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
    	}catch (Exception e){
    		log.debug("selectCmmnClCodeList error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg")+ e.toString());	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    }
	    
        return model;
	}

}
