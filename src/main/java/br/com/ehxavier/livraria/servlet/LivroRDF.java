package br.com.ehxavier.livraria.servlet;

public class LivroRDF {}


/*
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import br.com.ehxavier.livraria.dao.LivroDAO;
import br.com.ehxavier.livraria.modelo.Livro;


@WebServlet(name="LivroRDF", urlPatterns = { "/data/livro" })
public class LivroRDF extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private LivroDAO livroDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml");

		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("livraria", "http://localhost:8080/livraria-maven/");
		String myNS = "http://localhost:8080/livraria-maven/data/";

		Integer id = null;

		try {
			id = Integer.valueOf(request.getPathInfo().substring(1));
		} catch (Exception e) {
		}

		if (id == null) {
			model = getRDFlivros(model, myNS);
		} else {
			Livro livro = livroDAO.buscaPorId(id);
			if (livro != null) {
				model = getRDFlivro(model, myNS, livro);
			}
		}

		try (PrintWriter out = response.getWriter()) {
			model.write(out, "RDF/XML-ABBREV");
		}

	}

	private Model getRDFlivros(Model model, String myNS) {
		List<Livro> livros = livroDAO.listaTodos();

		for (int i = 0; i < livros.size(); i++) {
			Livro livro = livros.get(i);
			model = getRDFlivro(model, myNS, livro);
		}

		return model;
	}

	private Model getRDFlivro(Model model, String myNS, Livro livro) {
		
		String uri = "http://localhost:8080/livraria-maven/";
		String tituloLivro = livro.getTitulo();
		String isbnLivro = livro.getIsbn();
		String resenhaLivro = livro.getResenha();
		
		
		Resource resourceLivro = model.createResource(myNS+"livro/"+livro.getId());
		
		Property titulo = ResourceFactory.createProperty(uri+"livro.xml#titulo");
		Property isbn = ResourceFactory.createProperty(uri+"livro.xml#isbn");
		Property resenha = ResourceFactory.createProperty(uri+"livro.xml#resenha");
		
		resourceLivro.addProperty(titulo, tituloLivro);
		resourceLivro.addProperty(isbn, isbnLivro);
		resourceLivro.addProperty(resenha, resenhaLivro);
		
		return model;
	}

}
*/
