package com.flywin.enums;

/**
 * 正则校验枚举
 */
public enum CheckOutEnum {
    /**
     * 手机号
     */
    CHECK_OUT_CELL_PHONE("手机号", "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$"),
    /**
     * 邮箱
     */
    CHECK_OUT_EMAIL("邮箱", "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
    /**
     * 电话号码
     * XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX
     */
    CHECK_OUT_PHONE("电话号码", "^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$"),
    /**
     * 国内电话号码
     * 0511-4405222、021-87888822
     */
    CHECK_OUT_INLAND_PHONE("国内电话号码", "\\d{3}-\\d{8}|\\d{4}-\\d{7}"),
    /**
     * 联系号码
     * 支持手机号码，3-4位区号，6-8位直播号码，1－4位分机号
     */
    CHECK_OUT_CONTACT_NUMBER("联系电话",
            "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)"),
    /**
     * 汉字校验
     */
    CHECK_OUT_CHINESE("中文", "^[\\u4e00-\\u9fa5]{0,}$");


    /**
     * @param name  名
     * @param value 值
     */
    CheckOutEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * Gets the value of name.
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of value.
     *
     * @return the value of value
     */
    public String getValue() {
        return value;
    }
}
