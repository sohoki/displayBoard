package com.display.backoffice.etc.rest.mapper;

import com.display.backoffice.etc.rest.models.RestInfo;
import com.display.backoffice.etc.rest.models.RestInfoVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import java.util.List;

@Mapper("RestInfoManageMapper")
public interface RestInfoManageMapper {
  List<RestInfoVO> selectRestInfoPageInfoManageListByPagination(RestInfoVO paramRestInfoVO);
  
  RestInfoVO selectRestInfoDetail(String paramString);
  
  int insertRestInfo(RestInfo paramRestInfo);
  
  int updateRestInfo(RestInfo paramRestInfo);
  
  int updateRestGrade(RestInfo paramRestInfo);
  
  int deleteRestInfo(String paramString);
}
