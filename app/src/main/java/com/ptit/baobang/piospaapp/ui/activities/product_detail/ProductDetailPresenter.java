package com.ptit.baobang.piospaapp.ui.activities.product_detail;

import com.ptit.baobang.piospaapp.R;
import com.ptit.baobang.piospaapp.data.cart.Cart;
import com.ptit.baobang.piospaapp.data.cart.CartHelper;
import com.ptit.baobang.piospaapp.data.model.Product;
import com.ptit.baobang.piospaapp.data.network.api.EndPoint;
import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailPresenter extends BasePresenter implements IProductDetailPresenter{

    private IProductDetailView mView;

    public ProductDetailPresenter(IProductDetailView mIProductDetailView) {
        this.mView = mIProductDetailView;
    }

    @Override
    public void loadData(int productId) {
        mApiService.getProductById(productId).enqueue(new Callback<EndPoint<Product>>() {
            @Override
            public void onResponse(Call<EndPoint<Product>> call, Response<EndPoint<Product>> response) {
                if(response.isSuccessful()){
                    Product product = response.body().getData();
                    mView.stopShirrmentAnimation();
                    mView.showProductDetail(product);
                }
            }

            @Override
            public void onFailure(Call<EndPoint<Product>> call, Throwable t) {
                mView.showMessage(R.string.some_error);
            }
        });
    }

    @Override
    public void onClickAddCart(int productId) {
        mView.showMessage("OnClick");
        mApiService.getProductById(productId).enqueue(new Callback<EndPoint<Product>>() {
            @Override
            public void onResponse(Call<EndPoint<Product>> call, Response<EndPoint<Product>> response) {
                if(response.isSuccessful()){
                    Product product = response.body().getData();
                    Cart cart = CartHelper.getCart();
                    cart.add(product, 1);
                    mView.showMessage("Added " + product.getProductName());
                }
            }

            @Override
            public void onFailure(Call<EndPoint<Product>> call, Throwable t) {
                mView.showMessage("Failed");
            }
        });

    }

    @Override
    public void onClickGoToCart() {
        mView.openFramentCart();
    }
}
