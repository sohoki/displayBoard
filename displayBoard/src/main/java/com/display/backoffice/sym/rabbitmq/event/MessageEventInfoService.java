package com.display.backoffice.sym.rabbitmq.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.display.backoffice.bas.code.models.CmmnClCode;
import com.display.backoffice.bas.code.models.CmmnCode;
import com.display.backoffice.bas.code.models.CmmnDetailCode;
import com.display.backoffice.bas.code.models.dto.CmmnClCodeResDto;
import com.display.backoffice.bas.code.models.dto.CmmnCodeDto;
import com.display.backoffice.bas.code.models.dto.CmmnDetailCodeDto;
import com.display.backoffice.bas.code.service.EgovCcmCmmnClCodeManageService;
import com.display.backoffice.bas.code.service.EgovCcmCmmnCodeManageService;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;
import com.display.backoffice.bas.menu.models.dto.menuCreatdetailInfoReqDto;
import com.display.backoffice.bas.menu.service.MenuCreateManageService;
import com.display.backoffice.bas.program.models.ProgrmInfo;
import com.display.backoffice.bas.program.service.ProgrameInfoManageService;
import com.display.backoffice.sym.rabbitmq.models.dto.MessageDto;
import com.display.backoffice.uat.role.models.RoleInfo;
import com.display.backoffice.uat.role.models.dto.RoleInfoRequestDto;
import com.display.backoffice.uat.role.models.dto.RoleInfoVO;
import com.display.backoffice.uat.role.service.RoleInfoManageService;
import com.display.backoffice.uat.uia.models.AdminInfoVO;
import com.display.backoffice.uat.uia.models.ConsultantInfo;
import com.display.backoffice.uat.uia.models.PartInfo;
import com.display.backoffice.uat.uia.models.PartInfoVO;
import com.display.backoffice.uat.uia.service.AdminInfoManageService;
import com.display.backoffice.uat.uia.service.PartInfoManageService;
import com.display.backoffice.util.service.NetworkUtilService;
import com.display.backoffice.util.service.UtilInfoService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.cmm.service.Globals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageEventInfoService {

	@Value("${rabbitmq.queue.name}")
	private String exchangeName;
	
	@Value("${server.linkage.login_url}")
	private String url;
	
	@Value("${server.linkage.token_key}")
	private String key;
	
	
	@Autowired
	private NetworkUtilService netService;
	
	@Autowired
	private PartInfoManageService partService;
	
	@Autowired
	private RoleInfoManageService roleService;
	
	//추후 변경 작업 필요
	@Autowired
	private AdminInfoManageService userManagerService;
	
	@Autowired
	private EgovCcmCmmnClCodeManageService ckCodeManagerService;
	
	@Autowired
	private EgovCcmCmmnCodeManageService codeManagerService;
	
	@Autowired
	private EgovCcmCmmnDetailCodeManageService codeDetailManagerService;
	
	@Autowired
	private ProgrameInfoManageService programeInfoService;
	
	@Autowired
	private MenuCreateManageService menuManageService;
	
	
	/*
	@RabbitListener(bindings = @QueueBinding(
								value = @Queue(value = '큐이름', durable = "true"),
								exchange = @Exchange(value = '익스체인지이름', type = "fanout"),
								key = '라우팅키'
	))
	public void method1(SagaEventMessage message, Channel channel) throws IOException {
	
	}
	*/
	@RabbitListener(queues = "${rabbitmq.queue.name}")
	public void reciveMessage(MessageDto messageDto) {
		log.info("display reciveMessage message: {}", messageDto.toString());
	
		
		//JSON URL로 값 받기 
		
		try {
			String sendData = "";
			JSONObject result = new JSONObject();
			
			log.info("messageDto.getUrlMethod(): {}", messageDto.getUrlMethod());
			
			int ret = 0;
			if (messageDto.getUrlMethod().equals(Globals.SAVE_MODE_DELETE)) {
				
				log.info("넘기기" + messageDto.getUrlMethod());
				result.put(Globals.HTTP_STATUS, Globals.STATUS_SUCCESS);
				try {
					ret = receiveMessageUpdate(result, messageDto);
				}catch(Exception e) {
					log.info(e.toString());
					result.put(Globals.HTTP_STATUS, Globals.STATUS_FAIL);
				}
				
				if (ret > 0) {
					result.put(Globals.HTTP_STATUS, Globals.STATUS_SUCCESS);
				}else {
					result.put(Globals.HTTP_STATUS, Globals.STATUS_FAIL);
				}
			}else {
				
				String token = netService.getToken();
				result = netService.getResponse(token, messageDto.getUrl(), 
						sendData,  messageDto.getProcessGubun(),
						messageDto.getId(), messageDto.getUrlMethod());
	
				
				if (result.get(Globals.HTTP_STATUS).equals("200")) {
					
					ret = receiveMessageUpdate(result, messageDto);
					if (ret > 0) {
						result.put(Globals.HTTP_STATUS, Globals.STATUS_SUCCESS);
					}else {
						result.put(Globals.HTTP_STATUS, Globals.STATUS_FAIL);
					}
				}
			}
		} catch (Exception e) {
			log.error("reciveMessage error:" + e.getStackTrace()[0].getLineNumber()  );
			log.error("reciveMessage error:" + e.toString());
			
		}
	}
	
	private int receiveMessageUpdate(JSONObject object, MessageDto messageDto) throws Exception {
		int ret = 0;
		if (object != null) {
			
			log.info("receiveMessageUpdate display Received result: {}", object.toString() + ":" + messageDto.getProcessName());
			//log.info("rec json result :" + object.get(Globals.JSON_RETURN_RESULT));
			switch (messageDto.getProcessName()) {
				case "MANAGER" :
					if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						List<AdminInfoVO> lcodeList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), AdminInfoVO[].class));
						
						for (AdminInfoVO info : lcodeList) {
							AdminInfoVO vo = new AdminInfoVO();
							vo.setMode(Globals.SAVE_MODE_INSERT);
							vo.setAdminId(info.getAdminId());
							vo.setAdminPassword(info.getAdminPassword());
							vo.setPasswordCnsr(info.getPasswordCnsr());
							vo.setPasswordHint(info.getPasswordHint());
							vo.setAdminName(info.getAdminName());
							vo.setAdminEmail(info.getAdminEmail());
							vo.setUseYn(info.getUseYn());
							vo.setPartId(info.getPartId());
							vo.setInsttCode(info.getInsttCode());
							vo.setAdminTel(info.getAdminTel());
							vo.setUserId("SYSTEM");
							vo.setRoleId(info.getRoleId());
							vo.setEmpNo(UtilInfoService.NVLObj(info.getEmpNo(), ""));
							vo.setAdminStatus(UtilInfoService.NVLObj(info.getAdminState(), ""));
							vo.setPbxExtension(UtilInfoService.NVLObj(info.getPbxExtension(), ""));
							vo.setConsultantUseyn(UtilInfoService.NVLObj(info.getConsultantUseyn(), ""));
							userManagerService.updateAdminUserManage(vo);
						}
						
					}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						AdminInfoVO info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), AdminInfoVO.class);
						AdminInfoVO vo = new AdminInfoVO();
						vo.setAdminId(info.getAdminId());
						vo.setAdminPassword(info.getAdminPassword());
						vo.setAdminName(info.getAdminName());
						vo.setAdminEmail(info.getAdminEmail());
						vo.setUseYn(info.getUseYn());
						vo.setPartId(info.getPartId());
						vo.setInsttCode(info.getInsttCode());
						vo.setAdminTel(info.getAdminTel());
						vo.setUserId("SYSTEM");
						vo.setRoleId(info.getRoleId());
						vo.setEmpNo(UtilInfoService.NVLObj(info.getEmpNo(), ""));
						vo.setAdminStatus(UtilInfoService.NVLObj(info.getAdminState(), ""));
						vo.setPbxExtension(UtilInfoService.NVLObj(info.getPbxExtension(), ""));
						vo.setConsultantUseyn(UtilInfoService.NVLObj(info.getConsultantUseyn(), ""));
						userManagerService.updateAdminUserManage(vo);
						
					}else {
						 userManagerService.deleteAdminUserManage(messageDto.getId());
					}
					break;
				case "LCODEINFO" :
					if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						
						ObjectMapper mapper = new ObjectMapper();
						List<CmmnClCodeResDto> lcodeList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), CmmnClCodeResDto[].class));
						
						for (CmmnClCodeResDto info : lcodeList) {
							CmmnClCode lcode =  CmmnClCode.builder()
												.mode(Globals.SAVE_MODE_INSERT)
												.clCode(info.getClCode())
												.clCodeNm(info.getClCodeNm())
												.clCodeDc(info.getClCodeDc())
												.systemCode(info.getSystemCode())
												.userId("SYSTEM")
												.useAt(info.getUseAt())
												.build();
							ckCodeManagerService.updateCmmnClCode(lcode);
							
						}
					}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						CmmnClCodeResDto info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), CmmnClCodeResDto.class);
						
						CmmnClCode lcode =  CmmnClCode.builder()
											.mode(messageDto.getProcessGubun())
											.clCode(info.getClCode())
											.clCodeNm(info.getClCodeNm())
											.clCodeDc(info.getClCodeDc())
											.systemCode(info.getSystemCode())
											.useAt(info.getUseAt())
											.userId("SYSTEM")
											.build();
						ckCodeManagerService.updateCmmnClCode(lcode);
					}else {
						ckCodeManagerService.deleteCmmnClCode(messageDto.getId());
					}
					break;
				case "CODEINFO" :
					if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						List<CmmnCodeDto> codeList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), CmmnCodeDto[].class));
						
						for (CmmnCodeDto info : codeList) {
							CmmnCode code =  CmmnCode.builder()
												.mode(Globals.SAVE_MODE_INSERT)
												.clCode(info.getClCode())
												.codeId(info.getCodeId())
												.codeIdNm(info.getCodeIdNm())
												.codeIdDc(info.getCodeIdDc())
												.useAt(info.getUseAt())
												.systemCode(info.getSystemCode())
												.userId("SYSTEM")
												.build();
							codeManagerService.updateCmmnCode(code);
						}
					}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						CmmnCodeDto info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), CmmnCodeDto.class);						
						CmmnCode code =  CmmnCode.builder()
										.mode(messageDto.getProcessGubun())
										.clCode(info.getClCode())
										.codeId(info.getCodeId())
										.codeIdNm(info.getCodeIdNm())
										.codeIdDc(info.getCodeIdDc())
										.useAt(info.getUseAt())
										.systemCode(info.getSystemCode())
										.userId("SYSTEM")
										.build();
						codeManagerService.updateCmmnCode(code);
					}else {
						codeManagerService.deleteCmmnCode(messageDto.getId());
					}
					break;
				case "DETAILCODEINFO" :
					if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						List<CmmnDetailCodeDto> codeList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), CmmnDetailCodeDto[].class));
						
						for (CmmnDetailCodeDto info : codeList) {
							CmmnDetailCode code =  CmmnDetailCode.builder()
												.mode(Globals.SAVE_MODE_INSERT)
												.code(info.getCode())
												.codeId(info.getCodeId())
												.codeNm(info.getCodeNm())
												.codeDc(info.getCodeDc())
												.useAt(info.getUseAt() )
												.systemCode(info.getSystemCode())
												.userId("SYSTEM")
												.build();
							codeDetailManagerService.updateCmmnDetailCode(code);
						}
					}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						CmmnDetailCodeDto info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), CmmnDetailCodeDto.class);
						
						CmmnDetailCode code =  CmmnDetailCode.builder()
											.mode(messageDto.getProcessGubun())
											.code(info.getCode())
											.codeId(info.getCodeId())
											.codeNm(info.getCodeNm())
											.codeDc(info.getCodeDc())
											.useAt(info.getUseAt() )
											.systemCode(info.getSystemCode())
								.userId("SYSTEM")
								.build();
						codeDetailManagerService.updateCmmnDetailCode(code);
					}else {
						codeDetailManagerService.deleteCmmnDetailCode(messageDto.getId());
					}
					break;
				case "PROGRAMINFO" :
					if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						
						ObjectMapper mapper = new ObjectMapper();
						List<PartInfoVO> partList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), PartInfoVO[].class));
						for (PartInfoVO info : partList) {
							PartInfo part =  PartInfo.builder()
												.mode(Globals.SAVE_MODE_INSERT)
												.partId(info.getPartId())
												.partNm(info.getPartNm())
												.partCreateDe(info.getPartCreateDe())
												.partDc(info.getPartDc())
												.partUseyn(info.getPartUseyn())
												.parentPartId(info.getParentPartId())
												.partOrder(info.getPartOrder())
												.partHeadUserId(info.getPartHeadUserId())
												.partEndDe(info.getPartEndDe())
												.partEndyn(info.getPartEndyn())
												.insttCode(info.getInsttCode())
												.build();
							partService.updatePartInfoNetworkManage(part);
						}
					}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						
						ObjectMapper mapper = new ObjectMapper();
						PartInfoVO info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), PartInfoVO.class);
						
						PartInfo part =  PartInfo.builder()
								.mode(messageDto.getProcessGubun() )
								.partId(info.getPartId())
								.partNm(info.getPartNm())
								.partCreateDe(info.getPartCreateDe())
								.partDc(info.getPartDc())
								.partUseyn(info.getPartUseyn())
								.parentPartId(info.getParentPartId())
								.partOrder(info.getPartOrder())
								.partHeadUserId(info.getPartHeadUserId())
								.partEndDe(info.getPartEndDe())
								.partEndyn(info.getPartEndyn())
								.insttCode(info.getInsttCode())
								.build();
						partService.updatePartInfoNetworkManage(part);
						
					}else {
						partService.deletePartInfoManage(messageDto.getId());
					}
					break;
				case "PARTINFO" :
						if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
							
							ObjectMapper mapper = new ObjectMapper();
							List<PartInfoVO> partList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), PartInfoVO[].class));
							for (PartInfoVO info : partList) {
								PartInfo part =  PartInfo.builder()
													.mode(Globals.SAVE_MODE_INSERT)
													.partId(info.getPartId())
													.partNm(info.getPartNm())
													.partCreateDe(info.getPartCreateDe())
													.partDc(info.getPartDc())
													.partUseyn(info.getPartUseyn())
													.parentPartId(info.getParentPartId())
													.partOrder(info.getPartOrder())
													.partHeadUserId(info.getPartHeadUserId())
													.partEndDe(info.getPartEndDe())
													.partEndyn(info.getPartEndyn())
													.insttCode(info.getInsttCode())
													.build();
								partService.updatePartInfoNetworkManage(part);
							}
						}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE) && object.get(Globals.JSON_RETURN_RESULT) != null) {
							
							ObjectMapper mapper = new ObjectMapper();
							PartInfoVO info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), PartInfoVO.class);
							
							PartInfo part =  PartInfo.builder()
									.mode(messageDto.getProcessGubun() )
									.partId(info.getPartId())
									.partNm(info.getPartNm())
									.partCreateDe(info.getPartCreateDe())
									.partDc(info.getPartDc())
									.partUseyn(info.getPartUseyn())
									.parentPartId(info.getParentPartId())
									.partOrder(info.getPartOrder())
									.partHeadUserId(info.getPartHeadUserId())
									.partEndDe(info.getPartEndDe())
									.partEndyn(info.getPartEndyn())
									.insttCode(info.getInsttCode())
									.build();
							partService.updatePartInfoNetworkManage(part);
							
						}else {
							partService.deletePartInfoManage(messageDto.getId());
						}
					
					break;
				case "AUTHINFO" :
					if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE)  && object.get(Globals.JSON_RETURN_RESULT) != null) {
						//프로그램 먼저 처크
						log.info("AUTHINFO: 시작");
						JSONObject authMenuInfo = (JSONObject) object.get(Globals.JSON_RETURN_RESULT);
						ObjectMapper mapper = new ObjectMapper();
						List<ProgrmInfo> programeList = Arrays.asList(mapper.readValue(authMenuInfo.get(Globals.NETWORK_RESULT_PROGRAME).toString(), ProgrmInfo[].class));
						log.info("AUTHINFO: programeList 시작" + programeList.size());
						programeInfoService.insertExistProgrameInfo(programeList);
						
						//메뉴 체크 
						List<menuCreatdetailInfoReqDto> menuCreateList = Arrays.asList(mapper.readValue(authMenuInfo.get(Globals.NETWORK_RESULT_MENUINFO).toString(), menuCreatdetailInfoReqDto[].class));
						log.info("AUTHINFO: menuCreateList 시작" + menuCreateList.size());
						menuManageService.insertMenuManage_System(menuCreateList);
						
					}else {
						//프로그램 삭제 시 처리 할 방안 마련하기 
						
					}
					break;
				case "ROLEINFO" :
					if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						List<RoleInfoVO> partList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), RoleInfoVO[].class));
						for (RoleInfoVO info : partList) {
							RoleInfoRequestDto roleInfo =  RoleInfoRequestDto.builder()
															.mode(Globals.SAVE_MODE_INSERT)
															.roleId(info.getRoleId())
															.roleName(info.getRoleName())
															.roleDc(info.getRoleDc())
															.roleUseyn(info.getRoleUseyn())
															.systemCode(info.getSystemCode())
															.userId("SYSTEM")
															.build();
							roleService.updateRoleInfo(roleInfo);
						}
					}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE)  && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						RoleInfo info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), RoleInfo.class);
						
						RoleInfoRequestDto roleInfo =  RoleInfoRequestDto.builder()
								.mode(messageDto.getProcessGubun())
								.roleId(info.getRoleId())
								.roleName(info.getRoleName())
								.roleDc(info.getRoleDc())
								.roleUseyn(info.getRoleUseyn())
								.systemCode(info.getSystemCode())
								.userId("SYSTEM")
								.build();
						roleService.updateRoleInfo(roleInfo);
					}else {
						
						roleService.deleteRoleInfo(messageDto.getId());
					}
					break;
				case "CONSULTANT" :
					if (messageDto.getProcessGubun().equals(Globals.SAVE_MODE_LIST) && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						List<ConsultantInfo> partList = Arrays.asList(mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), ConsultantInfo[].class));
						for (ConsultantInfo info : partList) {
							System.out.println(info.toString() );
						}
					}else if (!messageDto.getProcessGubun().equals(Globals.SAVE_MODE_DELETE)  && object.get(Globals.JSON_RETURN_RESULT) != null) {
						ObjectMapper mapper = new ObjectMapper();
						ConsultantInfo info = mapper.readValue(object.get(Globals.JSON_RETURN_RESULT).toString(), ConsultantInfo.class);
						System.out.println(info.toString() );
					}else {
						//삭제 명령어 
						roleService.deleteRoleInfo(messageDto.getId());
					}
					break;
				default :
					break;
			}
			
		}
		return ret;
		
	}
	public static List<Map<String, Object>> getListMapFromJsonArray(JSONArray jsonArray) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (jsonArray != null) {

			int jsonSize = jsonArray.size();
			for (int i = 0; i < jsonSize; i++) {
				Map<String, Object> map = getMapFromJsonObject((JSONObject)jsonArray.get(i));
				list.add(map);
			}
		}

		return list;
	}
	public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObject) { 
		Map<String, Object> map = null;
		try {
			map = new ObjectMapper().readValue(jsonObject.toJSONString(), Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
}
