package com.progressoft.fxclusteredwarehouse.controller;

import com.progressoft.fxclusteredwarehouse.models.dto.DealRequestDto;
import com.progressoft.fxclusteredwarehouse.models.dto.DealResponseDto;
import com.progressoft.fxclusteredwarehouse.services.IDealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class DealControllerTest {

    @Mock
    private IDealService service ;

    @InjectMocks
    private DealController controller ;

    private DealRequestDto dealRequestDto;
    private DealResponseDto dealResponseDto;

    @BeforeEach
    void setup(){
        dealRequestDto = new DealRequestDto( "d1" ,
                Currency.getInstance("MAD"),
                Currency.getInstance("USD"),
                LocalDateTime.now(),
                BigDecimal.valueOf(2000)
        );

        dealResponseDto = new DealResponseDto("d1",
                Currency.getInstance("MAD"),
                Currency.getInstance("USD"),
                dealRequestDto.timestamp(),
                dealRequestDto.amount());
    }

    @Test
    void givenValidRequest_whenSave_thenReturnCreatedResponse() {
        given(service.created(dealRequestDto)).willReturn(dealResponseDto);

        ResponseEntity<DealResponseDto> actual = controller.created(dealRequestDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody()).isEqualTo(dealResponseDto);
        verify(service).created(dealRequestDto);
    }

    @Test
    void givenDuplicateDealId_whenSave_thenThrowDuplicateDealIdException() {
        given(service.created(any(DealRequestDto.class)))
                .willThrow(new com.progressoft.fxclusteredwarehouse.exception.DealIdException("Deal id already exists"));

        assertThatExceptionOfType(com.progressoft.fxclusteredwarehouse.exception.DealIdException.class)
                .isThrownBy(() -> controller.created(dealRequestDto))
                .withMessage("Deal id already exists");

        verify(service).created(dealRequestDto);
    }
}
