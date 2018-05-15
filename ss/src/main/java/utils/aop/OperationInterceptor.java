package utils.aop;


import com.emart.bean.AppLog;
import com.emart.bean.SysOperationLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import utils.Constant;
import utils.JsonMapper;
import utils.annotation.Loggable;
import utils.json.JsonError;
import utils.json.JsonObject;

import java.lang.reflect.Method;

public class OperationInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JsonMapper jsonMapper = JsonMapper.buildNonNullMapper();

//    @Autowired
//    private SysOperationLogMapper oprLogMapper;
    
//    @Autowired
//    private AppLogMapper appLogMapper;

    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String method = proceedingJoinPoint.getSignature().getName();
        String action = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        logger.info(String.format("Request: %s.%s  参数: %s", action, method, jsonMapper.toJson(proceedingJoinPoint.getArgs())));

        long before = System.currentTimeMillis();
        Object result;

        try {
            result = proceedingJoinPoint.proceed();

        } catch (Exception e) {
            JsonObject jObject = new JsonError(Constant.OPERATION_FAILED);

            logger.info(Constant.SYSTEM_ERROR + e);
            logger.info(String.format("Response: %s.%s  返回: %s", action, method, jsonMapper.toJson(jObject)));
            logger.info(String.format("Operation: %s.%s  耗时: %s ms  Success: %s", action, method, System.currentTimeMillis() - before, false));

            return jObject;
        }

        logger.info(String.format("Response: %s.%s  返回: %s", action, method, jsonMapper.toJson(result)));
        logger.info(String.format("Operation: %s.%s  耗时: %s ms  Success: %s", action, method, System.currentTimeMillis() - before, true));

        return result;
    }

    public void before2(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();

        String action = joinPoint.getTarget().getClass().getSimpleName();

        logger.info(String.format("%s.%s %s", action, method, "-------------- START"));
    }

    public void after2(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();

        String action = joinPoint.getTarget().getClass().getSimpleName();

        logger.info(String.format("%s.%s %s", action, method, "-------------- END"));
    }

    public void afterReturning3(JoinPoint joinPoint, Object retValue) {
        try {
            String methodName = joinPoint.getSignature().getName();// 获取方法名  
            Class<?> targetClass = joinPoint.getTarget().getClass();// 获取目标对象的类名  
            Method method = null;
            for (Method mt : targetClass.getMethods()) {
                if (methodName.equals(mt.getName())) {
                    method = mt;
                    break;
                }
            }
            if (method == null) {
                throw new AssertionError();
            }
            Loggable loggable = method.getAnnotation(Loggable.class);// 自定义注解

            if (loggable == null) {
                return;
            }
 
            String billType = loggable.billType();// 业务类型
            String optType = loggable.optType();// 操作类型
            if(billType.equals("APP")){            	                     
                Object params = joinPoint.getArgs()[0];// 获取参数            
                AppLog message = (AppLog) params;
                //记录日志
                AppLog oprLog = new AppLog();
                oprLog.setStoreCode(message.getStoreCode());//单据编码
                oprLog.setOrderType(billType);//单据类型
                oprLog.setOptType(optType);//操作类型
                oprLog.setOrderNumber(message.getOrderNumber());
                oprLog.setUserId(message.getUserId());
               // appLogMapper.insertAppLog(oprLog);
            }else{   
                String cont = loggable.cont();// 操作内容
                Object params = joinPoint.getArgs()[0];// 获取参数  
                SysOperationLog message = (SysOperationLog) params;
                //记录日志
                SysOperationLog oprLog = new SysOperationLog();
                oprLog.setBillnumber(message.getBillnumber());//单据编码
                oprLog.setBilltype(billType);//单据类型
                oprLog.setOpttype(optType);//操作类型
                oprLog.setUserid(message.getUserid());//操作用户ID
                oprLog.setUsername(message.getUsername());//操作用户名
                oprLog.setTruename(message.getTruename());//真实姓名
                oprLog.setCont(cont);//操作内容
              //  oprLogMapper.insertSysOprLog(oprLog);
            }          
        } catch (Exception e) {
            logger.info(Constant.SYSTEM_ERROR + e);
        }
    }
}
