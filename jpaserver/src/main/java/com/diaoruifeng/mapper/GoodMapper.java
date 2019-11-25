package com.diaoruifeng.mapper;


import com.diaoruifeng.domian.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface GoodMapper extends JpaRepository<Order,Integer>, JpaSpecificationExecutor<Order> {
}
