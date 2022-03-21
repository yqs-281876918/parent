package org.mixed.exam.paper.service;

import org.junit.jupiter.api.Test;
import org.mixed.exam.paper.pojo.dto.IntelligentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class IntelligentServiceTest {
    @Test
    public void testIndividual()
    {
        IntelligentService.Individual individual = new IntelligentService.Individual(31);
        individual.setGene(28,1);
        individual.setGene(17,1);
        individual.printGene();
    }
    @Test
    public void testIndividuals()
    {
//        IntelligentService service = new IntelligentService();
//        List<Individual> individuals = new ArrayList<>();
//        service.initIndividual(individuals,64,100);
//        for(Individual i : individuals)
//        {
//            i.printGene();
//        }
    }
    @Test
    public void testDate() throws ParseException {
        String pastDateStr = "2018-03-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date past = sdf.parse(pastDateStr);
        long difference =  (System.currentTimeMillis()-past.getTime())/86400000;
        System.out.println(Math.abs(difference));
    }
    @Test
    public void testNormpdf()
    {
        IntelligentService service = new IntelligentService();
        //System.out.println(service.normpdf(1.0));
    }
    @Test
    public void test()
    {
        IntelligentService service = new IntelligentService();
        IntelligentParam param =new IntelligentParam();
        service.build(param);
    }
    @Autowired
    private IntelligentService intelligentService;
    @Test
    public void createChinesePaper()
    {
        IntelligentParam param = new IntelligentParam();
        param.setDifficulty(3.5);
        param.setCourseID("622dbb040913ed05f6402c8b");
        param.setKeys(Arrays.asList("622dcb956fdcc85405ef85ad", "623074271aabf7331f816aad", "62304bf274a387398923fe3b"));
        List<IntelligentParam.SubjectDistribution> distributions = new ArrayList<>();
        distributions.add(new IntelligentParam.SubjectDistribution("singleChoiceQuestion",5));
        distributions.add(new IntelligentParam.SubjectDistribution("readComprehension",3));
        distributions.add(new IntelligentParam.SubjectDistribution("completion",8));
        intelligentService.build(param);
    }
}