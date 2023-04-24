/**
 * <li>文件名：PageOrder.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月14日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.repository.entity.page;

import org.springframework.data.domain.Sort.Order;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 *
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class PageOrder implements Serializable {

    /**
     * serialVersionUID: TODO
     */
    private static final long serialVersionUID = -1651422434496917649L;

    /**
     * 排序字段属性
     */
    private String property;

    /**
     * ASC、DESC
     */
    private String direction;

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property
     *            the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction
     *            the direction to set
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     *
     * @Title getOrder
     * @Description 获取JPA需要的排序
     * @return
     * @return Order
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public Order getOrder() {
        Assert.notNull(this.property, "排序属性 must not be null!");

        if (StringUtils.isEmpty(this.direction)) { // 默认为升序
            return Order.asc(this.property);
        } else if ("DESC".equals(this.direction.toUpperCase())) {
            return Order.desc(this.property);
        } else {
            return Order.asc(this.property);
        }
    }

}
