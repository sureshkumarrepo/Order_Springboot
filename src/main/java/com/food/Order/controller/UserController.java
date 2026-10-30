package com.food.Order.controller;

import com.food.Order.dto.*;
import com.food.Order.entity.OrderHeader;
import com.food.Order.io.BaseResponse;
import com.food.Order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse> CreateUser(@RequestBody UserDto userDto) throws Exception
    {
        BaseResponse response=userService.CreateUser(userDto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> Login(@RequestBody UserDto userDto) throws Exception {
        BaseResponse response = userService.Login(userDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/orders/{userId}")
    public ResponseEntity<BaseResponse> orders(@PathVariable String userId, @RequestBody List<OrderHeaderDto> orderDto) throws Exception {
        BaseResponse response = userService.orders(userId, orderDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<BaseResponse>getUserId(@PathVariable String userId) throws Exception {
        BaseResponse baseResponse=userService.getUserId(userId);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/updateUser/{userId}")
    public ResponseEntity<BaseResponse>updateUser(@PathVariable String userId,@RequestBody UserDto userDto) throws Exception{
        BaseResponse baseResponse=userService.updateUser(userId,userDto);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/filterItems")
    public ResponseEntity<BaseResponse> FilterItems(@RequestBody FilterDto filterDto) throws Exception {
        BaseResponse response = userService.FilterItems(filterDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getOrders/{userId}")
    public ResponseEntity<BaseResponse>getOrders(@PathVariable String userId) throws Exception {
        BaseResponse baseResponse=userService.getOrders(userId);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/getOrders/deliveryBoy")
    public ResponseEntity<BaseResponse>getOrdersDeliveryBoy() throws Exception {
        BaseResponse baseResponse=userService.getOrdersDeliveryBoy();
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updatePassword/{userId}")
    public ResponseEntity<BaseResponse>updatePassword(@PathVariable String userId,@RequestBody UserDto userDto) throws Exception{
        BaseResponse baseResponse=userService.updatePassword(userId,userDto);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/updateFvtItem/{userId}")
    public ResponseEntity<BaseResponse>updateFvtItem(@PathVariable String userId,@RequestBody FvtItemsDto fvtItemsDto) throws Exception {
        BaseResponse baseResponse = userService.updateFvtItem(userId, fvtItemsDto);
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/getFvtItem/{userId}")
    public ResponseEntity<BaseResponse>getFvtItem(@PathVariable String userId) throws Exception {
        BaseResponse baseResponse=userService.getFvtItem(userId);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/addCity")
    public ResponseEntity<BaseResponse>addCity(@RequestBody List<CityDto> req) throws Exception{
        BaseResponse response=userService.AddCity(req);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getCity")
    public ResponseEntity<BaseResponse> getCity() throws Exception {
        BaseResponse response = userService.getCity();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/deliveryUpdate/{userId}/{orderHeaderId}")
    public ResponseEntity<BaseResponse> deliveryUpdate(@PathVariable String orderHeaderId, @PathVariable String userId, @RequestBody OrderHeader req) throws Exception
    {
        BaseResponse response=userService.DeliveryStatus(userId,orderHeaderId,req);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deliveryUpdate/{userId}/{req}/{req1}")
    public ResponseEntity<BaseResponse> getOrdersDelivery(@PathVariable String userId,@PathVariable int req,@PathVariable int req1) throws Exception
    {
        BaseResponse response=userService.getOrdersDelivery(userId,req,req1);
        return ResponseEntity.ok(response);
    }
}
