import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.data.transfer.DataOperate;
import com.fast.dev.data.transfer.DataTransferFactory;
import com.fast.dev.data.transfer.model.DataItem;
import com.fast.dev.data.transfer.model.TableItem;
import com.fast.dev.data.transfer.type.DataFileType;

public class TestExcel {

	// 数据操作
	DataOperate dataOperate = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dataOperate = DataTransferFactory.build(DataFileType.Excel);

		export();
	}

	@After
	public void tearDown() throws Exception {
	}

	public void export() throws Exception {

		List<DataItem> dataItems = new ArrayList<DataItem>();
		dataItems.add(new DataItem(new Object[] { "1", null, "2", "3", 1, 1.243, 3.155 }));
		dataItems.add(new DataItem(new Object[] { "2", 15123241353l, "2", "3", true }));
		dataItems.add(new DataItem(new Object[] { "速度#(*!@)$*!@'ss", "2", null, "3" }));
		dataItems.add(new DataItem(new Object[] { "4", "2", "3", "4", new Date() }));

		FileOutputStream outputStream = new FileOutputStream(new File("c:/demo.xlsx"));
		TableItem tableItem = new TableItem();
		tableItem.setDataItems(dataItems);

		tableItem.setFieldNames(new ArrayList<String>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;
			{
				add("人员id");
				add("姓名");
				add("负责地区");
				add("岗位");
				add("部门");
				add("上级领导");
			}
		});

		tableItem.setDataItems(dataItems);
		tableItem.setName("工作簿1");
		dataOperate.write(outputStream, tableItem);
		outputStream.close();

	}

	@Test
	public void imports() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File("c:/demo.xlsx"));
		Map<String, DataItem[]> items = dataOperate.read(inputStream);
		inputStream.close();
		System.out.println(JsonUtil.toJson(items));
	}

	// @Test
	public void test1() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File("c:/demo/1.xlsx"));
		Map<String, DataItem[]> items = dataOperate.read(inputStream);
		inputStream.close();
		System.out.println(JsonUtil.toJson(items));
	}

	// @Test
	public void test2() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File("c:/demo/2.xlsx"));
		Map<String, DataItem[]> items = dataOperate.read(inputStream);
		inputStream.close();
		System.out.println(JsonUtil.toJson(items));
	}

	// @Test
	public void test3() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File("c:/demo/3.xlsx"));
		Map<String, DataItem[]> items = dataOperate.read(inputStream);
		inputStream.close();
		System.out.println(JsonUtil.toJson(items));

	}

	// @Test
	public void test4() throws Exception {
		FileInputStream inputStream = new FileInputStream(new File("c:/demo/4.xlsx"));
		Map<String, DataItem[]> items = dataOperate.read(inputStream);
		inputStream.close();
		System.out.println(JsonUtil.toJson(items));
	}

}
