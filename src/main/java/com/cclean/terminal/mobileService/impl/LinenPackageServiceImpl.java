package com.cclean.terminal.mobileService.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cclean.terminal.entity.PageMo;
import com.cclean.terminal.mobileService.LinenPackageService;
import com.cclean.terminal.model2.LinenPackage;
import com.cclean.terminal.model2.LinenPackageRecord;
import com.cclean.terminal.model2.LinenPackageStacount;
import com.cclean.terminal.model2.LinenPackageStatistics;
import com.cclean.terminal.vo.LinenPackageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yulq
 * @create 2018-05-11 9:33
 * @desc
 **/
@Service
public class LinenPackageServiceImpl implements LinenPackageService {

    @Value("${linen.url}")
    private String linenUrl;

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${cloud.url}")
    private String cloudUrl;

    /**
     * 布草袋登记
     *
     * @param token
     * @param codes
     * @param type
     * @param color
     * @param batch
     * @return
     */
    @Override
    public List<String> register(String token, List<String> codes, int type, String color, String batch) {
        List<String> list = new ArrayList<>();
//        Set<String> set = new HashSet<>(codes);
        list.add("CODE0000123");
        list.add("CODE0000123");
        list.add("CODE0000123");
        list.add("CODE0000123");
        return list;

    }

    /**
     * 修改布草袋的基础信息
     *
     * @param token
     * @param packge
     * @return
     */
    @Override
    public boolean update(String token, LinenPackageVO packge) {
        String url = cloudUrl +"";
        JSONObject param = new JSONObject();


        return true;
    }

    /**
     * 修改布草袋流转状态
     *
     * @param token
     * @param codes
     * @param linentype    类型
     * @param usetype  状态
     * @param userId   使用人
     * @param hotelId 酒店ID
     * @param pointId 配送点ID
     * @return 修改成功
     */
    @Override
    public boolean circulate(String token, List<String> codes, int linentype, int usetype, String userId, String hotelId, String pointId) {


        return true;
    }

    /**
     * 布草袋分页查询
     *
     * @param token
     * @param linenPackageVO
     * @return
     */
    @Override
    public PageMo<LinenPackage> pagePackage(String token, LinenPackageVO linenPackageVO) {
        JSONObject param = JSON.parseObject(JSONObject.toJSONString(linenPackageVO));
        String url = cloudUrl + "";
        List<LinenPackage> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LinenPackage bag = new LinenPackage();
            bag.setId("123456" + i);
            bag.setCode("BAG00000" + i);
            bag.setRfid("BAG00000" + i);
            bag.setBatch("10000" + i);
            bag.setColor("红色");
            bag.setLinenType(1);
            bag.setLinenState(1);
            bag.setWashNum(10);
            bag.setUsetype(1);
            bag.setHotelId("053db666f08c402fb58e079c1d81ab79");
            bag.setHotelName("如家金沙江店");
            bag.setPointId("095abce4eed845f7afc3b9e0dbe92843");
            bag.setPointName("2F");
            bag.setReceiveTime("2018-05-05 14:05:55");
            bag.setReturnTime("2018-05-10 14:05:55");
            bag.setUserId("0aaa0e1390324b0b8a4188a194353257");
            bag.setUserName("张三");
            bag.setCreator("0aaa0e1390324b0b8a4188a194353257");
            bag.setCreatorName("张三");
            bag.setCreateTime("2018-05-05 14:05:55");
            bag.setModifyTime("2018-05-05 14:05:55");
            list.add(bag);
        }
        return new PageMo<>(list, 1, 3, 3);
    }

    /**
     * 根据IDS批量查询布草袋
     *
     * @param token
     * @param codes
     * @return
     */
    @Override
    public List<LinenPackage> listPackage(String token, List<String> codes) {
        List<LinenPackage> list = new ArrayList<>();
        if (codes == null || codes.size() == 0) {
            return list;
        }
        for (int i = 0; i < 3; i++) {
            LinenPackage bag = new LinenPackage();
            bag.setId("123456" + i);
            bag.setCode("BAG00000" + i);
            bag.setRfid("BAG00000" + i);
            bag.setBatch("10000" + i);
            bag.setColor("蓝色");
            bag.setLinenType(2);
            bag.setLinenState(1);
            bag.setWashNum(5);
            bag.setUsetype(2);
            bag.setHotelId("053db666f08c402fb58e079c1d81ab79");
            bag.setHotelName("如家金沙江店");
            bag.setPointId("095abce4eed845f7afc3b9e0dbe92843");
            bag.setPointName("2F");
            bag.setReceiveTime("2018-05-05 14:05:55");
            bag.setReturnTime("2018-05-10 14:05:55");
            bag.setUserId("0aaa0e1390324b0b8a4188a194353257");
            bag.setUserName("张三");
            bag.setCreator("0aaa0e1390324b0b8a4188a194353257");
            bag.setCreatorName("张三");
            bag.setCreateTime("2018-05-05 14:05:55");
            bag.setModifyTime("2018-05-05 14:05:55");
            list.add(bag);
        }
        return list;
    }

    /**
     * 查询布草袋的使用记录
     *
     * @param token
     * @param code
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageMo<LinenPackageRecord> record(String token, String code, int pageNum, int pageSize) {
        List<LinenPackageRecord> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            LinenPackageRecord record = new LinenPackageRecord();
            record.setId("58961240254" + i);
            record.setPackageId("58961" + i);
            record.setCode("58961" + i);
            record.setHotelId("053db666f08c402fb58e079c1d81ab79");
            record.setHotelName("如家金沙江店");
            record.setPointId("095abce4eed845f7afc3b9e0dbe92843");
            record.setPointName("2F");
            record.setReceiveTime("2018-05-05 14:05:55");
            record.setReturnTime("2018-05-05 14:05:55");
            record.setCreator("113456");
            record.setCreatorName("张三");
            list.add(record);
        }
        return new PageMo<>(list, 1, 4, 5);
    }

    /**
     * 查询净布草袋的报表
     *
     * @param token
     * @param userId 使用人
     * @param beginNum
     * @param endNum
     * @return
     */
    @Override
    public List<LinenPackageStatistics> fineReport(String token, String userId, int beginNum, int endNum) {
        List<LinenPackageStatistics> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LinenPackageStatistics packageStat = new LinenPackageStatistics();
            packageStat.setHotelId("053db666f08c402fb58e079c1d81ab79");
            packageStat.setHotelName("如家金沙江店");
            List<LinenPackageStacount> stalist = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                LinenPackageStacount count = new LinenPackageStacount();
                count.setPointId("095abce4eed845f7afc3b9e0dbe92843");
                count.setPointName("3F");
                count.setLeft(21);
                count.setMiddle(80);
                count.setRight(10);
                stalist.add(count);
            }
            packageStat.setPackageStatics(stalist);
            list.add(packageStat);
        }
        return list;
    }

    /**
     * 查询脏布草袋的报表
     *
     * @param token
     * @param userId 使用人
     * @param beginNum
     * @param endNum
     * @return
     */
    @Override
    public LinenPackageStacount dirtyReport(String token, String userId, int beginNum, int endNum) {
            LinenPackageStacount count = new LinenPackageStacount();
            count.setLeft(21);
            count.setMiddle(80);
            count.setRight(10);
        return count;
    }


}
