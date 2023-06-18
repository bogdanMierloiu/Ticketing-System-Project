package ro.sci.requestservice.configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;


    public CustomAuthenticationManager(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Extrage cheia API din cerere
        String apiKey = (String) authentication.getPrincipal();

        // Creează un obiect CustomAuthenticationToken cu cheia API
        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(apiKey);

        // Autentifică obiectul CustomAuthenticationToken folosind authenticationProvider

        // Returnează obiectul Authentication autentificat
        return authenticationProvider.authenticate(authenticationToken);
    }

}
