package com.fast.dev.component.mongodb.lock;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.fast.dev.component.mongodb.lock.config.RemoteLockOption;
import com.fast.dev.component.mongodb.lock.dao.RemoteLockDaoImpl;
import com.fast.dev.component.mongodb.lock.task.TimerTaskImpl;
import com.fast.dev.core.lock.config.LockOption;
import com.fast.dev.core.lock.factory.RemoteLock;
import com.fast.dev.core.lock.factory.SyncToken;

/**
 * 远程锁，mongodb实现,必须注入到spring 容器里
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月20日
 *
 */
public class RemoteLockMongo extends RemoteLock {

	private RemoteLockOption option = null;

	/**
	 * 构造方法
	 * 
	 * @param option
	 */
	public RemoteLockMongo(LockOption option) {
		super(option);
		this.option = (RemoteLockOption) option;
	}

	@Autowired
	private RemoteLockDaoImpl remoteLockDao;

	// 定时器
	private Timer timer = new Timer();
	// 定时器容器
	private Map<String, TimerTaskImpl> timerMap = new ConcurrentHashMap<String, TimerTaskImpl>();

	@PreDestroy
	private void shutdown() {
		// 超时时间
		removeTimeoutRecored();
		// 关闭
		timer.cancel();
	}

	@Autowired
	private void init() {
		// 删除超时记录
		removeTimeoutRecored();
	}

	/**
	 * 删除超时记录
	 */
	private void removeTimeoutRecored() {
		this.remoteLockDao.removeTimeout(this.option.getMaxLiveTime());
	}

	/**
	 * 暂不支持
	 */
	@Override
	@Deprecated
	public SyncToken sync(String name) throws Exception {
		return null;
	}

	/**
	 * 添加定时器,延迟删除这条记录
	 */
	private synchronized void addMaxLiveTimer(final String uuid) {
		TimerTaskImpl timerTask = new TimerTaskImpl();
		timerTask.setRemoteLockDao(this.remoteLockDao);
		timerTask.setUuid(uuid);
		timerTask.setRemoteLockMongo(this);
		this.timerMap.put(uuid, timerTask);
		this.timer.schedule(timerTask, this.option.getMaxLiveTime());
	}

	/**
	 * 终止超时定时器
	 * 
	 * @param uuid
	 */
	private synchronized void cancelMaxLiveTimer(final String uuid) {
		TimerTaskImpl timerTask = removeTimerTask(uuid);
		if (timerTask != null) {
			// 逻辑设置不执行定时器
			timerTask.setCanRun(false);
			// 终止这个定时器
			timerTask.cancel();
		}
	}

	@Override
	public boolean lock(final String name) throws Exception {
		String uuid = this.remoteLockDao.add(this.option.getServiceName(), name);
		boolean lockFinish = uuid != null;
		if (lockFinish) {
			addMaxLiveTimer(uuid);
		}
		return lockFinish;
	}

	@Override
	public void unLock(String name) throws Exception {
		String uuid = this.remoteLockDao.remove(this.option.getServiceName(), name);
		if (uuid != null) {
			cancelMaxLiveTimer(uuid);
		}
	}

	/**
	 * 删除一个定时器任务
	 * 
	 * @param uuid
	 * @return
	 */
	public TimerTaskImpl removeTimerTask(String uuid) {
		return this.timerMap.remove(uuid);
	}

}
