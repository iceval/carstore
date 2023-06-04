package org.rosatom.carstore.exceptions;

public class CarNotAddedException extends RuntimeException{
    public CarNotAddedException(String msg){
        super(msg);
    }
}
