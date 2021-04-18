package com.example.annotationdemo.dto;

import javax.validation.constraints.NotNull;

public class User
{
    private String idempotentNo;

    @NotNull(message = "userName can't be null")
    private String userName;


    public User()
    {
    }


    public String getIdempotentNo()
    {
        return idempotentNo;
    }


    public void setIdempotentNo(final String idempotentNo)
    {
        this.idempotentNo = idempotentNo;
    }


    public String getUserName()
    {
        return userName;
    }


    public void setUserName(final String userName)
    {
        this.userName = userName;
    }
}
