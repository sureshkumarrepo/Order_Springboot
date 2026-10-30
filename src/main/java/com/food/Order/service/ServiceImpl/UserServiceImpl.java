package com.food.Order.service.ServiceImpl;

import com.food.Order.constants.MessageCodes;
import com.food.Order.dto.*;
import com.food.Order.entity.*;
import com.food.Order.io.BaseResponse;
import com.food.Order.io.StatusMessage;
import com.food.Order.repository.*;
import com.food.Order.service.UserService;
import com.food.Order.util.CommonUtils;
import com.food.Order.util.JwtUtil;
import com.food.Order.util.RandomIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    FvtItemsRepository fvtItemsRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    PinCodeRepository pinCodeRepository;

    @Override
    public BaseResponse CreateUser(UserDto userDto) throws Exception {
        if(userRepository.existsByNumber(userDto.getNumber()))
            throw new IllegalArgumentException("This Number already exists");
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        user.setUserId(RandomIdGenerator.generateRandomAplhaNumericString(6));
        user.setCreatedOn(LocalDateTime.now());
        user.set_active(true);
        userRepository.save(user);
        return BaseResponse.builder().status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build()).data(user).build();
    }

    @Override
    public BaseResponse Login(UserDto userDto) throws Exception {
        User userDetails=userRepository.findByNumber(userDto.getNumber());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getNumber(),userDto.getPassword()));
        } catch (Exception ex) {
            throw new IllegalArgumentException("inavalid username/password");
        }
        UserDto userDto1=new UserDto();
        BeanUtils.copyProperties(userDetails,userDto1);
        userDto1.setPassword(null);
        userDto1.setToken(jwtUtil.generateToken(userDto.getNumber()));
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(userDto1)
                .build();
    }

    @Override
    public BaseResponse orders(String userId, List<OrderHeaderDto> orderDtos) {
        User user=userRepository.findByUserId(userId);
        if (!CommonUtils.checkIsNullOrEmpty(user)) {

            for (OrderHeaderDto orderDto : orderDtos) {

                OrderHeader orderHeader= new OrderHeader();
                orderHeader.setOrderHeaderId(RandomIdGenerator.generateRandomAplhaNumericString(6));
                orderHeader.set_active(true);
                orderHeader.setStatus("0");
                orderHeader.setTotAmt(orderDto.getTotAmt());
                List<Order> orders = new ArrayList<>();
                for (OrderHeaderDto.OrderDto orderDto1 : orderDto.getOrderDtos()) {
                    Item item = itemRepository.findByItemId(orderDto1.getItemId());
                    double item1 = itemRepository.FindByItemId(orderDto1.getItemId());
                    Order order = new Order();
                    double amt = item1 * orderDto1.getQuantity();
                    order.set_active(true);
                    order.setCreatedOn(LocalDateTime.now());
                    order.setOrderId(RandomIdGenerator.generateRandomAplhaNumericString(5));
                    order.setOrderDetailsId(orderHeader.getOrderHeaderId());
                    order.setTotalAmount(amt);
                    order.setHotelId(item.getHotel().getHotelId());
                    order.setHotelName(item.getHotelName());
                    order.setItemName(item.getItemName());
                    order.setItemAmount(item1);
                    order.setImage_url(item.getImage_url());
                    order.setItemId(orderDto1.getItemId());
                    order.setQuantity(orderDto1.getQuantity());
                    order.setUser(user);
                    orders.add(order);
                    user.setOrders(orders);
                    userRepository.save(user);
                }

                orderHeaderRepository.save(orderHeader);

            }


        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Order successfully placed")
                .build();

    }

    @Override
    public BaseResponse getUserId(String userId) throws Exception {
        User user=userRepository.findByUserId(userId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(user)
                .build();
    }

    @Override
    public BaseResponse updateUser(String userId, UserDto userDto) throws Exception {
        User user=userRepository.findByUserId(userId);
        BeanUtils.copyProperties(userDto,user, CommonUtils.getNullPropertyNames(userDto));
        userRepository.save(user);
        user.setPassword(null);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(user)
                .build();
    }

    @Override
    public BaseResponse FilterItems(FilterDto filterDto) throws Exception {

        List<Item> items=null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> book = cq.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();

        if(!CommonUtils.checkIsNullOrEmpty(filterDto.getItemName())) {
            predicates.add(cb.like(book.get("itemName"), "%"+filterDto.getItemName()+"%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Item> query = entityManager.createQuery(cq);
        items= query.getResultList();
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
    public BaseResponse getOrders(String userId) throws Exception {
        List<Order> response=null;
        String query = "";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userid",userId);
        String selectQuery = "select oh.*,o.hotel_name as hotelName,o.item_amount as itemAmount,o.item_name as itemName,o.quantity as quantity,o.total_amount as totalAmount from orders_header oh,orders o where oh.order_header_id = o.order_details_id and o.user_id=:userid";
        javax.persistence.Query result = entityManager.createNativeQuery(selectQuery + query.toString());
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            result.setParameter(entry.getKey(), entry.getValue());
        }
        response = result.setFirstResult((int)0).setMaxResults(10000).getResultList();
        System.out.println(result);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(response)
                .build();
    }

    @Override
    @Scheduled(fixedRate = 5000)
    @Query(nativeQuery = true)
    public BaseResponse getOrdersDeliveryBoy() throws Exception {
        List<Order> response=null;
         String query = "";
        Map<String, Object> paramMap = new HashMap<>();
        String selectQuery = "SELECT distinct oh.order_header_id,oh.status,o.hotel_name,o.item_name,o.quantity,oh.tot_amt,o.item_amount,o.created_on,u.name, u.address, u.number,u.city,u.pin_code FROM Orders_header oh, Orders o, User_details u WHERE oh.order_header_id = o.order_details_id AND o.user_id =u.user_id AND oh.status='0'";
        javax.persistence.Query result = entityManager.createNativeQuery(selectQuery + query.toString());
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            result.setParameter(entry.getKey(), entry.getValue());
        }
        response = result.setFirstResult((int) 0).setMaxResults(10000).getResultList();
        System.out.println(result);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(response)
                .build();
    }

    @Override
    public BaseResponse updatePassword(String userId, UserDto userDto) throws Exception {
        User user=userRepository.findByUserId(userId);
       // BeanUtils.copyProperties(userDto,user, CommonUtils.getNullPropertyNames(userDto));
        String old_Password=user.getPassword();
        if(userDto.getOldPassword().equals(old_Password))
        {
            user.setPassword(userDto.getPassword());
            userRepository.save(user);
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data("Please Check Old Password")
                    .build();
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Password Change Successfully")
                .build();
    }

    @Override
    public BaseResponse updateFvtItem(String userId, FvtItemsDto fvtItemsDto) throws Exception {
        User user=userRepository.findByUserId(userId);
        Item item =itemRepository.findByItemId(fvtItemsDto.getItemId());
        FvtItems fvtId=fvtItemsRepository.fvtId(fvtItemsDto.getItemId(),userId);
        FvtItems fvtItems=new FvtItems();
        List<FvtItems> fvtItemsList = new ArrayList<>();
        if (!CommonUtils.checkIsNullOrEmpty(fvtId)){
            fvtItemsRepository.deleteById(fvtId.getFvtId());
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data("Remove From Favorite Food")
                    .build();
        }
        else {
            fvtItems.setFvtId(RandomIdGenerator.generateRandomAplhaNumericString(6));
            fvtItems.setItemId(fvtItemsDto.getItemId());
            fvtItems.set_active(true);
            fvtItems.setAmount(item.getAmount());
            fvtItems.setDescription(item.getDescription());
            fvtItems.setHotelName(item.getHotelName());
            fvtItems.setOffer(item.getOffer());
            fvtItems.setItemName(item.getItemName());
            fvtItems.setImage_url(item.getImage_url());
            fvtItems.setUser(user);
            fvtItemsList.add(fvtItems);
            user.setFvtItems(fvtItemsList);
            userRepository.save(user);
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data("Added To Favorite Food")
                    .build();
        }
    }

    @Override
    public BaseResponse getFvtItem(String userId) {
        List<FvtItems> fvtItemsList=fvtItemsRepository.FindByUserId(userId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(fvtItemsList)
                .build();
    }

    @Override
    public BaseResponse AddCity(List<CityDto> cityDtos) {
        for (CityDto location : cityDtos) {
            City city = cityRepository.findByCityName(location.getCityName());
            if (CommonUtils.checkIsNullOrEmpty(city)) {
                City city1 = new City();
                city1.setCityId(RandomIdGenerator.generateRandomAplhaNumericString(5));
                city1.setCityName(location.getCityName());
                List<PinCode> pinCodes = new ArrayList<>();
                if (!CommonUtils.checkIsNullOrEmpty(location.getPinCodes())) {
                    for (CityDto.PinCode pinCode : location.getPinCodes()) {
                        PinCode pinCode1 = new PinCode();
                        pinCode1.setPinCodeId(RandomIdGenerator.generateRandomAplhaNumericString(5));
                        pinCode1.setPinCode(pinCode.getPinCode());
                        pinCode1.setCity(city1);
                        pinCodes.add(pinCode1);
                    }
                }
                city1.setPinCodes(pinCodes);
                cityRepository.save(city1);
            } else {
                List<PinCode> pinCodeList = city.getPinCodes();
                if (!CommonUtils.checkIsNullOrEmpty(location.getPinCodes())) {
                    for (CityDto.PinCode cities : location.getPinCodes()) {
                        PinCode pinCode = pinCodeRepository.findByPinCode(cities.getPinCode());
                        if (CommonUtils.checkIsNullOrEmpty(pinCode)) {
                            PinCode newCity = new PinCode();
                            newCity.setPinCodeId(RandomIdGenerator.generateRandomAplhaNumericString(5));
                            newCity.setPinCode(cities.getPinCode());
                            newCity.setCity(city);


                            pinCodeList.add(newCity);
                            city.setPinCodes(pinCodeList);
                            cityRepository.save(city);
                        }
                    }
                }
            }
        }
        List<City> stateList = cityRepository.findAll();
        stateList.stream().forEach(r -> r.getPinCodes().stream().forEach(r1 -> r1.setCity(null)));
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS_MSG)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_MSG)
                        .build())
                .data(stateList)
                .build();

    }

    @Override
    public BaseResponse getCity() throws Exception {
            Object response = null;
            List<City> cityList = cityRepository.findAll();
            cityList.stream().forEach(r -> r.getPinCodes().stream().forEach(r1 -> r1.setCity(null)));
            response = cityList;
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS_MSG)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_MSG)
                        .build())
                .data(response)
                .build();
    }
    @Override
    public BaseResponse DeliveryStatus(String userId,String orderId,OrderHeader orderHeader1) throws Exception {
        OrderHeader orderHeader=orderHeaderRepository.findByOrderHeaderId(orderId);
        User user1=userRepository.findByUserId(userId);
        if ((!CommonUtils.checkIsNullOrEmpty(orderHeader))&&(!CommonUtils.checkIsNullOrEmpty(user1))){
            orderHeader.setDeliveryBoyId(user1.getUserId());
            orderHeader.setStatus(orderHeader1.getStatus());
            orderHeaderRepository.save(orderHeader);
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data(orderHeader)
                    .build();

        }
        return null;
    }

    @Override
    public BaseResponse getOrdersDelivery(String userId,int req,int req1) throws Exception {
        List<Order> response=null;
        String query = "";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userid",userId);
        String selectQuery = "SELECT distinct oh.order_header_id,oh.status,o.hotel_name,o.item_name,o.quantity,oh.tot_amt,o.item_amount,o.created_on,u.name, u.address, u.number,u.city,u.pin_code FROM Orders_header oh, Orders o, User_details u WHERE oh.order_header_id = o.order_details_id AND o.user_id =u.user_id AND oh.delivery_boy_id=:userid order by o.created_on";
        javax.persistence.Query result = entityManager.createNativeQuery(selectQuery + query.toString());
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            result.setParameter(entry.getKey(), entry.getValue());
        }
        response = result.setFirstResult((int) req).setMaxResults(req1).getResultList();
        System.out.println(result);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(response)
                .build();
    }

}

