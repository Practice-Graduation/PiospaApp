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
import com.ptit.baobang.piospaapp.data.model.Service;
import com.ptit.baobang.piospaapp.data.model.ServicePackage;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.DateTimeUtils;

import java.util.Date;
import java.util.List;

public class ShoppingCartServicePriceAdapter extends RecyclerView.Adapter<ShoppingCartServicePriceAdapter.ShoppingCartHolder> {

    private Context mContext;
    private List<CartServicePriceItem> mItems;
    private OnItemClickListener mAddListener, mRemoveListener;

    public ShoppingCartServicePriceAdapter(Context mContext, List<CartServicePriceItem> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ShoppingCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_serivce_shopping_cart, parent, false);
        return new ShoppingCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartHolder holder, int position) {
        holder.bindView(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ShoppingCartHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txtName, txtDate, txtTime,
                txtRoom, txtPrice, txtQuanlity, txtTotal;
        ImageView btnAdd, btnRemove;

        public ShoppingCartHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuanlity = itemView.findViewById(R.id.txtQuanlity);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }

        public void bindView(CartServicePriceItem cartServicePriveItem) {
            ServicePrice servicePrice = cartServicePriveItem.getBookingItem().getServicePrice();
            Date selectedDate =  cartServicePriveItem.getBookingItem().getSelectedDate();
            String name = "", image = "";
            if(servicePrice.getService() != null){
                Service service = servicePrice.getService();
                name = service.getServiceName();
                image = service.getImage();
            }else if(servicePrice.getServicePackage() != null){
                ServicePackage servicePackage = servicePrice.getServicePackage();
                name = servicePackage.getServicePackageName();
                image = servicePackage.getImage();
            }

            txtName.setText(name);
            txtPrice.setText(new StringBuilder(mContext.getString(R.string.price) + ": " + CommonUtils.formatToCurrency(servicePrice.getAllPrice())));
            txtDate.setText(new StringBuilder(DateTimeUtils.formatDate(selectedDate, DateTimeUtils.DATE_PATTERN_DDMMYY)));
            txtTime.setText(new StringBuilder(DateTimeUtils.formatDate(selectedDate, DateTimeUtils.TIME_PATTERN)));
            txtQuanlity.setText(new StringBuilder(cartServicePriveItem.getNumberCustomer() + ""));
            txtTotal.setText(CommonUtils.formatToCurrency(cartServicePriveItem.getTotalItem()));
            RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
            Glide.with(mContext).load(image).apply(options).into(img);
            btnAdd.setOnClickListener(view -> {
                if(mAddListener != null){
                    mAddListener.onItemSelected(getAdapterPosition());
                }
            });
            btnRemove.setOnClickListener(view ->{
                if(mRemoveListener != null){
                    mRemoveListener.onItemSelected(getAdapterPosition());
                }
            });
        }
    }

    public void onSetAddClickListener(OnItemClickListener callBack){
        this.mAddListener = callBack;
    }

    public void onSetRemoveClickListener(OnItemClickListener callBack){
        this.mRemoveListener = callBack;
    }
}
