package com.display.backoffice.sts.message.mapper;

import java.util.List;

import com.display.backoffice.sts.message.models.SendMessageInfo;
import com.display.backoffice.sts.message.models.SendMessageInfoVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SendMessageManageMapper")
public interface SendMessageManageMapper {

	public List<SendMessageInfoVO> selectSendMessageAgentList (SendMessageInfoVO vo);
	
	public List<SendMessageInfoVO> selectSendMessageAgentHistoryList(SendMessageInfoVO vo);
	
	public int insertSendMessage (SendMessageInfoVO vo);
	
	public int updateSendMessage (String schCode);
	
	public int updateSendMessageAgent(SendMessageInfoVO vo);
	
	public int deleteSendMessage (SendMessageInfo vo);
	
	public int deleteSendMessageAgent(SendMessageInfoVO vo);
}
