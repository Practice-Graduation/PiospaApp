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
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.local.db_realm.BookingDetailRealm;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderProductRealm;
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderRealm;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

public class OrderAdapter<P extends BasePresenter> extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderRealm> mItems;
    private OnItemClickListener mListen;

    public OrderAdapter(Context context, List<OrderRealm> mItems) {
        this.context = context;
        this.mItems = mItems;
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
        holder.itemView.setOnClickListener(v -> {
            if (mListen != null) {
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
        TextView txtName, txtDate, txtTime, txtQuanlity, txtPrice, txtMore, txtTotal, txtStatus;
        LinearLayout layoutMore;

        OrderRealm order;

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
            layoutMore.setVisibility(View.GONE);
            txtStatus = itemView.findViewById(R.id.txtStatus);

        }

        private void bindView(OrderRealm order) {
            String name = "", imgStr = "", date = "",
                    time = "", quanlity = "", price = context.getString(R.string.product) + ": ",
                    more = "", total = context.getString(R.string.total) + ": ";
            if (order.getOrderProductRealms().size() > 0) {
                OrderProductRealm orderProduct = order.getOrderProductRealms().get(0);
                name = orderProduct.getProductName();
                imgStr = orderProduct.getProductImage();
                txtDate.setVisibility(View.GONE);
                txtTime.setVisibility(View.GONE);
                quanlity = context.getString(R.string.amount) + ": " + orderProduct.getAmount();
                price += CommonUtils.formatToCurrency(orderProduct.getPrice());
            } else {
                if (order.getBookingDetails().size() > 0) {
                    BookingDetailRealm detail = order.getBookingDetails().get(0);
                    name = detail.getServiceName();
                    imgStr = detail.getServiceImage();

                    date = detail.getDateBooking();
                    time = detail.getTimeBooking();

                    quanlity = context.getString(R.string.number_customer) + ": " + detail.getNumberCustomer() + "";
                    price += CommonUtils.formatToCurrency(detail.getPrice());
                }
            }
            int totalNumber = order.getOrderProductRealms().size() + order.getBookingDetails().size();
            if (totalNumber <= 1) {
                layoutMore.setVisibility(View.GONE);
            } else {
                layoutMore.setVisibility(View.VISIBLE);
                more = context.getString(R.string.seen_more) + " " + (totalNumber - 1) + " " + context.getString(R.string.lower_product);
            }

            total += CommonUtils.formatToCurrency(order.getTotal() + "");

            txtName.setText(name);
            txtTime.setText(time);
            txtDate.setText(date);
            txtQuanlity.setText(quanlity);
            txtMore.setText(more);
            txtPrice.setText(price);
            txtTotal.setText(total);
            txtStatus.setText(order.getOrderStatusName());

            RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
            Glide.with(context).load(imgStr).apply(options).into(img);

        }
    }
}
