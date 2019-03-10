package com.gn4me.app.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gn4me.app.core.dao.AuthDao;
import com.gn4me.app.entities.Privilege;
import com.gn4me.app.entities.Role;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.User;
import com.gn4me.app.entities.enums.ResponseCode;
import com.gn4me.app.entities.response.ResponseStatus;
import com.gn4me.app.log.LogHelper;
import com.gn4me.app.util.AppException;
import com.gn4me.app.util.SqlQueriesUtil;
import com.gn4me.app.util.SystemLoader;

@Repository
public class SystemAuthDao implements AuthDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private LogHelper logHelper;

	
	@Override
	public boolean save(User user, Transition transition) throws AppException {
		
		String query = "";
		boolean inserted = false;
		
		try {
			query = "INSERT INTO user ( user.EMAIL, user.FIRST_NAME, user.LAST_NAME, user.IMAGE, user.USER_PASSWORD, user.STATUS_ID, user.AUTO_CREATED ) "
				  + "VALUES( :email, :firstName, :lastName, :image, :password, :statusId, :autoCreated )";
			
			KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
			
			int insertedRows = jdbcTemplate.update(query, namedParameters,generatedKeyHolder);
			
			if (insertedRows > 0) {
				user.setId(generatedKeyHolder.getKey().intValue());
				inserted = true;
			}
		} catch(DuplicateKeyException exp) {
			throw new AppException(new ResponseStatus(ResponseCode.ALREADY_EXIST), exp, transition);
		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "Save User, " + user, transition);
		}

		return inserted;
	}


	@Override
	public User findUserByUsername(String username, Transition transition) throws AppException {
		
		String query = "";
		Map<String, Object> mapParameters = null;
		User user =  null;
		
		try {
			
			query = " SELECT USER.* FROM USER WHERE USER.EMAIL= :username and USER.deleted=0";
			
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("username", username);

			user =  (User) jdbcTemplate.queryForObject(query, mapParameters, new UserMapper());
			
			if(user != null) {
				user.setRoles(getUserRoles(user.getId(), transition));
				user.setStatus(SystemLoader.statusPerId.get(user.getStatusId()));
			}
			
		} catch(EmptyResultDataAccessException exp) {
			
		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "find user by username, " + username, transition);
		}
		return user;
	}
	
	@Override
	public User findUserById(Transition transition) throws AppException {
		
		String query = "";
		Map<String, Object> mapParameters = null;
		User user =  null;
		
		try {
			
			query = " SELECT USER.* FROM USER WHERE USER.ID= :userId and USER.deleted=0";
			
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("userId", transition.getUserId());

			user =  (User) jdbcTemplate.queryForObject(query, mapParameters, new UserMapper());
			
			if(user != null) {
				user.setRoles(getUserRoles(user.getId(), transition));
				user.setStatus(SystemLoader.statusPerId.get(user.getStatusId()));
			}
			
		} catch (Exception exp) {
			exp.printStackTrace();
			SqlQueriesUtil.debugSQL(query, mapParameters);
			logHelper.logThrownExp(exp, "find user by userId ", transition);
		}
		return user;
	}
	
	public List<Role> getUserRoles(int userId, Transition transition) throws AppException {
		
		String query = "";
		Map<String, Object> mapParameters = null;
		List<Role> roles =  null;
		
		try {
			
			query = " SELECT role.*, privilege.* FROM system_role role" + 
					" JOIN user_role on role.ID = user_role.ROLE_ID and user_role.user_id = :userId and user_role.deleted=0" + 
					" LEFT JOIN system_role_privilege role_privilege ON (role.ID = role_privilege.role_id) and role_privilege.deleted =0" + 
					" LEFT JOIN system_privilege privilege on(role_privilege.PRIVILEGE_ID = privilege.ID) and privilege.deleted=0" + 
					" WHERE role.deleted = 0 ";
			
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("userId", userId);
						
			roles =  jdbcTemplate.query(query, mapParameters, new RoleMapper());
			
		} catch (Exception exp) {
			
			logHelper.logThrownExp(exp, "list user roles with userId, " + userId, transition);
		}
		return roles;
	}
	
	@Override
	public boolean updatePassword(String newPassword, Transition transition) throws AppException {
		
		String query = "";
		Map<String, Object> mapParameters = null;
		boolean updated = false;
		
		try {
			
			query = " UPDATE USER SET USER_PASSWORD= :password WHERE USER.ID= :userId AND DELETED=0";
			
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("password", newPassword);
			mapParameters.put("userId", transition.getUserId());
						
			int updatedRows =  jdbcTemplate.update(query, mapParameters);
			
			if(updatedRows > 0) {
				updated = true;
			}
		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "Update User password. ", transition);
		}
		return updated;
	}
	

	@Override
	public boolean updateValidationToken(User user, Transition transition) throws AppException {
		
		String query = "";
		Map<String, Object> mapParameters = null;
		boolean updated = false;
		
		try {
			
			query = " UPDATE USER SET USER.VALIDATION_TOKEN= :token, USER.TOKEN_EXPIRY_DATE= :expiryDate WHERE USER.EMAIL= :email AND DELETED=0";
			System.out.println("UUID.randomUUID(): " + UUID.randomUUID());
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("email", user.getEmail());
			mapParameters.put("token", user.getToken());
			mapParameters.put("expiryDate", user.getTokenExpiryDate());
						
			int updatedRows =  jdbcTemplate.update(query, mapParameters);
			
			if(updatedRows > 0) {
				updated = true;
			}
		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "Upddate Validation Token. ", transition);
		}
		return updated;
	}
	
	@Override
	public boolean restPassword(String token, String password, Transition transition) throws AppException {
		
		String query = "";
		Map<String, Object> mapParameters = null;
		boolean updated = false;
		
		try {
			
			query = " UPDATE USER SET USER_PASSWORD= :password, VALIDATION_TOKEN= :tokenVal WHERE VALIDATION_TOKEN= :token AND USER.TOKEN_EXPIRY_DATE < CURRENT_TIMESTAMP AND USER.DELETED=0";
			
			mapParameters = new HashMap<String, Object>();
			mapParameters.put("password", password);
			mapParameters.put("tokenVal", null);
			mapParameters.put("expiryDate", null);
			mapParameters.put("token", token);
						
			int updatedRows =  jdbcTemplate.update(query, mapParameters);
			
			if(updatedRows > 0) {
				updated = true;
			}
		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "Update User password. ", transition);
		}
		return updated;
	}
	
	
	//Get user details
	class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User();
			
			user.setId(rs.getInt("USER.ID"));
			user.setFirstName(rs.getString("USER.FIRST_NAME"));
			user.setLastName(rs.getString("USER.LAST_NAME"));
			user.setEmail(rs.getString("USER.EMAIL"));
			user.setUsername(rs.getString("USER.EMAIL"));
			user.setPassword(rs.getString("USER.USER_PASSWORD"));
			user.setStatusId(rs.getInt("USER.STATUS_ID"));
			user.setTrusted(rs.getBoolean("USER.TRUSTED"));
			user.setImage(rs.getString("USER.image"));
			user.setAutoCreated(rs.getInt("USER.AUTO_CREATED")  == 1 ? true : false );
			user.setTrusted(rs.getInt("USER.TRUSTED")  == 1 ? true : false );
			user.setTokenExpiryDate(rs.getTimestamp("USER.TOKEN_EXPIRY_DATE"));
			user.setToken(rs.getString("VALIDATION_TOKEN"));
			user.setInsertDate(rs.getTimestamp("USER.INSERT_DATE"));
			
			return user;
		}
	}

	//List specific user roles
	class RoleMapper implements ResultSetExtractor<List<Role>> {
		@Override
		public List<Role> extractData(ResultSet rs) throws SQLException, DataAccessException {
			Map<Integer, Role> map = new HashMap<Integer, Role>();
			Role role = null;
			Privilege privilege = null;
			while (rs.next()) {
				
				int roleId = rs.getInt("role.ID");
				int privilegeId = rs.getInt("privilege.ID");
				
				if(map.get(roleId) == null) {
					role = new Role(roleId);
					role.setRoleAr(rs.getString("role.ROLE_AR"));
					role.setRoleEn(rs.getString("role.ROLE_EN"));
					role.setCode(rs.getString("role.ROLE_CODE"));
					role.setInsertDate(rs.getTimestamp("role.INSERT_DATE"));
					role.setPrivileges(new ArrayList<>());
					map.put(roleId, role);
				}
				
				if(privilegeId > 0) {
					privilege = new Privilege();
					privilege.setId(rs.getInt("privilege.ID"));
					privilege.setPrivilegeAr(rs.getString("privilege.PRIVILEGE_AR"));
					privilege.setPrivilegeEn(rs.getString("privilege.PRIVILEGE_EN"));
					privilege.setCode(rs.getString("privilege.PRIV_CODE"));
					privilege.setInsertDate(rs.getTimestamp("privilege.INSERT_DATE"));
					role.getPrivileges().add(privilege);
				}
			}
			return new ArrayList<Role>(map.values());
		}
	}


	
}
