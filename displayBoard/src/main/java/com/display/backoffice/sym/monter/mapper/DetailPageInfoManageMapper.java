package com.display.backoffice.sym.monter.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.display.backoffice.sym.monter.models.DetailPageInfo;
import com.display.backoffice.sym.monter.models.DetailPageInfoVO;

import org.apache.ibatis.annotations.Param;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface DetailPageInfoManageMapper {

	public List<Map<String, Object>> selectDetailPageInfoManageListByPagination(@Param("params") Map<String, Object> params);
	
    public List<DetailPageInfoVO> selectDetailPageInfoManageListByContent(DetailPageInfoVO searchVO);
    
    public Map<String, Object> selectDetailInfo(String  detailSeq );
	//신규
    public List<DetailPageInfoVO> selectDisPlayPreviewList(DetailPageInfoVO searchVO);
    
    public List<Map<String, Object>> selectAgentPreviewList(DetailPageInfoVO vo);
    
    public List<String> selectDisplaySeqReturn(List report_Seq);
	//신규 end    
    public int selectDetailPageInfoManageListTotCnt_S(DetailPageInfoVO searchVO);
    
    public int selectDetailMaxSort(String displaySeq);
    
    public int selectDetailSumTime(String displaySeq);
	
    public int insertDetailPageInfoManage(DetailPageInfo vo);
	
    public int updateDetailPageInfoManage(DetailPageInfo vo);
    
    public int updateDetailPageInfoUpManage(DetailPageInfo vo);
    
    public int updateDetailPageInfoDownManage(DetailPageInfo vo);
	
    public int updateDetailTimeChangeManage(DetailPageInfo vo);
    
    public int updateDetailSortOrderUpSubInfoManage(@Param("displaySeq") String displaySeq, @Param("detailSeq") String detailSeq);
    //화면 순위 변경
    public int updateDetailSortOrderUpDownSubInfoManage(@Param("displaySeq") String displaySeq, @Param("detailSeq") String detailSeq, @Param("mode") String mode);
    
    public int updateDetailSortOrderDownInfoManage();   
    
    public int updateDetailSortOrderDownSubInfoManage(@Param("displaySeq") String displaySeq, @Param("detailSeq") String detailSeq);
    
    public int updateDisplayPageChangeInfo(List display_Seq); 
    
    public int updateDitailOrderUpdatePage(List<DetailPageInfo> list );
    
    public int deleteDetailPageInfoManage(@Param("displaySeq") String displaySeq, @Param("detailSeq") String detailSeq);
    
    public int deleteDetailReportSeq (List report_Seq);
        
}
