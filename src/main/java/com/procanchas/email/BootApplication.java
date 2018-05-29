package com.procanchas.email;

import com.procanchas.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {



    /**
     * Metodo inicializador de main de la app
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(BootApplication.class,args);
    }

}
