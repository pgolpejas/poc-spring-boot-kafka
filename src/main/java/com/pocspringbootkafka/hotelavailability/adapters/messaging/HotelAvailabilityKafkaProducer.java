package com.pocspringbootkafka.hotelavailability.adapters.messaging;

import com.pocspringbootkafka.hotelavailability.domain.model.HotelAvailabilitySearch;
import com.pocspringbootkafka.hotelavailability.ports.HotelAvailabilityMessagingProducerService;
import com.pocspringbootkafka.shared.constants.AppConstants;
import com.pocspringbootkafka.shared.utils.SearchIdGenerator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
class HotelAvailabilityKafkaProducer implements HotelAvailabilityMessagingProducerService {

    public static final String X_KAFKA_TIME = "x-kafka-time";

    private final KafkaTemplate<String, HotelAvailabilitySearch> kafkaTemplate;

    public HotelAvailabilityKafkaProducer(KafkaTemplate<String, HotelAvailabilitySearch> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(HotelAvailabilitySearch search) {
        Message<HotelAvailabilitySearch> message = MessageBuilder.withPayload(search)
            .setHeader(KafkaHeaders.TOPIC, AppConstants.TOPIC_NAME)
            .setHeader(KafkaHeaders.KEY, SearchIdGenerator.generateSearchId(search))
            .setHeader(X_KAFKA_TIME, OffsetDateTime.now().toString())
            .build();

        kafkaTemplate.send(message);
    }
}
