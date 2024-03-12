package com.display.backoffice.bas.code.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.bas.code.mapper.EgovCmmnClCodeManageMapper;
import com.display.backoffice.bas.code.models.CmmnClCode;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class EgovCcmCmmnClCodeManageService {

	
	private final EgovCmmnClCodeManageMapper cmmMapper;
	
	
    public List<Map<String, Object>> selectCmmnClCodeListByPagination(Map<String, Object> params){
    	
    	return cmmMapper.selectCmmnClCodeListByPagination(params);
    }
	
	public List<Map<String, Object>> selectCmmnClCodeList(){
		return cmmMapper.selectCmmnClCodeList();
	}
	
	public Optional<CmmnClCode> selectCmmnClCodeDetail(String clCode){
		return cmmMapper.selectCmmnClCodeDetail(clCode);
	}
	
	@Transactional(readOnly = false)
	public int updateCmmnClCode(CmmnClCode vo) {
		return vo.getMode().equals("Ins") ? cmmMapper.insertCmmnClCode(vo) : cmmMapper.updateCmmnClCode(vo);
	}
	@Transactional(readOnly = false)
	public int deleteCmmnClCode(String clCode) {
		return cmmMapper.deleteCmmnClCode(clCode);
	}
}
