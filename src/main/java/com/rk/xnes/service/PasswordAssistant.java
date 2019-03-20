package com.rk.xnes.service;

public interface PasswordAssistant {

	void passwordToMail(String usermail, String newPsd, String neckname, String oldPsd);
	
	void passwordToMailWithInlineResource(String usermail, String newPsd, String neckname, String oldPsd);
}
