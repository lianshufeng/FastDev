package com.fast.dev.component.notice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 请配置本实现到spring容器里
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2016年12月29日
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class MessageManagerImpl implements MessageManager {

	private Map<String, List<NoticeExecute<?>>> noticeInterfaceMap = new HashMap<String, List<NoticeExecute<?>>>();

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	private void init() {

		// 缓存spring 的容器对象
		for (NoticeExecute<?> noticeInterface : applicationContext.getBeansOfType(NoticeExecute.class).values()) {
			for (String subscribe : noticeInterface.subscribes()) {
				// 通过缓存里找到该对象
				List<NoticeExecute<?>> noticeInterfaces = noticeInterfaceMap.get(subscribe);
				if (noticeInterfaces == null) {
					noticeInterfaces = new ArrayList<NoticeExecute<?>>();
					noticeInterfaceMap.put(subscribe, noticeInterfaces);
				}
				noticeInterfaces.add(noticeInterface);
			}
		}

		// 排序
		for (Entry<String, List<NoticeExecute<?>>> entry : noticeInterfaceMap.entrySet()) {
			List<NoticeExecute<?>> noticeInterfaces = entry.getValue();
			// List<NoticeInterface<?>> newNoticeInterfaces =
			Collections.sort(noticeInterfaces, new Comparator<NoticeExecute<?>>() {
				@Override
				public int compare(NoticeExecute<?> noticeInterface1, NoticeExecute<?> noticeInterface2) {
					return noticeInterface1.sort() - noticeInterface2.sort();
				}
			});
		}

	}

	@Override
	public <T> void publish(T t, String... subscribes) {
		// 通过订阅号取的实现类并发送通知
		for (String subscribe : subscribes) {
			List<NoticeExecute<?>> noticeInterfaces = this.noticeInterfaceMap.get(subscribe);
			if (noticeInterfaces != null) {
				for (NoticeExecute noticeInterface : noticeInterfaces) {
					// 发送通知
					if (!noticeInterface.execute(subscribe, t)) {
						break;
					}
				}
			}
		}
	}

}
