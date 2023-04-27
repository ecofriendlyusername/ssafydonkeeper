package com.ssafy.moneykeeperbackend.util;

import javax.servlet.http.Cookie;

public class CookieUtil {

	public Cookie createCookie(String name, String value, int maxAge, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		return cookie;
	}
}
