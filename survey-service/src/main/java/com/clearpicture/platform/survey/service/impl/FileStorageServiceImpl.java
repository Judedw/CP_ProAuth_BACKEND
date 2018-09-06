package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.service.FileStorageService;
import com.sap.ecm.api.EcmService;
import com.sap.ecm.api.RepositoryOptions;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * FileStorageServiceImpl
 * Created by nuwan on 8/8/18.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {

    //private final Path fileStorageLocation;

    private Session openCmisSession = null;

    private static final String uniqueName = "clearpicture-documents";
    private static final  String secretKey = "nuw1nSAPH@n@";

    /*@Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }*/

    public String storeFile(MultipartFile file) throws IOException, ServletException {
        String objectId = createFolderAndDoc(file);
        return objectId;
    }

    /*public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }*/

    /**
     * Connect to the document store service in SAP Cloud Platform and create or look up sample repository.
     */
    @Override
    public Session connectToEcm() throws IOException,
            ServletException {
        // Get a handle to the service by performing a JNDI lookup; EcmService must be a <resource-ref> in the web.xml
        EcmService ecmService = null;
        String ecmServiceLookupName = "java:comp/env/" + "EcmService";
        try {
            InitialContext ctx = new InitialContext();
            ecmService = (EcmService) ctx.lookup(ecmServiceLookupName);
            //response.append("<p>Retrieved service instance for " + ecmServiceLookupName + "</p>");
        } catch (NamingException e) {
            throw new RuntimeException("Lookup of ECM service " + ecmServiceLookupName
                    + " via JNDI failed with reason: " + e.getMessage());
        }
        try {
            // Connect to ECM service accessing the repository
            openCmisSession = ecmService.connect(uniqueName, secretKey);
            // Continue if connection was successful
            //response.append("<p>Found an existing repository with name " + uniqueName + "</p>");
        } catch (CmisObjectNotFoundException e) {
            // If the session couldn't be created the repository is missing so create it now
            //response.append("<p>Could not find an existing repository with name " + uniqueName + ", creating one.</p>");

            e.printStackTrace();

            RepositoryOptions options = new RepositoryOptions();
            options.setUniqueName(uniqueName);
            options.setRepositoryKey(secretKey);
            options.setVisibility(RepositoryOptions.Visibility.PROTECTED);
            options.setMultiTenantCapable(true);
            ecmService.createRepository(options);

            // Now try again and connect to ECM service accessing the newly created repository
            openCmisSession = ecmService.connect(uniqueName, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connect to ECM service failed with reason: " + e.getMessage(), e);
        }

        // Print some information about the repository
        RepositoryInfo repositoryInfo = openCmisSession.getRepositoryInfo();
        /*if (repositoryInfo == null) {
            response.append("<p>Failed to get repository info!</p>");
        } else {
            response.append("<p>Product Name: " + repositoryInfo.getProductName() + "</p>");
            response.append("<p>Repository Id: " + repositoryInfo.getId() + "</p>");
            response.append("<p>Root Folder Id: " + repositoryInfo.getRootFolderId() + "</p>");
        }*/

        return openCmisSession;
    }


    /**
     * Create folder and document in root folder in sample repository if not yet done.
     */
    private String createFolderAndDoc(MultipartFile file)  {
        // If we didn't get a session fail with an appropriate message
        if (openCmisSession == null) {
            //response.append("<p>Cannot create folder or document, no connection to repository.</p>");
            try {
                connectToEcm();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
        // Get root folder from CMIS session
        Folder rootFolder = openCmisSession.getRootFolder();
        Document document = null;

        // Create new folder (requires at least type id and name of the folder)
        Map<String, String> folderProps = new HashMap<String, String>();
        folderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
        folderProps.put(PropertyIds.NAME, "Survey-Documents");
        try {
            rootFolder.createFolder(folderProps);
            //response.append("<p>A folder with name 'Clear-Picture-Documents' was created.</p>");
        } catch (CmisNameConstraintViolationException e) {
            // If the folder already exists, we get a CmisNameConstraintViolationException
            //response.append("<p>A folder with name 'My Folder' already exists, nothing created.</p>");
        }

        // Create new document (requires at least type id and name of the document and some content)
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, file.getOriginalFilename());
        //properties.put(PropertyIds.NAME, "HelloWorld.txt");


        byte[] fileContent = new byte[0];
        try {
            fileContent = file.getBytes();

        InputStream stream = new ByteArrayInputStream(file.getBytes());
        ContentStream contentStream = openCmisSession.getObjectFactory().createContentStream(file.getOriginalFilename(),
                fileContent.length, "text/plain; charset=UTF-8", stream);


        try {
            document = rootFolder.createDocument(properties, contentStream, VersioningState.NONE);
            //response.append("<p>A document with id "+document.getId());
            //response.append("was created.</p>");
        } catch (CmisNameConstraintViolationException e) {
            e.printStackTrace();
            throw new ComplexValidationException("imageName", "ProductCreateRequest.imageName already exist");
            // If the document already exists, we get a CmisNameConstraintViolationException
            //response.append("<p>A document with name 'HelloWorld.txt' already exists, nothing created.</p>");
        }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(document != null) {
            return document.getId();
        } else {
            return null;
        }

    }

}
