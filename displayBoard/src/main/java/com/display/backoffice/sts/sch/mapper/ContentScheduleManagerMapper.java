package com.display.backoffice.sts.sch.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.sts.sch.models.ContentScheduleInfo;
import com.display.backoffice.sts.sch.models.ContentScheduleInfoVO;


@Mapper
public interface ContentScheduleManagerMapper {

	
	public List<ContentScheduleInfoVO> selectContentSchduleInfoManageListByPagination(ContentScheduleInfoVO searchVO);
	
	public ContentScheduleInfoVO  selectConetntSchduleInfoManageView(String conschCode);
	
	public int insertContentSchduleInfoManage(ContentScheduleInfo vo);
	
	public int updateContentSchduleInfoManage(ContentScheduleInfo vo);
	
	public int deleteContentSchduleInfoManage(String conschCode);
}
