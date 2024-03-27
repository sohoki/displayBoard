package com.display.backoffice.sts.sch.service;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sts.message.mapper.SendMessageManageMapper;
import com.display.backoffice.sts.sch.mapper.AgentGroupInfoManagerMapper;
import com.display.backoffice.sts.sch.mapper.SchduleInfoManageMapper;
import com.display.backoffice.sts.sch.models.SchduleInfo;
import com.display.backoffice.sts.sch.models.SchduleInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchduleInfoManageService {

	
	@Autowired
	private SchduleInfoManageMapper schMapper;
	
	@Autowired
	private SendMessageManageMapper sendMapper;

	@Resource(name="egovSchIdGnrService")
	private EgovIdGnrService egovSchIdGnrService;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	
	public List<SchduleInfoVO> selectSchduleInfoManageListByPagination( SchduleInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		  
		return schMapper.selectSchduleInfoManageListByPagination(searchVO);
	}

	
	public SchduleInfoVO selectSchduleInfoManageDetail(String schCode)
			throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectSchduleInfoManageDetail(schCode);
	}

	
	public SchduleInfoVO selectSchduleInfoManageView(String schCode)
			throws Exception {
		// TODO Auto-generated method stub
		
		SchduleInfoVO schinfo = schMapper.selectSchduleInfoManageView(schCode);
		if (schinfo.getSchStartTime().contains(":")){
			String[] time_s = schinfo.getSchStartTime().split(":");
			schinfo.setSchStartTime1(time_s[0]);
			schinfo.setSchStartTime2(time_s[1]);	
		}
		
		if (schinfo.getSchEndTime().contains(":")){
			String[] time_e = schinfo.getSchEndTime().split(":");
			schinfo.setSchEndTime1(time_e[0]);
			schinfo.setSchEndTime2(time_e[1]);	
		}
		
		
		
		return schMapper.selectSchduleInfoManageView(schCode);
	}

	
	public int selectSchduleInfoManageListTotCnt_S(SchduleInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectSchduleInfoManageListTotCnt_S(searchVO);
	}

	
	
	public SchduleInfo updateSchduleInfoManage(SchduleInfo vo) throws Exception {
		// TODO Auto-generated method stub
		
		
		int ret = 0;
		String schCode = "";
		if (vo.getMode().equals("Ins")){
			schCode = egovSchIdGnrService.getNextStringId();
			vo.setSchCode(schCode);
			ret = schMapper.insertSchduleInfoManage(vo);
		}else {
			//수정 페이지 수정 이후 다시 정리 하기 
			ret = schMapper.updateSchduleInfoManage(vo);
			sendMapper.updateSendMessage(vo.getSchCode());
			
		}
		SchduleInfo info = null;
		if ( ret > 0) {
			info = schMapper.selectSchduleInfoManageView(schCode);
		}
		return info;
	}

	
	public int deleteSchduleInfoManage(String schCode) throws Exception {
		// TODO Auto-generated method stub
		return schMapper.deleteSchduleInfoManage(schCode);
	}

	
	public List<SchduleInfo> selectScheduleSendInfo(String agentNm) throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectScheduleSendInfo(agentNm);
	}

	
	public int selectScheduleSendInfoCnt(String agentNm) throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectScheduleSendInfoCnt(agentNm);
	}
}
