package com.display.backoffice.sts.report.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sts.report.mapper.ReportPageInfoManageMapper;
import com.display.backoffice.sts.report.models.ReportPageInfo;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;
import com.display.backoffice.sts.report.service.ReportPageInfoManageService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReportPageInfoManageServiceImpl extends EgovAbstractServiceImpl implements ReportPageInfoManageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportPageInfoManageServiceImpl.class);
	
	@Autowired
	private ReportPageInfoManageMapper reportMapper;
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Resource(name="egovFileIdGnrService")
	private EgovIdGnrService egovFileIdGnrService;    
	
	@Override
	public ModelAndView selectReportPageInfoManageListByPagination(ReportPageInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		
		ModelAndView model = new ModelAndView();
		try{
			/** pageing */       
		   	PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());

			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			searchVO.setReplacePath(propertiesService.getString("Globals.fileStorePathReplace") );
			
	        List<ReportPageInfoVO> list = reportMapper.selectReportPageInfoManageListByPagination(searchVO);
	        model.addObject("resultList",  list );
		    int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
		    paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject("paginationInfo", paginationInfo);
		    model.addObject("totalCnt", totCnt);
		    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    
		}catch(Exception e){
			LOGGER.error("selectReportPageInfoManageListByPagination ERROR:" + e.toString() );
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			//Utils.getPrintStackTrace(e);
			throw e;
		}
		return model; 
	}
	@Override
	public ModelAndView selectReportPageInfoManageListByPaginationAjax( ReportPageInfoVO searchVO, String displayGubun) throws Exception {
		ModelAndView model = new ModelAndView();
		try{
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());
			
			
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setReplacePath(propertiesService.getString("Globals.fileStorePathReplace") );
			
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			if (displayGubun.equals("DispalyGubun_2")){
				List<String> agentCode =  Arrays.asList("reportGubun_3,reportGubun_4".split("\\s*,\\s*"));;
				searchVO.setSearchReportGubun(agentCode);
			}
			List<ReportPageInfoVO> disList = reportMapper.selectReportPageInfoManageListByPagination(searchVO);
			model.addObject("resultList", disList);
			int totCnt = disList.size() > 0 ? disList.get(0).getTotalRecordCount() : 0  ;
			paginationInfo.setTotalRecordCount(totCnt);
			model.addObject("totalCnt", totCnt);
			model.addObject("paginationInfo", paginationInfo);
			
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
			LOGGER.error("selectReportPageInfoManageListByPaginationAjax ERROR:" + e.toString() );
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			//Utils.getPrintStackTrace(e);
			throw e;
		}
		
		return model;
	}	
	@Override
	public ReportPageInfoVO selectReportPageInfoManageDetail(String reportSeq)
			throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportPageInfoManageDetail(reportSeq);
	}

	@Override
	public ReportPageInfoVO selectReportPageInfoManageView(String reportSeq)
			throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportPageInfoManageView(reportSeq);
	}

	@Override
	public int selectReportPageInfoManageListTotCnt_S(ReportPageInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportPageInfoManageListTotCnt_S(searchVO);
	}

	@Override
	public int insertReportPageInfoManage(ReportPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.insertReportPageInfoManage(vo);
	}

	@Override
	public ModelAndView updateReportPageInfoManage(ReportPageInfo vo) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSONVIEW);
		try{
			if (vo.getMode().equals("Ins")){
				//atch file id 생성 			
				vo.setAtchFileId(egovFileIdGnrService.getNextStringId());
				reportMapper.insertReportPageInfoManage(vo);
			}else {
				reportMapper.updateReportPageInfoManage(vo);
			}	
			model.addObject("status", Globals.STATUS_SUCCESS);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.update"));
		}catch(Exception e){
			LOGGER.error("updateReportPageInfoManage ERROR:" + e.toString() );
			
			model.addObject("status", Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));
		}
		return model;
		
	}

	@Override
	public int updateReportPreviewInfoManage(ReportPageInfo vo)throws Exception {
		// TODO Auto-generated method stub
		return  reportMapper.updateReportPreviewInfoManage(vo);
	}

	@Override
	public String selectReportMax() throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportMax();
	}

	
}