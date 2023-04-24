/**
 * <li>文件名：BaseController.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年10月11日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.web;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flywin.core.dto.SysLoginUser;
import com.flywin.core.repository.entity.page.PageSearch;
import com.flywin.core.repository.mybaitis.MybatisBaseEntity;
import com.flywin.core.service.MybatisBaseService;
import com.flywin.core.web.response.ResponseResult;
import com.flywin.core.web.response.ResponseUtils;
import com.flywin.log.annotation.RequestLog;
import com.flywin.redis.RedisLoginUserManager;
import com.flywin.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @param <T> 实体对象
 * @param <M> 持久化类
 * @param <S> 业务处理类
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class BaseController<T extends MybatisBaseEntity, M extends BaseMapper<T>,
        S extends MybatisBaseService<M, T>> {

    /**
     * 日志
     */
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * request
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 用户Redis管理类
     */
    @Autowired
    private RedisLoginUserManager redisLoginUserManager;

    /**
     * 业务处理类
     */
    @Autowired
    protected S baseService;


    /**
     * @param id 对象id
     * @return ResponseResult<T> 格式化结果
     * @throws Exception 异常
     * @Title get
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    public ResponseResult<T> get(@PathVariable("id") String id) throws Exception {
        T entity = this.baseService.get(id);
        return ResponseUtils.success(entity);
    }

    /**
     * @param id 对象id
     * @return ResponseResult<T> 格式化结果
     * @throws Exception 异常
     * @Title getDetail
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestMapping(value = "/getDetail/{id}", method = RequestMethod.GET)
    public ResponseResult<T> getDetail(@PathVariable("id") String id) throws Exception {
        T entity = this.baseService.get(id);
        return ResponseUtils.success(entity);
    }


    /**
     * @param searchEntity 查询对象模板
     * @return
     * @return ResponseResult<List < T>> 列表格式化结果
     * @throws Exception 异常
     * @Title find
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ResponseResult<List<T>> find(@RequestBody T searchEntity) throws Exception {
        List<T> datas = this.baseService.find(searchEntity);
        return ResponseUtils.success(datas);
    }


    /**
     * @param pageSearch 查询对象模板
     * @return
     * @return ResponseResult<List < T>> 翻页对象格式化结果
     * @throws Exception 异常
     * @Title findPage
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public ResponseResult<Page<T>> findPage(@RequestBody PageSearch<T> pageSearch) throws Exception {
        Page<T> page = this.baseService.findPage(pageSearch.getPageRequst(), pageSearch.getSearchExmaple());
        return ResponseUtils.success(page);
    }


    /**
     * @param id 对象id
     * @return
     * @return ResponseResult<T> 返回结果
     * @throws Exception 异常
     * @Title deleteById
     * @Description
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestLog(name = "删除", description = "通用删除方法")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult<T> deleteById(@PathVariable("id") String id) throws Exception {
        this.baseService.deleteById(id);
        return ResponseUtils.success();
    }

    /**
     * @param ids 对象id数组
     * @return
     * @return ResponseResult<T> 返回结果
     * @throws Exception 异常
     * @Title deleteByIds
     * @Description 多个删除
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestLog(name = "批量删除", description = "通用批量删除方法")
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public ResponseResult<T> deleteByIds(@RequestBody String[] ids) throws Exception {
        this.baseService.deleteByIds(ids);
        return ResponseUtils.success();
    }

    /**
     * @param id 对象id
     * @return
     * @return ResponseResult<T> 返回结果
     * @throws Exception 异常
     * @Title softDeleteById
     * @Description 软删除
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestLog(name = "软删除", description = "通用软删除方法")
    @RequestMapping(value = "/softDeleteById/{id}", method = RequestMethod.POST)
    public ResponseResult<T> softDeleteById(@PathVariable("id") String id) throws Exception {
        this.baseService.softDeleteById(id, this.getLoginUser());
        return ResponseUtils.success();
    }

    /**
     * @param ids 对象id数组
     * @return
     * @return ResponseResult<T> 返回结果
     * @throws Exception 异常
     * @Title softDeleteByIds
     * @Description 多个软删除
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @RequestLog(name = "批量软删除", description = "通用批量软删除方法")
    @RequestMapping(value = "/softDeleteByIds", method = RequestMethod.POST)
    public ResponseResult<T> softDeleteByIds(@RequestBody String[] ids) throws Exception {
        this.baseService.softDeleteByIds(ids, this.getLoginUser());
        return ResponseUtils.success();
    }

    /**
     * @return SysLoginUser 登录对象
     * @Title getLoginUser
     * @Description 得到登录对象
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    protected SysLoginUser getLoginUser() {

        String token = TokenUtils.getRequestToken(request);

        return this.redisLoginUserManager.getLoginUserByToken(token);
    }
}
