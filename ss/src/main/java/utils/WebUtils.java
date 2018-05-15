package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

public class WebUtils {

    private static final Log logger = LogFactory.getLog(WebUtils.class);


    public static Map<String, Object> getParametersStartingWith(ServletRequest request) {
        logger.info("WebUtils::getParametersStartingWith(..) ---------- START");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String values = request.getParameter(paramName);
            params.put(paramName, values);
        }
        logger.info("WebUtils::getParametersStartingWith(..) ---------- END");
        return params;
    }

    public static String getHttpCheck(String resHttp) {
        String msg = "";
        if ("0".equals(resHttp)) {

            msg = "后台返回超时，请重试...";
        } else if ("1".equals(resHttp)) {

            msg = "后台请求失败，请重试...";
        } else if ("2".equals(resHttp)) {

            msg = "后台连接请求失败,请重�?..";
        } else if ("500".equals(resHttp)) {

            msg = "后台失败，服务器500异常，请重试...";
        } else if ("502".equals(resHttp)) {

            msg = "后台失败，服务器502异常，请重试...";
        } else if ("404".equals(resHttp) || "400".equals(resHttp)) {

            msg = "后台失败，服务器404异常，请重试...";
        }
        return msg;
    }
}
