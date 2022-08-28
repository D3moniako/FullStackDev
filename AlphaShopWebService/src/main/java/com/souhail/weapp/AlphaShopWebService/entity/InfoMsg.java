package com.souhail.weapp.AlphaShopWebService.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class InfoMsg {
    public LocalDate data;

    public String message;
}
