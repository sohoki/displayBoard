package com.display.backoffice.etc.rest.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestInfoVO extends RestInfo   implements  Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 검색조건 */
    private String searchCondition = "";    
    /** 검색Keyword */
    private String searchKeyword = "";    
    /** 검색사용여부 */
    private String searchUseYn = "";    
    
    private String mber_Sttus = "";
    
    
    /** 현재페이지 */
    private int pageIndex = 1;    
    /** 페이지갯수 */
    private int pageUnit = 10;    
    /** 페이지사이즈 */
    private int pageSize = 10;    
    private int firstIndex = 1;
    private int lastIndex = 1;    
    private int recordCountPerPage = 10;
    
    private String centerNm = "";
    private String menuGubunTxt = "";
    private int totalRecordCount = 0;
    private String searchStartDay = "";
    private String searchEndDay = "";
    private String searchMenugubun = "";
    private String searchOrder = "";
    private String searchTime = "";
    
    

}
