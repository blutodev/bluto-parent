package com.bluto.fc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class BaseService {
    @Autowired
    protected MessageSource messageSource;
}
