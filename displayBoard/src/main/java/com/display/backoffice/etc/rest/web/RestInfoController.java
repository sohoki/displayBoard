package com.display.backoffice.etc.rest.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.cnt.service.CenterInfoService;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;
import com.display.backoffice.etc.rest.models.RestInfo;
import com.display.backoffice.etc.rest.models.RestInfoVO;
import com.display.backoffice.etc.rest.models.RestNoticeInfo;
import com.display.backoffice.etc.rest.models.RestNoticeInfoVO;

import com.display.backoffice.etc.rest.service.RestInfoManageService;
import com.display.backoffice.etc.rest.service.RestNoticeInfoManageService;

import com.display.backoffice.sts.cnt.web.FileUpladController;
import com.display.backoffice.sym.monter.web.DisplayPageInfoManageController;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;



@Api(tags = {"RestInfoController 구성 관리 API"})
@Slf4j
@RestController
@RequestMapping("/api/RestManage")
public class RestInfoController {

	
	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	
	@Resource(name = "RestInfoManageService")
	private RestInfoManageService restService;
	
	@Autowired
	private CenterInfoService centerService;
	
	@Resource(name="RestNoticeInfoManageService")
	private RestNoticeInfoManageService restNoticeService;
	
	
    FileUpladController uploadFile = new FileUpladController();
	
    
    @RequestMapping(value="restNoticeList.do")
	public ModelAndView  selectRestNoticeInfoManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
															, @ModelAttribute("searchVO") RestNoticeInfoVO searchVO
															, HttpServletRequest request
															, BindingResult bindingResult	) throws Exception {
    	
    	ModelAndView model = new ModelAndView("/backoffice/RestManage/restNoticeList");
    	try{
    		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	        if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.setViewName("/backoffice/login");
	    		return model;
	    		
	        }
	        if(  searchVO.getPageUnit() > 0  ){    	   
		    	   searchVO.setPageUnit(searchVO.getPageUnit());
			}else {
					   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			}
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
		   
		    /** pageing */       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			  
			
	        List<RestNoticeInfoVO> list = restNoticeService.selectRestNoticeInfoLeftListByPagination(searchVO);
		    model.addObject("resultList",  list );
		    model.addObject("regist", searchVO);
		       
		    int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
		          
			paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject("paginationInfo", paginationInfo);
		    model.addObject("totalCnt", totCnt);
	        
		}catch(IOException e1){
			log.error("restPageInfoList error:" + e1.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject("status", "FAIL");
		}catch(Exception e){
			log.error("restPageInfoList error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject("status", "FAIL");
			
		}
    	return model;
    }
    @RequestMapping(value="restNoticeDelete.do")
    public ModelAndView deleteRestNOticeInfo(@ModelAttribute("loginVO") AdminLoginVO loginVO
								            , @RequestBody RestNoticeInfo vo
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
								
			ModelAndView model = new ModelAndView("jsonView");
			try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
				model.addObject("status", "LOGIN FAIL");
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
				return model;
				}
				
				
				int ret = restNoticeService.deleteRestNoticeInfo(vo.getNoteSeq());			
				if (ret > 0){
					model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
					model.addObject("status", "SUCCESS");
				
				}else {
				   throw new Exception();
			    }
			}catch(IOException e1){
				log.error("restPageInfoList error:" + e1.toString());
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
				model.addObject("status", "FAIL");
			}catch(Exception e){
				log.debug("detailUpdate error:" + e.toString());
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
				model.addObject("status", "FAIL");
			}
			return model;
	}
    
    @RequestMapping(value="restNoticeUpdate.do")
    public ModelAndView selectRestNoticePageUpdate(@ModelAttribute("loginVO") AdminLoginVO loginVO
													, @RequestBody RestNoticeInfoVO vo													
													, HttpServletRequest request
													, BindingResult bindingResult	) throws Exception {
    	
    	   ModelAndView model = new ModelAndView("jsonView");
    	   String meesage = "";
    	   try{
    		   Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	   		    if(!isAuthenticated) {
	   		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	   		    		model.addObject("status", "LOGIN FAIL");
	   		    		return model;
	   		    }
	   			vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString());
	   			
	   			int ret  = 0;
	   			
	   			if (vo.getMode().equals("Ins")){
	   				meesage = "sucess.common.insert";
	   			}else {
	   				meesage = "sucess.common.update";
	   			}	
	   			ret = restNoticeService.updateRestNoticeInfo(vo);
	   			log.debug("ret:" + ret);
   				if (ret >0){
	   				model.addObject("status", Globals.STATUS_SUCCESS);
	   				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));
	   			}else {
	   				throw new Exception();
	   			}
	       }catch(Exception e){
	    	    log.debug("error:" + e.toString());
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
				model.addObject("status", "FAIL");
    	   }
    	   return model;
    }
    @RequestMapping(value="restNoticeDetail.do")
	public ModelAndView selectRestNoticePageView(@ModelAttribute("loginVO") AdminLoginVO loginVO
												, @ModelAttribute("searchVO") RestNoticeInfoVO searchVO													
												, HttpServletRequest request
												, BindingResult bindingResult	) throws Exception {
									
			ModelAndView model = new ModelAndView("/backoffice/RestManage/resNoticeDetail");
			try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
					model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		model.setViewName("/backoffice/login");
					return model;
				}
				model.addObject("regist", searchVO);
			
				if (searchVO.getMode().equals("Edt")){
				    model.addObject("registInfo", restNoticeService.selectRestNoticeInfo(searchVO.getNoteSeq()));
				}
				model.addObject("status", "SUCCESS");
			}catch(Exception e){
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
				model.addObject("status", "FAIL");
			}
			return model;
	
	}
	@RequestMapping(value="restPageInfoList.do")
	public ModelAndView  selectRestpageInfoManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
																, @ModelAttribute("searchVO") RestInfoVO searchVO
																, HttpServletRequest request
																, BindingResult bindingResult	) throws Exception {
		
		ModelAndView model = new ModelAndView("/backoffice/RestManage/restList");
		try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	        if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.setViewName("/backoffice/login");
	    		return model;
	    		
	        }
	        if(  searchVO.getPageUnit() > 0  ){    	   
		    	   searchVO.setPageUnit(searchVO.getPageUnit());
			}else {
					   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			}
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
		   
		    /** pageing */       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			  
			model.addObject("selectCodeDM", cmmnDetailService.selectCmmnDetailCombo("MEAL_GUBUN"));  
			model.addObject("selectCenterCombo", centerService.selectCenterCombo()); 
	        List<RestInfoVO> list = restService.selectRestInfoPageInfoManageListByPagination(searchVO);
		    model.addObject("resultList",  list );
		    model.addObject("regist", searchVO);
		       
		    int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
		          
			paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject("paginationInfo", paginationInfo);
		    model.addObject("totalCnt", totCnt);
	        
		}catch(Exception e){
			log.error("restPageInfoList error:" + e.toString());
			
		}
		return model;
	}
	@RequestMapping(value="restDetail.do")
	public ModelAndView  selectRestPageView(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @ModelAttribute("searchVO") RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		
		//ModelAndView model = new ModelAndView("jsonView");
		ModelAndView model = new ModelAndView("/backoffice/RestManage/restDetail");
		try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	        if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.addObject("status", "LOGIN FAIL");
	    		return model;
	        }
	        //String menuSeq = request.getParameter("menuSeq") != null ? request.getParameter("menuSeq") : "";
	        model.addObject("regist", searchVO);
	        
	        model.addObject("selectCodeDM", cmmnDetailService.selectCmmnDetailCombo("MEAL_GUBUN"));  
			model.addObject("selectCenterCombo", centerService.selectCenterCombo()); 
			
	        if (searchVO.getMode().equals("Edt")){
	        	
	        	 model.addObject("regist", restService.selectRestInfoDetail(searchVO.getMenuSeq()));
	        }
	       
	        model.addObject("status", "SUCCESS");
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
    		model.addObject("status", "FAIL");
		}
		return model;
		
	}
	@RequestMapping(value="restPageMian.do")
	public ModelAndView  restPageMian(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @ModelAttribute("searchVO") RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		
		//ModelAndView model = new ModelAndView("jsonView");
		ModelAndView model = new ModelAndView("/backoffice/RestManage/restMain");
		try{
			
			if(  searchVO.getPageUnit() > 0  ){    	   
		    	   searchVO.setPageUnit(searchVO.getPageUnit());
			}else {
					   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			}
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
		   
		    /** pageing */       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());
			
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			
			
	        model.addObject("regist", searchVO);
	        String menu_day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        
	        searchVO.setSearchStartDay(menu_day.replaceAll("-", ""));
	        searchVO.setSearchEndDay(menu_day.replaceAll("-", ""));
	        searchVO.setSearchOrder("O");
	        
	        List<RestInfoVO> list = restService.selectRestInfoPageInfoManageListByPagination(searchVO);
	        int totCnt = list.size() > 0 ? Integer.valueOf(list.get(0).getTotalRecordCount()) : 0;    
	        model.addObject("resultList",  list );
	        model.addObject("menu_day",  menu_day );
	        model.addObject("menuCnt", totCnt  );
	        
	        RestNoticeInfoVO notice = new RestNoticeInfoVO();
	        notice.setSearchStartDay(menu_day.replaceAll("-", ""));
	        notice.setFirstIndex(0);
	        model.addObject("noteList",   restNoticeService.selectRestNoticeInfoLeftListByPagination(notice) );
	        
	        if (searchVO.getMode().equals("Edt")){
	        	 model.addObject("regist", restService.selectRestInfoDetail(searchVO.getMenuSeq()));
	        }
	       
	        model.addObject("status", "SUCCESS");
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
    		model.addObject("status", "FAIL");
		}
		return model;
	}
	@RequestMapping(value="resStarUpdate.do")
	public ModelAndView restPageStartCount(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @RequestBody RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		ModelAndView model = new ModelAndView("jsonView");
		try{
			
			int ret =   restService.updateRestGrade(searchVO);
			
		    model.addObject("menuInfo",  restService.selectRestInfoDetail(searchVO.getMenuSeq()) );
		    model.addObject("status", "SUCCESS");
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
    		model.addObject("status", "FAIL");
		}
		return model;
	}
	@RequestMapping(value="resMenuDetail.do")
	public ModelAndView resMenuDetail(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @ModelAttribute("searchVO") RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		ModelAndView model = new ModelAndView("/backoffice/RestManage/resMenuDetail");
	    try{
	    	
	    	
	    	
	    	
	    	
	    	if(  searchVO.getPageUnit() > 0  ){    	   
		    	   searchVO.setPageUnit(searchVO.getPageUnit());
			}else {
					   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			}
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
		   
		    /** pageing */       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());
			
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			
	        String menu_day = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String menu_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm"));
            
        	if (1300 < Integer.parseInt(menu_time)  ){
	        	searchVO.setSearchMenugubun("MEAL_GUBUN_2");
	        	searchVO.setSearchTime("F"); 
	        }else {
	        	searchVO.setSearchMenugubun("MEAL_GUBUN_1");
	        	searchVO.setSearchTime("M"); 
	        }
            
	        searchVO.setSearchStartDay(menu_day.replaceAll("-", ""));
	        searchVO.setSearchEndDay(menu_day.replaceAll("-", ""));
	        searchVO.setSearchOrder("O");
	        
	        List<RestInfoVO> list = restService.selectRestInfoPageInfoManageListByPagination(searchVO);
	        
	        RestNoticeInfoVO notice = new RestNoticeInfoVO();
	        notice.setSearchStartDay(menu_day.replaceAll("-", ""));
	        notice.setFirstIndex(0);
	        model.addObject("noteList",   restNoticeService.selectRestNoticeInfoLeftListByPagination(notice) );
	        
	        int totCnt = list.size() > 0 ? Integer.valueOf(list.get(0).getTotalRecordCount()) : 0;    
	        model.addObject("resultList",  list );
	        model.addObject("menu_day",  menu_day );
	        model.addObject("regist", searchVO);
	    	
	    }catch(Exception e){
	    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
    		model.addObject("status", "FAIL");
	    }
	    return model;
		
	}
	@RequestMapping(value="restPageInfoDelete.do")
	public ModelAndView deleteRestInfo(@ModelAttribute("loginVO") AdminLoginVO loginVO
							            , @RequestBody RestInfo vo
										, HttpServletRequest request
										, BindingResult bindingResult	) throws Exception {
							
			ModelAndView model = new ModelAndView("jsonView");
			try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			    if(!isAuthenticated) {
			    	model.addObject("status", "LOGIN FAIL");
			    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
			    	return model;
			    }
			    
			    
				int ret = restService.deleteRestInfo(vo.getMenuSeq());			
				if (ret > 0){
					model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
					model.addObject("status", "SUCCESS");
					
				}else {
					throw new Exception();
				}
			}catch(Exception e){
				log.debug("detailUpdate error:" + e.toString());
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
				model.addObject("status", "FAIL");
			}
            return model;
	}
	
	
}
