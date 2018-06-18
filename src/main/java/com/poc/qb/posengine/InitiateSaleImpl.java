package com.poc.qb.posengine;

import com.intuit.ipp.util.Logger;
import com.poc.qb.posentity.Product;
import com.poc.qb.posprocessor.SaleProcessor;
import com.poc.qb.posprocessor.SaleProcessorImpl;

public class InitiateSaleImpl implements InitiateSale {

    private static final org.slf4j.Logger LOG = Logger.getLogger();


    public void sellProduct(Product product) {

        if (null == product.getName() || null == product.getQuantity() || null == product.getUnitPrice()) {
            LOG.error("Invalid Product. Mandatory attributes not set.");
        } else {
            SaleProcessor processor = new SaleProcessorImpl();
            processor.processSale(product);
        }


    }
}
