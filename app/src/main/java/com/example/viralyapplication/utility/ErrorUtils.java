package com.example.viralyapplication.utility;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
        public static ErrorMessageModel parseError(Response<?> response) {
            Converter<ResponseBody, ErrorMessageModel> converter =
                    NetworkProfile.getRetrofitInstance()
                            .responseBodyConverter(ErrorMessageModel.class, new Annotation[0]);

            ErrorMessageModel error;

            try {
                assert response.errorBody() != null;
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                return new ErrorMessageModel();
            }

            return error;
        }
}
