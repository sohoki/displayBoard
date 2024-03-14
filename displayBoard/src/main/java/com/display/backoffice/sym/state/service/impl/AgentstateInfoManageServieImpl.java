package com.display.backoffice.sym.state.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.display.backoffice.sym.state.mapper.AgentStateManageMapper;
import com.display.backoffice.sym.state.models.AgentStateInfo;
import com.display.backoffice.sym.state.models.AgentStateInfoVO;
import com.display.backoffice.sym.state.service.AgentstateInfoManageServie;

@Service("AgentstateInfoManageServie")
public class AgentstateInfoManageServieImpl extends EgovAbstractServiceImpl implements AgentstateInfoManageServie {
	
	
	@Autowired
	private AgentStateManageMapper agentStateMapper;

	@Override
	public List<AgentStateInfo> selectAgentStateDay(AgentStateInfoVO searchVO) throws Exception {
		
		// TODO Auto-generated method stub
		return agentStateMapper.selectAgentStateDayList(searchVO);
	}
	@Override
	public List<AgentStateInfoVO> selectAgentStateOnyDayChart(
			AgentStateInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return agentStateMapper.selectAgentStateOnyDayChart(searchVO);
	}

	@Override
	public int updateAgentStateCnt(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentStateMapper.updateAgentStateCnt(agentCode);
	}

	
	
	

}
