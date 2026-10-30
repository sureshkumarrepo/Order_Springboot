package com.food.Order.controller;


import com.food.Order.io.BaseResponse;
import com.food.Order.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @PostMapping("/addImage")
    public ResponseEntity<BaseResponse> addImage(@RequestParam(value="file") List<MultipartFile> file) throws Exception{
        BaseResponse baseResponse= hotelService.AddImage(file);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/getAllImage")
    public ResponseEntity<BaseResponse>getAllImage() throws Exception {
        BaseResponse baseResponse=hotelService.getAllImage();
        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<BaseResponse> DeleteImage(@PathVariable String imageId) throws Exception {
        BaseResponse response = hotelService.DeleteImage(imageId);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/addHotel")
    public ResponseEntity<BaseResponse> addHotel(@RequestParam(value="file") MultipartFile file,
                                                 @RequestParam (value = "hotelName") String hotelName,
                                                 @RequestParam (value = "active") boolean active,
                                                 @RequestParam (value = "description") String description,
                                                 @RequestParam (value = "time") String time,
                                                 @RequestParam (value = "address") String address,
                                                 @RequestParam (value = "createdBy") String createdBy,
                                                 @RequestParam (value = "updatedBy") String updatedBy) throws Exception{
        BaseResponse baseResponse= hotelService.AddHotel(file,hotelName,active,description,time,address,createdBy,updatedBy);
        return ResponseEntity.ok(baseResponse);
    }
    @GetMapping("/getHotel/{HotelId}")
    public ResponseEntity<BaseResponse>getHotelId(@PathVariable String HotelId) throws Exception {
        BaseResponse baseResponse=hotelService.getHotelId(HotelId);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/getAllHotel")
    public ResponseEntity<BaseResponse>getAllHotel() throws Exception {
        BaseResponse baseResponse=hotelService.getAllHotel();
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateHotel/{HotelId}")
    public ResponseEntity<BaseResponse> Update(@RequestParam(value="file") MultipartFile file,
                                               @RequestParam (value = "hotelName") String hotelName,
                                               @RequestParam (value = "active") boolean active,
                                               @RequestParam (value = "description") String description,
                                               @RequestParam (value = "updatedBy") String updatedBy,@PathVariable String HotelId) throws Exception {
        BaseResponse response = hotelService.UpdateHotel(file,hotelName,active,description,updatedBy, HotelId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{HotelId}")
    public ResponseEntity<BaseResponse> Delete(@PathVariable String HotelId) throws Exception {
        BaseResponse response = hotelService.DeleteHotelm(HotelId);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/addItems/{HotelId}")
    public ResponseEntity<BaseResponse> addItems(@RequestParam(value="file") List<MultipartFile> file,
                                                 @RequestParam (value = "ItemName") String itemName,
                                                 @RequestParam (value = "active") boolean active,
                                                 @RequestParam (value = "description") String description,
                                                 @RequestParam (value = "createdBy") String createdBy,
                                                 @RequestParam(value = "Amount")double Amount,@PathVariable String HotelId,@RequestParam (value = "offer") Double offer) throws Exception{
        BaseResponse baseResponse= hotelService.AddItem(file,itemName,active,description,createdBy,Amount,HotelId,offer);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/getItem/{HotelId}")
    public ResponseEntity<BaseResponse>getItemId(@PathVariable String HotelId) throws Exception {
        BaseResponse baseResponse=hotelService.getItemId(HotelId);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("/getOffers")
    public ResponseEntity<BaseResponse>getOffers() throws Exception {
        BaseResponse baseResponse=hotelService.getOffers();
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/updateItem/{ItemId}")
    public ResponseEntity<BaseResponse> UpdateItem(@RequestParam(value="file") List<MultipartFile> file,
                                               @RequestParam (value = "ItemName") String ItemName,
                                               @RequestParam (value = "active") boolean active,
                                               @RequestParam (value = "description") String description,
                                               @RequestParam (value = "updatedBy") String updatedBy,
                                                   @RequestParam(value = "Amount")double Amount,@PathVariable String ItemId) throws Exception {
        BaseResponse response = hotelService.UpdateItem(file,ItemName,active,description,updatedBy,Amount, ItemId);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{ItemId}")
    public ResponseEntity<BaseResponse> DeleteItem(@PathVariable String ItemId) throws Exception {
        BaseResponse response = hotelService.DeleteItem(ItemId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getAllItems")
    public ResponseEntity<BaseResponse>getAllItems() throws Exception {
        BaseResponse baseResponse=hotelService.getAllItems();
        return ResponseEntity.ok(baseResponse);
    }
}
