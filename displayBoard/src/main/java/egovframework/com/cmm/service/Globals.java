package egovframework.com.cmm.service;

/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 * 
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see 
 * 
 */

public class Globals {
    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "filePath";
	
	public static final String STATUS = "STATUS";
	public static final String STATUS_MESSAGE = "MESSAGE";
	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_FAIL = "FAIL";
	
	//token 
	public static final String TOKEN = "jToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	
	
	public static final String HTTP_STATUS = "status";
	public static final String HTTP_STATUS_C = "STATUS";
	public static final String STATUS_UNIQUE = "UNIQUE";
	public static final String STATUS_UNIQUE_FAIL = "UNIQUE_FAIL";
	public static final String STATUS_LOGIN_FAIL = "LOGIN_FAIL";
	public static final String STATUS_OVERLAP_FAIL = "OVERLAP_FAIL";
	public static final String STATUS_SESSION_FAIL = "SESSION_FAIL";
	public static final String STATUS_REFERRER_FAIL = "REFERRER_FAIL";
	
	public static final String JSON_VIEW = "jsonView";
	public static final String JSON_RESULT = "json_res";
	public static final String JSON_RESULT_TOP = "model";
	public static final String JSON_RESULT_MELON = "melon";
	public static final String JSON_RESULT_PUSH = "pushCnt";
	public static final String JSON_RESULT_IOT = "iot";
	public static final String JSON_RETURN_RESULT = "result";
	public static final String JSON_RETURN_RESULT_ADD = "resultAdd";
	public static final String JSON_RETURN_RESULT_LIST = "resultList";
	public static final String JSON_PAGEINFO = "paginationInfo";
	
	public static final String NETWORK_RESULT_PROGRAME = "PROGRAME";
	public static final String NETWORK_RESULT_MENUINFO = "MENUINFO";
	
	
	public static final String NEXUS_CENTER_COMBO = "centerlist";
	
	public static final String LOGIN_CONNECT_MTHD_I = "I"; // 로그인
	public static final String LOGIN_CONNECT_MTHD_O = "O"; // 로그아웃
	public static final String ADMIN_INFO = "userId";

	public static final String SAVE_MODE_INSERT = "Ins";
	public static final String SAVE_MODE_UPDATE = "Edt";
	public static final String SAVE_MODE_LIST = "LIST";
	public static final String SAVE_MODE_DELETE = "DELETE";
	
	public static final String SYSLOG_PROCESS_SE_CODE_INSERT = "I";
	public static final String SYSLOG_PROCESS_SE_CODE_UPDATE = "U";
	public static final String SYSLOG_PROCESS_SE_CODE_DELETE = "D";
	public static final String SYSLOG_PROCESS_SE_CODE_SELECT = "S";
	
	public static final String TOKEN_CLAIM_NAME = "authorities";
	public static final String TOKEN_ACCESS_KEY = "accessToken";
	public static final String TOKEN_REFRESH_KEY = "refreshToken";
	public static final String TOKEN_AUTHOR_ERROR = "TOKEN ERROR";
	
	public static final String STATUS_FAIL_FREEZONE = "FAIL_FREEZONE"; 
	
	public static final String STATUS_FLOORS = "selectFloorCombo";
	public static final String STATUS_FLOORE = "selectFloorEndCombo";
	public static final String STATUS_GROUP = "selectGroupCombo";
	public static final String STATUS_DISPLAYCOMBO = "selectDisplayCombo";
	public static final String STATUS_DISPLAYCOMBO_NEXT = "selectDisplayNextCombo";
	
	public static final String STATUS_WORKGROUP = "selectWorkgubun";
	public static final String STATUS_SENDGROUP = "selectSendgubun";
	
	
	public static final String STATUS_REGINFO = "regist"; 
	
	public static final String STATUS_LOGINFAIL = "LOGIN FAIL";
	
	public static final String PAGE_FIRST_INDEX =  "firstIndex";
	public static final String PAGE_LAST_INDEX =  "lastRecordIndex";
	public static final String PAGE_RECORD_PER_PAGE =  "recordCountPerPage";
	public static final String PAGE_INDEX =  "pageIndex";
	public static final String PAGE_TOTAL_RECORD_COUNT =  "totalRecordCount";
	public static final String PAGE_INFO = "paginationInfo";
	public static final String PAGE_SIZE = "pageSize";
	public static final String PAGE_UNIT = "pageUnit";
	public static final String PAGE_TOTAL_COUNT = "totalCnt";
	public static final String PAGE_TOTAL_TIME = "totTime";
	
	public static final String PAGE_DOMAIN = "domainInfo";
	public static final String PAGE_SYSTEM_CODE = "systemCode";
	//전문 
	public static final String JSON_RESULT_COMMAND = "command_type";
	public static final String JSON_RESULT_COMMAND_ERROR = "error_command";
	
	public static final String JSON_RESULT_DATA = "command_data";
	public static final String JSON_RESULT_DATAERROR = "error_data";
	
	public static final String JSON_RETURN_CONFIGE = "CONINFO";
	public static final String JSON_RETURN_MESSAGEKEY = "MESSAGEINFO";
	
	public static final String STATUS_NTUNIQUE = "UNIQUE FAIL";
	public static final String JSON_RESULT_REPORT = "reportInfo";
	
	
	//token 
	public static final String USER_ROLE_ID = "roleId";
	public static final String USER_PART_ID = "partId";
	public static final String USER_INSTT_CODE = "insttCode";
	
}
