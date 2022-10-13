package com.infosetgroup.flexevent.security;

public interface SecurityParams {
    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String SECRET = "herve@gmail.com";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION = 365 * 24 * 3600 * 1000;
    public static final String EXPIRATION_DATE = "01/01/2025";
}
