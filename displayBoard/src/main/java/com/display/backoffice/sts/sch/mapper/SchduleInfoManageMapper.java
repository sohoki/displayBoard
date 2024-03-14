package com.display.backoffice.sts.sch.mapper;

import java.util.List;

import com.display.backoffice.sts.sch.models.SchduleInfo;
import com.display.backoffice.sts.sch.models.SchduleInfoVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;


@Mapper
public interface SchduleInfoManageMapper {

	public List<SchduleInfoVO> selectSchduleInfoManageListByPagination(SchduleInfoVO searchVO);
	
	public SchduleInfoVO selectSchduleInfoManageDetail(String schCode);
	
	public SchduleInfoVO selectSchduleInfoManageView(String schCode);
	
	public List<SchduleInfo> selectScheduleSendInfo(String agentCode);

	public int selectScheduleSendInfoCnt(String agentCode);
	
	public int selectSchduleInfoManageListTotCnt_S(SchduleInfoVO searchVO);
	
	public int insertSchduleInfoManage(SchduleInfo vo);
	
	public int updateSchduleInfoManage(SchduleInfo vo);
	
	public int deleteSchduleInfoManage(String schCode);
}
