package com.progressoft.fxclusteredwarehouse.models.entitis;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "deals")
public class Deal {
    @Id
    private String id;

    @NotNull
    private Currency fromCurrency ;

    @NotNull
    private Currency toCurrency;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private BigDecimal amount;

}
