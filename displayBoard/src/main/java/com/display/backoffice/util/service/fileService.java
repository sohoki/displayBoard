package com.display.backoffice.util.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.DemuxerTrack;
import org.jcodec.common.NIOUtils;
import org.jcodec.common.SeekableByteChannel;
import org.jcodec.common.model.Picture;
import org.jcodec.containers.mp4.demuxer.MP4Demuxer;
import org.jcodec.scale.AWTUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class fileService {

	public String fileSize(File f){
		  return f.exists() == true ?  Long.toString(f.length()) : "0"; 			
	}
	// 파일 확장자 변환
	public String modifyExtension(String extension){
	    	//기존
	    	/*String[] type = extension.split("\\.")[1].toString().toLowerCase();*/
	    	
	    	//2019.12.10_JDH
	    	//이마트 부분과 달리 해당부분에서 IMAGE MEDIA부분을 구분 못할경우 reportGubun컬럼이 null값이 들어가 무결성위반 에러 발생
	    	String[] typeSplit = extension.split("\\.");
	    	String type = typeSplit[typeSplit.length -1];
	    	
	    	
	    	
	    	log.info("type : " + type);
	    	if("mp4".equals(type) || "avi".equals(type) || "mpeg".equals(type) || "webm".equals(type)) {
	    		extension = "MEDIA";
			} else if("jpg".equals(type) || "jpeg".equals(type) || "gif".equals(type) || "png".equals(type) || "bmp".equals(type)) {
				extension = "IMAGE";
			} else if("mp3".equals(type) || "wav".equals(type) || "mid".equals(type)) {
				extension = "MUSIC";
			} else {
				extension = "MUSIC";
			}
	    	return extension;
    } 
	//썸네일 이미지 
    public static Map getImageFromFrame(String _fileNm, String _filePath) {
  		
    	   Map<String, String> map = new HashMap<String, String>();
  		  //aaaa
  	      /*Calendar calendar = Calendar.getInstance();
          java.util.Date date = calendar.getTime();
          String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));*/
    	  String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
          
          String thumnailFull = _filePath+ "/"+ today +"_thumnail.png";
          String thumnail = today+"_thumnail.png";
  		
  		  String fileName = _fileNm;// videoFile.getAbsolutePath();
  		  
  		  
	  	  double frameNumber = 0d;
	  	  try {
	  			File videoFile = new File(fileName);
	  			SeekableByteChannel bc = NIOUtils.readableFileChannel(videoFile);
	  			MP4Demuxer dm = new MP4Demuxer(bc);
	  			DemuxerTrack vt = dm.getVideoTrack();
	  			frameNumber = vt.getMeta().getTotalDuration() / 5.0;
	  			
	  			Metadata metadata = null;
				try {
					metadata = ImageMetadataReader.readMetadata(videoFile);
				} catch (ImageProcessingException e) {
					// TODO Auto-generated catch block
					log.debug("ImageProcessingException:" + e.toString());
				}  	  
	  	        for (Directory directory : metadata.getDirectories()) {
	  	    	    for (com.drew.metadata.Tag tag : directory.getTags()) {
	  	    	    	if (tag.getTagName().equals("Width") ){
	  	    	    		map.put("fileWidth", tag.getDescription().replace(" pixels", ""));
	  	    	    	}
	  	    	    	if (tag.getTagName().equals("Height") ){
	  	    	    		map.put("fileHeight", tag.getDescription().replace(" pixels", ""));
	  	    	    	}
	  	    	    	if (tag.getTagName().equals("Duration in Seconds") ){
	  	    	    		//초로 환산하기 
	  	    	    		String[] durationInfos = 	tag.getDescription().split(":");
	  	    	    		map.put("duration", changeTime(durationInfos[0].toString(), durationInfos[1].toString(), durationInfos[2].toString())); 
	  	    	    			
	  	    	    	}
	  	    	    	if (tag.getTagName().equals("File Size") ){
	  	    	    		map.put("fileSize", tag.getDescription().replace(" bytes", ""));
	  	    	    	}
	  	    	    }
	  	        }
	  			videoFile = null;
	  			
	  		} catch (FileNotFoundException e1) {
	  			log.debug("FileNotFoundException:" + e1.toString());
	  		} catch (IOException e) {
	  			log.debug("IOException:" + e.toString());
	  	  }
	  	  //파일 width/height 정리 하기
	  	  
	  	  
	  	  
	  	  try {
	  			Picture frame = FrameGrab.getNativeFrame(new File(fileName), frameNumber);
	  			BufferedImage img = AWTUtil.toBufferedImage(frame);
	  			File pngFile = new File(thumnailFull);
	  			if (!pngFile.exists()) {
	  				pngFile.createNewFile();
	  			}
	  			ImageIO.write(img, "png", pngFile);
	  			map.put("thumnail", thumnail);
	  			
	  			img = null;
	  			frame = null;
	  		} catch (IOException e) {
	  			log.debug("IOException:" + e.toString());
	  		} catch (JCodecException e) {
	  			log.debug("JCodecException:" + e.toString());
	  	  }
	  	  return map;

  	}
	public Map getImageSize (File _fileNm){
		Map<String, String> map = new HashMap<String, String>();
		
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(_fileNm);
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	  
	    for (com.drew.metadata.Directory directory : metadata.getDirectories()) {
		    for (com.drew.metadata.Tag tag : directory.getTags()) {
		    	if (tag.getTagName().equals("Image Width") ){
		    		map.put("fileWidth", tag.getDescription());
		    		
		    	}
		    	if (tag.getTagName().equals("Image Height") ){
		    		map.put("fileHeight", tag.getDescription());
		    	}
		    	if (tag.getTagName().equals("File Size") ){
		    		map.put("fileSize", tag.getDescription().replace(" bytes", ""));
		    	}
		    }
	    }
	    return map;   
	}
	//파일 확장자 
  	public String fileExt(File f, String _seq){
  		return f.getName().lastIndexOf(_seq) != -1 ? f.getName().substring( f.getName().lastIndexOf(_seq) + 1) : "";
  	}
  //시분초 변환 
    public static String changeTime (String hour, String min, String sec){
    	return (Integer.parseInt(hour)*3600 + Integer.parseInt(min)*60 + sec);
    }
	public  String uploadFileNm(List<MultipartFile> mpf, String filePath){
		
		String fileNm = "";
		String ext = "";
		
		try {
  	
	      	for (MultipartFile mFile : mpf) {
	      	    String originalFilename = mFile.getOriginalFilename(); //파일명	   
		            if (!originalFilename.equals("")){
		            	String fileFullPath = filePath + "/" + originalFilename;
			        	File file_s =  new File(fileFullPath);	        	
			        	 
			     	    int dot = originalFilename.lastIndexOf(".");
			     	    if (dot != -1) {                              //확장자가 없을때	     	    
			     	      ext = originalFilename.substring(dot+1);
			     	    } else {                                     //확장자가 있을때	     	    
			     	      ext = "";
			     	    }		     	    
			     	    //fileNm = UUID.randomUUID().toString().replace("-", "") +"."+ ext;	     	  
			        	file_s = rename(file_s, originalFilename, filePath);
			        	mFile.transferTo(file_s);
						fileNm = file_s.getName();	
		            }
	      	}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			log.debug("uploadFileNm IllegalStateException :" + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("uploadFileNm IOException :" + e.toString());
		} catch (Exception ex){			
			log.debug("uploadFileNm Exception :" + ex.toString());
		}        
		return fileNm;
	}
	public File rename(File f, String fileNm, String filedir) {             //File f는 원본 파일
			 
		
	    String name = f.getName();
	    String body = null;
	    String ext = null;
	    
	    
	    int dot = name.lastIndexOf(".");
	    
	    if (dot != -1) { //확장자 있을떄 
		    body  = fileNm.substring(0, fileNm.lastIndexOf(".")) ;
		    ext = fileNm.substring(fileNm.lastIndexOf("."), fileNm.length());
	    } else {  //확장자가 없을떄      
	    	body = fileNm;
		    ext = "";
	    }	
	    int count = 0;
	    //중복된 파일이 있을때
	    //파일이름뒤에 a숫자.확장자 이렇게 들어가게 되는데 숫자는 9999까지 된다.
	    
	    if ( fileExites(body +ext, filedir) && count == 0   ) {
	    	  String newName = body + ext;
		      f = new File(f.getParent(), newName);
		      log.debug("파일이 없을때  filenm:"+ newName);
	    }else {	    
		    while (!createNewFile(f) && count < 9999) {  
		      count++;
		      String newName = body+"[" + count +"]" + ext;
		      log.debug("파일이 있을때  filenm:"+ newName);
		      f = new File(f.getParent(), newName);
		    }
	    }
	    return f;
	}
	private boolean createNewFile(File f) { 
	    try {
	      return f.createNewFile();                        //존재하는 파일이 아니면
	    }catch (IOException ignored) {
	      return false;
	    }
	}	
	private boolean fileExites( String fileNm, String path){
			try{
				pathExist(path);
				
				File f = new File (path+"/"+fileNm);
				if (f.exists()){
					return false;
				}else {
					return true;
				}
			}catch (Exception e){
				log.debug("fileExites Error:" + e.toString());
				return false;
			}
			
	}
	public boolean pathExist(String path){
		try{
			File dir = new File(path);
		    if(!dir.isDirectory())
			   dir.mkdir(); //폴더 생성합니다.
			
		    return true; 
		}catch(Exception e){
			log.error("pathExist Error:" + e.toString());
			return false;
		}
	}
	//파일 삭제 
	public  boolean deleteFile (String fileNm ) {
	  	try{        	
	  		File delFile = new File ( fileNm);
	  		delFile.delete();
	  		return true;    		
	  	}catch(Exception e){
	  		log.debug("file Delete error{0}", e.toString());
	  		return false;
	  	}    	
	}
	public  boolean deleteFile (String fileNm, String path ) {
	  	try{        	
	  		pathExist(path);
	  		//path 먼저 확인 
	  		File delFile = new File ( fileNm);
	  		delFile.delete();
	  		return true;    		
	  	}catch(Exception e){
	  		log.debug("file Delete error{0}", e.toString());
	  		return false;
	  	}    	
	}
}
