package egovframework.com.cmm;

import java.util.List;

import com.display.backoffice.uat.uia.models.UserRoleInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title="AdminLoginVO : 관리자 로그인VO " )
public class AdminLoginVO {
	
	
	private String adminId;
	private String adminName;
	private String adminPassword;
	private String adminEmail;
	private String adminTel;
	private String regDate;
	private String updatePassword;
	private String lockYn;
	private String useYn;
	private String empNo;
	private String centerId;
	private String partId;
	private String partNm;
	private String userIp = "";
	private String adminStatus;
	private String adminPosition;
	private String roleGubun;
	private String roleId;
	private String insttCode;
	
	
	private List<UserRoleInfo> roleInfo;  
}
