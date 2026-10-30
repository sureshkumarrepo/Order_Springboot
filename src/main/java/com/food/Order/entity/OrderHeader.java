package com.food.Order.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders_header")
public class OrderHeader {
    @Id
    @Column(name = "orderHeaderId")
    private String orderHeaderId;

    @Column(name = "status")
    private String status;

    @Column(name = "totAmt")
    private double totAmt;

    @Column(name = "is_active")
    private boolean is_active;


    @Column(name = "deliveryBoyId")
    private String deliveryBoyId;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_on")
    private Date updatedOn;




}
