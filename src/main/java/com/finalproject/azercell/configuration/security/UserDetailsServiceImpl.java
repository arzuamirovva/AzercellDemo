package com.finalproject.azercell.configuration.security;


import com.finalproject.azercell.entity.NumberEntity;
import com.finalproject.azercell.entity.UserEntity;
import com.finalproject.azercell.enums.RoleEnum;
import com.finalproject.azercell.repository.NumberRepository;
import com.finalproject.azercell.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    //    private final UserRepository repository;
    private final NumberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
        NumberEntity client = repository.findByNumber(number).orElseThrow();
        List<String> roles = new ArrayList<>();
        Set<RoleEnum> authorities = Set.of(client.getUser().getRole());
        for (RoleEnum authority : authorities) {
            roles.add(authority.name());
        }
        UserDetails userDetails;
        userDetails = User.builder()
                .username(client.getNumber())
                .password(client.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
        return userDetails;
    }
}