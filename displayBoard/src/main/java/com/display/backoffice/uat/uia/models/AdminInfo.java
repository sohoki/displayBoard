package com.display.backoffice.uat.uia.models;

import java.io.Serializable;
import java.util.List;
import com.display.backoffice.uat.uia.models.dto.UserAuthInfoDto;



public class AdminInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String adminId;
	private String adminName;
	private String adminPassword;
	private String adminPassword2;
	
	private String adminEmail;
	private String adminTel;
	private String regDate;
	private String updatePassword;
	private String lockYn;
	private String useYn;
	private String mode;
	private String empNo;
	private String partId;
	private int totalRecordCount;
	private String adminStatus;
	//신규
	private String passwordHint;
	private String passwordCnsr;
	
	private String frstRegisterId;
	private String lastUpdusrId;
	
	private String roleId;
	private String pbxExtension;
	private String consultantUseyn;
	private String lastUpdtPnttm;
	private String insttCode;
	private String roleGubun;
	private String systemcodeUsecode;
	private String adminState;
	
	//private List<UserRoleInfo> roleInfo;
	private List<UserAuthInfoDto> authInfo;
	public List<UserAuthInfoDto> getAuthInfo() {
		return authInfo;
	}
	public void setAuthInfo(List<UserAuthInfoDto> authInfo) {
		this.authInfo = authInfo;
	}
	
    public String getSystemcodeUsecode() {
		return systemcodeUsecode;
	}
	public void setSystemcodeUsecode(String systemcodeUsecode) {
		this.systemcodeUsecode = systemcodeUsecode;
	}
	public String getAdminState() {
		return adminState;
	}
	public void setAdminState(String adminState) {
		this.adminState = adminState;
	}
	
	public String getRoleGubun() {
		return roleGubun;
	}
	public void setRoleGubun(String roleGubun) {
		this.roleGubun = roleGubun;
	}
	public String getInsttCode() {
		return insttCode;
	}
	public void setInsttCode(String insttCode) {
		this.insttCode = insttCode;
	}
	public String getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}
	public void setLastUpdtPnttm(String lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}
	/*
	public List<UserRoleInfo> getRoleInfo() {
		return roleInfo;
	}
	public void setRoleInfo(List<UserRoleInfo> roleInfo) {
		this.roleInfo = roleInfo;
	}
	*/
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPbxExtension() {
		return pbxExtension;
	}
	public void setPbxExtension(String pbxExtension) {
		this.pbxExtension = pbxExtension;
	}
	public String getConsultantUseyn() {
		return consultantUseyn;
	}
	public void setConsultantUseyn(String consultantUseyn) {
		this.consultantUseyn = consultantUseyn;
	}
	public String getPasswordHint() {
		return passwordHint;
	}
	public String getFrstRegisterId() {
		return frstRegisterId;
	}
	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}
	public String getLastUpdusrId() {
		return lastUpdusrId;
	}
	public void setLastUpdusrId(String lastUpdusrId) {
		this.lastUpdusrId = lastUpdusrId;
	}
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}
	public String getPasswordCnsr() {
		return passwordCnsr;
	}
	public void setPasswordCnsr(String passwordCnsr) {
		this.passwordCnsr = passwordCnsr;
	}
	public String getAdminStatus() {
		return adminStatus;
	}
	public void setAdminStatus(String adminStatus) {
		this.adminStatus = adminStatus;
	}
	
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public String getAdminPassword2() {
		return adminPassword2;
	}
	public void setAdminPassword2(String adminPassword2) {
		this.adminPassword2 = adminPassword2;
	}
	public String getPartId() {
		return partId;
	}
	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminTel() {
		return adminTel;
	}
	public void setAdminTel(String adminTel) {
		this.adminTel = adminTel;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getUpdatePassword() {
		return updatePassword;
	}
	public void setUpdatePassword(String updatePassword) {
		this.updatePassword = updatePassword;
	}

	public String getLockYn() {
		return lockYn;
	}
	public void setLockYn(String lockYn) {
		this.lockYn = lockYn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	
	
	
}
