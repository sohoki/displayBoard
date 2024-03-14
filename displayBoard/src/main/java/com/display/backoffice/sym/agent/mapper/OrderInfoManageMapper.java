package com.display.backoffice.sym.agent.mapper;

import com.display.backoffice.sym.agent.models.OrderInfo;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface OrderInfoManageMapper {

    public int selectAgentOrderCnt(String agentCode);
	
	public OrderInfo selectAgentOrderList (String agentCode);
	
	public int updateOrderCheck (String orderSeq);
}
