package com.display.backoffice.etc.rest.mapper;

import com.display.backoffice.etc.rest.models.RestNoticeInfo;
import com.display.backoffice.etc.rest.models.RestNoticeInfoVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import java.util.List;

@Mapper
public interface RestNoticeInfoManageMapper {
  List<RestNoticeInfoVO> selectRestNoticeInfoLeftListByPagination(RestNoticeInfoVO paramRestNoticeInfoVO);
  
  RestNoticeInfo selectRestNoticeInfo(String paramString);
  
  int insertRestNoticeInfo(RestNoticeInfoVO paramRestNoticeInfoVO);
  
  int updateRestNoticeInfo(RestNoticeInfoVO paramRestNoticeInfoVO);
  
  int deleteRestNoticeInfo(String paramString);
}
