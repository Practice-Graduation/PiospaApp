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
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.network.api.APIService;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductGroupAdapter<P extends BasePresenter> extends RecyclerView.Adapter<ProductGroupAdapter.ProductGroupHolder> {
    private static final String TAG = "ProductGroupAdapter";
    private Context mContext;
    private List<ProductGroup> mProductGroups;
    private APIService mApiService;
    private P mPresenter;

    public ProductGroupAdapter(Context mContext, List<ProductGroup> mProductGroups, P mPresenter, APIService mApiService) {
        this.mContext = mContext;
        this.mProductGroups = mProductGroups;
        this.mApiService = mApiService;
        this.mPresenter = mPresenter;
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
        holder.binView(mProductGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductGroups.size();
    }
    class ProductGroupHolder extends RecyclerView.ViewHolder {

        TextView txtGroupName;
        TextView txtMore;
        RecyclerView rvGroupProduct;
        List<Product> products;
        ProductAdapter adapter;
        ProductGroupHolder(View itemView) {
            super(itemView);
            txtGroupName = itemView.findViewById(R.id.txtGroupName);
            rvGroupProduct = itemView.findViewById(R.id.rvProducts);
            txtMore = itemView.findViewById(R.id.txtMore);
            products = new ArrayList<>();
            adapter = new ProductAdapter(mContext, products, R.layout.item_product);
            rvGroupProduct.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            rvGroupProduct.setAdapter(adapter);
        }

        void binView(ProductGroup productGroup) {
            txtGroupName.setText(productGroup.getProductGroupName());
//            mPresenter.getCompositeDisposable().add(
//                    mApiService.getProductByGroupId(productGroup.getProductGroupId())
//            .subscribeOn(Schedulers.computation())
//            .observeOn(AndroidSchedulers.mainThread())
//            .unsubscribeOn(Schedulers.io())
//            .subscribe(this::handleResponse, this::handleError));

            mApiService.getProductByGroupId(productGroup.getProductGroupId()).enqueue(new Callback<EndPoint<List<Product>>>() {
                @Override
                public void onResponse(Call<EndPoint<List<Product>>> call, Response<EndPoint<List<Product>>> response) {
                    if(response.body().getStatusCode() == 200){
                        handleResponse(response.body());
                    }
                }

                @Override
                public void onFailure(Call<EndPoint<List<Product>>> call, Throwable t) {
                    handleError(t);
                }
            });

            adapter.setOnClickListener(position -> mItemSelected.itemSelected(products.get(position)));
            txtMore.setOnClickListener(v -> {
                if(mListener != null){
                    mListener.onItemSelected(getAdapterPosition());
                }
            });
        }

        private void handleError(Throwable throwable) {

        }

        private void handleResponse(EndPoint<List<Product>> listEndPoint) {
            products.clear();
            List<Product> list = listEndPoint.getData();
            products.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }
    public interface OnSelectedItem{
        void itemSelected(Product product);
    }

    private OnSelectedItem mItemSelected;

    private OnItemClickListener mListener;

    public void setItemSelected(OnSelectedItem mItemSelected) {
        this.mItemSelected = mItemSelected;
    }

    public void setOnSelectMore(OnItemClickListener callBack){
        this.mListener = callBack;
    }
}
