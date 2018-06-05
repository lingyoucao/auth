package com.demo.token.userdetails;


import com.demo.mapper.UserMapper;
import com.demo.token.model.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CustomClientDetailsService implements ClientDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomClientDetailsService.class);

    private static final Set<String> granTypes = new HashSet<>(Arrays.asList("code,client_credentials,refresh_token,implicit,password".split(",")));
    private static final Set<String> scopes = new HashSet<>(Arrays.asList("all".split(",")));
    private static final List<String> roles = Arrays.asList("ROLE_CLIENT".split(","));

    @Value("${oauth2.tokenValiditySeconds}")
    private Integer tokenValiditySeconds;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try {
            User user = userMapper.getUserById(clientId);
            if(user == null) {
                logger.error("用户ID不存在");
                throw new ClientRegistrationException("用户ID不存在");
            }

            /**
             * 获取数据库中的加密密码
             */
            String clientSecret = user.getAuthCode();
            BaseClientDetails details = new BaseClientDetails();
            details.setClientId(clientId);
            details.setClientSecret(clientSecret);
            details.setAuthorizedGrantTypes(granTypes);
            details.setScope(scopes);
            Set<GrantedAuthority> authorities = roles.stream().map(authority ->
                    new SimpleGrantedAuthority(authority)
            ).collect(Collectors.toSet());
            details.setAuthorities(authorities);
            details.setAccessTokenValiditySeconds(tokenValiditySeconds);
            details.setRefreshTokenValiditySeconds(8640000);
            return details;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.debug(e.getMessage(), e);
            if(e.getCause() instanceof ClientRegistrationException) {
                logger.debug(e.getMessage(), e);
            } else {
                logger.error(e.getMessage(), e);
            }
            throw new ClientRegistrationException(e.getMessage());
        }

    }

}
