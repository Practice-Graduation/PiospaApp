package com.ptit.baobang.piospaapp.ui.activities.all_product;

import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AllProductPresenter extends BasePresenter implements IAllProductPresenter{

    private IAllProductView mView;

    AllProductPresenter(IAllProductView mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(int productGroupId) {
        getCompositeDisposable().add(
                mApiService.getProductByGroupId(productGroupId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleError(Throwable throwable) {
        mView.showMessage("Thông báo", throwable.getMessage(), SweetAlertDialog.ERROR_TYPE);

    }

    private void handleResponse(EndPoint<List<Product>> listEndPoint) {
        List<Product> products = listEndPoint.getData();
        mView.onUpdateRecyleView(products);
    }

    @Override
    public void onSelectedProduct(int productId) {
       mView.openProductDetailActivity(productId);
    }
}
