package com.display.backoffice.bas.cnt.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.display.backoffice.bas.cnt.mapper.CenterInfoManageMapper;
import com.display.backoffice.bas.cnt.models.CenterInfoVO;
import com.display.backoffice.bas.cnt.models.dto.CenterInfoReqDto;
import egovframework.com.cmm.EgovMessageSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CenterInfoService {

	
	private final CenterInfoManageMapper centerMapper;

	@Autowired
	protected EgovPropertyService propertiesService;
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	
	public List<CenterInfoVO> selectCenterInfoPageInfoManageListByPagination(Map<String, Object> searchVO) throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageListByPagination(searchVO);
	}

	
	public List<CenterInfoVO> selectCenterCombo() throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageCombo();
	}
	
	public Optional<CenterInfoVO> selectCenterInfoDetail(String centerId)
			throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageDetail(centerId);
	}

	
	public int updateCenterInfoManage(CenterInfoReqDto vo) throws Exception {
		// TODO Auto-generated method stub
		return vo.getMode().equals("Ins") ? centerMapper.insertCenterInfoManage(vo) : centerMapper.updateCenterInfoManage(vo);
	}
	
	public int deleteCenterInfoManage(String centerId) {
		return centerMapper.deleteCenterInfoManage(centerId);
	}
	public int updateCenterStateChange(CenterInfoReqDto vo) {
		return centerMapper.updateCenterStateChange(vo);
	}
	
}

