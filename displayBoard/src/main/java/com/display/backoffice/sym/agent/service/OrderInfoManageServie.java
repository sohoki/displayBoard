package com.display.backoffice.sym.agent.service;

import org.springframework.stereotype.Service;
import com.display.backoffice.sym.agent.mapper.OrderInfoManageMapper;
import com.display.backoffice.sym.agent.models.OrderInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public  class OrderInfoManageServie {

	
	private final OrderInfoManageMapper orderMapper;

	
	public int selectAgentOrderCnt(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.selectAgentOrderCnt(agentCode);
	}

	
	public OrderInfo selectAgentOrderList(String agentCode) {
		// TODO Auto-generated method stub
		return orderMapper.selectAgentOrderList(agentCode);
	}

	
	public int updateOrderCheck(String orderSeq) {
		// TODO Auto-generated method stub
		return orderMapper.updateOrderCheck(orderSeq);
	}
}
