package com.display.backoffice.uat.uia.mapper;

import java.util.Optional;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import com.display.backoffice.uat.uia.models.UserToken;

@Mapper
public interface UserTokenMapper {

	//토큰 조회 
	//Optional 활용해 보기 
	public Optional<UserToken> selectTokenInfo(String refreshToken);
	
	//token 생성 
	public int updateTokenInfo(UserToken tokeninfo);
	
	//로그 아웃 
	public int deleteTokenInfo(String userId );
	
	//만료 시간 삭제
	public int deleteExpirestTokenInfo();
}
