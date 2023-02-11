/**
 * DATN_FALL2022, 2022
 * SmtpMailSender.java, BUI_QUANG_HIEU
 */
package com.pro2111.service;

import javax.mail.MessagingException;

/**
 * @author BUI_QUANG_HIEU
 *
 */
public interface SmtpMailSender {

	/**
	 * Gửi mail
	 * 
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 */
	public void sendMail(String to, String subject, String body) throws MessagingException;

}
