package com.gn4me.app.mail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gn4me.app.config.props.AppInfo;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.User;


@Service
public class MailHandler {

	private Logger logger = Logger.getLogger("WiseDataCollectionDebugLogger");

	@Autowired
	AppInfo appInfo;
		
	@Autowired
	private JavaMailSender mailSender;

	
	public void sendMail(MailInfo info) throws MailParseException,
			MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

		messageHelper.setFrom(info.getSender());
		messageHelper.setTo(info.getReceiver().getEmail());
		messageHelper.setSubject(info.getSubject());
		messageHelper.setText(info.getMessage(), true);
		mailSender.send(message);
	}

	public boolean sendResetPasswordMail(User receiver, String resetPath, Transition transition) throws IOException {
		
		MailInfo mailInfo = null;
		MailAction action = null;
		BasicMailInfo basicInfo = new BasicMailInfo();
		
		try {
			
			logger.debug("[" + transition.getId()
					+ "] Going to build Basic Mail Info from User: "+ receiver + " resetPath: " + resetPath);
			
			User user = new User();
			user.setEmail(receiver.getEmail());
			
			// Set Mail Sender, Receiver, wise config
			basicInfo.setSender(appInfo.getEmail()); 
			basicInfo.setReceiver(user);
			basicInfo.setAppInfo(appInfo);
			
			// Set Mail Subject and Title 
			basicInfo.setSubject(appInfo.getName() + " | "+PasswordEmail.RESET_PASSWORD.getValue(transition.getLanguage()));
			basicInfo.setTitle(PasswordEmail.RESET_PASSWORD.getValue(transition.getLanguage()));
			
			// Set Mail Body
			basicInfo.getElements().add(PasswordEmail.START.getValue(transition.getLanguage()));

			basicInfo.getElements().add(PasswordEmail.BODY.getValue(transition.getLanguage()));
			// Set Mail Actions
			action = new MailAction();
			action.setLable(PasswordEmail.RESET_PASSWORD.getValue(transition.getLanguage()));
			action.setLink(resetPath);
			basicInfo.getActions().add(action);
			
			// Set Mail Notes.
			basicInfo.setNote(PasswordEmail.NOTE.getValue(transition.getLanguage()));
			
			logger.debug("[" + transition.getId()
					+ "] Basic Mail Info Built Successfully with  Mail Info: "+ basicInfo);
			
			mailInfo = MailTemplate.getBasicTemplate(basicInfo);
			sendMail(mailInfo);
			
			logger.debug("[" + transition.getId() + "] Mail sent to User with details: " + basicInfo );
			
		} catch (MailParseException | MessagingException | IOException exp) {
			logger.debug("[" + transition.getId() + "] Exception happened " + exp
					+ ", while sending reset password E-mail with details: " + basicInfo);
			logger.error("[" + transition.getId() + "] Exception happened " + exp
					+ ", while sending reset password E-mail with details: " + basicInfo, exp);
			return false;
		}

		return true;
	}
	
}
