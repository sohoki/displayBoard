package com.display.backoffice.uat.uia.mapper;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import com.display.backoffice.uat.uia.models.UniUtilInfo;

@Mapper
public interface UniUtilManageMapper {

	public int selectIdDoubleCheck(UniUtilInfo vo); 
	
	public int selectIdDoubleCheckString(String inCheckName, String inTable, String inCondition); 
	
	public int deleteUniStatement(UniUtilInfo vo);
	
	public String selectMaxValue(UniUtilInfo vo);
}