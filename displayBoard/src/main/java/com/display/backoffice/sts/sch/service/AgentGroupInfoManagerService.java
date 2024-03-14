package com.display.backoffice.sts.sch.service;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sts.sch.models.AgentGroupInfo;
import com.display.backoffice.sts.sch.models.AgentGroupInfoVO;



public interface AgentGroupInfoManagerService {

	ModelAndView selectAgentGroupInfoListByPagination(AgentGroupInfoVO search) throws Exception;
	
	List<AgentGroupInfoVO> selectAgentContentListInfo(AgentGroupInfoVO search)throws Exception;
	
	List<AgentGroupInfoVO> selectDisplayPageInfoContentList(String agentCode) throws Exception;
	
	List<AgentGroupInfoVO> selectDisplayFileInfoContentList(AgentGroupInfo vo) throws Exception;
	
	int selectAgentSchCount(String agentCode) throws Exception;
	
	String changedeleteAgentGroupInfo(AgentGroupInfo vo) throws Exception;
	
	int updateAgentResetUpdateContent(String conschCode) throws Exception;
	
	int updateAgentSendUpdate(List<AgentGroupInfo> vo) throws Exception;
	
	int updateAgentReceivedUpdate(AgentGroupInfo vo) throws Exception;
	
	int updateAgentFileUpdate(AgentGroupInfo vo) throws Exception;
}
