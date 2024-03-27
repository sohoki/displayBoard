package com.display.backoffice.sts.report.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.display.backoffice.sts.report.mapper.ReportPageInfoManageMapper;
import com.display.backoffice.sts.report.models.ReportPageInfo;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportPageInfoManageService {

	
	private final ReportPageInfoManageMapper reportMapper;
	
 
	@Resource(name="egovFileIdGnrService")
	private EgovIdGnrService egovFileIdGnrService; 
	
	public List<ReportPageInfoVO> selectReportPageInfoManageListByPagination(ReportPageInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		
		return reportMapper.selectReportPageInfoManageListByPagination(searchVO);
		
	}
	
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
	
	
	public ReportPageInfoVO selectReportPageInfoManageDetail(String reportSeq)throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportPageInfoManageDetail(reportSeq);
	}

	
	public ReportPageInfoVO selectReportPageInfoManageView(String reportSeq)throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportPageInfoManageView(reportSeq);
	}

	
	public int selectReportPageInfoManageListTotCnt_S(ReportPageInfoVO searchVO)throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportPageInfoManageListTotCnt_S(searchVO);
	}

	
	public int insertReportPageInfoManage(ReportPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.insertReportPageInfoManage(vo);
	}

	
	public int updateReportPageInfoManage(ReportPageInfo vo) throws Exception {
		
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			//atch file id 생성 			
			vo.setAtchFileId(egovFileIdGnrService.getNextStringId());
			ret =	reportMapper.insertReportPageInfoManage(vo);
		}else {
			ret = reportMapper.updateReportPageInfoManage(vo);
		}	
		return ret;
	}

	
	public int updateReportPreviewInfoManage(ReportPageInfo vo)throws Exception {
		// TODO Auto-generated method stub
		return  reportMapper.updateReportPreviewInfoManage(vo);
	}

	
	public String selectReportMax() throws Exception {
		// TODO Auto-generated method stub
		return reportMapper.selectReportMax();
	}

	
	
}
