package com.acs.Shopping.Cart.util;

public class ShoppingCartConstants {
    private ShoppingCartConstants() {}

    // db messages
    public static final String PRODUCT_NOT_FOUND = "Product Not Found!!";
    public static final String CATEGORY_NOT_FOUND = "Category Not Found!!";
    public static final String ALREADY_EXISTS = "Already Exists!!";
    public static final String IMAGE_NOT_FOUND = "Image not found";
    public static final String IMAGE_DOWNLOAD_URL = "/api/v1/images/image/download/";
    public static final String IMAGE_SAVE_FAILURE = "Failed to save image for product ID: ";
    public static final String IMAGE_UPDATE_FAILURE = "Failed to save update for product ID: ";
    public static final String UPLOAD_SUCCESS = "Upload Success";
    public static final String UPLOAD_FAILURE = "Upload Failure";
    public static final String UPDATE_SUCCESS = "Update Success";
    public static final String UPDATE_FAILURE = "Update Failure";
    public static final String DELETE_FAILURE = "Delete Failure";
    public static final String DELETE_SUCCESS = "Delete Success";
    public static final String RESOURCE_FOUND = "Resource Found";
    public static final String CART_NOT_FOUND = "Cart not found";
    public static final String CART_CLEARED = "Cart cleared";
    public static final String ITEM_ADDED_TO_CART = "Item added to cart successfully";
    public static final String ITEM_DELETED_FROM_CART = "Item deleted from cart successfully";
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String USER_ALREADY_EXISTS = "User with email : %s already exists";
    public static final String ORDER_CREATED = "Order Created";

    // http codes
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;

    // messages
    public static final String ERROR = "Error";
    public static final String SUCCESS ="Success";
}
