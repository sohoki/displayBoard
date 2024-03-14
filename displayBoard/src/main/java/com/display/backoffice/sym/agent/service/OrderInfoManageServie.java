package com.display.backoffice.sym.agent.service;

import com.display.backoffice.sym.agent.models.OrderInfo;


public  interface OrderInfoManageServie {

	int selectAgentOrderCnt(String agentCode)throws Exception;
	
    OrderInfo selectAgentOrderList (String agentCode);
	
	int updateOrderCheck (String orderSeq);
}
