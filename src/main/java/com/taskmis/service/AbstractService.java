package com.taskmis.service;

import com.taskmis.exception.ORMException;
import com.taskmis.exception.RecordNotFoundException;
import com.taskmis.repository.Repository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.List;

public abstract class AbstractService<E> implements Service<E> {
  @Getter private final Repository<E> repository;

  public AbstractService(Repository<E> repository) {
    this.repository = repository;
  }

  @Override
  public E findById(HttpServletRequest request) throws RecordNotFoundException, ORMException {
    int id = Integer.parseInt(request.getParameter("id"));
    return getRepository().findById(id);
  }

  @Override
  public List<E> findAll(HttpServletRequest request) throws ORMException {
    int page = Integer.parseInt(request.getParameter("page"));
    return getRepository().findAll(page);
  }
}
