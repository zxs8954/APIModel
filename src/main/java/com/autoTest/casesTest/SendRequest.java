package com.autoTest.casesTest;


import com.autoTest.getdata.Data;
import com.autoTest.util.RequestUtil;
import com.jayway.jsonpath.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(invocationCount =1,threadPoolSize = 4)
public class SendRequest {
    @Test
    public void showMyTask() {
        String s = RequestUtil.doRequest(Data.getUrl(1), Data.getParam(1), Data.getMethod(1));
        Assert.assertEquals(JsonPath.read(s,"$.data.userLevel"),"尊享用户");
    }
    @Test(dependsOnMethods = "showMyTask")
    public void showMyTaskDetail(){
        String s = RequestUtil.doRequest(Data.getUrl(2), Data.getParam(2), Data.getMethod(2));
        int data=JsonPath.read(s, "$.taskDetail[0].processId");
        Assert.assertEquals(data,1834376);
        Data.insertDependData(2,"processId",data);
    }
    @Test(dependsOnMethods = "showMyTaskDetail")
    public void receivingTasks(){
        Data.updateParam(3,2);
        String s=RequestUtil.doRequest(Data.getUrl(3),Data.getParam(3),Data.getMethod(3));
        int i=JsonPath.read(s,"$.taskDetail[0].processId");
        Assert.assertEquals(i,1834376);
        int taskId=JsonPath.read(s,"$.taskDetail[0].taskId");
        Data.insertDependData(3,"taskId",taskId);
    }
    @Test
    public void recharge(){
        Data.updateParam(4,2);
        Data.updateParam(4,3);
        String s=RequestUtil.doRequest(Data.getUrl(4),Data.getParam(4),Data.getMethod(4));
    }
    @Test
    public void ifash(){
        String s=RequestUtil.doRequest(Data.getUrl(5),Data.getParam(5),Data.getMethod(5));
        Assert.assertEquals(JsonPath.read(s,"$.data.isashtest"),new Boolean(true));
    }

}
