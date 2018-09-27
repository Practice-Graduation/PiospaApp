package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import android.content.Context;
import android.support.v7.widget.SearchView;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;
import com.ptit.baobang.piospaapp.utils.AppConstants;
import com.ptit.baobang.piospaapp.utils.RxSearchObservable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragmentPresenter extends BasePresenter implements IProductFragmentPresenter {

    private IProductFragmentView mView;
    private Context mContext;

    ProductFragmentPresenter(Context mContext,IProductFragmentView mIProductFragmentView) {
        super();
        this.mContext =  mContext;
        this.mView = mIProductFragmentView;
    }


    @Override
    public void onClickMore(ProductGroup productGroup) {
        mView.openAllProductActivity(productGroup.getProductGroupId(), productGroup.getProductGroupName());
    }


    @Override
    public void loadData() {
        mView.showLoading(mContext.getString(R.string.loading));
        mApiService.getAllProductGroup().enqueue(new Callback<EndPoint<List<ProductGroup>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<ProductGroup>>> call, Response<EndPoint<List<ProductGroup>>> response) {
                if(response.body() != null){
                    if (response.body().getStatusCode() == AppConstants.SUCCESS_CODE) {
                        handleResponse(response.body());
                    }else{
                        mView.hideLoading(response.body().getMessage(), false);
                    }
                }
            }

            @Override
            public void onFailure(Call<EndPoint<List<ProductGroup>>> call, Throwable t) {
                handleError(t);
            }
        });
    }

    @Override
    public void clickItem(Product product) {
        mView.openProductDetailActivity(product);
    }

    @Override
    public void filter(SearchView searchView) {
        RxSearchObservable.fromView(searchView)
                .debounce(RxSearchObservable.TIME_OUT, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handFilterResponse);
    }

    private void handFilterResponse(String s) {
        mView.getProductGroupAdapter().filter(s);
    }
    private void handleResponse(EndPoint<List<ProductGroup>> listEndPoint) {
        List<ProductGroup> groups = listEndPoint.getData();
        mView.onUpdateRecycleView(groups);
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }
}
