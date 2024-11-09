package com.storeservice.repository;

import com.storeservice.domain.entity.OrderEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("""
            SELECT o
            FROM OrderEntity o
            WHERE (cast(:minDate as date) IS NULL OR o.orderDate >= :minDate)
            AND (cast(:maxDate as date) IS NULL OR o.orderDate <= :maxDate)
            """)
    List<OrderEntity> findByDate(@Param("minDate") LocalDateTime minDate,
                                 @Param("maxDate") LocalDateTime maxDate,
                                 Sort sort);
}
