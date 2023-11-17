package com.Cafe.order.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.order.common.OrderState;
import com.Cafe.order.common.PaymentMethod;
import com.Cafe.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Table(name = "orders")
public class Order extends BaseEntity {
    @CreatedDate
    private LocalDateTime orderedTime;

    private long totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderMenu> orderMenus = new LinkedList<>();


}
