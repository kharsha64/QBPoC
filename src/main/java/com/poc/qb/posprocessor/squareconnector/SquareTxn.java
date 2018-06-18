package com.poc.qb.posprocessor.squareconnector;

import com.intuit.ipp.util.Logger;
import com.poc.qb.posentity.Product;
import com.poc.qb.posprocessor.squareconnector.properties.TxnProperties;
import com.poc.qb.posprocessor.squareconnector.stub.SqaureStub;
import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.TransactionsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.ChargeRequest;
import com.squareup.connect.models.ChargeResponse;
import com.squareup.connect.models.Money;

public class SquareTxn {

    private static final org.slf4j.Logger LOG = Logger.getLogger();

    public ChargeResponse finalizeSale(Product p) {

        ApiClient apiClient = Configuration.getDefaultApiClient();
        OAuth oauth2 = (OAuth) apiClient.getAuthentication("oauth2");
        oauth2.setAccessToken("sandbox-sq0atb-fiX5dtdzqEUyiAefUbidSA");

        TransactionsApi transactionsApi = new TransactionsApi();
        transactionsApi.setApiClient(apiClient);

        ChargeRequest chargeRequest = new ChargeRequest();
        chargeRequest.setIdempotencyKey("" + System.currentTimeMillis());
        chargeRequest.setCardNonce(TxnProperties.CARDNONNCE);
        chargeRequest.setReferenceId("IN10001");

        Money money = new Money();
        //money.setAmount((long) p.getTotal().longValue());
        money.setAmount((long)101);
        money.setCurrency(Money.CurrencyEnum.AUD);

        chargeRequest.setAmountMoney(money);

        ChargeResponse chargeResponse = null;
        try {
            //chargeResponse = transactionsApi.charge(TxnProperties.LOCATION_ID, chargeRequest);
            chargeResponse = SqaureStub.fetchData(chargeRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chargeResponse;
    }
}
