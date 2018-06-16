package com.ptit.baobang.piospaapp.ui.fragments.fragment_product;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.model.ProductGroup;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductFragmentPresenter extends BasePresenter implements IProductFragmentPresenter {

    private IProductFragmentView mView;

    ProductFragmentPresenter(IProductFragmentView mIProductFragmentView) {
        super();
        this.mView = mIProductFragmentView;
    }


    @Override
    public void onClickMore(ProductGroup productGroup) {
        mView.openAllProductActivity(productGroup.getProductGroupId());
    }


    @Override
    public void loadData() {
        mView.showLoading("Tải dữ liệu");
        getCompositeDisposable().add(
                mApiService.getAllProductGroup()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError));
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
