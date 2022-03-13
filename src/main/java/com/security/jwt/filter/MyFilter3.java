package com.security.jwt.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        System.out.println("filter3");
        chain.doFilter(request, response);
    }

}
