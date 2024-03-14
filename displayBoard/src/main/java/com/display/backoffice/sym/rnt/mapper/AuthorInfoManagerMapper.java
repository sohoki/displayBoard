package com.display.backoffice.sym.rnt.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.display.backoffice.sym.rnt.models.AuthorInfo;

@Mapper
public interface AuthorInfoManagerMapper {
	public List<AuthorInfo> selectAuthorIInfoManageCombo();
}
