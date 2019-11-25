package com.diaoruifeng.controller;

import com.diaoruifeng.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("order")
@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;


}
