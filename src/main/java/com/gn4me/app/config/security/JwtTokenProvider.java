package com.gn4me.app.config.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.gn4me.app.config.props.SecurityProps;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.User;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.enums.Security;
import com.gn4me.app.entities.response.ResponseStatus;
import com.gn4me.app.util.AppException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Autowired
	SecurityProps securityProps;

	@PostConstruct
	protected void init() {
		securityProps.setSecretKey(Base64.getEncoder().encodeToString(securityProps.getSecretKey().getBytes()));
	}

	public String createToken(User user, int refresh) {

		Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put(Security.RIGHT_CLAIM.getValue(), user.getRightsFromRules());
		claims.put(Security.USER_ID_CLAIM.getValue(), user.getId());
		claims.put(Security.REFRESH_CLAIM.getValue(), refresh);
		claims.put(Security.RE_LOGIN_STATE.getValue(), securityProps.getReLoginState());

		Date now = new Date();
		Date validity = new Date(now.getTime() + securityProps.getExpiredWithin());
		

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS256, securityProps.getSecretKey()).compact();
	}
	
	@SuppressWarnings("unchecked")
	public User getUserAuth(String token, Transition transition) throws AppException {
		
		User user = null;	
		
		try {
			user = new User();
			Jws<Claims> claims = Jwts.parser().setSigningKey(securityProps.getSecretKey()).parseClaimsJws(token);
			
			int state = (Integer)claims.getBody().get(Security.RE_LOGIN_STATE.getValue());
			
			if(securityProps.getReLoginState() != state && state != 0) {
				throw new AppException(new ResponseStatus(ResponseCode.DUPRICATED_TOKEN), transition);
			}
			
			Object rights = claims.getBody().get(Security.RIGHT_CLAIM.getValue());
			user.setEmail(claims.getBody().getSubject());
			user.setId((Integer)claims.getBody().get(Security.USER_ID_CLAIM.getValue()));

			if(rights instanceof ArrayList) {
				ArrayList<String> list = (ArrayList<String>) rights;
				user.setRights(list.toArray(new String[0]));
			}
		} catch (JwtException | IllegalArgumentException e) {
			throw new AppException(new ResponseStatus(ResponseCode.INVALID_TOKEN), transition);
		}
		
		return user;
	}
	
	public Authentication getAuthentication(User user, Transition transition) throws AppException {
		if(user != null && user.getRights() != null) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
				    AuthorityUtils.createAuthorityList(user.getRights()));
			return authenticationToken;	
		} else {
			throw new AppException(new ResponseStatus(ResponseCode.UNAUTHORIZED), transition);
		}
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(Security.SEC_HEADER_PARAM.getValue());
		if (bearerToken != null && bearerToken.startsWith(Security.BEARER_PREFIX.getValue())) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public boolean validateToken(String token, Transition transition) throws AppException {
		try {
			Jwts.parser().setSigningKey(securityProps.getSecretKey()).parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
			throw new AppException(new ResponseStatus(ResponseCode.INVALID_TOKEN), transition);
		}
	}

}
