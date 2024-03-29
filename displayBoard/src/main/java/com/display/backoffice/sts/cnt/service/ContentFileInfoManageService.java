package com.display.backoffice.sts.cnt.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.display.backoffice.bas.code.mapper.EgovCmmnCodeManageMapper;
import com.display.backoffice.bas.code.web.EgovCcmCmmnDetailCodeManageController;
import com.display.backoffice.sts.cnt.mapper.ContentFileManagerMapper;
import com.display.backoffice.sts.cnt.models.ContentFileInfo;
import com.display.backoffice.sts.cnt.models.ContentFileInfoVO;
import com.display.backoffice.util.service.fileService;

import egovframework.com.cmm.service.Globals;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service("ContentFileInfoManageService")
public class ContentFileInfoManageService {

	private final ContentFileManagerMapper conFileManager;
	
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
	private EgovIdGnrService egovFileIdGnrService;   
	
	
	
	public List<ContentFileInfoVO> selectFilePageListByPagination(
			ContentFileInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFilePageListByPagination(searchVO);
	}

	
	public int selectFilePageListByPaginationTotCnt_S(ContentFileInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFilePageListByPaginationTotCnt_S(searchVO);
	}

	
	public int selectFileListTotCnt_S(String conSeq) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFileListTotCnt_S(conSeq);
	}

	
	public ContentFileInfoVO selectFileDetail(String atchFileId)
			throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.selectFileDetail(atchFileId);
	}

	
	public int insertFileManage(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.insertFileManage(vo);
	}

	
	public int updateFileManage(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.updateFileManage(vo);
	}

	
	public int updateFileManageUseYn(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.updateFileManageUseYn(vo);
	}

	
	public int deleteFileManage(String atchFileId) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.deleteFileManage(atchFileId);
	}

	
	public List<ContentFileInfo> fileDelInfo(List reportSeq) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.fileDelInfo(reportSeq);
	}

	
	public int updateFileDetailInfo(ContentFileInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.updateFileDetailInfo(vo);
	}
    //신규 생성
	
	public int deleteFileListManage(List report_Seq) throws Exception {
		// TODO Auto-generated method stub
		return conFileManager.deleteFileListManage (report_Seq);
	}

	
	public Map fileUpload( MultipartHttpServletRequest multipartRequest) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		try{
			
			Iterator<String> itr = multipartRequest.getFileNames();
			
			fileService fileserice = new fileService();
			String inDate   = new java.text.SimpleDateFormat("yyyyMM").format(new java.util.Date());
			String file_path =  propertiesService.getString("Globals.fileStorePath")+"/"+inDate;
			while (itr.hasNext()) { //받은 파일들을 모두 돌린다.
				MultipartFile mpf = multipartRequest.getFile(itr.next());      
                String originalFilename = mpf.getOriginalFilename(); //파일명
                
                String Ext = "";        	      
                
                try {
                	//디렉톨 생성 여부 확인 
                	
                	            	
                	File filedir = new File(file_path);
                	
                	if (!filedir.isDirectory()){
                		filedir.mkdir();
                	}
                	filedir =null;
                    //파일 저장
                	
                	
                	log.debug("originalFilename:" + originalFilename);
                	
                	File file_s =  new File(file_path+"/" + originalFilename );            	
                	Ext = fileserice.fileExt(file_s,".");  
                	log.debug("Ext:" + Ext + " file_s:" + file_s.toString() );
                	
                	String atchFileId = egovFileIdGnrService.getNextStringId();
                	file_s = fileserice.rename(file_s, atchFileId+"."+Ext, file_path);            	
                    mpf.transferTo(file_s); //파일저장 실제로는 service에서 처리                
                    
                    
                    
                    // DB 에 저장
                    //동영상 파일시 썸네일 파일 생성 
                    
                    
                    ContentFileInfo vo = new ContentFileInfo();
                    
                    ContentFileInfoVO contentFileInfoVO = new ContentFileInfoVO();
                    contentFileInfoVO.setStreFileNm(file_s.getName());
                    contentFileInfoVO.setOrignlFileNm(originalFilename);
                    contentFileInfoVO.setSearchKeyword(file_s.getName());
                    contentFileInfoVO.setSearchCondition("atchFileId");
                    contentFileInfoVO.setMediaType(fileserice.modifyExtension(originalFilename));
                    
                    
                    if (contentFileInfoVO.getMediaType().equals("MEDIA") ){
                    	
                    	Map<String, String> videoInfo =fileserice.getImageFromFrame(file_s.toString(),  file_path);
                        
                        if (videoInfo.size() > 0){
                       	    vo.setFileThumnail(videoInfo.get("thumnail")  );
                       	    vo.setPlayTime(videoInfo.get("duration"));
                       	    vo.setFileWidth(videoInfo.get("fileWidth"));
                       	    vo.setFileHeight(videoInfo.get("fileHeight"));
                       	    vo.setFileSize(videoInfo.get("fileSize") );
                        }
                        videoInfo.clear();
                        
                	}else if (contentFileInfoVO.getMediaType().equals("IMAGE")){
                		//이미지 width , height 정리 하기 
                        Map<String, String> imageInfo =fileserice.getImageSize(file_s);
                        
                        if (imageInfo.size() > 0){
                       	    vo.setFileWidth(imageInfo.get("fileWidth"));
                       	    vo.setFileHeight(imageInfo.get("fileHeight"));
                       	    vo.setFileSize(imageInfo.get("fileSize") );
                        }
                        imageInfo.clear();
                	}
                    vo.setAtchFileId(atchFileId);                    
                   
    				vo.setFileStreCours(file_path+"/");
            		vo.setStreFileNm(file_s.getName());
            		vo.setOrignlFileNm(originalFilename);
            		vo.setFileExtsn(fileserice.fileExt(file_s,"."));      
                    vo.setFileOrder( Integer.toString( Integer.parseInt(atchFileId.replace("FILE_",""))));
                   
                    
                   if(contentFileInfoVO.getMediaType().equals("MEDIA")){
                    	contentFileInfoVO.setNotConType("MUSIC");
                    	vo.setReportGubun("reportGubun_4");
                   }else if (contentFileInfoVO.getMediaType().equals("IMAGE")){
                    	contentFileInfoVO.setNotConType("MUSIC");
                    	vo.setReportGubun("reportGubun_3");
                   }
                 
                   file_s = null;
                   
                   if(conFileManager.selectFilePageListByPaginationTotCnt_S(contentFileInfoVO) < 1) {
                	    conFileManager.insertFileManage(vo);
                	    
                    	map.put(Globals.STATUS_MESSAGE, "success");
                    	map.put("originalFilename", originalFilename);
                   } else {
                    	map.put(Globals.STATUS_MESSAGE, "fail");
                    	map.put("originalFilename", originalFilename);
                   }
                   contentFileInfoVO = null;
                } catch (NullPointerException e) {                
                	log.error("fileUpload error:" + e.toString());
                    map.put(Globals.STATUS_MESSAGE, "fail");
                }	catch (Exception e) {                
                	log.error("fileUpload error:" + e.toString());
                    map.put(Globals.STATUS_MESSAGE, "fail");
                }				
			}
			
			fileserice = null;
		}catch (NullPointerException e1){
			  log.error("fileUpload error:" + e1.toString());
              map.put(Globals.STATUS_MESSAGE, "fail");
		}catch (Exception e1){
			  log.error("fileUpload error:" + e1.toString());
              map.put(Globals.STATUS_MESSAGE, "fail");
		}
		return map;
	}
}
