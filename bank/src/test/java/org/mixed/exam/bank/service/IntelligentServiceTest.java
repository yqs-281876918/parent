package org.mixed.exam.bank.service;

import org.junit.jupiter.api.Test;
import org.mixed.exam.bank.pojo.dto.IntelligentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IntelligentServiceTest {

    @Autowired
    private IntelligentService intelligentService;
    @Test
    void buildTest() {
        IntelligentParam param = new IntelligentParam();
        param.setBeginDay("2000-2-5 00:00:00");
        param.setEndDay("2022-9-28 00:00:00");
        param.setDifficulty(4);
        param.setCourseID("622dbb040913ed05f6402c8b");
        Map<String,Integer> distribution = new HashMap<>();
        distribution.put("singleChoiceQuestion",5);
        distribution.put("completion",3);
        distribution.put("comprehensiveQuestion",2);
        List<String> keys = Arrays.asList("622dcb956fdcc85405ef85ad","623074271aabf7331f816aad","6230776f1aabf7331f816aae");
        param.setKeys(keys);
        param.setDistribution(distribution);
        intelligentService.build(param);
    }
}