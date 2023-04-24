package com.flywin.core.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年6月25日
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public abstract class BaseEntity {

    /**
     * @return
     * @return String
     * @Title getId
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract String getId();

    /**
     * @param id 对象id
     * @Title setId
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract void setId(String id);

    /**
     * @return
     * @return String
     * @Title getCreator
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract String getCreator();

    /**
     * @param creator 创建者
     * @Title setCreator
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract void setCreator(String creator);

    /**
     * @return
     * @return Date
     * @Title getCreateTime
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract Date getCreateTime();

    /**
     * @param createTime 创建时间
     * @Title setCreateTime
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract void setCreateTime(Date createTime);

    /**
     * @return
     * @return String
     * @Title getModifier
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract String getModifier();

    /**
     * @param modifier 修改者
     * @Title setModifier
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract void setModifier(String modifier);

    /**
     * @return
     * @return Date
     * @Title getModifyTime
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract Date getModifyTime();

    /**
     * @param modifyTime 修改时间
     * @Title setModifyTime
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract void setModifyTime(Date modifyTime);

    /**
     * @return
     * @return String
     * @Title getDelFlag
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract String getDelFlag();

    /**
     * @param delFlag 删除标志
     * @Title setDelFlag
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年6月25日
     */
    public abstract void setDelFlag(String delFlag);


}
