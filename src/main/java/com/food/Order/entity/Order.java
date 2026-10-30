package com.food.Order.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Order {
    @Id
    @Column(name = "orderId")
    private String orderId;

    @Column(name = "orderDetailsId")
    private String orderDetailsId;

    @Column(name = "itemId")
    private String itemId;

    @Column(name = "hotelId")
    private String hotelId;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "totalAmount")
    private double totalAmount;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "quantity")
    private double quantity;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json", name = "image_url")
    private List<String> image_url;

    @Column(name = "itemAmount")
    private double itemAmount;


    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;


}
