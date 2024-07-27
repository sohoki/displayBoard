package com.display.backoffice.bas.cnt.web;

import com.display.backoffice.etc.rest.service.RestInfoManageService;
import com.display.backoffice.sym.log.annotation.NoLogging;
import com.display.backoffice.etc.rest.models.RestInfoVO;
import com.display.backoffice.bas.cnt.service.CenterInfoService;
import com.display.backoffice.bas.cnt.models.CenterInfoVO;
import com.display.backoffice.bas.cnt.models.dto.CenterInfoReqDto;
import com.display.backoffice.uat.uia.service.PartInfoManageService;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;
import com.display.backoffice.util.service.UtilInfoService;
import com.display.backoffice.util.service.fileService;


@Api(tags = {"지점 정보 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/bas/cnt")
public class CenterInfoManageController {
	

	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
    @Value("${Globals.filePath}")
    private String filePath ;
    

	@Autowired
	protected EgovMessageSource egovMessageSource;

	@Autowired
	protected EgovPropertyService propertiesService;

	

	@Autowired
	private CenterInfoService centerService;

	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	/*
	@Autowired
	private RestInfoManageService restService;
	*/
	fileService uploadFile = new fileService();

	@ApiOperation(value="지점 현황 조회", notes = "성공시 지점 현황을 조회 합니다.")
	@PostMapping(value="centerList.do")
	public ModelAndView selectCenterInfoManageListByPagination(@RequestBody Map<String, Object> searchMap, 
																HttpServletRequest request, 
																BindingResult bindingResult) throws Exception {

		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
    			
        	}else {
        		//여기 부분 수정 
        		String[] userInfo = jwtVerification.getTokenUserInfo(request);
        		log.info("===============userInfo[3] : "+userInfo[3]+"===============");
        		searchMap.put("roleId", userInfo[2]);
        		searchMap.put("partId", userInfo[3]);
        	}
			
			int pageUnit = searchMap.get(Globals.PAGE_UNIT) == null ?   pageUnitSetting : Integer.valueOf((String) searchMap.get(Globals.PAGE_UNIT));
    		int pageSize = searchMap.get(Globals.PAGE_SIZE) == null ?   pageSizeSetting : Integer.valueOf((String) searchMap.get(Globals.PAGE_SIZE));  
    	   
    		log.info("pageUnit : "+pageUnit+ "   pageSize : "+pageSize);
    	    
        	/** pageing */
    		PaginationInfo paginationInfo = new PaginationInfo();
    		paginationInfo.setCurrentPageNo( Integer.valueOf( searchMap.get(Globals.PAGE_INDEX) == null  ?
    														  "1" : searchMap.get(Globals.PAGE_INDEX).toString() ));
    		paginationInfo.setRecordCountPerPage(pageUnit);
    		paginationInfo.setPageSize(pageSize);

    		searchMap.put(Globals.PAGE_FIRST_INDEX, paginationInfo.getFirstRecordIndex());
    		searchMap.put(Globals.PAGE_LAST_INDEX, paginationInfo.getLastRecordIndex());
    		searchMap.put(Globals.PAGE_RECORD_PER_PAGE, paginationInfo.getRecordCountPerPage());
    	    
    	    List<CenterInfoVO> codeList = centerService.selectCenterInfoPageInfoManageListByPagination(searchMap);
    	    int totCnt = codeList.size() > 0 ?  Integer.valueOf( codeList.get(0).getTotalRecordCount().toString()) :0;
            

    		paginationInfo.setTotalRecordCount(totCnt);
    		
    		model.addObject(Globals.STATUS_REGINFO, searchMap);
    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, codeList);
    		model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
    		model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    		
			
		}catch (NullPointerException e) {
			log.error("ERROR:" + e.toString());
		} catch (Exception e) {
			log.error("ERROR:" + e.toString());
		}
	    return model;
	}
	@ApiOperation(value="지점  상세 조회", notes = "성공시 지점 정보를 상세 조회 합니다.")
	@GetMapping(value="detail/{centerId}.do")
	public ModelAndView selectCenterInfoManageDetail(@PathVariable String centerId, 
													HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			Optional<CenterInfoVO> info = centerService.selectCenterInfoDetail(centerId);
			if (info.isPresent()){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_REGINFO, info.get());
			}else {
				model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
				model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			}
		}catch(Exception e) {
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		
		return model;
	}
	@ApiOperation(value="지점 사용 승인 ", notes = "성공시 지점 지점 사용 변경 합니다.")
	@GetMapping(value="state/{centerId}.do")
	public ModelAndView updateCenterStateChange(@PathVariable String centerId, 
												@RequestParam Map<String, Object> commandMap,
												HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		CenterInfoReqDto info = new CenterInfoReqDto();
	    		info.setAdminApprovalYn(UtilInfoService.NVLObj(commandMap.get("adminApprovalYn"), "Y"));
	    		info.setCenterId(centerId);
	    		info.setUserId(jwtVerification.getTokenUserName(request));
	    		int ret = centerService.updateCenterStateChange(info);
				if (ret > 0) {
		    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));
		    	}
		    	else {
		    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));
		    	}
	    	}
		}catch (NullPointerException e) {
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch (Exception e) {
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		
		return model;
	}

	
	@ApiOperation(value="지점 삭제", notes = "성공시 지점 정보를 삭제 합니다.")
	@ApiImplicitParam(name = "centerId", value = "지점 코드")
	@DeleteMapping(value="{centerId}.do")
	public ModelAndView deleteCenterInfo(@PathVariable String centerId,
										HttpServletRequest request
										) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);

		try {
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			log.info("=====centerId :"+centerId);
			int ret = centerService.deleteCenterInfoManage(centerId);
			if (ret > 0) {
        		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
        		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
        	}
        	else {
        		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    	    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
        	}
		} catch (NullPointerException e) {
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch (Exception e) {
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}

		return model;
	}
	@NoLogging
	@ApiOperation(value="지점 업데이트", notes = "성공시 지점 업데이트 합니다.")
	@PostMapping(value="centerUpdate.do")
	public ModelAndView updateCenterInfoManage(MultipartRequest mRequest,
												CenterInfoReqDto info,
												HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try {
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		info.setUserId(jwtVerification.getTokenUserName(request));
	    	}
			
			log.info("ss info : "+info.getAdminApprovalYn());
			
			String meesage = "";
			model.addObject(Globals.STATUS_REGINFO, info);
			
			String fileNm = uploadFile.uploadFileNm(mRequest.getFiles("centerImgFile"), propertiesService.getString("Globals.filePath"));
			if (!fileNm.isEmpty())
				info.setCenterImg(fileNm);
			 
			int ret = this.centerService.updateCenterInfoManage(info);
			if (ret > 0) {
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				meesage = info.getMode().equals("Ins") ? "success.common.insert": "success.common.update";
				model.addObject(Globals.STATUS_MESSAGE,	this.egovMessageSource.getMessage(meesage));
			} else {
				throw new Exception();
			}
		}catch (NullPointerException e) {
			log.debug("error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.common.insert"));
		}catch (Exception e) {
			log.debug("error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.common.insert"));
		} 
		
		
		return model;
	}
	/*
	@PostMapping({ "restPageInfoUpdate.do" })
	public ModelAndView updateRestInfo(@RequestBody RestInfoVO vo, 
								 MultipartRequest mRequest,
								 HttpServletRequest request, 
								 BindingResult bindingResult) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try {
			
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		vo.setUserId(jwtVerification.getTokenUserName(request));
	    	}
			log.debug("menuInfo:" + vo.getMenuInfo());
			vo.setMenuImg(this.uploadFile.uploadFileNm(mRequest.getFiles("centerImg"), filePath));
			
			log.debug("menuInfo:" + vo.getMenuImg());

			String meesage = vo.getMode().equals("Ins") ? "sucess.common.insert"
														: "sucess.common.update";
			int ret = this.restService.updateRestInfo(vo);
			if (ret > 0) {
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage(meesage));
			} else {
				throw new Exception();
			}
		}catch (NullPointerException e) {
			log.error("updateRestInfo error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.common.select"));
			
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		} catch (Exception e) {
			log.error("updateRestInfo error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE,this.egovMessageSource.getMessage("fail.common.select"));
			
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	*/
}
