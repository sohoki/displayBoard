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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
import com.display.backoffice.uat.uia.service.PartInfoManageService;
import com.display.backoffice.uat.uia.service.UniUtilManageService;
import com.display.backoffice.util.service.UtilInfoService;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
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
	@RequestMapping(value="dashboardInfoAjax.do")
	public ModelAndView selectDashboardJson(@ModelAttribute("loginVO") AdminLoginVO loginVO
			                                ,HttpServletRequest request
											,BindingResult bindingResult) throws Exception {
		
		ModelAndView model = new 	ModelAndView(Globals.JSONVIEW);
		try{
			
			
			  CenterInfoVO searchVO = new CenterInfoVO();
			  
			  //상태 확인 
			  agentService.updateAgentState();
			  
			  Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		      if(!isAuthenticated) {
		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
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
			 model = agentService.selectAgentNowStateInnfo(searchVO);
		     model.addObject(Globals.STATUS,Globals.STATUS_SUCCESS);
		     model.setViewName(Globals.JSONVIEW);
		      
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
	
	
	@RequestMapping("restartAgentInfo.do")
	public ModelAndView restartAgentInfo ( HttpServletRequest request ,
		                                   @RequestParam(value="RestartDidInfo") String RestartDidInfo,
		                                   @RequestParam(value="xmlProceNm") String xmlProceNm) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSONVIEW);
		
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated) {
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
    		return model;
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
        			sminfo.setFrstRegisterId(EgovUserDetailsHelper.getAuthenticatedUser().toString());
        			sminfo.setSendResult("N");	   
        			sminfoLists.add(sminfo);
        			
    			}
    		}catch(Exception e){
    			log.debug("error:"+ e.toString());
    		}
    	}
    	log.debug("===========================================sminfoLists" + sminfoLists.size());
    	model =  sendMsgService.insertSendMsgInfoManageList(sminfoLists);
    	model.setViewName(Globals.JSONVIEW);
		
    	log.debug("model:" + model.toString());
    	return model ;
	}
	@RequestMapping(value="AgentInfoCenterList.do")
	public ModelAndView selectAgentCenterInfoList (@ModelAttribute("loginVO") AdminLoginVO loginVO
												   ,@RequestBody AgentInfoVO searchVO
												   ,HttpServletRequest request
												   ,BindingResult bindingResult) throws Exception {
		
		ModelAndView model = new ModelAndView(Globals.JSONVIEW);
		try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			if(!isAuthenticated) {
				model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
	    		return model;
			}else{
				HttpSession httpSession = request.getSession(true);
		    	loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
		    	searchVO.setAdminLevel(loginVO.getAdminLevel());
		    	searchVO.setPartId(loginVO.getPartId());
			}
			searchVO.setRecordCountPerPage(propertiesService.getInt("AgentPageSize"));   
			//searchVO.setPageIndex(0);
			
			model = agentService.selectAgentPageInfoManageListByPagination(searchVO);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("success.common.select"));	
			model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			model.setViewName(Globals.JSONVIEW);
			
		}catch(NullPointerException e){
			log.error("deleteEqupInfoManage  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}catch(Exception e){
			log.error("deleteEqupInfoManage  error: "  + e.toString());
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.delete"));	
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
		}
		return model;
	}
	@RequestMapping(value="AgentInfoList.do")
	public ModelAndView  selectEqupInfoManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
															, @ModelAttribute("searchVO") AgentInfoVO searchVO
															, HttpServletRequest request
															, BindingResult bindingResult) throws Exception {
		
		  ModelAndView  model = new ModelAndView("/backoffice/basicManage/agentInfoList");
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
			  log.error("getPageUnit:" + searchVO.getPageUnit());  
			  searchVO.setPageSize(propertiesService.getInt("pageSize"));
			  //에이전트 상태값
			  searchVO.setErrorCnt(propertiesService.getInt("agentErrCnt"));
			  model = agentService.selectAgentPageInfoManageListByPagination(searchVO);
		      model.addObject("regist", searchVO);
		      model.addObject("selectCenterCombo", centerService.selectCenterCombo());
			  model.addObject("selectGroupCombo", partService.selectPartInfoCombo());
			  model.addObject("selectAgentGubun", cmmnDetailService.selectCmmnDetailCodeList("OS_TYPE") );
			  model.addObject("selectAgentContentGubun", cmmnDetailService.selectCmmnDetailCodeList("AGENT_CONTENT") );
			  model.setViewName("/backoffice/basicManage/agentInfoList");
		  }catch(NullPointerException e){
			  log.error("selectEqupInfoManageListByPagination error:" + e.toString());  
		  }catch(Exception e){
			  log.error("selectEqupInfoManageListByPagination error:" + e.toString());  
		  }
		  return model;	
	}
	// 신규 생성 구문 
	@RequestMapping (value="AgentInfoListAjax.do")
	public ModelAndView  selectAgentListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
													, @RequestParam Map<String, Object> commandMap
													, HttpServletRequest request
													, BindingResult bindingResult) throws Exception {
		ModelAndView  model = new ModelAndView(Globals.JSONVIEW);																	
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		 if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
	    		return model;
	      }else{
	    	  HttpSession httpSession = request.getSession(true);
		      loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
		      commandMap.put("adminLevel", loginVO.getAdminLevel());
		      commandMap.put("partId", loginVO.getPartId());
		      commandMap.put("recordCountPerPage", propertiesService.getInt("pageUnit"));
		      commandMap.put("agentContentGubun",   commandMap.get("displayGubun").equals("DispalyGubun_1") ? "AGENT_CONTENT_1" : "AGENT_CONTENT_2" );
		      
		      log.debug("commandMap:" + commandMap.toString());
		      model = agentService.selectDisplayStageChangePageList(commandMap) ; 
		      model.setViewName(Globals.JSONVIEW);
	      }
		
		return model;
	}
	@RequestMapping (value="AgentUpdateStateAjax.do")
	public ModelAndView  updateAgentState(@ModelAttribute("loginVO") AdminLoginVO loginVO
											, @RequestBody  AgentInfo vo
											, HttpServletRequest request
											, BindingResult bindingResult) throws Exception {
            ModelAndView  model = new ModelAndView(Globals.JSONVIEW);						
            Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	    if(!isAuthenticated) {
    	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
    	    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
    	    		return model;
    	    }	
            
    	    model = agentService.updateAgentDisplayChange(vo);
    	    model.setViewName(Globals.JSONVIEW);
            return model;
	}
	//신규 끝 부분 
	@RequestMapping (value="agentInfoDetail.do")
	public ModelAndView selecSeatInfoManageDetail(@ModelAttribute("loginVO") AdminLoginVO loginVO
			                                                          , @RequestBody  AgentInfoVO vo
			                                                          , HttpServletRequest request
			                                            			  , BindingResult bindingResult ) throws Exception{	
		
		
		ModelAndView  model = new ModelAndView(Globals.JSONVIEW);
		  
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	    if(!isAuthenticated) {
	    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
	    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
	    		return model;
	    }	
	    model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);  
	    model.addObject("agentInfo", agentService.selectAgentPageInfoManageDetail(vo.getAgentCode()));	     	
				
		return model;
	}
	@RequestMapping (value="agentInfoView.do")
	public ModelAndView selecEqupInfoManageView(@ModelAttribute("loginVO") AdminLoginVO loginVO
											                              , @ModelAttribute("searchVO") AgentInfoVO vo
								                                          , HttpServletRequest request
								                            			  , BindingResult bindingResult ) throws Exception{	
		
		ModelAndView  model = new ModelAndView("/backoffice/basicManage/agentInfoView");
		DispalyPageInfoVO searchVO = new DispalyPageInfoVO();  
		
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
	
	
	@RequestMapping (value="agentInfoDelete.do")
	public ModelAndView deleteEqupInfoManage(@ModelAttribute("loginVO") AdminLoginVO loginVO,
									         @RequestBody AgentInfo vo) throws Exception {
		
		ModelAndView  model = new ModelAndView(Globals.JSONVIEW);
		
		
		UniUtilInfo utilInfo = new UniUtilInfo();
		utilInfo.setInTable("TB_AGENTINFO");
		utilInfo.setInCondition("AGENT_CODE=["+vo.getAgentCode()+"[");
		try{
			
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
		    		return model;
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
	
	
	@RequestMapping (value="agentInfoUpdate.do")
	public ModelAndView updateequpInfoManage( @ModelAttribute("loginVO") AdminLoginVO loginVO
									                                 ,@RequestBody AgentInfoVO vo
						                                             , HttpServletRequest request                         				 
																     , BindingResult result) throws Exception{
		ModelAndView  model = new ModelAndView(Globals.JSONVIEW);
		
		model.addObject("regist", vo);
		String meesage = "";
		
		try{

			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject(Globals.STATUS, Globals.STATUS_LOGINFAIL);
		    		return model;
		    }
			vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString());
			
			int ret  = 0;
			meesage = vo.getMode().equals("Ins") ? "sucess.common.insert" : "sucess.common.update";
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
