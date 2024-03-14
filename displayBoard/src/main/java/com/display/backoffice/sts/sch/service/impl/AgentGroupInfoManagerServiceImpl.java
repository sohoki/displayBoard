package com.display.backoffice.sts.sch.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sts.sch.mapper.AgentGroupInfoManagerMapper;
import com.display.backoffice.sts.sch.models.AgentGroupInfo;
import com.display.backoffice.sts.sch.models.AgentGroupInfoVO;
import com.display.backoffice.sts.sch.service.AgentGroupInfoManagerService;

import egovframework.com.cmm.service.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("AgentGroupInfoManagerService")
public class AgentGroupInfoManagerServiceImpl extends EgovAbstractServiceImpl implements AgentGroupInfoManagerService{
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentGroupInfoManagerServiceImpl.class);
	
	@Autowired
	private AgentGroupInfoManagerMapper agentGroupMapper;

	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Override
	public ModelAndView selectAgentGroupInfoListByPagination(AgentGroupInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView model = new 	ModelAndView(Globals.JSONVIEW);
		try{
			searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			paginationInfo.setPageSize(searchVO.getPageSize());
			searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			List<AgentGroupInfoVO> leftAgentGroup = agentGroupMapper.selectAgentGroupInfoLeftListByPagination(searchVO);
			model.addObject("resultList",   leftAgentGroup);
		    int totCnt =  leftAgentGroup.size() > 0 ? leftAgentGroup.get(0).getTotalRecordCount() : 0; ;       
			paginationInfo.setTotalRecordCount(totCnt);
		    model.addObject("leftPaginationInfo", paginationInfo);
		    leftAgentGroup = null;
			model.addObject("resultRight",   agentGroupMapper.selectAgentGroupInfoRightListByPagination(searchVO));
		}catch(NullPointerException e){
			LOGGER.error("selectAgentGroupInfoListByPagination ERROR:" + e.toString());
			throw e;
		}catch(Exception e){
			LOGGER.error("selectAgentGroupInfoListByPagination ERROR:" + e.toString());
			throw e;
		}
		return model;
	}
	
	@Override
	public String changedeleteAgentGroupInfo(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		String result = Globals.STATUS_FAIL;
		try{
			if (vo.getMode().equals("Ins")){
				result = (agentGroupMapper.selectAgentGroupInfoCheck(vo) < 1) ? String.valueOf(agentGroupMapper.insertAgentGroupInfo(vo)) : Globals.STATUS_UNIQUE;
			}else {
				result = String.valueOf(agentGroupMapper.deleteAgentGroupInfo( String.valueOf( vo.getGroupSeq())));
			}
		}catch(NullPointerException e){
			LOGGER.error("changedeleteAgentGroupInfo ERROR:" + e.toString());
			throw e;
		}catch(Exception e){
			LOGGER.error("changedeleteAgentGroupInfo ERROR:" + e.toString());
			throw e;
		}
		
		return result;
	}
	
	@Override
	public int updateAgentSendUpdate(List<AgentGroupInfo> vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentSendUpdate(vo);
	}

	@Override
	public int updateAgentReceivedUpdate(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentReceivedUpdate(vo);
	}

	@Override
	public int updateAgentFileUpdate(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentFileUpdate(vo);
	}

	

	@Override
	public int selectAgentSchCount(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectAgentSchCount(agentCode);
	}

	@Override
	public List<AgentGroupInfoVO> selectDisplayPageInfoContentList(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectDisplayPageInfoContentList(agentCode);
	}

	@Override
	public List<AgentGroupInfoVO> selectDisplayFileInfoContentList(AgentGroupInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectDisplayFileInfoContentList(vo);
	}

	@Override
	public List<AgentGroupInfoVO> selectAgentContentListInfo(
			AgentGroupInfoVO search) throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.selectAgentContentListInfo(search);
	}

	@Override
	public int updateAgentResetUpdateContent(String conschCode)
			throws Exception {
		// TODO Auto-generated method stub
		return agentGroupMapper.updateAgentResetUpdateContent(conschCode);
	}

}
