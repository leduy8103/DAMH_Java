package com.example.DAJava.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "Tài khoản của bạn đã bị vô hiệu hóa.";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "Tài khoản của bạn đã hết hạn.";
        } else if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
            errorMessage = "Tài khoản của bạn đã bị khóa.";
        } else if (exception.getMessage().contains("Bad credentials")) {
            errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng.";
        } else {
            errorMessage = "Đã xảy ra lỗi. Vui lòng thử lại.";
        }

        // Log the exception message for debugging purposes
        logger.error("Login failed: " + exception.getMessage());

        request.getSession().setAttribute("error", errorMessage);
        response.sendRedirect("/login?error=true");
    }
}
