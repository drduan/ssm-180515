//package utils;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.time.DateUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.config.SocketConfig;
//import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.http.conn.DnsResolver;
//import org.apache.http.conn.HttpConnectionFactory;
//import org.apache.http.conn.ManagedHttpClientConnection;
//import org.apache.http.conn.routing.HttpRoute;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.impl.DefaultConnectionReuseStrategy;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
//import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
//import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.impl.conn.SystemDefaultDnsResolver;
//import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.ConnectException;
//import java.net.NoRouteToHostException;
//import java.net.SocketTimeoutException;
//import java.net.URI;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//public class HttpUtils {
//
//    private String url;
//    private String defaultEncoding = "UTF-8";
//
//    public static final int connectTimeOut = (int) (5 * DateUtils.MILLIS_PER_SECOND);//connect out time
//    public static final int timeOut = (int) (15 * DateUtils.MILLIS_PER_SECOND);//time out time
//
//    public HttpUtils() {
//
//    }
//
//    public HttpUtils(String url) {
//        this.url = url;
//    }
//
//    static PoolingHttpClientConnectionManager manager = null;
//    static CloseableHttpClient httpClient = null;
//
//    public static synchronized CloseableHttpClient getHttpClient() {
//        if (httpClient == null) {
//            //注册访问协议相关的socket工厂
//            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", SSLConnectionSocketFactory.getSystemSocketFactory()).build();
//            //HttpConnection工厂：配置写请求/解析响应处理器
//            HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(DefaultHttpRequestWriterFactory.INSTANCE, DefaultHttpResponseParserFactory.INSTANCE);
//            //DNS解析器
//            DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
//            //创建池化连接管理器
//            manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);
//            //默认为socket配置
//            SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
//            manager.setDefaultSocketConfig(defaultSocketConfig);
//
//            manager.setMaxTotal(600);//设置整个连接池的最大连接数
//            /**
//             * 每个路由的默认最大连接，每个路由实际最大连接数默认为DefaultMaxPerRoute控制，而MaxTotal是控制整个池子最大数
//             * 设置过小无法支持大并发（ConnectionPoolTimeoutException:Timeout waiting for connection from pool）,路由是对maxTotal的细分
//             */
//            manager.setDefaultMaxPerRoute(400);        //每个路由最大连接数
//            //在从链接池获取链接时，链接不活跃多长时间后需要进行一次验证，默认为2s
//            manager.setValidateAfterInactivity(5 * 1000);
//
//            //默认请求配置
//            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(20 * 1000)        //设置连接超时时间，20s
//                    .setSocketTimeout(50 * 1000)            //设置等待数据超时时间，50s
//                    .setConnectionRequestTimeout(20000)    //设置从连接池获取连接的等待超时时间，20s
//                    .build();
//
//            //创建HttpCilent
//            httpClient = HttpClients.custom().setConnectionManager(manager)                    //用于配置HttpClient使用的连接池
//                    .setConnectionManagerShared(false)                //连接池不是共享模式，用于配置次连接池是否存在多个HttpClient之间的共享
//                    .evictIdleConnections(60, TimeUnit.SECONDS)        //定期回收空闲连接
//                    .evictExpiredConnections()                        //定期回收过期连接
//                    .setConnectionTimeToLive(60, TimeUnit.SECONDS)    //连接存活时间，如果不设置，则根据长连接信息决定
//                    .setDefaultRequestConfig(defaultRequestConfig)    //设置默认请求配置
//                    .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)//连接重用策略，即是否能keepAlive
//                    .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)//长连接配置，即获取长连接生产多长时间
//                    .setRetryHandler(new DefaultHttpRequestRetryHandler(3, false))//设置重试次数，默认是3次；当前是禁用掉（有幂等的操作，才应开启）
//                    .build();
//
//            //JVM停止或重启时，关闭连接池释放掉连接（跟数据库连接池类似）
//            Runtime.getRuntime().addShutdownHook(new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        httpClient.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//        return httpClient;
//    }
//
//    /**
//     * get形式请求http资源
//     *
//     * @param encode
//     *
//     * @return
//     */
//    public String get(String encode, Map<String, Object> params) {
//        if (encode == null) {
//            encode = this.defaultEncoding;
//        }
//
//        String result = null;
//        HttpResponse response = null;
//        int statusCode = 0;
//        try {
//            HttpGet get = new HttpGet(url);
//            String url = this.url;
//            if (params != null && params.size() > 0) {
//                url += buildGetUrl(params);
//            }
//            get.setURI(new URI(url));
//            response = getHttpClient().execute(get);
//            statusCode = response.getStatusLine().getStatusCode();
//            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                EntityUtils.consume(response.getEntity());
//                //error
//            } else {
//                result = EntityUtils.toString(response.getEntity());
//                //ok
//            }
//        } catch (Exception e) {
//            //	    	log.error("Fatal transport error: " , e);
//        } finally {
//            if (response != null) {
//                try {
//                    EntityUtils.consume(response.getEntity());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return result;
//    }
//
//
//    @SuppressWarnings({"unchecked", "rawtypes"})
//    public String postWithFile(String encode, Map<String, Object> params, Map<String, File> fileParams) {
//        if (encode == null) {
//            encode = this.defaultEncoding;
//        }
//
//        String result = null;
//        HttpResponse response = null;
//        int statusCode = 0;
//        try {
//            HttpPost post = new HttpPost(url);
//            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//            if (params != null && params.size() > 0) {
//                // 创建参数队列
//                for (String key : params.keySet()) {
//                    paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
//                }
//            }
//
//            post.setEntity(new UrlEncodedFormEntity(paramList, encode));
//            response = getHttpClient().execute(post);
//            statusCode = response.getStatusLine().getStatusCode();
//            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                EntityUtils.consume(response.getEntity());
//                //error
//            } else {
//                result = EntityUtils.toString(response.getEntity());
//                //ok
//            }
//        }
//        //		catch (HttpException e) {
//        //			//	    		log.error("Fatal protocol violation: " , e);
//        //		}
//        catch (IOException e) {
//            //		    	log.error("Fatal transport error: " , e);
//        } finally {
//
//        }
//        return result;
//    }
//
//    /**
//     * post形式请求http资源
//     *
//     * @param params 携带的参数
//     * @param encode 编码方式
//     *
//     * @return
//     */
//    public String post(Map<String, Object> params, String encode) {
//
//        if (encode == null) {
//            encode = this.defaultEncoding;
//        }
//        String result = null;
//        HttpResponse response = null;
//        int statusCode = 0;
//        try {
//            HttpPost post = new HttpPost(url);
//            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//            if (params != null && params.size() > 0) {
//                // 创建参数队列
//                for (String key : params.keySet()) {
//                    paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
//                }
//            }
//
//            post.setEntity(new UrlEncodedFormEntity(paramList, encode));
//            response = getHttpClient().execute(post);
//            statusCode = response.getStatusLine().getStatusCode();
//            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                EntityUtils.consume(response.getEntity());
//                //error
//            } else {
//                result = EntityUtils.toString(response.getEntity());
//                //ok
//            }
//        } catch (IOException e) {
//            if (response != null) {
//                try {
//                    EntityUtils.consume(response.getEntity());
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//
//            //异常404 在该端口下有服务，但是访问的服务程序不正确  该端口下没有服务则会在此方法中被捕获
//            //500服务器端系统内部错误
//            //502服务器连接上游服务器出现的异常
//            if (e instanceof SocketTimeoutException) {
//                //如果在读取或接受套接字时发生超时，则抛出此异常。  服务器接到信息，但是超时返回。
//                return String.valueOf(statusCode);
//            }
//            if (e instanceof ConnectException) {
//                //试图将套接字连接到远程地址和端口时发生错误的情况下，抛出此异常。这些错误通常发生在拒绝远程连接时（例如，没有任何进程在远程地址/端口上进行侦听）。
//                statusCode = 1;
//                return String.valueOf(statusCode);
//            }
//            if (e instanceof ConnectTimeoutException) {
//                //指的是服务器请求超时 IP PING不通但是 本机联网
//                statusCode = 2;
//                return String.valueOf(statusCode);
//            }
//            if (e instanceof NoRouteToHostException) {
//                //试图将套接字连接到远程地址和端口时发生错误的情况下，抛出此异常。通常为无法到达远程主机，原因是防火墙干扰或者中间路由器停机。
//                statusCode = 2;
//                return String.valueOf(statusCode);
//            }
//            return String.valueOf(statusCode);
//            //	    	log.error("Fatal transport error: " , e);
//        } catch (Exception e) {
//            if (response != null) {
//                try {
//                    EntityUtils.consume(response.getEntity());
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        } finally {
//            if (response != null) {
//                try {
//                    EntityUtils.consume(response.getEntity());//释放链接
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return result;
//    }
//
//    @SuppressWarnings("rawtypes")
//    private List<NameValuePair> buildParamList(String key, Object value) {
//        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//        if (value == null) {
//            throw new RuntimeException("参数有误");
//        }
//        if (value instanceof Collection) {
//            for (Object tmp : (Collection) value) {
//                paramList.add(buildSimple(key, tmp));
//            }
//        } else {
//            paramList.add(buildSimple(key, value));
//        }
//
//        return paramList;
//    }
//
//    private NameValuePair buildSimple(String key, Object value) {
//        if (value instanceof Date) {
//            value = TGDateUtils.toString((Date) value);
//        }
//
//        BasicNameValuePair pair = new BasicNameValuePair(key, value.toString());
//        return pair;
//    }
//
//    /**
//     * post方法请求http资源
//     * 默认采用UTF-8编码
//     *
//     * @param params
//     *
//     * @return
//     */
//    public String post(Map<String, Object> params) {
//        return this.post(params, defaultEncoding);
//    }
//
//
//    public String post() {
//        return this.post(null);
//    }
//
//    public String postWithFile(Map<String, Object> params, Map<String, File> fileParams) {
//        return this.postWithFile(defaultEncoding, params, fileParams);
//    }
//
//    /**
//     * get方法请求http资源
//     * 默认采用UTF-8编码
//     *
//     * @return
//     */
//    public String get() {
//        return this.get(defaultEncoding, null);
//    }
//
//    public String get(Map<String, Object> params) {
//        return this.get(defaultEncoding, params);
//    }
//
//    private String buildGetUrl(Map<String, Object> params) {
//        StringBuilder result = new StringBuilder("?");
//        List<String> paramList = new ArrayList<String>();
//        for (String key : params.keySet()) {
//            paramList.addAll(buildUrl(key, params.get(key)));
//        }
//        result.append(StringUtils.join(paramList, "&"));
//        return result.toString();
//    }
//
//    private String buildSimpleUrl(String key, Object value) {
//        if (value instanceof Date) {
//            value = TGDateUtils.toString((Date) value);
//        }
//        return key + "=" + value;
//    }
//
//    @SuppressWarnings("rawtypes")
//    private List<String> buildUrl(String key, Object value) {
//        if (value == null) {
//            throw new RuntimeException("参数有误");
//        }
//        List<String> paramList = new ArrayList<String>();
//        if (value instanceof Collection) {
//            for (Object tmp : (Collection) value) {
//                paramList.add(this.buildSimpleUrl(key, tmp));
//            }
//        } else {
//            paramList.add(this.buildSimpleUrl(key, value));
//        }
//        return paramList;
//    }
//
//    public static HttpUtils getInstance(String url) {
//        return new HttpUtils(url);
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//}
