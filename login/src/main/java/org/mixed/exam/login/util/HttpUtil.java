package org.mixed.exam.login.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class HttpUtil
{
    private static Random random = new Random(System.currentTimeMillis());
    private static Map<String,String> cache = new HashMap<>();
    private static DiscoveryClient discoveryClient;

    @Autowired
    private void init(DiscoveryClient discoveryClient)
    {
        HttpUtil.discoveryClient=discoveryClient;
    }
    public static String getHostPort(String serviceName)
    {
        if(cache.containsKey(serviceName))
        {
            return cache.get(serviceName);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        EurekaDiscoveryClient.EurekaServiceInstance instance = (EurekaDiscoveryClient.EurekaServiceInstance) instances.get(random.nextInt(instances.size()));
        cache.put(serviceName,instance.getInstanceInfo().getIPAddr()+":"+instance.getPort());
        return cache.get(serviceName);
    }
    public static String getGatewayHostPort()
    {
        return getHostPort("gateway-service");
    }
    public static String buildRedirectUrl(String hostPort,String path)
    {
        if(path.charAt(0)=='/')
        {
            if(path.length()>=2)
            {
                path=path.substring(1);
            }
            else
            {
                path="";
            }
        }
        return "redirect:http://"+hostPort+"/"+path;
    }
}
