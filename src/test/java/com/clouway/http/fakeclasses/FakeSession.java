package com.clouway.http.fakeclasses;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeSession implements HttpSession {
    private Map<String,Object> attributes=new HashMap<String, Object>();

    public long getCreationTime() {
        return 0;
    }

    public String getId() {
        return null;
    }

    public long getLastAccessedTime() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public void setMaxInactiveInterval(int i) {

    }

    public int getMaxInactiveInterval() {
        return 0;
    }

    public HttpSessionContext getSessionContext() {
        return null;
    }

    public Object getAttribute(String s) {
        return attributes.get(s);
    }

    public Object getValue(String s) {
        return null;
    }

    public Enumeration<String> getAttributeNames() {
        return null;
    }

    public String[] getValueNames() {
        return new String[0];
    }

    public void setAttribute(String s, Object o) {
        attributes.put(s,o);
    }

    public void putValue(String s, Object o) {

    }

    public void removeAttribute(String s) {

    }

    public void removeValue(String s) {

    }

    public void invalidate() {

    }

    public boolean isNew() {
        return false;
    }
}
