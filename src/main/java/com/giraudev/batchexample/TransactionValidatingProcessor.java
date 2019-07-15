package com.giraudev.batchexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

@Slf4j
public class TransactionValidatingProcessor extends ValidatingItemProcessor<Customer> {

    public TransactionValidatingProcessor(final int limit){
        super(
                item -> {
                    if(item.getTransactions() >= limit){
                        throw new ValidationException("Customer has more than "+limit+"transactions");
                    }

                }
        );
        setFilter(true);
    }
}
