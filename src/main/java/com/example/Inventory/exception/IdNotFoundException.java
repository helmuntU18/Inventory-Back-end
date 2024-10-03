package com.example.Inventory.exception;

public class IdNotFoundException extends RuntimeException{
    
    public IdNotFoundException(String id){
        super("id numero: ".concat(id).concat(" No existe actualmente en la base de datos, por favor verifique e intentelo nuevamente"));
    }
}
