package com.display.backoffice.uat.role.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.display.backoffice.uat.role.mapper.RoleInfoManageMapper;
import com.display.backoffice.uat.role.models.RoleInfo;
import com.display.backoffice.uat.role.models.dto.RoleInfoRequestDto;
import com.display.backoffice.uat.role.service.RoleTService;


@Service
public class RoleManageServiceImpl extends EgovAbstractServiceImpl implements RoleTService{

	
	@Autowired
	private RoleInfoManageMapper roleMapper;
	
	@Override
	public List<Map<String, Object>> selectRoleInfoPageList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return roleMapper.selectRoleInfoPageList(params);
	}


	@Override
	public int updateRoleInfo(RoleInfoRequestDto vo) {
		// TODO Auto-generated method stub
		return vo.getMode().equals("Ins") ? roleMapper.insertRoleInfo(vo) : roleMapper.updateRoleInfo(vo);
	}

	@Override
	public int deleteRoleInfo(String roleId) {
		// TODO Auto-generated method stub
		return roleMapper.deleteRoleInfo(roleId);
	}

	@Override
	public Optional<RoleInfo> selectRoleInfoDetail(String roleId) {
		// TODO Auto-generated method stub
		return roleMapper.selectRoleInfoDetail(roleId);
	}

}
