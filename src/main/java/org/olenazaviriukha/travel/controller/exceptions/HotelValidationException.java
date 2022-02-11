package org.olenazaviriukha.travel.controller.exceptions;

import org.olenazaviriukha.travel.entity.Hotel;

import java.util.Map;

public class HotelValidationException extends Exception{
    private Map <String, String> errors;
    private Hotel hotel;
    public HotelValidationException(Hotel hotel, Map<String, String> errors) {
        this.errors = errors;
        this.hotel = hotel;
    }

    public Map <String, String> getErrors() {
        return this.errors;
    }

    public Hotel getHotel() {
        return this.hotel;
    }
}
