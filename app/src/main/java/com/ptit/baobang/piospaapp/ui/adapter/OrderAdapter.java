package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.BookingDetail;
import com.ptit.baobang.piospaapp.data.model.Order;
import com.ptit.baobang.piospaapp.data.model.OrderProduct;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.Service;
import com.ptit.baobang.piospaapp.data.model.ServicePackage;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.ApiUtils;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.data.network.model_request.OrderResponse;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> mItems;

    private APIService mApiService;

    public OrderAdapter(Context context, List<Order> mItems) {
        this.context = context;
        this.mItems = mItems;

        mApiService = ApiUtils.getAPIService();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_order, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtName, txtDate, txtTime, txtQuanlity, txtPrice, txtMore, txtTotal;
        LinearLayout layoutMore;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtQuanlity = itemView.findViewById(R.id.txtQuanlity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtMore = itemView.findViewById(R.id.txtMore);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            layoutMore = itemView.findViewById(R.id.layoutMore);
        }

        void bindView(Order order) {

            mApiService.getProductAndBookingDetail(order.getOrderId()).enqueue(new Callback<EndPoint<OrderResponse>>() {
                @Override
                public void onResponse(Call<EndPoint<OrderResponse>> call, Response<EndPoint<OrderResponse>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatusCode() == 200) {
                            List<OrderProduct> orderProducts = response.body().getData().getOrderProducts();
                            List<BookingDetail> bookingDetails = response.body().getData().getBookingDetails();
                            bindData(order, orderProducts, bookingDetails);
                        }else {
                            Log.e("TAG", response.body().getMessage());
                        }
                    }else{
                        Log.e("TAG", "response not Successful");
                    }
                }

                @Override
                public void onFailure(Call<EndPoint<OrderResponse>> call, Throwable t) {

                }
            });


        }

        private void bindData(Order order, List<OrderProduct> orderProducts, List<BookingDetail> bookingDetails) {
            String name = "", imgStr = "", date = "",
                    time = "", quanlity = "", price = "Đơn giá: ",
                    more = "", total = "Tổng cộng: ";
            if (orderProducts.size() > 0) {
                OrderProduct orderProduct = orderProducts.get(0);
                Product product = orderProduct.getProduct();
                name = product.getProductName();
                imgStr = product.getImage();
                txtDate.setVisibility(View.GONE);
                txtTime.setVisibility(View.GONE);
                quanlity = "Số lượng: "  + orderProduct.getNumber();
                price += CommonUtils.formatToCurrency(orderProduct.getPrice());
            } else {
                if (bookingDetails.size() > 0) {
                    BookingDetail detail = bookingDetails.get(0);
                    ServicePrice servicePrice = detail.getServicePrice();
                    if (servicePrice.getService() != null) {
                        Service service = servicePrice.getService();
                        name = service.getServiceName();
                        imgStr = service.getImage();
                    } else if (servicePrice.getService() != null) {
                        ServicePackage servicePackage = servicePrice.getServicePackage();
                        name = servicePackage.getServicePackageName();
                        imgStr = servicePackage.getImage();
                    }
                    date = detail.getDateBooking();
                    time = detail.getTimeStart();
                    quanlity = "Số khách" + detail.getNumber() + "";
                    price += CommonUtils.formatToCurrency(detail.getServicePrice().getAllPrice());
                }
            }
            int totalNumber = orderProducts.size() + bookingDetails.size();
            if (totalNumber <= 1) {
                layoutMore.setVisibility(View.GONE);
            } else {
                layoutMore.setVisibility(View.VISIBLE);
                more = "Xem thêm " + (totalNumber - 1) + " sản phẩm";
            }

            total +=  CommonUtils.formatToCurrency(order.getTotal());

            txtName.setText(name);
            txtTime.setText(time);
            txtDate.setText(date);
            txtQuanlity.setText(quanlity);
            txtMore.setText(more);
            txtPrice.setText(price);
            txtTotal.setText(total);


            RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
            Glide.with(context).load(imgStr).apply(options).into(img);
        }
    }
}
