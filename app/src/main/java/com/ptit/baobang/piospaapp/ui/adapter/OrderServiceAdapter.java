package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.local.db_realm.BookingDetailRealm;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

public class OrderServiceAdapter extends RecyclerView.Adapter<OrderServiceAdapter.ViewHolder> {

    private Context mContext;
    List<BookingDetailRealm> mItems;

    public OrderServiceAdapter(Context mContext, List<BookingDetailRealm> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_service_cart_comfirm, parent, false);
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
        TextView txtName, txtQuanlity, txtPrice, txtDate, txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtName = itemView.findViewById(R.id.txtName);
            txtQuanlity = itemView.findViewById(R.id.txtQuanlity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }

        public void bindView(BookingDetailRealm cartServicePriceItem) {
            String name = "", image = "";
            name = cartServicePriceItem.getServiceName();
            image = cartServicePriceItem.getServiceImage();

            txtName.setText(name);
            txtDate.setText(new StringBuilder(cartServicePriceItem.getDateBooking()));
            txtTime.setText(cartServicePriceItem.getTimeBooking());
            txtPrice.setText(new StringBuilder(mContext.getString(R.string.price) + ": " + CommonUtils.formatToCurrency(cartServicePriceItem.getPrice())));
            txtQuanlity.setText(new StringBuilder(mContext.getString(R.string.quanlity) + ": " + cartServicePriceItem.getNumberCustomer()));
            RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
            Glide.with(mContext).load(image)
                    .apply(options).into(img);
        }
    }
}
