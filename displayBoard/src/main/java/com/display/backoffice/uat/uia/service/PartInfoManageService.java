package com.display.backoffice.uat.uia.service;

import java.util.List;
import java.util.Map;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.display.backoffice.uat.uia.mapper.PartInfoManageMapper;
import com.display.backoffice.uat.uia.models.PartInfo;
import com.display.backoffice.uat.uia.models.PartInfoVO;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Service
public class PartInfoManageService {
	
    
	@Value("${Globals.DbType}")
	private String dbType;
    
    
	@Autowired
	private EgovIdGnrService egovGroupIdGnrService;
	
	@Autowired
	private PartInfoManageMapper partMapper;

	
	public List<PartInfoVO> selectPartInfoPageInfoManageListByPagination(Map<String, Object> params) throws Exception {
		return partMapper.selectPartInfoPageInfoManageListByPagination(params);
	}

	
	public List<PartInfoVO> selectPartInfoCombo(Map<String, Object> params) throws Exception {
		return partMapper.selectPartInfoCombo(params);
	}

	

	
	public PartInfoVO selectPartInfoDetail(String partId) throws Exception {
		// TODO Auto-generated method stub
		return partMapper.selectPartInfoDetail(partId);
	}

	public int deletePartInfoManage(String partId)throws Exception{
		return partMapper.deletePartInfoManage(partId);
	}
	public int updatePartInfoManage(PartInfo vo) throws Exception {
		// TODO Auto-generated method stub
		//환경 변수가 오라클이냐 아니냐 구분으로 확인 하기 
		if (vo.getMode().equals("Ins")){
			if ( dbType.equals("oracle")){
				vo.setPartId(egovGroupIdGnrService.getNextStringId());
			}
			return partMapper.insertPartInfoManage(vo);
		}else {
			return partMapper.updatePartInfoManage(vo);
		}
	}
	// 추후 partCode로 변경
	public int updatePartInfoNetworkManage(PartInfo vo) throws Exception {
		// TODO Auto-generated method stub
		//환경 변수가 오라클이냐 아니냐 구분으로 확인 하기 
		if (vo.getMode().equals("Ins")){
			if ( dbType.equals("oracle")){
				vo.setPartId(egovGroupIdGnrService.getNextStringId());
			}
			return partMapper.insertPartInfoNetworkManage(vo);
		}else {
			return partMapper.updatePartInfoManage(vo);
		}
	}
}
