package com.display.backoffice.uat.uia.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="AdminStateChangeInfo : 관리자 변경 내역 테이블 " )
public class AdminStateChangeInfo {

	private int manageSeq;
	private String userId;
	private String insttCode;
	private String partId;
	private String adminStatus;
	private String adminPosition;
	private String updateId;
	private String stateRegdate;
	private String changeDc;
}
