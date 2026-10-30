package com.food.Order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fvtItems")
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class FvtItems {
    @Id
    @Column(name = "fvtId")
    private String fvtId;

    @Column(name = "itemId")
    private String itemId;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "itemName")
    private String itemName;


//    @Column(name = "image_url")
//    private List<String> image_url;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json", name = "image_url")
    private List<String> image_url;

    @Column(name = "description")
    private String description;

    @Column(name = "offer")
    private double offer;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "Amount")
    private double Amount;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;


}
