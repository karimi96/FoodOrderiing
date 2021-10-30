package com.example.foodorderiing.activity.product.mvp;

public class ProductModel implements ProductPresenter {
    ProductView view;

    public ProductModel(ProductView view) {
        this.view = view;
    }

    @Override
    public void getDate() {
        view.setData();
    }

}
