package com.display.backoffice.uat.role.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.uat.role.models.RoleInfo;
import com.display.backoffice.uat.role.models.dto.RoleInfoRequestDto;
import com.display.backoffice.uat.role.service.RoleInfoManageService;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.uat.uia.service.UniUtilManageService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;


@RestController
@RequestMapping("/api/backoffice/uat/role/")
public class RoleInfoManageController {

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;
	
    @Autowired
    private RoleInfoManageService roleMangeServiec;
    
    @Autowired
    private UniUtilManageService uniMangeServiec;
    
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
    

    
    @Autowired
    private UniUtilManageService uniService;
    
    /** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
    
    @GetMapping("{roleId}.do")
    public ModelAndView selectServerDetailInfo(@PathVariable String roleId,
    										   HttpServletRequest request)throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	
    		Optional<RoleInfo> info = roleMangeServiec.selectRoleInfoDetail(roleId);
    		
    		info.orElseThrow(() -> new IllegalArgumentException("해당하는 서버 정보가가 없습니다. 잘못된 입력"));
    		
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
    		model.addObject(Globals.STATUS_REGINFO, info);
    		
    	}catch(Exception e) {
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	   		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
    }
    @GetMapping("roleCombo.do")
    public ModelAndView selectRoleComboInfo(HttpServletRequest request)throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
    		
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	
        	
    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS );
    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, roleMangeServiec.selectRoleInfoComboList());
    		
    	}catch(Exception e) {
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	   		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
    }
    @GetMapping("idCheck/{roleId}.do")
    public ModelAndView selectServerIdCkeckInfo(@PathVariable String roleId,
			  									HttpServletRequest request)throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	
        	
    		int ret = uniMangeServiec.selectIdDoubleCheckString("ROLE_ID", "TB_ROLEINFO", "ROLE_ID=["+ roleId + "[");
    		
    		String status = ret < 1 ? Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
    		String message = ret < 1 ? "common.codeOk.msg" : "common.codeFail.msg";
    		model.addObject(Globals.STATUS, status);
    		model.addObject(Globals.STATUS_MESSAGE,egovMessageSource.getMessage(message));
    		
    	}catch(Exception e) {
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	   		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
    }
    @DeleteMapping("{roleId}.do")
    public ModelAndView deleteServerDetailInfo(@PathVariable String roleId,
			   								HttpServletRequest request)throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	
        	
    		int ret = roleMangeServiec.deleteRoleInfo(roleId);
    		String status = (ret > 0) ? Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
    		String message = (ret > 0) ? egovMessageSource.getMessage("success.common.delete") : egovMessageSource.getMessage("fail.request.msg");
    		model.addObject(Globals.STATUS, status);
	   		model.addObject(Globals.STATUS_MESSAGE, message);
	   		 
    	}catch(Exception e) {
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	   		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
    }
    @PostMapping("roleUpdate.do")
    public ModelAndView updateRoleInfo(@Valid @RequestBody RoleInfoRequestDto info,
			   								HttpServletRequest request)throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		
    		
  	    	// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}else {
        		info.setUserId(jwtVerification.getTokenUserName(request));
        	}
        	
    		if (info.getMode().equals("Ins")) {
    			
    			UniUtilInfo uniCheck = new UniUtilInfo(); 
    			
    			
    			uniCheck.setInTable("TB_ROLEINFO");
    			uniCheck.setInCheckName("ROLE_ID");
    			uniCheck.setInCondition("ROLE_ID=["+ info.getRoleId() +"[");
    			if (uniService.selectIdDoubleCheck(uniCheck) > 0) {
    				model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
    		   		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("common.isExist.msg"));
    				return model;
    			}
    		}
    		
    		int ret = roleMangeServiec.updateRoleInfo(info);
    		
    		String status = (ret > 0) ? Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
    		String message = (ret > 0) ? egovMessageSource.getMessage("success.common.delete") : egovMessageSource.getMessage("fail.request.msg");
    		model.addObject(Globals.STATUS, status);
	   		model.addObject(Globals.STATUS_MESSAGE, message);
    	}catch(Exception e) {
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	   		model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
    }
    @PostMapping("roleList.do")
    public ModelAndView  selectServerInfoPageList(@RequestBody Map<String, Object> searchMap 
												 , HttpServletRequest request
												 , BindingResult bindingResult) throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try {
    		
    		
    		// 기존 세션 체크 인증에서 토큰 방식으로 변경
        	if (!jwtVerification.isVerification(request)) {

        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
        	
            int pageUnit = searchMap.get("pageUnit") == null ?   propertyService.getInt("pageUnit") : Integer.valueOf((String) searchMap.get("pageUnit"));
    		int pageSize = searchMap.get("pageSize") == null ?   propertyService.getInt("pageSize") : Integer.valueOf((String) searchMap.get("pageSize"));  
    	   
    	    
        	/** pageing */
        	PaginationInfo paginationInfo = new PaginationInfo();
    		paginationInfo.setCurrentPageNo( Integer.valueOf( searchMap.get("pageIndex").toString() ));
    		paginationInfo.setRecordCountPerPage(pageUnit);
    		paginationInfo.setPageSize(pageSize);

    		searchMap.put("firstIndex", paginationInfo.getFirstRecordIndex());
    		searchMap.put("lastRecordIndex", paginationInfo.getLastRecordIndex());
    		searchMap.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
    	    
    	    List<Map<String, Object>> codeList = (List<Map<String, Object>>) roleMangeServiec.selectRoleInfoPageList(searchMap);
    	    
    	    
    	    int totCnt = codeList.size() > 0 ?  Integer.valueOf( codeList.get(0).get("totalRecordCount").toString().replace("-", "") ) :0;
            

    		paginationInfo.setTotalRecordCount(totCnt);
    		model.addObject("resultList", codeList);
    		model.addObject("paginationInfo", paginationInfo);
    	}catch(Exception e) {
	   		 model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	   		 model.addObject(Globals.STATUS_MESSAGE, e.toString());
    	}
    	return model;
    }
}
