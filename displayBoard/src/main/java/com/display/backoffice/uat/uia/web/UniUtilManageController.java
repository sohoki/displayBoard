package com.display.backoffice.uat.uia.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.display.backoffice.uat.uia.models.UniUtilInfo;
import com.display.backoffice.uat.uia.service.UniUtilManageService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = {"공통관리 연동 API"})
@Slf4j
@RestController
public class UniUtilManageController {


	
    @Autowired
	private UniUtilManageService utilService;
	
	
	@RequestMapping(value="/api/util/UniCheck.do")
	@ResponseBody
	public String selectUniCheck(@ModelAttribute("vo") UniUtilInfo vo
								 , HttpServletRequest request
								 , BindingResult bindingResult						
								 , ModelMap model)throws Exception{
		
		
		try{
			
			log.debug("vo:" + vo.getInTable()+","+vo.getInCheckName()+","+vo.getInCondition());
			int ret = utilService.selectIdDoubleCheck(vo);
			log.debug("ret:"+ret);
			if (ret > 0){
				return "E";
			}else {
				return "O";
			}
		}catch(Exception e){
			log.debug("selectUniCheck error:" + e.toString());
			return "F";
		}
	}
	@RequestMapping(value="/api/util/UnideleteParam.do")
	@ResponseBody
	public String uniDeleteParam(HttpServletRequest request) throws Exception {
		
      try{
			
			String tableId =  request.getParameter("tableId");
			String conField =  request.getParameter("conField");
			String value =  request.getParameter("value");
			
    	    UniUtilInfo utilInfo = new UniUtilInfo();
  		    utilInfo.setInTable(tableId);
  		    utilInfo.setInCondition(conField+"=["+value+"[");
			
			int ret = utilService.deleteUniStatement(utilInfo);
			
			log.debug("ret:"+ret);
			if (ret > 0){
				return "O";
			}else {
				return "F";
			}
		}catch(Exception e){
			log.debug("uniDelete error:" + e.toString());
			return "F";
		}
	}
	@RequestMapping(value="/api/util/Unidelete.do")
	@ResponseBody
	public String uniDelete(@ModelAttribute("vo") UniUtilInfo vo
							, HttpServletRequest request
							, BindingResult bindingResult						
							, ModelMap model)throws Exception{
		try{
			
			int ret = utilService.deleteUniStatement(vo);
			
			log.debug("ret:"+ret);
			if (ret > 0){
				return "O";
			}else {
				return "F";
			}
		}catch(Exception e){
			log.debug("uniDelete error:" + e.toString());
			return "F";
		}
	}
	public int callUniDelete(UniUtilInfo vo)throws Exception{
		//임시 조치
		vo.setOtCnt(-1);
		utilService.deleteUniStatement(vo);
		int otCnt =vo.getOtCnt();
		log.debug("otCnt:" + otCnt);
		return 1;
	}
}
