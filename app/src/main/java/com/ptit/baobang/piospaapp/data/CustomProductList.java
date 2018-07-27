package com.ptit.baobang.piospaapp.data;

import com.ptit.baobang.piospaapp.data.model.Product;

import java.util.List;

public class CustomProductList{

    private List<Product> data;

    public CustomProductList(List<Product> data) {
        this.data = data;
    }

    public CustomProductList() {
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
