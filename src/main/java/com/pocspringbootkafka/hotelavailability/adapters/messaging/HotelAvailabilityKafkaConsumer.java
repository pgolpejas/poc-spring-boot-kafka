package com.pocspringbootkafka.hotelavailability.adapters.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocspringbootkafka.hotelavailability.domain.model.HotelAvailabilityDbSearch;
import com.pocspringbootkafka.hotelavailability.domain.model.HotelAvailabilitySearch;
import com.pocspringbootkafka.hotelavailability.ports.HotelAvailabilityRepository;
import com.pocspringbootkafka.shared.constants.AppConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
class HotelAvailabilityKafkaConsumer {

    private final HotelAvailabilityRepository hotelAvailabilityRepository;
    private final ObjectMapper objectMapper;

    public HotelAvailabilityKafkaConsumer(HotelAvailabilityRepository hotelAvailabilityRepository, ObjectMapper objectMapper) {
        this.hotelAvailabilityRepository = hotelAvailabilityRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = AppConstants.TOPIC_NAME, groupId = AppConstants.GROUP_ID)
    public void consumeMessage(@Payload final ConsumerRecord<String, String> message) throws JsonProcessingException {
        final String searchId = message.key();
        final HotelAvailabilitySearch search = objectMapper.readValue(message.value(), HotelAvailabilitySearch.class);

        HotelAvailabilityDbSearch dtoToPersist = hotelAvailabilityRepository.findById(searchId)
            .map(searchFound -> createHotelAvailabilityDbSearch(search, searchId, searchFound.count() + 1))
            .orElse(createHotelAvailabilityDbSearch(search, searchId, 1));
        hotelAvailabilityRepository.save(dtoToPersist);
    }

    private HotelAvailabilityDbSearch createHotelAvailabilityDbSearch(HotelAvailabilitySearch search, String searchId, Integer count) {
        return new HotelAvailabilityDbSearch(searchId, search.hotelId(), search.checkIn(), search.checkOut(), search.ages(), count);
    }
}
