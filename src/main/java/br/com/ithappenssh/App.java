package br.com.ithappenssh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}