package com.polo.apollo;

import com.polo.apollo.common.util.OkHttpUtil;
import com.polo.apollo.common.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApolloApplicationTests {

    @Test
    public void contextLoads() {
        String str = OkHttpUtil.get("http://ip.taobao.com/service/getIpInfo.php?ip=49.65.226.83");
        Map<String, Object> json = Utils.json2Obj(str, Map.class);
        if (json != null) {
            Map<String, String> dataJSON = (Map<String, String>) json.get("data");
            if (dataJSON != null) {
                System.out.println();
            }
        }
    }

}
