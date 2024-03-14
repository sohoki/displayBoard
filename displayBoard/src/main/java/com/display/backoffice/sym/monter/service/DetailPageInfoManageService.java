package com.display.backoffice.sym.monter.service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.cnt.mapper.CenterInfoManageMapper;
import com.display.backoffice.bas.cnt.service.CenterInfoService;
import com.display.backoffice.sts.cnt.models.ContentFileInfo;
import com.display.backoffice.sts.cnt.service.ContentFileInfoManageService;
import com.display.backoffice.sym.monter.mapper.DetailPageInfoManageMapper;
import com.display.backoffice.sym.monter.mapper.DisplayPageInfoManageMapper;
import com.display.backoffice.sym.monter.models.DetailPageInfo;
import com.display.backoffice.sym.monter.models.DetailPageInfoVO;
import com.display.backoffice.sym.monter.models.DispalyPageInfo;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.FileUtil;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;

@Slf4j
@RequiredArgsConstructor
@Service
public class DetailPageInfoManageService {
	
	@Autowired
	private UniUtilManageService utilService;
	
	@Autowired
	private ContentFileInfoManageService fileService;
	
	
	private final DisplayPageInfoManageMapper dispalyMapper;
	
	private final DetailPageInfoManageMapper detailMapper;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	
	public ModelAndView  selectDetailPageInfoManageListByPagination(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			HashMap<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("displaySeq", displaySeq);
			searchMap.put("replacePath", "");
			List<Map<String, Object>> detailInfo = detailMapper.selectDetailPageInfoManageListByPagination(searchMap);
			
			model.addObject("detailInfo", detailInfo); 
			model.addObject(Globals.PAGE_TOTAL_COUNT, detailInfo.get(0).get("total_record_count").toString());
			// 여기 부분 나중에 수정 하기 
			model.addObject(Globals.PAGE_TOTAL_TIME, detailMapper.selectDetailSumTime(displaySeq));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(NullPointerException e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}	
	
	public List<DetailPageInfoVO> selectDetailPageInfoManageListByContent(DetailPageInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.selectDetailPageInfoManageListByContent(searchVO);
	}
	

	
	
	public Map<String, Object> selectDetailInfo(String detailSeq)throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.selectDetailInfo(detailSeq);
	}

	
	public int selectDetailPageInfoManageListTotCnt_S(DetailPageInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.selectDetailPageInfoManageListTotCnt_S(searchVO);
	}

	
	public int insertDetailPageInfoManage(DetailPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		//return detailMapper.insertDetailPageInfoManage(vo);		
		int ret = 0;
		try{
			detailMapper.insertDetailPageInfoManage(vo);
			ret = 1;
		}catch(Exception e){
			//System.out.println("insertDetailPageInfoManage ERROR:" + e.toString());
			ret = -1;
		}
		return ret;
	}

	
	public int updateDetailPageInfoManage(DetailPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.updateDetailPageInfoManage(vo);
	}

	
	public int updateDetailPageInfoUpManage(DetailPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.updateDetailPageInfoUpManage(vo);
	}

	
	public int updateDetailPageInfoDownManage(DetailPageInfo vo)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int deleteDetailPageInfoManage(DetailPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		/*
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		try{
			
			
			detailMapper.deleteDetailPageInfoManage(vo.getDisplaySeq(), vo.getDetailSeq() );
			model.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("success.common.delete"));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(NullPointerException e){
			model.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("success.common.delete"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			model.addObject(Globals.STATUS_MESSAGE,  egovMessageSource.getMessage("success.common.delete"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
		*/
		return detailMapper.deleteDetailPageInfoManage(vo.getDisplaySeq(), vo.getDetailSeq());
	}
	
	
	public int selectDetailMaxSort(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.selectDetailMaxSort(displaySeq);
	}

	
	public int updateDetailTimeChangeManage(DetailPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		try{
			detailMapper.updateDetailTimeChangeManage(vo);
			ret = 1;
		}catch(NullPointerException e){
			log.error("updateDetailTimeChangeManage ERROR:" + e.toString());
			ret = -1;
		}catch(Exception e){
			log.error("updateDetailTimeChangeManage ERROR:" + e.toString());
			ret = -1;
		}
		
		return ret;
	}

	
	public int updateDetailSortOrderInfoManage(DetailPageInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		try{

			ret = vo.getDetailOrder().equals("DN") ? detailMapper.updateDetailSortOrderDownSubInfoManage(vo.getDisplaySeq().toString(), vo.getDetailSeq().toString()) :
													 detailMapper.updateDetailSortOrderUpSubInfoManage(vo.getDisplaySeq().toString(), vo.getDetailSeq().toString());
			
			
			//detailMapper.updateDetailSortOrderUpDownSubInfoManage(vo.getDisplaySeq().toString(), vo.getDetailSeq().toString(), vo.getDetailOrder());
			//ret = 1;
		}catch(NullPointerException e){
			log.error("updateDetailSortOrderInfoManage ERROR:" + e.toString());
			ret = -1;
		}catch(Exception e){
			log.error("updateDetailSortOrderInfoManage ERROR:" + e.toString());
			ret = -1;
		}
				
		return ret;
	}

	
	public int selectDetailSumTime(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.selectDetailSumTime(displaySeq);
	}

	
	public List<DetailPageInfoVO> selectDisPlayPreviewList(DetailPageInfoVO searchVO)throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.selectDisPlayPreviewList(searchVO);
	}

	
	public List<Map<String, Object>> selectAgentPreviewList(DetailPageInfoVO vo)throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.selectAgentPreviewList(vo);
	}
	
	
	public int updateDitailOrderUpdatePage(List<DetailPageInfo> detailPages) throws Exception {
		// TODO Auto-generated method stub
		return detailMapper.updateDitailOrderUpdatePage(detailPages);
		/*
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			int ret = detailMapper.updateDitailOrderUpdatePage(detailPages);
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);			
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("sucess.common.update"));
		}catch (NullPointerException e){
			log.error("updateDitailOrderUpdatePage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
		}catch (Exception e){
			log.error("updateDitailOrderUpdatePage error:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
		}
		return model;
		*/
	}	

	
	public int updateDisplayPageChangeInfo(String reportSeq) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		try{
			//삭제 하기
			
			
			UniUtilInfo utilInfo = new UniUtilInfo();
			utilInfo.setInTable("TB_REPORTINFO");
			utilInfo.setInCondition("REPORT_SEQ in ( "+reportSeq+" )");
			utilService.deleteUniStatement(utilInfo);
			
			List<String> report_seq =  Arrays.asList(reportSeq.split("\\s*,\\s*"));
			List<ContentFileInfo> fileInfo = fileService.fileDelInfo(report_seq);		
			//파일 삭제
			FileUtil fileutil = new FileUtil();
			for (ContentFileInfo file_Info : fileInfo) {
				fileutil.deleteFile(file_Info.getFileStreCours(), file_Info.getStreFileNm());
			}
			List<String> disPage = (List<String>)detailMapper.selectDisplaySeqReturn(report_seq);
			ret = detailMapper.deleteDetailReportSeq(report_seq);
			if (disPage.size() > 0){
				ret = detailMapper.updateDisplayPageChangeInfo(disPage);
			}
			//fileService.deleteFileListManage(report_Seq);
			fileutil = null;
		}catch(NullPointerException e){
			log.debug("error:" + e.toString());
		}catch(Exception e){
			log.debug("error:" + e.toString());
		}
		return ret;
	}

	
	public String returnHtmlPage(String displaySeq, String fileGubun)
			throws Exception {
		
		
             String pageInfo = null;
		
             
		     StringBuilder htmlPage = new StringBuilder();
		
			try{
			
				DispalyPageInfo vo_info = dispalyMapper.selectDisplayPageInfoManageView(displaySeq);
				//기초 정보 			DISPLAY_WIDTH
				String width  = vo_info.getDisplayWidth();
				String height = vo_info.getDisplayHeight();
				String top = "0";
				String left = "0";
				
				
			    htmlPage.append("<!DOCTYPE HTML>\r\n");
				htmlPage.append("<html>\r\n");  
				htmlPage.append("<head>\r\n");    
				htmlPage.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\r\n");   
				htmlPage.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\r\n");
				
				htmlPage.append("<style>	\r\n");
				htmlPage.append("    body{\r\n");
				htmlPage.append("        margin : 0;\r\n");
				htmlPage.append("        background-color : #000;\r\n");
				htmlPage.append("    }\r\n");
				htmlPage.append("    .contentBox0{\r\n");
				htmlPage.append("        position : fixed;\r\n");
				htmlPage.append("        top : 0;\r\n");
				htmlPage.append("        width: "+width+"px; height: "+height+"px;\r\n");	
				htmlPage.append("        float : left;\r\n");
				htmlPage.append("        background-color : #000;\r\n");
				htmlPage.append("    }\r\n");
				htmlPage.append("    .contentBox0 video{\r\n");
				htmlPage.append("        width: "+width+"px; height: "+height+"px;\r\n");
				htmlPage.append("        min-width: "+width+"px; min-height: "+height+"px;\r\n");	
				htmlPage.append("    }\r\n");
				htmlPage.append("</style>	\r\n");
				htmlPage.append("<title>" + vo_info.getDisplayTitle() + "</title>\r\n");    
				htmlPage.append("</head>	\r\n");
				htmlPage.append("<body>\r\n");
				if (fileGubun.equals("L")){
					htmlPage.append("    <iframe src='./silence.mp3' allow='autoplay' id='setAudio' style='display:none'></iframe>  \r\n");
				} else {
					htmlPage.append("    <iframe src='/upload/silence.mp3' allow='autoplay' id='setAudio' style='display:none'></iframe>  \r\n");	
				}
	    		htmlPage.append("    <div id='content_show0a' class='contentBox0' style='z-index:200;'></div>\r\n");
	    		htmlPage.append("    <div id='content_show0b' class='contentBox0' style='z-index:150;'></div>\r\n");
	    		if (fileGubun.equals("L")){
					htmlPage.append("<script type='text/javascript' src='./jquery-2.2.4.min.js'></script>\r\n");	
				}else {
					htmlPage.append("<script type='text/javascript' src='/js/jquery-2.2.4.min.js'></script>\r\n");
				}
				
				htmlPage.append("<script type='text/javascript'>\r\n");
				boolean musicPOPChk = false;
				String mediaType = "";
				DetailPageInfoVO search = new DetailPageInfoVO();
				search.setDisplaySeq(displaySeq);
				search.setReplacePath(propertiesService.getString("Globals.fileStorePathReplace"));
				//IMAP 으로 변경
				List<DetailPageInfoVO>  detailList = detailMapper.selectDetailPageInfoManageListByContent(search);
				JSONArray jsonA = JSONArray.fromObject(detailList);	
				
				search = null;
				
				mediaType= detailList.get(0).getMediaType().toString();
				if(mediaType == null){
					musicPOPChk = true;
				} else {
					if(!mediaType.equals("IMAGE") && !mediaType.equals("MEDIA")){
						musicPOPChk = true;
					}else{
						musicPOPChk = false;
					}
				}
				
				htmlPage.append("    var albumLst0 = '"+ jsonA  +"';\r\n");				
				htmlPage.append("    var contentCount0 = 0;\r\n");
				htmlPage.append("    var jsonData0;\r\n");				
				htmlPage.append("    var firstPlay0, errorFlag0;\r\n");
				htmlPage.append("    var prepareFileNm0, prepareFileType0, prepareFileTime0,  prepareMakeType0, prepareFileStreCours0  ;   \r\n");
			
				// onRead 
				htmlPage.append("    $(document).ready(function(){\r\n");
				htmlPage.append("        console.log('contents play start');\r\n");
				htmlPage.append("        function startContentSch(){\r\n");
				htmlPage.append("            jsonData0 = JSON.parse(albumLst0);\r\n");
				htmlPage.append("            if(jsonData0.length > 0){\r\n ");
				htmlPage.append("                firstPlay0 = true;\r\n ");
				htmlPage.append("                tagMaking0(jsonData0[contentCount0].report_dc, jsonData0[contentCount0].mediatype, jsonData0[contentCount0].detail_time, jsonData0[contentCount0].report_url, 'A');\r\n");
				htmlPage.append("            }\r\n");
				htmlPage.append("        }\r\n");
				htmlPage.append("        startContentSch();\r\n");
				htmlPage.append("    });\r\n");
				
				htmlPage.append("    function tagMaking0(reportDc, fileType, detailTime, reportUrl, makeType){\r\n");
				htmlPage.append("        var result, mediaTag;\r\n");
					
				if (fileGubun.equals("L")){ // local file myHandler 생성부
					htmlPage.append("        if (makeType == 'A'){\r\n");
					htmlPage.append("            $('#content_show0a').html('');\r\n");
					htmlPage.append("            switch(fileType){\r\n");
					htmlPage.append("                case 'IMAGE' : result = '<img id=\"content0a\" src=\"./'+reportDc+'\" width=\""+width+"\" height=\""+height+"\" />'; break;\r\n");	
					
					htmlPage.append("                case 'MEDIA' : result = '<video id=\"content0a\" src=\"./'+reportDc+'\"><source type=\"video/mp4\"/></video>'; break;\r\n");
					htmlPage.append("                case 'URL'   : result = '<iframe src=\"'+reportUrl+'\" width=\""+width+"\" height=\""+height+"\" marginheight=\"0\" marginwidth=\"0\" frameborder=\"0\" scrolling=\"auto\"/>'; break;\r\n");
					htmlPage.append("                default      : result = '<audio id=\"content0a\" controls src=\"./'+reportDc+'\"><source type=\"audio/mpeg\"/></audio>'; break;\r\n");
					htmlPage.append("            }\r\n");
					htmlPage.append("            mediaTag = $('#content0a');\r\n");
					htmlPage.append("            $('#content_show0a').html(result);\r\n");
				    htmlPage.append("        } else {\r\n");
					htmlPage.append("            $('#content_show0b').html('');\r\n");
					htmlPage.append("            switch(fileType){\r\n");
					htmlPage.append("                case 'IMAGE' : result = '<img id=\"content0b\" src=\"./'+reportDc+'\" width=\""+width+"\" height=\""+height+"\" />'; break;\r\n");	
					
					htmlPage.append("                case 'MEDIA' : result = '<video id=\"content0b\" src=\"./'+reportDc+'\"><source type=\"video/mp4\"/></video>'; break;\r\n");
					htmlPage.append("                case 'URL'   : result = '<iframe src=\"'+reportUrl+'\" width=\""+width+"\" height=\""+height+"\" marginheight=\"0\" marginwidth=\"0\" frameborder=\"0\" scrolling=\"auto\" />'; break;\r\n");
					htmlPage.append("                default      : result = '<audio id=\"content0b\" controls src=\"./'+reportDc+'\"><source type=\"audio/mpeg\"/></audio>'; break;\r\n");
					htmlPage.append("            }\r\n");
					htmlPage.append("            mediaTag = $('#content0b');\r\n");
					htmlPage.append("            $('#content_show0b').html(result);\r\n");
				    htmlPage.append("        }\r\n");
				}else {
					// 화면생성, 미리보기 생성부
					htmlPage.append("        if (makeType == 'A'){\r\n");
					htmlPage.append("            $('#content_show0a').html('');\r\n");
					htmlPage.append("            switch(fileType){\r\n");
					htmlPage.append("                case 'IMAGE' : result = '<img id=\"content0a\" src=\"'+reportUrl+reportDc+'\" width=\""+width+"\" height=\""+height+"\" />'; break;\r\n");	
					htmlPage.append("                case 'MEDIA' : result = '<video id=\"content0a\" src=\"'+reportUrl+reportDc+'\"><source type=\"video/mp4\"/></video>'; break;\r\n");
					htmlPage.append("                case 'URL'   : result = '<iframe src=\"'+reportUrl+'\" width=\""+width+"\" height=\""+height+"\" marginheight=\"0\" marginwidth=\"0\" frameborder=\"0\" scrolling=\"auto\" />'; break;\r\n");
					htmlPage.append("                default      : result = '<audio id=\"content0a\" controls src=\"'+reportUrl+reportDc+'\"><source type=\"audio/mpeg\"/></audio>'; break;\r\n");
					htmlPage.append("            }\r\n");
					htmlPage.append("            mediaTag = $('#content0a');\r\n");
					htmlPage.append("            $('#content_show0a').html(result);\r\n");
				    htmlPage.append("        } else {\r\n");
					htmlPage.append("            $('#content_show0b').html('');\r\n");
					htmlPage.append("            switch(fileType){\r\n");
					htmlPage.append("                case 'IMAGE' : result = '<img id=\"content0b\" src=\"'+reportUrl+reportDc+'\" width=\""+width+"\" height=\""+height+"\" />'; break;\r\n");	
					htmlPage.append("                case 'MEDIA' : result = '<video id=\"content0b\" src=\"'+reportUrl+reportDc+'\"><source type=\"video/mp4\"/></video>'; break;\r\n");
					htmlPage.append("                case 'URL'   : result = '<iframe src=\"'+reportUrl+'\" width=\""+width+"\" height=\""+height+"\" marginheight=\"0\" marginwidth=\"0\" frameborder=\"0\" scrolling=\"auto\"/>'; break;\r\n");
					htmlPage.append("                default      : result = '<audio id=\"content0b\" controls src=\"'+reportUrl+reportDc+'\"><source type=\"audio/mpeg\"/></audio>'; break;\r\n");
					htmlPage.append("            }\r\n");
					htmlPage.append("            mediaTag = $('#content0b');\r\n");
					htmlPage.append("            $('#content_show0b').html(result);\r\n");
				    htmlPage.append("        }\r\n");
				}
				htmlPage.append("        if(firstPlay0){\r\n");
			    htmlPage.append("            firstPlay0 = false;\r\n");
			    htmlPage.append("            mediaPlaySetting0(reportDc, fileType, detailTime, reportUrl, makeType);\r\n");
			    htmlPage.append("        } else if (errorFlag0){\r\n");
			    htmlPage.append("            errorFlag0 = false;\r\n");
			    htmlPage.append("            mediaPlaySetting0(reportDc, fileType, detailTime, reportUrl, makeType);\r\n");
			    htmlPage.append("        } else {\r\n");
			    htmlPage.append("            prepareFileNm0 = reportDc;\r\n");
			    htmlPage.append("            prepareFileType0 = fileType;\r\n");
			    htmlPage.append("            prepareFileTime0 = detailTime;\r\n");
			    htmlPage.append("            prepareFileStreCours0 = reportUrl;\r\n");
			    htmlPage.append("            prepareMakeType0 = makeType;\r\n");
			    htmlPage.append("        }\r\n");
				htmlPage.append("    }\r\n");			
				
				
				htmlPage.append("    function mediaPlaySetting0(reportDc, fileType, detailTime, reportUrl, makeType){\r\n");
				htmlPage.append("        var mediaTag;\r\n");
				htmlPage.append("        var contentLoadCheck = setTimeout(function(){\r\n");
				htmlPage.append("            console.log('CONTENTS LOADING ISSUE, NEXT !');\r\n");
				htmlPage.append("            nextSeqConChk0('1', makeTypeRevers(makeType));\r\n");
				htmlPage.append("        }, 3000);\r\n");
				htmlPage.append("        var contentPauseChk = setTimeout(function(){\r\n");
				htmlPage.append("            console.log('content Pause ? Play Time : ' + ((detailTime*1000)+3000));\r\n");
				htmlPage.append("            nextSeqConChk0('2', prepareMakeType0);\r\n");
				htmlPage.append("        }, ((detailTime*1000)+3000));\r\n");
				htmlPage.append("        switch(makeType){\r\n");
				htmlPage.append("            case 'A' : mediaTag = $('#content0a'); $('#content_show0b').css('z-index', '150'); break;\r\n");
				htmlPage.append("            case 'B' : mediaTag = $('#content0b'); $('#content_show0b').css('z-index', '300'); break;\r\n");
				htmlPage.append("        }\r\n");
				htmlPage.append("        if(fileType == 'IMAGE'   || fileType == 'URL'){\r\n");
				htmlPage.append("            mediaTag.each(function(){\r\n");
				htmlPage.append("                clearTimeout(contentLoadCheck);\r\n");
				htmlPage.append("                setTimeout(function(){\r\n");
				htmlPage.append("                    clearTimeout(contentPauseChk);\r\n");
				htmlPage.append("                    nextSeqConChk0('2', prepareMakeType0);\r\n");
				htmlPage.append("                }, detailTime*1000);\r\n");
				htmlPage.append("                nextSeqConChk0('1', makeTypeRevers(makeType));\r\n");
				htmlPage.append("            });\r\n");
				htmlPage.append("        } else {\r\n");
				htmlPage.append("            mediaTag[0].autoplay = true;\r\n");
				if (fileGubun.equals("L")){
					htmlPage.append("            mediaTag[0].src = './' + reportDc + '?autoplay=1';\r\n");	
				} else {
					htmlPage.append("            mediaTag[0].src = reportUrl+reportDc + '?autoplay=1';\r\n");
				}
				htmlPage.append("            mediaTag.on('loadeddata', function(){\r\n");
				htmlPage.append("                clearTimeout(contentLoadCheck);\r\n");
				htmlPage.append("                nextSeqConChk0('1', makeTypeRevers(makeType));\r\n");
				htmlPage.append("            });\r\n");
				htmlPage.append("            mediaTag.on('ended', function(){\r\n");
				htmlPage.append("                clearTimeout(contentPauseChk);\r\n");
				htmlPage.append("                nextSeqConChk0('2', prepareMakeType0);\r\n");
				htmlPage.append("            });\r\n");
				htmlPage.append("        }\r\n");
				htmlPage.append("        mediaTag.on('error', function(err){\r\n");
				htmlPage.append("            clearTimeout(contentLoadCheck);\r\n");
				htmlPage.append("            clearTimeout(contentPauseChk);\r\n");
				htmlPage.append("            errorFlag0 = true;\r\n");
				htmlPage.append("            nextSeqConChk0('1', makeType);\r\n");
				htmlPage.append("        });\r\n");
			    htmlPage.append("    }\r\n");
			    htmlPage.append("    function nextSeqConChk0(checkType, makeType){\r\n");
			    htmlPage.append("        if(contentCount0 == jsonData0.length){\r\n");
			    htmlPage.append("            location.href = document.location.href;\r\n");
			    htmlPage.append("        } else {\r\n");
			    htmlPage.append("            if(checkType == '1'){\r\n");
			    htmlPage.append("                contentCount0++;\r\n");
			    htmlPage.append("                if(contentCount0 != jsonData0.length){\r\n");
			    htmlPage.append("                    tagMaking0(jsonData0[contentCount0].report_dc, jsonData0[contentCount0].mediatype, jsonData0[contentCount0].detail_time,  jsonData0[contentCount0].report_url, makeType);\r\n");
			    htmlPage.append("                }\r\n");
			    htmlPage.append("            } else {\r\n");
			    htmlPage.append("                    mediaPlaySetting0(prepareFileNm0, prepareFileType0, prepareFileTime0, prepareFileStreCours0, prepareMakeType0);\r\n");
			    htmlPage.append("            }\r\n");
			    htmlPage.append("        }\r\n");
			    htmlPage.append("    }\r\n");
			    
				htmlPage.append("    function makeTypeRevers(makeType){\r\n");
			    htmlPage.append("        if(makeType == 'A'){\r\n");
			    htmlPage.append("            return 'B';\r\n");
			    htmlPage.append("        } else {\r\n");
			    htmlPage.append("            return 'A';\r\n");
			    htmlPage.append("        }\r\n");
			    htmlPage.append("    }\r\n");
				htmlPage.append("</script>\r\n");
				
				//다음 시퀀스가 있는지 없는지 확인 후 setTime값으로 하기		DISPLAY_NEXTSEQ	 DISPLAY_LOCALFILE
				if (! String.valueOf( vo_info.getDisplayNextseq()).equals("0") && ! String.valueOf( vo_info.getDisplayNextseq() ).equals("")){
					htmlPage.append("<script type='text/javascript'> \r\n");
					String page_url = "";
					DispalyPageInfo vo_infoNext = dispalyMapper.selectDisplayPageInfoManageView(String.valueOf( vo_info.getDisplayNextseq()   ));
					if (fileGubun.equals("L")){
					    page_url = vo_infoNext.getDisplayLocalfile().replace(".html", "_local.html");
					}else {
						page_url = vo_infoNext.getDisplayLocalfile();
					}
					String pageTime = vo_infoNext.getDisplayTotalTime();				
					htmlPage.append("setTimeout( 'nextPage()', "+ Integer.parseInt(vo_infoNext.getDisplayTotalTime())*1000  +");	\r\n");	
					htmlPage.append(" function nextPage(){ \r\n");
					htmlPage.append(" location.href='"+page_url+"';\r\n");
					htmlPage.append(" } \r\n");
					htmlPage.append("</script>	\r\n");	
					vo_infoNext =  null;
				}
				

				
				htmlPage.append("</body>	\r\n");
				htmlPage.append("</html>	\r\n");
				vo_info =  null;
			}catch(NullPointerException e){
				htmlPage.append("페이지 애러 : " + e.toString());
			}	catch(Exception e){
				htmlPage.append("페이지 애러 : " + e.toString());
			}		
			
			return htmlPage.toString();
			
	}

	
	public boolean ContentFileCreate(String htmlPgae, String displaySeq, String fileGubun)throws Exception {
		// TODO Auto-generated method stub
		boolean htmlCreate = false;
		BufferedWriter fw = null;
		try{
			 
			 DispalyPageInfo disInfo =	 dispalyMapper.selectDisplayPageInfoManageView(displaySeq);
			 String filePath = propertiesService.getString("Globals.fileStorePath") ;
			 File  htm_file = null;
			 String fileName =  ""; 	 
			
			 if (  !disInfo.getDisplayLocalfile().equals("N") && !disInfo.getDisplayLocalfile().toString().equals("") ){	
				    fileName = (fileGubun.equals("L")) ? disInfo.getDisplayLocalfile().replace(".html", "_local.html"): disInfo.getDisplayLocalfile();	
					htm_file = new File (filePath +"/" + fileName);
					if (htm_file.isFile()){
						htm_file.delete();
					}							
			 }
			 if (fileGubun.equals("L")){
				 fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"_"+displaySeq+"_local.html";
			 }else {
				 fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"_"+displaySeq+".html";
			 }
			 
			 fw = new BufferedWriter(new FileWriter(filePath +"/"+fileName, true));
			 fw.write(htmlPgae);
	         fw.flush();
	         fw.close(); 
	         DispalyPageInfo vo = new DispalyPageInfo();
	         if (fileGubun.equals("S")){
	        	 vo.setDisplaySeq(displaySeq);
		         vo.setDisplayLocalfile(fileName);;
				 int ret =      dispalyMapper.updateDisplayPageFileUpdate(vo);
				 if (ret > 0){
					 htmlCreate = true;
				 }else {
					 htmlCreate = false;
				 }
	         }else {
	        	 htmlCreate = true;
	         }
	        
		}catch(NullPointerException e){
			log.debug("CREATE HTML FILE ERROR:" + e.toString()  );
			htmlCreate = false;					
		}catch(Exception e){
			log.debug("CREATE HTML FILE ERROR:" + e.toString()  );
			htmlCreate = false;					
		}finally {
			try {
				if (fw != null) fw.close();
			} catch (Exception ex) {
				log.debug("ex error:" + ex.toString());
			}
			
		}
		return htmlCreate;
	}
}

