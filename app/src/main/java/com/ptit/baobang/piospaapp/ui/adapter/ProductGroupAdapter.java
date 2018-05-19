package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.fragments.fragment_product.IProductFragmentView;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductGroupAdapter extends RecyclerView.Adapter<ProductGroupAdapter.ProductGroupHolder> {
    private static final String TAG = "ProductGroupAdapter";
    private Context mContext;
    private List<ProductGroup> mProductGroups;
    private APIService mApiService;
    private IProductFragmentView mIProductFragmentView;

    public ProductGroupAdapter(Context mContext, List<ProductGroup> mProductGroups, APIService mApiService) {
        this.mContext = mContext;
        this.mProductGroups = mProductGroups;
        this.mApiService = mApiService;
    }

    @NonNull
    @Override
    public ProductGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycle_product, parent, false);
        return new ProductGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGroupHolder holder, int position) {
        holder.binView(mProductGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductGroups.size();
    }
    public class ProductGroupHolder extends RecyclerView.ViewHolder {

        TextView txtGroupName;
        TextView txtMore;
        RecyclerView rvGroupProduct;

        ProductGroupHolder(View itemView) {
            super(itemView);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);
            rvGroupProduct = itemView.findViewById(R.id.rvProducts);
            txtMore = itemView.findViewById(R.id.txtMore);
        }

        void binView(ProductGroup productGroup) {
            txtGroupName.setText(productGroup.getProductGroupName());
            List<Product> products = new ArrayList<>();
            ProductAdapter adapter = new ProductAdapter(mContext, products);
            rvGroupProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            rvGroupProduct.setAdapter(adapter);
            mApiService.getProductByGroupId(productGroup.getProductGroupId()).enqueue(new Callback<EndPoint<List<Product>>>() {
                @Override
                public void onResponse(@NonNull Call<EndPoint<List<Product>>> call, @NonNull Response<EndPoint<List<Product>>> response) {
                    Log.e(TAG, response.message());
                    if (response.isSuccessful()) {
                        products.clear();
                        List<Product> list = response.body().getData();
                        products.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<EndPoint<List<Product>>> call, @NonNull Throwable t) {

                }
            });
            adapter.setOnClickListener(position -> {
                if(mIProductFragmentView != null){
                    mIProductFragmentView.openProductDetailActivity(products.get(position).getProductId());
                }
            });
            txtMore.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                }
            });
        }
    }

    public void setmIProductFragmentView(IProductFragmentView mIProductFragmentView) {
        this.mIProductFragmentView = mIProductFragmentView;
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListerner(OnItemClickListener callBack){
        this.mListener = callBack;
    }
}
