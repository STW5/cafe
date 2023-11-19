package com.Cafe.supplier.entity;

import com.Cafe.config.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "supply")
public class Supply extends BaseEntity {
    private LocalDateTime suppliedDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

//    @OneToMany(mappedBy = "supply", orphanRemoval = true)
//    private List<SupplyItem> supplyItemList = new LinkedList<>();

    private boolean confirmed;
}
