package ro.sci.requestweb.service;

import org.springframework.stereotype.Service;
import ro.sci.requestweb.dto.UserInSession;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Hashtable;

@Service
public class AuthenticationService {

    public UserInSession authentication(String name, String pass) throws NamingException {
        String ldapUrl = "ldap://192.168.168.25:389"; // Adresa IP sau numele de gazdă al serverului LDAP
        String searchBase = "DC=dgpmbpublic,DC=local"; // Baza de căutare în directorul LDAP
        String searchFilter = "(sAMAccountName=" + name.trim() + ")";
//        String searchFilter = "(objectClass=user)"; // Filtrul de căutare pentru a găsi utilizatorii


        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, name.trim() + "@dgpmbpublic.local");
        env.put(Context.SECURITY_CREDENTIALS, pass);


        UserInSession userDetails = new UserInSession();

        try {
            // Creează contextul director initial
            DirContext context = new InitialDirContext(env);

            // Configurează setările de căutare
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            // Realizează căutarea și obține rezultatele
            NamingEnumeration<SearchResult> results = context.search(searchBase, searchFilter, searchControls);


            // Parcurge rezultatele
            while (results.hasMore()) {
                SearchResult result = results.next();
                Attributes attributes = result.getAttributes();
                // Member of
                Attribute memberOf = result.getAttributes().get("memberOf");
                String memberOfValue = memberOf.toString();
                int indexOfFirstComma = memberOfValue.indexOf(",");
                int indexOfFistEqual = memberOfValue.indexOf("=");
                String extractedValue = memberOfValue.substring(indexOfFistEqual + 1, indexOfFirstComma).toUpperCase();

                //Dislay name
                String displayName = attributes.get("displayName").toString();
                int indexOfPoints = displayName.indexOf(":");
                String userName = displayName.substring(indexOfPoints + 1);

                userDetails = UserInSession.builder()
                        .displayName(userName)
                        .memberOf(extractedValue)
                        .build();
            }

            context.close();

        } catch (AuthenticationException e) {
            throw new AuthenticationException("LDAP authentication failed");
        } catch (NamingException e) {
            e.printStackTrace();
            throw e;
        }
        return userDetails;
    }
}
