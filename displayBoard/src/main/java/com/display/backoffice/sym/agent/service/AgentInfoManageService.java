package com.display.backoffice.sym.agent.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.cnt.mapper.CenterInfoManageMapper;
import com.display.backoffice.bas.cnt.models.CenterInfoVO;
import com.display.backoffice.sym.agent.mapper.AgentInfoManageMapper;
import com.display.backoffice.sym.agent.models.AgentInfo;
import com.display.backoffice.sym.agent.models.AgentInfoVO;
import com.display.backoffice.sym.log.annotation.NoLogging;
import com.display.backoffice.sym.monter.mapper.DisplayPageInfoManageMapper;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class AgentInfoManageService {

	
	
	private final AgentInfoManageMapper agentMapper;
	
	
	private final CenterInfoManageMapper centerMapper;

	@Resource(name="egovEqupIdGnrService")
	private EgovIdGnrService egovEqupIdGnrService;
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	
	public ModelAndView selectAgentPageInfoManageListByPagination(AgentInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView model = new ModelAndView();
		try{
			
			 /** pageing */       
			 PaginationInfo paginationInfo = new PaginationInfo();
			 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			 paginationInfo.setPageSize(searchVO.getPageSize());
			 
			 

			 
			 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			 
			 log.debug("getPageIndex:" +searchVO.getPageIndex() );
			 log.debug("getPageUnit:" +searchVO.getPageUnit() );
			 log.debug("getPageSize:" +searchVO.getPageSize() );
			 log.debug("getFirstRecordIndex:" +searchVO.getFirstIndex() );
			 
			 ;
			 searchVO.setRecordCountPerPage(searchVO.getPageUnit());
			 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			 
	          List<AgentInfoVO> agentList = agentMapper.selectAgentPageInfoManageListByPagination(searchVO);
	          model.addObject("resultList",  agentList );
	          List<AgentInfoVO> floorLost = agentList.stream().sorted(Comparator.comparing((AgentInfoVO vo ) -> vo.getAgentFloor())).collect(Collectors.toList());
	          model.addObject("agentList", floorLost);
	          int totCnt = agentList.size() > 0 ? agentList.get(0).getTotalRecordCount() : 0;  
		      paginationInfo.setTotalRecordCount(totCnt);
			  model.addObject("paginationInfo", paginationInfo);
			  model.addObject("totalCnt", Integer.valueOf(totCnt));
			
		}catch(NullPointerException e){
			 log.error("selectAgentPageInfoManageListByPagination :" + e.toString());
		}catch(Exception e){
			 log.error("selectAgentPageInfoManageListByPagination :" + e.toString());
		}
		return model;
	}
	
	
	public ModelAndView selectDisplayStageChangePageList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		ModelAndView model = new ModelAndView();
		try{
			 log.debug("params:" + params.toString());
			 
			 
			 PaginationInfo leftPaginationInfo = new PaginationInfo();
			 leftPaginationInfo.setCurrentPageNo( Integer.valueOf( params.get("leftPageIndex").toString() ) );
			 leftPaginationInfo.setRecordCountPerPage( Integer.valueOf(  params.get("recordCountPerPage").toString() ) );
			 leftPaginationInfo.setPageSize( Integer.valueOf(  params.get("recordCountPerPage").toString() )  );
			 params.put("leftFirstIndex", leftPaginationInfo.getFirstRecordIndex());
			 
			 
			 PaginationInfo rightPaginationInfo = new PaginationInfo();
			 rightPaginationInfo.setCurrentPageNo( Integer.valueOf( params.get("rightPageIndex").toString() ) );
			 rightPaginationInfo.setRecordCountPerPage( Integer.valueOf(  params.get("recordCountPerPage").toString() ) );
			 rightPaginationInfo.setPageSize( Integer.valueOf(  params.get("recordCountPerPage").toString() )  );
			 params.put("rightFirstIndex", rightPaginationInfo.getFirstRecordIndex());
			 
			 log.debug("params:" + params.toString());
			 
			 
			 List<Map<String, Object>> disStateLists = agentMapper.selectDisplayStageChangePageList(params);
			 int totCntLeft = 0;
			 int totCntRight = 0;
			 //여기 부분 람다식으로 바꾸기 
			 for (Map<String, Object> disStateList : disStateLists){
				 if (disStateList.get("play_gubun").equals("OT")){
					 totCntLeft = Integer.valueOf( disStateList.get("total_record_count").toString());
				 }
				 if (disStateList.get("play_gubun").equals("IN")){
					 totCntRight = Integer.valueOf( disStateList.get("total_record_count").toString());
					 break;
				 }
			 }
			 
		 	 model.addObject("disStateList",  disStateLists );
		 	 model.addObject("totCntLeft",totCntLeft);
		 	 leftPaginationInfo.setTotalRecordCount(totCntLeft);
		 	 model.addObject("leftPaginationInfo", leftPaginationInfo);
			
		 	 model.addObject("totCntRight",totCntRight);
		 	 rightPaginationInfo.setTotalRecordCount(totCntRight);
			 model.addObject("rightPaginationInfo", rightPaginationInfo);
			
			 model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);  
		}catch(NullPointerException e){
			log.error("selectDisplayStageChangePageList ERROR :" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);  
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));  
		}catch(Exception e){
			log.error("selectDisplayStageChangePageList ERROR :" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);  
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));  
		}
		return model;
	}
	
	public ModelAndView updateAgentDisplayChange(AgentInfo vo)throws Exception {
		// TODO Auto-generated method stub
		ModelAndView model = new ModelAndView();
		try{
			AgentInfoVO info = selectAgentPageInfoManageDetail(vo.getAgentCode());
			//agent url 값 수정 
			if (vo.getAgentContentgubun().equals("AGENT_CONTENT_1") ) {
				agentMapper.updateAgentUrlRec(vo);
			}
			agentMapper.updateAgentDisplayChange(vo);
			 //값 수정 
			 
			 
			 
			 model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS); 
		}catch(NullPointerException e){
			log.error("updateAgentDisplayChange ERROR :" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);  
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));  
		}catch(Exception e){
			log.error("updateAgentDisplayChange ERROR :" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);  
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg"));  
		}
		return model;
	}

	
	public AgentInfoVO selectAgentPageInfoManageDetail(String agentCode)throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageDetail(agentCode);
	}

	
	public AgentInfoVO selectAgentPageInfoManageView(AgentInfoVO searchVO)throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageView(searchVO);
	}

	
	public AgentInfoVO selectAgentUrlCheck(AgentInfo searchVO) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentUrlCheck(searchVO);
	}

	
	public int selectAgentExist(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentExist(agentCode);
	}

	
	public String selectDisplayCheck(AgentInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectDisplayCheck(vo);
	}

	
	public int selectAgentPageInfoManageListTotCnt_S(AgentInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentPageInfoManageListTotCnt_S(searchVO);
	}

	
	public int updateAgentPageInfoManage(AgentInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			vo.setAgentCode(egovEqupIdGnrService.getNextStringId());
			ret = agentMapper.insertAgentPageInfoManage(vo);
		}else {
			ret = agentMapper.updateAgentPageInfoManage(vo);
		}
		return ret;
	}

	
	public int updateAgentUrlRec(AgentInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentUrlRec(vo);
	}

	
	public int updateAgentIpMac(AgentInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentIpMac(vo);
	}

	
	public int updateAgentStateUpdate(String displaySeq) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentStateUpdate(displaySeq);
	}

	
	public int updateDisplayUpdate(AgentInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateDisplayUpdate(vo);
	}

	
	public int deleteAgentPageInfoManage(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.deleteAgentPageInfoManage(agentCode);
	}

	
	public List<AgentInfoVO> selectAgentCenterPageList(AgentInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.selectAgentCenterPageList(searchVO);
	}

	
	public ModelAndView selectAgentNowStateInnfo(CenterInfoVO searchVO)throws Exception {
		// TODO Auto-generated method stub
		ModelAndView model = new ModelAndView();
		try{
			 PaginationInfo paginationInfo = new PaginationInfo();
			 paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			 paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			 paginationInfo.setPageSize(searchVO.getPageSize());

			 searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			 searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			  //수정
			 searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			  List<CenterInfoVO> centerLists =  centerMapper.selectCenterInfoManageListByPagination(searchVO);
		      int i =0;
		      for(CenterInfoVO center : centerLists ){
		    	  List<AgentInfoVO> agentList = agentMapper.selectAgentNowStateInnfo(center.getCenterId().toString() );
		    	  model.addObject("agentState"+i, agentList);
		    	  i +=1;
		      }
			  model.addObject("centerInfo", centerLists); 
		}catch(NullPointerException e){
			log.error("selectAgentNowStateInnfo ERROR:" + e.toString());
			throw e;
		}catch(Exception e){
			log.error("selectAgentNowStateInnfo ERROR:" + e.toString());
			throw e;
		}
		
		
		return model;
	}
	//로그 확인 하기 
	public int updateAgentState() throws Exception {
		// TODO Auto-generated method stub
		return agentMapper.updateAgentState();
	}
}

