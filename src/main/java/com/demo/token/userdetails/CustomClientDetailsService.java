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

    /**
     * Load a client by the client id. This method must not return null.
     *
     * @param clientId The client id.
     * @return The client details (never null).
     * @throws ClientRegistrationException If the client account is locked, expired, disabled, or invalid for any other reason.
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try {

            User user = userMapper.getUserById(clientId);
            // TODO 这里要去数据库获取密码

            // object -> Map
            Map<String, String> map = getAuthcode(clientId);

            if (map.isEmpty() || !map.containsKey("encryptAuthCode") || !map.containsKey("authStatus")) {
                throw new ClientRegistrationException("the tenant authorization code service returns an exception.");
            }

            if (!"1".equals(map.get("authStatus").toString())) {
                throw new ClientRegistrationException("tenant authorization is not enabled,tenantId=[" + clientId + "]");
            }

            if (Objects.isNull(map.get("encryptAuthCode"))) {
                throw new ClientRegistrationException("no tenant authorization code is configured,tenantId=[" + clientId + "]");
            }

            String clientSecret = map.get("encryptAuthCode").toString();

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

    private Map<String,String> getAuthcode(String clientId) {
        Map<String,String> map = new HashMap<>(8);
        map.put("tenantId","34234234");
        map.put("authCode","d6b0a65511a242069a33e653a58d6b49");
        map.put("encryptAuthCode","WS04YXcckYp3VsWy/GJbTdoQmw+1wD9F3kXnSCJOhwlqu9JXGCLD3g==");
        map.put("encryptAuthCode","F5fhHYbSnL3DsnWLvDyUFU3dF3tZYrUuy+A+oj4kmaTOHC1cpzopaA==");
        map.put("authStatus","1");
        return map;
    }





}
