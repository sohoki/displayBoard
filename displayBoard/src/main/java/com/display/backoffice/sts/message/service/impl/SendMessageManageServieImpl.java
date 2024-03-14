package com.display.backoffice.sts.message.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.display.backoffice.sts.message.mapper.SendMessageManageMapper;
import com.display.backoffice.sts.message.models.SendMessageInfo;
import com.display.backoffice.sts.message.models.SendMessageInfoVO;
import com.display.backoffice.sts.message.service.SendMessageInfoManageService;

@Service("SendMessageInfoManageService")
public class SendMessageManageServieImpl extends EgovAbstractServiceImpl implements  SendMessageInfoManageService{

	@Autowired
	private  SendMessageManageMapper sendMapper;
	
	
	@Override
	public int insertSendMessage(SendMessageInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		//삭제 한다음 없는거 인서트 
		if (vo.getAgentCodeLst().size() > 0){
			sendMapper.deleteSendMessageAgent(vo);
		}
		return sendMapper.insertSendMessage(vo);
	}

	@Override
	public int updateSendMessage(String schCode) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.updateSendMessage(schCode);
	}

	@Override
	public int deleteSendMessage(SendMessageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.deleteSendMessage(vo);
	}

	@Override
	public List<SendMessageInfoVO> selectSendMessageAgentList(SendMessageInfoVO vo) {
		// TODO Auto-generated method stub
		return sendMapper.selectSendMessageAgentList(vo);
	}

	@Override
	public int updateSendMessageAgent(SendMessageInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.updateSendMessageAgent(vo);
	}

	@Override
	public List<SendMessageInfoVO> selectSendMessageAgentHistoryList(SendMessageInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.selectSendMessageAgentHistoryList(vo);
	}
}
