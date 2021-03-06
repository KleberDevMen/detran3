package br.com.detran3.bean.administrador;

import br.com.detran3.bean.AbstractBean;
import br.com.detran3.dao.DAO;
import br.com.detran3.enuns.TipoUsuario;
import br.com.detran3.enuns.VariaveisSessao;
import br.com.detran3.model.Usuario;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author KLEBER
 */
@ManagedBean
@RequestScoped
public class EdiarUsuarioBean extends AbstractBean {

    private static final String HOME_ADM = "gerenciar-administradores?faces-redirect=true";
    private static final String HOME_COLABORADOR = "gerenciar-colaboradores?faces-redirect=true";

    private Usuario usuario;

    @PostConstruct
    public void init() {
        usuario = (Usuario) pegaDaSessao(VariaveisSessao.USER_TEMPORARIO);
        if (usuario == null) {
            usuario = new Usuario();
        }
    }

    public String btnEditar() {
        new DAO<>(Usuario.class).atualiza(usuario);
        removeDaSessao(VariaveisSessao.USER_TEMPORARIO);
        exibirMensagemFlash("Alterações salvas.");
        if (1 == (int) pegaDaSessao(VariaveisSessao.ITEM_MENU_EDICAO)) {
            return HOME_ADM;
        }
        return HOME_COLABORADOR;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String btnCancelar() {
        return HOME_ADM;
    }

    public List<TipoUsuario> getTiposUser() {
        return Arrays.asList(TipoUsuario.values());
    }

}
