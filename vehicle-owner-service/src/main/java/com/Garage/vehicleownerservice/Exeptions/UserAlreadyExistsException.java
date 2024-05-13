package com.Garage.vehicleownerservice.Exeptions;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
