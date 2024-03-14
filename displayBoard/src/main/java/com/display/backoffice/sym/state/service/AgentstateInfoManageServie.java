package com.display.backoffice.sym.state.service;

import java.util.List;
import com.display.backoffice.sym.state.models.AgentStateInfo;
import com.display.backoffice.sym.state.models.AgentStateInfoVO;


public interface AgentstateInfoManageServie {

	List<AgentStateInfo> selectAgentStateDay(AgentStateInfoVO searchVO) throws Exception;
	
	List<AgentStateInfoVO> selectAgentStateOnyDayChart(AgentStateInfoVO searchVO) throws Exception;
	
	int updateAgentStateCnt (String agentCode) throws Exception;
}