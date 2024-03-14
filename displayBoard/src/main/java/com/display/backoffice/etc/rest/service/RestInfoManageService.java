package com.display.backoffice.etc.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.display.backoffice.etc.rest.models.RestInfo;
import com.display.backoffice.etc.rest.models.RestInfoVO;
import com.display.backoffice.sym.agent.mapper.AgentInfoManageMapper;
import com.display.backoffice.sym.monter.mapper.DisplayPageInfoManageMapper;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;
import com.display.backoffice.etc.rest.mapper.RestInfoManageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestInfoManageService {
	
	private final RestInfoManageMapper restMapper;

	public List<RestInfoVO> selectRestInfoPageInfoManageListByPagination(
			RestInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return restMapper.selectRestInfoPageInfoManageListByPagination(searchVO);
	}
	
	public RestInfoVO selectRestInfoDetail(String menuSeq) throws Exception {
		// TODO Auto-generated method stub
		return restMapper.selectRestInfoDetail(menuSeq);
	}
	
	public int updateRestInfo(RestInfo RestInfo) throws Exception {
		// TODO Auto-generated method stub
		return RestInfo.getMode().equals("Ins") ? restMapper.insertRestInfo(RestInfo) : restMapper.updateRestInfo(RestInfo);
	}
	
	public int deleteRestInfo(String menuSeq) throws Exception {
		// TODO Auto-generated method stub
		return restMapper.deleteRestInfo(menuSeq);
	}

	public int updateRestGrade(RestInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return restMapper.updateRestGrade(vo);
	}
}
