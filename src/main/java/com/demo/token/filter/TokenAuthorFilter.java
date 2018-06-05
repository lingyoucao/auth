package com.demo.token.filter;


import com.alibaba.fastjson.JSON;
import com.demo.token.model.ResultInfo;
import com.demo.token.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;


@Component
public class TokenAuthorFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthorFilter.class);
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse rep = (HttpServletResponse) response;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            String token = req.getHeader("token");
            if (StringUtils.isBlank(token)) {
                String authorizationHeader = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);
                if (!StringUtils.isBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                    token = authorizationHeader.substring("Bearer".length()).trim();
                }
            }
            ResultInfo resultInfo = new ResultInfo();
            boolean isFilter = false;

            if (null == token || token.isEmpty()) {
                resultInfo.setError(HttpStatus.UNAUTHORIZED);
                resultInfo.setStatus(HttpStatus.UNAUTHORIZED.value());
                resultInfo.setMessage("请求头部中未找到token");
            } else {
                Map result = TokenUtil.volidateToken(token);
                if (!result.containsKey("exception")) {
                    resultInfo.setStatus(HttpStatus.OK.value());
                    resultInfo.setMessage("用户校验通过!");
                    isFilter = true;
                } else {
                    String message = result.get("message").toString();
                    logger.warn(message);
                    resultInfo.setError(HttpStatus.UNAUTHORIZED);
                    resultInfo.setStatus(HttpStatus.UNAUTHORIZED.value());
                    resultInfo.setMessage(message);
                }
            }
            // 验证失败
            if (resultInfo.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(response.getOutputStream(),
                            "UTF-8");
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(resultInfo);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (UnsupportedEncodingException e) {
                    logger.error("过滤器返回信息失败:" + e.getMessage(), e);
                } catch (IOException e) {
                    logger.error("过滤器返回信息失败:" + e.getMessage(), e);
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
                return;
            }

            if (isFilter) {
                logger.info("token filter过滤ok!");
                chain.doFilter(request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}