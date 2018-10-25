package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Element;
import com.clearpicture.platform.survey.entity.FutureSurvey;
import com.clearpicture.platform.survey.entity.Page;
import com.clearpicture.platform.survey.repository.FutureSurveyRepository;
import com.clearpicture.platform.survey.service.FutureSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


/**
 * created by Raveen -  18/10/24
 * FutureSurveyController - Handling every advanced surveys & e votes
 */

@Transactional
@Service
public class FutureSurveyServiceImpl implements FutureSurveyService {


    @Autowired
    private FutureSurveyRepository futureSurveyRepository;

    @Override
    public FutureSurvey save(FutureSurvey futureSurvey) {

        System.out.println("TITLE : " + futureSurvey.getTitle());
        System.out.println("client id  : " + futureSurvey.getClientId());
        System.out.println("JSON content : " + futureSurvey.getJsonContent());

        for (Page page : futureSurvey.getPages()) {
            //System.out.println("PAGE NAME : " + page.getName());
            page.setFutureSurvey(futureSurvey);

            Set<Element> elementSet = page.getElements();
            if (!(elementSet == null)) {
                for (Element ele : elementSet) {
                    ele.setPage(page);

                    /*System.out.println("......ELE NAME : " + ele.getName());
                    System.out.println("......ELE TYPE : " + ele.getType());
                    System.out.println("......ELE ID : " + ele.getId());
                    System.out.println("......ELE QID : " + ele.getQuestionId());*/
                }
            }

        }




        return futureSurveyRepository.save(futureSurvey);
    }
}
