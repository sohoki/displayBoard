package com.display.backoffice.bas.program.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.program.models.dto.ProgrmInfoDto;
import com.display.backoffice.bas.program.service.ProgrameInfoManageService;
import com.display.backoffice.sym.log.annotation.NoLogging;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.UtilInfoService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"프로그램 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/sys/prog")
public class ProgrmInfoManageController {

	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
	private ProgrameInfoManageService progrmService;
	
    @Autowired
    private UniUtilManageService uniMangeServiec;


	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
    

	/**
	 * 프로그램 목록 조회
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="프로그램 목록 조회", notes="프로그램 목록 조회 상세")
	@PostMapping("programList.do")
	public ModelAndView selectProgrmInfoListAjax(@RequestBody Map<String, Object> searchVO,
			HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView (Globals.JSON_VIEW);
		
		if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
    	}
		
		int pageUnit = searchVO.get(Globals.PAGE_UNIT) == null ? propertiesService.getInt(Globals.PAGE_UNIT)
				: Integer.valueOf((String) searchVO.get(Globals.PAGE_UNIT));
		int pageSize =   searchVO.get(Globals.PAGE_SIZE) == null ? propertiesService.getInt(Globals.PAGE_SIZE)
				: Integer.valueOf((String) searchVO.get(Globals.PAGE_SIZE));
   	    PaginationInfo paginationInfo = new PaginationInfo();
	    paginationInfo.setCurrentPageNo( Integer.parseInt(UtilInfoService.NVL(searchVO.get(Globals.PAGE_INDEX),"1")));
	    paginationInfo.setRecordCountPerPage(pageUnit);
	    paginationInfo.setPageSize(pageSize);
	    
	    System.out.println("pageUnit:" + pageUnit + ": pageSize" + pageSize);

	    searchVO.put(Globals.PAGE_FIRST_INDEX, paginationInfo.getFirstRecordIndex());
	    searchVO.put(Globals.PAGE_LAST_INDEX, paginationInfo.getLastRecordIndex());
	    searchVO.put(Globals.PAGE_RECORD_PER_PAGE, paginationInfo.getRecordCountPerPage());
	    
	    
		List<ProgrmInfoDto> list = progrmService .selectProgrmInfoList(searchVO);
        int totCnt =  list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
		paginationInfo.setTotalRecordCount(totCnt);

		model.addObject(Globals.STATUS_REGINFO, searchVO);
		model.addObject(Globals.JSON_RETURN_RESULT_LIST, list);
	    model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
	    model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
	    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		
		return model;
	}
	
	/**
	 * 프로그램 저장
	 * @param progrmInfo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="프로그램 정보 저장", notes="프로그램 정보 저장")
	@PostMapping ("programInfo.do")
	public ModelAndView updateProgrmInfo(@Valid @RequestBody ProgrmInfoDto progrmInfoDto,
										 HttpServletRequest request) throws Exception{
		ModelAndView model = new ModelAndView (Globals.JSON_VIEW);
		
		if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
    	}else {
    		progrmInfoDto.setUserId(jwtVerification.getTokenUserName(request));
    	}
		
		int ret = 0;
		switch (progrmInfoDto.getMode()) {
			case Globals.SAVE_MODE_INSERT:
				ret = progrmService.insertProgrmInfo(progrmInfoDto);
				break;
			case Globals.SAVE_MODE_UPDATE:
				ret = progrmService.updateProgrmInfo(progrmInfoDto);
				break;
			default:
				throw new EgovBizException("잘못된 호출입니다.");
		}
		
		String messageKey = "";
		if (ret > 0) {
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			messageKey = StringUtils.equals(progrmInfoDto.getMode(), Globals.SAVE_MODE_INSERT) 
					? "success.common.insert" : "success.common.update";
		}
		else {
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			messageKey = StringUtils.equals(progrmInfoDto.getMode(), Globals.SAVE_MODE_INSERT) 
					? "fail.common.insert" : "fail.common.update";
		}
		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(messageKey));
		
		return model;
	}
	
	/**
	 * 프로그램 삭제
	 * @param progrmInfo
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="프로그램 정보 삭제", notes="프로그램 정보 삭제")
	@ApiImplicitParam(name = "progrmFileNm", value = "프로그램 생성시 발급되는 progrmFileNm")
	@DeleteMapping ("{progrmFileNm}.do")
	public ModelAndView deleteProgrmInfoManage(@PathVariable String progrmFileNm, 
												HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView (Globals.JSON_VIEW);
		

		if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
    	}
		
		
		int ret = progrmService.deleteProgrmInfo(progrmFileNm);
		if (ret > 0) {
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
		}
		else {
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
		}
		
		return model;
	}
	
	/**
	 * 프로그램 중복 체크
	 * @param progrmFileNm
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="프로그램 코드 중복 체크", notes="프로그램 코드 중복 체크")
	@NoLogging
    @GetMapping ("programIDCheck/{progrmFileNm}.do")
    public ModelAndView programIDCheck(@PathVariable String progrmFileNm, 
    								  HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView (Globals.JSON_VIEW);

		if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
    	}
		
		
    	int ret = uniMangeServiec.selectIdDoubleCheckString("PROGRM_FILE_NM", "COMTNPROGRMLIST", "PROGRM_FILE_NM = ["+ progrmFileNm + "[");
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
}
