/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsf.dao;

import br.com.jsf.entidade.Produto;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public class ProdutoDaoImpl extends BaseDaoImpl<Produto, Long>
                                implements ProdutoDao, Serializable{

    @Override
    public Produto pesquisarPorId(Long id, Session sessao) 
                                            throws HibernateException {
        return (Produto)sessao.get(Produto.class, id);
    }

    @Override
    public List listarTodo(Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Produto");
        return consulta.list();
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("from Produto where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}
