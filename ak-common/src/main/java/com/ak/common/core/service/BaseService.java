package com.ak.common.core.service;

import com.ak.common.core.domain.BaseEntity;
import com.ak.common.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service基类
 *
 * @author Vean
 * @version 2014-05-16
 */
public abstract class BaseService<D extends BaseMapper<T>, T extends BaseEntity> {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     * @return
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<T> findList(T entity) {
        return dao.findList(entity);
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        dao.delete(entity);
    }

}
