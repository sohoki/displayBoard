package egovframework.let.uat.uia.service.impl;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.LoginVO;
import egovframework.let.uat.uia.mapper.LoginManageMapper;
import egovframework.let.uat.uia.models.dto.LoginReqDto;
import egovframework.let.uat.uia.service.EgovLoginService;
import egovframework.let.utl.fcc.service.EgovNumberUtil;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.let.utl.sim.service.EgovFileScrty;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.display.backoffice.uat.uia.mapper.UserRoleInfoManageMapper;
import com.display.backoffice.uat.uia.mapper.UserTokenMapper;
import com.display.backoffice.uat.uia.models.UserToken;

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
@Service("loginService")
public class EgovLoginServiceImpl extends EgovAbstractServiceImpl implements EgovLoginService {

	
	@Autowired
	private LoginManageMapper loginMapper;
	
	@Autowired
	private UserTokenMapper tokenMapper;
	
	@Autowired 
	private UserRoleInfoManageMapper userRoleMapper;
	
	
	@Value("${token.refresh_time}")
	private String TOKEN_REFRESH_TIME;
	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
	@Override
	public AdminLoginVO actionLogin(LoginReqDto vo) throws Exception {

		// 1. 입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(vo.getAdminPassword(), vo.getAdminId());
		vo.setAdminPassword(enpassword);

		// 2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
		AdminLoginVO loginVO = loginMapper.actionLogin(vo);
		// 3. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getAdminId().equals("") && !loginVO.getAdminPassword().equals("")) {
			//추후 여기 부분을 system 권한 으로 변경 
			loginVO.setRoleInfo(userRoleMapper.userRoleInfoSelectList(loginVO.getAdminId()));
			return loginVO;
		} else {
			loginVO = new AdminLoginVO();
		}

		return loginVO;
	}

	/**
	 * 아이디를 찾는다.
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
	@Override
	public String searchId(LoginReqDto vo) throws Exception {

		// 1. 이름, 이메일주소가 DB와 일치하는 사용자 ID를 조회한다.
		return loginMapper.searchId(vo);
	}

	/**
	 * 비밀번호를 찾는다.
	 * @param vo LoginVO
	 * @return boolean
	 * @exception Exception
	 */
	@Transactional
	@Override
	public boolean searchPassword(LoginReqDto vo) throws Exception {

		boolean result = true;

		// 1. 아이디, 이름, 이메일주소, 비밀번호 힌트, 비밀번호 정답이 DB와 일치하는 사용자 Password를 조회한다.
		AdminLoginVO loginVO = loginMapper.searchPassword(vo);
		if (loginVO == null || loginVO.getAdminPassword() == null || loginVO.getAdminPassword().equals("")) {
			return false;
		}

		// 2. 임시 비밀번호를 생성한다.(영+영+숫+영+영+숫=6자리)
		String newpassword = "";
		for (int i = 1; i <= 6; i++) {
			// 영자
			if (i % 3 != 0) {
				newpassword += EgovStringUtil.getRandomStr('a', 'z');
				// 숫자
			} else {
				newpassword += EgovNumberUtil.getRandomNum(0, 9);
			}
		}

		// 3. 임시 비밀번호를 암호화하여 DB에 저장한다.
		AdminLoginVO pwVO = new AdminLoginVO();
		String enpassword = EgovFileScrty.encryptPassword(newpassword, vo.getAdminId());
		pwVO.setAdminId(vo.getAdminId());
		pwVO.setAdminPassword(enpassword);
		
		//메일 보내는 구문 넣어서 메일로 전송 
		
		//메일 보내는 구문 넣어서 메일로 전송
		loginMapper.updatePassword(pwVO);// .updatePassword(pwVO);

		return result;
	}
	@Transactional
	@Override
	public int updateRefreshToken(String userId, String updateRefreshToken, String userName) throws Exception {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		UserToken token = UserToken.builder()
				.userId(userId)
				.refreshToken(updateRefreshToken)
				.expireat( dateFormat.format(new Date(System.currentTimeMillis() + Long.parseLong(TOKEN_REFRESH_TIME))))
				.userName(userName)
				.build();	  
		return tokenMapper.updateTokenInfo(token);		
	}
	@Transactional
	@Override
	public int deleteRefreshToken(String userId) throws Exception {
		// TODO Auto-generated method stub
		return tokenMapper.deleteTokenInfo(userId);		
	}

	@Override
	public Optional<UserToken> selectTokenInfo(String refreshToken) throws Exception {
		// TODO Auto-generated method stub
		return tokenMapper.selectTokenInfo(refreshToken);
	}

	@Override
	public AdminLoginVO actionLoginSso(String vo) throws Exception {
		// TODO Auto-generated method stub
		AdminLoginVO loginVO = loginMapper.actionLoginSso(vo);
		// 3. 결과를 리턴한다.
		if (loginVO != null && !loginVO.getAdminId().equals("")) {
			
			loginVO.setRoleInfo(userRoleMapper.userRoleInfoSelectList(loginVO.getAdminId()));
			return loginVO;
		} else {
			loginVO = new AdminLoginVO();
		}

		return loginVO;
	}
}