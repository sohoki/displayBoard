package com.display.backoffice.uat.uia.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.display.backoffice.uat.uia.mapper.UniUtilManageMapper;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import lombok.RequiredArgsConstructor;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service("UniUtilManageService")
public class UniUtilManageService {

	
	
	private final UniUtilManageMapper uniMapper;

	
	public int selectIdDoubleCheck(UniUtilInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return uniMapper.selectIdDoubleCheck(vo);
	}
	
	
	public int selectIdDoubleCheckString(String _field, String _tableNm, String _condition ) throws Exception {
		
		UniUtilInfo vo = new UniUtilInfo();
		vo.setInCheckName(_field);
		vo.setInTable(_tableNm);
		vo.setInCondition(_condition);
		// TODO Auto-generated method stub
		return uniMapper.selectIdDoubleCheck(vo);
	}

	@Transactional(readOnly = false)
	public int deleteUniStatement(UniUtilInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = uniMapper.deleteUniStatement(vo);
		return 1;
	}

	
	public String selectMaxValue(UniUtilInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return uniMapper.selectMaxValue(vo);
	}
}
