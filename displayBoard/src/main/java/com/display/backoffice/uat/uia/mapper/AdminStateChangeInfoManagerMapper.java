package com.display.backoffice.uat.uia.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.uat.uia.models.AdminStateChangeInfo;


@Mapper
public interface AdminStateChangeInfoManagerMapper {

	public int deleteAdminStateChangeAllManage(String adminId) throws Exception;
	
	public int deleteAdminStateChangeSeqManage(String manageSeq) throws Exception;
	
	public int insertAdminStateChangeManage(AdminStateChangeInfo vo) throws Exception;
	
	public List<?> selectAdminStateChangeManageListByPagination(@Param("params") Map<String, Object> params) throws Exception;
}
