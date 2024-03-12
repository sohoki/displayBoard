package com.display.backoffice.bas.code.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.bas.code.models.CmmnClCode;

@Mapper
public interface EgovCmmnClCodeManageMapper {

    public List<Map<String, Object>> selectCmmnClCodeListByPagination(@Param("params") Map<String, Object> vo);
	
	public List<Map<String, Object>> selectCmmnClCodeList();
	
	public Optional<CmmnClCode> selectCmmnClCodeDetail(String clCode);
	
	public int insertCmmnClCode(CmmnClCode vo);
	
	public int updateCmmnClCode(CmmnClCode vo);
	
	public int deleteCmmnClCode(String clCode);
}
