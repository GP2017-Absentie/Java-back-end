package com.gp2017;

import com.gp2017.Model.AbsentieModel;
import com.gp2017.Model.DatabaseModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class Main
{
    public static void main(String [] args) {
        DatabaseModel.open();
        SpringApplication.run(Main.class, args);
    }
//
//
}