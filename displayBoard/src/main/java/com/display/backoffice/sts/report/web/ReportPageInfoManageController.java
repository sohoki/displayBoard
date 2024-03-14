package com.display.backoffice.sts.report.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import egovframework.com.cmm.AdminLoginVO;
import com.display.backoffice.sym.monter.service.DetailPageInfoManageService;
import com.display.backoffice.sym.monter.models.DetailPageInfoVO;
import com.display.backoffice.sts.cnt.models.ContentFileInfo;
import com.display.backoffice.sts.cnt.service.ContentFileInfoManageService;
import com.display.backoffice.sts.cnt.web.FileUpladController;
import com.display.backoffice.sts.report.models.ReportPageInfo;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;
import com.display.backoffice.sts.report.service.ReportPageInfoManageService;
import com.display.backoffice.uat.models.UniUtilInfo;
import com.display.backoffice.uat.service.UniUtilManageService;
import com.display.backoffice.util.web.service.fileService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import com.display.backoffice.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;


@RestController
@RequestMapping("/backoffice/contentManage")
public class ReportPageInfoManageController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportPageInfoManageController.class);
	
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
    protected ReportPageInfoManageService reportService;
	
	@Autowired
	private EgovCcmCmmnDetailCodeManageService cmmnDetailService;
	
	@Autowired
	private DetailPageInfoManageService detailService;
	
	@Autowired
	private UniUtilManageService uniUtil;
	
    @Autowired
	private ContentFileInfoManageService conFileService;
	
    @Resource(name="egovFileIdGnrService")
	private EgovIdGnrService egovFileIdGnrService;   
    
    fileService uploadFile = new fileService();
	
	
	@RequestMapping(value="pageInfoList.do")
	public ModelAndView  selectEqupInfoManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
														, @ModelAttribute("searchVO") ReportPageInfoVO searchVO
														, HttpServletRequest request
														, BindingResult bindingResult	) throws Exception {
		 ModelAndView model = new ModelAndView("/backoffice/contentManage/PageInfoList");
		 Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	     if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE , egovMessageSource.getMessage("fail.common.login"));
	    		model.addObject(Globals.STATUS , Globals.STATUS_LOGINFAIL);
	    		model.setViewName("/backoffice/login");
	    		return model;
	     }
         model.addObject("selectCodeDM", cmmnDetailService.selectCmmnDetailCombo("reportGubun"));  
		 model.addObject("regist", searchVO);
		
		return model;	
	}
	@RequestMapping(value="pageReportInfoList.do")
	public ModelAndView pageInfoListInfo (@ModelAttribute("loginVO") AdminLoginVO loginVO
			                                               , @RequestBody  ReportPageInfoVO searchVO
			                                               , HttpServletRequest request
			                                    		   , BindingResult bindingResult )throws Exception {
		
		        ModelAndView model = new ModelAndView( );
				
				//공용 확인 하기 
			    Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		        if(!isAuthenticated) {
		    		model.addObject(Globals.STATUS_MESSAGE , egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject(Globals.STATUS , Globals.STATUS_LOGINFAIL );
		    		return model;
		    		
		        }else {
			    	 HttpSession httpSession = request.getSession(true);
			    	 loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
				     searchVO.setAdminLevel(loginVO.getAdminLevel());
				     searchVO.setPartId(loginVO.getPartId());
		        }
		        if( searchVO.getPageUnit() > 0  ){    	   
			    	   searchVO.setPageUnit(searchVO.getPageUnit());
				}else {
						 searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
				}
				searchVO.setPageSize(propertiesService.getInt("pageSize"));
			    if  (searchVO.getReportUseYn() == null) { searchVO.setReportUseYn("");}
			    
			    model =  reportService.selectReportPageInfoManageListByPagination(searchVO);
			    model.setViewName(Globals.JSONVIEW);
			    return model;
		
	}
	//파일 업로드 파일 올리기
	@RequestMapping (value="fileIupload.do")
	public ModelAndView fileUplad(@ModelAttribute("loginVO") AdminLoginVO loginVO			
												, HttpServletRequest request
												, BindingResult bindingResult) throws Exception{			
		ModelAndView model = new ModelAndView("/backoffice/contentManage/FileUpload");
		return model;		
	}
	@RequestMapping (value="createPage.do")
	public ModelAndView createPage(@ModelAttribute("loginVO") AdminLoginVO loginVO			
							, HttpServletRequest request
							, BindingResult bindingResult) throws Exception{	
		ModelAndView model = new ModelAndView("/backoffice/popup/createPage");
		return model;		
	}
	
	@RequestMapping (value="pageInfoView.do")
	public ModelAndView selecEqupInfoManageView(@ModelAttribute("loginVO") AdminLoginVO loginVO
                                                ,@RequestBody  ReportPageInfoVO vo
                                                , HttpServletRequest request
                                    			, BindingResult bindingResult ) throws Exception{	
		
		
		ModelAndView model = new ModelAndView(Globals.JSONVIEW);		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
    		model.addObject("status", "LOGIN FAIL");
    		return model;
        }
        
        model.addObject("status", Globals.STATUS_SUCCESS);
        ReportPageInfoVO report = reportService.selectReportPageInfoManageDetail(vo.getReportSeq());
        model.addObject("reportInfo", report);
		return model;
	}
	//삭제 확인
	@RequestMapping (value="reportDelete.do", method=RequestMethod.POST)
	public ModelAndView deleteReportInfoManage(@ModelAttribute("loginVO") AdminLoginVO loginVO,
			                                                           @RequestParam("reportSeqDel") String reportSeqDel)  throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSONVIEW);
		
		try{
			detailService.updateDisplayPageChangeInfo(reportSeqDel);			
			model.addObject("status", Globals.STATUS_SUCCESS);
			return model;
		}catch(NullPointerException e){
			model.addObject("status", Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
			return model;
		}catch(Exception e){
			model.addObject("status", Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));
			return model;
		}
	}
	
	@RequestMapping (value="reportPreView.do")
	public ModelAndView screenUrlPagePreview(HttpServletRequest request) throws Exception{
		
		ModelAndView model = new ModelAndView("/backoffice/contentManage/preView");
		try{
			
	        String reportSeq = request.getParameter("reportSeq") != null ? request.getParameter("reportSeq") : "1";
	        model.addObject("status", Globals.STATUS_SUCCESS);
	        ReportPageInfoVO report = reportService.selectReportPageInfoManageDetail(reportSeq);
	        model.addObject("reportInfo", report);
		}catch(NullPointerException e){
			LOGGER.debug("error:" + e.toString());
		}catch(Exception e){
			LOGGER.debug("error:" + e.toString());
		}
		
		return model;
	}
	
	@RequestMapping (value="pageInfoUpdate.do", method=RequestMethod.POST)
	public ModelAndView updateequpInfoManage(@RequestBody ReportPageInfo vo	
							                                         , HttpServletRequest request                         				 
															  	     , BindingResult result) throws Exception{
		
		ModelAndView model = new 	ModelAndView(Globals.JSONVIEW);
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
    		model.addObject("status", Globals.STATUS_LOGINFAIL);
    		return model;
        }
		vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString() );
		model = reportService.updateReportPageInfoManage(vo);
		return model;
	}
	
	@RequestMapping(value = "reportUpload.do", method = RequestMethod.POST)
    public ModelAndView reportUpload( @RequestParam Map<String, String> paramMap
											    		 , HttpServletRequest request ) throws Exception{
        
		ModelAndView model = new 	ModelAndView(Globals.JSONVIEW);
		
		try {
			
			
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
		                	 model.addObject("status", Globals.STATUS_SUCCESS);
		                	 model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.update"));
		                }
		            }
	            }else {
	            	model.addObject("status", Globals.STATUS_FAIL);
	            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
	            }
            }else {
            	model.addObject("status", Globals.STATUS_FAIL);
            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
            }
            
        }catch (NullPointerException e) {
            // TODO Auto-generated catch block
        	model.addObject("status", Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
            LOGGER.error("reportUpload error:"  + e.toString());
        }  catch (Exception e) {
            // TODO Auto-generated catch block
        	model.addObject("status", Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
            LOGGER.error("reportUpload error:"  + e.toString());
        } 
		return model;
    }
	
	@RequestMapping(value = "reportCreateUpdate.do", method = RequestMethod.POST)
    public ModelAndView reportCreateUpdate(HttpServletRequest request, 
    		                                                    @RequestParam Map<String, String> paramMap) throws Exception{
        
		ModelAndView model = new 	ModelAndView(Globals.JSONVIEW);
		
		try {
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			
			String imgData = paramMap.get("imgData");// request.getParameter("imgData");
	        String reportTitle = paramMap.get("reportTitle") != null ? paramMap.get("reportTitle") : "";          
            
	        
            if (imgData.length() > 0){
            	 
    	        if(!isAuthenticated) {
    	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
    	    		model.addObject("status", Globals.STATUS_LOGINFAIL);
    	    		model.addObject("url", "/backoffice/login");
    	    		return model;
    	        }
    	        //추후 서비스로 이동
    	        String filePath = propertiesService.getString("Globals.fileStorePath");
    	        String atchFileId = egovFileIdGnrService.getNextStringId();
    	        
    	        imgData = imgData.replaceAll("data:image/jpeg;base64,", "");
	            byte[] file = Base64.decodeBase64(imgData);
	            
	            String fileNm = atchFileId+".jpg";
	            
	            ContentFileInfo vo = new ContentFileInfo();
	            
	            
    	        try (OutputStream stream = new FileOutputStream(filePath+"/PreView/"+fileNm)) {
	                stream.write(file);
	                stream.close();
	                vo.setAtchFileId(atchFileId);
	                vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString()); 
     				vo.setFileStreCours(filePath+"/PreView/");
             		vo.setStreFileNm(fileNm);
             		vo.setOrignlFileNm(fileNm);
             		vo.setFileExtsn("jpg");  
             		vo.setReportGubun("reportGubun_3");
             		//배열 처리 문제
             		//vo.setFileSize(fileSize(  file ));
                    vo.setFileOrder(Integer.toString( Integer.parseInt(atchFileId.replace("FILE_",""))));
                    
                    
                    LOGGER.error("ContentFileInfo:"  + vo.toString());

                    
					conFileService.insertFileManage(vo);
	               	model.addObject("status", Globals.STATUS_SUCCESS);
	               	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.insert"));
	            }catch(NullPointerException e1){
	            	LOGGER.error("reportUpload error:"  + e1.toString());
	             	model.addObject("status", Globals.STATUS_FAIL);
	            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));
	            }catch(Exception e1){
	            	LOGGER.error("reportUpload error:"  + e1.toString());
	             	model.addObject("status", Globals.STATUS_FAIL);
	            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));
	            }
            }else {
            	model.addObject("status", Globals.STATUS_FAIL);
            	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
            }
            
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
        	model.addObject("status", Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
            LOGGER.error("reportUpload error:"  + e.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
        	model.addObject("status", Globals.STATUS_FAIL);
        	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.insert"));	
            LOGGER.error("reportUpload error:"  + e.toString());
        } 
		return model;
    }
	public String fileSize(File f){
		String fileSize = "";
		if (f.exists()){
			fileSize =  Long.toString(f.length());
		}else {
			fileSize = "0";
		}
		return fileSize;
	}
	
	
}
