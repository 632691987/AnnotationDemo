package com.example.annotationdemo.controller;

import com.example.annotationdemo.aop.OpLog;
import com.example.annotationdemo.aop.OpType;
import com.example.annotationdemo.dao.OrderDao;
import com.example.annotationdemo.dto.BaseResponse;
import com.example.annotationdemo.dto.InsertResult;
import com.example.annotationdemo.dto.OrderVO;
import com.example.annotationdemo.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController
{

    @Autowired
    private OrderDao orderDao;


    @PostMapping
    @OpLog(opType = OpType.QUERY, opItem = "order", opItemIdExpression = "#insertResult.id")
    public InsertResult insert(OrderVO orderVo)
    {
        return orderDao.insert(orderVo);
    }

    @GetMapping
    public BaseResponse errorCase() {
        return orderDao.query(new User());
    }
}
