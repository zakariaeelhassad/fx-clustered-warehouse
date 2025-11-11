package com.progressoft.fxclusteredwarehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.progressoft.fxclusteredwarehouse.models.dto.DealRequestDto;
import com.progressoft.fxclusteredwarehouse.models.dto.DealResponseDto;
import com.progressoft.fxclusteredwarehouse.services.IDealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deals")
public class DealController {

    private final IDealService service;

    @PostMapping
    public ResponseEntity<DealResponseDto> created(@RequestBody @Valid DealRequestDto dto) {
        DealResponseDto deal = service.created(dto);

        return new ResponseEntity<>(deal, HttpStatus.CREATED);
    }
}
