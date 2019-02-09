package com.gn4me.app.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * The Class DaoUtil.
 */
public class SqlQueriesUtil {
	/** Logger to trace info */
	private static Logger logger = Logger.getLogger("AppDebugLogger");
	
	/**
	 * @return the setShowSql
	 */
	public static boolean isSetShowSql() {
		String variable = System.getenv("setShowSql");
		if ("true".equals(variable)) {
			return true;
		}
		return false;
	}

	public static boolean isSetReplaceSqlParameters() {
		String variable = System.getenv("setReplaceSqlParameters");
		if ("true".equals(variable)) {
			return true;
		}
		return false;
	}

	private static String formatParamValue(String name, Object theValue) {
		if (theValue == null) {
			return ("NULL");
		} else if (Date.class.isInstance(theValue)) {
			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			final String fecha = "'".concat(df.format(theValue)).concat("'");
			return fecha;
		} else if (Timestamp.class.isInstance(theValue)) {
			final DateFormat df = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
			final String fechaHora = "'".concat(df.format(theValue)).concat("'");
			return fechaHora;
		} else if (ArrayList.class.isInstance(theValue)) {
			final String lista = theValue.toString();
			final String nuevaLista = lista.replaceAll("\\[", "").replaceAll("\\]", "");
			return nuevaLista;
		} else {
			logger.debug("Parameter type not implemented=".concat(name).concat(" TYPE=")
					.concat(theValue.getClass().toString()));
		}
		return (theValue.toString());
	}

	/**
	 * Display the query with parameters if setShowSql=true
	 * 
	 * @param sql
	 * @param parameter
	 */
	public static void debugSQL(String sql, SqlParameterSource parameter) {
		MapSqlParameterSource paramMap = null;
		BeanPropertySqlParameterSource paramBean = null;
		EmptySqlParameterSource paramEmpty = null;
		if (isSetShowSql()) {
			if (parameter != null && isSetReplaceSqlParameters()) {
				try {
					if (MapSqlParameterSource.class.isAssignableFrom(parameter.getClass())) {
						paramMap = MapSqlParameterSource.class.cast(parameter);
						debugSQL(sql, paramMap);
					} else if (BeanPropertySqlParameterSource.class.isAssignableFrom(parameter.getClass())) {
						paramBean = BeanPropertySqlParameterSource.class.cast(parameter);
						debugSQL(sql, paramBean);
					} else if (EmptySqlParameterSource.class.isAssignableFrom(parameter.getClass())) {
						debugSQL(sql, paramEmpty);
					} else {
						logger.debug("The parameter don't have a indentified type");
					}
				} catch (ClassCastException e) {
					logger.error("Casting error with the parameter: ?" + parameter);
					paramMap = null;
				}
			} else {
				debugSQL(sql);
			}
		}
	}

	/**
	 * Display the query with parameters if setShowSql=true
	 * 
	 * @param sql
	 */
	public static void debugSQL(String sql) {
		if (isSetShowSql() && sql != null) {
			logger.debug("SQL_ORIGINAL: \t".concat(sql));
		}
	}

	/**
	 * Display the query with parameters if setShowSql=true
	 * 
	 * @param sql
	 * @param parameter
	 */
	private static void debugSQL(String sql, BeanPropertySqlParameterSource parameter) {
		String newSql = sql;
		StringBuilder paramResult = new StringBuilder();
		String[] paramsArray = parameter.getReadablePropertyNames();
		// Mientras haya parámetros en la lista o en la SQL por reemplazar
		for (String paramName : paramsArray) {
			final String paramValue = parameter.getValue(paramName).toString();
			newSql = newSql.replaceAll(":".concat(paramName), paramValue);
			paramResult.append(paramName).append("=").append(paramValue).append(", ");
		}
//        LOG.debug("SQL_ORIGINAL:".concat(sql));
		logger.debug("SQL_BEAN_REPLACED: \t".concat(newSql));
//        LOG.debug("PARAMS_BEAN:".concat(paramResult.toString()));
	}

	/**
	 * Display the query with parameters if setShowSql=true
	 * 
	 * @param sql
	 * @param parameter
	 */
	private static void debugSQL(String sql, MapSqlParameterSource parameter) {
		String newSql = sql;
		Iterator<Entry<String, Object>> parametros = parameter.getValues().entrySet().iterator();
		while (parametros.hasNext() && newSql.lastIndexOf(":") != -1) {
			final Entry<String, Object> nuevoParam = parametros.next();
			final String paramName = nuevoParam.getKey();
			final Object valor = nuevoParam.getValue();
			final String paramValue = formatParamValue(paramName, valor);
			newSql = newSql.replaceAll(":".concat(paramName).concat(" "), paramValue.concat(" "));
			newSql = newSql.replaceAll(":".concat(paramName).concat(","), paramValue.concat(","));
			newSql = newSql.replaceAll(":".concat(paramName).concat("\\)"), paramValue.concat(")"));
		}
//        LOG.debug("SQL_ORIGINAL:".concat(sql));
		logger.debug("SQL_MAP_REPLACED: \t".concat(newSql));
//        LOG.debug("PARAMS_MAP:".concat(Arrays.toString(parameter.getValues().entrySet().toArray())));
	}
	

	/**
	 * Display the query with parameters if setShowSql=true
	 * 
	 * @param sql
	 * @param parameter
	 */
	private static void debugSQL(String sql, EmptySqlParameterSource parameter) {
		if (isSetShowSql()) {
			logger.debug("SQL_NO_PARAMETER: \t".concat(sql));
		}
	}

	/**
	 * Display the query with parameters if setShowSql=true
	 * 
	 * @param sql
	 * @param paramMap
	 */
	public static void debugSQL(String sql, Map<String, ?> paramMap) {
		if (isSetShowSql()) {
			logger.debug("SQL_ORIGINAL: \t".concat(sql));
			if (paramMap != null && !paramMap.isEmpty()) {
				logger.debug("PARAMS: \t".concat(Arrays.toString(paramMap.entrySet().toArray())));
			}
		}
	}

	/**
	 * Display the query with parameters if setShowSql=true
	 * 
	 * @param sql
	 */
	public static void debugSQL(String[] listaSql) {
		for (int i = 0; i < listaSql.length; i++) {
			debugSQL(listaSql[i]);
		}
	}

	public static void debugSQL(String sql, Map<String, ?>[] batchValues) {
		if (isSetShowSql()) {
			logger.debug("SQL_ORIGINAL: \t".concat(sql));
			if (batchValues != null && !(batchValues.length > 0)) {
				logger.debug("PARAMS: \t".concat(batchValues.toString()));
			}
		}
	}

	public static void debugSQL(String sql, SqlParameterSource[] batchArgs) {
		// TODO Auto-generated method stub
	}
}
