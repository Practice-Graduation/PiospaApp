package com.ptit.baobang.piospaapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.CustomProductList;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BaseView;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductGroupAdapter extends RecyclerView.Adapter<ProductGroupAdapter.ProductGroupHolder> {
    private static final String TAG = "ProductGroupAdapter";
    private Context mContext;

    private List<ProductGroup> mProductGroups;
    private ProductAdapter[] mProductAdapters;
    private CustomProductList[] mProductLists;
    private APIService mApiService;
    private BaseView mView;
    public ProductGroupAdapter(Context mContext, List<ProductGroup> mProductGroups, BaseView mView, APIService mApiService) {
        this.mContext = mContext;
        this.mProductGroups = mProductGroups;
        this.mApiService = mApiService;
        this.mView = mView;
        getProductAdapter();
    }

    @NonNull
    @Override
    public ProductGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycle_product, parent, false);
        return new ProductGroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductGroupAdapter.ProductGroupHolder holder, int position) {
        holder.binView(holder.itemView, mProductGroups.get(position));
        ProductAdapter adapter = mProductAdapters[position];
        if (adapter == null) {
            holder.itemView.setVisibility(View.GONE);
        }else{
            if(adapter.getItemCount() > 0){
                holder.itemView.setVisibility(View.VISIBLE);
            }else{
                holder.itemView.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mProductGroups.size();
    }

    public void filter(String s) {
        for (ProductAdapter productAdapter : mProductAdapters) {
            productAdapter.getFilter().filter(s);
            notifyDataSetChanged();
        }
    }

    public void getProductAdapter() {
        mProductAdapters = new ProductAdapter[mProductGroups.size()];
        mProductLists = new CustomProductList[mProductGroups.size()];
        for (int i = 0; i < mProductGroups.size(); i++) {
            int index = i;
            mApiService.getProductByGroupId(mProductGroups.get(i).getProductGroupId()).enqueue(new Callback<EndPoint<List<Product>>>() {

                @Override
                public void onResponse(Call<EndPoint<List<Product>>> call, Response<EndPoint<List<Product>>> response) {
                    if (response.body().getStatusCode() == AppConstants.SUCCESS_CODE) {
                        handleResponse(index, response.body());
                    }
                }

                @Override
                public void onFailure(Call<EndPoint<List<Product>>> call, Throwable t) {
                    handleError(t);
                }

                private void handleError(Throwable throwable) {

                }

                private void handleResponse(int index, EndPoint<List<Product>> listEndPoint) {

                    List<Product> products = new ArrayList<>();
                    products.addAll(listEndPoint.getData());
                    mProductLists[index] = new CustomProductList(products);
                    ProductAdapter adapter = new ProductAdapter(mContext, products, R.layout.item_product);
                    mProductAdapters[index] = adapter;
                    notifyDataSetChanged();
                    if(checkComplete(mProductAdapters)){
                        mView.hideLoading();
                    }
                }
            });
        }
    }

    private boolean checkComplete(ProductAdapter[] mProductAdapters) {
        for(ProductAdapter adapter : mProductAdapters){
            if(adapter == null){
                return false;
            }
        }
        return true;
    }


    class ProductGroupHolder extends RecyclerView.ViewHolder {

        TextView txtGroupName;
        TextView txtMore;
        RecyclerView rvGroupProduct;

        ProductGroupHolder(View itemView) {
            super(itemView);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);

            txtMore = itemView.findViewById(R.id.txtMore);
        }

        void binView(View itemView, ProductGroup productGroup) {

            txtGroupName.setText(productGroup.getProductGroupName());
            txtMore.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemSelected(getAdapterPosition());
                }
            });
            if (mProductAdapters.length > getAdapterPosition()) {
                rvGroupProduct = this.itemView.findViewById(R.id.rvProducts);
                ProductAdapter adapter = mProductAdapters[getAdapterPosition()];
                if (adapter != null) {
                    rvGroupProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    rvGroupProduct.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setOnClickListener(position -> mItemSelected.itemSelected(mProductLists[getAdapterPosition()].getData().get(position)));
                }
            }
        }

    }

    public interface OnSelectedItem {
        void itemSelected(Object product);
    }

    private OnSelectedItem mItemSelected;

    private OnItemClickListener mListener;

    public void setItemSelected(OnSelectedItem mItemSelected) {
        this.mItemSelected = mItemSelected;
    }

    public void setOnSelectMore(OnItemClickListener callBack) {
        this.mListener = callBack;
    }
}
