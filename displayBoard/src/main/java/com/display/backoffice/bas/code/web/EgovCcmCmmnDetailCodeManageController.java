package com.display.backoffice.bas.code.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.bas.code.models.CmmnDetailCode;
import com.display.backoffice.bas.code.models.dto.CmmnDetailCodeDto;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Api(tags = {"공통상세코드 정보 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/sys/cmm/cde")
public class EgovCcmCmmnDetailCodeManageController {

	
	
    @Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
    @Autowired
    private EgovCcmCmmnDetailCodeManageService cmmnDetailCodeManageService;



	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;


    @ApiOperation(value="공통 상세 코드 삭제", notes = "성공시 공통 상세 코드를 삭제 합니다.")
	@ApiImplicitParam(name = "code", value = "공통상세코드 CODE")
    @DeleteMapping("{code}.do")
	public ModelAndView deleteCmmnDetailCode (@PathVariable String code, 
			                                  ModelMap modelMe,
			                                  HttpServletRequest request) throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try{
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {

        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
    		int ret = cmmnDetailCodeManageService.deleteCmmnDetailCode(code);
        	if (ret > 0) {
        		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
        		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
        	}
        	else {
        		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    	    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
        	}
        	
    		
    	}catch(Exception e){
    		log.error("deleteCmmnDetailCode error:" + e.toString());
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
	}

	/**
	* 공통상세코드 상세항목을 조회한다.
	* @param loginVO
	 * @param cmmnDetailCode
	 * @param model
	 * @return "cmm/sym/ccm/EgovCcmCmmnDetailCodeDetail"
	 * @throws Exception
	 */
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드를 조회 합니다.")
	@ApiImplicitParam(name = "code", value = "공통상세코드 CODE")
	@GetMapping("{code}.do")
 	public ModelAndView selectCmmnDetailCodeDetail (@PathVariable String code, 
 												HttpServletRequest request)	throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
        	model.addObject(Globals.JSON_RETURN_RESULT, cmmnDetailCodeManageService.selectCmmnDetailCodeDetail(code));
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
			log.error("selectCmmnDetailCodeDetail error:" + e.toString());
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
	}
    @ApiOperation(value="공통 상세 코드 combo list", notes = "성공시 공통 상세 코드를 combo list 합니다.")
	@ApiImplicitParam(name = "code", value = "공통코드 CODE")
	@GetMapping("combo/{codeId}.do")
 	public ModelAndView selectCmmnDetailComboList (@PathVariable String codeId, 
 												HttpServletRequest request)	throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
        	model.addObject(Globals.JSON_RETURN_RESULT, cmmnDetailCodeManageService.selectCmmnDetailComboLamp(codeId));
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
			log.error("selectCmmnDetailComboList error:" + e.toString());
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
	}
    /**
	 * 공통상세코드 목록을 조회한다.
     * @param loginVO
     * @param searchVO
     * @param model 
     * @throws Exception
     */
    @ApiOperation(value="공통 상세 코드 조회", notes = "성공시 공통 상세 코드 조회 합니다.")
    @PostMapping(value="detailList.do")
    public ModelAndView selectCmmnDetailCodeList ( @RequestBody Map<String, Object> searchMap, 
    		                                       HttpServletRequest request)  {
    	//나중에 권한 설정 값 넣기 
    	
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try{
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
        	
            List<CmmnDetailCodeDto> codeDetailList = cmmnDetailCodeManageService.selectCmmnDetailCodeList(searchMap.get("codeId").toString());
            int totCnt = codeDetailList.size() > 0 ? codeDetailList.size()  : 0;

    		model.addObject( Globals.STATUS_REGINFO, searchMap.get("codeId"));
    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, codeDetailList);
            model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
            
    	}catch(Exception e){
    		log.error("selectCmmnDetailCodeList error:" + e.toString());
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    		
    	}   
    	return model;
	}
    @ApiOperation(value="공통 상세 코드 업데이트", notes = "성공시 공통 상세 코드 업데이트 합니다.")
    @PostMapping(value="CodeDetailUpdate.do")
	public ModelAndView updateCmmnDetailCode ( @RequestBody CmmnDetailCode vo
			                                  , HttpServletRequest request
											  , BindingResult bindingResult ) throws Exception {
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try{
  	    	
  	    	// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}else {
        		vo.setUserId(jwtVerification.getTokenUserName(request));
        	}
  	    	
    		String status = cmmnDetailCodeManageService.updateCmmnDetailCode(vo) > 0 ?
			 		 Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
			String message = status.equals( Globals.STATUS_SUCCESS) ?
					 	 egovMessageSource.getMessage("success.request.msg") :
						 egovMessageSource.getMessage("fail.request.msg") ;
			model.addObject(Globals.STATUS, status);
	   		model.addObject(Globals.STATUS_MESSAGE, message);
	   		
  	    }catch (Exception e){
  	    	log.error("updateCmmnDetailCode error:" + e.toString());
  	    	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));		
  	    }
    	return model;
  	    
    	
    }
}
