package com.display.backoffice.uat.uia.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.uat.uia.models.PartInfo;
import com.display.backoffice.uat.uia.models.PartInfoVO;

@Mapper
public interface PartInfoManageMapper {
	
    public List<PartInfoVO> selectPartInfoPageInfoManageListByPagination(@Param("params") Map<String, Object> params)throws Exception;
	
	public List<PartInfoVO> selectPartInfoCombo(@Param("params") Map<String, Object> params) throws Exception;
	
	public PartInfoVO selectPartInfoDetail(String PartId);
	
	public int insertPartInfoManage(PartInfo vo);
	
	public int updatePartInfoManage(PartInfo vo);
	
	public int updatePartEndInfoManage(PartInfo vo);
	
	public int deletePartInfoManage(String PartId);
}
