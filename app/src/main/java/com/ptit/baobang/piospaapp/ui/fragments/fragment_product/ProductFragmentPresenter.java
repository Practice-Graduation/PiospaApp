package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragmentPresenter extends BasePresenter implements IProductFragmentPresenter{

    private IProductFragmentView mView;
    public ProductFragmentPresenter(IProductFragmentView mIProductFragmentView) {
        super();
        this.mView = mIProductFragmentView;
    }


    @Override
    public void onClickMore(ProductGroup productGroup) {
        mView.openAllProductActivity(productGroup.getProductGroupId());
    }



    @Override
    public void loadData() {

        mApiService.getAllProductGroup().enqueue(new Callback<EndPoint<List<ProductGroup>>>() {

            @Override
            public void onResponse(Call<EndPoint<List<ProductGroup>>> call, Response<EndPoint<List<ProductGroup>>> response) {
                if(response.isSuccessful()){
                    List<ProductGroup> groups = response.body().getData();
                    mView.onUpdateRecycleView(groups);
                    mView.stopShimmerAnimation();
                }
            }
            @Override
            public void onFailure(Call<EndPoint<List<ProductGroup>>> call, Throwable t) {
                mView.showMessage(t.getMessage());
            }
        });

    }
}
