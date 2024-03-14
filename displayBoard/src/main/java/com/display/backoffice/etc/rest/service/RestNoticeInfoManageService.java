package com.display.backoffice.etc.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.etc.rest.mapper.RestInfoManageMapper;
import com.display.backoffice.etc.rest.mapper.RestNoticeInfoManageMapper;
import com.display.backoffice.etc.rest.models.RestNoticeInfo;
import com.display.backoffice.etc.rest.models.RestNoticeInfoVO;
import com.display.backoffice.sym.agent.mapper.AgentInfoManageMapper;
import com.display.backoffice.sym.monter.mapper.DisplayPageInfoManageMapper;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RequiredArgsConstructor
@Service
public class RestNoticeInfoManageService {
	
	
	private final RestNoticeInfoManageMapper noticecMapper;

	
	public List<RestNoticeInfoVO> selectRestNoticeInfoLeftListByPagination(
			RestNoticeInfoVO search) throws Exception {
		// TODO Auto-generated method stub
		return noticecMapper.selectRestNoticeInfoLeftListByPagination(search);
	}

	
	public RestNoticeInfo selectRestNoticeInfo(String noticeSeq)
			throws Exception {
		// TODO Auto-generated method stub
		return noticecMapper.selectRestNoticeInfo(noticeSeq);
	}

	
	public int updateRestNoticeInfo(RestNoticeInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		return vo.getMode().equals("Ins") ?  noticecMapper.insertRestNoticeInfo(vo) : noticecMapper.updateRestNoticeInfo(vo);
	}

	public int deleteRestNoticeInfo(String noticeSeq) throws Exception {
		// TODO Auto-generated method stub
		return noticecMapper.deleteRestNoticeInfo(noticeSeq);
	}
}
