package com.display.backoffice.sts.xml.service;

import java.util.List;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.cnt.mapper.CenterInfoManageMapper;
import com.display.backoffice.bas.cnt.service.CenterInfoService;
import com.display.backoffice.sts.xml.mapper.SendMsgInfoManagerMapper;
import com.display.backoffice.sts.xml.models.SendMsgInfo;
import com.display.backoffice.sts.xml.models.SendMsgInfoVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class SendMsgInfoManageService {

	private final SendMsgInfoManagerMapper sendMsg;
	 
	@Autowired
	protected EgovMessageSource egovMessageSource;

	
	public List<SendMsgInfoVO> selectSendMsgInfoManageListByPagination(SendMsgInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		//ModelAndView model = new ModelAndView(Globals.JSONVIEW);
		return sendMsg.selectSendMsgInfoManageListByPagination(searchVO);
	}

	
	public int selectAgentOrderCount(SendMsgInfo searchVO) throws Exception {
		// TODO Auto-generated method stub
		return sendMsg.selectAgentOrderCount(searchVO);
	}

	
	public int selectAgentMessageCount(SendMsgInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return sendMsg.selectAgentMessageCount(searchVO);
	}

	
	public List<SendMsgInfoVO> selectAgentOrderLst(SendMsgInfo searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return sendMsg.selectAgentOrderLst(searchVO);
	}


	
	public ModelAndView insertSendMsgInfoManageList(List<SendMsgInfo> vo) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView model = new ModelAndView();
		try{
			 sendMsg.insertSendMsgInfoManageList(vo);
			 model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			 model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.update"));	
		}catch(NullPointerException e){
			log.error("insertSendMsgInfoManage ERROR:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			throw e;
		}catch(Exception e){
			log.error("insertSendMsgInfoManage ERROR:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			throw e;
		}
		return model;
	}
	
	public int insertSendMsgInfoManage(SendMsgInfo vo)throws Exception {
		// TODO Auto-generated method stub
		int ret =0;
		try{
			ret = sendMsg.insertSendMsgInfoManage(vo);
		}catch(NullPointerException e){
			log.error("insertSendMsgInfoManage ERROR:" + e.toString());
			throw e;
		}catch(Exception e){
			log.error("insertSendMsgInfoManage ERROR:" + e.toString());
			throw e;
		}
		return sendMsg.insertSendMsgInfoManage(vo);
	}

	
	public int updateSendMsgInfoManage(SendMsgInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return sendMsg.updateSendMsgInfoManage(vo);
	}
}
