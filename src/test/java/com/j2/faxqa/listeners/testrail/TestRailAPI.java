package com.j2.faxqa.listeners.testrail;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRailAPI
{
    private static String user = System.getProperty("truser");
    private static String auth = System.getProperty("trpw");

    public static APIClient client()
    {
        APIClient client = new APIClient("https://testrail.test.j2noc.com");
        client.setUser(user);
        client.setPassword(auth);
        return client;
    }

    public static JSONObject getCase(int p_caseId) throws Exception
    {
        APIClient client = TestRailAPI.client();
        JSONObject c = (JSONObject) client.sendGet("get_case/" + p_caseId);
        System.out.println(c);
        return c;
    }

    public static JSONObject addResult(int p_statusId, String p_comment, int p_runId, int p_caseId) throws Exception
    {
        APIClient client = TestRailAPI.client();
        Map data = new HashMap();
        data.put("status_id", p_statusId);
        data.put("comment", p_comment);
        List dataList = new ArrayList();

        dataList.add(data);

        System.out.println("\n\nP_runId: " + p_runId);
        JSONObject r = (JSONObject) client.sendPost("add_result_for_case/" + p_runId + "/" + p_caseId, data);
        System.out.println("\n\n\nJSONObject: " + r);
        return r;
    }


}
