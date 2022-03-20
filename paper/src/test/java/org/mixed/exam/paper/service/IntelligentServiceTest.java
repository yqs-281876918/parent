package org.mixed.exam.paper.service;

import org.junit.Test;
import org.mixed.exam.paper.pojo.dto.IntelligentParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntelligentServiceTest {
    @Test
    public void testIndividual()
    {
        Individual individual = new Individual(31);
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
        //param.setSubjectCount(30);
        service.build(param);
    }
}