package com.display.backoffice.sts.xml.web;


import javax.annotation.Resource;

import egovframework.com.cmm.EgovMessageSource;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import com.display.backoffice.sym.agent.service.AgentInfoManageService;
import com.display.backoffice.sts.xml.models.XmlInfo;
import com.display.backoffice.sts.xml.service.XmlInfoManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



@Api(tags = {"에이전트 전문 관리 API"})
@Slf4j
@RestController
@RequestMapping("/api/backoffice/operManage/xmlRest")

//@RestController
//@RequestMapping("/backoffice/operManage/")
public class XmlInfoManageRestContoller {
   
    
    @Resource(name="XmlInfoManageService")
    protected XmlInfoManageService xmlInfoManageService;
    
    @Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
    
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    @Autowired
    protected AgentInfoManageService agentService;
	
    
	//
	@GetMapping("/jsonAuthView/{xmlSeq}.do")
	public String jsonViewRes(@RequestParam String xmlSeq)throws Exception{
		return jsonDoc(xmlInfoManageService.selectXmlrInfoManageDetail(xmlSeq));
	}
	// jsonDoc 만들기
	public String jsonDoc(XmlInfo vo)
	{	
		String[] inputParamArrays ;
		inputParamArrays = vo.getXmlInputParam().split(",");
		String[] inputParamSampleArrays ;
		inputParamSampleArrays = vo.getXmlInputParamSample().split(",");
		
		
		JSONObject obj = new JSONObject();
		obj.put("command_type", vo.getXmlProcessName());
		
		try {			
			
			JSONArray dataArray = new JSONArray( ); 
			JSONObject sObject = new JSONObject();//배열 내에 들어갈 json			
			 for (int i = 0; i < inputParamArrays .length; i++){			
				sObject.put(inputParamArrays[i].toString().trim(), inputParamSampleArrays[i].toString().trim());				
			 }
			 dataArray.add(sObject);
			 obj.put("command_data", dataArray);
			 
		}catch (Exception e) {
			log.debug("jsonDoc:"+ e.toString());			
		}		
	   return obj.toJSONString(); 	
	}
}
