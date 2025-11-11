package com.progressoft.fxclusteredwarehouse.services.impl;

import com.progressoft.fxclusteredwarehouse.services.IDealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.progressoft.fxclusteredwarehouse.exception.DealIdException;
import com.progressoft.fxclusteredwarehouse.mapper.DealMapper;
import com.progressoft.fxclusteredwarehouse.models.dto.DealRequestDto;
import com.progressoft.fxclusteredwarehouse.models.dto.DealResponseDto;
import com.progressoft.fxclusteredwarehouse.models.entitis.Deal;
import com.progressoft.fxclusteredwarehouse.repository.DealRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealService implements IDealService {

    private final DealRepository repository;
    private final DealMapper mapper;

    @Override
    public DealResponseDto created(DealRequestDto dto){
        checkCurrencyEquality(dto);

        log.info("Attempting to save deal with ID: {}", dto.id());

        if(repository.existsById(dto.id())) {
            log.warn("Duplicate deal ID detected: {}. Operation aborted.", dto.id());
            throw new DealIdException("Deal id already exists");
        }

        Deal savedDeal = repository.save(mapper.toEntity(dto));
        log.info("Deal saved successfully with ID: {}", savedDeal.getId());

        return mapper.toResponseEntity(savedDeal);
    }

    private void checkCurrencyEquality(DealRequestDto dto) {
        if (dto.fromCurrency().equals(dto.toCurrency())) {
            log.warn("Currency equality detected: {} to {}. Operation aborted.",
                    dto.fromCurrency(), dto.toCurrency());
            throw new DealIdException("Cannot convert currency to itself. fromCurrency and toCurrency must be different.");
        }
    }

}
