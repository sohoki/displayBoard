package com.display.backoffice.sym.rnt.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.display.backoffice.sym.rnt.mapper.AuthorInfoManagerMapper;
import com.display.backoffice.sym.rnt.models.AuthorInfo;
import com.display.backoffice.sym.rnt.service.AuthorInfoManageService;

@Service("AuthorInfoManageService")
public class AuthorInfoManageServiceImpl extends EgovAbstractServiceImpl implements AuthorInfoManageService {

	
	@Autowired
	private AuthorInfoManagerMapper authMapper;
	
	@Override
	public List<AuthorInfo> selectAuthorIInfoManageCombo() {
		// TODO Auto-generated method stub
		return authMapper.selectAuthorIInfoManageCombo();
	}
	

}