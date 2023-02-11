package com.pro2111.beans;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.pro2111.entities.Bill;
import com.pro2111.entities.CartDetail;

import lombok.Data;

@Data
public class BuyRequest {
    @NotNull
    private Bill bill;
    @NotEmpty
    private List<CartDetail> cartDetails;
}
