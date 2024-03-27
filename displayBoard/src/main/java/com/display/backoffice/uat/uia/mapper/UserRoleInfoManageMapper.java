package com.display.backoffice.uat.uia.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.uat.uia.models.UserRoleInfo;

@Mapper
public interface UserRoleInfoManageMapper {

	public List<UserRoleInfo> userRoleInfoSelectList(String userId);
	
    
    @SuppressWarnings("rawtypes")
    public int deleteUserRoleList(List list);
    
    public int deleteUserRole(String userId);
}
