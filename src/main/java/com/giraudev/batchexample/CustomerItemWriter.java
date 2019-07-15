package com.giraudev.batchexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.List;

@Slf4j
public class CustomerItemWriter implements ItemWriter<Customer>, Closeable {

    private final PrintWriter writer;

    public CustomerItemWriter(){
        OutputStream out;
        try {
            out = new FileOutputStream("output.txt");
        }catch (FileNotFoundException e){
            out = System.out;
        }

        this.writer = new PrintWriter(out);
    }

    @Override
    public void write(final List<? extends Customer> items) throws Exception{
        for(Customer item:items){
            writer.println(item.toString());
        }
    }

    @PreDestroy
    @Override
    public void close() throws IOException{
        writer.close();
    }

}
