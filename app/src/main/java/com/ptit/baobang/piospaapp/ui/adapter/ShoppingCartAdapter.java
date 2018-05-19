package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.CartItem;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.CommonUtils;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartHolder> {

    private Context mContext;
    private List<CartItem> mCartItems;

    private OnItemClickListener mAddListener, mRemoveListener;

    public ShoppingCartAdapter(Context mContext, List<CartItem> mCartItems) {
        this.mContext = mContext;
        this.mCartItems = mCartItems;
    }

    @NonNull
    @Override
    public ShoppingCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_shopping_cart, parent, false);
        return new ShoppingCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartHolder holder, int position) {
            holder.binView(mCartItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mCartItems.size();
    }

    class ShoppingCartHolder extends RecyclerView.ViewHolder {

        ImageView img;
        ImageButton btnAdd, btnRemove;
        TextView txtName, txtQuanlity, txtPrice, txtTotal;

        ShoppingCartHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            txtName = itemView.findViewById(R.id.txtName);
            txtQuanlity = itemView.findViewById(R.id.txtQuanlity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtTotal = itemView.findViewById(R.id.txtTotal);
        }

        void binView(CartItem cartItem) {
            Product product = cartItem.getProduct();
            txtName.setText(product.getProductName());
            txtPrice.setText(new StringBuilder("Giá: " + CommonUtils.formatToCurrency(product.getPrice())));
            txtQuanlity.setText(new StringBuilder(cartItem.getQuanlity() + ""));
            txtTotal.setText(CommonUtils.formatToCurrency(cartItem.getTotalItem()));
            RequestOptions options = new RequestOptions().error(R.drawable.error).placeholder(R.drawable.paceholder);
            Glide.with(mContext).load(product.getImage()).apply(options).into(img);
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
