package egovframework.let.uat.uia.service;

import java.util.Optional;

import com.display.backoffice.uat.uia.models.UserToken;
import egovframework.com.cmm.AdminLoginVO;
import egovframework.let.uat.uia.models.dto.LoginReqDto;

/**
 * 일반 로그인을 처리하는 비즈니스 구현 클래스
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.06
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2009.03.06  박지욱          최초 생성
 *  2011.08.31  JJY            경량환경 템플릿 커스터마이징버전 생성
 *
 *  </pre>
 */
public interface EgovLoginService {

	/**
	 * 일반 로그인을 처리한다
	 * @return LoginVO
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public AdminLoginVO actionLogin(LoginReqDto vo) throws Exception;
	
	public AdminLoginVO actionLoginSso(String vo) throws Exception;

	/**
	 * 아이디를 찾는다.
	 * @return LoginVO
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public String searchId(LoginReqDto vo) throws Exception;

	/**
	 * 비밀번호를 찾는다.
	 * @return boolean
	 *
	 * @param vo    LoginVO
	 * @exception Exception Exception
	 */
	public boolean searchPassword(LoginReqDto vo) throws Exception;
	
	
	public int updateRefreshToken(String userId, String updateRefreshToken, String userName) throws Exception;
	
	public int deleteRefreshToken(String userId) throws Exception;
	
	public Optional<UserToken> selectTokenInfo(String refreshToken) throws Exception;

}