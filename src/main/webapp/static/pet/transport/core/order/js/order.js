//图片滚动
$('#slide1').swipeSlide({
    autoSwipe:true,//自动切换默认是
    speed:3000,//速度默认4000
    continuousScroll:true,//默认否
    transitionType:'cubic-bezier(0.22, 0.69, 0.72, 0.88)',//过渡动画linear/ease/ease-in/ease-out/ease-in-out/cubic-bezier
    lazyLoad:true,//懒加载默认否
    firstCallback : function(i,sum,me){
        me.find('.dot').children().first().addClass('cur');
    },
    callback : function(i,sum,me){
        me.find('.dot').children().eq(i).addClass('cur').siblings().removeClass('cur');
    }
});


function subMitOrder(){
    var param = {};
    //组装前端参数
    var startPlaceCode = $("#startPlaceCode").val();
    if(startPlaceCode==undefined || startPlaceCode==""){
        showErrorTips("请选择出发地");
        return;
    }
    var startPlaceName = $("#startPlaceName").val();

    var destinationPlaceCode = $("#destinationPlaceCode").val();
    if(destinationPlaceCode==undefined || destinationPlaceCode==""){
        showErrorTips("请选择目的地");
        return;
    }
    var destinationPlaceName = $("#destinationPlaceName").val();

    var selectDate = $("#selectDate").val();
    if(selectDate==undefined || selectDate==""){
        showErrorTips("请选择航班时间");
        return;
    }

    var petKind = $("#petKind").val();
    if(petKind==undefined || petKind==""){
        showErrorTips("请填写宠物品种");
        return;
    }

    var petWeight = $("#petWeight").val();
    if(petWeight==undefined || petWeight==""){
        showErrorTips("请填写宠物重量");
        return;
    }

    var selHkx = $("#selHkx").val();
    if(selHkx==undefined || selHkx==""){
        //showErrorTips("请选择"); 使用提示的方式进行提示没有选择航空箱
    }

    if($("#selDWJYHG").is(":checked") ==false){
        showErrorTips("请确认已经开具出发当日有效的《动物检疫许可证》！"); //
        return;
    }
    var detailId = $("#detailId").val();
    var receiptPlace = $("#receiptPlace").val();

    var detailName="";
    if($("#selSmjc").is(":checked") ==true){
        //选择了上门取宠 才进行校验
        if(detailId==undefined || detailId==""){
            showErrorTips("请选择接宠地点");
            return;
        }

        detailName=$("#detailId").find("option:selected").text();
        if(receiptPlace==undefined || receiptPlace==""){
            showErrorTips("请填写接宠详细地址");
            return;
        }

    }


    //showErrorTips("请选择"); 使用提示的方式进行提示没有选择航空箱
    param.placeAreaCode=detailId;
    param.placeAreaName=detailName;//"历下区";
    param.placeDetail=receiptPlace;

    param.startPlaceCode=startPlaceCode;
    param.startPlaceName=startPlaceName;
    param.destinationPlaceCode=destinationPlaceCode;
    param.destinationPlaceName=destinationPlaceName;
    param.insuredPrice="0";

    param.transDate=selectDate;
    param.petKind=petKind;
    param.petWeight=petWeight;
    param.selHkx=$("#selHkx").is(":checked");
    param.selSmjc=$("#selSmjc").is(":checked");

    $.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url : "/pet/ticket/order/sumbitOrder",
        data : param,
        success : function(data) {
            var retData = JSON.parse(data);
            if(retData.status =="success"){
                $('#hkxsysm,#sumbitFail').fadeOut();
                $('#sumbitSuccess').fadeIn();
                $('#orderId').val(retData.orderId);
            }else{
                $('#hkxsysm,#sumbitSuccess').fadeOut();
                $('#sumbitFail').fadeIn();
            }
            // console.log(data);
        },
        error : function(){
            showErrorTips("出现网络错误");
        }

    });

}

/**
 * {
                "appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入
                "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数
                "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串
                "package":"prepay_id=u802345jgfjsdfgsdg888",
                "signType":"MD5",         //微信签名方式：
                "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
            }
 */
function onBridgeReady(data){
    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', data,
        function(res){
            if(res.err_msg == "get_brand_wcpay_request:ok" ){
                // 使用以上方式判断前端返回,微信团队郑重提示：
                //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
            }
        });
}