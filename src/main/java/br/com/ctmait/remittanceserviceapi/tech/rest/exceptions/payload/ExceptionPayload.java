package br.com.ctmait.remittanceserviceapi.tech.rest.exceptions.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionPayload {
	private final String error;
	private final String errorDescription;
}
