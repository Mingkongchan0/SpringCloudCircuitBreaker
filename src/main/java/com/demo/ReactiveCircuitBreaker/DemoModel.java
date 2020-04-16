package com.demo.ReactiveCircuitBreaker;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class DemoModel {
    @Id private Integer id;
    private String name, username, email, password;

    DemoModel(){
        this.setName("NAME NOT FOUND");
    }
}
