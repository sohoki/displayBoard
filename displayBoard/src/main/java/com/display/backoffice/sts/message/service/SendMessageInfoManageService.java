package com.display.backoffice.sts.message.service;

import java.util.List;
import com.display.backoffice.sts.message.models.SendMessageInfo;
import com.display.backoffice.sts.message.models.SendMessageInfoVO;



public interface SendMessageInfoManageService {
	
	
	List<SendMessageInfoVO> selectSendMessageAgentList (SendMessageInfoVO vo) throws Exception;
	
	List<SendMessageInfoVO> selectSendMessageAgentHistoryList(SendMessageInfoVO vo) throws Exception;
	
    int insertSendMessage (SendMessageInfoVO vo) throws Exception;
	
	int updateSendMessage (String schCode) throws Exception;
	
	int updateSendMessageAgent(SendMessageInfoVO vo) throws Exception;
	
	int deleteSendMessage (SendMessageInfo vo) throws Exception;
}
