package com.work.lp3;

import com.work.lp3.service.MenuGeralService;
import com.work.lp3.service.apostador.ApostadorService;
import com.work.lp3.service.jogo.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lp3Application {

    @Autowired
    private MenuGeralService menuGeralService;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Lp3Application.class);
        builder.headless(false);
        builder.run(args);
    }

    @Bean
    public void run() {
        menuGeralService.criarMenuAposta();
    }

}
