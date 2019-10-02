package com.polo.apollo.entity.count;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baoqianyong
 * @date 2019/10/2
 */
@Getter
public class CountDatas {
    private List<String> types;
    private List<Integer> counts;

    private CountDatas() {}

    public static CountDatas getCountData(List<String> types, List<CountData> countDataList) {

        Integer count = null;
        CountDatas countData = new CountDatas();
        countData.types = new ArrayList<>();
        countData.counts = new ArrayList<>();
        Map<String, Integer> values = convertToMap(countDataList);
        for (String day : types) {
            count = values.getOrDefault(day, 0);
            countData.counts.add(count == null ? 0 : count);
            countData.types.add(day);
        }
        return countData;
    }

//    public String getTypes() {
//        char ch = ',';
//        StringBuffer sb = new StringBuffer("[");
//        for (String type :  types) {
//            sb.append(ch).append("'").append(type).append("'");
//        }
//        if (sb.charAt(1) == ch) {
//            sb.deleteCharAt(1);
//        }
//        sb.append("]");
//        return "";
//    }

    private static Map<String, Integer> convertToMap(List<CountData> countDataList) {
        Map<String, Integer> map = new HashMap<>(countDataList.size());
        for (CountData data : countDataList) {
            if (data == null) {
                continue;
            }
            map.put(data.getType(), data.getNum());
        }
        return map;
    }
}
