package com.Cafe.order.entity;

import com.Cafe.config.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Table(name = "orders")
public class Order extends BaseEntity {
    private Date orderedTime;

    private long totalAmount;
}
