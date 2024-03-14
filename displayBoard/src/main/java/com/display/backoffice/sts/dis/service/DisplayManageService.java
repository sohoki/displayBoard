package com.display.backoffice.sts.dis.service;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.display.backoffice.sts.dis.mapper.DisplayMapper;

import egovframework.com.cmm.EgovMessageSource;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DisplayManageService {

	
	private final DisplayMapper displayMapper;
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;

	
	public List<Map<String, Object>> selectDisplayGq() {
		// TODO Auto-generated method stub
		return displayMapper.selectDisplayGq();
	}

	
	public List<Map<String, Object>> selectDisplayAi() {
		// TODO Auto-generated method stub
		return displayMapper.selectDisplayAi();
	}

	
	public List<Map<String, Object>> selectDisplayAs() {
		// TODO Auto-generated method stub
		return displayMapper.selectDisplayAs();
	}

	
	public List<Map<String, Object>> selectDisplayAt() {
		// TODO Auto-generated method stub
		return displayMapper.selectDisplayAt();
	}
}
