package com.food.Order.service.ServiceImpl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.food.Order.constants.MessageCodes;
import com.food.Order.entity.*;
import com.food.Order.io.BaseResponse;
import com.food.Order.io.StatusMessage;
import com.food.Order.repository.HotelRepository;
import com.food.Order.repository.ImageRepository;
import com.food.Order.repository.ItemRepository;
import com.food.Order.service.HotelService;
import com.food.Order.util.CommonUtils;
import com.food.Order.util.RandomIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {
    public static String saveFile=System.getProperty("user.dir")+"/uploads";

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public BaseResponse AddHotel(MultipartFile file, String HotelName, boolean active, String description,String time,String address, String createdBy, String updatedBy) throws Exception {
        File fileObj = convertMultiPartFileToFile(file);
        Hotel hotel=new Hotel();
        BeanUtils.copyProperties(HotelName,hotel);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        hotel.setHotelId(RandomIdGenerator.generateRandomAplhaNumericString(5));
        hotel.setImage_url(Arrays.asList("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName));
        hotel.set_active(true);
        String path=System.getProperty("user.dir");
        File folder = new File(path+"/image");
        InputStream inputStream=file.getInputStream();
        try {
            if (!folder.exists()) {
                folder.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(folder);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        hotel.setDescription(description);
        hotel.setTime(time);
        hotel.setAddress(address);
        hotel.setCreatedOn(LocalDateTime.now());
        hotel.setHotelName(HotelName);
        fileObj.delete();
        hotelRepository.save(hotel);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotel)
                .build();
    }

    @Override
    public BaseResponse AddImage(List<MultipartFile> file) {
        Image image=new Image();
        BeanUtils.copyProperties(file,image);
        image.setImageId(RandomIdGenerator.generateRandomAplhaNumericString(6));
        List<String> hotelList = new ArrayList<>();
        for(MultipartFile file1: file){
            File fileObj = convertMultiPartFileToFile(file1);
            String fileName = System.currentTimeMillis() + "_" + file1.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
            hotelList.add("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName);
        }
        image.setImage_url(hotelList);
        imageRepository.save(image);
        return BaseResponse.builder().status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build()).data(image).build();
    }

    @Override
    public BaseResponse getHotelId(String HotelId) throws Exception {
        Hotel hotel=hotelRepository.findByHotelId(HotelId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotel)
                .build();

    }

    @Override
    public BaseResponse getAllHotel() throws Exception {
        List<Hotel> hotels=hotelRepository.findAll();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotels)
                .build();
    }

    @Override
    public BaseResponse getAllImage() throws Exception {
        List<Image> imageList=imageRepository.findAll();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(imageList)
                .build();
    }

    @Override
    public BaseResponse getOffers() throws Exception {
        List<Item> items=itemRepository.FindByOffers();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(items)
                .build();
    }

    @Override
    public BaseResponse DeleteImage(String imageId) throws Exception {
        imageRepository.deleteById(imageId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Deleted successfully"+imageId)
                .build();
    }

    @Override
    public BaseResponse UpdateHotel(MultipartFile file, String HotelName, boolean active, String description, String updatedBy, String HotelId) throws Exception {
        Hotel hotel=hotelRepository.findByHotelId(HotelId);
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        hotel.setImage_url(Arrays.asList("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName));
        hotel.set_active(true);
        hotel.setDescription(description);
        hotel.setCreatedOn(LocalDateTime.now());
        hotel.setHotelName(HotelName);
        fileObj.delete();
        hotelRepository.save(hotel);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotel)
                .build();

    }

    @Override
    public BaseResponse DeleteHotelm(String HotelId) throws Exception {
         hotelRepository.deleteById(HotelId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Deleted successfully"+HotelId)
                .build();
    }

    @Override
    public BaseResponse AddItem(List<MultipartFile> file,String itemName, boolean active, String description, String createdBy,double Amount,String HotelId,Double offer) throws Exception {
        Hotel hotel=hotelRepository.findByHotelId(HotelId);
        if (!CommonUtils.checkIsNullOrEmpty(hotel)){
            Item item=new Item();
            item.setItemId(RandomIdGenerator.generateRandomAplhaNumericString(6));
            item.setActive(true);
            item.setAmount(Amount);
            item.setHotelName(hotel.getHotelName());
            item.setCreatedOn(LocalDateTime.now());
            item.setDescription(description);
            item.setItemName(itemName);
            item.setOffer(offer);
            item.setHotel(hotel);
            List<Item>items=hotel.getItem();
            List<String> hotelList = new ArrayList<>();
            for(MultipartFile file1: file){
                File fileObj = convertMultiPartFileToFile(file1);
                String fileName = System.currentTimeMillis() + "_" + file1.getOriginalFilename();
                s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
                hotelList.add("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName);
            }
            item.setImage_url(hotelList);
            items.add(item);
            hotel.setItem(items);
            hotelRepository.save(hotel);
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data(item)
                    .build();

        }
        return null;
    }

    @Override
    public BaseResponse getItemId(String HotelId) throws Exception {
List<Item>items=itemRepository.FindByHotelId(HotelId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(items)
                .build();
    }

    @Override
    public BaseResponse UpdateItem(List<MultipartFile> file,String ItemName, boolean active, String description, String updatedBy,double Amont, String ItemId) throws Exception {
        Item item=itemRepository.findByItemId(ItemId);
        List<String> hotelList = new ArrayList<>();
        for(MultipartFile file1: file){
            File fileObj = convertMultiPartFileToFile(file1);
            String fileName = System.currentTimeMillis() + "_" + file1.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
            hotelList.add("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName);
        }
        item.setActive(true);
        item.setDescription(description);
        item.setCreatedOn(LocalDateTime.now());
        item.setItemName(ItemName);
        item.setAmount(Amont);
        item.setImage_url(hotelList);
        itemRepository.save(item);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(item)
                .build();
    }

    @Override
    public BaseResponse DeleteItem(String ItemId) throws Exception {
        itemRepository.deleteById(ItemId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Deleted successfully"+ItemId)
                .build();
    }

    @Override
    public BaseResponse getAllItems() throws Exception {
        List<Item> itemList=itemRepository.findAll();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(itemList)
                .build();
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
