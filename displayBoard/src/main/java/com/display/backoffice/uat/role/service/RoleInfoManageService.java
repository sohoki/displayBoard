package com.display.backoffice.uat.role.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.display.backoffice.uat.role.mapper.RoleInfoManageMapper;
import com.display.backoffice.uat.role.models.RoleInfo;
import com.display.backoffice.uat.role.models.dto.RoleInfoRequestDto;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RoleInfoManageService {

	private final RoleInfoManageMapper roleMapper;
	

	public List<Map<String, Object>> selectRoleInfoPageList(Map<String, Object> params){
		return roleMapper.selectRoleInfoPageList(params);
	}
	
	public List<Map<String, Object>> selectRoleInfoComboList(){
		return roleMapper.selectRoleInfoComboList();
	}
	
	public Optional<RoleInfo> selectRoleInfoDetail(String roleId) {
		return roleMapper.selectRoleInfoDetail(roleId);
	}
	@Transactional(readOnly = false)
    public int updateRoleInfo(RoleInfoRequestDto vo) {
    	return vo.getMode().equals("Ins") ? roleMapper.insertRoleInfo(vo) : roleMapper.updateRoleInfo(vo);
    }
	@Transactional(readOnly = false)
    public int deleteRoleInfo(String roleId) {
    	return roleMapper.deleteRoleInfo(roleId);
    }
	
}
