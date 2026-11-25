package com.d4xaris.bookstore.repo;

import com.d4xaris.bookstore.model.Order;
import com.d4xaris.bookstore.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            select distinct o from Order o
            join fetch o.client
            left join fetch o.employee
            left join fetch o.bookItems bi
            left join fetch bi.book
            where o.client.email = :email
            order by o.orderDate desc
            """)
    List<Order> findDetailedByClientEmail(@Param("email") String email);

    @Query("""
            select distinct o from Order o
            join fetch o.client
            left join fetch o.employee
            left join fetch o.bookItems bi
            left join fetch bi.book
            order by o.orderDate desc
            """)
    List<Order> findAllDetailed();

    @Query("""
            select distinct o from Order o
            join fetch o.client
            left join fetch o.employee
            left join fetch o.bookItems bi
            left join fetch bi.book
            where o.status = :status
            order by o.orderDate desc
            """)
    List<Order> findDetailedByStatus(@Param("status") OrderStatus status);

    @Query("""
            select distinct o from Order o
            join fetch o.client
            left join fetch o.employee
            left join fetch o.bookItems bi
            left join fetch bi.book
            where o.id = :id
            """)
    Optional<Order> findDetailedById(@Param("id") Long id);
}