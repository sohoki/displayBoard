package com.display.backoffice.sts.sch.service;

import java.util.List;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.display.backoffice.sts.sch.mapper.AgentGroupInfoManagerMapper;
import com.display.backoffice.sts.sch.models.AgentGroupInfo;
import com.display.backoffice.sts.sch.models.AgentGroupInfoVO;
import egovframework.com.cmm.service.Globals;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AgentGroupInfoManagerService {

	private final AgentGroupInfoManagerMapper agentGroupMapper;

	@Autowired
    protected EgovPropertyService propertiesService;
	
	//추후 하나로 합치기
	public List<AgentGroupInfoVO> selectAgentGroupInfoListByPagination(AgentGroupInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectAgentGroupInfoLeftListByPagination(searchVO);
	}
	public List<AgentGroupInfoVO> selectAgentGroupInfoRightListByPagination(AgentGroupInfoVO search){
		return agentGroupMapper.selectAgentGroupInfoRightListByPagination(search);
	}
	
	public String changedeleteAgentGroupInfo(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		
		try{
			if (vo.getMode().equals("Ins")){
				return (agentGroupMapper.selectAgentGroupInfoCheck(vo) < 1) ? 
						String.valueOf(agentGroupMapper.insertAgentGroupInfo(vo)) : Globals.STATUS_UNIQUE;
			}else {
				return String.valueOf(agentGroupMapper.deleteAgentGroupInfo( String.valueOf( vo.getGroupSeq())));
			}
		}catch(Exception e){
			log.error("changedeleteAgentGroupInfo error:" + e.toString());
			return "ERROR"; 
		}
		
	}
	
	
	public int updateAgentSendUpdate(List<AgentGroupInfo> vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentSendUpdate(vo);
	}

	
	public int updateAgentReceivedUpdate(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentReceivedUpdate(vo);
	}

	
	public int updateAgentFileUpdate(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentFileUpdate(vo);
	}

	

	
	public int selectAgentSchCount(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectAgentSchCount(agentCode);
	}

	
	public List<AgentGroupInfoVO> selectDisplayPageInfoContentList(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectDisplayPageInfoContentList(agentCode);
	}

	
	public List<AgentGroupInfoVO> selectDisplayFileInfoContentList(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectDisplayFileInfoContentList(vo);
	}

	
	public List<AgentGroupInfoVO> selectAgentContentListInfo(
			AgentGroupInfoVO search) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectAgentContentListInfo(search);
	}

	
	public int updateAgentResetUpdateContent(String conschCode)
			throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentResetUpdateContent(conschCode);
	}
}
