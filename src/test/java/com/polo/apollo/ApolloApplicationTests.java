package com.polo.apollo;

import com.polo.apollo.common.util.OkHttpUtil;
import com.polo.apollo.common.util.Utils;
import com.polo.apollo.entity.dto.SiteMapDto;
import com.polo.apollo.service.sytem.SiteMapService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApolloApplicationTests {

    @Autowired
    private SiteMapService siteMapService;

    @Test
    public void contextLoads() {
        List<SiteMapDto> list = new ArrayList<>();
        for (int i = 0 ; i < 10; i++) {
            SiteMapDto dto = new SiteMapDto();
            dto.setUrl("dasdsa"+i);
            dto.setLastMod(new Date());
            dto.setPriority(0.8);
            list.add(dto);
        }
        String xml = siteMapService.buildXml(list);
        System.out.println(xml);
    }

}
