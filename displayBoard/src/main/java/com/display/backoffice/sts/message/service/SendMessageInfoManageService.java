package com.display.backoffice.sts.message.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.display.backoffice.sts.message.mapper.SendMessageManageMapper;
import com.display.backoffice.sts.message.models.SendMessageInfo;
import com.display.backoffice.sts.message.models.SendMessageInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class SendMessageInfoManageService {
	
	
	
	private final  SendMessageManageMapper sendMapper;
	
	
	
	public int insertSendMessage(SendMessageInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		//삭제 한다음 없는거 인서트 
		if (vo.getAgentCodeLst().size() > 0)
			sendMapper.deleteSendMessageAgent(vo);
		return sendMapper.insertSendMessage(vo);
	}

	
	public int updateSendMessage(String schCode) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.updateSendMessage(schCode);
	}

	
	public int deleteSendMessage(SendMessageInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.deleteSendMessage(vo);
	}

	
	public List<SendMessageInfoVO> selectSendMessageAgentList(SendMessageInfoVO vo) {
		// TODO Auto-generated method stub
		return sendMapper.selectSendMessageAgentList(vo);
	}

	
	public int updateSendMessageAgent(SendMessageInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.updateSendMessageAgent(vo);
	}

	
	public List<SendMessageInfoVO> selectSendMessageAgentHistoryList(SendMessageInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sendMapper.selectSendMessageAgentHistoryList(vo);
	}
}
