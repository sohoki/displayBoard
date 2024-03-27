package com.display.backoffice.sym.rabbitmq.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfo {

	
	private int messageSeq;
	private String messageSystemcode;
	private String messageprocessName;
	private String messageprocessGubun;
	private String id;
	private String recDate;
}
