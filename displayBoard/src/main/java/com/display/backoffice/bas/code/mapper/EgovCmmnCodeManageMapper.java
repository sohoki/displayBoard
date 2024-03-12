package com.display.backoffice.bas.code.mapper;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import com.display.backoffice.bas.code.models.CmmnCode;
import com.display.backoffice.bas.code.models.dto.CmmnCodeDto;

@Mapper
public interface EgovCmmnCodeManageMapper {

    public List<CmmnCodeDto> selectCmmnCodeListByPagination(@Param("params") Map<String, Object> params);
	
	public List<CmmnCodeDto> selectCmmnCodeList();
	
	public CmmnCodeDto selectCmmnCodeDetail(String codeId);
	
	public int insertCmmnCode(CmmnCode vo);
	
	public int updateCmmnCode(CmmnCode vo);
	
	public int deleteCmmnCode(String codeId);
}
