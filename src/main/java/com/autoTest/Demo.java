package com.autoTest;

import com.autoTest.getdata.Data;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    @Test
    public void aa() {
        Map<String,String> map=new HashMap<String, String>();
        map.put("stbId","111800609120F0C24CD1A3FF");
        Data.addParam(4,map);
    }
}
