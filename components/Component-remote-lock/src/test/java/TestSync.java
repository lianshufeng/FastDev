import org.apache.zookeeper.KeeperException;

public class TestSync extends Demo {


	public static void main(String[] args) throws Exception, KeeperException, InterruptedException {
		new TestSync().run();
	}
	
	@Override
	public long maxRunCount() {
		return 500;
	}
	
	
	@Override
	public int maxTheadPool() {
		return 300;
	}
	
	
	

}
