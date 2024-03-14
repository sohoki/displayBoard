package com.display.backoffice.sts.message.models;

import java.io.Serializable;
import java.util.List;

import com.display.backoffice.sts.error.models.ErrorInfo;
import com.display.backoffice.sts.message.models.SendMessageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor	
public class SendMessageInfoVO extends SendMessageInfo implements Serializable  {

	
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
    private int recordCountPerPage = 1;
    //배열 형태로 메세지 받기
    private List<String> agentCodeLst;;
    private String agentNm = "";;
    private String agentRemark = "";;
    private String adminLevel;
    private String partId;
    private String centerNm;
    private String agentFloor;
    private int totalRecordCount;
    
    
    
}
