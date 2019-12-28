package com.quizter.entity;

import com.quizter.dictionary.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Credentials implements UserDetails {

    String username;

    String password;

    Set<Role> roles;

    Boolean isActive;

    public Credentials(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.roles = Collections.singleton(user.getRole());
        this.isActive = user.getActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

}
