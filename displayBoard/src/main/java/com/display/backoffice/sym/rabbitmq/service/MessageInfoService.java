package com.display.backoffice.sym.rabbitmq.service;

import org.springframework.stereotype.Service;

import com.display.backoffice.sym.rabbitmq.mapper.MessageInfoManageMapper;
import com.display.backoffice.sym.rabbitmq.models.MessageInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageInfoService {

	
	private final MessageInfoManageMapper messageMapper;
	
	public int ReceiveMessageInfo (MessageInfo info) {
		return messageMapper.receiveMessageInfo(info);
		
	}
}
