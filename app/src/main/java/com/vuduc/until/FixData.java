package com.vuduc.until;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VuDuc on 9/27/2017.
 */

public class FixData {
    public static List<String>  addTypeData() {
        List<String> typeData = new ArrayList<>();

        String m1 = new String("Nhiệt độ");
        typeData.add(m1);
        String m2 = new String("Độ ẩm");
        typeData.add(m2);
        String m3 = new String("Độ ẩm đất");
        typeData.add(m3);
        String m4 = new String("Ánh sáng");
        typeData.add(m4);

        return typeData;
    }

    public static List<String> addTypeDate() {
        List<String> typeDate = new ArrayList<>();

        String t1 = new String("Trong ngày");
        typeDate.add(t1);
        String t2 = new String("Khoảng ngày");
        typeDate.add(t2);

        return typeDate;
    }

    public static List<String> labelsLineChartDay() {
        ArrayList<String> labels = new ArrayList<String>();
        for(int i=0;i<24;i++){
            String hour = i+"h";
            labels.add(hour);
        }
        return labels;
    }
}
