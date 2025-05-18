package com.taskmis.service;

import com.taskmis.exception.ORMException;
import com.taskmis.exception.RecordNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface Service<E> {
  /**
   * Find single entity record
   * @param request User request
   * @return E
   */
  E findById(HttpServletRequest request) throws RecordNotFoundException, ORMException;

  /**
   * Find all record on a given page
   * @param request User request
   * @return List<E>
   */
  List<E> findAll(HttpServletRequest request) throws ORMException;
}
