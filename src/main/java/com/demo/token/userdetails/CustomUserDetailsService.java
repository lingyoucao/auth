/*
package com.demo.token.userdetails;


import com.demo.mapper.UserMapper;
import com.demo.token.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserById(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User[username=%s] not found", username));
        }
        if (CollectionUtils.isEmpty(user.getRoles())) {
            return null;
        }
        Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map(authority ->
                new SimpleGrantedAuthority(authority)
        ).collect(Collectors.toSet());
        return new CustomUserDetails(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
*/
