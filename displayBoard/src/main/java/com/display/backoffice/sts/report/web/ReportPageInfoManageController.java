package com.display.backoffice.sts.report.web;

import java.io.FileOutputStream;

import java.io.OutputStream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;
import com.display.backoffice.sym.monter.service.DetailPageInfoManageService;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.UtilInfoService;
import com.display.backoffice.util.service.fileService;
import com.display.backoffice.sts.cnt.models.ContentFileInfo;
import com.display.backoffice.sts.cnt.service.ContentFileInfoManageService;
import com.display.backoffice.sts.report.models.ReportPageInfo;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;
import com.display.backoffice.sts.report.service.ReportPageInfoManageService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Api(tags = {"Report 정보 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/content/resport")
public class ReportPageInfoManageController {

	
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
	private DetailPageInfoManageService detailService;
	
	@Autowired
	private UniUtilManageService uniUtil;
	
    @Autowired
	private ContentFileInfoManageService conFileService;
    
    @Autowired
    private ReportPageInfoManageService reportService;
    
    @Resource(name="egovFileIdGnrService")
	private EgovIdGnrService egovFileIdGnrService; 
	
    /** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
    
    fileService uploadFile = new fileService();
	
	
	
	@PostMapping(value="pageReportInfoList.do")
	public ModelAndView pageInfoListInfo (@RequestBody  ReportPageInfoVO searchVO
										, HttpServletRequest request)throws Exception {
		
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try {
			
	        if (!jwtVerification.isVerification(request)) {
        		ResultVO resultVO = new ResultVO();
    			return jwtVerification.handleAuthError(resultVO); // 토큰 확
        	}else {
        		//여기 부분 수정 
        		String[] userInfo = jwtVerification.getTokenUserInfo(request);
        		searchVO.setAdminLevel( userInfo[2]);
			    searchVO.setPartId( userInfo[3]);
        	}
	        
	        searchVO.setPageUnit(UtilInfoService.NVL(searchVO.getPageUnit(), pageUnitSetting) );
			searchVO.setPageSize(UtilInfoService.NVL(searchVO.getPageSize(), pageSizeSetting) );
			searchVO.setPageIndex(UtilInfoService.NVL(searchVO.getPageIndex(), 1) );
			
			
	        /** pageing */       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			searchVO.setReplacePath(filePath);
			
	        List<ReportPageInfoVO> list = reportService.selectReportPageInfoManageListByPagination(searchVO);
	        model.addObject(Globals.JSON_RETURN_RESULT_LIST,  list );
		    int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
		    paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject(Globals.PAGE_INFO, paginationInfo);
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
	
	@PostMapping (value="pageInfoView.do")
	public ModelAndView selecEqupInfoManageView(@RequestBody  ReportPageInfoVO vo
                                                , HttpServletRequest request) throws Exception{	
		
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		if (!jwtVerification.isVerification(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확
    	}
        model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
        ReportPageInfoVO report = reportService.selectReportPageInfoManageDetail(vo.getReportSeq());
        model.addObject(Globals.JSON_RESULT_REPORT, report);
		return model;
	}
	//삭제 확인
	@RequestMapping (value="reportDelete.do", method=RequestMethod.POST)
	public ModelAndView deleteReportInfoManage(@RequestParam("reportSeqDel") String reportSeqDel
											, HttpServletRequest request)  throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			int ret = detailService.updateDisplayPageChangeInfo(reportSeqDel);	
			if (ret > 0){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("sucess.common.delete"));
			}else {
				throw new Exception();
			}
			
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			return model;
		}catch(NullPointerException e){
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
			return model;
		}catch(Exception e){
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
			return model;
		}
	}
	
	@RequestMapping (value="preview/{reportSeq}.do")
	public ModelAndView screenUrlPagePreview(@PathVariable String reportSeq, 
											HttpServletRequest request) throws Exception{
		
		ModelAndView model = new ModelAndView("/backoffice/contentManage/preView");
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
	        
	        model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
	        ReportPageInfoVO report = reportService.selectReportPageInfoManageDetail(reportSeq);
	        model.addObject(Globals.JSON_RESULT_REPORT, report);
		}catch(NullPointerException e){
			log.debug("error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));
		}catch(Exception e){
			log.debug("error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));
		}
		
		return model;
	}
	
	@PostMapping (value="pageInfoUpdate.do")
	public ModelAndView updateequpInfoManage(@RequestBody ReportPageInfo vo	
											, HttpServletRequest request) throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		vo.setUserId(jwtVerification.getTokenUserName(request));
	    	}
			
			int ret = reportService.updateReportPageInfoManage(vo);
			if (ret > 0) {
	    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));
	    	}
	    	else {
	    		model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));
	    	}
		}catch(Exception e){
			log.error("updateReportPageInfoManage ERROR:" + e.toString() );
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
		}
		return model;
		
		
	}
	
	@GetMapping(value = "reportUpload.do")
    public ModelAndView reportUpload(@RequestParam Map<String, String> paramMap
									 , HttpServletRequest request ) throws Exception{
        
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		
		try {
			
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			
			String filePath = propertiesService.getString("Globals.fileStorePath") ;   
            String imgData = paramMap.get("imgData");
            String reportPreview = paramMap.get("reportPreview")!= null ? paramMap.get("reportPreview") : "";
            String reportSeq = paramMap.get("reportSeq") != null ? paramMap.get("reportSeq") : "";
            
            if (!reportPreview.equals("")){
            	//전 파일 삭제 
            	uploadFile.deleteFile(filePath+"/PreView/"+reportPreview, filePath+"/PreView");
            }
            
            
            if (imgData.length() > 0){
	            imgData = imgData.replaceAll("data:image/jpeg;base64,", "");
	            
	            
	            byte[] file = Base64.decodeBase64(imgData);
	            String fileNm = reportSeq + "_"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+".jpg";
	            
	            boolean pathCheck = uploadFile.pathExist(filePath+"/PreView");
	            if (pathCheck == true){
	            	try (OutputStream stream = new FileOutputStream(filePath+"/PreView/"+fileNm)) {
		                stream.write(file);
		                stream.close();
		                
		                ReportPageInfo vo = new ReportPageInfo();
		                vo.setReportPreview(fileNm);
		                vo.setReportSeq(reportSeq);
		                    
		                
		                if (reportService.updateReportPreviewInfoManage(vo) >  0){
		                	 model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		                	 model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.update"));
		                }
		            }
	            }else {
	            	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
	            }
            }else {
            	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
            }
            
        }catch (NullPointerException e) {
            // TODO Auto-generated catch block
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
            log.error("reportUpload error:"  + e.toString());
        }  catch (Exception e) {
            // TODO Auto-generated catch block
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
        	log.error("reportUpload error:"  + e.toString());
        } 
		return model;
    }
	
	@GetMapping(value = "reportCreateUpdate.do")
    public ModelAndView reportCreateUpdate(HttpServletRequest request, 
    		                                @RequestParam Map<String, String> paramMap) throws Exception{
        
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		
		try {
			
			String imgData = paramMap.get("imgData");// request.getParameter("imgData");
	        String reportTitle = paramMap.get("reportTitle") != null ? paramMap.get("reportTitle") : "";          
            
	        
            if (imgData.length() > 0){
            	 
            	if (!jwtVerification.isVerification(request)) {
    	    		ResultVO resultVO = new ResultVO();
    				return jwtVerification.handleAuthError(resultVO); // 토큰 확
    	    	}
    			
    	        //추후 서비스로 이동
    	       
    	        String atchFileId = egovFileIdGnrService.getNextStringId();
    	        
    	        imgData = imgData.replaceAll("data:image/jpeg;base64,", "");
	            byte[] file = Base64.decodeBase64(imgData);
	            
	            String fileNm = atchFileId+".jpg";
	            
	            ContentFileInfo vo = new ContentFileInfo();
	            
	            try (OutputStream stream = new FileOutputStream(filePath+"/PreView/"+fileNm)) {
	                stream.write(file);
	                stream.close();
	                vo.setAtchFileId(atchFileId);
	                vo.setUserId(jwtVerification.getTokenUserName(request)); 
     				vo.setFileStreCours(filePath+"/PreView/");
             		vo.setStreFileNm(fileNm);
             		vo.setOrignlFileNm(fileNm);
             		vo.setFileExtsn("jpg");  
             		vo.setReportGubun("reportGubun_3");
             		//배열 처리 문제
             		//vo.setFileSize(fileSize(  file ));
                    vo.setFileOrder(Integer.toString( Integer.parseInt(atchFileId.replace("FILE_",""))));
                    
                    log.error("ContentFileInfo:"  + vo.toString());

                    
					conFileService.insertFileManage(vo);
	               	model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
	               	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.insert"));
	            }catch(NullPointerException e1){
	            	log.error("reportUpload error:"  + e1.toString());
	             	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));
	            }catch(Exception e1){
	            	log.error("reportUpload error:"  + e1.toString());
	             	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
	            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));
	            }
            }else {
            	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
            }
            
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
        	log.error("reportUpload error:"  + e.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
        	log.error("reportUpload error:"  + e.toString());
        } 
		return model;
    }	
}
