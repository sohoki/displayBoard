package com.display.backoffice.bas.menu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.display.backoffice.bas.menu.models.MenuInfo;
import com.display.backoffice.bas.menu.models.dto.MenuInfoRequestDto;

@Mapper
public interface MenuInfoManageMapper {

	/**
	 * 메뉴목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	public List<Map<String, Object>> selectMenuManageList_D(@Param("params") Map<String, Object> vo) ;

	/**
	 * 메뉴목록관리 기본정보를 조회
	 * @param vo ComDefaultVO
	 * @return MenuManageVO
	 * @exception Exception
	 */
	public Map<String, Object> selectMenuManage_D(String menuNo);

	/**
	 * 메뉴목록 기본정보를 등록
	 * @param vo MenuManageVO
	 * @exception Exception
	 */
	public int insertMenuManage_S(MenuInfoRequestDto vo);
	/**
	 * 메뉴목록 기본정보를 수정
	 * @param vo MenuManageVO
	 * @exception Exception
	 */
	public int updateMenuManage_S(MenuInfoRequestDto vo);

	/**
	 * 메뉴목록 기본정보를 삭제
	 * @param vo MenuManageVO
	 * @exception Exception
	 */
	public int deleteMenuManage_S(String menuNo);
	
	public int deleteMenuManage_L(@Param("menuNoList") List<String> menuNoList);
	

	/**
	 * 메뉴 전체목록을 조회
	 * @return list
	 * @exception Exception
	 */
	public List<Map<String, Object>> selectMenuListT_D();


	/**
	 * 메뉴번호 존재여부를 조회
	 * @param vo MenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectMenuNoByPk(@Param("params") Map<String, Object> params);



	/**
	 * 메뉴번호를 상위메뉴로 참조하고 있는 메뉴 존재여부를 조회
	 * @param vo MenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectUpperMenuNoByPk(@Param("params") Map<String, Object> params);




    public int deleteMenuNo(MenuInfo menu);

	/**
	 * 메뉴정보 전체삭제 초기화
	 * @return boolean
	 * @exception Exception
	 */
	public int deleteAllMenuList();

    /**
	 * 메뉴정보 존재여부 조회한다.
	 * @return int
	 * @exception Exception
	 */
    public int selectMenuListTotCnt();


	/*### 메뉴관련 프로세스 ###*/
	/**
	 * MainMenu Head Menu 조회
	 * @param vo MenuManageVO
	 * @return List
	 * @exception Exception
	 */
	public List<Map<String, Object>> selectMainMenuHead(String empNo) ;

	/**
	 * MainMenu Left Menu 조회
	 * @param vo MenuManageVO
	 * @return List
	 * @exception Exception
	 */
	public List<Map<String, Object>> selectMainMenuLeft(String adminId, String url) ;

	/**
	 * MainMenu Head MenuURL 조회
	 * @param vo MenuManageVO
	 * @return  String
	 * @exception Exception
	 */
	public String selectLastMenuURL(String menuNo);

	/**
	 * MainMenu Left Menu 조회
	 * @param vo MenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectLastMenuNo(@Param("params") Map<String, Object> params );

	/**
	 * MainMenu Left Menu 조회
	 * @param vo MenuManageVO
	 * @return int
	 * @exception Exception
	 */
	public int selectLastMenuNoCnt(@Param("params") Map<String, Object> params );
}
