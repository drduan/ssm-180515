package utils;

public class Constant {
    
  public static final String START_PROCESS = "http://10.1.0.36:8003/ActivitiTest/startProcess.action";
  public static final String START_PROCESS_BATCH = "http://10.1.0.36:8003/ActivitiTest/startProcessBatch.action";
  public static final String FIND_TASK_INFO = "http://10.1.0.36:8003/ActivitiTest/findTaskInfo.action";
  public static final String FIND_BUSINESSKEY = "http://10.1.0.36:8003/ActivitiTest/findBusinessKey.action";
  public static final String DEAL_TASK = "http://10.1.0.36:8003/ActivitiTest/dealTask.action";
  public static final String DEAL_TASK_BATCH = "http://10.1.0.36:8003/ActivitiTest/dealTaskBatch.action";
  public static final String FIND_COMMENT = "http://10.1.0.36:8003/ActivitiTest/findComment.action";
  public static final String FIND_TASK_POST = "http://10.1.0.36:8003/ActivitiTest/findTaskPost.action";
  public static final String FIND_TASK_INFO_DSDQ = "http://10.1.0.36:8003/ActivitiTest/findTaskInfoDsDq.action";
  public static final String FIND_BUSINESSKEY_DSDQ = "http://10.1.0.36:8003/ActivitiTest/findBusinessKeyDsDq.action";
  public static final String ROLLBACK_STARTPROCESS = "http://10.1.0.36:8003/ActivitiTest/rollbackStartProcess.action";
  public static final String ROLLBACK_STARTPROCESSBATCH = "http://10.1.0.36:8003/ActivitiTest/rollbackStartProcessBatch.action";
  public static final String FIND_TASK_INFO_MX = "http://10.1.0.36:8003/ActivitiTest/findTaskInfoMx.action";
  public static final String WRITE_EXCEPTION = "http://10.1.0.36:8003/ActivitiTest/writeException.action";

  
//  public static final String START_PROCESS = "http://10.100.200.134:8080/Activiti/startProcess.action";
//  public static final String START_PROCESS_BATCH = "http://10.100.200.134:8080/Activiti/startProcessBatch.action";
//  public static final String FIND_TASK_INFO = "http://10.100.200.134:8080/Activiti/findTaskInfo.action";
//  public static final String FIND_BUSINESSKEY = "http://10.100.200.134:8080/Activiti/findBusinessKey.action";
//  public static final String DEAL_TASK = "http://10.100.200.134:8080/Activiti/dealTask.action";
//  public static final String DEAL_TASK_BATCH = "http://10.100.200.134:8080/Activiti/dealTaskBatch.action";
//  public static final String FIND_COMMENT = "http://10.100.200.134:8080/Activiti/findComment.action";
//  public static final String FIND_TASK_POST = "http://10.100.200.134:8080/Activiti/findTaskPost.action";
//  public static final String FIND_TASK_INFO_DSDQ = "http://10.100.200.134:8080/Activiti/findTaskInfoDsDq.action";
//  public static final String FIND_BUSINESSKEY_DSDQ = "http://10.100.200.134:8080/Activiti/findBusinessKeyDsDq.action";
//  public static final String ROLLBACK_STARTPROCESS = "http://10.100.200.134:8080/Activiti/rollbackStartProcess.action";
//  public static final String ROLLBACK_STARTPROCESSBATCH = "http://10.100.200.134:8080/Activiti/rollbackStartProcessBatch.action";
//  public static final String FIND_TASK_INFO_MX = "http://10.100.200.134:8080/Activiti/findTaskInfoMx.action";
//  public static final String WRITE_EXCEPTION = "http://10.100.200.134:8080/Activiti/writeException.action";
//  public static final String FIND_EXCEPTION = "http://10.100.200.134:8080/Activiti/findException.action";
  
  
    public static final String TASK_COMPLETED_NOT = "0";
    public static final String TASK_COMPLETED = "1";
    public static final String TASK_COMPLETED_BY_OTHERS = "2";

    public static final String OPERATION_SUCCEED = "操作成功.";
    public static final String OPERATION_FAILED = "操作失败,请重试.";
    public static final String SAVE_SUCCEED = "保存成功.";
    public static final String SAVE_FAILED = "保存失败.";
    public static final String MODIFY_SUCCEED = "修改成功.";
    public static final String MODIFY_FAILED = " 修改失败.";
    public static final String SUBMIT_SUCCEED = "提交成功.";
    public static final String SUBMIT_FAILED = "提交失败.";
    public static final String DEAL_SUCCEED = "处理成功.";
    public static final String DEAL_FAILED = "处理失败.";
    public static final String SYSTEM_ERROR = "系统异常:";
    public static final String FIND_FAILED = "查询失败.";

    public static final String NO_DETAIL_ERROR = "请填写明细信息.";
    
    public static final String OPRFLAG_NEW = "0";
    public static final String OPRFLAG_MOD = "1";
    public static final String OPRFLAG_LIST = "list";
    public static final String OPRFLAG_DETAIL = "detail";

    public static final String OPTTYPE_NEW = "新增";
    public static final String OPTTYPE_MOD = "修改";
    public static final String OPTTYPE_DEL = "作废";
    public static final String OPTTYPEBACK = "驳回";
    public static final String OPTTYPESUBMIT = "提交";

    public static final String CKRESULT_YES = "1";
    public static final String CKRESULT_NO = "2";

    //POS日志常量
    public static final String BILLTYPE_POS = "POS";
    public static final String GETCONFIG_POS = "POS获取参数";
    public static final String UPLOADLOG_POS = "POS上传日志";
    public static final String LOGIN_POS = "POS登录";
    public static final String UPLOADCASHIERMONEY_POS = "上传备用金大钞";
    
    
    
    //开单日志常量
    public static final String BILLTYPE_APP = "APP";
    public static final String LOGIN_APP = "手机登录";
    public static final String AUTHORIZEMANAGER_APP = "主管授权";
    public static final String CHANGEPWD = "修改密码";
    public static final String LOGISTICSINFO_APP = "仓库信息";
    public static final String CUSTOMINFO_APP = "客户信息";
    public static final String QUERYORDERNUMBER_APP = "查询订单编码";
    public static final String QUERYMER_APP = "查询商品";
    public static final String SUBMITMER_APP = "提交商品";
    public static final String DELETEMER_APP = "删除商品";
    public static final String DELETEORDER_APP = "删除开单";
    public static final String SUBMITORDER_APP = "提交开单";
    public static final String ORDERLIST_APP = "开单列表";
    public static final String ORDERDETAIL_APP = "开单详情";
    public static final String RETURNORDER_APP = "原单退货";
    public static final String RETURNTOGETHER_APP = "合并退货";
    public static final String MEMBERORDERDETAIL_APP = "会员开单";
    public static final String PAY_APP = "开单付款";
    public static final String UPLOADORDERCONFIRM = "调单确认";
    public static final String UPLOADORDERCANCEL = "调单取消";
    public static final String UPLOADTRANS = "上传交易";
    public static final String REPRINTTRANS = "重打印交易水单";
    
    public static final String STATUS_ZERO = "0";
    public static final String STATUS_ONE = "1";
    public static final String STATUS_TWO = "2";
    public static final String STATUS_THREE = "3";
    public static final String STATUS_FOUR = "4";
    public static final String STATUS_FIVE = "5";
    public static final String STATUS_SIX = "6";

    public static final String SL = "SL";
    public static final String PICI = "PICI";

    public static final String GJ = "购进";
    public static final String SH = "收货";

    public static final String NEED_TASK = "0";

    //日志回滚策略
    public static final int MAX_WINDOW_SIZE = 500;


    public static final String NOT_FIND_DATA = "没有查询到相应数据.";
  public static final String NO_POSITION = "没有设置仓位.";
  public static final String BILL_IS_DEALED = "单据已经被处理.";

  public static final String TASK_FIND_FAILED = "待办任务查询失败.";
  
  //缓存时间
  public static final int REDIS_SECONDS = 2419200;    //4周

  
    //  public static final String FIND_COMMENT = "http://10.1.0.36:8001/Activiti/findComment.action";
}
