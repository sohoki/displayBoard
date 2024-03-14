package com.display.backoffice.sts.dis.mapper;

import java.util.List;
import java.util.Map;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface DisplayMapper {

	public List<Map<String, Object>> selectDisplayGq();
	
	public List<Map<String, Object>> selectDisplayAi();
	
	public List<Map<String, Object>> selectDisplayAs();
	
	public List<Map<String, Object>> selectDisplayAt();
}
