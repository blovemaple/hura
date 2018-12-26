package com.github.blovemaple.hura.programeto;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WXSnsService {

	public static WXSnsService create() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.weixin.qq.com/sns/")
				.addConverterFactory(JacksonConverterFactory.create()).build();
		return retrofit.create(WXSnsService.class);
	}

	@GET("jscode2session")
	Call<WXCode2SessionResponse> jscode2session( //
			@Query("appid") String appid, //
			@Query("secret") String secret, //
			@Query("js_code") String jsCode, //
			@Query("grant_type") String grantType);
}
