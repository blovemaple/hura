package com.github.blovemaple.hura.programeto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "loginkey is invalid")
public class InvalidLoginKeyException extends Exception {
}
