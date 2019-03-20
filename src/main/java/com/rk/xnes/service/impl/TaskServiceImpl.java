
package com.rk.xnes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.xnes.dao.HelpInfoDao;
import com.rk.xnes.dao.HelpStateDao;
import com.rk.xnes.dao.UserCertifDao;
import com.rk.xnes.entity.HelpInfo;
import com.rk.xnes.entity.HelpState;
import com.rk.xnes.entity.UserCertif;
import com.rk.xnes.service.TaskService;

/** 
                   _ooOoo_ 
                  o8888888o 
                  88" . "88 
                  (| -_- |) 
                  O\  =  /O 
               ____/`---'\____ 
             .'  \\|     |//  `. 
            /  \\|||  :  |||//  \ 
           /  _||||| -:- |||||-  \ 
           |   | \\\  -  /// |   | 
           | \_|  ''\---/''  |   | 
           \  .-\__  `-`  ___/-. / 
         ___`. .'  /--.--\  `. . __ 
      ."" '<  `.___\_<|>_/___.'  >'"". 
     | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
     \  \ `-.   \_ __\ /__ _/   .-` /  / 
======`-.____`-.___\_____/___.-`____.-'====== 
                   `=---=' 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
         佛祖保佑       永无BUG 
*/

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	HelpInfoDao helpInfoDao;
	
	@Autowired
	HelpStateDao helpStateDao;
	
	@Autowired
	UserCertifDao userCertifDao;
	
	@Override
	public Integer saveOneInfo(HelpInfo helpInfo) {
		//首先存一个HelpInfo
		Integer rs = null;
		try {
			rs = helpInfoDao.add(helpInfo);
		} catch (Exception e) {
			System.out.println("[EXCEPTION]保存helpInfo出错");
			e.printStackTrace();
		}
		if(rs > 0) {
			HelpState helpState = new HelpState();
			//查询插入的id
			HelpInfo helpInfo2 = helpInfoDao.selectByContent(helpInfo.getContent());
			Integer infoid = helpInfo2.getId();
			
			//设置helpstate
			helpState.setInfoid(infoid);
			helpState.setAchieved(0);
			helpState.setReceived(0);
			helpState.setTimeout(0);
			
			//插入数据库记录
			return helpStateDao.add(helpState);
		}
		return null;
	}

	@Override
	public List<HelpInfo> findByUserId(Integer userid) {
		List<HelpInfo> list = null;
		try {
			list = helpInfoDao.selectByUserId(userid);
			return list;
		} catch (Exception e) {
			System.out.println("[EXCEPTION] 查找用户发布任务失败!");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HelpState findByInfoId(Integer infoid) {
		HelpState helpState = null;
		try {
			helpState = helpStateDao.selectByInfoId(infoid);
			return helpState;
		} catch (Exception e) {
			System.out.println("[EXCEPTION] 查找用户发布任务的状态时失败!");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<HelpInfo> findAll() {
		List<HelpInfo> list = null;
		try {
			list = helpInfoDao.selectAll();
			return list;
		} catch (Exception e) {
			System.out.println("[EXCEPTION] 查找用户发布任务失败!");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HelpInfo findHelpInfoById(Integer infoid) {
		HelpInfo helpInfo = null;
		try {
			helpInfo = helpInfoDao.selectById(infoid);
		} catch (Exception e) {
			System.out.println("[LOG]未能找到id=" +infoid + "的helpInfo");
			e.printStackTrace();
			return null;
		}
		return helpInfo;
	}

	@Override
	@Transactional
	public boolean deleteHelpInfoByInfoId(Integer infoid) {
		try {
			helpStateDao.deleteByInfoId(infoid);
			helpInfoDao.deleteById(infoid);
			return true;
		} catch (Exception e) {
			System.out.println("[LOG]删除失败infoid" + infoid);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean UpdateHelpInfo(HelpInfo helpInfo) {
		if(helpInfoDao.update(helpInfo) > 0)
			return true;
		return false;
	}

	@Override
	public boolean UpdateHelpState(HelpState helpState) {
		if(helpStateDao.update(helpState) > 0)
			return true;
		return false;
	}

	@Override
	public boolean usercertification(Integer userid, String cardname) {
		
		UserCertif userCertif = userCertifDao.selectByUserId(userid);
		userCertif.setCardpath(cardname);
		
		if(userCertifDao.update(userCertif) > 0) 
			return true;
		return false;
	}
}
