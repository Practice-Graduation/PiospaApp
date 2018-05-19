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
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.CommonUtils;
import com.ptit.baobang.piospaapp.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context mContext;
    private List<Product> mProducts;
    private OnItemClickListener mListener;

    public ProductAdapter(Context mContext, List<Product> mProducts) {
        this.mContext = mContext;
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.binView(holder.itemView, mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;

        ProductHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemSelected(getAdapterPosition());
                }
            });

        }

        void binView(View itemView, Product product) {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.paceholder)
                    .error(R.drawable.error)
                    .fitCenter();
            Glide.with(mContext)
                    .load(product.getImage())
                    .apply(options)
                    .into(img);
            txtName.setText(product.getProductName());
            txtPrice.setText(CommonUtils.formatToCurrency(product.getPrice()));
            itemView.getLayoutParams().width = (ScreenUtils.getScreenWidth(mContext) - AppConstants.SPAN_COUNT * AppConstants.MARGIN) / AppConstants.SPAN_COUNT;
            itemView.getLayoutParams().height =
                    itemView.getLayoutParams().height > ViewGroup.LayoutParams.WRAP_CONTENT ?
                            itemView.getLayoutParams().height : ViewGroup.LayoutParams.WRAP_CONTENT;
        }
    }

    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }
}
