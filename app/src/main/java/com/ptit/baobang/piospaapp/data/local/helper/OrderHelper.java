package com.ptit.baobang.piospaapp.data.local.helper;

import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.data.local.listener.RealmCallBack;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class OrderHelper {
    private static final String TAG = "OrderHelper";

    public static boolean saveOrder(OrderRealm order) {

        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            realm.insertOrUpdate(order);
            realm.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<OrderRealm> getOrderByStatus(int customreId, int statusId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<OrderRealm> result = realm.where(OrderRealm.class)
                .equalTo("orderStatusId", statusId)
                .and()
                .equalTo("customerId", customreId)
                .findAll().sort("orderId", Sort.DESCENDING);
        List<OrderRealm> orderRealms = realm.copyFromRealm(result);
        realm.close();
        return orderRealms;
    }

    public static OrderRealm getOrderById(int orderId) {
        Realm realm = Realm.getDefaultInstance();
        OrderRealm result = realm.where(OrderRealm.class)
                .equalTo("orderId", orderId)
                .findFirst();
        OrderRealm orderRealm = realm.copyFromRealm(result);
        realm.close();
        return orderRealm;

    }

    public static void saveOrderList(List<OrderRealm> orderRealms, RealmCallBack callBack) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 -> realm1.insertOrUpdate(orderRealms),
                () -> {
                            callBack.onSuccess();
                            realm.close();
                      },
                error -> {
                        callBack.onFail(error.getMessage());
                        realm.close();
                });
        realm.close();

    }
}
