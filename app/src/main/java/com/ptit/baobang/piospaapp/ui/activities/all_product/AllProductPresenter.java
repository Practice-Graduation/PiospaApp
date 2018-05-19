package com.ptit.baobang.piospaapp.ui.activities.all_product;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductPresenter extends BasePresenter implements IAllProductPresenter{

    private IAllProductView mView;

    public AllProductPresenter(IAllProductView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(int productGroupId) {
        mApiService.getProductByGroupId(productGroupId).enqueue(new Callback<EndPoint<List<Product>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<Product>>> call, Response<EndPoint<List<Product>>> response) {
                if(response.isSuccessful()){
                    List<Product> products = response.body().getData();
                    mView.onUpdateRecyleView(products);
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<Product>>> call, Throwable t) {
                mView.showMessage(R.string.some_error);
            }
        });
    }

    @Override
    public void onSelectedProduct(int productId) {
       mView.openProductDetailActivity(productId);
    }
}
