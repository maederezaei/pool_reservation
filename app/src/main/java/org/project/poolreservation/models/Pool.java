package org.project.poolreservation.models;

public class Pool {
    public String poolName;
    public String poolRate;
    public String poolCapacity;
    public String poolPhone;
    public String poolAddress;
    public String poolInfo;
    public String poolId;


    public Pool(String poolId,String poolName,String poolRate,String poolCapacity,String poolPhone,String poolAddress,String poolInfo)
    {
        this.poolId=poolId;
        this.poolName = poolName;
        this.poolAddress=poolAddress;
        this.poolCapacity=poolCapacity;
        this.poolRate=poolRate;
        this.poolInfo=poolInfo;
        this.poolPhone=poolPhone;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }


}
