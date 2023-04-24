package com.flywin.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.servlet.http.Cookie;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysLoginUser
 * @Description: 登录用户
 * @Author: 冉小刚
 * @Date: 2021-3-19 15:32:44
 * @Version: 2.0
 */
@Data
@ApiModel(description = "登录用户信息")
public class SysLoginUser implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -1392036166051633735L;

    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户ID")
    private String id;

    /**
     * 用户账号
     */
    @NotBlank(message = "{login.user.valid.account.empty}")
    @ApiModelProperty(value = "用户账号", notes = "登录时（测试环境）前端需要传入")
    private String account;

    /**
     * 用户密码
     */
    @NotBlank(message = "{login.user.valid.password.empty}")
    @ApiModelProperty(value = "用户密码", notes = "登录时（测试环境）前端需要加密传入")
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", notes = "登录时（测试环境）前端需要传入")
    private String validateCode;

    /**
     * 登录前传递的是验证码关联Token，登录后返回登录用户Token
     */
    @ApiModelProperty(value = "验证码token/用户token", notes = "登录时（测试环境）前端需要加密传入验证码token，登录后返回用户token（正式环境：CAS认证后返回的access_token）")
    private String token;

    /**
     * 传输加密Token
     */
    @NotBlank(message = "{trans.encrypt.token.empty}")
    @ApiModelProperty(value = "传输加密Token", notes = "登录时（测试环境）前端需要传入")
    private String transToken;

    /**
     * 登录设备：0.PC端，1.供方APP，2需方APP（默认为PC端）
     */
    @NotBlank(message = "{login.terminal.equipment.empty}")
    @ApiModelProperty(value = "登录设备：0.PC端，1.供方APP，2需方APP（默认为PC端）", notes = "登录时前端需要传入")
    private String equipmentCode = "0";

    /**
     * Sys认证 cookie（即将弃用）
     */
    @ApiModelProperty(value = "Sys认证cookie", notes = "Sys认证cookie（即将弃用）")
    private Cookie cookie;

    /**
     * CAS认证后的刷新token
     */
    @ApiModelProperty(value = "CAS认证后的刷新token", notes = "CAS认证后的刷新token")
    private String refreshToken;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;

    /**
     * 头像信息
     */
    @ApiModelProperty(value = "头像信息")
    private String headImg;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobilePhone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 传真
     */
    @ApiModelProperty(value = "传真")
    private String fax;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String sex;

    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id")
    private String orgId;

    /**
     * 机构名称
     */
    @ApiModelProperty(value = "机构名称")
    private String orgName;

    /**
     * 机构系统编码
     */
    @ApiModelProperty(value = "机构系统编码")
    private String orgSysCode;

    /**
     * 所属部门ID
     */
    @ApiModelProperty(value = "所属部门ID")
    private String deptId;

    /**
     * 所属部门系统编码
     */
    @ApiModelProperty(value = "所属部门系统编码")
    private String deptSysCode;

    /**
     * 是否为超级管理员
     */
    @ApiModelProperty(value = "是否为超级管理员")
    private boolean isSuperAdmin;

    /**
     * 是否为机构管理员
     */
    @ApiModelProperty(value = "是否为机构管理员")
    private boolean isOrgAdmin;

    /**
     * 机构所属类型代码
     */
    @ApiModelProperty(value = "机构所属类型代码")
    private String orgTypeCode;

    /**
     * 机构所属类型名称
     */
    @ApiModelProperty(value = "机构所属类型名称")
    private String orgTypeName;

    /**
     * 航空公司二字码
     */
    @ApiModelProperty(value = "航空公司二字码")
    private String orgIata;

    /**
     * 航空公司三字码
     */
    @ApiModelProperty(value = "航空公司三字码")
    private String orgIaco;

    /**
     * 机构所属供需标志：1供方，2需方
     */
    @ApiModelProperty(value = "用户供需标志", notes = "供需标志：1供方，2需方")
    private String supplDemandFlag;

    /**
     * 认证用户类型：1:仅Cas认证成功,2:Cas及FlyWin系统认证成功
     */
    @ApiModelProperty(value = "认证用户类型", notes = "1:仅Cas认证成功,2:Cas及FlyWin系统认证成功")
    private String loginLevel;

    /**
     * 允许多端同时登录 0:不允许,1:允许
     */
    @ApiModelProperty(value = "是否允许多端同时登录", notes = "是否允许多端同时登录：0:不允许,1:允许")
    private Long allowMultlogin;

    /**
     * 是否移除其他用户
     */
    @ApiModelProperty(value = "是否移除其他用户", notes = "是否移除其他用户：0:不,1:要")
    private String removeOldLogin = "0";


    /**
     * 角色列表
     */
    @ApiModelProperty(value = "角色列表", notes = "用户所拥有的角色列表")
    private List<String> roleCodes;

    /**
     * 用户访问数据权限级别：1个人级别，2部门级别，3机构级别
     */
    @ApiModelProperty(value = "用户访问数据权限级别：1个人级别，2部门级别，3机构级别")
    private String dataAuthLevelCode;

    /**
     * 用户访问数据控制：1所有可见，2指定机构/部门可见，3所有不可见
     */
    @ApiModelProperty(value = "用户访问数据控制：1所有可见，2指定机构/部门可见，3所有不可见")
    private String dataAccessRestrictionCode;

    /**
     * 用户所管理的机构部门ID列表
     */
    @ApiModelProperty(value = "机构部门ID列表", notes = "用户所管理的机构部门ID列表")
    private List<String> manageOrgDeptIds;

    /**
     * 用户所管理的机构部门级联编码列表
     */
    @ApiModelProperty(value = "机构部门级联编码列表", notes = "用户所管理的机构部门级联编码列表")
    private List<String> manageOrgDeptSysCodes;


    /**
     * 添加角色
     *
     * @param roleCode 角色系统编码
     */
    public void andRoleCode(String roleCode) {
        if (this.roleCodes == null) {
            this.roleCodes = new ArrayList<>();
        }
        this.roleCodes.add(roleCode);
    }

    /**
     * 添加多个角色
     *
     * @param roleCodes 角色系统编码
     */
    public void andAllRoleCode(List<String> roleCodes) {
        if (this.roleCodes == null) {
            this.roleCodes = new ArrayList<>();
        }
        this.roleCodes.addAll(roleCodes);
    }


    /**
     * 添加用户管理的组织系统编码
     *
     * @param orgDeptSysCode 机构/部门系统编码
     */
    public void addManageOrgDeptSysCode(String orgDeptSysCode) {
        if (this.manageOrgDeptSysCodes == null) {
            this.manageOrgDeptSysCodes = new ArrayList();
        }
        this.manageOrgDeptSysCodes.add(orgDeptSysCode);
    }

    /**
     * 添加多个用户管理的组织系统编码
     *
     * @param orgDeptSysCodes 机构/部门系统编码
     */
    public void addAllManageOrgDeptSysCode(List<String> orgDeptSysCodes) {
        if (this.manageOrgDeptSysCodes == null) {
            this.manageOrgDeptSysCodes = new ArrayList<>();
        }
        this.manageOrgDeptSysCodes.addAll(orgDeptSysCodes);
    }

    /**
     * 添加用户管理的组织ID
     *
     * @param orgDeptId 机构/部门ID
     */
    public void addManageOrgDeptId(String orgDeptId) {
        if (this.manageOrgDeptIds == null) {
            this.manageOrgDeptIds = new ArrayList();
        }
        this.manageOrgDeptIds.add(orgDeptId);
    }

    /**
     * 添加多个用户管理的组织系统编码
     *
     * @param orgDeptIds 机构/部门ID
     */
    public void addAllManageOrgDeptId(List<String> orgDeptIds) {
        if (this.manageOrgDeptIds == null) {
            this.manageOrgDeptIds = new ArrayList<>();
        }
        this.manageOrgDeptIds.addAll(orgDeptIds);
    }

}
