package com.food.Order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "district")
public class PinCode {
    @Id
    @Column(name = "pinCodeId")
    private String pinCodeId;

    @Column(name = "pinCode")
    private String pinCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId")
    @JsonBackReference
    private City city;
}
