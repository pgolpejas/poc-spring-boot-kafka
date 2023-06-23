package com.pocspringbootkafka.hotelavailability.ports;

import com.pocspringbootkafka.hotelavailability.domain.model.HotelAvailabilityDbSearch;

import java.util.Optional;

public interface HotelAvailabilityRepository {

    Optional<HotelAvailabilityDbSearch> findById(String id);

    HotelAvailabilityDbSearch save(HotelAvailabilityDbSearch search);

}
