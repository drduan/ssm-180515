package utils.redis;

public enum RedisTimeManager {

	ORDER("订单", 900), OTHER("其他", 2419200);
	
	private String type ;
    private int time ;
     
    private RedisTimeManager( String type , int time ){
        this.type = type ;
        this.time = time ;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
    
}
