package com.sgmarghade.kafkaproducerpool;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by swapnil on 19/01/16.
 */
public class KafkaProducerGeneratorTest {

    KafkaProducerGenerator generator;
    private GenericObjectPool pool;

    @Before
    public void setup() {
        generator = new KafkaProducerGenerator(new Properties(), 5);
        pool = Mockito.mock(GenericObjectPool.class);
    }

    @Test
    public void testMaxMinIdleCount() {
        GenericObjectPool<Producer<String, String>> pool = generator.getPool();
        Assert.assertEquals(5, pool.getMaxIdle());
        Assert.assertEquals(5, pool.getMinIdle());
    }

    @Test
    public void stopShouldStopPool() {
        generator.stop();
        Assert.assertEquals(true, generator.getPool().isClosed());
    }

    @Test
    public void testShouldSendSingleMessageToKafka() throws Exception {
        generator.setPool(pool);
        KeyedMessage message = new KeyedMessage("topic","Key","Value");
        Producer producer = Mockito.mock(Producer.class);
        Mockito.doReturn(producer).when(pool).borrowObject();
        Mockito.doNothing().when(pool).returnObject(producer);
        generator.getKafkaProducer().send(message);
        Mockito.verify(producer).send(message);
    }

    @Test
    public void testShouldSendMultipleMessagesToKafka() throws Exception {
        generator.setPool(pool);
        KeyedMessage message = new KeyedMessage("topic","Key","Value");
        Producer producer = Mockito.mock(Producer.class);
        Mockito.doReturn(producer).when(pool).borrowObject();
        Mockito.doNothing().when(pool).returnObject(producer);
        List<KeyedMessage<String, String>> messageList = Collections.singletonList(message);
        generator.getKafkaProducer().send(messageList);
        Mockito.verify(producer).send(messageList);
    }
}
