/**
 * <li>文件名：PageSearch.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年11月14日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.repository.entity.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Description: TODO
 * @param <T>
 *            实体对象泛型
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class PageSearch<T> implements Serializable {
    public static final int PAGE_SIZE = 20;

    /**
     * 类型：long
     */
    private static final long serialVersionUID = -7355745257284399104L;

    /**
     * 页数
     */
    private int pageNumber = 0;

    /**
     * 每页记录数，默认20
     */
    private int pageSize = PAGE_SIZE;

    /**
     * 排序，格式{"direction":"DESC","property":"name"}
     */
    private List<PageOrder> pageOrders = new ArrayList<PageOrder>();

    /**
     * 查询模板模板
     */
    private T searchExmaple;

    /**
     * 导出CVS文件列分隔符，1Tab,2分号‘;’，3逗号‘,’
     */
    private String cloumnSeparatorType;

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber
     *            the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the pageOrders
     */
    public List<PageOrder> getPageOrders() {
        return pageOrders;
    }

    /**
     * @param pageOrders
     *            the pageOrders to set
     */
    public void setPageOrders(List<PageOrder> pageOrders) {
        this.pageOrders = pageOrders;
    }

    /**
     * @return the searchExmaple
     */
    public T getSearchExmaple() {
        return searchExmaple;
    }

    /**
     * @param searchExmaple
     *            the searchExmaple to set
     */
    public void setSearchExmaple(T searchExmaple) {
        this.searchExmaple = searchExmaple;
    }

    /**
     * @return the cloumnSeparatorType
     */
    public String getCloumnSeparatorType() {
        return cloumnSeparatorType;
    }

    /**
     * @param cloumnSeparatorType
     *            the cloumnSeparatorType to set
     */
    public void setCloumnSeparatorType(String cloumnSeparatorType) {
        this.cloumnSeparatorType = cloumnSeparatorType;
    }

    /**
     *
     * @Title getPageRequst
     * @Description TODO
     * @return
     * @return PageRequest
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public PageRequest getPageRequst() {
        List<Order> orders = new ArrayList<>();
        for (PageOrder pageOrder : this.pageOrders) {
            Order order = pageOrder.getOrder();
            if (order != null) {
                orders.add(order);
            }
        }

        PageRequest pageRequest = PageRequest.of(this.pageNumber, this.pageSize, Sort.by(orders));

        return pageRequest;
    }

}
