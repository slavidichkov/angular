package com.clouway.http.fakeclasses;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class FakeResponse implements HttpServletResponse{
    private ByteArrayOutputStream outputStream;
    private String redirectUrl;
    private ServletOutputStream servletOutputStream;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int i) {
        status = i;
    }

    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void sendRedirect(String s) throws IOException {
        this.redirectUrl = s;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(outputStream);
    }
    public void setServletOutputStream(ServletOutputStream servletOutputStream){
        this.servletOutputStream = servletOutputStream;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    public void addCookie(Cookie cookie) {

    }

    public boolean containsHeader(String s) {
        return false;
    }

    public String encodeURL(String s) {
        return null;
    }

    public String encodeRedirectURL(String s) {
        return null;
    }

    public String encodeUrl(String s) {
        return null;
    }

    public String encodeRedirectUrl(String s) {
        return null;
    }

    public void sendError(int i, String s) throws IOException {

    }

    public void sendError(int i) throws IOException {

    }

    public void setDateHeader(String s, long l) {

    }

    public void addDateHeader(String s, long l) {

    }

    public void setHeader(String s, String s1) {

    }

    public void addHeader(String s, String s1) {

    }

    public void setIntHeader(String s, int i) {

    }

    public void addIntHeader(String s, int i) {

    }

    public void setStatus(int i, String s) {

    }

    public String getHeader(String s) {
        return null;
    }

    public Collection<String> getHeaders(String s) {
        return null;
    }

    public Collection<String> getHeaderNames() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public String getContentType() {
        return null;
    }

    public void setCharacterEncoding(String s) {

    }

    public void setContentLength(int i) {

    }

    public void setContentLengthLong(long len) {

    }

    public void setContentType(String s) {

    }

    public void setBufferSize(int i) {

    }

    public int getBufferSize() {
        return 0;
    }

    public void flushBuffer() throws IOException {

    }

    public void resetBuffer() {

    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {

    }

    public void setLocale(Locale locale) {

    }

    public Locale getLocale() {
        return null;
    }

}
