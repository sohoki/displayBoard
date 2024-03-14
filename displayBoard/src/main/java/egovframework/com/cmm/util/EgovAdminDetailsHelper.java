package egovframework.com.cmm.util;

import java.util.ArrayList;
import java.util.List;

import org.egovframe.rte.fdl.string.EgovObjectUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import egovframework.com.cmm.AdminLoginVO;

public class EgovAdminDetailsHelper {

	/**
	 * 인증된 사용자객체를 VO형식으로 가져온다.
	 * @return Object - 사용자 ValueObject
	 */
	public static Object getAuthenticatedUser() {
		return (AdminLoginVO)RequestContextHolder.currentRequestAttributes().getAttribute("AdminLoginVO", RequestAttributes.SCOPE_SESSION)==null ?
				new AdminLoginVO() : (AdminLoginVO) RequestContextHolder.currentRequestAttributes().getAttribute("AdminLoginVO", RequestAttributes.SCOPE_SESSION);

	}

	/**
	 * 인증된 사용자의 권한 정보를 가져온다.
	 * 예) [ROLE_ADMIN, ROLE_USER, ROLE_A, ROLE_B, ROLE_RESTRICTED, IS_AUTHENTICATED_FULLY, IS_AUTHENTICATED_REMEMBERED, IS_AUTHENTICATED_ANONYMOUSLY]
	 * @return List - 사용자 권한정보 목록
	 */
	public static List<String> getAuthorities() {
		List<String> listAuth = new ArrayList<String>();

		if (EgovObjectUtil.isNull(RequestContextHolder.currentRequestAttributes().getAttribute("AdminLoginVO", RequestAttributes.SCOPE_SESSION))) {
			// log.debug("## authentication object is null!!");
			return null;
		}

		return listAuth;
	}

	/**
	 * 인증된 사용자 여부를 체크한다.
	 * @return Boolean - 인증된 사용자 여부(TRUE / FALSE)
	 */
	public static Boolean isAuthenticated() {
		if (EgovObjectUtil.isNull(RequestContextHolder.currentRequestAttributes().getAttribute("AdminLoginVO", RequestAttributes.SCOPE_SESSION))) {
			// log.debug("## authentication object is null!!");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
