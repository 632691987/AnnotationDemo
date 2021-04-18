package com.example.annotationdemo.aop;

public enum OpType
{
    QUERY("QUERY");


    OpType(final String name)
    {
        this.name = name;
    }

    private String name;
}
