package com.clownstore.cart.feign;

import com.clownstore.cart.exception.ProductNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

public class OpenFeignError implements ErrorDecoder {

    private ErrorDecoder errorDecoder;

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        switch (status) {
            case NOT_FOUND:
                return new ProductNotFoundException("Product not found");
        }

        return errorDecoder.decode(methodKey, response);
    }

}
