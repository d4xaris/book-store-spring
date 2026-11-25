package com.d4xaris.bookstore.model.enums;

import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {
    NEW, CONFIRMED, SHIPPED, DELIVERED, CANCELLED;

    private Set<OrderStatus> allowedNext;

    static {
        NEW.allowedNext       = EnumSet.of(CONFIRMED, CANCELLED);
        CONFIRMED.allowedNext = EnumSet.of(SHIPPED, CANCELLED);
        SHIPPED.allowedNext   = EnumSet.of(DELIVERED);
        DELIVERED.allowedNext = EnumSet.noneOf(OrderStatus.class);
        CANCELLED.allowedNext = EnumSet.noneOf(OrderStatus.class);
    }

    public boolean canTransitionTo(OrderStatus target) {
        return allowedNext.contains(target);
    }
}