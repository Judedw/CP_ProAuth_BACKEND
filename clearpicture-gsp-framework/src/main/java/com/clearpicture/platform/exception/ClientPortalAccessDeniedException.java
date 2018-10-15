package com.clearpicture.platform.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * @author Virajith
 *
 */
public class ClientPortalAccessDeniedException extends AuthenticationException {

	private static final long serialVersionUID = 7359084794470084789L;

	public ClientPortalAccessDeniedException() {
		super("Client portal access is denied");
	}

}
