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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
import egovframework.com.cmm.service.ResultVO;
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
    private EgovCcmCmmnDetailCodeManageService cmmnDetailService;
	
	
	
	@Autowired
	private CenterInfoService centerService;
	
	@Resource(name="RestNoticeInfoManageService")
	private RestNoticeInfoManageService restNoticeService;
	
	
    FileUpladController uploadFile = new FileUpladController();
	
    
    @PostMapping(value="restNoticeList.do")
	public ModelAndView  selectRestNoticeInfoManageListByPagination(@RequestBody RestNoticeInfoVO searchVO
																	, HttpServletRequest request
																	, BindingResult bindingResult	) throws Exception {
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
    	try{
    		if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
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
		    model.addObject(Globals.JSON_RETURN_RESULT_LIST,  list );
		    model.addObject(Globals.STATUS_REGINFO, searchVO);
		       
		    int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
		          
			paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
		    model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
		    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(IOException e1){
			log.error("restPageInfoList error:" + e1.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.error("restPageInfoList error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			
		}
    	return model;
    }
    @DeleteMapping(value="restNoticeDelete.do")
    public ModelAndView deleteRestNOticeInfo( @RequestBody RestNoticeInfo vo
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
								
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
			
			int ret = restNoticeService.deleteRestNoticeInfo(vo.getNoteSeq());			
			if (ret > 0){
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			
			}else {
			   throw new Exception();
		    }
		}catch(IOException e1){
			log.error("restPageInfoList error:" + e1.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.debug("detailUpdate error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
    
    @PostMapping(value="restNoticeUpdate.do")
    public ModelAndView selectRestNoticePageUpdate( @RequestBody RestNoticeInfoVO vo													
													, HttpServletRequest request
													, BindingResult bindingResult	) throws Exception {
    	
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
	    String meesage = "";
	    try{
	    	if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}else {
        		vo.setUserId(jwtVerification.getTokenUserName(request));
        	}
   			
   			int ret  = 0;
   			meesage = vo.getMode().equals("Ins") ? "sucess.common.insert": "sucess.common.update";
   			
   			ret = restNoticeService.updateRestNoticeInfo(vo);
   			if (ret >0){
   				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
   				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));
   			}else {
   				throw new Exception();
   			}
        }catch(Exception e){
    	    log.debug("error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    }
	    return model;
    }
    @PostMapping(value="restNoticeDetail.do")
	public ModelAndView selectRestNoticePageView(@RequestBody RestNoticeInfoVO searchVO													
												, HttpServletRequest request
												, BindingResult bindingResult	) throws Exception {
									
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
			model.addObject("regist", searchVO);
		
			if (searchVO.getMode().equals("Edt")){
			    model.addObject("registInfo", restNoticeService.selectRestNoticeInfo(searchVO.getNoteSeq()));
			}
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	
	}
    @PostMapping(value="restPageInfoList.do")
	public ModelAndView  selectRestpageInfoManageListByPagination(@RequestBody RestInfoVO searchVO
																, HttpServletRequest request
																, BindingResult bindingResult	) throws Exception {
    	ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		//ModelAndView model = new ModelAndView("/backoffice/RestManage/restList");
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
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
	@PostMapping(value="restDetail.do")
	public ModelAndView  selectRestPageView(@RequestBody RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		//ModelAndView model = new ModelAndView("/backoffice/RestManage/restDetail");
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
	        //String menuSeq = request.getParameter("menuSeq") != null ? request.getParameter("menuSeq") : "";
	        model.addObject("regist", searchVO);
	        
	        model.addObject("selectCodeDM", cmmnDetailService.selectCmmnDetailCombo("MEAL_GUBUN"));  
			model.addObject("selectCenterCombo", centerService.selectCenterCombo()); 
			
	        if (searchVO.getMode().equals("Edt")){
	        	
	        	 model.addObject("regist", restService.selectRestInfoDetail(searchVO.getMenuSeq()));
	        }
	       
	        model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
		
	}
	@RequestMapping(value="restPageMian.do")
	public ModelAndView  restPageMian(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @ModelAttribute("searchVO") RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		
		//ModelAndView model = new ModelAndView("jsonView");
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
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
	        
	        if (searchVO.getMode().equals(Globals.SAVE_MODE_UPDATE)){
	        	 model.addObject(Globals.STATUS_REGINFO, restService.selectRestInfoDetail(searchVO.getMenuSeq()));
	        }
	       
	        model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	@RequestMapping(value="resStarUpdate.do")
	public ModelAndView restPageStartCount(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @RequestBody RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			
			int ret =   restService.updateRestGrade(searchVO);
			
		    model.addObject("menuInfo",  restService.selectRestInfoDetail(searchVO.getMenuSeq()) );
		    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	@RequestMapping(value="resMenuDetail.do")
	public ModelAndView resMenuDetail(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @ModelAttribute("searchVO") RestInfoVO searchVO													
											, HttpServletRequest request
											, BindingResult bindingResult	) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
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
    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	    }
	    return model;
		
	}
	@PostMapping(value="restPageInfoDelete.do")
	public ModelAndView deleteRestInfo( @RequestBody RestInfo vo
										, HttpServletRequest request
										, BindingResult bindingResult	) throws Exception {
							
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        	}
		    
		    
			int ret = restService.deleteRestInfo(vo.getMenuSeq());			
			if (ret > 0){
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				
			}else {
				throw new Exception();
			}
		}catch(Exception e){
			log.debug("detailUpdate error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
        return model;
	}
	
	
}
