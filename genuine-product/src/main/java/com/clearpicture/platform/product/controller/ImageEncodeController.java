package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.product.entity.model.Image;
import com.clearpicture.platform.product.util.UtilBase64Image;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ImageEncodeController
 * Created by nuwan on 8/8/18.
 */
@RestController
public class ImageEncodeController {

    @GetMapping("${app.endpoint.imagesDecode}")
    public Image get(@RequestParam("name") String name) {
        System.out.println(String.format("/GET info: imageName = %s", name));
        String imagePath = "/opt/project-root/java/personal-project/Shalindra/SAP/Image/encoder/" + name;

        System.out.println(" Image Path :"+imagePath);
        String imageBase64 = UtilBase64Image.encoder(imagePath);

        if(imageBase64 != null){
            Image image = new Image(name, imageBase64);
            return image;
        }
        return null;
    }

}
