package com.display.backoffice.bas.cnt.service;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.cnt.mapper.CenterInfoManageMapper;
import com.display.backoffice.bas.cnt.models.CenterInfo;
import com.display.backoffice.bas.cnt.models.CenterInfoVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
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
	
	public CenterInfoVO selectCenterInfoDetail(String centerId)
			throws Exception {
		// TODO Auto-generated method stub
		return centerMapper.selectCenterInfoManageDetail(centerId);
	}

	
	public int updateCenterInfoManage(CenterInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			ret = centerMapper.insertCenterInfoManage(vo);
		}else {
			ret = centerMapper.updateCenterInfoManage(vo);
		}
		return ret;
	}
	
}

