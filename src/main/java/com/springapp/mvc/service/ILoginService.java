package com.springapp.mvc.service;

import com.springapp.mvc.commons.Result;

public interface ILoginService {
    public Result codeForSessionKey(String code);
}
