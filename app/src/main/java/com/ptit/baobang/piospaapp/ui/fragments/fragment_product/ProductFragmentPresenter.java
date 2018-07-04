package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragmentPresenter extends BasePresenter implements IProductFragmentPresenter {

    private IProductFragmentView mView;

    ProductFragmentPresenter(IProductFragmentView mIProductFragmentView) {
        super();
        this.mView = mIProductFragmentView;
    }


    @Override
    public void onClickMore(ProductGroup productGroup) {
        mView.openAllProductActivity(productGroup.getProductGroupId(), productGroup.getProductGroupName());
    }


    @Override
    public void loadData() {
        mView.showLoading("Tải dữ liệu");
        mApiService.getAllProductGroup().enqueue(new Callback<EndPoint<List<ProductGroup>>>() {
            @Override
            public void onResponse(Call<EndPoint<List<ProductGroup>>> call, Response<EndPoint<List<ProductGroup>>> response) {
                if (response.body().getStatusCode() == 200) {
                    handleResponse(response.body());
                }else{
                    mView.hideLoading(response.body().getMessage(), false);
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

    private void handleResponse(EndPoint<List<ProductGroup>> listEndPoint) {
        List<ProductGroup> groups = listEndPoint.getData();
        mView.onUpdateRecycleView(groups);
        mView.hideLoading();
    }

    private void handleError(Throwable throwable) {
        mView.hideLoading(throwable.getMessage(), false);
    }
}
