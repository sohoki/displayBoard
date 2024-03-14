package com.display.backoffice.sym.agent.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sym.agent.models.AgentInfo;
import com.display.backoffice.sym.agent.models.AgentInfoVO;
import com.display.backoffice.sym.cnt.models.CenterInfoVO;



public interface AgentInfoManageService {

	
	ModelAndView selectAgentPageInfoManageListByPagination(AgentInfoVO searchVO) throws Exception;
	//신규 
	ModelAndView selectDisplayStageChangePageList(@Param("params") Map<String, Object> params)throws Exception;
	
	ModelAndView updateAgentDisplayChange(AgentInfo vo)throws Exception;
	//신규 끝 부분 
	ModelAndView selectAgentNowStateInnfo(CenterInfoVO searchVO) throws Exception;
	
	List<AgentInfoVO> selectAgentCenterPageList(AgentInfoVO searchVO) throws Exception;
	
    AgentInfoVO selectAgentPageInfoManageDetail(String  agentCode )throws Exception;
	
    AgentInfoVO selectAgentPageInfoManageView(AgentInfoVO searchVO) throws Exception;
    
    AgentInfoVO selectAgentUrlCheck(AgentInfo searchVO)throws Exception;
    
    int selectAgentExist (String agentCode) throws Exception;
	
    String selectDisplayCheck(AgentInfo vo) throws Exception;
    
    int selectAgentPageInfoManageListTotCnt_S(AgentInfoVO searchVO) throws Exception;
    
    int updateAgentState()throws Exception;
	
    int updateAgentPageInfoManage(AgentInfo vo) throws Exception;
    //url 업데이트
    int updateAgentUrlRec(AgentInfo vo)throws Exception;
    
    int updateAgentIpMac (AgentInfo vo) throws Exception;
    
    int updateAgentStateUpdate(String displaySeq) throws Exception;
    
    int updateDisplayUpdate(AgentInfo vo)throws Exception;
	
    int deleteAgentPageInfoManage(String  agentCode) throws Exception;
}

