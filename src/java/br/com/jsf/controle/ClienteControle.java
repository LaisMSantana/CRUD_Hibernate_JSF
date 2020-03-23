package br.com.jsf.controle;

import br.com.jsf.dao.ClienteDao;
import br.com.jsf.dao.ClienteDaoImpl;
import br.com.jsf.dao.HibernateUtil;
import br.com.jsf.entidade.Cliente;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

@ManagedBean(name = "clienteC")
public class ClienteControle {

    private Cliente cliente;
    private ClienteDao clienteDao;
    private Session session;
    private DataModel clientes;

    @PostConstruct
    public void iniciar() {
        cliente = new Cliente();
        clienteDao = new ClienteDaoImpl();
    }

    public String salvar() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            session = HibernateUtil.abrirSessao();
            clienteDao.salvarOuAlterar(cliente, session);
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Salvo com Sucesso!", "");
            contexto.addMessage(null, mensagem);
            cliente = new Cliente();
        } catch (HibernateException e) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao salvar!", "");
            contexto.addMessage(null, mensagem);
        } finally {
            session.close();
        }
        return "principal";
    }
    
    public void pesquisarCliente(){
        try {
            clienteDao = new ClienteDaoImpl();
            session = HibernateUtil.abrirSessao();
            List<Cliente> listClientes = clienteDao.
                               pesquisarPorNome(cliente.getNome(), session);
            clientes = new ListDataModel(listClientes);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar por nome " + e.getMessage());
        }
    }

    
    
//    getters e setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DataModel getClientes() {
        return clientes;
    }
    
    

}
