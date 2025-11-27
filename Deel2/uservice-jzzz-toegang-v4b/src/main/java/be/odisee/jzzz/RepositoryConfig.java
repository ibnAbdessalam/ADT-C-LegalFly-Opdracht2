package be.odisee.jzzz;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import be.odisee.jzzz.domain.Evenement;
import be.odisee.jzzz.domain.Toegang;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
   
   /**
    * Volgende configuratie override is nodig om de id's van Lid te tonen, we hebben ze nodig
    * REST geeft ze anders niet als attribuut door omdat ze ook als URI beschikbaar zijn
    */
   @Override
   public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry corsRepositoryRestConfiguration) {
       config.exposeIdsFor(Evenement.class, Toegang.class);
   }
}