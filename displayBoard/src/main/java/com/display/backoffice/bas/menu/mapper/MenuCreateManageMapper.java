package com.display.backoffice.bas.menu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.display.backoffice.bas.menu.models.MenuCreatInfo;
import com.display.backoffice.bas.menu.models.dto.MenuInfoRequestDto;
import com.display.backoffice.bas.menu.models.dto.menuCreatdetailInfoReqDto;


@Mapper
public interface MenuCreateManageMapper {

	public List<Map<String, Object>>selectMenuCreatManageList_D(@Param("params") Map<String, Object> vo);
	
	public int selectMenuCreatManageTotCnt_S(String searchKeyword);
	
	public MenuCreatInfo selectAuthorByUsr(String empNo);
	
	public int insertMenuManage_System(List<menuCreatdetailInfoReqDto> list);
	
	public int selectUsrByPk(String empNo);
	
	public int selectMenuCreatCnt_S(MenuCreatInfo params);
	
	public List<Map<String, Object>> selectMenuCreatList_D(String authorCode);
	//tree 메뉴 설정
	public List<Map<String, Object>> selectMenuCreatList_Author(String roleId, String systemCode, String hidMenuGubun);
	
	public int insertMenuCreat_S(MenuCreatInfo info);
	
	public int updateMenuCreat_S(MenuCreatInfo info);
	
	public int deleteMenuCreat_S(MenuCreatInfo info);
}
