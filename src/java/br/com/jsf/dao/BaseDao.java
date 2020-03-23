/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsf.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 * @param <T>
 * @param <ID>
 */
public interface BaseDao<T, ID> {
    
    void salvarOuAlterar(T entidade, Session sessao) 
                                        throws HibernateException;
    void excluir(T entidade, Session sessao)
                                        throws HibernateException;
    
    T pesquisarPorId(ID id, Session sessao)
                                         throws HibernateException;
    
    List listarTodo(Session sessao)throws HibernateException;
}
