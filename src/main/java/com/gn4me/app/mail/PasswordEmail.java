package com.gn4me.app.mail;

import com.gn4me.app.entities.enums.Language;

public enum PasswordEmail {
	RESET_PASSWORD("Reset Password.", "إستعادة كلمة المرور"),
	START("Looks like you lost your password?", "يبدو أنك نسية كلمة المرور؟"),
	BODY("We Are here to help. Click on the button below to change your password.", "نحن هنا لمساعدتك!! فقط اضغط على إستعادة كلمة المرور؟"),
	NOTE("You receive this email because you or someone initiated a password recovery operation on your Wise account.", "لقد تلقيت هذا ايميل لانك طلبت إستعادة كلمة المرور الخاصة بك من حسابك");
	
	private String valueEn;
	private String valueAr;

	private PasswordEmail(String valueEn, String valueAr) {
		this.valueEn = valueEn;
		this.valueAr = valueAr;
	}

	public String getValue(Language lang) {
		if(lang.equals(Language.EN)) {
			return valueEn;
		} else {
			return valueAr;
		}
	}
}
