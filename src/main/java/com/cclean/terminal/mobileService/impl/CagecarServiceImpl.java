package com.cclean.terminal.mobileService.impl;

import com.cclean.terminal.mobileService.CagecarService;
import com.cclean.terminal.model2.Cagecar;
import com.cclean.terminal.model2.CagecarUseLog;
import com.cclean.terminal.model2.LinenPackM;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yulq
 * @create 2018-05-23 11:38
 * @desc 笼车
 **/
@Service
public class CagecarServiceImpl implements CagecarService {

    /**
     * 笼车登记
     *
     * @param token
     * @param codes
     * @return 登记成功的笼车ids
     */
    public List<String> register(String token, List<String> codes) {

        return null;
    }

    /**
     * 笼车查询
     *
     * @param token
     * @param codes
     * @return
     */
    public List<Cagecar> list(String token, List<String> codes) {
        return null;
    }

    /**
     * 笼车的使用
     *
     * @param token     借用人
     * @param status    状态
     * @param userId    使用人
     * @param factoryId 使用工厂
     * @param codes     笼车code
     * @return          使用记录的ids
     */
    public List<String> borrow(String token, int status, String userId, String factoryId, List<String> codes) {

        return null;

    }

    /**
     * 查询笼车当前使用记录
     *
     * @param token
     * @param code      笼车
     * @return          最新的一条记录
     */
    public CagecarUseLog recored(String token, String code) {
        return null;
    }

    /**
     * 使用记录条件查询
     *
     * @param token
     * @param pageNum
     * @param pageSize
     * @param status
     * @param code
     * @param borrowDriverId
     * @param borrowTimeStart
     * @param borrowTimeEnd
     * @param deliveryDriverId
     * @param deliveryTimeStart
     * @param deliveryTimeEnd
     * @return
     */
    public List<CagecarUseLog> query(String token, int pageNum, int pageSize, int status, String code, String borrowDriverId,
                               String borrowTimeStart, String borrowTimeEnd, String deliveryDriverId, String deliveryTimeStart, String deliveryTimeEnd) {
        return null;
    }

    /**
     *  笼车配货
     * @param token
     * @param logId     使用记录的id
     * @param zcodes    打扎单号
     * @return          使用记录ID
     */
    public String packCage(String token,String logId,List<String> zcodes){
        return null;
    }

    /**
     *  查询记录下的打扎单
     * @param token
     * @param logId     使用记录ID
     * @return          打扎单详情
     */
    public List<LinenPackM> findPacks(String token, String logId){
        return null;
    }
}
