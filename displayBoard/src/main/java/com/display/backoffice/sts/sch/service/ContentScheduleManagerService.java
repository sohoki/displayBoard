package com.display.backoffice.sts.sch.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sts.sch.models.ContentScheduleInfo;
import com.display.backoffice.sts.sch.models.ContentScheduleInfoVO;



public interface ContentScheduleManagerService {

	
	ModelAndView selectContentSchduleInfoManageListByPagination(ContentScheduleInfoVO searchVO) throws Exception;
	
	ContentScheduleInfoVO  selectConetntSchduleInfoManageView(String conschCode) throws Exception;
	
	String conPageDetail(String displaySeq, String fileGubun)throws Exception;
	
	int updateContentSchduleInfoManage(ContentScheduleInfo vo) throws Exception;
	
	int deleteContentSchduleInfoManage(String conschCode) throws Exception;
	
}
