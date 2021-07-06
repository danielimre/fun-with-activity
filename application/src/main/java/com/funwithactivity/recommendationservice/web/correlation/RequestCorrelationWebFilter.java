package com.funwithactivity.recommendationservice.web.correlation;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;

public final class RequestCorrelationWebFilter extends OncePerRequestFilter implements OrderedFilter {

    @Override
    public int getOrder() {
        return OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER + 1;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        try {
            MDC.put("request-id", getRequestId(request));
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String getRequestId(HttpServletRequest request) {
        return getHeader(request, "request-id")
            .orElseGet(() -> UUID.randomUUID().toString());
    }

    private Optional<String> getHeader(HttpServletRequest request, String header) {
        return Optional.ofNullable(request.getHeader(header));
    }
}
