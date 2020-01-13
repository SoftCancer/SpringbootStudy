package com.dongl.boot_config_11_druid.service;
import com.dongl.boot_config_11_druid.dao.master.CapitalAccountMapper;
import com.dongl.boot_config_11_druid.dao.slave.RedPacketAccountMapper;
import com.dongl.boot_config_11_druid.entity.CapitalAccount;
import com.dongl.boot_config_11_druid.entity.RedPacketAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/13 15:02
 */
@Service
public class PayService {

    @Autowired
    private CapitalAccountMapper accountMapper;
    @Autowired
    private RedPacketAccountMapper redPacketAccountMapper;


    @Transactional(transactionManager = "masterTransactionManager")
    public void payAccout(String userId,int account){
        CapitalAccount capitalAccount = new CapitalAccount();
        capitalAccount.setUserId(userId);
        CapitalAccount capitalAccount1 = accountMapper.selectOne(capitalAccount);
        capitalAccount1.setBalanceAmount(capitalAccount1.getBalanceAmount() -account);
        accountMapper.updateByPrimaryKey(capitalAccount1);

    }

    @Transactional(transactionManager = "slaveTransactionManager")
    public void payRedAccout(String userId,int account){
        RedPacketAccount redPacketAccount = new RedPacketAccount();
        redPacketAccount.setUserId(userId);
        RedPacketAccount redPacket = redPacketAccountMapper.selectOne(redPacketAccount);
        redPacket.setBalanceAmount(redPacket.getBalanceAmount() +account);
        redPacketAccountMapper.updateByPrimaryKey(redPacket);
        int i = 9/0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void pay(String formUserId,String toUserId,int account){
        this.payAccout(formUserId,account);
        this.payRedAccout(toUserId,account);
    }


}
