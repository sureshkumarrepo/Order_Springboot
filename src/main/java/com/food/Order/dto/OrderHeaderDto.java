package com.food.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderHeaderDto {
        private String orderHeaderId;
        private String hotelId;
        private String hotelName;
        private double totAmt;
        private boolean is_active;
        private String status;
        private String deliveryBoyId;
        private List<OrderDto> orderDtos;
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class OrderDto {
            private String orderId;
            private String itemId;
            private String hotelId;
            private double totalAmount;
            private boolean is_active;
            private double quantity;
            private double itemAmount;
            private String status;
        }
    }

