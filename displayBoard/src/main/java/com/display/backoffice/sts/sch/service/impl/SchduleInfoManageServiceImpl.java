package com.display.backoffice.sts.sch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sts.message.mapper.SendMessageManageMapper;
import com.display.backoffice.sts.sch.mapper.SchduleInfoManageMapper;
import com.display.backoffice.sts.sch.models.SchduleInfo;
import com.display.backoffice.sts.sch.models.SchduleInfoVO;
import com.display.backoffice.sts.sch.service.SchduleInfoManageService;

@Service("SchduleInfoManageService")
public class SchduleInfoManageServiceImpl extends  EgovAbstractServiceImpl implements SchduleInfoManageService{
	
	
	@Autowired
	private SchduleInfoManageMapper schMapper;
	
	@Autowired
	private SendMessageManageMapper sendMapper;

	@Resource(name="egovSchIdGnrService")
	private EgovIdGnrService egovSchIdGnrService;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Override
	public ModelAndView selectSchduleInfoManageListByPagination( SchduleInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		  
		
		  ModelAndView model = new ModelAndView();
		  try{
			  if(  searchVO.getPageUnit() > 0  ){    	   
		    	   searchVO.setPageUnit(searchVO.getPageUnit());
			  }else {
					   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			  }
			  searchVO.setPageSize(propertiesService.getInt("pageSize"));
		       
		       
		       /** pageing */       
		   	  PaginationInfo paginationInfo = new PaginationInfo();
			  paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
			  paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
			  paginationInfo.setPageSize(searchVO.getPageSize());

			  searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
			  searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
			  //수정
			  searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			  
			  
	           List<SchduleInfoVO> schduleList = schMapper.selectSchduleInfoManageListByPagination(searchVO);
	        
		       model.addObject("resultList",   schduleList);
		      
		       
		       int totCnt =  schduleList.size() > 0 ? schduleList.get(0).getTotalRecordCount() : 0; ;       
			   paginationInfo.setTotalRecordCount(totCnt);
		       model.addObject("paginationInfo", paginationInfo);
		       model.addObject("totalCnt", totCnt);
		  }catch(Exception e){
			  throw e;
			  
		  }
		  return model;
		
	}

	@Override
	public SchduleInfoVO selectSchduleInfoManageDetail(String schCode)
			throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectSchduleInfoManageDetail(schCode);
	}

	@Override
	public SchduleInfoVO selectSchduleInfoManageView(String schCode)
			throws Exception {
		// TODO Auto-generated method stub
		
		SchduleInfoVO schinfo = schMapper.selectSchduleInfoManageView(schCode);
		if (schinfo.getSchStartTime().contains(":")){
			String[] time_s = schinfo.getSchStartTime().split(":");
			schinfo.setSchStartTime1(time_s[0]);
			schinfo.setSchStartTime2(time_s[1]);	
		}
		
		if (schinfo.getSchEndTime().contains(":")){
			String[] time_e = schinfo.getSchEndTime().split(":");
			schinfo.setSchEndTime1(time_e[0]);
			schinfo.setSchEndTime2(time_e[1]);	
		}
		
		
		
		return schMapper.selectSchduleInfoManageView(schCode);
	}

	@Override
	public int selectSchduleInfoManageListTotCnt_S(SchduleInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectSchduleInfoManageListTotCnt_S(searchVO);
	}

	
	@Override
	public SchduleInfo updateSchduleInfoManage(SchduleInfo vo) throws Exception {
		// TODO Auto-generated method stub
		
		
		int ret = 0;
		String schCode = "";
		if (vo.getMode().equals("Ins")){
			schCode = egovSchIdGnrService.getNextStringId();
			vo.setSchCode(schCode);
			ret = schMapper.insertSchduleInfoManage(vo);
		}else {
			//수정 페이지 수정 이후 다시 정리 하기 
			ret = schMapper.updateSchduleInfoManage(vo);
			sendMapper.updateSendMessage(vo.getSchCode());
			
		}
		SchduleInfo info = null;
		if ( ret > 0) {
			info = schMapper.selectSchduleInfoManageView(schCode);
		}
		return info;
	}

	@Override
	public int deleteSchduleInfoManage(String schCode) throws Exception {
		// TODO Auto-generated method stub
		return schMapper.deleteSchduleInfoManage(schCode);
	}

	@Override
	public List<SchduleInfo> selectScheduleSendInfo(String agentNm) throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectScheduleSendInfo(agentNm);
	}

	@Override
	public int selectScheduleSendInfoCnt(String agentNm) throws Exception {
		// TODO Auto-generated method stub
		return schMapper.selectScheduleSendInfoCnt(agentNm);
	}
	
	
	
	

}
