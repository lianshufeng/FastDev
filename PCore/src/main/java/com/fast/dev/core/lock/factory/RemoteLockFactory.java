package com.fast.dev.core.lock.factory;

import java.io.IOException;
import java.lang.reflect.Constructor;

import com.fast.dev.core.lock.config.LockOption;

@SuppressWarnings("unchecked")
public final class RemoteLockFactory {

	/**
	 * 构建远程锁
	 * 
	 * @param option
	 * @return
	 * @throws InterruptedException
	 * @throws KeeperException
	 * @throws IOException
	 */
	public static RemoteLock build(Class<? extends RemoteLock> remoteLockCls, LockOption option) throws Exception {
		Constructor<RemoteLock> constructor = findConstructor(remoteLockCls);
		return constructor.newInstance(option);
	};

	/**
	 * 找到构造方法
	 * 
	 * @param remoteLockCls
	 * @return
	 */
	private static Constructor<RemoteLock> findConstructor(Class<? extends RemoteLock> remoteLockCls) {
		// 构造方法
		Constructor<?>[] constructors = remoteLockCls.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterTypes() != null && constructor.getParameterTypes().length == 1) {
				Class<?> parametersCls = constructor.getParameterTypes()[0];
				if (LockOption.class.isAssignableFrom(parametersCls)) {
					return (Constructor<RemoteLock>) constructor;
				}
			}
		}
		return null;
	}

}
