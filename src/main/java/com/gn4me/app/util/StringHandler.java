package com.gn4me.app.util;

import java.util.UUID;


import org.springframework.stereotype.Service;

@Service
public class StringHandler {

	public String getSplittedString(String str) {
		
		return str.substring(0, str.indexOf("/")).toUpperCase();
	}
	
	public String getConcatenatedQueryString(String concatenatedString) {

		StringBuilder res = new StringBuilder();
		String[] splitted = concatenatedString.split(",");
		
		for(String str : splitted) {
			res.append("'").append(str).append("',");
		}
		res.deleteCharAt(res.length() - 1);
		return res.toString();
	}

	public String[] getCommaSeparatedString(String concatenatedString) {

		String[] splitted = concatenatedString.split(",");
		return splitted;
	}
	
	public String generateRandomCode() {
		return UUID.randomUUID().toString();
	}
	
	
}
