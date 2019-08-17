package ldap;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class TestLDAP {
	
	/*static {
		String keystorePath = "C:\\Users\\sesa513057\\Documents\\Schneider\\Work\\7. Sailpoint-ldap-sync\\ssl-connect-app\\certs\\opendjTruststore.jks";
		System.setProperty("javax.net.ssl.keyStore", keystorePath);
		 
		System.setProperty("javax.net.ssl.keyStorePassword", "test@123");
	}*/

	public static void main(String[] args) {
		DirContext dirContext = null;
		
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.put(Context.SECURITY_AUTHENTICATION, "simple");
		props.put(Context.PROVIDER_URL, "ldap://10.155.2.51:1389");
		props.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
		props.put(Context.SECURITY_CREDENTIALS, args[0]);
		
		/*props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.put(Context.SECURITY_AUTHENTICATION, "simple");
		props.put(Context.PROVIDER_URL, "ldaps://opendj-qa1.schneider-electric.com:1636");
		props.put(Context.SECURITY_PRINCIPAL, "uid=sailpointsyncuser,ou=people,dc=schneider-electric,dc=com");
		props.put(Context.SECURITY_CREDENTIALS, "Sailpoint@123");*/
		
		/*props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		props.put(Context.SECURITY_AUTHENTICATION, "simple");
		props.put(Context.PROVIDER_URL, "ldap://10.155.2.90:1389");
		props.put(Context.SECURITY_PRINCIPAL, "uid=sailpointsyncuser,ou=people,dc=schneider-electric,dc=com");
		props.put(Context.SECURITY_CREDENTIALS, "Sailpoint@123");*/
		
		//props.put("java.naming.referral", "follow");
		//props.put("java.naming.security.protocol", "ssl");
		
		String baseDN = null;
		NamingEnumeration<SearchResult> results = null;
		
		//uid=sailpointsyncuser
		try {
			dirContext = new InitialDirContext(props);
			baseDN = "ou=people,dc=schneider-electric,dc=com";
			String query = "(&(objectclass=*)(mail=pplogzio*@mailinator.com))";
			//String query = "(&(objectclass=*)(uid=cn00knwV-k5K0-30Mj-WgDk-E5z2I1qHlVxn))";
			results = dirContext.search(
					baseDN, 
					query, new SearchControls());

		} catch (NamingException e1) {
			e1.printStackTrace();
			return;
		}
		
		System.out.println("Connected");
		
		//Add attribute - Loginid
		//Remove attribute - AuthID = []
		
		if(false) {
			while(results.hasMoreElements()) {
				SearchResult searchResult = results.nextElement();
				Attributes attrs = searchResult.getAttributes();
				
				//List<String> record = new LinkedList<String>();
				//Shyam: Temporarily disable - Need to externalize this code
				//record.add(Long.toString(curRecIndex));
				
				String uidStr = null;
				Attribute attributeUID = attrs.get("uid");
				Attribute attrActivated = attrs.get("isActivated");
				Attribute mailID = attrs.get("mail");
				
				//If the specified attribute is not available with this search result
				if(attributeUID == null && attrActivated == null) {
					System.out.println("-");
					continue;
				}
				
				StringBuilder valueBuf = new StringBuilder();
				NamingEnumeration<?> all;
				try {
					if(attributeUID != null) {
						all = attributeUID.getAll();
						if(all.hasMoreElements()) {
							uidStr = all.nextElement().toString();
							valueBuf.append(uidStr);
						}
						
						while(all.hasMoreElements()) {
							valueBuf.append(", " + all.nextElement().toString());
						}
					}
					
					if(attrActivated != null) {
						all = attrActivated.getAll();
						if(all.hasMoreElements())
							valueBuf.append(" | " + all.nextElement().toString());
						
						while(all.hasMoreElements()) {
							valueBuf.append(", " + all.nextElement().toString());
						}
						
					}
	
					//System.out.println(valueBuf.toString());
					
					try {
						ModificationItem[] mods = new ModificationItem[1];
						Attribute mod0 = new BasicAttribute("isActivated", "true");
						mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
						
						String m = mailID.toString();
						m = m.substring(m.indexOf(":")+1).trim();
						//System.out.println("====> " + m);
						//System.out.println("====> " + uidStr);
						
						Attribute mod1 = new BasicAttribute("Loginid", m);
						mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod1);
						
						Attribute mod2 = new BasicAttribute("AuthID", null);
						mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod2);
						
						String name = "uid=" + uidStr + "," + baseDN;
						dirContext.modifyAttributes(name, mods);
					} catch(Exception e) {
						System.out.println("Exception in " + uidStr + ", " + e.getMessage());
					}
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
			
		//System.exit(0);

		while(results.hasMoreElements()) {
			try {
				SearchResult searchResult = results.next();
				Attributes attrs = searchResult.getAttributes();
				List<String> retAttrs = new LinkedList<String>();
				
				NamingEnumeration<? extends Attribute> attrEnum = attrs.getAll();
				while(attrEnum.hasMoreElements()) {
					Attribute attribute = attrEnum.nextElement();
					
					//If the specified attribute is not available with this search result
					if(attribute == null) {
						continue;
					}
					
					StringBuilder valueBuf = new StringBuilder();
					NamingEnumeration<?> all = attribute.getAll();
					
					if(all.hasMoreElements())
						valueBuf.append(all.nextElement().toString());
					
					while(all.hasMoreElements()) {
						valueBuf.append(", " + all.nextElement().toString());
					}
					
					System.out.println("name = " + attribute.getID()
							 + ", value = " + valueBuf.toString());
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}
}
