/**
 * <li>文件名：BaseService.java
 * <li>说明：
 * <li>创建人： 曾明辉
 * <li>创建日期：2018年4月23日
 * <li>修改人：
 * <li>修改日期：
 */
package com.flywin.core.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flywin.core.dto.SysLoginUser;
import com.flywin.core.exception.BusinessException;
import com.flywin.core.repository.mybaitis.MybatisBaseEntity;
import com.flywin.redis.RedisLoginUserManager;
import com.flywin.utils.TokenUtils;
import com.flywin.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * @param <T> 实体对象
 * @param <M> 持久化类
 * @Description: TODO
 * @author: 曾明辉
 * @date: 2019年8月14日
 */
public class MybatisBaseService<M extends BaseMapper<T>, T extends MybatisBaseEntity> extends ServiceImpl<M, T> {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(MybatisBaseService.class);

    /**
     * 用户Redis管理类
     */
    @Autowired
    private RedisLoginUserManager redisLoginUserManager;

    /**
     * @param id 对象
     * @return T
     * @throws Exception 异常
     * @Title get 获取基本对象，一般不重载此方法
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public T get(Serializable id) throws Exception {
        return this.get(id);
    }

    /**
     * @return List<T>
     * @throws Exception 异常
     * @Title findAll
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public List<T> findAll() throws Exception {
        return this.findAll();
    }

    /**
     * @param example 查找对象模板
     * @return List<T>
     * @throws Exception 异常
     * @Title find
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public List<T> find(T example) throws Exception {
        example.setIsDelete(false); // 增加默认查询条件
        return this.find(example);
    }


    /**
     * @param pageable 翻页
     * @param example  查找对象模板
     * @return Page<T>
     * @throws Exception 异常
     * @Title findPage
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    public Page<T> findPage(Pageable pageable, T example) throws Exception {
        example.setIsDelete(false); // 增加默认查询条件
        return this.findPage(pageable, example);
    }

    /**
     * @param entityList 对象List
     * @return List<T>
     * @throws Exception 异常
     * @Title saveAll
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Transactional(rollbackFor = Exception.class)
    public List<T> saveAll(List<T> entityList) throws Exception {
        return this.saveAll(entityList);
    }

    /**
     * @param id 对象id
     * @throws Exception 异常
     * @Title deleteById
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Serializable id) throws Exception {
        this.deleteById(id);
    }

    /**
     * @param ids 对象id数组
     * @throws Exception 异常
     * @Title deleteByIds
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年7月15日
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Serializable[] ids) throws Exception {
        for (Serializable id : ids) {
            this.deleteById(id);
        }
    }

    /**
     * @param id        对象id
     * @param loginUser 登录用户
     * @throws Exception 异常
     * @Title softDeleteById
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月12日
     */
    @Transactional(rollbackFor = Exception.class)
    public void softDeleteById(Serializable id, SysLoginUser loginUser) throws Exception {

    }

    /**
     * @param ids       对象id数字
     * @param loginUser 登录用户
     * @throws Exception 异常
     * @Title softDeleteByIds
     * @Description TODO
     * @author 曾明辉
     * @date: 2019年8月14日
     */
    @Transactional(rollbackFor = Exception.class)
    public void softDeleteByIds(Serializable[] ids, SysLoginUser loginUser) throws Exception {

    }

    /**
     * 获取当前登录用户
     *
     * @return SysLoginUser
     * @throws BusinessException
     */
    private SysLoginUser getCurrentSysLoginUser() throws BusinessException {
        /**获取HttpServletRequest*/
        HttpServletRequest request = WebUtils.getRequest();
        /**获取登录用户*/
        String token = TokenUtils.getRequestToken(request);
        return this.redisLoginUserManager.getLoginUserByToken(token);
    }


}
