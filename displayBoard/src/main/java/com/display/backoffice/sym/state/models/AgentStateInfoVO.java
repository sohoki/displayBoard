package com.display.backoffice.sym.state.models;

import java.io.Serializable;
import java.util.List;

import com.display.backoffice.sym.monter.models.DetailPageInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgentStateInfoVO extends AgentStateInfo implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;
	/** 검색조건 */
    private String searchCondition = "";    
    /** 검색Keyword */
    private String searchKeyword = "";    
    /** 검색사용여부 */
    private String searchUseYn = "";    
    
    /** 현재페이지 */
    private int pageIndex = 1;    
    /** 페이지갯수 */
    private int pageUnit = 10;    
    /** 페이지사이즈 */
    private int pageSize = 10;    
    private int firstIndex = 1;
    private int lastIndex = 1;    
    private int recordCountPerPage = 10;
    private String searchDay = "";
    private String seachAgentCode = "";
    private List items;
    
    private String agentCodeList;
    
    
    
    

}
