package com.progressoft.fxclusteredwarehouse.services;


import com.progressoft.fxclusteredwarehouse.exception.DealIdException;
import com.progressoft.fxclusteredwarehouse.mapper.DealMapper;
import com.progressoft.fxclusteredwarehouse.models.dto.DealRequestDto;
import com.progressoft.fxclusteredwarehouse.models.dto.DealResponseDto;
import com.progressoft.fxclusteredwarehouse.models.entitis.Deal;
import com.progressoft.fxclusteredwarehouse.repository.DealRepository;
import com.progressoft.fxclusteredwarehouse.services.impl.DealService;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class DealServiceTest {

    @Mock
    private DealRepository repository ;

    @Mock
    private DealMapper mapper ;

    private DealService service ;

    private DealRequestDto dealRequestDto;

    private Deal deal ;

    @BeforeEach
    void setup(){
        service = new DealService(repository , mapper);
        dealRequestDto = new DealRequestDto( "d1" ,
                Currency.getInstance("MAD"),
                Currency.getInstance("USD"),
                LocalDateTime.now(),
                BigDecimal.valueOf(2000)
        );

        deal = new Deal(dealRequestDto.id(),
                dealRequestDto.fromCurrency(),
                dealRequestDto.toCurrency(),
                dealRequestDto.timestamp(),
                dealRequestDto.amount());
    }

    @Test
    void givenValidRequest_whenSave_thenReturnCreatedDeal(){
        given(mapper.toEntity(dealRequestDto)).willReturn(deal);
        given(repository.save(any(Deal.class))).willReturn(deal);
        given(mapper.toResponseEntity(deal)).willReturn(new DealResponseDto(dealRequestDto.id(),
                dealRequestDto.fromCurrency(),
                dealRequestDto.toCurrency(),
                dealRequestDto.timestamp(),
                dealRequestDto.amount()));

        DealResponseDto save = service.created(dealRequestDto);

        assertThat(save).isNotNull();
        assertThat(save.id()).isEqualTo(deal.getId());
        verify(repository).save(any(Deal.class));

    }

    @Test
    void givenDealIdAlreadyExists_whenSave_thenThrowDuplicatedDealIdException(){
        given(repository.existsById(dealRequestDto.id())).willReturn(true);

        assertThatExceptionOfType(DealIdException.class).isThrownBy(() -> service.created(dealRequestDto))
                .withMessage("Deal id already exists");
    }
}
