package egovframework.let.uat.uia.service.impl;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.let.uat.uia.mapper.EgovLoginMapper;
import egovframework.let.uat.uia.models.dto.LoginReqDto;
import egovframework.let.uat.uia.service.EgovLoginService;
import egovframework.let.utl.fcc.service.EgovNumberUtil;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.let.utl.sim.service.EgovFileScrty;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


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
	private EgovLoginMapper loginMapper;

	
	
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
		String  adminId = loginMapper.searchId(vo);

		// 2. 결과를 리턴한다.
		if (adminId != null && !adminId.equals("")) {
			return adminId;
		} else {
			return "";
		}
	}

	/**
	 * 비밀번호를 찾는다.
	 * @param vo LoginVO
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean searchPassword(LoginReqDto vo) throws Exception {

		boolean result = true;

		// 1. 아이디, 이름, 이메일주소, 비밀번호 힌트, 비밀번호 정답이 DB와 일치하는 사용자 Password를 조회한다.
		String  adminPassword = loginMapper.searchPassword(vo);
		
		
		if ( adminPassword == null || adminPassword.equals("")) {
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
		
		loginMapper.updatePassword(pwVO);

		return result;
	}

	@Override
	public AdminLoginVO actionLoginSso(String adminId) throws Exception {
		// TODO Auto-generated method stub
		return loginMapper.actionLoginSso(adminId);
	}

	
}