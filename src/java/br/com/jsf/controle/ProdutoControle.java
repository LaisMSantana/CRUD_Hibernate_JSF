package br.com.jsf.controle;

import br.com.jsf.dao.HibernateUtil;
import br.com.jsf.dao.ProdutoDao;
import br.com.jsf.dao.ProdutoDaoImpl;
import br.com.jsf.entidade.Produto;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@ManagedBean(name = "produtoC")
@ViewScoped
public class ProdutoControle {

    private Produto produto;
    private ProdutoDao produtoDao;
    private Session session;
    private DataModel<Produto> produtos;
    private boolean mostra_toolbar = true;

    @PostConstruct
    public void iniciar() {
        produto = new Produto();
        produtoDao = new ProdutoDaoImpl();
    }
    
    public void novo(){
        mostra_toolbar = !mostra_toolbar;
    }

    public String salvar() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            produtoDao.salvarOuAlterar(produto, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            produto = new Produto();
        } catch (HibernateException e) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar!", "");
            contexto.addMessage(null, mensagem);
        } finally {
            session.close();
        }
        return "principal";
    }
    
    public void pesquisarProduto(){
        try {
            produtoDao = new ProdutoDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Produto> listProdutos = produtoDao.
                               pesquisarPorNome(produto.getNome(), session);
            produtos = new ListDataModel(listProdutos);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar por nome " + e.getMessage());
        }
    }
    
    public String carregaProduto(){
        return "novoProdutoP";
    }
    
    public void excluir(){
        FacesContext contexto = FacesContext.getCurrentInstance();
        produto = produtos.getRowData();
        try {
            session = HibernateUtil.abrirSessao();
            produtoDao = new ProdutoDaoImpl();
            produtoDao.excluir(produto, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Excluído com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            produtos = null;
            produto = new Produto();
            
        } catch (HibernateException e) {
            System.out.println("Erro ao excluir " + e.getMessage());
        }finally{
            session.close();
        }
    }

    
    
//    getters e setters
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public DataModel getProdutos() {
        return produtos;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }   

}
