package com.hiramexpress.service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hiramexpress.dao.CheckRecordDao;
import com.hiramexpress.domain.CheckRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CheckRecordService {

    private final Logger logger = LoggerFactory.getLogger(CheckRecordService.class);

    @Resource
    CheckRecordDao checkRecordDao;

    public int addCheckRecord(CheckRecord checkRecord) {
        return checkRecordDao.insertCheckRecords(checkRecord);
    }

    public JSONArray findCheckRecords() {
        List<CheckRecord> records = checkRecordDao.selectACheckRecords();
        JSONArray result = new JSONArray();
        if (records.size() > 0) {

            List<CheckRecord> successRecords = new ArrayList<>();    // 成功的记录
            List<CheckRecord> failureRecords = new ArrayList<>();    // 失败的记录

            JSONObject successJ = new JSONObject();
            JSONObject failureJ = new JSONObject();
            for (CheckRecord checkRecord : records) {
                if (checkRecord.getCheckResult() >= 0) {
                    successRecords.add(checkRecord);
                } else {
                    failureRecords.add(checkRecord);
                }
            }

            JSONArray successJChildren = new JSONArray();
            Map<String, List<CheckRecord>> successJChildrenJChildren = new LinkedHashMap<>();
            for (CheckRecord checkRecord: successRecords) {
                String platform = checkRecord.getCheckPlatform();
                if (!successJChildrenJChildren.containsKey(platform)) {
                    successJChildrenJChildren.put(platform, new ArrayList<>());
                }
                successJChildrenJChildren.get(platform).add(checkRecord);
            }

            JSONObject successJChildrenJ;

            JSONArray finalArr;

            for (String platform : successJChildrenJChildren.keySet()) {
                List<CheckRecord> recordList = successJChildrenJChildren.get(platform);
                successJChildrenJ = new JSONObject();
                successJChildrenJ.put("name", platform);
                successJChildrenJ.put("value", recordList.size());

                finalArr = new JSONArray();

                Map<String, Integer> finalMap = new LinkedHashMap<>();
                assembleFinalMap(recordList, finalMap);

                assembleFinalArr(finalArr, finalMap);

                successJChildrenJ.put("children", finalArr);
                successJChildren.add(successJChildrenJ);
            }


            JSONArray failureJChildren = new JSONArray();
            Map<String, List<CheckRecord>> failureJChildrenJChildren = new LinkedHashMap<>();
            for (CheckRecord checkRecord: failureRecords) {
                String reason = checkRecord.getCheckReason();
                if (!failureJChildrenJChildren.containsKey(reason)) {
                    failureJChildrenJChildren.put(reason, new ArrayList<>());
                }
                failureJChildrenJChildren.get(reason).add(checkRecord);
            }

            JSONObject failureJChildrenJ;

            for (String reason : failureJChildrenJChildren.keySet()) {
                List<CheckRecord> recordList = failureJChildrenJChildren.get(reason);
                failureJChildrenJ = new JSONObject();
                failureJChildrenJ.put("name", reason);
                failureJChildrenJ.put("value", recordList.size());

                finalArr = new JSONArray();

                Map<String, Integer> finalMap = new LinkedHashMap<>();
                assembleFinalMap(recordList, finalMap);

                assembleFinalArr(finalArr, finalMap);

                if (!StringUtils.isEmpty(recordList.get(0).getCheckShipperCode())) {
                    failureJChildrenJ.put("children", finalArr);
                }
                failureJChildren.add(failureJChildrenJ);
            }

            successJ.put("name", "成功");
            successJ.put("value", successRecords.size());
            successJ.put("children", successJChildren);
            failureJ.put("name", "失败");
            failureJ.put("value", failureRecords.size());
            failureJ.put("children", failureJChildren);
            result.add(successJ);
            result.add(failureJ);
        }
        return result;
    }

    private void assembleFinalArr(JSONArray finalArr, Map<String, Integer> finalMap) {
        JSONObject finalJ;
        for (String express : finalMap.keySet()) {
            finalJ = new JSONObject();
            finalJ.put("name", express);
            finalJ.put("value", finalMap.get(express));
            finalArr.add(finalJ);
        }
    }

    private void assembleFinalMap(List<CheckRecord> recordList, Map<String, Integer> finalMap) {
        for (CheckRecord checkRecord : recordList) {
            String checkShipperCode = checkRecord.getCheckShipperCode();
            if (!finalMap.containsKey(checkShipperCode)) {
                finalMap.put(checkShipperCode, 0);
            }
            finalMap.put(checkShipperCode, finalMap.get(checkShipperCode) + 1);
        }
    }
}
