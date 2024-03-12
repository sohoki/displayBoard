package com.display.backoffice.uat.uia.models;

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
public class UniUtilInfo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private String inTable;
	private String inCheckName;
	private String inCondition;
	private int otCnt;
	
	
	
	
}
