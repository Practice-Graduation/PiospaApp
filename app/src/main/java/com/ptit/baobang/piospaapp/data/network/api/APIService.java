package com.ptit.baobang.piospaapp.data.network.api;

import com.ptit.baobang.piospaapp.data.model.BookingDetail;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.data.model.OrderPaymentType;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.Service;
import com.ptit.baobang.piospaapp.data.model.ServiceGroup;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.model_request.BookingDetailRequest;
import com.ptit.baobang.piospaapp.data.network.model_request.LoginRequest;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderBodyRequest;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderCustomerBody;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    //---------------PRODUCT--------------------------

    @GET("product/group")
    Observable<EndPoint<List<ProductGroup>>> getAllProductGroup();

    @GET("product/group/{groupId}/products")
    Observable<EndPoint<List<Product>>> getProductByGroupId(@Path("groupId") int groupId);

    @GET("product/{productId}")
    Call<EndPoint<Product>> getProductById(@Path("productId") int productId);

    //---------------SERVICE--------------------------

    @GET("service/group")
    Observable<EndPoint<List<ServiceGroup>>> getAllServiceGroup();

    @GET("service/price/group/{groupId}")
    Observable<EndPoint<List<ServicePrice>>> getServicePriceByGroupId(@Path("groupId") int groupId);

    @GET("service/{serviceId}")
    Call<EndPoint<Service>> getServiceById(@Path("serviceId") int serviceId);

    @GET("service/price/{servicePriceId}")
    Observable<EndPoint<ServicePrice>> getServicePriceById(@Path("servicePriceId") int servicePriceId);

    @GET("/service/packages/{packageId}")
    Observable<EndPoint<List<Service>>> getServiceByPackageId(@Path("packageId") int packageId);

    //---------------ROOM--------------------------
    @GET("room")
    Call<EndPoint<List<Room>>> getAllRoom();

    //---------------BOOKINNG DETAIL--------------------------
    @POST("detail/date")
    Observable<EndPoint<List<BookingDetail>>> getBookingDetailOnDayOfRoom(@Body BookingDetailRequest request);

    // ------------------CUSTOMEMR------------------------------------
    @POST("customer/login")
    Observable<EndPoint<Customer>> login(@Body LoginRequest loginRequest);

    @POST("customer")
    Observable<EndPoint<Customer>> register(@Body Customer customer);

    // ------------------ADDREsS------------------------------------
    @GET("province")
    Observable<EndPoint<List<Province>>> getAllProvince();

    @GET("province/{provinceId}/district")
    Observable<EndPoint<List<District>>> getDistrictByProvinceId(@Path("provinceId") int provinceid);

    @GET("district/{districtId}/ward")
    Observable<EndPoint<List<Ward>>> getWardtByDistrictId(@Path("districtId") int districtId);
    // ------------------ORDER------------------------------------
    @GET("order/delivery-type")
    Observable<EndPoint<List<OrderDeliveryType>>> getAllOrderDeliveryType();

    @GET("order/payment-type")
    Observable<EndPoint<List<OrderPaymentType>>> getAllOrderPaymentType();

    @GET("order/status")
    Observable<EndPoint<List<OrderStatus>>> getAllOrderStatuses();

    @POST("order")
    Observable<EndPoint<Order>> createOrder(@Body OrderBodyRequest orderBodyRequest);

    @POST("order/customer-order-status")
    Observable<EndPoint<List<Order>>> getOrderByStatus(@Body OrderCustomerBody orderCustomerBody);

    @GET("order/{orderId}/order-product-service-price")
    Observable<EndPoint<OrderResponse>> getProductAndBookingDetail(@Path("orderId") int orderId);

}
