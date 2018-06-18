package com.poc.test;

import com.poc.qb.posengine.InitiateSale;
import com.poc.qb.posengine.InitiateSaleImpl;
import com.poc.qb.posentity.Product;

import java.math.BigDecimal;

public class QBTest {

    public static void main (String[] a) {
        InitiateSale sale = new InitiateSaleImpl();

        Product product = new Product();
        product.setName("Chai Latte");
        product.setQuantity(new BigDecimal(2));
        product.setUnitPrice(new BigDecimal(4.5));

        sale.sellProduct(product);
    }
}
