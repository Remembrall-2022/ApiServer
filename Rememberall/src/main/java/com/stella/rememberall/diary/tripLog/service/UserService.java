package com.stella.rememberall.diary.tripLog.service;

import java.util.NoSuchElementException;

public interface UserService {
    void checkExists(Long userId) throws NoSuchElementException;
}
