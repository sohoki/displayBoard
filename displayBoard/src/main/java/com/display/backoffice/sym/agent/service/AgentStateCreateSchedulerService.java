package com.display.backoffice.sym.agent.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.display.backoffice.sts.error.mapper.ErrorInfoManageMapper;
import com.display.backoffice.sts.error.models.ErrorInfo;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;
import com.display.backoffice.sym.state.mapper.AgentStateManageMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("AgentStateCreateSchedulerService")
public class AgentStateCreateSchedulerService {

		
	@Autowired
	private AgentStateManageMapper agentState;
	
	@Autowired
	private ErrorInfoManageMapper errorInfo;
	
	public void agentStateScheduling() throws Exception{
		try{
			agentState.insertAgentStateCreate();
		}catch (RuntimeException re) {
	       log.error("resveBreakScheduling run failed", re);
	       ErrorInfo vo = new ErrorInfo();
	       vo.setErrorType("ERROR_TYPE_1");
	       vo.setErrorMessage("resveBreakScheduling runing error:" + re.toString());
		   errorInfo.insertErrorMessage(vo);
		}catch (Exception e){
			log.error("resveBreakScheduling failed", e);
			ErrorInfo vo = new ErrorInfo();
			vo.setErrorType("ERROR_TYPE_1");
			vo.setErrorMessage("resveBreakScheduling error:" + e.toString());
			errorInfo.insertErrorMessage(vo);
		}
	}
}

