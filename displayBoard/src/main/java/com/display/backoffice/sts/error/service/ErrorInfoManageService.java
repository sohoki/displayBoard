package com.display.backoffice.sts.error.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.sts.cnt.mapper.ContentFileManagerMapper;
import com.display.backoffice.sts.error.mapper.ErrorInfoManageMapper;
import com.display.backoffice.sts.error.models.ErrorInfo;
import com.display.backoffice.sts.error.models.ErrorInfoVO;

import lombok.RequiredArgsConstructor;


public interface ErrorInfoManageService {
	
	
	List<ErrorInfoVO> selectErrorMessage(ErrorInfoVO searchVO) throws Exception;
	
	int selectErrorMessageCnt(ErrorInfoVO searchVO)throws Exception;
	
	int insertErrorMessage(String errorMessage)throws Exception;
	
	int deleteErrorMessage(String errorSeq)throws Exception;
	

}
