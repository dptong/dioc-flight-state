/**
 * <li>文件名：UrlPattern.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2019年1月23日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class UrlPattern implements Serializable {

    /**
     * 类型：long
     */
    private static final long serialVersionUID = 2639638123414221474L;

    /**
     * 路径匹配规则
     */
    private String urlPattern;

    /**
     * 角色代码列表
     */
    private List<String> roleCodes;

    /**
     * @return the urlPattern
     */
    public String getUrlPattern() {
        return urlPattern;
    }

    /**
     * @param urlPattern
     *            the urlPattern to set
     */
    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    /**
     * @return the roleCodes
     */
    public List<String> getRoleCodes() {
        return roleCodes;
    }

    /**
     * @param roleCodes
     *            the roleCodes to set
     */
    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    /**
     *
     * @Title addRoleCode
     * @Description TODO
     * @param rolCode
     *            角色编码
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public void addRoleCode(String rolCode) {
        if (this.roleCodes == null) {
            this.roleCodes = new ArrayList<String>();
        }

        this.roleCodes.add(rolCode);
    }

}
