package com.procanchas.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BootApplication {



    /**
     * Metodo inicializador de main de la app
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(BootApplication.class,args);
    }

}
