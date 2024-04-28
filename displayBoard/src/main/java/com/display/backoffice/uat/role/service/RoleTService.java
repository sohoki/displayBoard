package com.display.backoffice.uat.role.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.display.backoffice.uat.role.models.RoleInfo;
import com.display.backoffice.uat.role.models.dto.RoleInfoRequestDto;

public interface RoleTService {

	
	 List<Map<String, Object>> selectRoleInfoPageList( Map<String, Object> params);
	
     int updateRoleInfo(RoleInfoRequestDto vo);
    
     int deleteRoleInfo(String roleId);

	 Optional<RoleInfo> selectRoleInfoDetail(String roleId);
}
