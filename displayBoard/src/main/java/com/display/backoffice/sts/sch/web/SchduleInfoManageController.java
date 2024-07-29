package com.display.backoffice.sts.sch.web;

import java.util.List;

import com.display.backoffice.sts.sch.models.SchduleInfo;
import com.display.backoffice.sts.sch.models.SchduleInfoVO;
import com.display.backoffice.sts.sch.service.SchduleInfoManageService;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.UtilInfoService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import egovframework.let.utl.fcc.service.EgovDateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.display.backoffice.sts.cnt.web.FileUpladController;

@Api(tags = {"메시지관리 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/contentManage/schedule")
public class SchduleInfoManageController {

	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	
	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
    @Value("${Globals.filePath}")
    private String filePath ;
	
    FileUpladController uploadFile = new FileUpladController();
    
    @Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Resource(name="egovSchIdGnrService")
	private EgovIdGnrService egovSchIdGnrService;
	
	@Autowired
	private SchduleInfoManageService schService;
	
	@Autowired
	private UniUtilManageService utilService;
	

	
	@ApiOperation(value="메시지관리 조회", notes = "성공시 메시지 현황을 조회 합니다.")
	@PostMapping(value="schInfoList.do")
	public ModelAndView  selectSchInfoManageListByPagination( @RequestBody SchduleInfoVO searchVO
															, HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			
			if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}
			searchVO.setPageUnit(UtilInfoService.NVL(searchVO.getPageUnit(), pageUnitSetting) );
			searchVO.setPageSize(UtilInfoService.NVL(searchVO.getPageSize(), pageSizeSetting) );
			searchVO.setPageIndex(UtilInfoService.NVL(searchVO.getPageIndex(), 1) );
	    	
			   
			model.addObject(Globals.STATUS_REGINFO, searchVO);
			 
		     /** pageing */       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			//수정
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			
	         List<SchduleInfoVO> schduleList = schService.selectSchduleInfoManageListByPagination(searchVO);
	        
		     model.addObject(Globals.JSON_RETURN_RESULT_LIST,   schduleList);
		    
		     
		     int totCnt =  schduleList.size() > 0 ? schduleList.get(0).getTotalRecordCount() : 0; ;       
			 paginationInfo.setTotalRecordCount(totCnt);
		     model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
		     model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
		     model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			  
		}catch (NullPointerException e){
			log.error("selectContentSchList ERROR: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch (Exception e){
			log.error("selectContentSchList ERROR: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;	
	}
	
	@DeleteMapping (value="schInfoDelete.do")
	public ModelAndView deleteSchInfoManage(@RequestBody SchduleInfo vo
											, HttpServletRequest request) throws Exception {
		
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확
    	}
		
		
		UniUtilInfo utilInfo = new UniUtilInfo();
		utilInfo.setInTable("TB_SCHEDULEINFO");
		utilInfo.setInCondition("SCH_CODE=["+vo.getSchCode()+"[");
		try{
			
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
		
			int ret = 	utilService.deleteUniStatement(utilInfo);	
			if (ret > 0 ) {		    	  
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
	@PostMapping (value="schInfoUpdate.do")
	public ModelAndView updateequpInfoManage(@RequestBody SchduleInfoVO vo
											 , HttpServletRequest request) throws Exception{
		
		
        ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		model.addObject(Globals.STATUS_REGINFO, vo);
		//관련 내용 넣기
		/** 파일 업로드 확인 하기 **/		
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			
			/** 파일 업로드 확인 하기 **/
			
	    	//String realFolder = propertiesService.getString("Globals.fileStorePath") ;
	     	
	     	EgovDateUtil dateUtil = new EgovDateUtil();
	     	
	     	vo.setSchStartTime(dateUtil.timeInfo(vo.getSchStartTime1())+":"+dateUtil.timeInfo(vo.getSchStartTime2()));
	     	vo.setSchEndTime(dateUtil.timeInfo(vo.getSchEndTime1())+":" +dateUtil.timeInfo(vo.getSchEndTime2()));
			vo.setUserId(jwtVerification.getTokenUserName(request));
			
			
			String meesage = vo.getMode().equals("Ins") ? "sucess.common.insert" : "sucess.common.update";
			SchduleInfo info = schService.updateSchduleInfoManage(vo);
			
			
			if (info != null){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));
				model.addObject(Globals.STATUS_REGINFO, info);
						
			}else {
				throw new Exception();
			}
			
		}catch (NullPointerException e){
			log.debug("updateequpInfoManage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));			
			
		}catch (Exception e){
			log.debug("updateequpInfoManage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));			
			
		}finally {
			return model;	
		}
	}
	
}
