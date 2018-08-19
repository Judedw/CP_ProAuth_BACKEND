package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.product.service.FileStorageService;
import com.sap.ecm.api.EcmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * FileUploadController
 * Created by nuwan on 8/8/18.
 */
@Slf4j
@RestController
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file")MultipartFile file) {

        System.out.println("original file name :"+file.getOriginalFilename());

        StringBuilder response = new StringBuilder();

                File convertFile = new File(file.getOriginalFilename());

        try
        {
            String uniqueName = "clearpicture-documents";
            String secretKey = "nuw1nSAPH@n@";

            com.sap.ecm.api.EcmService ecmSvc = null;

            InitialContext ctx = new InitialContext();
            String lookupName = "java:comp/env/" + "EcmService";
            try
            {
                ecmSvc = (EcmService) ctx.lookup(lookupName);
                Session openCmisSession = null;

                //connect to my repository
                openCmisSession = ecmSvc.connect(uniqueName,secretKey);

                if(openCmisSession != null) {
                    response.append("You have now connected to CMS :"+openCmisSession.getRepositoryInfo().getId());
                    System.out.println("You have now connected to CMS :"+openCmisSession.getRepositoryInfo().getId());
                } else {
                    response.append("You can not connected to CMS :"+openCmisSession);
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        response.append("original file name :"+file.getOriginalFilename());

        return response.toString();
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
