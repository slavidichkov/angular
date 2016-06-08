package com.clouway.http.authorization;

import com.clouway.core.SidGatherer;

import javax.servlet.http.Cookie;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class CookieSidGatherer implements SidGatherer {
    private final Cookie[] cookies;

    public CookieSidGatherer(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public String getSid() {
        String sid="";
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if ("sid".equals(cookie.getName())) {
                sid = cookie.getValue();
            }
        }
        return sid;
    }
}
