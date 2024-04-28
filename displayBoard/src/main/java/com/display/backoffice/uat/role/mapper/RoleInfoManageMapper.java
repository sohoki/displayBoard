package com.display.backoffice.uat.role.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.uat.role.models.RoleInfo;
import com.display.backoffice.uat.role.models.dto.RoleInfoRequestDto;

@Mapper
public interface RoleInfoManageMapper {

	
	public List<Map<String, Object>> selectRoleInfoPageList(@Param("params") Map<String, Object> params);
	
	public List<Map<String, Object>> selectRoleInfoComboList();
	
    public int insertRoleInfo(RoleInfoRequestDto vo);
	
    public int updateRoleInfo(RoleInfoRequestDto vo);
    
    public int deleteRoleInfo(String roleId);

	public Optional<RoleInfo> selectRoleInfoDetail(String roleId);
}
