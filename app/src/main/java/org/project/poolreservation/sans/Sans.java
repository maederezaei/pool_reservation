package org.project.poolreservation.sans;


public class Sans {
    private String time;
    private String sex;
    private int reserves;
    private int price;
    private int id;
    private String token;

    public Sans(String token,int id,String time,String sex,int reserves,int price){
        this.id=id;
        this.token=token;
        this.reserves=reserves;
        this.time=time;
        this.sex=sex;
        this.price=price;

    }

    public void setReserves(int reserves) {
        this.reserves = reserves;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getReserves() {
        return reserves;
    }

    public String getSex() {
        return sex;
    }
//change1
    public String getTime() {
        return time;
    }
    public int getPrice(){
        return price;
    }
    public int getId(){
        return id;
    }
    public String getToken(){
        return token;
    }


    @Override
    public String toString() {
        return time;
    }
}
