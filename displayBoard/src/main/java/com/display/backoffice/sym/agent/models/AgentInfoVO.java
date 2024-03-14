package com.display.backoffice.sym.agent.models;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class AgentInfoVO extends AgentInfo implements Serializable {

	
	
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
    
    private String displayTitle = "";
    private String displayTotalTime = "";
    private String detailSeq_N = "";
    private String agentStateInfo = "";
    
    private int errorCnt = 10;
    private int totalRecordCount = 0;  
    private String centerNm = "";
    private String partNm = "";
    
    private String osType = "";
    private String adminLevel = "";
    private String contentGubun = "";
    
    //검색 관련 내용 추가
    
    private String searchCenterid = "";
    private String searchFloor= "";
    private String searchPartid= "";
    private String searchAgentgubun= "";
    private String searchDisplayseq = "";
    private String searchNotDisplayseq = "";
    
    private String agentCnt = "";
    private String tCnt = "";
    private String statePer = "";
    private String searchAgentState = "";
    private String searchOrderGubun = "";
    
    
    
    
	public String getSearchOrderGubun() {
		return searchOrderGubun;
	}
	public void setSearchOrderGubun(String searchOrderGubun) {
		this.searchOrderGubun = searchOrderGubun;
	}
	public String getSearchNotDisplayseq() {
		return searchNotDisplayseq;
	}
	public void setSearchNotDisplayseq(String searchNotDisplayseq) {
		this.searchNotDisplayseq = searchNotDisplayseq;
	}
	public String getSearchAgentState() {
		return searchAgentState;
	}
	public void setSearchAgentState(String searchAgentState) {
		this.searchAgentState = searchAgentState;
	}
	public String gettCnt() {
		return tCnt;
	}
	public void settCnt(String tCnt) {
		this.tCnt = tCnt;
	}
	public String getStatePer() {
		return statePer;
	}
	public void setStatePer(String statePer) {
		this.statePer = statePer;
	}
	public String getAgentCnt() {
		return agentCnt;
	}
	public void setAgentCnt(String agentCnt) {
		this.agentCnt = agentCnt;
	}
	public String getSearchDisplayseq() {
		return searchDisplayseq;
	}
	public void setSearchDisplayseq(String searchDisplayseq) {
		this.searchDisplayseq = searchDisplayseq;
	}
	public String getSearchCenterid() {
		return searchCenterid;
	}
	public void setSearchCenterid(String searchCenterid) {
		this.searchCenterid = searchCenterid;
	}
	public String getSearchFloor() {
		return searchFloor;
	}
	public void setSearchFloor(String searchFloor) {
		this.searchFloor = searchFloor;
	}
	public String getSearchPartid() {
		return searchPartid;
	}
	public void setSearchPartid(String searchPartid) {
		this.searchPartid = searchPartid;
	}
	public String getSearchAgentgubun() {
		return searchAgentgubun;
	}
	public void setSearchAgentgubun(String searchAgentgubun) {
		this.searchAgentgubun = searchAgentgubun;
	}
	public String getContentGubun() {
		return contentGubun;
	}
	public void setContentGubun(String contentGubun) {
		this.contentGubun = contentGubun;
	}
	public String getAdminLevel() {
		return adminLevel;
	}
	public void setAdminLevel(String adminLevel) {
		this.adminLevel = adminLevel;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getCenterNm() {
		return centerNm;
	}
	public void setCenterNm(String centerNm) {
		this.centerNm = centerNm;
	}
	public String getPartNm() {
		return partNm;
	}
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public int getErrorCnt() {
		return errorCnt;
	}
	public void setErrorCnt(int errorCnt) {
		this.errorCnt = errorCnt;
	}
	public String getAgentStateInfo() {
		return agentStateInfo;
	}
	public void setAgentStateInfo(String agentStateInfo) {
		this.agentStateInfo = agentStateInfo;
	}
	public String getDetailSeq_N() {
		return detailSeq_N;
	}
	public void setDetailSeq_N(String detailSeq_N) {
		this.detailSeq_N = detailSeq_N;
	}
	public String getDisplayTotalTime() {
		return displayTotalTime;
	}
	public void setDisplayTotalTime(String displayTotalTime) {
		this.displayTotalTime = displayTotalTime;
	}
	public String getDisplayTitle() {
		return displayTitle;
	}
	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
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
	public String getMber_Sttus() {
		return mber_Sttus;
	}
	public void setMber_Sttus(String mber_Sttus) {
		this.mber_Sttus = mber_Sttus;
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
		return "AgentInfoVO [searchCondition=" + searchCondition
				+ ", searchKeyword=" + searchKeyword + ", searchUseYn="
				+ searchUseYn + ", mber_Sttus=" + mber_Sttus + ", pageIndex="
				+ pageIndex + ", pageUnit=" + pageUnit + ", pageSize="
				+ pageSize + ", firstIndex=" + firstIndex + ", lastIndex="
				+ lastIndex + ", recordCountPerPage=" + recordCountPerPage
				+ ", displayTitle=" + displayTitle + ", displayTotalTime="
				+ displayTotalTime + ", detailSeq_N=" + detailSeq_N
				+ ", agentStateInfo=" + agentStateInfo + ", errorCnt="
				+ errorCnt + ", totalRecordCount=" + totalRecordCount
				+ ", centerNm=" + centerNm + ", partNm=" + partNm + ", osType="
				+ osType + ", adminLevel=" + adminLevel + ", contentGubun="
				+ contentGubun + ", searchCenterid=" + searchCenterid
				+ ", searchFloor=" + searchFloor + ", searchPartid="
				+ searchPartid + ", searchAgentgubun=" + searchAgentgubun
				+ ", searchDisplayseq=" + searchDisplayseq + ", agentCnt="
				+ agentCnt + ", tCnt=" + tCnt + ", statePer=" + statePer
				+ ", searchAgentState=" + searchAgentState + ", getConnDate()="
				+ getConnDate() + ", getAgentState()=" + getAgentState()
				+ ", getAgentContentgubun()=" + getAgentContentgubun()
				+ ", getAgentFloor()=" + getAgentFloor() + ", getAgentGubun()="
				+ getAgentGubun() + ", getAgentStarttime()="
				+ getAgentStarttime() + ", getAgentEndtime()="
				+ getAgentEndtime() + ", getCenterId()=" + getCenterId()
				+ ", getPartId()=" + getPartId() + ", getUpdateChange()="
				+ getUpdateChange() + ", getUpdateDate()=" + getUpdateDate()
				+ ", getUserId()=" + getUserId() + ", getAgentCode()="
				+ getAgentCode() + ", getAgentNm()=" + getAgentNm()
				+ ", getAgentRemark()=" + getAgentRemark() + ", getAgentIp()="
				+ getAgentIp() + ", getAgentMac()=" + getAgentMac()
				+ ", getDisplaySeq()=" + getDisplaySeq() + ", getAgentUseYn()="
				+ getAgentUseYn() + ", getFrstRegistPnttm()="
				+ getFrstRegistPnttm() + ", getFrstRegistId()="
				+ getFrstRegistId() + ", getLastUpdtPnttm()="
				+ getLastUpdtPnttm() + ", getLastUpusrId()=" + getLastUpusrId()
				+ ", getMode()=" + getMode() + "]";
	}
    
    
}
