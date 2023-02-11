package com.pro2111.utils;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptUtil {

	/**
	 * Mã hóa password
	 * 
	 * @param origin
	 * @return
	 */
	public static String encrypt(String origin) {
		return BCrypt.hashpw(origin, BCrypt.gensalt());
	}

	/**
	 * Kiểm tra password có hợp lệ hay không
	 * 
	 * @param origin
	 * @param encrypted
	 * @return
	 */
	public static boolean check(String origin, String encrypted) {
		return BCrypt.checkpw(origin, encrypted);
	}
}
