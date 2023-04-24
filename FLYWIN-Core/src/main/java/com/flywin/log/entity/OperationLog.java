package com.flywin.log.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Description 操作日志
 * @Author wumin
 * @Date 2020-03-25 16:57
 * @Version 1.0
 */
@ApiModel(description = "操作日志")
public class OperationLog {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 应用
     */
    @ApiModelProperty(value = "应用")
    private String application;
    /**
     * 操作名称
     */
    @ApiModelProperty(value = "操作名称")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 请求数据
     */
    @ApiModelProperty(value = "请求数据")
    private String requestJson;
    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private String responseJson;
    /**
     * IP
     */
    @ApiModelProperty(value = "IP")
    private String ip;
    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    private String path;
    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID")
    private String creatorId;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creator;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 是否管理员：1是，0否
     */
    @ApiModelProperty(value = "是否管理员：1是，0否")
    private Integer isAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
