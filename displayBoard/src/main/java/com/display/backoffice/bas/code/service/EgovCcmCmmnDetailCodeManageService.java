package com.display.backoffice.bas.code.service;


import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.bas.code.mapper.EgovCmmnDetailCodeManageMapper;
import com.display.backoffice.bas.code.models.CmmnDetailCode;
import com.display.backoffice.bas.code.models.dto.CmmnDetailCodeDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class EgovCcmCmmnDetailCodeManageService {

	private final EgovCmmnDetailCodeManageMapper detailMapper;
	
	public List<CmmnDetailCodeDto> selectCmmnDetailCodeListByPagination(String codeId){
		return detailMapper.selectCmmnDetailCodeListByPagination(codeId);
	}
	
	public List<CmmnDetailCodeDto> selectCmmnDetailCodeList(String codeId){
		return detailMapper.selectCmmnDetailCodeListByPagination(codeId);
	}
	
	
	public List<CmmnDetailCodeDto> selectCmmnDetailCombo (String code){
		return detailMapper.selectCmmnDetailCombo(code);
	}
	
	public List<CmmnDetailCodeDto> selectCmmnDetailComboLamp (String code){
		return detailMapper.selectCmmnDetailComboLamp(code);
	}
	
	public List<CmmnDetailCodeDto> selectCmmnDetailComboEtc( Map<String, Object> params){
		return detailMapper.selectCmmnDetailComboEtc(params);
	}
	
	public CmmnDetailCodeDto selectCmmnDetailCodeDetail(String code) {
		return  detailMapper.selectCmmnDetailCodeDetail(code);
	}
	
	public CmmnDetailCodeDto selectCmmnDetail(String code){
		return detailMapper.selectCmmnDetail(code);
	}
	@Transactional(readOnly = false)
	public int updateCmmnDetailCode(CmmnDetailCode vo) {
		return vo.getMode().equals("Ins") ? detailMapper.insertCmmnDetailCode(vo) : detailMapper.updateCmmnDetailCode(vo);
	}
	@Transactional(readOnly = false)
	public int deleteCmmnDetailCode(String code) {
		return detailMapper.deleteCmmnDetailCode(code);
	}
	@Transactional(readOnly = false)
	public int deleteCmmnDetailCodeId(String value) {
		return detailMapper.deleteCmmnDetailCodeId(value);
	}
	
	public List<CmmnDetailCodeDto> selectCmmnDetailResTypeCombo (Map<String, Object> vo){
		return detailMapper.selectCmmnDetailResTypeCombo(vo);
	}
}
