package com.sgmarghade.kafkaproducerpool.Producer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.List;

/**
 * Created by swapnil on 19/01/16.
 */
public class KafkaProducer {

    private final GenericObjectPool<Producer<String, String>> pool;

    public KafkaProducer(GenericObjectPool<Producer<String, String>> pool) {
        this.pool = pool;
    }

    public void send(KeyedMessage<String, String> message) throws Exception{
        Producer<String, String> producer = null;
        try {
            producer = pool.borrowObject();
            producer.send(message);
        }  finally {
            if (producer != null) {
                pool.returnObject(producer);
            }
        }
    }

    public void send(List<KeyedMessage<String, String>> message) throws Exception{
        Producer<String, String> producer = null;
        try {
            producer = pool.borrowObject();
            producer.send(message);

        }  finally {
            if (producer != null) {
                pool.returnObject(producer);
            }
        }
    }

}
