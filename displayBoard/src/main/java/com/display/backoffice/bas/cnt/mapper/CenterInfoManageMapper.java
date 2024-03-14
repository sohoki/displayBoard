package com.display.backoffice.bas.cnt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.display.backoffice.bas.cnt.models.CenterInfo;
import com.display.backoffice.bas.cnt.models.CenterInfoVO;


@Mapper
public interface CenterInfoManageMapper {
	
    public List<CenterInfoVO> selectCenterInfoManageListByPagination(@Param("params") Map<String, Object> vo);
	
    public List<CenterInfoVO> selectCenterInfoManageCombo();
	
    public CenterInfoVO selectCenterInfoManageDetail(String centerId);
	
    public int updateCenterInfoManage(CenterInfo vo);
    
    public int updateFileDetailInfo(CenterInfo vo);
    
    public int insertCenterInfoManage(CenterInfo vo);
}
