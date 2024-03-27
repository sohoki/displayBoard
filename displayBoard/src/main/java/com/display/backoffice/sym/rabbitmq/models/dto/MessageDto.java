package com.display.backoffice.sym.rabbitmq.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

	private String systemCode;
	private String processName;
	private String processGubun;
	private String id;
}
