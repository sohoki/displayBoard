package com.display.backoffice.sym.rnt.models;

import java.io.Serializable;

import com.display.backoffice.sym.monter.models.DetailPageInfo;

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
public class AuthorInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String authorCode;
	private String authorNm;
	private String authorDc;
	private String authorCreateDe;
	
	
	
	

}
