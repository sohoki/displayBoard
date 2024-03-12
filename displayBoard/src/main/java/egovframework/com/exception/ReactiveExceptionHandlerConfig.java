package egovframework.com.exception;

import org.springframework.context.MessageSource;

import egovframework.com.exception.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReactiveExceptionHandlerConfig {

	private final MessageSource messageSource;

    public ReactiveExceptionHandlerConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 에러 발생 시 에러 정보 중 필요한 내용만 반환한다
     *
     * @return
     */
    /*
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
                Map<String, Object> defaultMap = super.getErrorAttributes(request, options);
                Map<String, Object> errorAttributes = new LinkedHashMap<>();

                int status = (int) defaultMap.get("status");
                ErrorCode errorCode = getErrorCode(status);
                String message = messageSource.getMessage(errorCode.getMessage(), null, LocaleContextHolder.getLocale());
                errorAttributes.put("timestamp", LocalDateTime.now());
                errorAttributes.put("message", message);
                errorAttributes.put("status", status);
                errorAttributes.put("code", errorCode.getCode());
                // API Gateway 에서 FieldError는 처리하지 않는다.

                log.error("getErrorAttributes()={}", defaultMap);
                return errorAttributes;
            }
        };
    }
	*/
    /**
     * 상태코드로부터 ErrorCode 를 매핑하여 리턴한다.
     *
     * @param status
     * @return
     */
    private ErrorCode getErrorCode(int status) {
        switch (status) {
            case 400:
                return ErrorCode.ENTITY_NOT_FOUND;
            case 401:
                return ErrorCode.UNAUTHORIZED;
            case 403:
                return ErrorCode.ACCESS_DENIED;
            case 404:
                return ErrorCode.NOT_FOUND;
            case 405:
                return ErrorCode.METHOD_NOT_ALLOWED;
            case 422:
                return ErrorCode.UNPROCESSABLE_ENTITY;
            default:
                return ErrorCode.INTERNAL_SERVER_ERROR;
        }
    }
}
