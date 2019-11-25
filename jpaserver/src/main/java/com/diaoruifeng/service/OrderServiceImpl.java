package com.diaoruifeng.service;

import com.diaoruifeng.mapper.GoodMapper;
import com.diaoruifeng.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodMapper goodMapper;
}
