package com.progressoft.fxclusteredwarehouse.mapper;


import com.progressoft.fxclusteredwarehouse.models.dto.DealRequestDto;
import com.progressoft.fxclusteredwarehouse.models.dto.DealResponseDto;
import com.progressoft.fxclusteredwarehouse.models.entitis.Deal;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealMapper {

    Deal toEntity(DealRequestDto dto);

    DealResponseDto toResponseEntity(Deal deal);
}
