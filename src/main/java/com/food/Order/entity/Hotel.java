package com.food.Order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Entity
@Table(name = "hotel")
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Hotel {
    @Id
    @Column(name = "hotelId")
    private String hotelId;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "time")
    private String time;

    @Column(name = "address")
    private String address;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json", name = "image_url")
    private List<String> image_url;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @Access(AccessType.PROPERTY)
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JsonManagedReference
    private List<Item> item;


}