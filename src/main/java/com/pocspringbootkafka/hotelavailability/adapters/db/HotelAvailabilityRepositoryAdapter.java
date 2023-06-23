package com.pocspringbootkafka.hotelavailability.adapters.db;

import com.pocspringbootkafka.hotelavailability.domain.model.HotelAvailabilityDbSearch;
import com.pocspringbootkafka.hotelavailability.ports.HotelAvailabilityRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class HotelAvailabilityRepositoryAdapter implements HotelAvailabilityRepository {
    private final JpaHotelAvailabilitySearchRepository jpaHotelAvailabilitySearchRepository;
    private final HotelAvailabilitySearchEntityMapper hotelAvailabilityEntityMapper;

    public HotelAvailabilityRepositoryAdapter(JpaHotelAvailabilitySearchRepository jpaHotelAvailabilitySearchRepository,
        HotelAvailabilitySearchEntityMapper hotelAvailabilityEntityMapper) {
        this.jpaHotelAvailabilitySearchRepository = jpaHotelAvailabilitySearchRepository;
        this.hotelAvailabilityEntityMapper = hotelAvailabilityEntityMapper;
    }

    @Override
    public Optional<HotelAvailabilityDbSearch> findById(String id) {
        Optional<HotelAvailabilitySearchEntity> entity = jpaHotelAvailabilitySearchRepository.findById(id);
        return entity.isPresent() ? Optional.of(hotelAvailabilityEntityMapper.toHotelAvailabilitySearch(entity.get())) : Optional.empty();
    }

    @Override
    public HotelAvailabilityDbSearch save(HotelAvailabilityDbSearch search) {
        HotelAvailabilitySearchEntity entity = hotelAvailabilityEntityMapper.toHotelAvailabilitySearchEntity(search);
        entity = jpaHotelAvailabilitySearchRepository.save(entity);
        return hotelAvailabilityEntityMapper.toHotelAvailabilitySearch(entity);
    }

}
