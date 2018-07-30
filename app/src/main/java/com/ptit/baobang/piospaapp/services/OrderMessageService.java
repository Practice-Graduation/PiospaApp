package com.ptit.baobang.piospaapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.data.local.helper.OrderHelper;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.ApiUtils;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.activities.order.OrderActivity;
import com.ptit.baobang.piospaapp.ui.activities.order_detail.OrderDetailActivity;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.SharedPreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderMessageService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    APIService apiService;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            showMessage(remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            String orderId = remoteMessage.getData().get("message");
            updateOrder(orderId, new OrderCallBack() {
                @Override
                public void onSuccess(Order order) {

                    OrderRealm orderRealm = OrderHelper.getOrderById(order.getOrderId());
                    orderRealm.setOrderStatusId(order.getOrderStatus().getOrderStatusId());
                    orderRealm.setOrderStatusName(order.getOrderStatus().getOrderStatusName());
                    OrderHelper.saveOrder(orderRealm);
                    Intent intent = new Intent(OrderMessageService.this, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.ORDER, order.getOrderId());
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    showMessage("Đơn hàng số " + orderId + " đã được cập nhật", "Thông báo từ hệ thống Spa", intent);
                }

                @Override
                public void onFailed(String error) {
                    Log.e("Service", error);
                    Intent intent = new Intent(OrderMessageService.this, OrderActivity.class);
                    SharedPreferenceUtils.saveIsUpdateOrder(OrderMessageService.this, false);
                    showMessage("Đơn hàng số " + orderId + " đã được cập nhật", "Thông báo từ hệ thống Spa", intent);
                }
            });
        }
    }

    private void showMessage(String body, String title, Intent intent) {

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_notify)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setTicker(body)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void showMessage(String body) {
        Intent intent = new Intent(OrderMessageService.this, OrderActivity.class);
        showMessage(body, getString(R.string.message), intent);
    }

    private void updateOrder(String orderId, OrderCallBack orderCallBack) {
        apiService = ApiUtils.getAPIService();
        apiService.getOrderByCode(orderId).enqueue(new Callback<EndPoint<Order>>() {
            @Override
            public void onResponse(Call<EndPoint<Order>> call, Response<EndPoint<Order>> response) {
                orderCallBack.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<EndPoint<Order>> call, Throwable t) {
                orderCallBack.onFailed(t.getMessage());
            }
        });
    }
}
