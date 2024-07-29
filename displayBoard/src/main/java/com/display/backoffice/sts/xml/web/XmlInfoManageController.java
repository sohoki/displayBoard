package com.display.backoffice.sts.xml.web;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.display.backoffice.sts.xml.models.XmlInfoVO;
import com.display.backoffice.sts.xml.service.XmlInfoManageService;
import com.display.backoffice.sym.log.annotation.NoLogging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.display.backoffice.bas.code.service.EgovCcmCmmnDetailCodeManageService;
import com.google.gson.Gson;



@Api(tags = {"에이전트 전문 관리 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/operManage/xml")
public class XmlInfoManageController {


	@Value("${page.pageUnit}")
    private int pageUnitSetting ;
    
    @Value("${page.pageSize}")
    private int pageSizeSetting ;
    
	@Autowired
    protected XmlInfoManageService xmlInfoManageService;
    
    @Autowired
	protected EgovMessageSource egovMessageSource;
    
    @Autowired
    private EgovCcmCmmnDetailCodeManageService cmmnDetailCodeManageService;
    
	@Autowired
    protected EgovPropertyService propertiesService;
    
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	
	@ApiOperation(value="전문 조회", notes = "성공시 전문 정보를조회 합니다.")
	@PostMapping ("xmlList.do")
	public ModelAndView selectXmlLst(@RequestBody XmlInfoVO searchVO
									, HttpServletRequest request
									, BindingResult bindingResult) throws Exception {
		
          ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		  //공용 확인 하기 
          
          try{
	        	if (!jwtVerification.isVerification(request)) {
	          		ResultVO resultVO = new ResultVO();
	      			return jwtVerification.handleAuthError(resultVO); // 토큰 확
	          	}
	    		
	        	
	        	
	        	int pageUnit = searchVO.getPageUnit() > 0 ?   pageUnitSetting : searchVO.getPageUnit();
	    		int pageSize = searchVO.getPageSize() > 0 ?   pageSizeSetting : searchVO.getPageSize();  
	    	   
	    		
	    	    
	        	/** pageing */
	    		PaginationInfo paginationInfo = new PaginationInfo();
	    		paginationInfo.setCurrentPageNo(  searchVO.getPageIndex() < 1  ?  1 : searchVO.getPageIndex());
	    		paginationInfo.setRecordCountPerPage(pageUnit);
	    		paginationInfo.setPageSize(pageSize);
	    		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	    		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
	    		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
	    		
	    	    List<XmlInfoVO> codeList = xmlInfoManageService.selectXmlInfoManageListByPagination(searchVO);
	    	    int totCnt = codeList.size() > 0 ?   codeList.get(0).getTotalRecordCount() :0;
	            paginationInfo.setTotalRecordCount(totCnt);
	    		
	    		model.addObject(Globals.STATUS_REGINFO, searchVO);
	    		model.addObject(Globals.JSON_RETURN_RESULT_LIST, codeList);
	    		model.addObject(Globals.PAGE_TOTAL_COUNT, totCnt);
	    		model.addObject(Globals.JSON_PAGEINFO, paginationInfo);
	    		model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
	    		
    	      
    	      
          }catch(NullPointerException e){
        	  log.debug("selectXmlLst error: " + e.toString());
        	  model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
          }catch(Exception e){
        	  log.debug("selectXmlLst error: " + e.toString());
        	  model.addObject(Globals.STATUS_MESSAGE, this.egovMessageSource.getMessage("fail.request.msg"));
          }
	      
	      return model;
	}
	@ApiOperation(value="전문  상세 조회", notes = "성공시 전문 정보를 상세 조회 합니다.")
	@GetMapping(value="{xmlSeq}.do")
	public ModelAndView selectViewXml(@PathVariable String xmlSeq
									, HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}
			model.addObject(Globals.STATUS_WORKGROUP, cmmnDetailCodeManageService.selectCmmnDetailCombo("WORKGUBUN"));
	    	model.addObject(Globals.STATUS_SENDGROUP, cmmnDetailCodeManageService.selectCmmnDetailCombo("SEND_GUBUN"));
	    	model.addObject(Globals.STATUS_REGINFO, xmlInfoManageService.selectXmlrInfoManageDetail(xmlSeq) );
	    	model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);	
		}catch(NullPointerException e){
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
		}catch(Exception e){
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
		}
	return model;
	}
	
	@ApiOperation(value="전문 업데이트", notes = "성공시 전문 정보를 업데이트 합니다.")
	@PostMapping(value="xmlUpdate.do")
	public ModelAndView updateXml(XmlInfoVO vo
								, HttpServletRequest request
								, BindingResult bindingResult)throws Exception{
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		model.addObject("regist", vo);
		try{
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
	    	}else {
	    		vo.setUserId(jwtVerification.getTokenUserName(request));
	    	}
		    
		    int ret = xmlInfoManageService.updateXmlInfoManage(vo);
		    String meesage = (ret >0) ? vo.getMode().equals("Ins") ? "sucess.common.insert" : "sucess.common.update"
		    							: "fail.request.msg";
		    String Status =  (ret >0) ?  Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
		   
		    
		    
		    model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage(meesage));
	    	model.addObject(Globals.STATUS, Status);
	    	  
	    	  
		}catch (NullPointerException e){
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			
		}catch (Exception e){
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.request.msg"));	
			
		}					
		return model;
		
	}
	// groupCodeJson 체크 
	@ApiOperation(value="전문 결과 ", notes = "성공시 전문 결과값를 표출 합니다.")
	@NoLogging
	@GetMapping("jsonView/{xmlSeq}.do")
	public ModelAndView selectJsonPage(HttpServletRequest request,
			                           @RequestParam("xmlSeq") String xmlSeq) throws Exception{	
		   
		   ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		   try{
			   if (!jwtVerification.isVerification(request)) {
		    		ResultVO resultVO = new ResultVO();
					return jwtVerification.handleAuthError(resultVO); // 토큰 확
			   }
			   model = xmlInfoManageService.selectXmlrInfoManageDetail_JSONVIEW(xmlSeq);
		   }catch(NullPointerException e){
			   log.error("jsonAuthView :" + e.toString());
			   model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			   model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		   }catch(Exception e){
			   log.error("jsonAuthView :" + e.toString());
			   model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			   model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		   }
		   
		   return model;
	}
	@ApiOperation(value="전문 요청 값 ", notes = "성공시 전문 결과 요청 를 표출 합니다.")
	@NoLogging
	@RequestMapping({"jsonAuthReq/{xmlSeq}.do"})
	public ModelAndView selectJsonSendPage(HttpServletRequest request,
                                           @RequestParam("xmlSeq") String xmlSeq)   throws Exception {
		
		ModelAndView model = new 	ModelAndView(Globals.JSON_VIEW);
		try{
			
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확
		    }
			model = xmlInfoManageService.selectXmlrInfoManageDetail_JSONVIEW(xmlSeq); 
			/*LOGGER.debug("----1:" +model.toString() ); */  
		    Gson gson = new Gson();
			
		    JSONParser  jsonparse = new JSONParser(); 		
		    JSONObject jsonObject = (JSONObject) jsonparse.parse( gson.toJson( model).toString().replace("'", "\""));	
		     
			String json =jsonObject.get(Globals.JSON_RESULT_TOP).toString() ; 
			jsonObject = (JSONObject) jsonparse.parse( json);	
			
			String commandType = jsonObject.get(Globals.JSON_RESULT).toString();
			jsonObject = (JSONObject) jsonparse.parse( commandType);	
			commandType = jsonObject.get(Globals.JSON_RESULT_COMMAND).toString();
			
			
			int ProcessCk = xmlInfoManageService.selectXmlProcessCount(commandType);
			if (ProcessCk > 0){	
			 model.addObject(Globals.STATUS, Globals.STATUS_SUCCESS);
			 
			 jsonObject = (JSONObject) jsonparse.parse( json);
			 json =jsonObject.get(Globals.JSON_RESULT).toString() ; 
			 
			 //LOGGER.debug("json result:" + xmlInfoManageService.selectXmlrInfoManageDetail_JSONVIEW_RESULT(json));
			 model.addObject(Globals.JSON_RESULT, xmlInfoManageService.selectXmlrInfoManageDetail_JSONVIEW_RESULT(json));
			}else {
			 model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			 model.addObject(Globals.JSON_RESULT,"NO_JSON");
			}	
			
			
		}catch(NullPointerException e){
			 log.error("jsonAuthReq.do :" + e.toString());
			 model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			 model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		}catch(Exception e){
			log.error("jsonAuthReq.do :" + e.toString());
			 model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			 model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.select"));	
		}
	    return model;
	  }
	// jsonDoc 만들기
	@ApiOperation(value="전문 요청 값 ", notes = "성공시 전문 결과 요청 를 표출 합니다.")
	@GetMapping("jsonAuth/{json}.do")
	public String selectJsonResultPage(HttpServletRequest request,
			                           @RequestParam("json") String json) throws Exception{
		
		String jsonResult = "";
	    json = json.replace("'", "\"").replace("&quot;", "\"");
	    JSONParser  jsonparse = new JSONParser(); 		
	    JSONObject jsonObject = (JSONObject) jsonparse.parse(json);						 
		String commandType = jsonObject.get("command_type").toString();
		int ProcessCk = xmlInfoManageService.selectXmlProcessCount(commandType);
		if (ProcessCk > 0){	
			jsonResult =xmlInfoManageService.selectXmlrInfoManageDetail_JSONVIEW_RESULT(json);
		}else {
			jsonResult = "NO_JSON";
		}	
		return jsonResult;
		
	}
    
}
