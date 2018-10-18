package br.com.detran3.bean.home;

import br.com.detran3.bean.AbstractBean;
import br.com.detran3.dao.DAO;
import br.com.detran3.enuns.TipoUsuario;
import br.com.detran3.enuns.VariaveisSessao;
import br.com.detran3.model.Usuario2;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kleber Junio
 */
@ManagedBean
@RequestScoped
public class HomeAdmBean extends AbstractBean {

    private static final String LOGIN_ADM = "loginAdm?faces-redirect=true";
    private static final String EDIT_USUARIO = "editar-usuario?faces-redirect=true";
    private static final String NEW_USUARIO = "cadastrar-usuario?faces-redirect=true";
    private Usuario2 admSession;

    private Usuario2 colaboradorParaCadastro;
    private Usuario2 admParaCadastro;

    @PostConstruct
    public void init() {
        if (admSession == null) {
            admSession = (Usuario2) pegaDaSessao(VariaveisSessao.USER_ADMINISTRADOR);
        }
    }

    public void autenticar() {
        if (admSession == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/detran3/colaborador/login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(HomeAdmBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    public String btnNew() {
        return EDIT_USUARIO;
    }

    public String editar(Integer idusuario2) {
        Usuario2 user = new DAO<>(Usuario2.class).buscaPorId(idusuario2);
        adicionaNaSessao(VariaveisSessao.USER_TEMPORARIO, user);
        return EDIT_USUARIO;
    }

    public void remover(Integer idusuario2) {
        Usuario2 user = new DAO<>(Usuario2.class).buscaPorId(idusuario2);
        new DAO<>(Usuario2.class).remove(user);
    }

    public String novo() {
        return NEW_USUARIO;
    }

    public void salvar() {
        System.out.println("salvando...");
    }

    public String sair() {
        removeDaSessao(VariaveisSessao.USER_ADMINISTRADOR);
        return LOGIN_ADM;
    }
//    public void sair()  {
//        removeDaSessao(VariaveisSessao.USER_ADMINISTRADOR);
//        try {
//            getContext().getExternalContext().redirect(LOGIN_ADM);
//        } catch (IOException ex) {
//            Logger.getLogger(HomeAdmBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public Usuario2 getAdmSession() {
        return admSession;
    }

    public List<Usuario2> getAdministradores() {
        List<Usuario2> list = new DAO<>(Usuario2.class).listaUsuarios(TipoUsuario.ADM);
        return list;
    }

    public List<Usuario2> getColaboradores() {
        return new DAO<>(Usuario2.class).listaUsuarios(TipoUsuario.COLABORADOR);
//        return list;
    }

    public Usuario2 getAdmParaCadastro() {
        return admParaCadastro;
    }

    public void setAdmParaCadastro(Usuario2 admParaCadastro) {
        this.admParaCadastro = admParaCadastro;
    }

    public Usuario2 getColaboradorParaCadastro() {
        return colaboradorParaCadastro;
    }

    public void setColaboradorParaCadastro(Usuario2 colaboradorParaCadastro) {
        this.colaboradorParaCadastro = colaboradorParaCadastro;
    }

}
