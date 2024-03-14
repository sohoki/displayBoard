package com.display.backoffice.sts.cnt.mapper;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.sts.cnt.models.ContentFileInfo;
import com.display.backoffice.sts.cnt.models.ContentFileInfoVO;

@Mapper("ContentFileManagerMapper")
public interface ContentFileManagerMapper {
	
	public List<ContentFileInfoVO> selectFilePageListByPagination (ContentFileInfoVO  searchVO);
	
	public int selectFilePageListByPaginationTotCnt_S (ContentFileInfoVO  searchVO);
	
	public List<ContentFileInfo> fileDelInfo(List reportSeq);
	
	public int selectFileListTotCnt_S  (String conSeq);
	
	public ContentFileInfoVO selectFileDetail (String atchFileId);
	
	public int insertFileManage(ContentFileInfo vo);
	
	public int updateFileManage(ContentFileInfo vo);
	
	public int updateFileManageUseYn(ContentFileInfo vo);
	
	public int updateFileDetailInfo(ContentFileInfo vo);
		
	public int deleteFileManage(String atchFileId);	
	//추가 수정 
	public int deleteFileListManage(List reportSeq);
	
}
