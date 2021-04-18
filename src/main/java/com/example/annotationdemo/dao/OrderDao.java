package com.example.annotationdemo.dao;

import com.example.annotationdemo.aop.Facade;
import com.example.annotationdemo.dto.BaseResponse;
import com.example.annotationdemo.dto.InsertResult;
import com.example.annotationdemo.dto.OrderVO;
import com.example.annotationdemo.dto.User;
import org.springframework.stereotype.Service;

@Service
public class OrderDao
{
    public InsertResult insert(final OrderVO orderVo)
    {
        return new InsertResult(1);
    }


    @Facade
    public BaseResponse query(User user)
    {
        return null;
    }
}
