package com.poc.qb.posprocessor;

import com.intuit.ipp.data.*;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.util.Logger;
import com.poc.qb.posentity.Product;
import com.poc.qb.posprocessor.squareconnector.SquareTxn;
import com.poc.qb.qbservice.DataServiceFactory;
import com.squareup.connect.models.ChargeResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SaleProcessorImpl implements SaleProcessor {

    private static final org.slf4j.Logger LOG = Logger.getLogger();

    public void processSale(Product p) {

        p.setTotal(p.getQuantity().multiply(p.getUnitPrice()));

        SquareTxn squareTxn = new SquareTxn();
        ChargeResponse response = squareTxn.finalizeSale(p);

        if (null != response.getTransaction()) {

            try {
                DataService service = DataServiceFactory.getDataService();

                SalesReceipt sr = new SalesReceipt();
                sr.setAutoDocNumber(true);

                ReferenceType customerRef = new ReferenceType();
                customerRef.setValue("1");
                customerRef.setName("Adwin Ko");

                sr.setCustomerRef(customerRef);

                Line line = new Line();
                line.setLineNum(new BigInteger("1"));
                line.setDescription("App Test");
                line.setAmount(p.getTotal());
                line.setDetailType(LineDetailTypeEnum.SALES_ITEM_LINE_DETAIL);


                SalesItemLineDetail salesItemLineDetail = new SalesItemLineDetail();
                salesItemLineDetail.setUnitPrice(p.getUnitPrice());
                salesItemLineDetail.setQty(p.getQuantity());

                ReferenceType taxRefType = new ReferenceType();
                taxRefType.setValue("10");
                salesItemLineDetail.setTaxCodeRef(taxRefType);

                ReferenceType itemRefType = new ReferenceType();
                itemRefType.setValue("18");
                itemRefType.setName("Name Badges");
                salesItemLineDetail.setItemRef(itemRefType);

                line.setSalesItemLineDetail(salesItemLineDetail);

                List<Line> list = new ArrayList<>();
                list.add(line);

                sr.setLine(list);

                SalesReceipt savedSalesReceipt = service.add(sr);
                LOG.info("SalesReceipt created: " + savedSalesReceipt.getId() + " ::salesreceipt doc num: " + savedSalesReceipt.getDocNumber());
            } catch (FMSException e) {
                e.printStackTrace();
            }
        }


    }
}
