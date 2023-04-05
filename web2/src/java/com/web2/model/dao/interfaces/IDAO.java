/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.web2.model.dao.interfaces;

import com.web2.model.exceptions.DAOException;
import java.util.ArrayList;

public interface IDAO<T, E> {

    public void insert(T t) throws DAOException;

    public void update(T t) throws DAOException;

    public void delete(E e) throws DAOException;

    public T selectById(E e) throws DAOException;
}
