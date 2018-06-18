package com.poc.qb.posprocessor.squareconnector.stub;

import com.squareup.connect.models.ChargeRequest;
import com.squareup.connect.models.ChargeResponse;
import com.squareup.connect.models.Transaction;

public class SqaureStub {

    public static ChargeResponse fetchData(ChargeRequest chargeRequest) {
        ChargeResponse response = new ChargeResponse();

        Transaction trn = new Transaction();
        trn.setId(""+System.nanoTime());
        trn.setLocationId("CBASEDd88X9L8qUhxAkCh6P8wWAgAQs");
        trn.setProduct(Transaction.ProductEnum.ONLINE_STORE);
        trn.setOrderId(""+(System.currentTimeMillis()/2));
        trn.setReferenceId(chargeRequest.getReferenceId());

        response.setTransaction(trn);

        return response;
    }
}
