https://bcrypt-generator.com/

1) package pe.edu.upc.demoauthor.security;
   //Clase 1
   @Component
   public class JwtTokenUtil implements Serializable

2) package pe.edu.upc.demoauthor.servicesimplements;

   //Clase 2
   @Service
   public class JwtUserDetailsService implements UserDetailsService

3)
//Clase 3
@RestController
@CrossOrigin
public class JwtAuthenticationController

4) package pe.edu.upc.demoauthor.security;
   import java.io.Serializable;

   //Clase 4
   public class JwtRequest implements Serializable
5)  package pe.edu.upc.demoauthor.security;

    //clase 5
    public class JwtResponse implements Serializable

6)
    package pe.edu.upc.demoauthor.security;
    //Clase 6
    @Component
   public class JwtRequestFilter extends OncePerRequestFilter

7)  package pe.edu.upc.demoauthor.security;
    //Clase S7
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    public class WebSecurityConfig


    JwtAuthenticationController <----- JwtUserDetailsService y JwtTokenUtil y JwtRequest(--username, password--)
    return JwtResponse


