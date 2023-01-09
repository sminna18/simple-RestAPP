package com.example.RestAPP.util;

public class PersonNotCreatedExeption extends RuntimeException{
    public PersonNotCreatedExeption(String msg) {
        super(msg);
    }
}
