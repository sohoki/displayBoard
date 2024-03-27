package com.display.backoffice.bas.cnt.models.dto;

import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor	
@Schema(title="CenterInfoReqDto : 지점 정보 DTO " )
public class CenterInfoReqDto {

	@NotBlank(message="모드를 입력해 주세요.")
	@Schema(description="DB 처리 구분", example="Ins/Edt/Del")
	private String mode;
	private String centerId;
	@NotBlank(message="지점명를 입력해 주세요.")
	@Schema(description="지점명")
	private String centerNm;
	private String centerZipcode;
	private String centerZipcode1;
	private String centerZipcode2;
	private String centerAddr1;
	private String centerAddr2;
	private String centerTel;
	private String centerFax;
	private String userId;
	private String centerImg;
	private String centerUrl;
	private String centerSeatImg;
	private String centerUseYn;
	private String restInfo;
	private String meetingroomInfo;
	private String centerInfo;
	private String insttCode;
	private String adminApprovalYn;
	private String centerFloor;
	private String partId;
	private String centerFloorEnd;
	private String floorInfo;
}