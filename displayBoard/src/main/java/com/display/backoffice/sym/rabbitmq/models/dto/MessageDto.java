package com.display.backoffice.sym.rabbitmq.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title="MessageDto : MQ 전송 DTO " )
public class MessageDto {

	@Schema(description="시스템 코드", example="IPCC")
	private String systemCode;
	@Schema(description="전송 영역명 코드", example="MANAGER/CODE/ROLE/AUTHROLE")
	private String processName;
	@Schema(description="전송 처리 명열 코드", example="INSERT/UPDATE/DELETE/SELECT")
	private String processGubun;
	@Schema(description="JSON URL 주소", example="IPCC 연동 URL")
	private String url;
	@Schema(description="url mehtod", example="GET/POST/PUT/DELETE")
	private String urlMethod;
	@Schema(description="처리 ID")
	private String id;
}
