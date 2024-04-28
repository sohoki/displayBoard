package com.display.backoffice.bas.program.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.bas.program.mapper.ProgrmInfoManageMapper;
import com.display.backoffice.bas.program.models.ProgrmInfo;
import com.display.backoffice.bas.program.models.dto.ProgrmInfoDto;
import com.display.backoffice.uat.uia.mapper.UniUtilManageMapper;
import com.display.backoffice.util.service.UtilInfoService;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProgrameInfoManageService {

	@Autowired
	private ProgrmInfoManageMapper progrmMapper;
	
	@Autowired
	private UniUtilManageMapper uniMapper;
	
	
	public List<ProgrmInfoDto> selectProgrmInfoList(Map<String, Object> params) throws Exception {
		return progrmMapper.selectProgrmInfoList(params);
	}

	
	public Optional<ProgrmInfoDto> selectProgrmInfoDetail(String progrmFileNm) throws Exception {
		return progrmMapper.selectProgrmInfoDetail(progrmFileNm);
	}
	
	@Transactional(readOnly = false)
	public int insertProgrmInfo(ProgrmInfoDto progrmInfoDto) throws Exception {
		ProgrmInfo progrmInfo = ProgrmInfo.builder()
										.progrmFileNm(progrmInfoDto.getProgrmFileNm())
										.progrmStrePath(progrmInfoDto.getProgrmStrePath())
										.progrmKoreannm(progrmInfoDto.getProgrmKoreannm())
										.progrmDc(progrmInfoDto.getProgrmDc())
										.url(progrmInfoDto.getUrl())
										.build();
		return (uniMapper.selectIdDoubleCheckString("PROGRM_FILE_NM", "COMTNPROGRMLIST", "PROGRM_FILE_NM = ["+ progrmInfo.getProgrmFileNm() + "[" ) > 0) ? -1 :  progrmMapper.insertProgrmInfo(progrmInfo);
	}
	
	@Transactional(readOnly = false)
	public int insertExistProgrameInfo(List<ProgrmInfo> list) throws Exception {
		return progrmMapper.insertExistProgrameInfo(list);
	}
	
	@Transactional(readOnly = false)
	public int updateProgrmInfo(ProgrmInfoDto progrmInfoDto) throws Exception {
		ProgrmInfo progrmInfo = ProgrmInfo.builder()
										.progrmFileNm(progrmInfoDto.getProgrmFileNm())
										.progrmStrePath(progrmInfoDto.getProgrmStrePath())
										.progrmKoreannm(progrmInfoDto.getProgrmKoreannm())
										.progrmDc(progrmInfoDto.getProgrmDc())
										.url(progrmInfoDto.getUrl())
										.build();
		return progrmMapper.updateProgrmInfo(progrmInfo);
	}

	@Transactional(readOnly = false)
	public int deleteProgrmInfo(String progrmFileNm) throws Exception {
		return progrmMapper.deleteProgrmInfo(progrmFileNm);
	}

	@Transactional(readOnly = false)
	public int deleteProgrmManageList(String checkedProgrmFileNmForDel) throws Exception {
		List<String> programFiles = UtilInfoService.dotToList(checkedProgrmFileNmForDel);
		return progrmMapper.deleteProgrmManageList(programFiles);
	}
}
