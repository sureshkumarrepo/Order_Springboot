package com.food.Order.service;

import com.food.Order.dto.*;
import com.food.Order.entity.OrderHeader;
import com.food.Order.io.BaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    BaseResponse CreateUser(UserDto userDto) throws Exception;
    BaseResponse Login(UserDto userDto) throws Exception;
    BaseResponse orders(String userId, List<OrderHeaderDto> orderDtos);
    BaseResponse getUserId(String userId) throws Exception;
    BaseResponse updateUser(String userId,UserDto userDto)throws Exception;
    BaseResponse FilterItems(FilterDto filterDto)throws Exception;
    BaseResponse getOrders(String userId) throws Exception;
    BaseResponse getOrdersDeliveryBoy() throws Exception;
    BaseResponse updatePassword(String userId,UserDto userDto) throws Exception;
    BaseResponse updateFvtItem(String userId, FvtItemsDto fvtItemsDto)throws Exception;
    BaseResponse getFvtItem(String userId);
    BaseResponse AddCity(List<CityDto> cityDto);
    BaseResponse getCity() throws Exception;
    BaseResponse DeliveryStatus(String userId,String orderHeaderId, OrderHeader req) throws Exception;
    BaseResponse getOrdersDelivery(String userId,int req,int req1) throws Exception;
}
