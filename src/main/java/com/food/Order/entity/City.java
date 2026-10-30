package com.food.Order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "city")
public class City {
    @Id
    @Column(name = "cityId")
    private String cityId;

    @Column(name = "cityName")
    private String cityName;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    @JsonManagedReference
    private List<PinCode> pinCodes;
}
