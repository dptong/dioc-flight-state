package com.flywin.msg.dto;

import lombok.Getter;

/**
 * 收信人类型
 *
 * @author xg-ran
 * @date 2022-01-11 10:55:30
 * @since 1.0.0
 */
public enum RecipientType {

    /**
     * 用户id
     */
    USER_ID("u"),

    /**
     * 机构id
     */
    ORG_ID("o"),

    /**
     * 运代公司id
     */
    AGENT_TRANSPORT_COMPANY_ID("a"),

    /**
     * 角色系统编码
     */
    ROLE_CODE("r"),

    /**
     * 手机号
     */
    PHONE("p"),

    /**
     * 邮箱地址
     */
    EMAIL_ADDRESSES("e");

    @Getter
    private String code;

    RecipientType(String code) {
        this.code = code;
    }
}
