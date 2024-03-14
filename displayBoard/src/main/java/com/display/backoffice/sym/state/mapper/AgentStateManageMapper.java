package com.display.backoffice.sym.state.mapper;

import java.util.List;

import com.display.backoffice.sym.state.models.AgentStateInfo;
import com.display.backoffice.sym.state.models.AgentStateInfoVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface AgentStateManageMapper {
	
    public List<AgentStateInfo> selectAgentStateDayList(AgentStateInfoVO searchVO);
    
    public List<AgentStateInfoVO> selectAgentStateOnyDayChart(AgentStateInfoVO searchVO);
	
    public int updateAgentStateCnt (String agentCode);
    
    public int insertAgentStateCreate();
}
