package com.display.backoffice.sym.monter.service;

import java.util.List;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.cnt.mapper.CenterInfoManageMapper;
import com.display.backoffice.bas.cnt.service.CenterInfoService;
import com.display.backoffice.sym.agent.mapper.AgentInfoManageMapper;
import com.display.backoffice.sym.monter.mapper.DisplayPageInfoManageMapper;
import com.display.backoffice.sym.monter.models.DispalyPageInfo;
import com.display.backoffice.sym.monter.models.DispalyPageInfoVO;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class DisplayPageInfoManageService {

	
	private final DisplayPageInfoManageMapper dispalyMapper;
	
	
	private final AgentInfoManageMapper agentMapper;

	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	
	public List<DispalyPageInfoVO> selectDisplayPageInfoManageListByPagination(DispalyPageInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		/*
		ModelAndView  model = new ModelAndView();
		try{
			if(  searchVO.getPageUnit() > 0  ){    	   
		    	   searchVO.setPageUnit(searchVO.getPageUnit());
			 }else {
				   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			 }
			 searchVO.setPageSize(propertiesService.getInt("pageSize"));
		       
			 
		       
		   	 PaginationInfo paginationInfo = new PaginationInfo();
			 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			 paginationInfo.setPageSize(searchVO.getPageSize());
			 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			 searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			 searchVO.setDisplayGubun("DispalyGubun_2");
			 
			 List<DispalyPageInfoVO> displayList = dispalyMapper.selectDisplayPageInfoManageListByPagination(searchVO);
		     
		     int totCnt = displayList.size() > 0 ?   displayList.get(0).getTotalRecordCount() : 0; 
	         
			 paginationInfo.setTotalRecordCount(totCnt);
			 model.addObject("resultList", displayList );
		     model.addObject("paginationInfo", paginationInfo);
		     model.addObject("totalCnt", totCnt);
		}catch(NullPointerException e){
			log.error("ERROR selectDisplayPageInfoManageListByPagination :" + e.toString());
			throw e;
		}catch(Exception e){
			log.error("ERROR selectDisplayPageInfoManageListByPagination :" + e.toString());
			throw e;
		}
		*/
		return   dispalyMapper.selectDisplayPageInfoManageListByPagination(searchVO);
		
	}

	
	public DispalyPageInfoVO selectDisplayPageInfoManageDetail(String reportSeq)
			throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.selectDisplayPageInfoManageDetail(reportSeq);
	}

	
	public DispalyPageInfoVO selectDisplayPageInfoManageView(String displaySeq)
			throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.selectDisplayPageInfoManageView(displaySeq);
	}

	
	public int selectDisplayPageInfoManageListTotCnt_S(
			DispalyPageInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.selectDisplayPageInfoManageListTotCnt_S(searchVO);
	}

	
	public int updateDisplayPageInfoManage(DispalyPageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return vo.getMode().equals("Ins") ? dispalyMapper.insertDisplayPageInfoManage(vo) : dispalyMapper.updateDisplayPageInfoManage(vo);
	}

	
	public ModelAndView deleteDisplayPageInfoManage(String  displaySeq) throws Exception {
		// TODO Auto-generated method stub
		
		ModelAndView model = new ModelAndView();
		try{
			//log.debug("searchVO:" + searchVO.toString());
			//여기 부분에서 전광판/현황판 구분 해서 정리 하기
			
			DispalyPageInfoVO searchVO = new DispalyPageInfoVO();
			searchVO.setDisplaySeq(displaySeq);
			
			int totalCnt = dispalyMapper.deleteDisplayPageInfoManage(searchVO ); 
			log.debug("totalCnt:" + totalCnt);
			
		    if (totalCnt == -1 ){
		    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("page.display.alert03"));	
				model.addObject(Globals.STATUS, Globals.STATUS_NTUNIQUE);
				
				
		    }else{
		    	model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		    }
		}catch(NullPointerException e){
			log.error("deleteDisplayPageInfoManage ERROR:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("page.display.alert03"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.error("deleteDisplayPageInfoManage ERROR:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("page.display.alert03"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		
		return model;
		/*
		DispalyPageInfoVO searchVO = new DispalyPageInfoVO();
		searchVO.setDisplaySeq(displaySeq);
		return dispalyMapper.deleteDisplayPageInfoManage(searchVO );
		*/
	}

	
	public List<DispalyPageInfoVO> selectDisplayPageInfoCombo(DispalyPageInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.selectDisplayPageInfoCombo(searchVO);
	}

	
	public int updateDisplayTimeInfoManage(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.updateDisplayTimeInfoManage(displaySeq);
	}

	
	public String selectDisplayMaxSeq() throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.selectDisplayMaxSeq();
	}

	
	public int updateDisplayPageUpManage(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.updateDisplayPageUpManage(displaySeq);
	}

	
	public int updateDisplayPageDownManage(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.updateDisplayPageDownManage(displaySeq);
	}

	
	public int updateDisplayReSendManage(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		/*
		ModelAndView model = new ModelAndView();
		try{
			dispalyMapper.updateDisplayReSendManage(displaySeq);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(NullPointerException e){
			log.debug("updateSisplayUpdateChange Error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			
		}catch(Exception e){
			log.debug("updateSisplayUpdateChange Error:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			
		}
		*/
		return dispalyMapper.updateDisplayReSendManage(displaySeq);
	}

	
	public List<DispalyPageInfo> selectDisplayPageInfoContentCombo(String displayGubun)
			throws Exception {
		// TODO Auto-generated method stub
		return dispalyMapper.selectDisplayPageInfoContentCombo(displayGubun);
	}
    
}

