package com.display.backoffice.sts.cnt.web;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.service.ResultVO;
import egovframework.com.jwt.config.JwtVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.display.backoffice.sts.cnt.service.ContentFileInfoManageService;
import com.display.backoffice.sts.cnt.models.ContentFileInfoVO;



@Api(tags = {"파일 업로드 연동 API"})
@Slf4j
@RequiredArgsConstructor // final이 선언된 모든 필드를 인자값으로 하는 생성자를 대신 생성하여, 빈을 생성자로 주입받게 한다.
@RestController
@RequestMapping("/api/backoffice/sys/file")
public class FileUpladController {

	
	
	/** EgovPropertyService */
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	    
    @Resource(name="ContentFileInfoManageService")
	private ContentFileInfoManageService conFileService;
    
    /** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	@ApiOperation(value="파일 정보 업로드", notes = "성공시 파일 정보 합니다.")
	@GetMapping(value="conFileUpdate.do")
	public ModelAndView conMutiDetail (@RequestParam Map<String, Object> commandMap,
										HttpServletRequest request,
										BindingResult bindingResult)throws Exception{
		
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);
		try {
			String atchFileId = commandMap.get("atchFileId") != null ? commandMap.get("atchFileId").toString() : "";
			String playTime = commandMap.get("playTime") != null ? commandMap.get("playTime").toString() : "";
			String fileWidth = commandMap.get("fileWidth") != null ? commandMap.get("fileWidth").toString() : "";
			String fileHeight = commandMap.get("fileHeight") != null ? commandMap.get("fileHeight").toString() : "";
			
			ContentFileInfoVO vo = new ContentFileInfoVO();
			vo.setAtchFileId(atchFileId);
			if (!playTime.equals("") && playTime != null){
			    vo.setPlayTime(Integer.toString((int)( Double.parseDouble( playTime))));
			}
			vo.setFileWidth(fileWidth);
			vo.setFileHeight(fileHeight);
			String delResult = "";	
			
			if (!jwtVerification.isVerification(request)) {
	    		ResultVO resultVO = new ResultVO();
				return jwtVerification.handleAuthError(resultVO); // 토큰 확인
	    	}else {
	    		vo.setUserId(jwtVerification.getTokenUserName(request));
	    	}
			
			int ret = conFileService.updateFileDetailInfo(vo) ;
			String status =  (ret > 0) ? Globals.STATUS_SUCCESS : Globals.STATUS_FAIL;
			
			model.addObject(Globals.STATUS, status);
			
		}catch(Exception e) {
			model.addObject(Globals.STATUS, Globals.STATUS_FAIL);
			model.addObject(Globals.STATUS_MESSAGE, egovMessageSource.getMessage("fail.common.msg") + e.toString());	
		}
		return model;
	}
	
}
