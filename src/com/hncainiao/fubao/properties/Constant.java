package com.hncainiao.fubao.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Environment;

/**
 * @author zhaojing
 * @version 2015年4月1日 上午10:34:52
 * 
 *          常量
 */
public class Constant {

	/**
	 * onInterceptTouchEvent默认返回值是false，
	 * 这样touch事件会传递到View控件，ViewGroup里的onTouchEvent默认返回值是false；
	 */
	public static boolean ISITEMSCROLL = false;

	/**
	 * 基本数据定义
	 */
	public static final String EMPTY_STRING = ""; // 空的字符串

	public static final String NULL_STRING = "null";

	public static final int ZERO_INT = 0;

	public static final int NEGATIVE_ONE = -1;

	public static final boolean DEFAULT_BOOL = false;
	
	


	public static String LOOK_INTRDOUCE_VERSION = "LOOK_INTRDOUCE";

	public static Integer PAGENUMHOW = 0;

	public static boolean isLocation = false;

	/**
	 * 头像地址
	 */
	public static String url_getimage="";

	public static String action_image="imagehead";

	public static final String ISAUTOSTART = "ISAUTOSTART";
	/**
	 * 图片域名
	 */
	public static final String URL_IMAGE_HOST_STRING="http://wx.zgcainiao.com/Uploads/Images/";
	
	/**
	 * 主机
	 */
	public static final String host="http://wx.zgcainiao.com";

	/**
	 * 上传头像
	 */
	public static String upimage=host+"/index.php/api/modAvatar";
	/**
	 * 验证码
	 */
	public static final String url_YZM=host+"/index.php/api/getVcode";
	
	
	/**
	 * 注册
	 */
	public static final String url_Register=host+"/index.php/api/register";
	
	/**
	 * 登录
	 */
	public static final String url_login=host+"/index.php/api/login";
	
	/**
	 *  初始化套餐列表
	 */
	public static final String URL_LEI_STRING=host+"/index.php/api/getPhysicalExamPackageList";
	
	
	/**
	 * 获取套餐分类
	 */
	public static final String url_taocantype=host+"/index.php/api/getPhysicalExamPackageCategory";
	
	/**
	 * 套餐搜索
	 */
	public static final String URL_TAOSANSOSO_STRING=host+"/index.php/api/getPhysicalExamPackage";
	/**
	 * 得到历史体检
	 */
	public static final String URL_HOSTY_EXAM_STRING=host+"/index.php/api/getPhysicalExamInfo";
	
	public static  final String url_hosteyphy=host+"/index.php/api/getPhysicalExamList";
	
	/**
	 * 个人中心-我的预约体检
	 */
	public static final String url_my_phy=host+"/index.php/api/getPhysicalExamList";
	/**
	 * 个人中心-我的体检报告
	 */
	public static final String url_my_baogao=host+"/index.php/api/getPhysicalExamReportList";
	/**
	 * 体检搜索
	 */
	public static final String URL_PHYSEARCH_STRING=host+"/index.php/api/getPhysicalExamPackage";
	// 手机获取验证码接口：
	public static final String YZM_STRING = "http://wx.zgcainiao.com/index.php/api/getVcode";
	// 注册提交接口
	public static final String REGURL = "http://wx.zgcainiao.com/index.php/api/register";
	// 登陆提交
	public static final String LOGINURL = "http://wx.zgcainiao.com/index.php/api/login";
	// 退出接口
	public static final String LOGOUT = "http://wx.zgcainiao.com/index.php/api/logout";
	// 默认12个城市接口
	public static final String MORENCITY = "http://wx.zgcainiao.com/index.php/api/getRegion";
	// 获取医院列表（10公里内）
	public static final String SELECTHOSPITAL = "http://wx.zgcainiao.com/index.php/api/getHospital";
	// 挂号选择科室 (先进入医院再选择科室)56
	public static final String GUAHAO_KESHI = "http://wx.zgcainiao.com/index.php/api/getDepartment";
	// 获取医生列表
	public static final String DOCTOR = "http://wx.zgcainiao.com/index.php/api/getDoctorList";
	// 医生详情接口
	public static final String DOCTORINFO = "http://wx.zgcainiao.com/index.php/api/getDoctorInfo";
	// 醫院主页接口
	public static final String MAIN_HOSPITAL = "http://wx.zgcainiao.com/index.php/api/getHospitalModule";
	public static final String MAIN_HOSPITAL2 = "http://wx.zgcainiao.com/index.php/api/getHospitalHome";
	// 预约挂号接口：
	public static final String ORDER_GUAHAO = "http://wx.zgcainiao.com/index.php/api/getHospitalHome";
	// 预约详情接口
	public static final String ORDER_JUTI = "http://wx.zgcainiao.com/index.php/api/getDoctorScheduleInfo";
	// 添加关注接口
	public static final String ADDATTENTION = "http://wx.zgcainiao.com/index.php/api/addSubscribe";
	//预约列表
	public static final String ORDER_list = "http://wx.zgcainiao.com/index.php/api/getDoctorScheduleList";

	// 新建就诊人接口
	public static final String ADDJIUZHENREN = "http://wx.zgcainiao.com/index.php/api/addPatient";
	// 就診人列表
	public static final String LISTJIUZHENREN = "http://wx.zgcainiao.com/index.php/api/getPatientList";
	//就诊人删除
	public static final String DELETEJIUZHENREN = "http://wx.zgcainiao.com/index.php/api/delPatient";
	//就诊人修改
	public static final String UPDATEJIUZHENREN = "http://wx.zgcainiao.com/index.php/api/modPatient";


	// 就诊人详情
	public static final String Jiuzhenren_Juti = "http://wx.zgcainiao.com/index.php/api/getPatientInfo";
	// 银行卡列表
	public static final String BANKLIST = "http://wx.zgcainiao.com/index.php/api/getBankcardList";
	// 所有銀行
	public static final String ALLBAKN = "http://wx.zgcainiao.com/index.php/api/getBank";
	// 添加银行
	public static final String ADDBANK = "http://wx.zgcainiao.com/index.php/api/addBankcard";
	// 体检分类
	public static final String FENLEI_TIJIAN = "http://wx.zgcainiao.com/index.php/api/getPhysicalExamPackageCategory";
	// 套餐列表
	public static final String TaoCan_LIST = "http://wx.zgcainiao.com/index.php/api/getPhysicalExamPackageList";
	// 新建体检人
	public static final String NEWtijian_person = "http://wx.zgcainiao.com/index.php/api/addPhysicalExamPeople";
	// 体检人列表
	public static final String Tijianperson_List = "http://wx.zgcainiao.com/index.php/api/getPhysicalExamPeopleList";
    //关注的医生列表
	public static final String CONNER_DOCTOR = host+"/index.php/api/getSubscribeList";
	//关注状态
	public static final String CONNER_zt = host+"/index.php/api/getSubscribeStatus";
	//取消關注
	public static final String DEL_CONNER = host+"/index.php/api/cancelSubscribe";
	//体检人详情
	public static final String TijianXiangqing = host+"/index.php/api/getPhysicalExamPeopleInfo";
	//体检人删除
	public static final String DELtijian = host+"/index.php/api/delPhysicalExamPeople";
	
	//体检人修改号码
	public static final String Updatatijianphone = host+"/index.php/api/modPhysicalExamPeoplePhone";
	//添加挂号生成订单
	public static final String CreatOrder = host+"/index.php/api/addRegister";
	//挂号订单列表
	public static final String ResList = host+"/index.php/api/getRegisterList";
	//挂号详情
	public static final String ResXiqng = host+"/index.php/api/getRegisterInfo";
	//取消挂号
	public static final String CannelGuahao = host+"/index.php/api/cancelRegister";
	//修改密码
	public static final String Updatekey = host+"/index.php/api/resetPassword";
	//app版本
	public static final String APPVersion = host+"/index.php/api/getVersion";
	//挂号搜索
	public static final String ResSerach = host+"/index.php/api/search";

	

	
	




	/**
	 * 得到用户绑定的银行卡
	 */
	public static final String member_yhk=host+"/index.php/api/getBankcardList";
	
	
	/**
	 * 到院支付
	 */
	public static final String porder_host=host+"/index.php/api/addPhysicalExam";
	
	/**
	 * 拍照
	 */
	public static final int PHOTO_REQUEST_CAMERA = 1;
	/**
	 * 从相册中选择
	 */
	public static final int PHOTO_REQUEST_GALLERY = 2;
	/**
	 * 裁剪返回结果
	 */
	public static final int PHOTO_REQUEST_CUT = 3;

	/**
	 * 头像根路径
	 */
	public static final String ROOT_PATH = Environment
			.getExternalStorageDirectory().getPath() + "/" + ".yanyuan";
	/**
	 * 头像的临时目录路径
	 */
	public static final String TMP_PATH = ROOT_PATH + "/tmp";

	/**
	 * 设置医院主页中的菜单数据
	 * 
	 * @return
	 */
	public static List<HashMap<String, String>> getMenuList() {
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("classId", "1");
		map1.put("imageId", "selector_daozheng");
		list.add(map1);

		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("classId", "2");
		map2.put("imageId", "selector_order_regis");
		list.add(map2);

		HashMap<String, String> map3 = new HashMap<String, String>();
		map3.put("classId", "3");
		map3.put("imageId", "selector_wait_doctor");
		list.add(map3);

		HashMap<String, String> map4 = new HashMap<String, String>();
		map4.put("classId", "4");
		map4.put("imageId", "takemedicsion_noopen");
		list.add(map4);

		HashMap<String, String> map5 = new HashMap<String, String>();
		map5.put("classId", "5");
		map5.put("imageId", "hos_noopen");
		list.add(map5);

		HashMap<String, String> map6 = new HashMap<String, String>();
		map6.put("classId", "6");
		map6.put("imageId", "reporter_noopen");
		list.add(map6);

		HashMap<String, String> map7 = new HashMap<String, String>();
		map7.put("classId", "7");
		map7.put("imageId", "baoxiao_noopen");
		list.add(map7);

		HashMap<String, String> map8 = new HashMap<String, String>();
		map8.put("classId", "8");
		map8.put("imageId", "zhuanzhen_noopen");
		list.add(map8);

		HashMap<String, String> map9 = new HashMap<String, String>();
		map9.put("classId", "9");
		map9.put("imageId", "zhuyuan_noopen");
		list.add(map9);
		return list;
	}
	public  static String[][] ary = {
 			// A
 			{ "鞍山市", "安庆市", "安阳市", "阿坝藏族羌族自治州", "安顺市", "安康市", "阿里地区", "阿勒泰地区",
 					"阿克苏地区", "阿拉尔市", "阿拉善盟", "澳门特别行政区" },
 			// B
 
 			{ "北京市", "保定市", "本溪市", "白城市", "白山市", "蚌埠市", "亳州市", "滨州市", "白银市",
 					"巴中市", "毕节地区", "白沙黎族自治县", "保亭黎族苗族自治县", "保山市", "宝鸡市", "百色市",
 					"北海市", "博尔塔拉蒙古自治州", "巴音郭楞蒙古自治州", "包头市", "巴彦淖尔市" },
 			// C
 
 			{ "长沙市", "承德市", "沧州市", "朝阳市", "常州市", "滁州市", "巢湖市", "池州市", "郴州市",
 					"常德市", "潮州市", "成都市", "澄迈县", "昌江黎族自治县", "楚雄彝族自治州", "崇左市",
 					"昌都地区", "昌吉回族自治州", "赤峰市" },
 			// D
 
 			{ "大同市", "大连市", "丹东市", "大兴安岭地区", "大庆市", "德州市", "东营市", "东莞市", "定西市",
 					"达州市", "德阳市", "儋州市", "东方市", "定安县", "德宏傣族景颇族自治州", "大理白族自治州",
 					"迪庆藏族自治州" },
 			// E
 
 			{ "恩施市", "鄂州市", "鄂尔多斯市" },
 
 			// F
 
 			{ "抚顺市", "阜新市", "阜阳市", "福州市", "抚州市", "佛山市", "防城港市" },
 			// G
 
 			{ "赣州市", "广州市", "甘南藏族自治州", "广安市", "甘孜藏族自治州", "广元市", "贵阳市",
 					"果洛藏族自治州", "桂林市", "贵港市", "固原市", "高雄市" },
 			// H
 
 			{ "衡水市", "呼和浩特市", "呼伦贝尔市", "和田地区", "哈密地区", "河池市", "贺州市", "汉中市",
 					"海西蒙古族藏族自治州", "邯郸市", "海南藏族自治州", "黄南藏族自治州", "海东地区",
 					"海北藏族自治州", "红河哈尼族彝族自治州", "海口市", "河源市", "惠州市", "怀化市", "衡阳市",
 					"黄石市", "黄冈市", "鹤壁市", "菏泽市", "黄山市", "淮北市", "淮南市", "合肥市",
 					"湖州市", "杭州市", "淮安市", "黑河市", "鹤岗市", "哈尔滨市", "葫芦岛市" },
 			// I
 			{ "暂无" },
 			// J
 
 			{ "嘉义市", "基隆市", "酒泉市", "嘉峪关市", "金昌市", "江门市", "揭阳市", "荆门市", "荆州市",
 					"焦作市", "济源市", "济宁市", "济南市", "景德镇市", "吉安市", "九江市", "金华市",
 					"嘉兴市", "佳木斯市", "鸡西市", "吉林市", "锦州市", "晋城市", "晋中市" },
 			// K
 
 			{ "开封市", "昆明市", "克孜勒苏柯尔克孜自治州", "克拉玛依市", "喀什地区" },
 			// L
 
 			{ "林芝地区", "拉萨市", "柳州市", "来宾市", "丽江市", "临沧市", "陵水黎族自治县", "乐东黎族自治县",
 					"临高县", "六盘水市", "凉山彝族自治州", "乐山市", "泸州市", "临夏回族自治州", "陇南市",
 					"兰州市", "娄底市", "漯河市", "洛阳市", "聊城市", "莱芜市", "临沂市", "龙岩市",
 					"六安市", "丽水市", "连云港市", "辽源市", "辽阳市", "吕梁市", "临汾市", "廊坊市" },
 			// M
 
 			{ "牡丹江市", "马鞍山市", "茂名市", "梅州市", "绵阳市", "眉山市" },
 			// N
 
 			{ "南京市", "南通市", "宁波市", "宁德市", "南平市", "南昌市", "南阳市", "南充市", "内江市",
 					"怒江傈傈族自治州", "南宁市", "那曲地区" },
 			// O
 			{ "暂无" },
 			// P
 
 			{ "盘锦市", "莆田市", "萍乡市", "平顶山市", "濮阳市", "平凉市", "攀枝花市", "普洱市" },
 			// Q
 
 			{ "秦皇岛市", "齐齐哈尔市", "七台河市", "衢州市", "泉州市", "青岛市", "潜江市", "清远市",
 					"庆阳市", "黔南布依族苗族自治州", "黔东南苗族侗族自治州", "黔西南布依族苗族自治州", "琼海市",
 					"琼中黎族苗族自治县", "曲靖市", "钦州市" },
 			// R
 
 			{ "日照市", "日喀则地区" },
 			// S
 			{ "上海市", "石家庄市", "朔州市", "沈阳市", "四平市", "松原市", "双鸭山市", "绥化市", "苏州市",
 					"宿迁市", "绍兴市", "宿州市", "厦门市", "三明市", "上饶市", "商丘市", "三门峡市",
 					"神农架林区", "十堰市", "随州市", "邵阳市", "汕尾市", "韶关市", "汕头市", "深圳市",
 					"遂宁市", "三亚市", "商洛市", "山南地区", "石嘴山市", "石河子市" },
 			// T
 
 			{ "天津市", "唐山市", "太原市", "铁岭市", "通化市", "泰州市", "台州市", "铜陵市", "泰安市",
 					"天门市", "天水市", "铜仁地区", "屯昌县", "铜川市", "塔城地区", "吐鲁番地区",
 					"图木舒克市", "通辽市", "台北市", "台中市", "台南市" },
 			// U
 			{ "暂无" },
 			// V
 			{ "暂无" },
 			// W
 
 			{ "无锡市", "温州市", "芜湖市", "潍坊市", "威海市", "武汉市", "武威市", "五指山市", "文昌市",
 					"万宁市", "文山壮族苗族自治州", "渭南市", "梧州市", "吴忠市", "乌鲁木齐市", "五家渠市",
 					"乌海市", "乌兰察布市" },
 			// X
 
 			{ "邢台市", "忻州市", "徐州市", "宣城市", "新余市", "新乡市", "许昌市", "信阳市", "襄阳市",
 					"孝感市", "咸宁市", "仙桃市", "湘潭市", "湘西土家族苗族自治州", "西双版纳傣族自治州",
 					"西宁市", "西安市", "咸阳市", "锡林郭勒盟", "兴安盟", "新竹市", "香港特别行政区" },
 			// Y
 
 			{ "阳泉市", "运城市", "营口市", "延边朝鲜族自治州", "伊春市", "扬州市", "盐城市", "鹰潭市",
 					"宜春市", "烟台市", "宜昌市", "岳阳市", "益阳市", "永州市", "阳江市", "云浮市",
 					"宜宾市", "雅安市", "玉溪市", "玉树藏族自治州", "延安市", "榆林市", "玉林市", "银川市",
 					"伊犁哈萨克自治州" },
 			// Z
 
 			{  "张家口市", "长治市", "长春市", "镇江市", "舟山市", "漳州市", "淄博市", "枣庄市",
 					"郑州市", "周口市", "驻马店市", "株洲市", "张家界市", "珠海市", "肇庆市", "湛江市",
 					"中山市", "张掖市", "自贡市", "资阳市", "遵义市", "昭通市", "中卫市" } };

	/**
	 * 我的个人消息
	 */
	public static String url_my_message=host+"/index.php/api/getMessageList";

	public static String url_taocan=host+"/index.php/api/getPhysicalExamPackage";

	/**
	 * 修改手机号码
	 */
	public static String url_modify_phone=host+"/index.php/api/modPhone";
	/**
	 * 修改昵称
	 */
	public static String url_modify_nick=host+"/index.php/api/modNickname";
	/**
	 * 修改地址
	 */
	public static String url_modify_address=host+"/index.php/api/modAddress";
	/**
	 * 修改性别
	 */
	public static String url_modify_sex=host+"/index.php/api/modGender";

	/**
	 * 修改密码
	 */
	public static String url_modify_pwd=host+"/index.php/api/modPassword";

	/**
	 *  得到tn码
	 */
	public static String url_gettn=host+"/index.php/api/getTn";
	/**
	 *  添加银行卡
	 */
	public static String url_addbankcard=host+"/index.php/api/addBankcard";
	/**
	 *  获取银行
	 */
	public static String url_getbank=host+"/index.php/api/getBank";


}
