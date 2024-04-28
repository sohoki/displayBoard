package com.display.backoffice.bas.code.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.display.backoffice.bas.code.models.CmmnDetailCode;
import com.display.backoffice.bas.code.models.dto.CmmnDetailCodeDto;

@Mapper
public interface EgovCmmnDetailCodeManageMapper {

    public List<CmmnDetailCodeDto> selectCmmnDetailCodeListByPagination(String codeId);
	
	public List<CmmnDetailCodeDto> selectCmmnDetailCombo (String code);
	
	public List<CmmnDetailCodeDto> selectCmmnDetailComboLamp (String code);
	
	public List<CmmnDetailCodeDto> selectCmmnDetailComboEtc(@Param("params") Map<String, Object> params);
	
	public CmmnDetailCodeDto selectCmmnDetailCodeDetail(String code);
	
	public CmmnDetailCodeDto selectCmmnDetail(String code);
	
	public List<CmmnDetailCodeDto> selectComboSwcCon();
		
	public int insertCmmnDetailCodeIPCC(CmmnDetailCode vo);
	
	public int insertCmmnDetailCode(CmmnDetailCode vo);
	               
	public int updateCmmnDetailCode(CmmnDetailCode vo);
	
	public int deleteCmmnDetailCode(String code);
	
	public int deleteCmmnDetailCodeId(String value);
	
	public List<CmmnDetailCodeDto> selectCmmnDetailResTypeCombo (Map<String, Object> vo);
}
