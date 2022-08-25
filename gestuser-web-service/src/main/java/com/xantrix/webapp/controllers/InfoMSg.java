package com.xantrix.webapp.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class InfoMSg {

    public LocalDate data;

    public String message;
}
