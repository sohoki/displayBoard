package com.display.backoffice.sts.report.service;

import java.util.List;
import com.display.backoffice.sts.report.models.ReportPageInfo;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;
import org.springframework.web.servlet.ModelAndView;


public interface ReportPageInfoManageService {

	
	ModelAndView selectReportPageInfoManageListByPagination(ReportPageInfoVO searchVO) throws Exception;
	
	ModelAndView selectReportPageInfoManageListByPaginationAjax(ReportPageInfoVO searchVO, String displayGubun) throws Exception;
	
    ReportPageInfoVO selectReportPageInfoManageDetail(String  reportSeq )throws Exception;
	
    ReportPageInfoVO selectReportPageInfoManageView(String reportSeq) throws Exception;
    
    String selectReportMax() throws Exception;
	
    int selectReportPageInfoManageListTotCnt_S(ReportPageInfoVO searchVO) throws Exception;
	
    int insertReportPageInfoManage(ReportPageInfo vo) throws Exception;
	
    ModelAndView updateReportPageInfoManage(ReportPageInfo vo) throws Exception;
    
    int updateReportPreviewInfoManage(ReportPageInfo vo) throws Exception;

	
	
}
