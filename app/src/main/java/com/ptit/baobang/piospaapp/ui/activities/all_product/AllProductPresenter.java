package com.ptit.baobang.piospaapp.ui.activities.all_product;

import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductPresenter extends BasePresenter implements IAllProductPresenter{

    private IAllProductView mView;

    AllProductPresenter(IAllProductView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(int productGroupId) {
        mView.showLoading("Tải dữ liệu");
//        getCompositeDisposable().add(
//                mApiService.getProductByGroupId(productGroupId)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(this::handleResponse, this::handleError));
        mApiService.getProductByGroupId(productGroupId).enqueue(new Callback<EndPoint<List<Product>>>() {
            @Override
            public void onResponse(@NonNull Call<EndPoint<List<Product>>> call, @NonNull Response<EndPoint<List<Product>>> response) {
                handleResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<EndPoint<List<Product>>> call, @NonNull Throwable t) {
                    handleError(t);
            }
        });

    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);

    }

    private void handleResponse(EndPoint<List<Product>> listEndPoint) {
        mView.hideLoading();
        List<Product> products = listEndPoint.getData();
        mView.onUpdateRecyleView(products);
    }

    @Override
    public void onSelectedProduct(Product product) {
       mView.openProductDetailActivity(product);
    }
}
