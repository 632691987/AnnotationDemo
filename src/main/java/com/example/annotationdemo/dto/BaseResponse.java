package com.example.annotationdemo.dto;

public class BaseResponse
{
    private boolean success;
    private String responseMessage;

    public boolean isSuccess()
    {
        return success;
    }


    public void setSuccess(final boolean success)
    {
        this.success = success;
    }


    public String getResponseMessage()
    {
        return responseMessage;
    }


    public void setResponseMessage(final String responseMessage)
    {
        this.responseMessage = responseMessage;
    }
}
