package com.Cafe.order.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.order.common.OrderState;
import com.Cafe.user.entity.User;
import lombok.*;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
