package bean;

/**
用于记录交易数据的bean
 */
public class TradeBean {
    //canceled 表示用户是否退票，退票了为true，不退票为false
    private int tradeId;
    private int userId;
    private int runId;
    private boolean canceled;

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
