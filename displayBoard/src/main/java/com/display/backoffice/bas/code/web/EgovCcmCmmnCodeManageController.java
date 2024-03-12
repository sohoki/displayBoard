package com.display.backoffice.bas.code.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.bas.code.models.CmmnCode;
import com.display.backoffice.bas.code.models.dto.CmmnCodeDto;
import com.display.backoffice.bas.code.service.EgovCcmCmmnCodeManageService;
import com.display.backoffice.sym.log.annotation.NoLogging;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Api(tags = {"공통코드 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/sys/cmm/cca")
public class EgovCcmCmmnCodeManageController {

	
	@Autowired
    private EgovCcmCmmnCodeManageService cmmnCodeManageService;


    @Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
	
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	@Autowired
	private UniUtilManageService utilService;

	/**
	 * 공통코드를 삭제한다.
	 * @param AdminLoginVO
	 * @param cmmnCode
	 * @param model
	 * @return "forward:/sym/ccm/cca/EgovCcmCmmnCodeList.do"
	 * @throws Exception
	 */
	@ApiOperation(value="공통코드를 삭제", notes = "성공시 공통코드를 삭제 합니다.")
	@ApiImplicitParam(name = "codeId", value = "공통코드 CODE ID")
    @DeleteMapping("{codeId}.do")
	public ModelAndView deleteCmmnCode (@PathVariable String codeId 
								  , HttpServletRequest request
								  , BindingResult bindingResult	) throws Exception {
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {

        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
    		
    		int ret = cmmnCodeManageService.deleteCmmnCode(codeId);
        	
    		
        	if (ret > 0){
    			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    			model.addObject(Globals.STATUS_MESSAGE,   egovMessageSource.getMessage("success.common.delete"));    		
        	}else {
        		throw new Exception();    		
        	}
        	
    	}catch(Exception e) {
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
    	//상세 코드 삭제 
    	
	}

	
    @ApiOperation(value="공통코드 리스트", notes = "성공시 공통코드 조회 합니다.")
    @PostMapping("code/codeList.do")
	public ModelAndView selectCmmnCodeList (@RequestBody Map<String, Object> searchMap
										, HttpServletRequest request
										, BindingResult bindingResult
										) throws Exception {
    	
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
    		paginationInfo.setCurrentPageNo( Integer.valueOf( searchMap.get(Globals.PAGE_INDEX) == null  ?
    														  "1" : searchMap.get(Globals.PAGE_INDEX).toString() ));
    		paginationInfo.setRecordCountPerPage(pageUnit);
    		paginationInfo.setPageSize(pageSize);

    		searchMap.put(Globals.PAGE_FIRST_INDEX, paginationInfo.getFirstRecordIndex());
    		searchMap.put(Globals.PAGE_LAST_INDEX, paginationInfo.getLastRecordIndex());
    		searchMap.put(Globals.PAGE_RECORD_PER_PAGE, paginationInfo.getRecordCountPerPage());
    	    
    	    List<CmmnCodeDto> codeList = cmmnCodeManageService.selectCmmnCodeListByPagination(searchMap);
    	    int totCnt = codeList.size() > 0 ?  Integer.valueOf( codeList.get(0).getTotalRecordCount().toString()) :0;
            

    		paginationInfo.setTotalRecordCount(totCnt);
    		
    		model.addObject(Globals.STATUS_REGINFO, searchMap);
    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, codeList);
    		model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
    		model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    		
    		
    		
    	}catch (Exception e){
			log.debug("selectCmmnCodeList error:" + e.toString());
			log.debug("selectCmmnCodeList number:" + e.getStackTrace()[0].getLineNumber());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg")+ e.toString());	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    }
	    
        return model;
        
        
	}
    @ApiOperation(value="공통코드 중복체크", notes = "성공시 공통코드 중복체크 합니다.")
    //ID 체크 
    @NoLogging
    @GetMapping("codeIDCheck/{codeId}.do")
    public ModelAndView selectIdCheck(@PathVariable String codeId ,
    								 HttpServletRequest request)throws Exception{
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	
		// 기존 세션 체크 인증에서 토큰 방식으로 변경
    	if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
    	}
    	
    	
    	
    	UniUtilInfo util = new UniUtilInfo();
    	util.setInCondition("CODE_ID = [" + codeId + "[");
    	util.setInTable("COMTCCMMNCODE");
    	util.setInCheckName("CODE_ID");
    	
		int ret = utilService.selectIdDoubleCheck(util);
		if (ret == 0) {
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("common.codeOk.msg"));
		}
		else {
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("common.codeFail.msg"));
		}
		return model;
    }
    
  	
  	@SuppressWarnings("finally")	
  	@ApiOperation(value="공통코드 업데이트", notes = "성공시 공통코드 업데이트 합니다.")
  	@PostMapping("code/codeUpdate.do")
  	public ModelAndView  updateCmmCode (@RequestBody CmmnCode vo
                                       , HttpServletRequest request
                      			       , BindingResult bindingResult) throws Exception {
			
  		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
  	    try{
  	    	
  	    	// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}else {
        		vo.setUserId(jwtVerification.getTokenUserName(request));
        	}
  	    	
    		String status = cmmnCodeManageService.updateCmmnCode(vo) > 0 ?
			 		 Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
			String message = status.equals( Globals.STATUS_SUCCESS) ?
					 	 egovMessageSource.getMessage("success.request.msg") :
						 egovMessageSource.getMessage("fail.request.msg") ;
			model.addObject(Globals.STATUS, status);
	   		model.addObject(Globals.STATUS_MESSAGE, message);
	   		
  	    }catch (Exception e){
  	    	System.out.println("error:"+ e.toString()  );
  	    	model.addObject("status", Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));		
  	    }
  	    finally{
  	    	return model;
  	    }
  		
  	}
}
