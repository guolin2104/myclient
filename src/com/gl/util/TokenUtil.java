package com.gl.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspWriter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import net.sf.json.JSONSerializer;

public class TokenUtil {

	// ������Կ�ͻ��˲���֪��
	public static String SECRET = "FreeMaNong";

	public static String createToken(String userName, String sn) throws UnsupportedEncodingException {
		// ǩ������ʱ��
		Date iatDate = new Date();

		// ����ǩ������ʱ�䣬1��
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.HOUR, 24 * 7);
		Date expiresDate = nowTime.getTime();

		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		String token = JWT.create()
				.withHeader(map)
				.withClaim("name", userName)
				.withClaim("sn", sn)
				.withIssuedAt(iatDate)
				.withExpiresAt(expiresDate)
				.sign(Algorithm.HMAC256(SECRET));

		return token;
	}

	public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
		// �ù�����Կ������֤
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
		DecodedJWT jwt = null;
		Map<String, Claim> map = null;
		try {
			jwt = verifier.verify(token);
			map = jwt.getClaims();
		} catch (TokenExpiredException e) {
			map = null;
			e.printStackTrace();
		} catch (Exception e) {
			map = null;
			e.printStackTrace();
			throw new RuntimeException("��¼ƾ֤�ѹ��ڣ������µ�¼");
		}

		return map;
	}
}
