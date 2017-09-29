package br.com.ehxavier.livraria.transaction;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class TransactionManager implements Serializable {

	@Inject
	private EntityManager em;

	@AroundInvoke
	public Object executeTransaction(InvocationContext context) throws Exception{
		
		// abrindo uma transacao
		em.getTransaction().begin();
		
		// carregando os DAOs para uma transação	
		Object result = context.proceed();
		
		// commitando uma transacao
		em.getTransaction().commit();
	
		return result;
		
	}
	
}
