
import com.fast.dev.component.taskpool.pool.TaskParameter;

public class TaskParameterImpl extends TaskParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String taskId;

	private String file;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
