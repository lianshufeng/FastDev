import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fast.dev.data.transfer.DataOperate;
import com.fast.dev.data.transfer.DataTransferFactory;
import com.fast.dev.data.transfer.model.DataItem;
import com.fast.dev.data.transfer.model.FieldInformation;
import com.fast.dev.data.transfer.model.FileOption;
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void export() throws Exception {

		FileOption option = new FileOption();
		option.setVersion("v1.0.0");
		option.setFields(new ArrayList<FieldInformation>() {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;
			{
				add(new FieldInformation("人员id", String.class));
				add(new FieldInformation("姓名", String.class));
				add(new FieldInformation("负责地区", String.class));
				add(new FieldInformation("岗位", String.class));
				add(new FieldInformation("部门", String.class));
				add(new FieldInformation("上级领导", String.class));
			}
		});

		List<DataItem> dataItems = new ArrayList<DataItem>();
		dataItems.add(new DataItem(new Object[] { "1", "2", "3", 1, 1.243, 3.155 }));
		dataItems.add(new DataItem(new Object[] { "2", "2", "3" }));
		dataItems.add(new DataItem(new Object[] { "3", "2", "3" }));
		dataItems.add(new DataItem(new Object[] { "4", "2", "3", "4", new Date() }));
		FileOutputStream outputStream = new FileOutputStream(new File("c:/demo.xls"));
		dataOperate.write(outputStream, null, option);
		outputStream.close();

	}

	@Test
	public void imports() {
		System.out.println("imp");
	}

}
