/**
 * @Title: TransactionDto.java
 * @Package com.flywin.sapmq.dto
 * @Description: TODO
 * @author: 曾明辉
 * @date 2021年5月10日
 */
package com.flywin.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2021年5月10日
 */
@Getter
@Setter
public class TransactionDto implements Serializable {

    /**
     * serialVersionUID: TODO
     */
    private static final long serialVersionUID = -7195694598760515321L;

    /**
     * 交易Key
     */
    private String transactionKey;

    /**
     * 交易配置文件区分
     */
    private String transFileDiff;

    /**
     * 交易时间
     */
    private Date transactionTime;

    /**
     * 数据
     */
    private Object data;

}
