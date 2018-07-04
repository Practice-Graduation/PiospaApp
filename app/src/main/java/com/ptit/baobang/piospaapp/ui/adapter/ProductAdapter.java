package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.ptit.baobang.piospaapp.utils.VNCharacterUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable {

    private Context mContext;
    private List<Product> mFilterProducts;
    private List<Product> mProducts;
    private OnItemClickListener mListener;

    public ProductAdapter(Context mContext, List<Product> mProducts) {
        this.mContext = mContext;
        this.mFilterProducts = mProducts;
        this.mProducts = new ArrayList<>();
        this.mProducts.addAll(mFilterProducts);
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
        holder.binView(holder.itemView, mFilterProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilterProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new ProductFilter(this);
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

    private class ProductFilter extends Filter {
        ProductAdapter mmProductAdapter;
        public ProductFilter(ProductAdapter productAdapter) {
            mmProductAdapter = productAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterSeq = constraint.toString().toLowerCase();
            Log.e("mCus", mProducts.size() + "");
            Log.e("mFil", mFilterProducts.size() + "");
            Log.e("filterSeq", filterSeq);
            if (filterSeq.length() > 0) {
                ArrayList<Product> filter = new ArrayList<>();
                for (Product product : mProducts) {
                    // the filtering itself:
                    if (checkContaintKey(product, filterSeq))
                        filter.add(product);
                }
                mFilterProducts.clear();
                mFilterProducts.addAll(filter);

            } else {
                // add all objects
                mFilterProducts.clear();
                mFilterProducts.addAll(mProducts);
            }
            FilterResults result = new FilterResults();
            result.values = mFilterProducts;
            result.count = mFilterProducts.size();
            Log.e("result", result.count + "");
            return result;
        }

        private boolean checkContaintKey(Product products, String filterSeq) {
            if(products.getProductName().trim().toLowerCase().contains(filterSeq.trim())){
                return true;
            }
            if(String.valueOf(products.getPrice()).trim().toLowerCase().contains(filterSeq.trim())){
                return true;
            }
            String noSign = VNCharacterUtils.removeAccent(products.getProductName()).toLowerCase().trim();
            String filNoSign = VNCharacterUtils.removeAccent(filterSeq).toLowerCase().trim();
            if(noSign.contains(filNoSign)){
                return true;
            }

            return false;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    }
}
