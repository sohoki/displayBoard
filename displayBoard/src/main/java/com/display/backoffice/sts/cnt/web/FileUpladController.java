package com.display.backoffice.sts.cnt.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.Globals;
import com.display.backoffice.sts.cnt.models.ContentFileInfo;
import com.display.backoffice.sts.cnt.service.ContentFileInfoManageService;
import com.display.backoffice.sts.cnt.models.ContentFileInfoVO;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
//thumnail 관련 작업
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.scale.AWTUtil;
//동영상 이미지 캡처ㅜ
//mp3
import org.tritonus.share.sampled.file.TAudioFileFormat;

@Controller
public class FileUpladController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUpladController.class);
	
	/** EgovPropertyService */
    
	    
    @Resource(name="ContentFileInfoManageService")
	private ContentFileInfoManageService conFileService;
    
    
	@RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
    public String dragAndDrop(Model model) {         
        return "fileUpload";         
    }
	
	/*
	@RequestMapping(value = "/backoffice/upload/fileUpload.do") //ajax에서 호출하는 부분
	@ResponseBody
    public Map upload(MultipartHttpServletRequest multipartRequest
    		          , HttpServletRequest request) throws Exception { 
		
			   Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	           if(!isAuthenticated) {
	        	   Map<String, String> map = new HashMap<String, String>();
	               map.put(Globals.STATUS_MESSAGE, "fail Login Error");
	               return map;
	           }else {
	               return conFileService.fileUpload(multipartRequest);
	           }	        	        
    }	
    */
	@RequestMapping(value="/backoffice/popup/fileView.do")
	public String selectFileView (@ModelAttribute("AdminLoginVO") LoginVO loginVO
			                                   , ContentFileInfoVO vo
								               , HttpServletRequest request
											   , BindingResult bindingResult
											   , ModelMap model) throws  Exception{			
		   String atchFileId = request.getParameter("atchFileId") != null ? request.getParameter("atchFileId") : "";
		   model.addAttribute("regist",  conFileService.selectFileDetail(atchFileId));				
		   return "/backoffice/popup/FileView";
	}
	@RequestMapping(value="/backoffice/sub/conManage/conFileUpdate.do")
	@ResponseBody
	public String conMutiDetail ( @ModelAttribute("AdminLoginVO") LoginVO loginVO
								  , ContentFileInfoVO vo
					              , HttpServletRequest request
								  , BindingResult bindingResult
								  , ModelMap model)throws Exception{
		String atchFileId = request.getParameter("atchFileId") != null ? request.getParameter("atchFileId") : "";
		String playTime = request.getParameter("playTime") != null ? request.getParameter("playTime") : "";
		String fileWidth = request.getParameter("fileWidth") != null ? request.getParameter("fileWidth") : "";
		String fileHeight = request.getParameter("fileHeight") != null ? request.getParameter("fileHeight") : "";
		
		
		vo.setAtchFileId(atchFileId);
		if (!playTime.equals("") && playTime != null){
		    vo.setPlayTime(Integer.toString((int)( Double.parseDouble( playTime))));
		}
		vo.setFileWidth(fileWidth);
		vo.setFileHeight(fileHeight);
		String delResult = "";	
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
        	//delResult="F";
        	
        	vo.setUserId("not user");
        }
    	vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString());
    	
		
		int ret = conFileService.updateFileDetailInfo(vo) ;
		if (ret > 0){
			delResult="O";   	
		}else {
			delResult="F";
		}		
		return delResult; 
		
		
	}
	
}
