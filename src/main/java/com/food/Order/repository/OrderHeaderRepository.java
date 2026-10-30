package com.food.Order.repository;


import com.food.Order.entity.Item;
import com.food.Order.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader,String> {
    OrderHeader findByOrderHeaderId(String orderHeaderId);


}
