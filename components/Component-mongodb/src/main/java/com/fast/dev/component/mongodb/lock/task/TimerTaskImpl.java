package com.fast.dev.component.mongodb.lock.task;

import java.util.TimerTask;

import com.fast.dev.component.mongodb.lock.RemoteLockMongo;
import com.fast.dev.component.mongodb.lock.dao.RemoteLockDaoImpl;

/**
 * 
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月23日
 *
 */
public class TimerTaskImpl extends TimerTask implements Runnable {

	private boolean canRun = true;

	// 远程锁
	private RemoteLockDaoImpl remoteLockDao;

	// uuid
	private String uuid;

	// 远程锁客户端
	private RemoteLockMongo remoteLockMongo;

	@Override
	public void run() {
		this.remoteLockMongo.removeTimerTask(this.uuid);
		if (this.canRun) {
			this.remoteLockDao.removeFromUUID(uuid);
		}
	}

	/**
	 * @return the canRun
	 */
	public boolean isCanRun() {
		return canRun;
	}

	/**
	 * @param canRun
	 *            the canRun to set
	 */
	public void setCanRun(boolean canRun) {
		this.canRun = canRun;
	}

	/**
	 * @return the remoteLockDao
	 */
	public RemoteLockDaoImpl getRemoteLockDao() {
		return remoteLockDao;
	}

	/**
	 * @param remoteLockDao
	 *            the remoteLockDao to set
	 */
	public void setRemoteLockDao(RemoteLockDaoImpl remoteLockDao) {
		this.remoteLockDao = remoteLockDao;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the remoteLockMongo
	 */
	public RemoteLockMongo getRemoteLockMongo() {
		return remoteLockMongo;
	}

	/**
	 * @param remoteLockMongo
	 *            the remoteLockMongo to set
	 */
	public void setRemoteLockMongo(RemoteLockMongo remoteLockMongo) {
		this.remoteLockMongo = remoteLockMongo;
	}

}
