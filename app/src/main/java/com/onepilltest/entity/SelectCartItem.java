package com.onepilltest.entity;

public class SelectCartItem {
    private boolean selected;
    private MyCart cart;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public MyCart getCart() {
        return cart;
    }

    public void setCart(MyCart cart) {
        this.cart = cart;
    }
}
