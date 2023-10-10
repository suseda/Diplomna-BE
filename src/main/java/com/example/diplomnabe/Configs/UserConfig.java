package com.example.diplomnabe.Configs;

import com.example.diplomnabe.Repositories.UserRepository;
import com.example.diplomnabe.Classes.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig
{
    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository repository
    ) {
        return args -> {
            User kaloyan = new User("Kaloyan","kaloyan@gmail.com",18,"kalko");
            User mitko = new User("Mitko","mitko@gmail.com",18,"mitko123");
            repository.saveAll(List.of(kaloyan,mitko));

        };
    }
}
