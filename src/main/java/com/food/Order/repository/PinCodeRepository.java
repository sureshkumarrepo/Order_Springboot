package com.food.Order.repository;

import com.food.Order.entity.PinCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinCodeRepository extends JpaRepository<PinCode, String> {
    PinCode findByPinCode(String pinCode);
}
