package com.demo.task.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BaseService<Entity, ID> {

    Entity save(Entity entity, HttpServletRequest request);

    Entity update(Entity entity, HttpServletRequest request) throws Exception;

    void deleteByIds(ID ids);

    List<Entity> getDataByIds(ID... ids);

    List<Entity> getData();

}
