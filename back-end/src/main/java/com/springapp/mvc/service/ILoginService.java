package com.springapp.mvc.service;

import com.springapp.mvc.dto.Result;

public interface ILoginService {
    public Result codeForSessionKey(String code);
}
