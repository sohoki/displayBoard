package com.display.backoffice.util.service;

import java.io.File;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	public static synchronized boolean   deleteFile (String filePath, String fileNm ) {
    	try{  
    		String file_Path = filePath + fileNm;
    		log.debug("file_Path:" +file_Path);
    		File file = new File(file_Path);
    		
	        if( file.exists() ){
	            if(file.delete()){
	            	log.debug("파일삭제 성공");
	            }else{
	            	log.debug("파일삭제 실패");
	            }
	        }else{
	        	log.debug("파일이 존재하지 않습니다.");
	        }
    		return true;    		
    	}catch(Exception e){
    		log.debug("file Delete error{0}", e.toString());
    		return false;
    	}    	
    }
}
