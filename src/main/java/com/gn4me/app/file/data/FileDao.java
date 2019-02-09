package com.gn4me.app.file.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gn4me.app.entities.Transition;
import com.gn4me.app.file.FileUtil;
import com.gn4me.app.file.entities.AppFile;
import com.gn4me.app.file.entities.FileModule;
import com.gn4me.app.log.LogHelper;
import com.gn4me.app.util.AppException;

@Repository
public class FileDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private LogHelper logHelper;
	
	@Autowired
	private FileUtil fileUtil;

	
	public List<FileModule> listFileModules(Transition transition) throws Exception {

		String query = "";
		List<FileModule> modules = null;

		try {

			query = "SELECT FILE_MODULE.* FROM FILE_MODULE WHERE DELETED=0;";

			modules = jdbcTemplate.query(query, new RowMapper<FileModule>() {

				@Override
				public FileModule mapRow(ResultSet rs, int arg1) throws SQLException {
					FileModule fileModule = new FileModule();
					fileModule.setId(rs.getInt("ID"));
					fileModule.setModule(rs.getString("MODULE_CODE"));
					try {
						fileModule.setSizes(fileUtil.getFileSizes(rs.getString("AVAILABLE_SIZES"), transition));
						fileModule.setCustomSizes(fileUtil.getCustomFileSizes(rs.getString("CUSTOM_SIZE"), transition));
						fileModule.setDefaultSize(fileUtil.getDefaultFileSize(rs.getString("DEFAULT_SIZE"), transition));
					} catch (Exception exp) {
						logHelper.logExp(exp, "Format Image sizes", transition);
					}
					
					fileModule.setCompressLevel(rs.getInt("COMPRESSION_LEVEL"));
					fileModule.setMaxSize(rs.getInt("MAX_SIZE"));
					return fileModule;
				}

			});

		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "list File Modules, ", transition);
		}

		return modules;
	}
	
	
	public AppFile save(AppFile file, Transition transition) throws AppException {
		
		StringBuilder query = new StringBuilder();

		String tableName = "file_" + file.getFileModule().getModule().toLowerCase(); 
		try {
			query.append("INSERT INTO ")
				 .append(tableName)
				 .append("( FILE_MODULE_ID, GENERATED_CODE, NAME, TAGS, TYPE ) ")
				 .append("VALUES( :fileModuleId, :generatedCode, :name, :tags, :typeStr ) ");
			
			KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(file);

			int insertedRows = jdbcTemplate.update(query.toString(), namedParameters, generatedKeyHolder);
			
			if (insertedRows > 0) {
				file.setId(generatedKeyHolder.getKey().intValue());
			}
		} catch (Exception exp) {
			logHelper.logThrownExp(exp, "Save File, " + file, transition);
		}

		return file;
	}

}
