package com.food.Order.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "image",schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Image {
    @Id
    @Column(name = "imageId")
    private String imageId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json", name = "image_url")
    private List image_url;
}
