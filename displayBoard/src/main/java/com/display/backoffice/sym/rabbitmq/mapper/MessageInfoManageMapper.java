package com.display.backoffice.sym.rabbitmq.mapper;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.display.backoffice.sym.rabbitmq.models.MessageInfo;

@Mapper
public interface MessageInfoManageMapper {

	
	public int receiveMessageInfo(MessageInfo info);
}
