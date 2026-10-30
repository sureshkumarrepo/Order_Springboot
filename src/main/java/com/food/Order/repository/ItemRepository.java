package com.food.Order.repository;


import com.food.Order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,String> {

    @Query(value = "SELECT * FROM Item where hotel_id =?1",nativeQuery = true)
    List<Item> FindByHotelId(String HotelId);

    Item findByItemId(String itemId);

    @Query(value = "SELECT amount FROM Item  where item_id =?1",nativeQuery = true)
    double FindByItemId(String itemId);

    @Query(value = "SELECT * FROM Item where offer>0",nativeQuery = true)
    List<Item> FindByOffers();




}
