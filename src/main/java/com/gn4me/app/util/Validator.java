package com.gn4me.app.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gn4me.app.entities.enums.Language;


@Service
public class Validator {
	
	@Value("${default.lang}")
	private static String defaultLang;

	
	
}
