package com.ptit.baobang.piospaapp.ui.activities.payment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ptit.baobang.piospaapp.data.cart.BookingItem;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.model.Customer;
import com.ptit.baobang.piospaapp.data.model.District;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryStatus;
import com.ptit.baobang.piospaapp.data.model.OrderDeliveryType;
import com.ptit.baobang.piospaapp.data.model.OrderPaymentType;
import com.ptit.baobang.piospaapp.data.model.OrderStatus;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.Province;
import com.ptit.baobang.piospaapp.data.model.Ward;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.CartItemProduct;
import com.ptit.baobang.piospaapp.data.network.model_request.CartItemService;
import com.ptit.baobang.piospaapp.data.network.model_request.CartShopping;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderBodyRequest;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentPresenter extends BasePresenter implements IPaymentPresenter {

    private static String TAG = "PaymentPresenter";

    private IPaymentView mView;
    private Context mContext;

    PaymentPresenter(IPaymentView mView, Context context) {
        this.mView = mView;
        this.mContext = context;
    }

    @Override
    public void clickButtonNext(int currentStep, String name,
                                String phone, Province mProvince,
                                District mDistrict, Ward mWard,
                                String address, OrderDeliveryType mDeliveryType,
                                OrderPaymentType mPaymentType) {
        switch (currentStep) {
            case 0:
                if(!checkDeliveryInfoInput(name, phone, mProvince,
                        mDistrict, mWard, address)) {
                  return;
                }
                break;
            case 1:
                if(!checkPaymentInput(mDeliveryType, mPaymentType)) {
                    return;
                }
                break;
            case 2:
                createOrder(name, phone, mProvince, mDistrict, mWard,
                        address, mDeliveryType, mPaymentType);
                break;
        }
        currentStep++;
        mView.nextStep(currentStep);
    }

    private void createOrder(String name, String phone, Province mProvince,
                             District mDistrict, Ward mWard, String address,
                             OrderDeliveryType mDeliveryType,
                             OrderPaymentType mPaymentType) {

        String deliveyAddress = address + ", " + mWard.getType() + " " + mWard.getName()
                + "," + mDistrict.getType() + " " + mDistrict.getName()
                + ", " + mProvince.getType() + " " + mProvince.getName();

        Customer customer = SharedPreferenceUtils.getUser(mContext);
        Order order = new Order();

        order.setFullName(name);
        order.setPhone(phone);
        order.setCustomer(customer);
        order.setAddress(customer.getFullAddress());
        order.setAddressDelivery(deliveyAddress);
        order.setOrderPaymentType(mPaymentType);
        order.setOrderDeliveryType(mDeliveryType);
        order.setDeliveryCost(mDeliveryType.getPrice());
        // to get default value was define on database
        order.setOrderStatus(new OrderStatus());
        order.setOrderDeliveryStatus(new OrderDeliveryStatus());

        List<CartItemProduct> itemProducts = getCartItemProducts();
        List<CartItemService> itemServices = getCartItemServices();
        CartShopping cartShopping = new CartShopping();
        cartShopping.setCartItemServices(itemServices);
        cartShopping.setCartItemProducts(itemProducts);

        OrderBodyRequest orderBodyRequest = new OrderBodyRequest();
        orderBodyRequest.setOrder(order);
        orderBodyRequest.setCartShopping(cartShopping);

        mApiService.createOrder(orderBodyRequest).enqueue(new Callback<EndPoint<Order>>() {
            @Override
            public void onResponse(Call<EndPoint<Order>> call, Response<EndPoint<Order>> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatusCode() == 200){
                        mView.doneStep();
                        mView.showMessage("Đặt hàng thành công");
                    }else{
                        mView.showMessage("Đặt hàng thất bại");
                        Log.e(TAG, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<EndPoint<Order>> call, Throwable t) {
                mView.showMessage("Kiểm tra lại kết nối");
                Log.e(TAG, t.getMessage());
            }
        });


    }

    private boolean checkPaymentInput(OrderDeliveryType mDeliveryType,
                                      OrderPaymentType mPaymentType) {
        if(mDeliveryType == null){
            mView.showMessage("Vui lòng chọn hình thức giao hàng");
            return  false;
        }
        if(mPaymentType == null){
            mView.showMessage("Vui lòng chọn hình thức thanh toán");
            return  false;
        }
        return true;
    }

    private boolean checkDeliveryInfoInput(String name, String phone, Province mProvince, District mDistrict, Ward mWard, String address) {
        if (name == null || name.isEmpty()) {
            mView.showMessage("Vui lòng nhập vào họ và tên");
            return false;
        }
        if (phone == null || phone.isEmpty()) {
            mView.showMessage("Vui lòng nhập vào số điện thoại");
            return false;
        }
        if (mProvince == null) {
            mView.showMessage("Vui lòng chọn tỉnh/thành phố");
            return false;
        }
        if (mDistrict == null) {
            mView.showMessage("Vui lòng chọn quận/huyện");
            return false;
        }
        if (mWard == null) {
            mView.showMessage("Vui lòng chọn phường/xã");
            return false;
        }
        if (address == null || address.isEmpty()) {
            mView.showMessage("Vui lòng nhập địa chỉ cụ thể");
            return false;
        }
        return true;
    }

    @Override
    public void clickTextViewProvince() {
        mView.openDialogProvince();
    }

    @Override
    public void clickTextViewDistrict(Province province) {
        mView.openDialogDistrict(province);
    }

    @Override
    public void clickTextViewWard(District mDistrict) {
        mView.openDialogWard(mDistrict);
    }

    @Override
    public void loadDeliveryType() {
        mApiService.getAllOrderDeliveryType().enqueue(new Callback<EndPoint<List<OrderDeliveryType>>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<List<OrderDeliveryType>>> call,
                                   @NonNull Response<EndPoint<List<OrderDeliveryType>>> response) {
                if (response.isSuccessful()) {
                    List<OrderDeliveryType> deliveryTypes = response.body().getData();
                    mView.updateRVOrderDeliveryType(deliveryTypes);
                }
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<List<OrderDeliveryType>>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void loadPaymentType() {
        mApiService.getAllOrderPaymentType().enqueue(new Callback<EndPoint<List<OrderPaymentType>>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<List<OrderPaymentType>>> call,
                                   @NonNull Response<EndPoint<List<OrderPaymentType>>> response) {
                if (response.isSuccessful()) {
                    List<OrderPaymentType> deliveryTypes = response.body().getData();
                    mView.updateRVOrderPaymentType(deliveryTypes);
                }
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<List<OrderPaymentType>>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void attachData(String name, String phone, Province mProvince,
                           District mDistrict, Ward mWard, String address,
                           OrderDeliveryType mDeliveryType,
                           OrderPaymentType mPaymentType) {
        mView.showData(name, phone, mProvince.getName(), mDistrict.getName(),
                mWard.getName(), address, mDeliveryType.getOrderDeliveryTypeName(),
               mPaymentType.getOrderPaymentTypeName(), mPaymentType.getOrderPaymentTypeDescription());
    }

    @Override
    public void loadCartItem() {
        List<CartProductItem> cartProductItems = getCartProductItems();
        List<CartServicePriceItem> cartServicePriceItems = getCartServiceItems();
        mView.updateRVProduct(cartProductItems);
        mView.updateRVService(cartServicePriceItems);
    }

    @Override
    public void attachDataForInput(Context baseContext) {
        Customer customer = SharedPreferenceUtils.getUser(baseContext);
        mView.showDataForInput(customer);
    }

    private List<CartProductItem> getCartProductItems() {

        List<CartProductItem> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();
        Map<Product, Integer> itemMap = cart.getItemWithQuantityProduct();

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            CartProductItem cartItem = new CartProductItem();
            cartItem.setProduct(entry.getKey());
            cartItem.setQuanlity(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private List<CartServicePriceItem> getCartServiceItems() {
        List<CartServicePriceItem> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();

        Map<BookingItem, Integer> itemMap = cart.getItemWithQuantityServices();

        for (Map.Entry<BookingItem, Integer> entry : itemMap.entrySet()) {
            CartServicePriceItem cartItem = new CartServicePriceItem();
            cartItem.setBookingItem( entry.getKey());
            cartItem.setNumberCustomer(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private List<CartItemProduct> getCartItemProducts() {
        List<CartItemProduct> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();
        Map<Product, Integer> itemMap = cart.getItemWithQuantityProduct();

        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            CartItemProduct cartItem = new CartItemProduct();
            cartItem.setProductId(entry.getKey().getProductId());
            cartItem.setNumber(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private List<CartItemService> getCartItemServices() {
        List<CartItemService> cartItems = new ArrayList<>();
        Cart cart = CartHelper.getCart();

        Map<BookingItem, Integer> itemMap = cart.getItemWithQuantityServices();

        for (Map.Entry<BookingItem, Integer> entry : itemMap.entrySet()) {
            CartItemService cartItem = new CartItemService();
            cartItem.setProductId(entry.getKey().getServicePrice().getServicePriceId());
            cartItem.setDateBooking(entry.getKey().getSelectedDate().toString());
            cartItem.setNumber(entry.getValue());
            cartItems.add(cartItem);
        }
        return cartItems;
    }
}
