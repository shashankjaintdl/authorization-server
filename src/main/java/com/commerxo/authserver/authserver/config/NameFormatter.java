package com.commerxo.authserver.authserver.config;

import org.springframework.format.Formatter;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.util.Locale;

public class NameFormatter implements Formatter<String> {

    @Override
    public String print(String input, Locale locale) {
        return formatName(input, locale);
    }

    @Override
    public String parse(String input, Locale locale) throws ParseException {
        return formatName(input, locale);
    }

    private String formatName(String input, Locale locale) {
        return StringUtils.replace(input, " ", ",");
    }
}