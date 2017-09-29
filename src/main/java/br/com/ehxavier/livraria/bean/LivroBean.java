package br.com.ehxavier.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean; - Removido pela implementação CDI
//import javax.faces.bean.ViewScoped; - Alterado pela implementação CDI
import javax.faces.view.ViewScoped;
//import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
//import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JOptionPane;

import br.com.ehxavier.livraria.dao.AutorDAO;
//import br.com.ehxavier.livraria.dao.DAO;
import br.com.ehxavier.livraria.dao.LivroDAO;
import br.com.ehxavier.livraria.modelo.Autor;
import br.com.ehxavier.livraria.modelo.Livro;
import br.com.ehxavier.livraria.transaction.Transacional;

//@ManagedBean - Removido pela implementação CDI
@Named
@ViewScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorId;
	
	@Inject
	LivroDAO livroDao;
	
	@Inject
	AutorDAO autorDao;
	
	@Inject
	FacesContext context;

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {
		return this.livroDao.listaTodos();
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando livro: " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}
			
		if(this.livro.getId() == null){
			this.livroDao.adiciona(this.livro);
		} else {
			this.livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}
	
	@Transacional
	public void remover(Livro livro){
		System.out.println("Removendo livro: " + livro.getTitulo());
		this.livroDao.remove(livro);
	}
	
	
	public void removerAutorLivro(Autor autor){
		this.livro.removeAutor(autor);
	}
	
	public void alterar(Livro livro){
		this.livro = livro;
		System.out.println("Alterando livro: " + livro.getTitulo());
	}

	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	/*
	public void comecaComDigitoUm(FacesContext fc, UIComponent component,
			Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(
					"ISBN deveria começar com 1"));
		}

	}
	*/
}
