package com.display.backoffice.sym.monter.mapper;

import java.util.List;
import java.util.Map;

import com.display.backoffice.sym.monter.models.DispalyPageInfo;
import com.display.backoffice.sym.monter.models.DispalyPageInfoVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.web.servlet.ModelAndView;

@Mapper
public interface DisplayPageInfoManageMapper {

	
	public List<DispalyPageInfoVO> selectDisplayPageInfoManageListByPagination(DispalyPageInfoVO searchVO);
    
    public List<DispalyPageInfo> selectDisplayPageInfoContentCombo(String displayGubun);
	
    public DispalyPageInfoVO selectDisplayPageInfoManageDetail(String reportSeq );
    
    public Map<String, Object> selectPageCntNullCheck(String displaySeq);
	
    public DispalyPageInfoVO selectDisplayPageInfoManageView(String displaySeq);
	
    public DispalyPageInfo selectTimeIntevalNullCheck(String displaySeq);
    
    public List<DispalyPageInfoVO> selectDisplayPageInfoCombo(DispalyPageInfoVO searchVO);
    
    public String selectDisplayMaxSeq();
    
    public int selectDisplayPageInfoManageListTotCnt_S(DispalyPageInfoVO searchVO);
	
    public int insertDisplayPageInfoManage(DispalyPageInfo vo);
	//파일 수정 
    public int updateDisplayPageFileUpdate(DispalyPageInfo vo);
    
    public int updateDisplayPageInfoManage(DispalyPageInfo vo);
    
    public int updateDisplayPageUpManage(String displaySeq);
    
    public int updateDisplayPageDownManage(String displaySeq);
    
    public int updateDisplayTimeInfoManage(String displaySeq);
    //재 전송 관련 내용 추가로 넣기 
    public int updateDisplayReSendManage(String displaySeq);
    
    public int deleteDisplayPageInfoManage(DispalyPageInfoVO  vo);
    
}
