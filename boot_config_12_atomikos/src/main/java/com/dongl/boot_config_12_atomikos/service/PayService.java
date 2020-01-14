package com.dongl.boot_config_12_atomikos.service;
import com.dongl.boot_config_12_atomikos.dao.master.CapitalAccountMapper;
import com.dongl.boot_config_12_atomikos.dao.slave.RedPacketAccountMapper;
import com.dongl.boot_config_12_atomikos.entity.CapitalAccount;
import com.dongl.boot_config_12_atomikos.entity.RedPacketAccount;
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


    @Transactional(rollbackFor = Exception.class)
    public void payAccout(String userId,int account){
        CapitalAccount capitalAccount = new CapitalAccount();
        capitalAccount.setUserId(userId);
        CapitalAccount oldCapitalAccount = accountMapper.selectOne(capitalAccount);
        oldCapitalAccount.setBalanceAmount(oldCapitalAccount.getBalanceAmount() -account);
        accountMapper.updateByPrimaryKey(oldCapitalAccount);
    }


    @Transactional(rollbackFor = Exception.class)
    public void payRedAccout(String userId,int account){
        RedPacketAccount redPacketAccount = new RedPacketAccount();
        redPacketAccount.setUserId(userId);
        RedPacketAccount oldRedPacket = redPacketAccountMapper.selectOne(redPacketAccount);
        oldRedPacket.setBalanceAmount(oldRedPacket.getBalanceAmount() +account);
        redPacketAccountMapper.updateByPrimaryKey(oldRedPacket);
        int i = 9/0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void pay(String formUserId,String toUserId,int account){
        this.payAccout(formUserId,account);
        this.payRedAccout(toUserId,account);
    }


}
