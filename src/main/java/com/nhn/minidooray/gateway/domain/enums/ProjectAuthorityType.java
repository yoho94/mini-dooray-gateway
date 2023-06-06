package com.nhn.minidooray.gateway.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

@Getter
public enum ProjectAuthorityType {
    ADMIN("01", "관리자") {
        @Override
        public Map<PermissionType, Boolean> getProjectAuthority() {
            return Map.of(
                    PermissionType.READ, true,
                    PermissionType.WRITE, true,
                    PermissionType.MODIFY, true,
                    PermissionType.DELETE, true
            );
        }

        @Override
        public Map<PermissionType, Boolean> getAccountAuthority() {
            return Map.of(
                    PermissionType.LIST, true,
                    PermissionType.READ, true,
                    PermissionType.WRITE, true,
                    PermissionType.MODIFY, true,
                    PermissionType.DELETE, true
            );
        }

        @Override
        public Map<PermissionType, Boolean> getTaskAuthority() {
            return Map.of(
                    PermissionType.LIST, true,
                    PermissionType.READ, true,
                    PermissionType.WRITE, true,
                    PermissionType.MODIFY, true,
                    PermissionType.DELETE, true
            );
        }

        @Override
        public Map<PermissionType, Boolean> getCommentAuthority() {
            return Map.of(
                    PermissionType.READ, true,
                    PermissionType.WRITE, true,
                    PermissionType.MODIFY, true,
                    PermissionType.DELETE, true
            );
        }
    },
    MEMBER("02", "멤버") {
        @Override
        public Map<PermissionType, Boolean> getProjectAuthority() {
            return Map.of(
                    PermissionType.READ, true,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getAccountAuthority() {
            return Map.of(
                    PermissionType.LIST, true,
                    PermissionType.READ, true,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getTaskAuthority() {
            return Map.of(
                    PermissionType.LIST, true,
                    PermissionType.READ, true,
                    PermissionType.WRITE, true,
                    PermissionType.MODIFY, true,
                    PermissionType.DELETE, true
            );
        }

        @Override
        public Map<PermissionType, Boolean> getCommentAuthority() {
            return Map.of(
                    PermissionType.READ, true,
                    PermissionType.WRITE, true,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }
    },
    GUEST("03", "손님") {
        @Override
        public Map<PermissionType, Boolean> getProjectAuthority() {
            return Map.of(
                    PermissionType.READ, true,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getAccountAuthority() {
            return Map.of(
                    PermissionType.LIST, true,
                    PermissionType.READ, false,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getTaskAuthority() {
            return Map.of(
                    PermissionType.LIST, true,
                    PermissionType.READ, true,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getCommentAuthority() {
            return Map.of(
                    PermissionType.READ, false,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }
    },
    NONE("04", "없음") {
        @Override
        public Map<PermissionType, Boolean> getProjectAuthority() {
            return Map.of(
                    PermissionType.READ, false,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getAccountAuthority() {
            return Map.of(
                    PermissionType.LIST, false,
                    PermissionType.READ, false,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getTaskAuthority() {
            return Map.of(
                    PermissionType.LIST, false,
                    PermissionType.READ, false,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }

        @Override
        public Map<PermissionType, Boolean> getCommentAuthority() {
            return Map.of(
                    PermissionType.READ, false,
                    PermissionType.WRITE, false,
                    PermissionType.MODIFY, false,
                    PermissionType.DELETE, false
            );
        }
    };

    private final String code;
    private final String name;

    ProjectAuthorityType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ProjectAuthorityType valueOfCode(String code) {
        return Arrays.stream(values())
                .filter(projectAuthorityType -> projectAuthorityType.getCode().equals(code))
                .findAny()
                .orElseThrow();
    }

    public enum PermissionType {
        LIST, READ, WRITE, MODIFY, DELETE
    }

    public abstract Map<PermissionType, Boolean> getProjectAuthority();

    public abstract Map<PermissionType, Boolean> getTaskAuthority();

    public abstract Map<PermissionType, Boolean> getAccountAuthority();

    public abstract Map<PermissionType, Boolean> getCommentAuthority();
}
