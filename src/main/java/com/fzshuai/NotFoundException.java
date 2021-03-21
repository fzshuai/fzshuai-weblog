package com.fzshuai;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author 软件二班傅同学
<<<<<<< HEAD
 * @date 2021-01-18 12:39
=======
 * @description TODO
 * @date 2021-02-05 21:01
>>>>>>> d30a2ee (项目第一次提交)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
