package com.display.backoffice.sts.xml.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.sts.xml.models.SendMsgInfo;
import com.display.backoffice.sts.xml.models.SendMsgInfoVO;

@Mapper
public interface SendMsgInfoManagerMapper {
	
	public List<SendMsgInfoVO> selectSendMsgInfoManageListByPagination(SendMsgInfoVO searchVO);  
	
	public int selectAgentOrderCount(SendMsgInfo searchVO);  
	
	public int selectAgentMessageCount (SendMsgInfoVO searchVO);  
	
	public List<SendMsgInfoVO>  selectAgentOrderLst(SendMsgInfo searchVO);  
	//다중 처리 msgSeq 값 필요 없을떄
	public int insertSendMsgInfoManageList(List<SendMsgInfo> list);
	//단일 처리 msgSeq 값 필요할떄
	public int insertSendMsgInfoManage(SendMsgInfo vo);
	
	public int updateSendMsgInfoManage(SendMsgInfo vo);
	
}
