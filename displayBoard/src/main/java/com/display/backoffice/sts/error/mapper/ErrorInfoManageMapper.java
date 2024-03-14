package com.display.backoffice.sts.error.mapper;

import java.util.List;
import com.display.backoffice.sts.error.models.ErrorInfo;
import com.display.backoffice.sts.error.models.ErrorInfoVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ErrorInfoManageMapper")
public interface ErrorInfoManageMapper {
	
    public List<ErrorInfoVO> selectErrorMessage(ErrorInfoVO searchVO);
    
    public int selectErrorMessageCnt(ErrorInfoVO searchVO);
	
    public int insertErrorMessage(ErrorInfo vo);

    public int deleteErrorMessage(String errorSeq);

}
