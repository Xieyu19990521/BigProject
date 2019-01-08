package com.example.yu.bigproject;

/**
 * @author YU
 * 应用接口
 */
public class Apis {
    //登录
    public static String USER_LOGIN="user/v1/login";
    public static String LOGIN_PHONE="phone";
    public static String LOGIN_PWD="pwd";

    //注册
    public static String USER_SIGN="user/v1/register";
    public static String SIGN_PHONE="phone";
    public static String SIGN_PWD="pwd";

    //首页
    //recyclerview数据
    public static String HOME_LIST="commodity/v1/commodityList";
    //banner数据
    public static String HOME_BANER="commodity/v1/bannerShow";
    //根据关键字搜索
    public static String HOME_SEARCH="commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=10";
    //点击详情
    public static String HOME_DETAIL="commodity/v1/findCommodityDetailsById?commodityId=%s";
    //点击更多
    public static String HOME_MORE="commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=10";
    //弹出框 一级类条目
    public static String HOME_POP_FIRST="commodity/v1/findFirstCategory";
    //弹出框 二级类条目
    public static String HOME_POP_SECONED="commodity/v1/findSecondCategory?firstCategoryId=%s";
    public static String HOME_SECONED_MORE="commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=10";


    //圈子
    //圈子加载数据
    public static String CIRCLE_LIST="circle/v1/findCircleList?page=1&count=10";
    //圈子点赞
    public static String CIRCLE_ADDGREAT="circle/verify/v1/addCircleGreat";
    //圈子取消点赞
    public static String CIRCLE_CANCELFREAT="circle/verify/v1/cancelCircleGreat?circleId=%d";


    //购物车
    //加入购物车
    public static String SHOPCAR_ADD="order/verify/v1/syncShoppingCart";
    //查看购物车信息
    public static String SHOPCAR_SEL="order/verify/v1/findShoppingCart";


    //订单相关的接口
    public static String LIST_URL="order/verify/v1/findOrderListByStatus?page=%d&count=10";
    //status  0=全部 1=查看待付款  2=查看待收货  3=查看待评价  9=查看已完成


    //我的页面
    //修改用户昵称
    public static String USER_UPDATE_PETNAME="user/verify/v1/modifyUserNick";
    //修改用户密码
    public static String USER_UPDATE_PWD="user/verify/v1/modifyUserPwd";
    //用户上传头像
    public static String USER_AVATAR="user/verify/v1/modifyHeadPic";
    //获取用户信息
    public static String USER_PERSON_NEW="user/verify/v1/getUserById";
    //我的圈子
    public static String USER_CIRCLE="circle/verify/v1/findMyCircleById?page=%d&count=10";
    //我的足迹
    public static String USER_FOOT="commodity/verify/v1/browseList?page=%d&count=10";
    //我的地址列表
    public static String USER_ADDRESS="user/verify/v1/receiveAddressList";
    //修改地址
    public static String USER_ADDRESS_UPDATE="user/verify/v1/changeReceiveAddress";
    //新增收货地址
    public static String USER_ADD_ADDRESS="user/verify/v1/addReceiveAddress";
    //设置默认收货地址
    public static String USER_DEFAULT_ADDRESS="user/verify/v1/setDefaultReceiveAddress";
}
