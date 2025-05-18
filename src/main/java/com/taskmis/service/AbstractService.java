package com.taskmis.service;

import com.taskmis.exception.ORMException;
import com.taskmis.exception.RecordNotFoundException;
import com.taskmis.repository.Repository;
import com.taskmis.util.RequestParams;
import com.taskmis.util.validator.ParamsValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

import java.util.List;

public abstract class AbstractService<E> implements Service<E> {
  @Getter private final Repository<E> repository;

  protected AbstractService(Repository<E> repository) {
    this.repository = repository;
  }

  @Override
  public E findById(HttpServletRequest request) throws RecordNotFoundException, ORMException {
    String param = request.getParameter("id");
    // Validate
    ParamsValidator.isValidIdParam(param, null);
    return getRepository().findById(Integer.parseInt(param));
  }

  @Override
  public List<E> findAll(HttpServletRequest request) throws ORMException {
    return getRepository().findAll(RequestParams.getPage(request));
  }
}
