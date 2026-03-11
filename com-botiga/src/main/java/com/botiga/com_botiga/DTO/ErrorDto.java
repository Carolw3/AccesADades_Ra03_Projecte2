package com.botiga.com_botiga.DTO;

public class ErrorDto {
   private String error = "Error";

    public ErrorDto(String error) {
        this.error = error;
    }

    public ErrorDto(){
        
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}