package com.display.backoffice.sts.sch.service;

import java.util.List;
import javax.annotation.Resource;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.stereotype.Service;
import com.display.backoffice.sts.sch.mapper.ContentScheduleManagerMapper;
import com.display.backoffice.sts.sch.models.ContentScheduleInfo;
import com.display.backoffice.sts.sch.models.ContentScheduleInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class ContentScheduleManagerService {

	
	private final ContentScheduleManagerMapper contentSchedule;
	
	@Resource(name="egovSchIdGnrService")
	private EgovIdGnrService egovConSchIdGnrService;
	
	
	public List<ContentScheduleInfoVO> selectContentSchduleInfoManageListByPagination(ContentScheduleInfoVO searchVO) throws Exception{
		return contentSchedule.selectContentSchduleInfoManageListByPagination(searchVO);
		
	};
	
	public ContentScheduleInfoVO  selectConetntSchduleInfoManageView(String conschCode) throws Exception{
		return contentSchedule.selectConetntSchduleInfoManageView(conschCode);
	};
	
	public String conPageDetail(String displaySeq, String fileGubun)throws Exception{
		return null;
	};
	
	public int updateContentSchduleInfoManage(ContentScheduleInfo vo) throws Exception{
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			vo.setConschCode(egovConSchIdGnrService.getNextStringId());
			ret = contentSchedule.insertContentSchduleInfoManage(vo);
		}else {
			ret = contentSchedule.updateContentSchduleInfoManage(vo);
		}
		return ret ;
	};
	
	public int deleteContentSchduleInfoManage(String conschCode) throws Exception{
		return contentSchedule.deleteContentSchduleInfoManage(conschCode);
	};
	
}
