package com.display.backoffice.bas.menu.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.display.backoffice.bas.menu.mapper.MenuCreateManageMapper;
import com.display.backoffice.bas.menu.models.MenuCreatInfo;
import com.display.backoffice.bas.menu.models.dto.menuCreatdetailInfoReqDto;
import com.display.backoffice.util.service.UtilInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class MenuCreateManageService {

	@Autowired
	private MenuCreateManageMapper createMapper;

	
	
	public List<Map<String, Object>> selectMenuCreatList(String authorCode) throws Exception {
		return createMapper.selectMenuCreatList_D(authorCode);
	}

	
	public List<Map<String, Object>> selectMenuCreatList_Author(String roleId, String systemCode, String hidMenuGubun) throws Exception {
		return createMapper.selectMenuCreatList_Author(roleId, systemCode, hidMenuGubun);
	}
	
	
	public int selectMenuCreatManagTotCnt(MenuCreatInfo searchKeyword) throws Exception {
		return createMapper.selectMenuCreatCnt_S(searchKeyword);
	}

	
	public int selectUsrByPk(String empNo) throws Exception {
		return createMapper.selectUsrByPk(empNo);
	}

	
	public MenuCreatInfo selectAuthorByUsr(String empNo) throws Exception {
		return createMapper.selectAuthorByUsr(empNo);
	}

	
	public List<Map<String, Object>> selectMenuCreatManagList(Map<String, Object> searchVO) throws Exception {
		return createMapper.selectMenuCreatManageList_D(searchVO);
	}

	
	@Transactional(readOnly = false)
	public void insertMenuManage_System(List<menuCreatdetailInfoReqDto> list) throws Exception {
		
		try {
			createMapper.insertMenuManage_System(list);
			
			MenuCreatInfo menuCreatVO = new MenuCreatInfo();
			menuCreatVO.setRoleId(list.get(0).getRoleId());
			
			int AuthorCnt = createMapper.selectMenuCreatCnt_S(menuCreatVO);
			if (AuthorCnt > 0) {
				createMapper.deleteMenuCreat_S(menuCreatVO);
			}
					
			for (menuCreatdetailInfoReqDto menu : list) {
				MenuCreatInfo info = MenuCreatInfo.builder()
												.roleId(menu.getRoleId())
												.menuNo(menu.getMenuNo())
												.userId("SYSTEM")
												.build();
				createMapper.insertMenuCreat_S(info);
			}
		}catch(Exception e) {
			log.error("insertMenuManage_System error:" + e.toString());
		}
	}
	
	@Transactional(readOnly = false)
	public void insertMenuCreatList(String authorCode, String userId, 
									String checkedMenuNoForInsert, String hid_menuGubun, List<Map<String, Object>> menuList) throws Exception {
		int AuthorCnt = 0;
		
		MenuCreatInfo menuCreatVO = new MenuCreatInfo();
		menuCreatVO.setRoleId(authorCode);
		
		AuthorCnt = createMapper.selectMenuCreatCnt_S(menuCreatVO);
		if (AuthorCnt > 0) {
			createMapper.deleteMenuCreat_S(menuCreatVO);
		}
		if (hid_menuGubun.equals("MENU_GUBUN_1")) {
			checkedMenuNoForInsert = checkedMenuNoForInsert.contains(",0") == false ? checkedMenuNoForInsert = checkedMenuNoForInsert.concat(",0") : checkedMenuNoForInsert;
	        List<String> insertMenuNo =  UtilInfoService.dotToList(checkedMenuNoForInsert).stream().distinct().collect(Collectors.toList());			
			for (String menu : insertMenuNo) {
				menuCreatVO.setRoleId(authorCode);
				menuCreatVO.setMenuNo(menu);
				menuCreatVO.setUserId(userId);
				createMapper.insertMenuCreat_S(menuCreatVO);
			}
		}else {
			for (Map<String, Object> menuBasic: menuList) {
				menuCreatVO.setRoleId(authorCode);
				menuCreatVO.setMenuNo(menuBasic.get("id").toString());
				menuCreatVO.setMenuBasicInfo(menuBasic.get("basicMenu").toString());
				menuCreatVO.setUserId(userId);
				createMapper.insertMenuCreat_S(menuCreatVO);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public int deleteMenuCreat_S(String authorCode) throws Exception {
		MenuCreatInfo menuCreatVO = new MenuCreatInfo();
		menuCreatVO.setRoleId(authorCode);
		return createMapper.deleteMenuCreat_S(menuCreatVO);
	}
}
