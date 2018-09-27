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
import com.ptit.baobang.piospaapp.data.local.db_realm.OrderProductRealm;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {

    private Context mContext;
    List<OrderProductRealm> mItems;

    public OrderProductAdapter(Context mContext, List<OrderProductRealm> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product_cart_comfirm, parent, false);
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
        TextView txtName, txtQuanlity, txtPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtQuanlity = itemView.findViewById(R.id.txtQuanlity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }

        void bindView(OrderProductRealm cartProductItem) {
            try {
                txtName.setText(cartProductItem.getProductName());
                txtPrice.setText(new StringBuilder(mContext.getString(R.string.price) + AppConstants.DOUBLE_DOT + CommonUtils.formatToCurrency(cartProductItem.getPrice())));
                txtQuanlity.setText(new StringBuilder(mContext.getString(R.string.quanlity) + AppConstants.DOUBLE_DOT + cartProductItem.getAmount()));
                RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
                Glide.with(mContext).load(cartProductItem.getProductImage())
                        .apply(options).into(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
