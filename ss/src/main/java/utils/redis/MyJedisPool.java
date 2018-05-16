package utils.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import utils.Constant;

import java.io.IOException;
import java.util.Properties;

public class MyJedisPool {

	private static JedisPool pool;

	public final static String TG_PREX = "DSDQ_";
	
	private final static  Logger logger = LoggerFactory.getLogger(MyJedisPool.class.getName());
	
	// 静态代码初始化池配置
	static {
		try {
			Properties props = new Properties();
			props.load(MyJedisPool.class.getClassLoader().getResourceAsStream("redis.properties"));
			// 创建jedis池配置实例
			JedisPoolConfig config = new JedisPoolConfig();
			// 设置池配置项值		
			//控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
	        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
			config.setMaxTotal(Integer.valueOf(props.getProperty("jedis.pool.maxTotal")));
			//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。 
			config.setMaxIdle(Integer.valueOf(props.getProperty("jedis.pool.maxIdle")));
			//表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException
			config.setMaxWaitMillis (Long.valueOf(props.getProperty("jedis.pool.maxWaitMillis")));
			//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(Boolean.valueOf(props.getProperty("jedis.pool.testOnBorrow")));
			config.setTestOnReturn(Boolean.valueOf(props.getProperty("jedis.pool.testOnReturn")));
			// 根据配置实例化jedis池
//			pool = new JedisPool(config, props.getProperty("redis.ip"),
//					Integer.valueOf(props.getProperty("redis.port")),Integer.valueOf(props.getProperty("redis.timeout")),props.getProperty("redis.password"));			
			pool = new JedisPool(config, props.getProperty("redis.ip"),
					Integer.valueOf(props.getProperty("redis.port")),Integer.valueOf(props.getProperty("redis.timeout")),props.getProperty("redis.password"),15);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 获得jedis对象 */
	public static Jedis getJedisObject() {
		return pool.getResource();
	}

	/** 归还jedis对象 */
	public static void recycleJedisOjbect(Jedis jedis) {
		jedis.close();
    //		pool.close();
	}
	
	private static String buildKey(String key){  
        return TG_PREX + key;  
    }

	public static void setString(int redisTime, String key, String value) {
		Jedis jedis = null;
		try {
			value = StringUtils.isEmpty(value) ? "" : value;
			jedis = getJedisObject();		
			jedis.setex(buildKey(key), redisTime, value);
		} catch (Exception e) {
			logger.info(Constant.SYSTEM_ERROR + e.getMessage());
		} finally {
			//将获取的jedis实例对象还回池中
			recycleJedisOjbect(jedis);			
		}
	}
	
	public static String getString(String key) { 
		String str = "";
		Jedis jedis = null;
		try {
			String bKey = buildKey(key);  
			jedis = getJedisObject();	// 获得jedis实例	

			if(jedis != null && jedis.exists(bKey)){  
				str = jedis.get(bKey);
			}
		} catch (Exception e) {
			logger.info(Constant.SYSTEM_ERROR + e.getMessage());
		} finally {
			//将获取的jedis实例对象还回池中
			recycleJedisOjbect(jedis);			
		}
		return str;  
	} 
	
	public static void delString(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedisObject();	 // 获得jedis实例
			jedis.del(buildKey(key));
		} catch (Exception e) {
			logger.info(Constant.SYSTEM_ERROR + e.getMessage());
		} finally {
			//将获取的jedis实例对象还回池中
			recycleJedisOjbect(jedis);			
		}
	}
}
