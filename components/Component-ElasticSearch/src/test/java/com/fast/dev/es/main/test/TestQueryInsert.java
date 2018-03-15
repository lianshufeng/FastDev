package com.fast.dev.es.main.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

@SuppressWarnings("serial")
public class TestQueryInsert extends TestSuper {

	@Test
	public void testInsert() {
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> m = new HashMap<>();
			m.put("test" + i, 1);
			m.put("info", i + "中文可以随便分词_" + System.currentTimeMillis());
			m.put("bool", true);
			m.put("time", System.currentTimeMillis());
			list.add(m);
		}

		addInfo(list, "创新，无疑是引领发展的第一动力。今年的政府工作报告提出，集众智汇众力，一定能跑出中国创新“加速度”。");

		addInfo(list,
				"面对这一新的动员令，我们底气十足。我国拥有世界上规模最大的人力人才资源，这是创新发展的最大“富矿”。这几年，在大众创业、万众创新的鼓舞下，一股创新创业的热潮席卷全国，“双创”已成为创新发展的新钥匙、新动能。今年的政府工作报告明确提出要促进大众创业、万众创新上水平，并指出了下一步着力的重点：要提供全方位创新创业服务，推进“双创”示范基地建设，鼓励大企业、高校和科研院所开放创新资源，发展平台经济、共享经济，形成线上线下结合、产学研用协同、大中小企业融合的创新创业格局。相信只要按照政府工作报告的部署抓落实，就一定能打造出“双创”升级版，推动我国创新发展上台阶");
		addInfo(list, "面对这一新的动员令，我们尚须加力。");
		addInfo(list,
				"激发创新活动，必须破除创新藩篱。毋庸讳言，当前我们创新工作中还存在着体制机制不活、创新环境不优等突出问题。今年的政府工作报告对症下药、开出了良方：“有悖于激励创新的陈规旧章，要抓紧修改废止；有碍于释放创新活力的繁文缛节，要下决心砍掉。”我们要进一步为创新开路，加强科技创新政策与其他领域相关政策协调，解决政策落实中的“肠梗阻”问题，把现有政策落实到位。进一步深化人才发展体制改革，推动人力资源自由流动，支持企业提高技术工人待遇，加大对高技能人才激励措施，鼓励海外留学人员回国创新创业，拓宽外国人才来华绿色通道。");
		addInfo(list,
				"经过不懈的努力，我们看到了创新的威力，中国在许多领域从跟跑到并跑，并实现了领跑。相信只要坚定信心、深化改革，破除障碍、激活要素，中国一定能跑出创新“加速度”，并进而成为更多领域的“领跑者”，展示出加快建设创新型国家、驱动高质量发展的新气象新作为。");

		System.out.println(esDao.save(list.toArray()));
	}

	private static void addInfo(List<Map<String, Object>> list, final String info) {
		list.add(new HashMap<String, Object>() {
			{
				put("time", System.currentTimeMillis());
				put("info", info);
			}
		});
	}

}
