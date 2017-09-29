package br.com.ehxavier.livraria.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.ehxavier.livraria.modelo.Usuario;

public class AutorizadorPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent evento) {
		FacesContext context = evento.getFacesContext();
		String pagina = context.getViewRoot().getViewId();
		System.out.println("Nome da pagina: " + pagina);
		if(pagina.equals("/login.xhtml")){
			return;
		}
		
		Usuario usuarioAutenticado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioAutenticado");
		
		if(usuarioAutenticado != null){
			return;
		}
		
		//redirecionando para login.xhtml
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		//System.out.println("FASE: " + event.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
