package br.com.ehxavier.livraria.bean;

import java.io.Serializable;
import java.util.List;

//import javax.faces.bean.ManagedBean; - Removido pela implementação CDI
//import javax.faces.bean.ViewScoped; - Alterado pela implementação CDI
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import br.com.ehxavier.livraria.dao.AutorDAO;
import br.com.ehxavier.livraria.modelo.Autor;
import br.com.ehxavier.livraria.transaction.Transacional;

//@ManagedBean - Removido pela implementação CDI
@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	@Inject
	private AutorDAO dao; // Criação e injeção do DAO pelo CDI

	public Autor getAutor() {
		return autor;
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			this.dao.adiciona(this.autor);
		} else {
			this.dao.atualiza(this.autor);
		}

		this.autor = new Autor();

		/*
		 * if(){ return "livro?faces-redirect=true"; } else { return ??? }
		 */
	}

	public List<Autor> getAutores() {
		return this.dao.listaTodos();
	}

	@Transacional
	// M�todo para remover autor
	public void remover(Autor autor) {
		System.out.println("Removendo autor: " + autor.getNome());
		this.dao.remove(autor);
	}

	// M�todo para alterar autor
	public void alterar(Autor autor) {
		System.out.println("Atualizando autor: " + autor.getNome());
		this.autor = autor;
	}

	// M�todo para carregar dados do autor na web
	public Autor consultarAutorWeb() {

		System.out.println("Consultando na web...");
		System.out.println(autor.getNome());

		if (autor.getNome() != null && autor.getNome().length() > 0) {

			String prefix = "PREFIX dbo:<http://dbpedia.org/ontology/> "
					+ "PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>"
					+ "PREFIX dbp:<http://dbpedia.org/property/>";
			String sparql = "SELECT DISTINCT ?author ?name ?nacionality ?bplacename ?dtnasc "
					+ "WHERE { ?book a dbo:Book .  ?book dbo:author ?author . ?author rdfs:label ?name . "
					+ "?author dbo:birthDate ?dtnasc . ?author dbp:nationality ?nacionality . "
					+ "?author dbo:birthPlace ?bplace . ?bplace rdfs:label ?bplacename . "
					+ "FILTER(lang(?name) = 'pt') FILTER(lang(?bplacename) = 'pt') " + "FILTER regex(?name, \""
					+ autor.getNome() + "\"@pt) }";

			String queryStr = prefix + sparql;

			Query query = QueryFactory.create(queryStr);

			QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

			ResultSet rs = queryExecution.execSelect();

			if (rs.hasNext()) {

				QuerySolution querySolution = rs.next();

				autor.setNome(querySolution.getLiteral("nome").toString());
				autor.setNacionalidade(querySolution.getLiteral("nacionalidade").toString());
				autor.setNaturalidade(querySolution.getLiteral("naturalidade").toString());

				System.out.println(autor.getNome());
				System.out.println(autor.getNacionalidade());
				System.out.println(autor.getNaturalidade());
				System.out.println(autor.getDataNasc());

			}
		}

		return autor;

	}
}
