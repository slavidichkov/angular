package com.clouway.http.fakeclasses;


import javax.servlet.*;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeRequest implements HttpServletRequest {
    private HttpSession session;
    private Map<String, String> parameters = new HashMap<String, String>();
    private Map<String, Object> attribute = new HashMap<String, Object>();
    private String uri;
    private List<Cookie> cookies=new ArrayList<Cookie>(){{
        add(new Cookie("dada","dadadafsafawefg"));
    }};
    private ServletInputStream servletInputStream;

    public FakeRequest(FakeSession session) {
        this.session = session;
    }

    public FakeRequest() {
    }

    public void setServletInputStream(ServletInputStream servletInputStream) throws IOException {
        this.servletInputStream = servletInputStream;
    }

    public ServletInputStream getInputStream() throws IOException {
        return servletInputStream;
    }

    public String getRequestURI() {
        return uri;
    }

    public void setRequestURI(String uri){
        this.uri = uri;
    }

    public void addCookies(Cookie cookie) {
       cookies.add(cookie);
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    public String changeSessionId() {
        return null;
    }

    public void setParameter(String name, String parameter) {
        parameters.put(name, parameter);
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
    }

    public String getParameter(String s) {
        return parameters.get(s);
    }

    public Object getAttribute(String s) {
        return attribute.get(s);
    }

    public void setAttribute(String s, Object o) {
        attribute.put(s, o);
    }

    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        Cookie[] a=new Cookie[cookies.size()];
        cookies.toArray(a);
        return a;
    }

    public long getDateHeader(String s) {
        return 0;
    }

    public String getHeader(String s) {
        return null;
    }

    public Enumeration<String> getHeaders(String s) {
        return null;
    }

    public Enumeration<String> getHeaderNames() {
        return null;
    }

    public int getIntHeader(String s) {
        return 0;
    }

    public String getMethod() {
        return null;
    }

    public String getPathInfo() {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public String getQueryString() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String s) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getServletPath() {
        return null;
    }

    public HttpSession getSession(boolean b) {
        return null;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }


    public void login(String s, String s1) throws ServletException {

    }

    public void logout() throws ServletException {

    }

    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    public Part getPart(String s) throws IOException, ServletException {
        return null;
    }

    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

    public Enumeration<String> getAttributeNames() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    public int getContentLength() {
        return 0;
    }

    public long getContentLengthLong() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public Enumeration<String> getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String s) {
        return new String[0];
    }

    public Map<String, String[]> getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void removeAttribute(String s) {

    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration<Locale> getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    public String getRealPath(String s) {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public ServletContext getServletContext() {
        return null;
    }

    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    public boolean isAsyncStarted() {
        return false;
    }

    public boolean isAsyncSupported() {
        return false;
    }

    public AsyncContext getAsyncContext() {
        return null;
    }

    public DispatcherType getDispatcherType() {
        return null;
    }
}
