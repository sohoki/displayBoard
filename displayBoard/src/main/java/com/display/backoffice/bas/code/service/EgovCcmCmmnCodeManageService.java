package com.display.backoffice.bas.code.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.bas.code.mapper.EgovCmmnCodeManageMapper;
import com.display.backoffice.bas.code.models.CmmnCode;
import com.display.backoffice.bas.code.models.dto.CmmnCodeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EgovCcmCmmnCodeManageService {

	private final EgovCmmnCodeManageMapper cmmMapper;
	
	public List<CmmnCodeDto> selectCmmnCodeListByPagination(Map<String, Object> params){
		return cmmMapper.selectCmmnCodeListByPagination(params);
	}
	
	public List<CmmnCodeDto> selectCmmnCodeList(){
		return cmmMapper.selectCmmnCodeList();
	}
	
	public CmmnCodeDto selectCmmnCodeDetail(String codeId) {
		return cmmMapper.selectCmmnCodeDetail(codeId);
	}
	
	@Transactional(readOnly = false)
	public int updateCmmnCode(CmmnCode vo) {
		return vo.getMode().equals("Ins") ? cmmMapper.insertCmmnCode(vo) : cmmMapper.updateCmmnCode(vo);
	}
	@Transactional(readOnly = false)
	public int deleteCmmnCode(String codeId) {
		return cmmMapper.deleteCmmnCode(codeId);
	}
}
