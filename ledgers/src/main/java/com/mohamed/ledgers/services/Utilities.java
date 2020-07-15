package com.mohamed.ledgers.services;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohamed.ledgers.apiModels.ApiHeaders;

@Service
public class Utilities implements IUtilities {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Random RANDOM = new SecureRandom();
	private final String ALPHAPET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	@Autowired
	private ObjectMapper objMapper;
	
	@Value("${secret_key}")
	private String secretKey; // JWT Secret Key

	@Override
	public ApiHeaders GetHeaderData(HttpServletRequest request) {
		ApiHeaders headers = new ApiHeaders();
		try {
			String auth = request.getHeader("Authorization");

			auth = auth.replace("'", "");
			String[] au = auth.split(" ");
			headers.setAuthorization(au[1]);

			String payloadData = request.getHeader("payload_data");
			headers.setPayload_data(payloadData);

			String sessionToken = request.getHeader("session_token");

			sessionToken = sessionToken.replace("'", "");

			headers.setSession_token(sessionToken);

			logger.info("===================== Headers Data =====================");

			logger.info(objMapper.writeValueAsString(headers));
		} catch (Exception e) {
			headers.setStatus_code("1100");
			return headers;
		}
		logger.info("==========================================");
		return headers;

	}

	@Override
	public ApiHeaders GetSessionHeaderData(String Session) {
		ApiHeaders headers = new ApiHeaders();
		ObjectMapper mapper = new ObjectMapper();
		String s = this.secretKey;
		byte[] b = s.getBytes(StandardCharsets.US_ASCII);
		String Ses;
		try {
			Algorithm algorithm = Algorithm.HMAC256(b);
			JWTVerifier verifier = JWT.require(algorithm).acceptLeeway(1).build();
			DecodedJWT jwt = verifier.verify(Session);
			byte[] bs = Base64.getDecoder().decode(jwt.getPayload());
			Ses = new String(bs);
			headers = mapper.readValue(Ses, ApiHeaders.class);
			logger.info("===================== Session Data =====================");
			logger.info(objMapper.writeValueAsString(headers));
			logger.info("==========================================");
			return headers;

		} catch (JWTVerificationException e) {
			logger.error("ERROR: JWT Verification Error ", e);
			headers.setStatus_code(e.getMessage());
			return headers;
		} catch (Exception e) {
			headers.setStatus_code("1100");
			return headers;
		}
	}

	@Override
	public String generateRandomString(int length, String prefix) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHAPET.charAt(RANDOM.nextInt(ALPHAPET.length())));
		}

		return new String(prefix + returnValue);
	}

}
