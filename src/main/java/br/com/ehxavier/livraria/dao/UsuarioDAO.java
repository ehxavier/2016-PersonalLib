package br.com.ehxavier.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.ehxavier.livraria.modelo.Usuario;

public class UsuarioDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject		
	EntityManager em;
	
	public boolean existe(Usuario usuario) {
		
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u "
				+ "where u.login = :pLogin and u.senha = :pSenha", Usuario.class);
		query.setParameter("pLogin", usuario.getLogin());
		query.setParameter("pSenha", usuario.getSenha());
		try{
			Usuario result = query.getSingleResult();
		} catch (NoResultException e){
			return false;
		}
		
		return true;
	}

}
