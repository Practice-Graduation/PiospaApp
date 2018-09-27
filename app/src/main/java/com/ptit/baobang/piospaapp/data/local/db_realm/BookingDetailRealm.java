package com.ptit.baobang.piospaapp.data.local.db_realm;

import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.model.BookingDetailObject;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import io.realm.RealmObject;
import io.realm.annotations.RealmField;

public class BookingDetailRealm extends RealmObject {

    @RealmField(name = "service_name")
    private String serviceName;

    @RealmField(name = "service_image")
    private String serviceImage;

    @RealmField(name = "date_booking")
    private String dateBooking;

    @RealmField(name = "time_booking")
    private String timeBooking;

    @RealmField(name = "number_customer")
    private int numberCustomer;

    @RealmField(name = "price")
    private int price;

    @RealmField(name = "room_id")
    private int roomId;

    @RealmField(name = "room_name")
    private String roomName;

    public BookingDetailRealm() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public BookingDetailRealm(CartServicePriceItem item) {
        if (item.getBookingItem().getServicePrice().getService() != null) {
            serviceName = item.getBookingItem().getServicePrice().getService().getServiceName();
            serviceImage = item.getBookingItem().getServicePrice().getService().getImage();
        } else if (item.getBookingItem().getServicePrice().getServicePackage() != null) {
            serviceName = item.getBookingItem().getServicePrice().getServicePackage().getServicePackageName();
            serviceImage = item.getBookingItem().getServicePrice().getServicePackage().getImage();
        } else {
            serviceName = "";
        }
        dateBooking = DateTimeUtils.formatDate(item.getBookingItem().getSelectedDate(), DateTimeUtils.DATE_PATTERN_DDMMYY);
        timeBooking = DateTimeUtils.formatDate(item.getBookingItem().getSelectedDate(), DateTimeUtils.TIME_PATTERN);
        numberCustomer = item.getNumberCustomer();
        price = item.getBookingItem().getServicePrice().getAllPrice();
        roomId = item.getBookingItem().getRoom().getRoomId();
        roomName = item.getBookingItem().getRoom().getRoomName();
    }

    public BookingDetailRealm(BookingDetailObject bookingDetailObject) {
        serviceName = bookingDetailObject.getServiceName();
        serviceImage = bookingDetailObject.getServiceImage();
        dateBooking = bookingDetailObject.getDateBooking();
        timeBooking = bookingDetailObject.getTimeBooking();
        numberCustomer = bookingDetailObject.getNumberCustomer();
        price = bookingDetailObject.getPrice();
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public void setTimeBooking(String timeBooking) {
        this.timeBooking = timeBooking;
    }

    public int getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(int numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
