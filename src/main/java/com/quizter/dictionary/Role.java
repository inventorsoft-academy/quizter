package com.quizter.dictionary;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    STUDENT("Student"), TEACHER("Teacher"), ADMIN("Admin");

    private final String displayValue;

    Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
