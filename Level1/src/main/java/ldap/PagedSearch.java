package ldap;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

/**
 * Shows how a paged search can be performed using the PagedResultsControl API
 */

class PagedSearch {

  public static void main(String[] args) throws Exception {

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
    env
        .put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");

    /* Specify host and port to use for directory service */
    env.put(Context.PROVIDER_URL,
        "ldap://10.155.3.69:1389");
    
    env.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
    
    env.put(Context.SECURITY_CREDENTIALS, "");
    
    /*Properties theProps = new Properties();
	theProps.put(LDAPPropertyMapping_E.INTIAL_CTX_FACTORY.specValue(), "com.sun.jndi.ldap.LdapCtxFactory");
	theProps.put(LDAPPropertyMapping_E.PROVIDER_URL.specValue(), "ldap://10.155.2.90:1389");
	theProps.put(LDAPPropertyMapping_E.SECURITY_PRINCIPAL.specValue(), "cn=sesa513057");
	theProps.put(LDAPPropertyMapping_E.SECURITY_CREDENTIALS.specValue(), "August@123");*/
	
	LdapContext ctx;
	
	try {
		ctx = new InitialLdapContext(env, null);
	} catch (NamingException e) {
		throw new Exception("Error connecting to server", e);
	}

    try {
      ctx = new InitialLdapContext(env, null);

      // Activate paged results
      int pageSize = 5;
      byte[] cookie = null;
      ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize,
          Control.CRITICAL) });
      int total;

      SearchControls theSearchControls = new SearchControls();
      theSearchControls.setSearchScope(2);
      //theSearchControls.setCountLimit(pageSize);

      do {
        /* perform the search */
        NamingEnumeration results = ctx.search("ou=people,dc=schneider-electric,dc=com", 
        		"(&(tncFlag=True)(objectclass=*)(!(registerationSource=idms))(!(registerationSource=oauthSampleApp))(!(registerationSource=samlSampleApp)))",
        		theSearchControls);

        int count = 0;
        /* for each entry print out name + all attrs and values */
        while (results != null && results.hasMore()) {
          SearchResult entry = (SearchResult) results.next();
          System.out.println(entry.getName());
          count ++;
        }
        
        System.out.println(count);

        // Examine the paged results control response
        Control[] controls = ctx.getResponseControls();
        if (controls != null) {
          for (int i = 0; i < controls.length; i++) {
            if (controls[i] instanceof PagedResultsResponseControl) {
              PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
              total = prrc.getResultSize();
              if (total != 0) {
                System.out.println("***************** END-OF-PAGE "
                    + "(total : " + total + ") *****************\n");
              } else {
                System.out.println("***************** END-OF-PAGE "
                    + "(total: unknown) ***************\n");
              }
              cookie = prrc.getCookie();
            }
          }
        } else {
          System.out.println("No controls were sent from the server");
        }
        // Re-activate paged results
        ctx.setRequestControls(new Control[] { new PagedResultsControl(
            pageSize, cookie, Control.CRITICAL) });

      } while (cookie != null);

      ctx.close();

    } catch (NamingException e) {
      System.err.println("PagedSearch failed.");
      e.printStackTrace();
    } catch (IOException ie) {
      System.err.println("PagedSearch failed.");
      ie.printStackTrace();
    }
  }
}
