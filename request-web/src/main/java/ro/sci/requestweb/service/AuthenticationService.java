package ro.sci.requestweb.service;

import org.springframework.stereotype.Service;
import ro.sci.requestweb.dto.UserInSession;
import ro.sci.requestweb.exception.NotHaveAccessException;

import javax.naming.*;
import javax.naming.directory.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@Service
public class AuthenticationService {

    public UserInSession authentication(String name, String pass) throws NamingException, NotHaveAccessException {
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
        env.put("java.naming.ldap.referral", "follow");


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
                String memberOfValue = memberOf.toString().toLowerCase();
                String accessLevel = checkAccess(memberOfValue);

                // Display name
                String displayName = attributes.get("displayName").toString();
                int indexOfColon = displayName.indexOf(":");
                String userNameBeforeFormat = displayName.substring(indexOfColon + 1).trim();
                StringBuilder formattedUserName = new StringBuilder();
                String[] nameParts = userNameBeforeFormat.split(" ");
                for (String namePart : nameParts) {
                    String firstLetter = namePart.substring(0, 1).toUpperCase();
                    String restOfName = namePart.substring(1).toLowerCase();
                    formattedUserName.append(firstLetter).append(restOfName).append(" ");
                }
                String formattedDisplayName = formattedUserName.toString().trim();

                userDetails = UserInSession.builder()
                        .displayName(formattedDisplayName)
                        .memberOf(accessLevel)
                        .build();
            }

            context.close();

        } catch (AuthenticationException e) {
            throw new AuthenticationException("LDAP authentication failed");
        } catch (PartialResultException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
            throw e;
        }
        return userDetails;
    }


    private String checkAccess(String memberOfValue) throws NotHaveAccessException {
        Map<String, String> roleMappings = new HashMap<>();
        roleMappings.put("solicitari_cont_b", "sef_birou");
        roleMappings.put("aprobare_solicitare_b", "sef_structura");
        roleMappings.put("autorizare_cont_b", "structura_securitate");
        roleMappings.put("admin_solicitari_b", "admin");

        for (Map.Entry<String, String> entry : roleMappings.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (memberOfValue.contains(key)) {
                return value;
            }
        }
        throw new NotHaveAccessException("User does not have access");
    }


}
