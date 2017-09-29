package br.com.ehxavier.livraria.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class JSFUtil {
	
	@Produces
	@RequestScoped
	public FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}

}
