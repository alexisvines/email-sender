package emailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    /**
     * Metodo inicializador de main de la app
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(Application.class,args);
    }
}
