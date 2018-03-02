package org.se.lab.helpers;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class RemoteICTestHelper {

	private static final String APP_NAME = "";
	private static final String MODUL_NAME = "pse";
	private static final String DISTINCT_NAME = "";
	

	@SuppressWarnings("unchecked")
	public static <T> T lookup(Class<? extends T> bean, Class<T> viewClass) throws NamingException, IOException {
    	
    	// TODO jndi.properties File
    	final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", "true");
        final Context context = new InitialContext(jndiProperties);
        
        // @see https://docs.oracle.com/cd/E19798-01/821-1841/gipiu/index.html
        final String jndiName = "ejb:" + APP_NAME 
        						+ "/" + MODUL_NAME 
        						+ "/" + DISTINCT_NAME
        						+ "/" + bean.getSimpleName()
        						+ "!" + viewClass.getName();
        
        return (T) context.lookup(jndiName);
	}
	
}
