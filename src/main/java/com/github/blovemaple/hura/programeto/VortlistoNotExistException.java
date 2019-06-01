package com.github.blovemaple.hura.programeto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "vortlisto not found")
public class VortlistoNotExistException extends Exception {
}
