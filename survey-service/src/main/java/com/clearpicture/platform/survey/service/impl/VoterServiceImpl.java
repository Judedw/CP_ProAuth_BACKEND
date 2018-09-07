package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Voter;
import com.clearpicture.platform.survey.repository.VoterRepository;
import com.clearpicture.platform.survey.service.VoterService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * VoterServiceImpl
 * Created by nuwan on 9/6/18.
 */
@Service
public class VoterServiceImpl implements VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Override
    public List<Voter> uploadFile(MultipartFile multipartFile,Long clientId) throws IOException {
        File file = convertMultiPartToFile(multipartFile);

        List<Voter> mandatoryMissedList = new ArrayList<Voter>();

        try (Reader reader = new FileReader(file);) {
            @SuppressWarnings("unchecked")
            CsvToBean<Voter> csvToBean = new CsvToBeanBuilder<Voter>(reader).withType(Voter.class)
                    .withIgnoreLeadingWhiteSpace(true).build();
            List<Voter> voterList = csvToBean.parse();

            Iterator<Voter> voterListClone = voterList.iterator();

            while (voterListClone.hasNext()) {

                Voter voter = voterListClone.next();

                if (voter.getName() == null || voter.getEmail().isEmpty()
                        || voter.getIdentityNumber() == null || voter.getBatchNumber() == null) {
                    mandatoryMissedList.add(voter);
                    voterListClone.remove();
                }
            }

            voterList.forEach(voter -> {
                voter.setClientId(clientId);
            });

            voterRepository.saveAll(voterList);
        }
        return mandatoryMissedList;

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
