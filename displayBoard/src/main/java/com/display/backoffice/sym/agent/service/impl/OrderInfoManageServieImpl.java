package com.display.backoffice.sym.agent.service.impl;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.display.backoffice.sym.agent.mapper.OrderInfoManageMapper;
import com.display.backoffice.sym.agent.models.OrderInfo;
import com.display.backoffice.sym.agent.service.OrderInfoManageServie;

@Service("OrderInfoManageServie")
public class OrderInfoManageServieImpl extends EgovAbstractServiceImpl implements  OrderInfoManageServie{
	
	
	@Autowired
	private OrderInfoManageMapper orderMapper;

	@Override
	public int selectAgentOrderCnt(String agentCode) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.selectAgentOrderCnt(agentCode);
	}

	@Override
	public OrderInfo selectAgentOrderList(String agentCode) {
		// TODO Auto-generated method stub
		return orderMapper.selectAgentOrderList(agentCode);
	}

	@Override
	public int updateOrderCheck(String orderSeq) {
		// TODO Auto-generated method stub
		return orderMapper.updateOrderCheck(orderSeq);
	}
	
	

}

