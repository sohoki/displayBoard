package com.display.backoffice.uat.uia.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import com.display.backoffice.bas.code.web.EgovCcmCmmnDetailCodeManageController;
import com.display.backoffice.sym.log.service.EgovSysLogService;
import com.display.backoffice.uat.uia.models.PartInfo;
import com.display.backoffice.uat.uia.models.PartInfoVO;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.uat.uia.service.PartInfoManageService;
import com.display.backoffice.uat.uia.service.UniUtilManageService;

@Api(tags = {"기관별 부서 정보 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/uat/uia/part")
public class PartInfoManageController {

	
	
 
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;

	@Autowired
	protected EgovPropertyService propertiesService;
	
	@Autowired
	private PartInfoManageService partService;
	
	@Autowired
	private EgovSysLogService sysLogService;
	
	@Autowired
	private UniUtilManageService utilService;
	 
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	@ApiOperation(value="기관별 부서 코드 조회", notes = "성공시 기관별 부서 코드 조회 합니다.")
	@PostMapping("partList.do")
	public ModelAndView selectPartManagerList( @RequestBody Map<String, Object> searchVO 
												 , HttpServletRequest request
												 , BindingResult bindingResult) throws Exception {  
		 
		   ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		   try{
			   			   
	    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
	           if (!jwtVerification.isVerification(request)) {
	        		ResultVO resultVO = new ResultVO();
	    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
	           }
			   
			   
		       searchVO.put(Globals.PAGE_UNIT, propertiesService.getInt(Globals.PAGE_UNIT));
		       searchVO.put(Globals.PAGE_SIZE, propertiesService.getInt(Globals.PAGE_SIZE));
		       
		       
		       model.addObject(Globals.STATUS_REGINFO, searchVO);
		       
		       //** pageing *//   
		       
		   	   PaginationInfo paginationInfo = new PaginationInfo();
			   paginationInfo.setCurrentPageNo(Integer.parseInt(searchVO.get(Globals.PAGE_INDEX).toString()));
			   
			   paginationInfo.setRecordCountPerPage(propertiesService.getInt(Globals.PAGE_UNIT));
			   paginationInfo.setPageSize(propertiesService.getInt(Globals.PAGE_UNIT));
			   
			   searchVO.put(Globals.PAGE_FIRST_INDEX, paginationInfo.getFirstRecordIndex());
			   searchVO.put(Globals.PAGE_LAST_INDEX, paginationInfo.getLastRecordIndex());
			   searchVO.put(Globals.PAGE_RECORD_PER_PAGE, paginationInfo.getRecordCountPerPage());
			   
			   List<PartInfoVO> partList = partService.selectPartInfoPageInfoManageListByPagination(searchVO);
			   
			   int totCnt = partList.size() > 0 ? partList.get(0).getTotalRecordCount() : 0;  
			   model.addObject(Globals.JSON_RETURN_RESULT_LIST ,  partList );      
		             
			   paginationInfo.setTotalRecordCount(totCnt);
		       model.addObject(Globals.PAGE_INFO, paginationInfo);
		       model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
		       model.addObject("selectGroupCombo", partService.selectPartInfoCombo(searchVO));
		      
		   }catch (Exception e){
			   System.out.println("e:" + e.toString());
		   }
		   return model;
	}
	@ApiOperation(value="기관별 부서 상세 정보 조회", notes = "성공시 기관별 부서 상세 정보 조회 합니다.")
	@ApiImplicitParam(name = "partId", value = "부서 코드 ")
	@GetMapping("partDetail/{partId}.do")
	public ModelAndView partInfoDetail ( @PathVariable String partId
									  , HttpServletRequest request
									  , BindingResult bindingResult) throws Exception {  
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW); 
		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
		}
		
		
		try{ 
			PartInfoVO info = partService.selectPartInfoDetail(partId);
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    		model.addObject(Globals.STATUS_REGINFO, info);
    		
		 }catch(Exception e){
			log.error("partInfoDetail : error" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		 }
		 return model;
	}
	@ApiOperation(value="기관별 부서 combobox 정보 조회", notes = "성공시 기관별 부서 combobox 정보 조회 합니다.")
	@ApiImplicitParam(name = "partId", value = "부서 코드 ")
	@GetMapping("partCombo.do")
	public ModelAndView partComboInfo (@RequestParam Map<String, Object> commandMap,
										HttpServletRequest request,
										BindingResult bindingResult) throws Exception {  
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW); 
		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
		}
		
		try{ 
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, partService.selectPartInfoCombo(commandMap));
    		
		 }catch(Exception e){
			log.error("partComboInfo : error" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		 }
		 return model;
	}
	@ApiOperation(value="상위 부서 combobox 정보 조회", notes = "성공시 상위 부서 combobox 정보 조회 합니다.")
	@GetMapping("parentPartCombo.do")
	public ModelAndView parentPartComboInfo (@RequestParam Map<String, Object> commandMap,
											HttpServletRequest request) throws Exception {  
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW); 
		/*
		if (!jwtVerification.isVerificationAdmin(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        }
		*/
		try{ 
			model.addObject(Globals.JSON_RETURN_RESULT_LIST, partService.selectPartInfoCombo(commandMap));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
				//result = "F";
			log.error("parentPartComboInfo : error" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		 }
		 return model;
	}
	@ApiOperation(value="내선번호 환경 부서 combobox 정보 조회", notes = "성공시 내선번호 환경 부서 combobox 정보 조회 합니다.")
	@GetMapping("paxExtensionCombo.do")
	public ModelAndView paxExtensionComboComboInfo (@RequestParam Map<String, Object> commandMap,
													HttpServletRequest request) throws Exception {  
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW); 
		
		if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        }
		
		try{ 
			model.addObject(Globals.JSON_RETURN_RESULT_LIST, partService.selectPartInfoCombo(commandMap));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
				//result = "F";
			log.error("groupInfoDetail : error" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		 }
		 return model;
	}
	@ApiOperation(value="부서 정보 업데이트", notes = "성공시 부서 정보 업데이트 합니다.")
	@PostMapping("partUpdate.do")
	public ModelAndView partInfoUpdate ( @RequestBody PartInfo partInfo
										, HttpServletRequest request
										, BindingResult bindingResult) throws Exception {  
		
		
	    	// 기존 세션 체크 인증에서 토큰 방식으로 변경
    	if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
    	}else {
    		partInfo.setUserId(jwtVerification.getTokenUserName(request));
    	}
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		String meesage = "";
		String status  = "";
		try{
				
			int ret = ret = partService.updatePartInfoManage(partInfo);	
			System.out.println("partUpdate " + ret);
			
			status = ret >0 ? Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
			meesage = partInfo.getMode().equals("Ins") ? "sucess.common.insert" : "sucess.common.update";
			
			
			model.addObject(Globals.STATUS, status);
			model.addObject(Globals.STATUS_MESSAGE , egovMessageSource.getMessage(meesage));
		}catch(Exception e){
			
		    model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		    model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg") + e.toString());	
		}
		return model;
	}
	@ApiOperation(value="기관별 부서 정보 삭제", notes = "성공시 기관별 부서 정보 삭제 합니다.")
	@ApiImplicitParam(name = "partId", value = "부서 코드 ")
	@DeleteMapping("{partId}.do")
	public ModelAndView partDelete(@PathVariable String partId ) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
        
		UniUtilInfo utilInfo = new UniUtilInfo();
		utilInfo.setInTable("TB_PARTINFO");
		utilInfo.setInCondition("PART_ID=["+partId+"[");
		try{
			
		    int ret = utilService.deleteUniStatement(utilInfo);	
		    model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS); 
		    
		}catch (Exception e){
			//result = "F";
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
}
