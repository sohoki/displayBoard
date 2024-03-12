package com.display.backoffice.util.service;


import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.display.backoffice.sym.log.models.InterfaceInfo;
import com.display.backoffice.sym.log.models.sendEnum;

public class UtilInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UtilInfoService.class);
	
	@Autowired
	protected EgovPropertyService propertiesService;
	/*
	@Autowired
    public InterfaceInfoManageService interfaceService;

	@Autowired
    private void InterfaceInfoManageService(InterfaceInfoManageService interfaceService) {
        this.interfaceService = interfaceService;
    }
	*/
	public void XMLParse(String xmlData) throws ParserConfigurationException, SAXException, IOException{
		InputSource is = new InputSource(new StringReader(xmlData));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document doc = documentBuilder.parse(is);
		Element root = doc.getDocumentElement();
		NodeList chideren = root.getChildNodes();
		
		for (int i = 0; i < chideren.getLength(); i ++){
			Node node = chideren.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				Element ele = (Element)node;
				String nodeName = ele.getNodeName();
				if(nodeName.equals("")) {
					
				} else {
					
				}
			}
		}		
	}
	
	/*
	 *  오늘의 날자
	 * 
	 */
	public static String reqDay( int _number  ){
		LocalDate now = LocalDate.now();
		String dayFormat = _number == 0  ? now.format(DateTimeFormatter.ofPattern("yyyyMMdd")) :  now.plusDays(_number).format((DateTimeFormatter.ofPattern("yyyyMMdd")));
		return  dayFormat;
	}
	
	/*
	 *  해당 월 마지막 일자  날 구하기 
	 *  추후 월 일자, 요일 도 같이 하기 
	*/
    public static String reqEndDay(String _day){
    	//String day = LocalDate.parse("20181211", DateTimeFormatter.BASIC_ISO_DATE).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String dayFormat = LocalDate.parse(_day, DateTimeFormatter.BASIC_ISO_DATE).with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		return  dayFormat;
	}
    /*
     *  현재 시간과 비교 하여 초 환산 보내 주기 
     */
    public static String timeCheck(String _timeDate) {
    	LocalDateTime now = LocalDateTime.now();
    	//String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		LocalDateTime date = LocalDateTime.parse(_timeDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		Duration duration = Duration.between(now, date);
		return  String.valueOf(duration.getSeconds());
    }
    
    /*
     *  현재 시간
     * 
     * 
     */
    public static String nowTime() {
    	LocalDateTime now = LocalDateTime.now();
    	return now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    //null or empty 체크
    public static String NVL(String _val, String _replace) {
    	return _val.isEmpty() ? _replace : _val;
    }
    
    public static String NVL(Object _val, String _replace) {
    	return _val == null ? _replace : StringUtils.isBlank(_val.toString()) ? _replace : _val.toString();
    }
    public static String NVLObj(Object _val, String _replace) {
    	return _val == null ? _replace : StringUtils.isBlank(_val.toString()) ? _replace : _val.toString();
    }
    
    public static int NVL(Object _val, int _replace) {
    	return _val == null ? _replace : Integer.valueOf( _val.toString());
    }
	
    public static List<String> dotToList (String _dotlist) {
    	return !_dotlist.equals("") ?  Arrays.asList(_dotlist.split("\\s*,\\s*")) : null;
    }
    
    //요소 확인 후 삭제 하기 
    public static String checkItemList(List<String> _arrayList, String _nowVal, String _newVal) {
    	String itemList = "";
    	if (_arrayList.size()>0) {
    		
    		List list = _arrayList.stream().filter(e ->  !e.startsWith(_nowVal)).collect(Collectors.toList()); 
    		if (!_newVal.isEmpty())
    	    	list.add(_newVal);
    		itemList = (String) list.stream().distinct().sorted().collect(Collectors.joining(","));
    	}
    	return itemList;
    	
    }
    
    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static String nullConvert(Object src) {
		//if (src != null && src.getClass().getName().equals("java.math.BigDecimal")) {
		if (src != null && src instanceof java.math.BigDecimal) {
		    return ((BigDecimal)src).toString();
		}
	
		if (src == null || src.equals("null")) {
		    return "";
		} else {
		    return ((String)src).trim();
		}
    }
    public static String checkHtmlView(String strString) {
		String strNew = "";

		StringBuffer strTxt = new StringBuffer("");

		char chrBuff;
		int len = strString.length();

		for (int i = 0; i < len; i++) {
			chrBuff = (char) strString.charAt(i);

			switch (chrBuff) {
				case '<':
					strTxt.append("&lt;");
					break;
				case '>':
					strTxt.append("&gt;");
					break;
				case '"':
					strTxt.append("&quot;");
					break;
				case 10:
					strTxt.append("<br>");
					break;
				case ' ':
					strTxt.append("&nbsp;");
					break;
				case '&' :
					strTxt.append("&amp;");
				break;
				default:
					strTxt.append(chrBuff);
			}
		}

		strNew = strTxt.toString();

		return strNew;
	}

    /**
     * Object Empty Check
     * @param obj
     * @return
     */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String ) return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List) return obj == null || ((List)obj).isEmpty();
		else if (obj instanceof Map) return obj == null || ((Map)obj).isEmpty();
		else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
		else return obj == null;
		
	}

	/**
	 * Http통신 공용 함수
	 * @param _url
	 * @param _sendInfos
	 * @return
	 */
	public JsonNode requestHttpForm(String _url, Map<String, String> _sendInfos) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(_url);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            /*
            for (Map.Entry<String, String> entry : _sendInfos.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            */
            _sendInfos.forEach((key, value)  -> 
                               nameValuePairs.add(new BasicNameValuePair(key ,value)));
            
            //headers.forEach((key, value) -> map.put(key, Collections.unmodifiableList(value));
            		
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = client.execute(httpPost);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = null;
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                System.out.println("[RESPONSE] requestHttpForm() " + body);

                node = objectMapper.readTree(body);
            } else {
            	LOGGER.error("response is error:" + response.getStatusLine().getStatusCode());
                node = objectMapper.readTree("{\"ERROR CODE\":\""+ response.getStatusLine().getStatusCode() + "\"}");
            }
            return node;
        } catch (IOException e) {
            LOGGER.error("requestHttpForm IOException requestHttpForm : " + e.toString());
        }

        return null;
    }

	/**
	 * json 전송후 값 받아 오기   
	 *
	 * @param _url (전송 URL)
	 * @param _jsonInfo (전송 Json)
	 * @param _integId (연계 ID)
	 * @param _provdId (제공 ID)
	 * @param _requstId (요청 ID)
	 * @return
	 */
	public static JsonNode requestHttpJson(String _url, String _jsonInfo, String _integId, String _provdId, String _requstId){

        HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
        HttpPost httpPost = new HttpPost(_url); //POST 메소드 URL 새성
        try {
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("Content-Type", "application/json");

            httpPost.setEntity(new StringEntity(_jsonInfo, "UTF-8")); //json 메시지 입력
            String requstTrnsmitTm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            HttpResponse response = client.execute(httpPost);
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = null;
            //Response 출력
            
            InterfaceInfo info = new InterfaceInfo();
            info.setTrsmrcvSeCode(sendEnum.RPQ.getCode() );
            info.setIntegId(_integId);
            
            info.setRequstInsttId(_requstId);
            info.setRspnsRecptnTm(nowTime());
            
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                node = objectMapper.readTree(body);       
            } else {
            	LOGGER.error("response is error : " + response.getStatusLine().getStatusCode());
            	node = objectMapper.readTree("{\"Error_Cd\":\""+ response.getStatusLine().getStatusCode() + "\"}");
            }
            
            //전송 내용 & 수신 요청 
            info.setRequstTrnsmitTm(requstTrnsmitTm);
            info.setRspnsRecptnTm(nowTime());
            info.setResultCode(node.get("Error_Cd").asText());
            info.setResultMessage(node.toString());
            info.setSendMessage(_jsonInfo);
            info.setProvdInsttId(_provdId);
            info.setRqesterId("SYSTEM");
            //interfaceService.InterfaceInsertLoginLog(info);
            
            return node;
        } catch (Exception e) {
        	LOGGER.error("requestHttpJson Exception ERROR : " + e.toString());
        }

        return null;
    }
	
	public static String[] getSplitPhNum(String phNum) throws Exception {
		String[] splitPhNumArray = new String[3];
		
		if(phNum.length() >= 9 && phNum.length() <= 12) {
			if(phNum.length() == 11) {
				splitPhNumArray[0] = phNum.substring(0, 3);
				splitPhNumArray[1] = phNum.substring(3, 7);
				splitPhNumArray[2] = phNum.substring(7, 11);
			} else {
				splitPhNumArray[0] = phNum.substring(0, 3);
				splitPhNumArray[1] = phNum.substring(3, 6);
				splitPhNumArray[2] = phNum.substring(6, 10);
			}
		} else {
			LOGGER.info("올바르지 않은 휴대전화번호 기입");
			throw new Exception();
		}
		
		return splitPhNumArray;
	}
	
	
	/**
	 * 스피드온 비밀번호 암호화 기능(복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
	 * 
	 * @param data 암호화할 비밀번호
	 * @param salt Salt
	 * @return 암호화된 비밀번호
	 * @throws Exception
	 */
	public static String encryptPassword(String data, String encryptType) {
		byte[] hashValue = null; // 해쉬값
		MessageDigest md;
		
		try {

			md = MessageDigest.getInstance(encryptType);
			md.reset();

			hashValue = md.digest(data.getBytes());

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("encryptPassword NoSuchAlgorithmException ERROR : " + e.toString());
		}
		
		return new String(Base64.encodeBase64(hashValue)); 
	}
	
	/**
	 * SPDM제공 관리자 계정 패스워드 암호화 함수
	 * 
	 * @param a_origin
	 * @return
	 */
	public static String getEncryptSHA256(String a_origin) {
		String encryptedSHA256 = "";
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(a_origin.getBytes(), 0, a_origin.length());
			encryptedSHA256 = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("getEncryptSHA256 NoSuchAlgorithmException ERROR : " + e.toString());
		}
		
		return encryptedSHA256;
	}

	/**
	 * 문자열 알파벳만 검증
	 * 
	 * @param String str
	 * @return boolean
	 */
	public static boolean isValidAlphabet(String str) {
		String regex = "^[a-zA-Z]+$";
		// 문자열이 정규식 패턴과 일치하는지 확인
	    return str.matches(regex);
	}

	/**
	 * 문자열 이메일 형식 검증
	 * 
	 * @param String email
	 * @return boolean
	 */
	public static boolean isValidEmail(String email) {
		 // 이메일 주소를 검증하기 위한 정규식 패턴
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        // 정규식에 일치하면 true를 반환, 그렇지 않으면 false를 반환
        return matcher.matches();
	}
}
