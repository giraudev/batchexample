package com.giraudev.batchexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

@Slf4j
@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
public class BatchexampleApplication {

	public static void main(String[] args) {

		prepareTestData(1000);
		SpringApplication.run(BatchexampleApplication.class, args);
	}

	private static void prepareTestData(final int amount){
		final int actualYear = new GregorianCalendar().get(Calendar.YEAR);
		final Collection<Customer> customers = new LinkedList<>();
		for (int i=1; i<= amount; i++){
			final Calendar birthday = new GregorianCalendar();
			birthday.set(Calendar.YEAR, random(actualYear - 100, actualYear));
			birthday.set(Calendar.DAY_OF_YEAR, random(1, birthday.getActualMaximum(Calendar.DAY_OF_YEAR)));
			final Customer customer = new Customer();
			customer.setId(i);
			customer.setName(UUID.randomUUID().toString().replaceAll("[^a-z]",""));
			customer.setBirthday(birthday);
			customer.setTransactions(random(0,100));
			customers.add(customer);
		}

		try(final XMLEncoder encoder = new XMLEncoder(new FileOutputStream(CustomerReportJobConfig.XML_FILE))){
			encoder.writeObject(customers);
		}catch (final FileNotFoundException e){
			System.exit(-1);
		}
	}

	private static int random(final int start, final int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

}
