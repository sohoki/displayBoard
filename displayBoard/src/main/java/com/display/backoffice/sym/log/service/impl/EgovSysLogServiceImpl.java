package com.display.backoffice.sym.log.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sym.log.mapper.SysLogManageMapper;
import com.display.backoffice.sym.log.annotation.NoLogging;
import com.display.backoffice.sym.log.service.EgovSysLogService;
import com.display.backoffice.sym.log.vo.SysLog;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("EgovSysLogService")
public class EgovSysLogServiceImpl  extends EgovAbstractServiceImpl implements EgovSysLogService {

	
	
	//@Autowired
	@Resource(name="SysLogManageMapper")
	private SysLogManageMapper syslogMapper;
	
	/** ID Generation */
	@Resource(name="egovSysLogIdGnrService")
	private EgovIdGnrService egovSysLogIdGnrService;
	
	@NoLogging
	@Override
	public void logInsertSysLog(SysLog sysLog) throws Exception {
		// TODO Auto-generated method stub
		sysLog.setRequstId(egovSysLogIdGnrService.getNextStringId());
		syslogMapper.logInsertSysLog(sysLog);
	}
	@NoLogging
	@Override
	public void logInsertSysLogSummary() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@NoLogging
	@Override
	public Map<String, Object> selectSysLogInfo(@Param("requestId") String requstId) throws Exception {
		// TODO Auto-generated method stub
		return syslogMapper.selectSysLogInfo(requstId);
	}
	@Override
	public ModelAndView selectSysLogList(SysLog sysLog)  throws Exception {
		// TODO Auto-generated method stub
		//페이징 처리 다시 한번 생각하기 
		
		ModelAndView mav = new ModelAndView();
		
		PaginationInfo paginationInfo = new PaginationInfo();
	   	paginationInfo.setCurrentPageNo(sysLog.getPageIndex());
		paginationInfo.setRecordCountPerPage(sysLog.getPageUnit());
		paginationInfo.setPageSize(sysLog.getPageSize());
		 
		sysLog.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sysLog.setLastIndex(paginationInfo.getLastRecordIndex());
		sysLog.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		//수정 변경
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("firstIndex", paginationInfo.getFirstRecordIndex());
		params.put("recordCountPerPage", paginationInfo.getRecordCountPerPage());
		 		
		List<Map<String, Object>> list = syslogMapper.selectSysLogList(params);
		int totCnt = list.size() > 0 ? Integer.parseInt( list.get(0).get("total_record_count").toString() ): 0;
		paginationInfo.setTotalRecordCount(totCnt);
		mav.addObject("regist", sysLog);
    	mav.addObject("resultList",  list );
    	mav.addObject("paginationInfo", paginationInfo);
    	mav.addObject("totalCnt", totCnt);
		return mav;
	}
	
	
}
