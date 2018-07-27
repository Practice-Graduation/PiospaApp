package com.ptit.baobang.piospaapp.ui.activities.all_product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductPresenter extends BasePresenter implements IAllProductPresenter {

    private IAllProductView mView;
    private Context mContext;

    AllProductPresenter(Context mContext, IAllProductView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void loadData(int productGroupId) {
        mView.showLoading(mContext.getString(R.string.loading));
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

    @SuppressLint("CheckResult")
    @Override
    public void filter(SearchView searchView) {
        RxSearchObservable.fromView(searchView)
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handFilterResponse);
    }

    private void handFilterResponse(String s) {
        Log.e("TAG", s);
        mView.getProductAdapter().getFilter().filter(s);
    }
}
