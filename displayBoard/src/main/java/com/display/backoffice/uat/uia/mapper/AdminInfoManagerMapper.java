package com.display.backoffice.uat.uia.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.uat.uia.models.AdminInfo;
import com.display.backoffice.uat.uia.models.dto.UserAuthInfoDto;
import com.display.backoffice.uat.uia.models.dto.UserAuthInfoReqDto;



@Mapper
public interface AdminInfoManagerMapper {

	public int deleteAdminUserManage(String mberId) throws Exception;
	
	public int insertAdminUserManage(AdminInfo vo) throws Exception;
	
	public int updatePassChange(AdminInfo vo) throws Exception;
	
	public int updateAdminUserLockManage(String adminId) throws Exception;
	
	public int updateAdminUserManage(AdminInfo adminInfo) throws Exception;
	
	public int updateSystemMenuInfo(List<UserAuthInfoDto> updateAuthList) throws Exception;

	public Optional<AdminInfo> selectAdminUserManageDetail(String adminId) throws Exception;
	
	public List<?> selectAdminUserManageListByPagination(@Param("params") Map<String, Object> params) throws Exception;
	//수정 중
	public List<UserAuthInfoDto> selectSystemUserMenuInfo(UserAuthInfoReqDto req) throws Exception;
	
	public List<UserAuthInfoDto> selectSystemUserMenuInfo_s(String adminId) throws Exception;
	
	//추후 수정 예정
	public int deleteSystemMenuInfo(String userId) throws Exception;
	
	//추후 수정 예정 끝
	public int selectAdminUserMangerIDCheck(String code) throws Exception;
	
	public List<Map<String, Object>> selectAdminUserCombo(@Param("params") Map<String, Object> params) throws Exception;
	
	public int selectAdminPasswordCheck(@Param("params") Map<String, Object> params) throws Exception;
	
}
