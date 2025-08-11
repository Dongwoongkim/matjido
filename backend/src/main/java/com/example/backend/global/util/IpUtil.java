package com.example.backend.global.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpUtil {

    public static String getClientIp(HttpServletRequest request) {
        final String[] CLIENT_IP_HEADER_NAMES = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : CLIENT_IP_HEADER_NAMES) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                log.info("client ip found in header {}: {}", header, ip);
                return ip;
            }
        }

        String ip = request.getRemoteAddr();
        log.info("client ip resolved from request.getRemoteAddr(): {}", ip);
        return ip;
    }
}
