package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.product.service.FileStorageService;
import com.clearpicture.platform.product.util.MediaTypeUtils;
import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * FileUploadController
 * Created by nuwan on 8/8/18.
 */
@Slf4j
@RestController
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    private Session openCmisSession = null;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file")MultipartFile file) {

        System.out.println("original file name :"+file.getOriginalFilename());

        StringBuilder response = new StringBuilder();

        try {
            // Connect to the document service in SAP Cloud Platform
            connectToEcm(response);

            // List content of root folder
            listRootFolder(response);

            // Create a folder in the root folder and upload a document
            createFolderAndDoc(response,file);
        } catch (Exception e) {
            response.append("Access of ECM service failed with reason: " + e.getMessage());
            log.error("Access of ECM service failed", e);
        }

        File convertFile = new File(file.getOriginalFilename());
        /*try
        {
            String uniqueName = "clearpicture-documents";
            String secretKey = "nuw1nSAPH@n@";

            com.sap.ecm.api.EcmService ecmSvc = null;

            InitialContext ctx = new InitialContext();
            String lookupName = "java:comp/env/" + "EcmService";
            try
            {
                ecmSvc = (EcmService) ctx.lookup(lookupName);
                //Session openCmisSession = null;

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
        }*/

        response.append("original file name :"+file.getOriginalFilename());

        return response.toString();
    }



    /**
     * Connect to the document store service in SAP Cloud Platform and create or look up sample repository.
     */
    private void connectToEcm(StringBuilder response) throws IOException,
            ServletException {
        // Get a handle to the service by performing a JNDI lookup; EcmService must be a <resource-ref> in the web.xml
        EcmService ecmService = null;
        String ecmServiceLookupName = "java:comp/env/" + "EcmService";
        try {
            InitialContext ctx = new InitialContext();
            ecmService = (EcmService) ctx.lookup(ecmServiceLookupName);
            response.append("<p>Retrieved service instance for " + ecmServiceLookupName + "</p>");
        } catch (NamingException e) {
            throw new RuntimeException("Lookup of ECM service " + ecmServiceLookupName
                    + " via JNDI failed with reason: " + e.getMessage());
        }

        // Create a key secured repository identified by a unique name and a secret key (minimum length 10 characters)
        //String uniqueName = "SampleRepository";
        //String secretKey = "abcdef0123456789";

        String uniqueName = "clearpicture-documents";
        String secretKey = "nuw1nSAPH@n@";
        try {
            // Connect to ECM service accessing the repository
            openCmisSession = ecmService.connect(uniqueName, secretKey);

            // Continue if connection was successful
            response.append("<p>Found an existing repository with name " + uniqueName + "</p>");
        } catch (CmisObjectNotFoundException e) {
            // If the session couldn't be created the repository is missing so create it now
            response.append(
                    "<p>Could not find an existing repository with name " + uniqueName + ", creating one.</p>");
            RepositoryOptions options = new RepositoryOptions();
            options.setUniqueName(uniqueName);
            options.setRepositoryKey(secretKey);
            options.setVisibility(RepositoryOptions.Visibility.PROTECTED);
            options.setMultiTenantCapable(true);
            ecmService.createRepository(options);

            // Now try again and connect to ECM service accessing the newly created repository
            openCmisSession = ecmService.connect(uniqueName, secretKey);
        } catch (Exception e) {
            throw new RuntimeException("Connect to ECM service failed with reason: " + e.getMessage(), e);
        }

        // Print some information about the repository
        RepositoryInfo repositoryInfo = openCmisSession.getRepositoryInfo();
        if (repositoryInfo == null) {
            response.append("<p>Failed to get repository info!</p>");
        } else {
            response.append("<p>Product Name: " + repositoryInfo.getProductName() + "</p>");
            response.append("<p>Repository Id: " + repositoryInfo.getId() + "</p>");
            response.append("<p>Root Folder Id: " + repositoryInfo.getRootFolderId() + "</p>");
        }
    }

    /**
     * List content of root folder in sample repository.
     */
    private void listRootFolder(StringBuilder response) throws IOException {
        // If we didn't get a session fail with an appropriate message
        if (openCmisSession == null) {
            response.append("<p>Cannot list folders, no connection to repository.</p>");
            return;
        }

        // Get root folder from CMIS session
        Folder rootFolder = openCmisSession.getRootFolder();

        // List content of root folder
        ItemIterable<CmisObject> children = rootFolder.getChildren();
        if (children.iterator().hasNext()) {
            response.append(
                    "The root folder of the repository with id " + rootFolder.getId()
                            + " contains the following objects:");
            response.append("<ul>");
            for (CmisObject o : children) {
                response.append("<li>" + o.getName());
                if (o instanceof Folder) {
                    response.append(", type: Folder, createdBy: " + o.getCreatedBy() + "</li>");
                } else {
                    Document doc = (Document) o;
                    response.append(
                            ", type: Document,  createdBy: " + o.getCreatedBy() + " filesize: "
                                    + doc.getContentStreamLength() + " bytes" + "</li>");
                }
            }
            response.append("</ul>");
        } else {
            response.append(
                    "The root folder of the repository with id " + rootFolder.getId() + " contains no objects.");
        }
    }

    /**
     * Create folder and document in root folder in sample repository if not yet done.
     */
    private void createFolderAndDoc(StringBuilder response,MultipartFile file) throws IOException {
        // If we didn't get a session fail with an appropriate message
        if (openCmisSession == null) {
            //response.append("<p>Cannot create folder or document, no connection to repository.</p>");
            return;
        }

        // Get root folder from CMIS session
        Folder rootFolder = openCmisSession.getRootFolder();

        // Create new folder (requires at least type id and name of the folder)
        Map<String, String> folderProps = new HashMap<String, String>();
        folderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        folderProps.put(PropertyIds.NAME, "Clear-Picture-Documents");
        try {
            rootFolder.createFolder(folderProps);

            response.append("<p>A folder with name 'Clear-Picture-Documents' was created.</p>");
        } catch (CmisNameConstraintViolationException e) {
            // If the folder already exists, we get a CmisNameConstraintViolationException
            response.append("<p>A folder with name 'My Folder' already exists, nothing created.</p>");
        }

        // Create new document (requires at least type id and name of the document and some content)
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, file.getOriginalFilename());
        //properties.put(PropertyIds.NAME, "HelloWorld.txt");


        byte[] helloContent = file.getBytes();
        InputStream stream = new ByteArrayInputStream(file.getBytes());
        ContentStream contentStream = openCmisSession.getObjectFactory().createContentStream(file.getOriginalFilename(),
                helloContent.length, "text/plain; charset=UTF-8", stream);
        try {
            Document document = rootFolder.createDocument(properties, contentStream, VersioningState.NONE);
            response.append("<p>A document with id "+document.getId());
            response.append("was created.</p>");
        } catch (CmisNameConstraintViolationException e) {
            // If the document already exists, we get a CmisNameConstraintViolationException
            response.append(
                    "<p>A document with name 'HelloWorld.txt' already exists, nothing created.</p>");
        }
    }

    private static final String DEFAULT_FILE_NAME = "5.jpg";
    private static final String DIRECTORY = "/home/nuwan/Desktop/images";

    @Autowired
    private ServletContext servletContext;

    //@GetMapping("/downloadFile/{objectId}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String objectId) throws IOException, ServletException {

        String name = null;
        String type = null;
        InputStream stream;
        OutputStream outputStream = null;

        MediaType mediaType = null;
        File file = null;

        StringBuilder response = new StringBuilder();
        // Connect to the document service in SAP Cloud Platform
        connectToEcm(response);

        if (openCmisSession != null) {

            Document doc = (Document) openCmisSession.getObject(objectId);
            ContentStream content = doc.getContentStream();
            type = content.getMimeType();
            name = content.getFileName();
            int length = (int) content.getLength();
            stream = content.getStream();

            mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, name);
            System.out.println("fileName: " + name);
            System.out.println("mediaType: " + mediaType);

            Folder rootFolder = openCmisSession.getRootFolder();

            file = new File(name);
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = stream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } else {

            connectToEcm(response);
        }
        //File file = new File(DIRECTORY + "/" + DEFAULT_FILE_NAME);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);

    }

    private void ioCopy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[1 << 13];
        int read;
        while ((read = in.read(buf)) >= 0)
            out.write(buf, 0, read);
    }


    @GetMapping("/download1")
    public ResponseEntity<InputStreamResource> downloadFile1(
            @RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {

        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        System.out.println("fileName: " + fileName);
        System.out.println("mediaType: " + mediaType);

        File file = new File(DIRECTORY + "/" + fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                        // Content-Type
                .contentType(mediaType)
                        // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }

}
