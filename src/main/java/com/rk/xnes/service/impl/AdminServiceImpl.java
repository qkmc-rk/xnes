
package com.rk.xnes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rk.xnes.dao.AdminDao;
import com.rk.xnes.dao.UserCertifDao;
import com.rk.xnes.entity.Admin;
import com.rk.xnes.entity.UserCertif;
import com.rk.xnes.service.AdminService;

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
         ·ð×æ±£ÓÓ       ÓÀÎÞBUG 
*/

@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;
	
	@Autowired
	UserCertifDao userCertifyDao;
	
	@Override
	public Admin findAdminByUserid(Integer userid) {
		return adminDao.selectByUserId(userid);
	}

	@Override
	public boolean passUserCertify(Integer userid) {
		UserCertif userCertif = null;
		try {
			userCertif= userCertifyDao.selectByUserId(userid);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(userCertif == null) return false;
		else {
			userCertif.setState(1);
			if(userCertifyDao.update(userCertif) > 0) 
				return true;
			else
				return false;
		}
	}

}
