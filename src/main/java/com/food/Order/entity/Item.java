package com.food.Order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "item",schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Item {
    @Id
    @Column(name = "itemId")
    private String itemId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "description")
    private String description;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json", name = "image_url")
    private List image_url;

    @Column(name = "offer")
    private double offer;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "Amount")
    private double Amount;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_on")
    private Date updatedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotelId")
    @JsonBackReference
    private Hotel hotel;
}
