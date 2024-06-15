package com.display.backoffice.util.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.display.backoffice.sym.rabbitmq.models.dto.MessageDto;
import com.google.gson.Gson;

import egovframework.com.cmm.service.Globals;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONException;

//네트워크 util 서비스
@Slf4j
@Service
public class NetworkUtilService {

	@Value("${server.linkage.login_url}")
	private String url;
	
	@Value("${server.linkage.token_key}")
	private String key;
	
	@Value("${server.linkage.system_name}")
	private String name;
	
	@Value("${Globals.IPCC.url}")
	private String ipccUrl;
	
	
	
	public String getToken () {
		String tokenInfo = null;
		HttpURLConnection connection = null;
		try {
			URL api = new URL(url);
			connection = (HttpURLConnection)api.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(10000);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
		
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("systemCode", name);
			jsonObject.put("systemTokenKey", key);
			wr.write(new Gson().toJson(jsonObject));	

		
			wr.flush();
			connection.connect();
			int responseCode = connection.getResponseCode();
			if (responseCode == 400 || responseCode == 401 || responseCode == 500 ) {
				log.error("connection error:" + responseCode);
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				JSONParser parser = new JSONParser();
				JSONObject  responseJson = (JSONObject) parser.parse(sb.toString());
				if (responseJson.get(Globals.HTTP_STATUS_C).equals(Globals.STATUS_SUCCESS)) 
					tokenInfo = responseJson.get("jToken").toString();
				br.close();
			}
			connection.disconnect();
			connection = null;
		}catch (Exception e) {
			connection.disconnect();
		}finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return tokenInfo;
	}
	public JSONObject getResponse(String token,
								String reqUrl, 
								String paramData,
								String proessGubun,
								String reqId,
								String method) throws Exception{
		
		HttpURLConnection connection = null;
		BufferedReader br = null;
		JSONObject result = new JSONObject();
		try {
			
			
			URL url = new URL(ipccUrl + reqUrl);
			connection = (HttpURLConnection)url.openConnection();
			
			connection.setConnectTimeout(10000);
			connection.setRequestMethod(method);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Accept", "application/json");
			
			if (token != null && !token.equals("")) {
				connection.setRequestProperty("Authorization", "Bearer "+ token);
			}
			log.info("token:" + token + ": " + reqUrl);
			
			if (method.equals("POST")) {
				connection.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("systemCode", "");
				wr.write(new Gson().toJson(jsonObject));	
				wr.flush();
			}
			
			connection.connect();
			log.info("connection.getResponseCode():" + connection.getResponseCode());
			
			if (connection.getResponseCode() == 200 ) {
				
				
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				
				
				JSONParser parser = new JSONParser();
				JSONObject  responseJson = (JSONObject) parser.parse(sb.toString());
				br.close();
				
				
				if (responseJson.get(Globals.HTTP_STATUS_C).equals(Globals.STATUS_SUCCESS)) {
					result.put(Globals.HTTP_STATUS, Globals.STATUS_SUCCESS);
					
					if (!proessGubun.equals(Globals.SAVE_MODE_LIST))
						result.put(Globals.JSON_RETURN_RESULT, responseJson.get(Globals.JSON_RETURN_RESULT));
					else
						result.put(Globals.JSON_RETURN_RESULT, responseJson.get(Globals.JSON_RETURN_RESULT_LIST));
				}else {
					result.put(Globals.HTTP_STATUS, Globals.STATUS_FAIL);
				}
			}
			result.put(Globals.HTTP_STATUS, String.valueOf(connection.getResponseCode()));
		}catch (MalformedURLException e) {
			log.error("MalformedURLException error:" + e.toString());
			result.put(Globals.HTTP_STATUS, Globals.STATUS_FAIL);
		} catch (IOException e) {
			log.error("MalformedURLException error:" + e.toString());
			result.put(Globals.HTTP_STATUS, Globals.STATUS_FAIL);
		} catch (JSONException e) {
			log.error("not JSON Format response error:" + e.toString());
			e.printStackTrace();
		}catch (Exception e) {
			result.put(Globals.HTTP_STATUS, Globals.STATUS_FAIL);
			log.error("NetworkUtilService:" + e.getStackTrace()[0].getLineNumber());
			log.error("NetworkUtilService error:" + e.toString());
		}finally{
			if (br != null) br.close();
			if (connection != null) connection.disconnect();
		}
		return result;
	}
	 
}
