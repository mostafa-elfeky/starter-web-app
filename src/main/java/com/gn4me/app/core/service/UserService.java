package com.gn4me.app.core.service;


import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gn4me.app.config.security.JwtTokenProvider;
import com.gn4me.app.core.dao.AuthDao;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.User;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.enums.Security;
import com.gn4me.app.entities.enums.SystemStatusEnum;
import com.gn4me.app.entities.response.AppResponse;
import com.gn4me.app.entities.response.AppResponse.ResponseBuilder;
import com.gn4me.app.entities.response.GeneralResponse;
import com.gn4me.app.entities.response.ResponseStatus;
import com.gn4me.app.entities.response.UserResponse;
import com.gn4me.app.log.Loggable;
import com.gn4me.app.log.Type;
import com.gn4me.app.mail.MailHandler;
import com.gn4me.app.util.AppException;
import com.gn4me.app.util.SystemLoader;
import com.gn4me.app.util.UtilHandler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Service
@Loggable(Type = Type.SERVICCE)
public class UserService {

	@Autowired
	private AuthDao authDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Value("${security.secret-key}")
	private String secretKey;
	
	@Value("${security.max-refresh-rate}")
	private int maxRefreshRate;
	
	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds;
	
	@Value("${security.relogin.state}")
	private int reloginState;
	
	@Value("${user.confirm.validityTime}")
	int validityTime;
	
	@Autowired
	private MailHandler mailHandler;
	
	@Autowired
	private UtilHandler utilHandler;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public AppResponse<User> signin(String username, String password, Transition transition) throws AppException {
		
		ResponseBuilder<User> respBuilder = AppResponse.builder(transition);
		
		User user = authDao.findUserByUsername(username, transition);
		
		if(user != null 
				&& user.getStatus().getCode().equals(SystemStatusEnum.ACTIVE.name())
				&& passwordEncoder.matches(password, user.getPassword())) {
			
			 String token = jwtTokenProvider.createToken(user, 1);
			 respBuilder.data(user);
			 respBuilder.info("token", token);	
			 
		} else {
			respBuilder.status(ResponseCode.INVALID_AUTH);
		}

		return respBuilder.build();
	}

	public UserResponse refreshKey(String token, Transition transition) throws AppException {
		
		UserResponse response  = new UserResponse();
		String email = null;
		int refreshRate = 0, state = 0;

		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			
			email = claims.getBody().getSubject();
			refreshRate = (Integer)claims.getBody().get(Security.REFRESH_CLAIM.getValue());
			state = (Integer)claims.getBody().get(Security.RE_LOGIN_STATE.getValue());
			
			if(reloginState != state && state != 0) {
				throw new AppException(new ResponseStatus(ResponseCode.DUPRICATED_TOKEN), transition);
			}
			
		} catch (ExpiredJwtException e) {
			try {
				email =  e.getClaims().getSubject();
				refreshRate = (Integer) e.getClaims().get(Security.REFRESH_CLAIM.getValue());
			} catch(Exception exp) {
				throw new AppException(new ResponseStatus(ResponseCode.GENERAL_FAILURE), transition);
			}
		} catch (JwtException | IllegalArgumentException e) {
			throw new AppException(new ResponseStatus(ResponseCode.INVALID_TOKEN), transition);
		}
		// we may want to test if request date not exceed max date
		if(email != null && refreshRate != 0 && refreshRate < maxRefreshRate) {
			User user = authDao.findUserByUsername(email, transition);
			
			if(user != null) {
				 String newToken = jwtTokenProvider.createToken(user, refreshRate + 1);
				 user.setSecToken(newToken);
				 response.setUser(user);
				 response.setResponseStatus(ResponseCode.SUCCESS);
			} else {
				response.setResponseStatus(ResponseCode.INVALID_TOKEN, transition);
			}
		} else {
			response.setResponseStatus(ResponseCode.DUPRICATED_TOKEN, transition);
		}

		return response;
	}
	
	public GeneralResponse signup(User user, Transition transition) throws Exception {
		
		GeneralResponse response = new GeneralResponse();
		
		user.setStatusId(SystemLoader.statusPerCode.get(SystemStatusEnum.ACTIVE.name()).getId());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if(true) {
			//throw new Exception("testtttttttttttttttttttttttttt");
		}
		
		boolean inserted = authDao.save(user, transition);
		
		if(inserted) {
			response.setResponseStatus(ResponseCode.SUCCESS);
		} else {
			response.setResponseStatus(ResponseCode.NO_DATA_SAVED, transition);
		}
		
		return response;
	}
	
	public GeneralResponse updatePassword(String oldPassword, String newPassword, Transition transition) throws AppException {
		
		GeneralResponse response = new GeneralResponse();

		if(newPassword != null 
				&& oldPassword != null 
				&& transition != null 
				&& transition.getUserId() > 0) {
			
			User user = authDao.findUserById(transition);
			
			if(passwordEncoder.matches(oldPassword, user.getPassword())) {
				boolean inserted = authDao.updatePassword(passwordEncoder.encode(newPassword), transition);
				
				if(inserted) {
					response.setResponseStatus(ResponseCode.SUCCESS);
				} else {
					//response.setResponseStatus(ResponseCode.NOT_EXIST, transition);
				}
			} else {
				response.setResponseStatus(ResponseCode.FORBIDDEN, transition);
			}
			
		} else {
			response.setResponseStatus(ResponseCode.BAD_REQUEST, transition);
		}
		
		return response;
	}

	public GeneralResponse forgetPassword(String email, Transition transition) throws AppException, IOException {
		
		GeneralResponse response = new GeneralResponse();
		
		if(email != null && email != "") {
			
			User user = authDao.findUserByUsername(email, transition);
			
			if(user!= null && user.getId() > 0) {
				
				user.setEmail(email);
				user.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
				user.setTokenExpiryDate(utilHandler.getDateWith(new Date(), validityTime));
				
				boolean updated = authDao.updateValidationToken(user, transition);
				String restPassUrl = utilHandler.getAppInfo().getUserResetPasswordUrl() + "/" + user.getToken();
				
				if(updated) {
					//send Email To User to Confirm
					mailHandler.sendResetPasswordMail(user, restPassUrl, transition);
					response.setResponseStatus(ResponseCode.SUCCESS);
				} else {
					response.setResponseStatus(ResponseCode.GENERAL_FAILURE, transition);
				}
				
			} else {
				//response.setResponseStatus(ResponseCode.NOT_EXIST, transition);
			}
			
		} else {
			response.setResponseStatus(ResponseCode.BAD_REQUEST, transition);
		}
		return response;
	}

	public GeneralResponse resetPassword(String token, String password, Transition transition) throws AppException {
		
		GeneralResponse response = new GeneralResponse();
		
		if(token != null && token != "") {
			
			boolean reset = authDao.restPassword(token, passwordEncoder.encode(password), transition);
			
			if(reset) {
				response.setResponseStatus(ResponseCode.SUCCESS);
			} else {
				response.setResponseStatus(ResponseCode.INVALID_TOKEN, transition);
			}
			
		} else {
			response.setResponseStatus(ResponseCode.BAD_REQUEST, transition);
		}
		return response;
	}
	

}
