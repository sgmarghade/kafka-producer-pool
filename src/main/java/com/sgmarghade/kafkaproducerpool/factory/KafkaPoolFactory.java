package com.sgmarghade.kafkaproducerpool.factory;


import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Properties;

/**
 * Created by swapnil on 19/01/16.
 */
public class KafkaPoolFactory extends BasePooledObjectFactory<Producer<String,String>>{
    private final Properties properties;
    private final int poolSize;

    public KafkaPoolFactory(Properties properties , int poolSize){
        this.properties = properties;
        this.poolSize = poolSize;
    }

    @Override
    public Producer<String, String> create() throws Exception {
        return new Producer<>(new ProducerConfig(properties));
    }

    @Override
    public PooledObject<Producer<String, String>> wrap(Producer<String, String> producer) {
        return new DefaultPooledObject<>(producer);
    }
}
