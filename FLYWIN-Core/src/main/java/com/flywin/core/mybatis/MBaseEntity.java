package com.flywin.core.mybatis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: flywin
 * @description: mybatis基础类
 * @author: tongdanping
 * @create: 2023-04-24 10:48
 **/
@Data
public class MBaseEntity implements Serializable {
    /**
     * @desc 自增主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * @desc 创建人工号
     */
    @TableField(value = "create_by")
    private String createBy;


    /**
     * @desc 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * @desc 更新人工号
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * @desc 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * @desc 是否逻辑删除
     */
    @TableField(value = "is_delete")
    private Boolean isDelete;
}
