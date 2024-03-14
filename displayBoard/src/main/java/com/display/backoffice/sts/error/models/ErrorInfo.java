package com.display.backoffice.sts.error.models;

import java.io.Serializable;
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
public class ErrorInfo implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String errorSeq;
	private String errorType;
	private String errorMessage;
	private String errorRegdate;
	
	

}
