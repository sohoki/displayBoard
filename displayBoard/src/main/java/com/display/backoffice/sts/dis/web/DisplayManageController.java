package com.display.backoffice.sts.dis.web;

import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.display.backoffice.sts.dis.service.DisplayManageService;
import com.display.backoffice.sts.report.models.ReportPageInfoVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


@Api(tags = {"공통상세코드 정보 API"})
@Slf4j
@RestController
@RequestMapping("/backoffice/display")
public class DisplayManageController {

	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
	protected DisplayManageService displayService;
	
	@RequestMapping(value="gqAjax.do")
	public ModelAndView  selectQqListByPagination( HttpServletRequest request	) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);

         model.addObject("reglist", displayService.selectDisplayGq());  
		 model.addObject("STATUS", "SUCCESS");
		
		return model;	
	}
	@RequestMapping(value="aiAjax.do")
	public ModelAndView  selectAiListByPagination( HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);

         model.addObject("reglist", displayService.selectDisplayAi());  
		 model.addObject("STATUS", "SUCCESS");
		
		return model;	
	}
	@RequestMapping(value="asAjax.do")
	public ModelAndView  selectAsListByPagination( HttpServletRequest request	) throws Exception {
		ModelAndView model = new ModelAndView(Globals.JSON_VIEW);

         model.addObject("reglist", displayService.selectDisplayAs());  
		 model.addObject("STATUS", "SUCCESS");
		
		return model;	
	}
	@RequestMapping(value="atAjax.do")
	public ModelAndView  selectAtListByPagination( HttpServletRequest request) throws Exception {
		 ModelAndView model = new ModelAndView(Globals.JSON_VIEW);

         model.addObject("reglist", displayService.selectDisplayAt());  
		 model.addObject("STATUS", "SUCCESS");
		
		return model;	
	}
}
