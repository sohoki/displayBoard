package com.display.backoffice.sts.sch.web;

import java.util.List;

import egovframework.com.cmm.AdminLoginVO;
import com.display.backoffice.sts.report.web.ReportPageInfoManageController;
import com.display.backoffice.sts.sch.models.SchduleInfo;
import com.display.backoffice.sts.sch.models.SchduleInfoVO;
import com.display.backoffice.sts.sch.service.SchduleInfoManageService;


import com.display.backoffice.uat.models.UniUtilInfo;
import com.display.backoffice.uat.service.UniUtilManageService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import com.display.backoffice.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;
import com.display.backoffice.util.service.EgovDateUtil;
import com.display.backoffice.sts.cnt.web.FileUpladController;

@RestController
@RequestMapping("/backoffice/contentManage")
public class SchduleInfoManageController {

private static final Logger LOGGER = LoggerFactory.getLogger(SchduleInfoManageController.class);
	
    FileUpladController uploadFile = new FileUpladController();
    
    @Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Resource(name="egovSchIdGnrService")
	private EgovIdGnrService egovSchIdGnrService;
	
	@Autowired
	private EgovCcmCmmnDetailCodeManageService cmmnDetailService;
	
	@Autowired
	private SchduleInfoManageService schService;
	
	@Autowired
	private UniUtilManageService utilService;
	
	
	@RequestMapping(value="schInfoList.do")
	public ModelAndView  selectSchInfoManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
																						, @ModelAttribute("searchVO") SchduleInfoVO searchVO
																						, HttpServletRequest request
																						, BindingResult bindingResult						
																						, ModelMap model) throws Exception {
		
		  ModelAndView mv = new ModelAndView("/backoffice/contentManage/schInfoList");
		  
		  Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	      if(!isAuthenticated) {
	    	    mv.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    	    mv.setViewName("/backoffice/login");
	    		return mv;
	      }
	      
		  
		  if(  searchVO.getPageUnit() > 0  ){    	   
	    	   searchVO.setPageUnit(searchVO.getPageUnit());
		  }else {
				   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
		  }
		  searchVO.setPageSize(propertiesService.getInt("pageSize"));
	       
		  mv.addObject("regist", searchVO);
		  mv = schService.selectSchduleInfoManageListByPagination(searchVO);
		  return mv;	
	}
	@RequestMapping (value="schInfoView.do")
	public ModelAndView selecEqupInfoManageView(@ModelAttribute("loginVO") AdminLoginVO loginVO
	                                             , @ModelAttribute("vo")  SchduleInfoVO vo
	                                             , HttpServletRequest request
	                                			 , BindingResult bindingResult) throws Exception{	
		
		
		ModelAndView model = new ModelAndView("/backoffice/contentManage/schInfoView");
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	    if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.setViewName("/backoffice/login");
	    		return model;
	    }
		
		model.addObject("regist", vo);	
	    model.addObject("selectCodeDM", cmmnDetailService.selectCmmnDetailCombo("FileGubun"));
	    
		if (!vo.getMode().equals("Ins")){	
		  model.addObject("registinfo", schService.selectSchduleInfoManageView(vo.getSchCode()));
		}
		
		return model;
	}
	@RequestMapping (value="schInfoDelete.do")
	public ModelAndView deleteSchInfoManage(@ModelAttribute("loginVO") AdminLoginVO loginVO
										                          , @RequestBody SchduleInfo vo) throws Exception {
		
		
		ModelAndView model = new 	ModelAndView(Globals.JSONVIEW);
		
		
		UniUtilInfo utilInfo = new UniUtilInfo();
		utilInfo.setInTable("TB_SCHEDULEINFO");
		utilInfo.setInCondition("SCH_CODE=["+vo.getSchCode()+"[");
		String result = "F";
		try{
			
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject(Globals.STATUS,  Globals.STATUS_LOGINFAIL );
		    		return model;
		    }
		
		      int ret = 	utilService.deleteUniStatement(utilInfo);	
		      if (ret > 0 ) {		    	  
		    	  result = "O";	    
		    	  model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
		    	  model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		      }else {
		    	  throw new Exception();		    	  
		      }
		}catch (NullPointerException e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
	    	model.addObject(Globals.STATUS,  Globals.STATUS_FAIL);
		}catch (Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
	    	model.addObject(Globals.STATUS,  Globals.STATUS_FAIL);
		}
		return model;
	}
	
	//파일 업로드 관련 내용 정리 하기
	@RequestMapping (value="schInfoUpdate.do")
	public ModelAndView updateequpInfoManage( @ModelAttribute("loginVO") AdminLoginVO loginVO
				                             , @RequestBody SchduleInfoVO vo
											 , HttpServletRequest request                         				 
											 , BindingResult result) throws Exception{
		
		
        ModelAndView model = new 	ModelAndView(Globals.JSONVIEW);
		model.addObject("regist", vo);
		//관련 내용 넣기
		/** 파일 업로드 확인 하기 **/		
		try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
		    		return model;
		    }
			AdminLoginVO user = (AdminLoginVO) request.getSession().getAttribute("AdminLoginVO");	
			/** 파일 업로드 확인 하기 **/
			
	    	String realFolder = propertiesService.getString("Globals.fileStorePath") ;
	     	
	     	EgovDateUtil dateUtil = new EgovDateUtil();
	     	
	     	vo.setSchStartTime(dateUtil.timeInfo(vo.getSchStartTime1())+":"+dateUtil.timeInfo(vo.getSchStartTime2()));
	     	vo.setSchEndTime(dateUtil.timeInfo(vo.getSchEndTime1())+":" +dateUtil.timeInfo(vo.getSchEndTime2()));
			vo.setUserId(user.getAdminId());
			
			
			String meesage = vo.getMode().equals("Ins") ? "sucess.common.insert" : "sucess.common.update";
			SchduleInfo info = schService.updateSchduleInfoManage(vo);
			
			
			if (info != null){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));
				model.addObject("regist", info);
						
			}else {
				throw new Exception();
			}
			
		}catch (NullPointerException e){
			LOGGER.debug("updateequpInfoManage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));			
			
		}catch (Exception e){
			LOGGER.debug("updateequpInfoManage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));			
			
		}finally {
			return model;	
		}
	}
	
}
