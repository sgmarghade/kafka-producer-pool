Kafka producer pool is library with pool of kafka client. Old api of kafka where kafka client is in scala sync message
produce throughput hampers due to serial delviery of message from client to kafka broker.

# Usage
- KafkaProducerGenerator kafkaGenerator = new KafkaProducerGenerator(Properties kafkaProperties , int poolSize);
- KafkaProducer producer =  kafkaGenerator.getKafkaProducer();
- producer.send(keyedMessage);
- producer.send(KeyedMessageList);
- kafkaGenerator.stop(); // While stopping server. 