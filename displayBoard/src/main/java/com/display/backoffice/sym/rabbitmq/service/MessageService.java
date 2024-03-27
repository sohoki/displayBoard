package com.display.backoffice.sym.rabbitmq.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.display.backoffice.sym.rabbitmq.models.MessageInfo;
import com.display.backoffice.sym.rabbitmq.models.dto.MessageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

	
	/*
	@Value("${rabbitmq.topic.name}")
	private String exchangeName;

	@Value("${rabbitmq.topic.key}")
	private String routingKey;

	*/
	private final RabbitTemplate rabbitTemplate;
	
	private final MessageInfoService messageService;

	/**
	 * Queue로 메시지를 발행
	 *
	 * @param messageDto 발행할 메시지의 DTO 객체
	 */
	public void sendMessage(MessageDto messageDto, 
							String sendGubun, 
							String exchangeName,
							String routingKey) {
		log.info("message sent: {}", messageDto.toString());
		//message 보내기 
		switch (sendGubun) {
			case "Direct" : 
				rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDto);
				break;
			case "Topic" :
				rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDto);
				break;
			default :
				rabbitTemplate.convertAndSend(exchangeName, "", messageDto);
		}
	}

	/**
	 * Queue에서 메시지를 구독
	 *  
	 * @param messageDto 구독한 메시지를 담고 있는 MessageDto 객체
	 */
	@RabbitListener(queues = "${rabbitmq.queue.name}")
	public void reciveMessage(MessageDto messageDto) {
	  log.info("display Received message: {}", messageDto.toString());
	  
	  //메세지가 되었을떄 처리 하기 
	  
	  MessageInfo info = MessageInfo.builder()
			  		     .messageSystemcode("display")
			  		     .messageprocessGubun(messageDto.getProcessGubun())
			  		     .messageprocessName(messageDto.getProcessName())
			  		     .id(messageDto.getId())
			  			 .build();
	  messageService.ReceiveMessageInfo( info);
	  
	}
}
