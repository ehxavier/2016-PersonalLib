package br.com.ehxavier.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean; - Removido pela implementação CDI
//import javax.faces.bean.ViewScoped; - Alterado pela implementação CDI
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ehxavier.livraria.dao.UsuarioDAO;
import br.com.ehxavier.livraria.modelo.Usuario;

//@ManagedBean - Removido pela implementação CDI
@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	UsuarioDAO dao;
	
	@Inject
	FacesContext context;

	public Usuario getUsuario() {
		return usuario;
	}

	public String autenticarUsuario(){
		System.out.println("Realizando autenticacao do usuario " + usuario.getLogin());
		boolean existe = dao.existe(this.usuario);
		if(existe){			
			context.getExternalContext().getSessionMap().put("usuarioAutenticado", this.usuario);
			return "livro?faces-redirect=true";
		}
		
		context.addMessage("Login:login", new FacesMessage("Usuario/senha incorretos"));	
		
		return "login";
		
	}
	
	public String desconectarUsuario(){
		context.getExternalContext().getSessionMap().remove("usuarioAutenticado");
		return "login?faces-redirect=true";
	}
	
}
