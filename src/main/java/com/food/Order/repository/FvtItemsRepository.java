package com.food.Order.repository;

import com.food.Order.entity.FvtItems;
import com.food.Order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FvtItemsRepository extends JpaRepository<FvtItems, String> {
    @Query(value = "SELECT * FROM public.fvt_items where item_id =?1 and user_id =?2",nativeQuery = true)
    FvtItems fvtId(String itemId,String userId);

    @Query(value = "SELECT * FROM public.fvt_items where user_id =?1",nativeQuery = true)
    List<FvtItems> FindByUserId(String userId);
}
