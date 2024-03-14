package com.display.backoffice.sts.xml.models;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class XmlInfoVO extends XmlInfo implements Serializable {
	
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
    
    private String seachWorkGubun = "";
    private int totalRecordCount = 0;
    
    private String sendGubunTxt = "";
    
    
    
        
    
	public String getSendGubunTxt() {
		return sendGubunTxt;
	}
	public void setSendGubunTxt(String sendGubunTxt) {
		this.sendGubunTxt = sendGubunTxt;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getSeachWorkGubun() {
		return seachWorkGubun;
	}
	public void setSeachWorkGubun(String seachWorkGubun) {
		this.seachWorkGubun = seachWorkGubun;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchUseYn() {
		return searchUseYn;
	}
	public void setSearchUseYn(String searchUseYn) {
		this.searchUseYn = searchUseYn;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageUnit() {
		return pageUnit;
	}
	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
	public int getLastIndex() {
		return lastIndex;
	}
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	@Override
	public String toString() {
		return "XmlInfoVO [searchCondition=" + searchCondition
				+ ", searchKeyword=" + searchKeyword + ", searchUseYn="
				+ searchUseYn + ", pageIndex=" + pageIndex + ", pageUnit="
				+ pageUnit + ", pageSize=" + pageSize + ", firstIndex="
				+ firstIndex + ", lastIndex=" + lastIndex
				+ ", recordCountPerPage=" + recordCountPerPage
				+ ", seachWorkGubun=" + seachWorkGubun + ", totalRecordCount="
				+ totalRecordCount + ", sendGubunTxt=" + sendGubunTxt
				+ ", getSendGubun()=" + getSendGubun() + ", getUserId()="
				+ getUserId() + ", getXmlSeq()=" + getXmlSeq()
				+ ", getWorkGubun()=" + getWorkGubun()
				+ ", getXmlProcessName()=" + getXmlProcessName()
				+ ", getProcessRemark()=" + getProcessRemark()
				+ ", getXmlInputParam()=" + getXmlInputParam()
				+ ", getXmlOutputParam()=" + getXmlOutputParam()
				+ ", getResultCodeExample()=" + getResultCodeExample()
				+ ", getEtc1()=" + getEtc1() + ", getEtc2()=" + getEtc2()
				+ ", getEtc3()=" + getEtc3() + ", getTestOk()=" + getTestOk()
				+ ", getXmlInputParamSample()=" + getXmlInputParamSample()
				+ ", getXmlExplain()=" + getXmlExplain() + ", getMode()="
				+ getMode() + ", getFrstRegistPnttm()=" + getFrstRegistPnttm()
				+ ", getLastRegistPnttm()=" + getLastRegistPnttm()
				+ ", getFrstRegisterId()=" + getFrstRegisterId()
				+ ", getLastRegisterId()=" + getLastRegisterId()
				+ ", getCodeNm()=" + getCodeNm() + ", getProtType()="
				+ getProtType() + "]";
	}
    
    
}


