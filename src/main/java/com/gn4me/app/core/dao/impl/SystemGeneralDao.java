package com.gn4me.app.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gn4me.app.core.dao.IGeneralDao;
import com.gn4me.app.entities.Gender;
import com.gn4me.app.entities.Section;
import com.gn4me.app.entities.SectionListingContentType;
import com.gn4me.app.entities.SectionPage;
import com.gn4me.app.entities.SystemConfiguration;
import com.gn4me.app.entities.Status;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.SystemModuleEnum;
import com.gn4me.app.log.LogHelper;

@Repository
public class SystemGeneralDao implements IGeneralDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private LogHelper logHelper;

	@Override
	public List<Status> listSytemStatus(SystemModuleEnum module, Transition transition) throws Exception {

		String query = "";
		Map<String, Object> mapParameters = null;
		List<Status> status = null;

		try {
			
			query = "SELECT * FROM SYSTEM_STATUS WHERE DELETED=0 "+(module == null ? "" : "AND (MODULE = :module OR MODULE = :all) ");
			
			mapParameters = new HashMap<String, Object>();
			if(module != null) {
				mapParameters.put("module", module.name());
				mapParameters.put("all", SystemModuleEnum.ALL.name());
			}

			status = jdbcTemplate.query(query, mapParameters, new RowMapper<Status>() {

				@Override
				public Status mapRow(ResultSet rs, int arg1) throws SQLException {
					Status status = new Status();
					status.setId(rs.getInt("ID"));
					status.setCode(rs.getString("STATUS_CODE"));
					status.setStatusAr(rs.getString("STATUS_AR"));
					status.setStatusEn(rs.getString("STATUS_EN"));
					return status;
				}

			});

		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "find user by email, ", transition);
		}

		return status;
	}
	
	@Override
	public List<Gender> listGenders(Transition transition) throws Exception {

		String query = "";
		List<Gender> genders = null;

		try {

			query = "SELECT * FROM SYSTEM_STATUS WHERE DELETED=0 ";

			genders = jdbcTemplate.query(query, new RowMapper<Gender>() {

				@Override
				public Gender mapRow(ResultSet rs, int arg1) throws SQLException {
					Gender gender = new Gender();
					gender.setId(rs.getInt("ID"));
					gender.setGenderAr(rs.getString("STATUS_AR"));
					gender.setGenderEn(rs.getString("STATUS_EN"));
					return gender;
				}

			});

		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "find user by email, ", transition);
		}

		return genders;
	}
	
	@Override
	public List<SystemConfiguration> listSytemConfigurations(Transition transition) throws Exception {

		String query = "";
		List<SystemConfiguration> configurationsList = null;

		try {		
			query = "SELECT * FROM system_configuration WHERE DELETED=0";
		
			configurationsList = jdbcTemplate.query(query, new RowMapper<SystemConfiguration>() {

				@Override
				public SystemConfiguration mapRow(ResultSet rs, int arg1) throws SQLException {
					SystemConfiguration configuration = new SystemConfiguration();
					configuration.setId(rs.getInt("ID"));
					configuration.setKey(rs.getString("KEY"));
					configuration.setValue(rs.getString("GENERAL_VALUE"));
					configuration.setFrontEndDisplay(rs.getBoolean("FRONTEND_DISPLAY"));
					return configuration;
				}
			});
		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "listSytemConfigurations(), ", transition);
		}
		return configurationsList;
	}

	@Override
	public List<Section> listSections(Transition transition) throws Exception {
		String query = "";
		List<Section> sectionsList = null;
		
		try{
			query=" select section.ID sectionId,section.NAME sectionName,SECTION_CODE,CATEGORY_ID,CONTENT_TYPE_ID,"
				 +" section_listing_content_types.ID listingId,section_listing_content_types.METHOD_NAME,section_listing_content_types.VIEW_NAME,"
				 +" section_page.ID pageId,section_page.NAME pageName,section_page.PAGE_CODE "
				 +" from section , section_listing_content_types, section_page"
				 +" where section.DELETED=0 and section_listing_content_types.DELETED=0 and section_page.DELETED=0"
				 +" and section.SECTION_PAGE_ID=section_page.ID and section.LISTING_TYPE_ID=section_listing_content_types.ID";
		
				sectionsList=jdbcTemplate.query(query, new RowMapper<Section>() {
					@Override
					public Section mapRow(ResultSet rs, int arg1) throws SQLException {
						// TODO Auto-generated method stub
						Section section =new Section();
						section.setId(rs.getInt("sectionId"));
						section.setName(rs.getString("sectionName"));
						section.setSectionCode(rs.getString("SECTION_CODE"));
						section.setCategoryId(rs.getInt("CATEGORY_ID"));
						section.setContentTypeId(rs.getInt("CONTENT_TYPE_ID"));
						
						SectionListingContentType listingType=new SectionListingContentType();
						listingType.setId(rs.getInt("listingId"));
						listingType.setMethodName(rs.getString("METHOD_NAME"));
						listingType.setViewName(rs.getString("VIEW_NAME"));
						
						SectionPage page=new SectionPage();
						page.setId(rs.getInt("pageId"));
						page.setName(rs.getString("pageName"));
						page.setPageCode(rs.getString("PAGE_CODE"));
						
						section.setSectionListingContentType(listingType);
						section.setSectionPage(page);
						return section;
					}				
				});
		}catch (Exception exp) {
			logHelper.logThrownExp(exp, "listSections(), ", transition);
		}
		return sectionsList;
	}
}
