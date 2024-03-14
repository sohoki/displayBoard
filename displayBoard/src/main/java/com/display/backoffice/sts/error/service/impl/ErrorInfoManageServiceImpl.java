package com.display.backoffice.sts.error.service.impl;

import java.util.List;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.display.backoffice.sts.error.mapper.ErrorInfoManageMapper;
import com.display.backoffice.sts.error.models.ErrorInfo;
import com.display.backoffice.sts.error.models.ErrorInfoVO;
import com.display.backoffice.sts.error.service.ErrorInfoManageService;

@Service("ErrorInfoManageService")
public class ErrorInfoManageServiceImpl extends EgovAbstractServiceImpl implements ErrorInfoManageService {

	@Autowired
	private ErrorInfoManageMapper errorMapper;
	
	

	@Override
	public List<ErrorInfoVO> selectErrorMessage(ErrorInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return errorMapper.selectErrorMessage(searchVO);
	}

	@Override
	public int insertErrorMessage(String errorMessage) throws Exception {
		// TODO Auto-generated method stub
		
		ErrorInfo vo = new ErrorInfo();
		String[] errorInfp = errorMessage.split(":");
		int ret = 0;
		if (errorInfp.length>1){
			vo.setErrorType(errorInfp[0]);
			vo.setErrorMessage(errorInfp[1]);
			ret = errorMapper.insertErrorMessage(vo);
		}else {
			System.out.println("not string");
		}
		return ret;
	}

	@Override
	public int deleteErrorMessage(String errorSeq) throws Exception {
		// TODO Auto-generated method stub
		return errorMapper.deleteErrorMessage(errorSeq);
	}

	@Override
	public int selectErrorMessageCnt(ErrorInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return errorMapper.selectErrorMessageCnt(searchVO);
	}

}
