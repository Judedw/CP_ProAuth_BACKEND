package com.clearpicture.platform.survey.util;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

/**
 * MediaTypeUtils
 * Created by nuwan on 9/4/18.
 */
public class MediaTypeUtils {

    // abc.zip
    // abc.pdf,..
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
