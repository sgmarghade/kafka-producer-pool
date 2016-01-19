package com.sgmarghade.kafkaproducerpool;

import com.sgmarghade.kafkaproducerpool.Producer.KafkaProducer;
import com.sgmarghade.kafkaproducerpool.factory.KafkaPoolFactory;
import kafka.javaapi.producer.Producer;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.Properties;


/**
 * Created by swapnil on 19/01/16.
 */
public class KafkaProducerGenerator {
    private final Properties properties;
    private final int poolSize;
    private KafkaProducer kafkaProducer;
    private GenericObjectPool<Producer<String, String>> pool;

    public KafkaProducerGenerator(Properties properties, int poolSize){
        this.properties = properties;
        this.poolSize = poolSize;
        this.pool = generatePool(properties, poolSize);
        this.kafkaProducer = new KafkaProducer(pool);
    }

    protected GenericObjectPool<Producer<String, String>> generatePool(Properties properties, int poolSize) {
        GenericObjectPool<Producer<String, String>> pool = new GenericObjectPool<>(new KafkaPoolFactory(properties,poolSize));
        pool.setMinIdle(poolSize);
        pool.setMaxIdle(poolSize);
        pool.setMaxTotal(poolSize);
        return pool;
    }

    public KafkaProducer getKafkaProducer(){
        return this.kafkaProducer;
    }

    public void stop(){
        this.pool.close();
    }

    protected GenericObjectPool<Producer<String, String>> getPool(){
        return pool;
    }

    protected void setPool(GenericObjectPool<Producer<String, String>> pool){
        this.pool = pool;
        this.kafkaProducer = new KafkaProducer(pool);
    }
}
