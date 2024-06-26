package com.display.backoffice.uat.uia.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.uat.uia.models.AdminInfo;
import com.display.backoffice.uat.uia.models.AdminInfoVO;
import com.display.backoffice.uat.uia.models.AdminStateChangeInfo;
import com.display.backoffice.uat.uia.models.dto.UserAuthInfoDto;
import com.display.backoffice.uat.uia.models.dto.UserAuthInfoReqDto;
import com.display.backoffice.uat.uia.mapper.AdminInfoManagerMapper;
import com.display.backoffice.uat.uia.mapper.AdminStateChangeInfoManagerMapper;
import com.display.backoffice.util.service.UtilInfoService;

import egovframework.com.cmm.service.Globals;
import egovframework.let.utl.sim.service.EgovFileScrty;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public  class AdminInfoManageService {

	
	private final AdminInfoManagerMapper adminMapper;
	
	
	
	@Autowired
	private AdminStateChangeInfoManagerMapper stateChangeMapper;
	
	@Transactional(readOnly = false)
	public int deleteAdminUserManage(String mberId) throws Exception {
		// TODO Auto-generated method stub
		int ret = adminMapper.deleteAdminUserManage(mberId);
		if (ret > 0) {
			//userRoleMapper.deleteUserRole(mberId);
			adminMapper.deleteSystemMenuInfo(mberId);
			stateChangeMapper.deleteAdminStateChangeAllManage(mberId);
		}
		return ret;
	}
	@Transactional(readOnly = false)
	public int updateAdminUserManage(AdminInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		if (vo.getMode().equals(Globals.SAVE_MODE_INSERT)) {
			String enpassword = EgovFileScrty.encryptPassword(vo.getAdminPassword(), vo.getAdminId());
			vo.setAdminPassword(enpassword);
		}
		if (vo.getMode().equals(Globals.SAVE_MODE_INSERT) && adminMapper.selectAdminUserMangerIDCheck(vo.getAdminId()) > 0) {
			return -1;
		}
		int ret = (vo.getMode().equals(Globals.SAVE_MODE_INSERT)) ? adminMapper.insertAdminUserManage(vo) : adminMapper.updateAdminUserManage(vo) ;
		
		return ret;
	}

	
	public Optional<AdminInfo> selectAdminUserManageDetail(String mberId) throws Exception {
		// TODO Auto-generated method stub
		
		Optional<AdminInfo> userInfo = adminMapper.selectAdminUserManageDetail(mberId);
		if (userInfo.isPresent()) {
			UserAuthInfoReqDto req = new UserAuthInfoReqDto();
			req.setUserId(mberId);
			req.setSystemCode(userInfo.get().getSystemcodeUsecode());
			req.setSearchCode(Arrays.asList(userInfo.get().getSystemcodeUsecode().split("\\s*,\\s*")));
			
		}
		return userInfo;
	}
	
	public List<UserAuthInfoDto> selectSystemMenuList(UserAuthInfoReqDto req) throws Exception {
		// TODO Auto-generated method stub
		req.setSearchCode(Arrays.asList(req.getSystemCode().split("\\s*,\\s*")));
		return adminMapper.selectSystemUserMenuInfo(req);
	}

	
	public List<?> selectAdminUserManageListByPagination(Map<String, Object> params)throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminUserManageListByPagination(params);
	}

	
	public int selectAdminUserMangerIDCheck(String code) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminUserMangerIDCheck(code);
	}
	@Transactional(readOnly = false)
	public int updateAdminUserLockManage(String adminId) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.updateAdminUserLockManage(adminId);
	}

	@Transactional(readOnly = false)
	public int updatePassChange(AdminInfo vo) throws Exception {
		// TODO Auto-generated method stub
		String enpassword = EgovFileScrty.encryptPassword(vo.getAdminPassword(), vo.getAdminId());
		vo.setAdminPassword(enpassword);
		
		return adminMapper.updatePassChange(vo);
	}
	
	public int selectAdminPasswordCheck(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		String enpassword = EgovFileScrty.encryptPassword(params.get("adminPassword").toString(), params.get("adminId").toString());
		params.put("adminPasswordEnc", enpassword);
		
		return adminMapper.selectAdminPasswordCheck(params);
	}
	
	public List<Map<String, Object>> selectAdminUserCombo(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminUserCombo(params);
	}
	@Transactional(readOnly = false)
	public int updateAdminStateChange(AdminStateChangeInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return stateChangeMapper.insertAdminStateChangeManage(vo);
	}
	@Transactional(readOnly = false)
	public int deleteAdminStateChangeSeqManage(String gubun, String manageSeq) throws Exception {
		// TODO Auto-generated method stub
		return gubun.equals("All") ? stateChangeMapper.deleteAdminStateChangeAllManage(manageSeq) :  
									 stateChangeMapper.deleteAdminStateChangeSeqManage(manageSeq);
	}
	
	public List<?> selectAdminStateChangeManageListByPagination(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return stateChangeMapper.selectAdminStateChangeManageListByPagination(params);
	}
}
