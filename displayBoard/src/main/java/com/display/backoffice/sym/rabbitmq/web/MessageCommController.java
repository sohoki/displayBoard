package com.display.backoffice.sym.rabbitmq.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.util.service.NetworkUtilService;
import com.google.gson.Gson;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;

@Api(tags = {"Message test API"})
@Slf4j
@RestController
public class MessageCommController {

	
	

	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
	protected NetworkUtilService networkService;
	
	
	
	@GetMapping("messageTest.do")
	public ModelAndView systemCombo(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView (Globals.JSON_VIEW);
		try {
			String token = networkService.getToken();
			String url = "http://localhost:8080/api/backoffice/uat/uia/manager/ID9991.do";
			System.out.println("token:" + token);
			if (token != null) {
				JSONObject object = networkService.getResponse(token, url, "", "INSERT", null, "GET");
				model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
				model.addObject(Globals.JSON_RETURN_RESULT, object);
			}else {
				model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			}
		} catch(Exception e){
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg") + e.toString());
		}
		return model;
	}
}
