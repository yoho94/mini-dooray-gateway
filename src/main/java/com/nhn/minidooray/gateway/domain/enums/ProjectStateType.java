package com.nhn.minidooray.gateway.domain.enums;

import com.nhn.minidooray.gateway.exception.NoSuchException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProjectStateType {
    DORMANT("01", "활성", true),
    ACTIVE("02", "휴면", false),
    END("03", "종료", false);

    private final String code;
    private final String name;
    private final boolean isActive;

    ProjectStateType(String code, String name, boolean isActive) {
        this.code = code;
        this.name = name;
        this.isActive = isActive;
    }

    public static ProjectStateType valueOfCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new NoSuchException("ProjectStateType"));
    }
}
