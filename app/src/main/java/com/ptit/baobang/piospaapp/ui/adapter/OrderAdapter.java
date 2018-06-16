package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
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
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderAdapter<P extends BasePresenter> extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<Order> mItems;
    private OnItemClickListener mListen;
    private APIService mApiService;
    private P mPresenter;

    public OrderAdapter(Context context, List<Order> mItems, P mPresenter) {
        this.context = context;
        this.mItems = mItems;
        this.mPresenter = mPresenter;
        mApiService = ApiUtils.getAPIService();
    }

    public void setItemClickListen(OnItemClickListener mListen) {
        this.mListen = mListen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_order, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.bindView(mItems.get(position));
        holder.itemView.setOnClickListener(v->{
            if(mListen != null){
                mListen.onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtName, txtDate, txtTime, txtQuanlity, txtPrice, txtMore, txtTotal;
        LinearLayout layoutMore;
        ShimmerFrameLayout shimmerFrameLayout;

        Order order;

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
            shimmerFrameLayout = itemView.findViewById(R.id.shimmerLayout);
            layoutMore.setVisibility(View.GONE);
        }

        void bindView(Order order) {
            this.order = order;
            mPresenter.getCompositeDisposable().add(
                    mApiService.getProductAndBookingDetail(order.getOrderId())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
            );
        }

        private void handleError(Throwable throwable) {

        }

        private void handleResponse(EndPoint<OrderResponse> orderResponseEndPoint) {
            List<OrderProduct> orderProducts = orderResponseEndPoint.getData().getOrderProducts();
            List<BookingDetail> bookingDetails = orderResponseEndPoint.getData().getBookingDetails();
            bindData(order, orderProducts, bookingDetails);
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
            if(shimmerFrameLayout != null){
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        }
    }
}
