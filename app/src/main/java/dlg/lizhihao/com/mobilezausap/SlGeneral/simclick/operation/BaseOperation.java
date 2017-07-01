package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

/**
 * Created by sogood on 16/10/29.
 */

public abstract class BaseOperation implements Operation {

    public int mTaskId;
    public int mProgramId;
    public double mStep;

    public void setId(int taskId, int programId, double step) {
        mTaskId = taskId;
        mProgramId = programId;
        mStep = step;
    }

    public int getTaskId() {
        return mTaskId;
    }

    public int getParamId() {
        return mProgramId;
    }

    public double getStep() {
        return mStep;
    }

}
