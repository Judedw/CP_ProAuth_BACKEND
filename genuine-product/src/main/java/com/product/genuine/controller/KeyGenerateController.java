package com.product.genuine.controller;

import com.product.genuine.dto.response.SuccessResponse;
import com.product.genuine.util.GenerateKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * KeyGenerateController
 * Created by nuwan on 8/4/18.
 */
@RestController
public class KeyGenerateController {

    @Autowired
    private GenerateKeys generateKeys ;


    @GetMapping("${app.endpoint.keysCreate}")
    public SuccessResponse key(@PathVariable("appId") String appId) {
        SuccessResponse res = new SuccessResponse();
        if (null != appId && !appId.equals("")) {
            res.setData(generateKeys.keyGenerateAndReturnPublicKey(appId));
            return res;

        } else {

            res.setData("missing appId");
            return res;
        }

    }

}
