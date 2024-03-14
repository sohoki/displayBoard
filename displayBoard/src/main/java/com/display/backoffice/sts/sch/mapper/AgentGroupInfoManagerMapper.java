package com.display.backoffice.sts.sch.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.sts.sch.models.AgentGroupInfo;
import com.display.backoffice.sts.sch.models.AgentGroupInfoVO;


@Mapper
public interface AgentGroupInfoManagerMapper {
	
	public List<AgentGroupInfoVO> selectAgentGroupInfoLeftListByPagination(AgentGroupInfoVO search);
	
	public List<AgentGroupInfoVO> selectAgentGroupInfoRightListByPagination(AgentGroupInfoVO search);
	
	public List<AgentGroupInfoVO> selectAgentContentListInfo(AgentGroupInfoVO search);
	
	public List<AgentGroupInfoVO> selectDisplayPageInfoContentList(String agentCode);
	
	public List<AgentGroupInfoVO> selectDisplayFileInfoContentList(AgentGroupInfo vo);
	
	public int selectAgentGroupInfoCheck(AgentGroupInfo vo);
	
	public int selectAgentSchCount(String agentCode);
	
	public int insertAgentGroupInfo(AgentGroupInfo vo);
	
	public int updateAgentResetUpdateContent(String conschCode);
	
	public int updateAgentSendUpdate(List<AgentGroupInfo> vo);
	
	public int updateAgentReceivedUpdate(AgentGroupInfo vo);
	
	public int updateAgentFileUpdate(AgentGroupInfo vo);
	
	public int deleteAgentGroupInfo(String groupSeq);
	
}
