package com.display.backoffice.sts.error.web;

import com.display.backoffice.sts.error.models.ErrorInfo;
import com.display.backoffice.sts.error.models.ErrorInfoVO;
import com.display.backoffice.sts.error.service.ErrorInfoManageService;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import com.display.backoffice.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/backoffice/*")
public class ErrorInfoManageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorInfoManageController.class);
	
	
	@Resource (name="ErrorInfoManageService")
	private ErrorInfoManageService errService;
	
	
	@RequestMapping("errList")
	public String selectT(@RequestBody ErrorInfoVO searchVO)throws Exception{
		
        JSONObject datas = new JSONObject();
		JSONObject  result = new JSONObject();
		JSONObject data_list = new JSONObject();
		try{
			
			datas.put("result", true);
			JSONObject paginationInfo = new JSONObject();
			JSONObject pagination = new JSONObject();
			pagination.put("page", searchVO.getPageIndex());
			pagination.put("totalCount",  errService.selectErrorMessageCnt(searchVO));
			data_list.put("contents", errService.selectErrorMessage(searchVO));
			data_list.put("pagination", pagination);
			
			datas.put("data", data_list);
			
		}catch (NullPointerException e){
			datas.put("result", true);
			datas.put("data", null);
			datas.put(Globals.STATUS_MESSAGE, e.toString());
		}catch (Exception e){
			datas.put("result", true);
			datas.put("data", null);
			datas.put(Globals.STATUS_MESSAGE, e.toString());
		}
		return datas.toString();
	}
	@RequestMapping(value="errList.do")
	public ModelAndView selectErrorData( @ModelAttribute("loginVO") AdminLoginVO loginVO 
                                        )throws Exception{
		ModelAndView mav = new ModelAndView("/backoffice/operManage/logList");
		return mav;
	}
	
	@RequestMapping("errListAjax.do")
	public String selectErrorData( AdminLoginVO loginVO ,
			                       ErrorInfoVO searchVO )throws Exception{
		JSONObject datas = new JSONObject();
		
		JSONObject  result = new JSONObject();
		JSONObject data_list = new JSONObject();
		try{
			
			datas.put("result", true);
			
			
			List<ErrorInfoVO> jsonList = errService.selectErrorMessage(searchVO);
			JSONArray contentList =  JSONArray.fromObject(jsonList);
			
			JSONObject paginationInfo = new JSONObject();
			JSONObject pagination = new JSONObject();
			pagination.put("page", searchVO.getPageIndex());
			pagination.put("totalCount",  errService.selectErrorMessageCnt(searchVO));
			data_list.put("contents", contentList );
			data_list.put("pagination", pagination);
			
			datas.put("data", data_list);
			
		}catch (NullPointerException e){
			datas.put("result", true);
			datas.put("data", null);
			datas.put(Globals.STATUS_MESSAGE, e.toString());
		}catch (Exception e){
			datas.put("result", true);
			datas.put("data", null);
			datas.put(Globals.STATUS_MESSAGE, e.toString());
		}
		return datas.toString();
	}
}
