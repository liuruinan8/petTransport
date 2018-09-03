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
    //组装前端参数
    var startPlaceCode = $("#startPlaceCode").val();
    if(startPlaceCode==undefined || startPlaceCode==""){
        showErrorTips("请选择出发地");
        return;
    }
    var destinationPlaceCode = $("#destinationPlaceCode").val();
    if(destinationPlaceCode==undefined || destinationPlaceCode==""){
        showErrorTips("请选择目的地");
        return;
    }
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
    var selSmjc = $("#selSmjc").val();
    if(selSmjc==undefined || selSmjc==""){
        //showErrorTips("请选择"); 使用提示的方式进行提示没有选择航空箱
    }

    var param = {};
    param.startPlaceCode=startPlaceCode;
    param.destinationPlaceCode=destinationPlaceCode;
    param.selectDate=selectDate;
    param.petKind=petKind;
    param.petWeight=petWeight;
    param.selHkx=selHkx;
    param.selSmjc=selSmjc;

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