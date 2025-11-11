package com.progressoft.fxclusteredwarehouse.services;

import com.progressoft.fxclusteredwarehouse.models.dto.DealRequestDto;
import com.progressoft.fxclusteredwarehouse.models.dto.DealResponseDto;

public interface IDealService {

    DealResponseDto created(DealRequestDto dto);
}
