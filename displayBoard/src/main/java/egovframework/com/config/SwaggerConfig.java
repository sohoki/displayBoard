package egovframework.com.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAsync
@EnableSwagger2
public class SwaggerConfig {
	
	private static final String API_NAME = "DISPLAY MANAGEMENT";
	private static final String API_VERSION = "1.0.0";
	private static final String API_DESCRIPTION = "전광판 명세서";
	
	
	@Bean
	public Docket api() {
		return  new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.POST, getArrayList())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();		
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(API_NAME)
				.version(API_VERSION)
				.description(API_DESCRIPTION)
				.license("MIT License").licenseUrl("http://opensource.org/licenses/MIT")//
				.contact(new Contact("eGovFrame", "https://www.egovframe.go.kr/", "egovframesupport@gmail.com"))
				.build();
	}
	private ArrayList<ResponseMessage> getArrayList() {
        ArrayList<ResponseMessage> lists = new ArrayList<ResponseMessage>();
        lists.add(new ResponseMessageBuilder().code(500).message("500 ERROR").build());
        lists.add(new ResponseMessageBuilder().code(403).message("403 ERROR").build());
        lists.add(new ResponseMessageBuilder().code(401).message("401 ERROR").build());
        return lists;
    }
}
