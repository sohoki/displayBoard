package com.display.backoffice.sym.agent.web;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.bas.cnt.models.CenterInfoVO;
import com.display.backoffice.bas.cnt.service.CenterInfoService;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;
import com.display.backoffice.bas.code.web.EgovCcmCmmnDetailCodeManageController;
import com.display.backoffice.sts.xml.models.SendMsgInfo;
import com.display.backoffice.sts.xml.service.SendMsgInfoManageService;
import com.display.backoffice.sym.agent.models.AgentInfo;
import com.display.backoffice.sym.agent.service.AgentInfoManageService;
import com.display.backoffice.sym.agent.models.AgentInfoVO;
import com.display.backoffice.sym.monter.service.DetailPageInfoManageService;
import com.display.backoffice.sym.monter.models.DetailPageInfoVO;
import com.display.backoffice.sym.monter.models.DispalyPageInfoVO;
import com.display.backoffice.sym.monter.service.DisplayPageInfoManageService;
import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.uat.uia.service.PartInfoManageService;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.UtilInfoService;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.property.EgovPropertyService;

@Api(tags = {"에이전트 정보 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/basicManage/agent")
public class AgentInfoMangeController {

	
	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
    @Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
    protected AgentInfoManageService agentService;
	
	@Autowired
	private EgovCcmCmmnDetailCodeManageService cmmnDetailService;
	
	@Autowired
	private DisplayPageInfoManageService disService;
	
	@Autowired
	private DetailPageInfoManageService detailService;
	
	@Autowired
	private UniUtilManageService utilService;
	
	@Autowired
	private CenterInfoService centerService;
	
	@Autowired
	 private PartInfoManageService partService;
	
	@Autowired
	private SendMsgInfoManageService sendMsgService;
	
	
	
	
    /** TRACE */
    @Resource(name="leaveaTrace")
    LeaveaTrace leaveaTrace;
    public static final int PORT = 9;  
	
	@GetMapping(value="AgentInfoPreview/{agentCode}.do")
	public ModelAndView  selectAgentPreview(@PathVariable String agentCode, 
											@RequestParam Map<String, String> searchVO,
											HttpServletRequest request) throws Exception {
		//ModelAndView model = new ModelAndView("/backoffice/contentManage/agentInfoPreview");
	    
		
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			String detailSeq_N = UtilInfoService.NVLObj(searchVO.get("detailSeq_N"), "");
			
			AgentInfoVO vo = agentService.selectAgentPageInfoManageDetail(agentCode);
			vo.setDetailSeq_N(detailSeq_N);
			DetailPageInfoVO detailVO = new DetailPageInfoVO();
			detailVO.setDisplaySeq( String.valueOf( vo.getDisplaySeq()));			
			model.addObject(Globals.STATUS_REGINFO, vo);
			
			detailVO.setReplacePath(propertiesService.getString("Globals.fileStorePathReplace"));
			detailVO.setAgentCode(agentCode);
			model.addObject("resultDetailList",   detailService.selectAgentPreviewList(detailVO) );
			model.addObject(Globals.PAGE_TOTAL_COUNT,   detailService.selectDetailPageInfoManageListTotCnt_S(detailVO) );
		}catch(Exception e) {
			log.error("selectAgentPreview:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		
	    
		return model;
	}
	@RequestMapping(value="AgentWakeOnLenInfo.do")
	public ModelAndView  selectAgentWakeOnLen( HttpServletRequest request ) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		String agentCode = request.getParameter("agentCode") != null ? request.getParameter("agentCode") : "";
		AgentInfoVO vo = agentService.selectAgentPageInfoManageDetail(agentCode);
		
		String ipStr = vo.getAgentIp() ;
        String macStr = vo.getAgentMac();
	        
        try {
        	log.debug("macStr:" + macStr);
            byte[] macBytes = getMacBytes(macStr);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }
            log.debug("macBytes:" + macBytes);
            InetAddress address = InetAddress.getByName(ipStr);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.update"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
        }
        catch (Exception e) {
        	log.error("AgentWakeOnLenInfo:" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg") );	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
        }
		return model;
	}
	private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
	/*
	@RequestMapping(value="dashboardInfo.do")
	public ModelAndView selectDashboard(@ModelAttribute("loginVO") AdminLoginVO loginVO
								                           , @ModelAttribute("searchVO") AgentInfoVO searchVO
														   ,HttpServletRequest request
														   ,BindingResult bindingResult) throws Exception {
							
		ModelAndView model = new ModelAndView("/backoffice/operManage/dashBoardInfo");
		try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	        if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.setViewName("redirect:http://192.168.90.55:8080/SSOService.do");
	    		return model;
	       }else{
	    	   
	    	  HttpSession httpSession = request.getSession(true);
		      loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
		      searchVO.setAdminLevel(loginVO.getAdminLevel());
		      searchVO.setPartId(loginVO.getPartId());
		      
	       }
	        if(  searchVO.getPageUnit() > 0  ){    	   
		    	searchVO.setPageUnit(searchVO.getPageUnit());
			 }else {
				searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			 }
			 searchVO.setPageSize(propertiesService.getInt("pageSize"));
			 searchVO.setSearchAgentState("F");
			 searchVO.setSearchOrderGubun("CONN_DATE");
			 model = agentService.selectAgentPageInfoManageListByPagination(searchVO);
		     model.addObject("regist", searchVO);
		     model.setViewName("/backoffice/operManage/dashBoardInfo");
	        
		}catch(NullPointerException e){
			log.debug("ERROR");
		}catch(Exception e){
			log.debug("ERROR");
		}
		return model;
	}
	*/
	@PostMapping(value="dashboardInfoAjax.do")
	public ModelAndView selectDashboardJson(HttpServletRequest request
											,BindingResult bindingResult) throws Exception {
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		try{
			
			
			CenterInfoVO searchVO = new CenterInfoVO();
			  
			  //상태 확인 
			agentService.updateAgentState();
			if (!jwtVerification.isVerification(request)) {
	        	ResultVO resultVO = new ResultVO();
	    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
	        }else {
	        	String[] userInfo = jwtVerification.getTokenUserInfo(request);
	        	searchVO.setAdminLevel(userInfo[2]);
			    searchVO.setPartId(userInfo[3]);
	        }
			
		    if(searchVO.getPageUnit() > 0  ){    	   
		    	searchVO.setPageUnit(searchVO.getPageUnit());
			}else {
				searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
			}
			searchVO.setPageSize(propertiesService.getInt("pageSize"));
			model = agentService.selectAgentNowStateInnfo(searchVO);
		    model.addObject(Globals.STATUS,Globals.STATUS_SUCCESS);
		      
		}catch(NullPointerException e){
			log.error("selectDashboardJson  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.error("selectDashboardJson  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	
	
	@GetMapping("restartAgentInfo.do")
	public ModelAndView restartAgentInfo ( HttpServletRequest request ,
		                                   @RequestParam(value="RestartDidInfo") String RestartDidInfo,
		                                   @RequestParam(value="xmlProceNm") String xmlProceNm) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		if (!jwtVerification.isVerification(request)) {
        	ResultVO resultVO = new ResultVO();
    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
        }
    	String[] AgentArrays =  RestartDidInfo.split(",");
    	List<SendMsgInfo> sminfoLists = new ArrayList<SendMsgInfo>();
    	
    	for (String AgentArray : AgentArrays){
    		try{
    			if (AgentArray.contains("/")){
    				SendMsgInfo sminfo = new SendMsgInfo();   
    				String[] agentInfo = AgentArray.split("/");
        			String agentCode = agentInfo[0] != null ? agentInfo[0] : "";
        			String agentMac = agentInfo[1] != null ? agentInfo[1] : "";
        			sminfo.setAgentCode(agentCode);
        			sminfo.setAgentMac(agentMac);
        			sminfo.setXmlProcessName(xmlProceNm);
        			sminfo.setFrstRegisterId(jwtVerification.getTokenUserName(request) );
        			sminfo.setSendResult("N");	   
        			sminfoLists.add(sminfo);
        			
    			}
    		}catch(Exception e){
    			log.debug("error:"+ e.toString());
    		}
    	}
    	log.debug("===========================================sminfoLists" + sminfoLists.size());
    	model =  sendMsgService.insertSendMsgInfoManageList(sminfoLists);
    	model.setViewName(Globals.JSON_VIEW);
		
    	log.debug("model:" + model.toString());
    	return model ;
	}
	@PostMapping(value="AgentInfoCenterList.do")
	public ModelAndView selectAgentCenterInfoList (@RequestBody AgentInfoVO searchVO
												   ,HttpServletRequest request
												   ,BindingResult bindingResult) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try{
			if (!jwtVerification.isVerification(request)) {
	        	ResultVO resultVO = new ResultVO();
	    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
	        }else {
	        	String[] userInfo = jwtVerification.getTokenUserInfo(request);
	        	searchVO.setAdminLevel(userInfo[2]);
			    searchVO.setPartId(userInfo[3]);
	        }
//			searchVO.setRecordCountPerPage(propertiesService.getInt("AgentPageSize"));   
			//searchVO.setPageIndex(0);
			
			model = agentService.selectAgentPageInfoManageListByPagination(searchVO);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			model.setViewName(Globals.JSON_VIEW);
			
		}catch(NullPointerException e){
			log.error("selectAgentCenterInfoList  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.error("selectAgentCenterInfoList  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	
	// 신규 생성 구문 
	@GetMapping (value="AgentInfoListAjax.do")
	public ModelAndView  selectAgentListByPagination(@RequestParam Map<String, Object> commandMap
													, HttpServletRequest request
													, BindingResult bindingResult) throws Exception {
		ModelAndView  model = new ModelAndView(Globals.JSON_VIEW);
		
		if (!jwtVerification.isVerification(request)) {
        	ResultVO resultVO = new ResultVO();
    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
        }else {
        	String[] userInfo = jwtVerification.getTokenUserInfo(request);
        	commandMap.put("adminLevel",userInfo[2]);
        	commandMap.put("partId",userInfo[3]);
        	commandMap.put("recordCountPerPage", propertiesService.getInt("pageUnit"));
		    commandMap.put("agentContentGubun",   commandMap.get("displayGubun").equals("DispalyGubun_1") ? "AGENT_CONTENT_1" : "AGENT_CONTENT_2" );
		    model = agentService.selectDisplayStageChangePageList(commandMap) ;  
        }
		
		return model;
	}
	@PostMapping (value="AgentUpdateStateAjax.do")
	public ModelAndView  updateAgentState(@RequestBody  AgentInfo vo
											, HttpServletRequest request
											, BindingResult bindingResult) throws Exception {
		ModelAndView  model = new ModelAndView(Globals.JSON_VIEW);						
        if (!jwtVerification.isVerification(request)) {
        	ResultVO resultVO = new ResultVO();
    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
        }
	    model = agentService.updateAgentDisplayChange(vo);
        return model;
	}
	//신규 끝 부분 
	@PostMapping (value="agentInfoDetail.do")
	public ModelAndView selecSeatInfoManageDetail(@RequestBody  AgentInfoVO vo
	                                              , HttpServletRequest request
	                                			  , BindingResult bindingResult ) throws Exception{	
		
		
		ModelAndView  model = new ModelAndView(Globals.JSON_VIEW);
		  
		if (!jwtVerification.isVerification(request)) {
        	ResultVO resultVO = new ResultVO();
    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
        }
	    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);  
	    model.addObject("agentInfo", agentService.selectAgentPageInfoManageDetail(vo.getAgentCode()));	     	
				
		return model;
	}
	@ApiOperation(value="Display combobox 정보 조회", notes = "성공시 지점 Display 정보 조회 합니다.")
	@GetMapping("selectCodeDM.do")
	public ModelAndView CenterComboInfo (HttpServletRequest request) throws Exception {  
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW); 
		DispalyPageInfoVO searchVO = new DispalyPageInfoVO(); 
		/*
		if (!jwtVerification.isVerificationAdmin(request)) {
    		ResultVO resultVO = new ResultVO();
			return jwtVerification.handleAuthError(resultVO); // 토큰 확인
        }
		*/

		try{ 
			model.addObject(Globals.JSON_RETURN_RESULT_LIST, disService.selectDisplayPageInfoCombo(searchVO));
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
		}catch(Exception e){
				//result = "F";
			log.error("CenterComboInfo : error" + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		 }
		 return model;
	}
	
	/*
	@PostMapping (value="agentInfoView.do")
	public ModelAndView selecEqupInfoManageView(@RequestBody AgentInfoVO vo
												, HttpServletRequest request
												, BindingResult bindingResult ) throws Exception{	
		
		ModelAndView  model = new ModelAndView("/backoffice/basicManage/agentInfoView");
		DispalyPageInfoVO searchVO = new DispalyPageInfoVO();  
		
		if (!jwtVerification.isVerification(request)) {
        	ResultVO resultVO = new ResultVO();
    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
        }else {
        	String[] userInfo = jwtVerification.getTokenUserInfo(request);
        	searchVO.setAdminLevel(userInfo[2]);
		    searchVO.setPartId(userInfo[3]);
        }
		
	    
	    vo.setErrorCnt(propertiesService.getInt("agentErrCnt"));
		if (!vo.getAgentContentgubun().equals("AGENT_CONTENT_1")){
	    	searchVO.setDisplayGubun("DispalyGubun_2");
	    }
		
		model.addObject("selectCodeDM", disService.selectDisplayPageInfoCombo(searchVO));
		model.addObject("selectCenterCombo", centerService.selectCenterCombo());
		model.addObject("selectGroupCombo", partService.selectPartInfoCombo());
		//수정 
		model.addObject("selectAgentGubun", cmmnDetailService.selectCmmnDetailCodeList("OS_TYPE") );
		model.addObject("selectAgentContentGubun", cmmnDetailService.selectCmmnDetailCodeList("AGENT_CONTENT") );
		model.addObject("search", vo);
	    model.addObject("regist", agentService.selectAgentPageInfoManageView(vo));
		return model;
	}
	*/
	
	@DeleteMapping (value="agentInfoDelete.do")
	public ModelAndView deleteEqupInfoManage(@RequestBody AgentInfo vo
											, HttpServletRequest request) throws Exception {
		
		ModelAndView  model = new ModelAndView(Globals.JSON_VIEW);
		
		
		UniUtilInfo utilInfo = new UniUtilInfo();
		utilInfo.setInTable("TB_AGENTINFO");
		utilInfo.setInCondition("AGENT_CODE=["+vo.getAgentCode()+"[");
		try{
			
			if (!jwtVerification.isVerification(request)) {
	        	ResultVO resultVO = new ResultVO();
	    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
	        }
		    int ret = 	utilService.deleteUniStatement(utilInfo);	
		     
		    if (ret > 0 ) {		    	  
		    	  model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.delete"));
				  model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);  
		    }else {
		    	  throw new Exception();		    	  
		    }
		}catch (NullPointerException e){
			log.error("deleteEqupInfoManage  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch (Exception e){
			log.error("deleteEqupInfoManage  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	
	
	@PostMapping (value="agentInfoUpdate.do")
	public ModelAndView updateequpInfoManage(AgentInfoVO vo
											 , HttpServletRequest request                         				 
											 , BindingResult result) throws Exception{
		ModelAndView  model = new ModelAndView(Globals.JSON_VIEW);
		
		model.addObject("regist", vo);
		String meesage = "";
		
		try{

			if (!jwtVerification.isVerification(request)) {
	        	ResultVO resultVO = new ResultVO();
	    		return jwtVerification.handleAuthError(resultVO); // 토큰 확
	        }
			vo.setUserId(jwtVerification.getTokenUserName(request));
			
			int ret  = 0;
			meesage = vo.getMode().equals("Ins") ? "sucess.common.insert" : "success.common.update";
			ret = agentService.updateAgentPageInfoManage(vo);
		
			if (ret >0){
				model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
				model.addObject("agentCode", vo.getAgentCode());
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));
			}else {
				throw new Exception();
			}
			
		}catch (NullPointerException e){
			log.error("updateequpInfoManage ERROR:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
		}catch (Exception e){
			log.error("updateequpInfoManage ERROR:" + e.toString());
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.update"));	
		}	
		return model;
	}
}
