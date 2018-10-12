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
import com.ptit.baobang.piospaapp.data.cart.CartProductItem;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

public class ProductCartConfirmAdapter extends RecyclerView.Adapter<ProductCartConfirmAdapter.ViewHolder> {

    private Context mContext;
    List<CartProductItem> mItems;

    public ProductCartConfirmAdapter(Context mContext, List<CartProductItem> mItems) {
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

        void bindView(CartProductItem cartProductItem) {
            try {
                txtName.setText(cartProductItem.getProduct().getProductName());
                txtPrice.setText(new StringBuilder(mContext.getString(R.string.price) + AppConstants.DOUBLE_DOT + CommonUtils.formatToCurrency(cartProductItem.getProduct().getPrice())));
                txtQuanlity.setText(new StringBuilder(mContext.getString(R.string.quanlity) + AppConstants.DOUBLE_DOT + cartProductItem.getQuanlity()));
                RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
                Glide.with(mContext).load(cartProductItem.getProduct().getImage())
                        .apply(options).into(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
