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
import com.ptit.baobang.piospaapp.data.cart.CartServicePriceItem;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import java.util.List;

public class ServiceCartComfirmAdapter extends RecyclerView.Adapter<ServiceCartComfirmAdapter.ViewHolder> {

    private Context mContext;
    List<CartServicePriceItem> mItems;

    public ServiceCartComfirmAdapter(Context mContext, List<CartServicePriceItem> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view  = inflater.inflate(R.layout.item_service_cart_comfirm, parent, false);
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

        public void bindView(CartServicePriceItem cartServicePriceItem) {
            ServicePrice servicePrice = cartServicePriceItem.getBookingItem().getServicePrice();
            String name = "", image = "";
            if(servicePrice.getServicePackage() != null){
                name = servicePrice.getServicePackage().getServicePackageName();
                image = servicePrice.getServicePackage().getImage();
            }else if(servicePrice.getService() != null){
                name = servicePrice.getService().getServiceName();
                image = servicePrice.getService().getImage();
            }

            txtName.setText(name);
            txtDate.setText(new StringBuilder(mContext.getString(R.string.date_booking) + ": " + DateTimeUtils.formatDate(cartServicePriceItem.getBookingItem().getSelectedDate(), DateTimeUtils.DATE_PATTERN_DDMMYY)));
            txtTime.setText(new StringBuilder(mContext.getString(R.string.time_booking) + ": " + DateTimeUtils.formatDate(cartServicePriceItem.getBookingItem().getSelectedDate(), DateTimeUtils.TIME_PATTERN)));
            txtPrice.setText(new StringBuilder(mContext.getString(R.string.price) + ": " + CommonUtils.formatToCurrency(servicePrice.getAllPrice())));
            txtQuanlity.setText(new StringBuilder(mContext.getString(R.string.quanlity)+": "+cartServicePriceItem.getNumberCustomer()));
            RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
            Glide.with(mContext).load(image)
                    .apply(options).into(img);
        }
    }
}
