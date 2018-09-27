package com.ptit.baobang.piospaapp.data.local.db_realm;

import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.model.Order;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class OrderRealm extends RealmObject implements Serializable {
    @PrimaryKey
    @RealmField(name = "order_id")
    private int orderId;

    @RealmField(name = "created_at")
    private String createdAt;

    @RealmField(name = "order_status_id")
    private int orderStatusId;

    @RealmField(name = "order_status_name")
    private String orderStatusName;

    @RealmField(name = "shipping_address")
    private String shippingAddress;

    @RealmField(name = "delivery_type")
    private String deliveryType;

    @RealmField(name = "payment_type")
    private String paymentType;

    @RealmField(name = "payment_type_description")
    private String paymentTypeDescription;

    @RealmField(name = "total")
    private int total;

    @RealmField(name = "tax_name")
    private String taxName;

    @RealmField(name = "tax_value")
    private int taxValue;

    @RealmField(name = "tax_unit")
    private String taxUnit;

    @RealmField(name = "ship")
    private int ship;

    @RealmField(name = "payment")
    private int payment;

    @RealmField(name = "customer_id")
    private int customerId;

    @RealmField(name = "customer_name")
    private String customerName;

    @RealmField(name = "customer_phone")
    private String customerPhone;

    @RealmField(name = "bookings")
    private RealmList<BookingDetailRealm> bookingDetails;

    @RealmField(name = "order_products")
    private RealmList<OrderProductRealm> orderProductRealms;

    public OrderRealm() {
    }

    public OrderRealm(Order order, RealmList<BookingDetailRealm> bookingDetails, RealmList<OrderProductRealm> orderProductRealms) {
        orderId = order.getOrderId();
        createdAt = order.getCreatedAt();
        orderStatusId = order.getOrderStatus().getOrderStatusId();
        orderStatusName = order.getOrderStatus().getOrderStatusName();
        shippingAddress = order.getAddressDelivery();

        if(order.getOrderDeliveryType() != null){
            deliveryType = order.getOrderDeliveryType().getOrderDeliveryTypeName();
        }else{
            deliveryType = "";
        }

        if(order.getOrderPaymentType() != null){
            paymentType = order.getOrderPaymentType().getOrderPaymentTypeName();
        }else{
            paymentType = "";
            paymentTypeDescription = "";
        }

        total = order.getTotal();
        taxName = order.getTax().getName();
        taxValue = order.getTax().getValue();
        taxUnit = order.getTax().getType();
        ship = order.getDeliveryCost();
        payment = order.getSubTotal();
        customerId = order.getCustomer().getCustomerId();
        customerName = order.getFullName();
        customerPhone = order.getPhone();
        this.bookingDetails = bookingDetails;
        this.orderProductRealms = orderProductRealms;

    }


    public OrderRealm(Order order, List<CartProductItem> cartProductItems, List<CartServicePriceItem> cartServicePriceItems) {
        orderId = order.getOrderId();
        createdAt = order.getCreatedAt();
        orderStatusId = order.getOrderStatus().getOrderStatusId();
        orderStatusName = order.getOrderStatus().getOrderStatusName();
        shippingAddress = order.getAddressDelivery();
        if (order.getOrderDeliveryType() != null) {
            deliveryType = order.getOrderDeliveryType().getOrderDeliveryTypeName();
        }else{
            deliveryType = "";
        }
        if(order.getOrderPaymentType() != null){
            paymentType = order.getOrderPaymentType().getOrderPaymentTypeName();
        }else{
            paymentType = "";
            paymentTypeDescription = "";
        }

        total = order.getTotal();
        taxName = order.getTax().getName();
        taxValue = order.getTax().getValue();
        taxUnit = order.getTax().getType();
        ship = order.getDeliveryCost();
        payment = order.getSubTotal();
        customerId = order.getCustomer().getCustomerId();
        customerName = order.getFullName();
        customerPhone = order.getPhone();
        orderProductRealms = new RealmList<>();
        bookingDetails = new RealmList<>();
        for (CartProductItem cpi : cartProductItems) {
            orderProductRealms.add(new OrderProductRealm(cpi));
        }
        for (CartServicePriceItem cspi : cartServicePriceItems) {
            bookingDetails.add(new BookingDetailRealm(cspi));
        }
    }

    public String getTaxUnit() {
        return taxUnit;
    }

    public void setTaxUnit(String taxUnit) {
        this.taxUnit = taxUnit;
    }

    public RealmList<BookingDetailRealm> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(RealmList<BookingDetailRealm> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public RealmList<OrderProductRealm> getOrderProductRealms() {
        return orderProductRealms;
    }

    public void setOrderProductRealms(RealmList<OrderProductRealm> orderProductRealms) {
        this.orderProductRealms = orderProductRealms;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeDescription() {
        return paymentTypeDescription;
    }

    public void setPaymentTypeDescription(String paymentTypeDescription) {
        this.paymentTypeDescription = paymentTypeDescription;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public int getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(int taxValue) {
        this.taxValue = taxValue;
    }

    public int getShip() {
        return ship;
    }

    public void setShip(int ship) {
        this.ship = ship;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
