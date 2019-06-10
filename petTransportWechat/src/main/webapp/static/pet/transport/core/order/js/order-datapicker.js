/**
 * 显示错误提示
 * @param msg
 */
function showErrorTips(msg){

    var $tooltips = $('.js_tooltips');
    $tooltips.html(msg);//"请选择出发地点"
    if ($tooltips.css('display') != 'none') return;

    // toptips的fixed, 如果有`animation`, `position: fixed`不生效
    $('.page.cell').removeClass('slideIn');

    $tooltips.css('display', 'block');
    setTimeout(function () {
        $tooltips.css('display', 'none');
    }, 2000);
}

/**
 * 页面加载时
 * 查询所有的出发地点
 */
$.ajax({
    type : "POST",
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url : "/pet/ticket/startPlace/getAll",
    data : "",
    success : function(data) {
        //console.log(data);
        var dataArray = [];
        var placeData = JSON.parse(data);
        var startPlaceCode = $("#startPlaceCode").val();
        var startPlaceName = $("#startPlaceName").val();
        for( var attr in placeData)
        {
            var array = placeData[attr];
            var newArray = [];
            for(var i=0;i<array.length;i++){
                var tmp = {
                    label:array[i].startPlaceName,
                    value:array[i].startPlaceCode+";"+array[i].startPlaceName
                };
                newArray.push(tmp);
            }
            var tmp = {
                label:attr,
                value:attr,
                children:newArray
            };
            dataArray.push(tmp);
        }
        $('#startPlaceName').on('click', function () {
            weui.picker(dataArray,
                {
                defaultValue:startPlaceCode+";"+startPlaceName,
                onChange: function (result) {
                    var start = result[1];
                    var startVal = start.split(";");
                    $("#startPlaceCode").val(startVal[0]);
                    $("#startPlaceName").val(startVal[1]);

                    $("#destinationPlaceCode").val("");
                    $("#destinationPlaceName").val("");

                    //清空上门取宠 和option
                    $('#selSmjc').prop("checked","");
                    $('#jieChongInfo').hide();
                    $("#detailId").empty();
                },
                onConfirm: function (result) {
                    var start = result[1];
                    var startVal = start.split(";");
                    $("#startPlaceCode").val(startVal[0]);
                    $("#startPlaceName").val(startVal[1]);
                    getDist(startVal[0]);
                }
            });
        });
    },
    error : function(){
        showErrorTips("出现网络错误");
    }

});
$("#startPlaceCode").val("5028_CKG");
$("#startPlaceName").val("重庆江北");
var startPlaceCode = $("#startPlaceCode").val();
if(startPlaceCode !=""){
    getDist(startPlaceCode);
    if($("#selSmjc").is(":checked") ==true){
        //点击上门取宠后 加载相关信息
        getPlaceDetail(startPlaceCode);
    }
}
/**
 * 显示当前日期之后的一个月数据
 * @type {Array}
 */
var lastMonth = [];
for(var i = 29;i>=0;i--){
    var obj ={};
    var dateStr ="";
    if(new Date(new Date()
        .setDate(new Date().getDate()+i)).getMonth()<9){
        dateStr =new Date(new Date()
            .setDate(new Date().getDate()+i))
            .toLocaleString().substring(0,9);
    }else{
        dateStr =new Date(new Date()
            .setDate(new Date().getDate()+i))
            .toLocaleString().substring(0,10);
    }
    var str = "";
    var week = new Date(new Date()
        .setDate(new Date().getDate()+i)).getDay();
    if (week == 0) {
        str = "星期日";
    } else if (week == 1) {
        str = "星期一";
    } else if (week == 2) {
        str = "星期二";
    } else if (week == 3) {
        str = "星期三";
    } else if (week == 4) {
        str = "星期四";
    } else if (week == 5) {
        str = "星期五";
    } else if (week == 6) {
        str = "星期六";
    }
    obj.label=dateStr+" "+str;
    obj.value=dateStr;
    lastMonth.unshift(obj);
}
var placeDataStr="";
/**
 * 监听目的地点击方法
 */
// language=JQuery-CSS
$('#destinationPlaceName').on('click', function () {
    var startPlaceCode = $("#startPlaceCode").val();
    if(startPlaceCode==undefined || startPlaceCode==""){
        //alert("请选择出发地点");
        showErrorTips("请选择出发地点");
    }else{
        //getDist(startPlaceCode);
        showDst(placeDataStr);
    }
});


/**
 * 监听机票预订日期点击方法
 */
$('#showDatePicker').on('click', function () {
    var dateStr="";
    if(new Date().getMonth()<9){
        dateStr =new Date(new Date()
            .setDate(new Date().getDate()))
            .toLocaleString().substring(0,9);
    }else{
        dateStr =new Date(new Date()
            .setDate(new Date().getDate()))
            .toLocaleString().substring(0,10);
    }
    weui.picker(lastMonth, {
        className: 'custom-classname',
        defaultValue: [dateStr, dateStr],
        onChange: function (result) {
            //console.log(result)
            document.getElementById("showDatePicker").innerText="已选择航班日期"+result;
            document.getElementById("selectDate").value=result;
        },
        onConfirm: function (result) {
            // console.log(result)
            document.getElementById("showDatePicker").innerText="已选择航班日期"+result;
            document.getElementById("selectDate").value=result;
        },
        id: 'showDatePicker'
    });
});
//加载品种信息
$.ajax({
    type : "POST",
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url : "/pet/ticket/petKind/getAll",
    data : "",
    success : function(data) {
        //console.log(data);
        var dataArray = [];
        var placeData = JSON.parse(data);
        for( var attr in placeData)
        {
            var array = placeData[attr];
            var newArray = [];
            for(var i=0;i<array.length;i++){
                var tmp = {
                    label:array[i].kindName,
                    value:array[i].kindCode+";"+array[i].kindName+";"+array[i].kindCharacter
                };
                newArray.push(tmp);
            }
            var tmp = {
                label:attr,
                value:attr,
                children:newArray
            };
            dataArray.push(tmp);
        };
        /**
         * 监听宠物品种点击方法
         */
        $('#showPetKindPickerTemplate').on('click', function () {
            weui.picker(dataArray, {
                className: 'custom-classname',
                //defaultValue: [dateStr, dateStr],
                onChange: function (result) {
                    //console.log(result)
                    var value = result[1].split(";")[0];
                    var disRes = result[1].split(";")[1];
                    var kindCharacter =  result[1].split(";")[2];
                    document.getElementById("showPetKindPickerTemplate").value=disRes;
                    document.getElementById("petKindTemplate").value=disRes+"("+value+")";
                    document.getElementById("petCharacterTemplate").value=kindCharacter;
                },
                onConfirm: function (result) {
                    // console.log(result)
                    var value = result[1].split(";")[0];
                    var disRes = result[1].split(";")[1];
                    var kindCharacter =  result[1].split(";")[2];
                    document.getElementById("showPetKindPickerTemplate").value=""+disRes;
                    document.getElementById("petKindTemplate").value=disRes+"("+value+")";
                    document.getElementById("petCharacterTemplate").value=kindCharacter;
                },
                id: 'showPetKindPickerTemplate'
            });
        });
        /**
         * 监听宠物品种点击方法
         */
        $('#showPetKindPicker').on('click', function () {
            weui.picker(dataArray, {
                className: 'custom-classname',
                //defaultValue: [dateStr, dateStr],
                onChange: function (result) {
                    //console.log(result)
                    var value = result[1].split(";")[0];
                    var disRes = result[1].split(";")[1];
                    var kindCharacter =  result[1].split(";")[2];
                    document.getElementById("showPetKindPicker").value=disRes;
                    document.getElementById("petKind").value=disRes+"("+value+")";
                    document.getElementById("petCharacter").value=kindCharacter;
                },
                onConfirm: function (result) {
                    // console.log(result)
                    var value = result[1].split(";")[0];
                    var disRes = result[1].split(";")[1];
                    var kindCharacter =  result[1].split(";")[2];
                    document.getElementById("showPetKindPicker").value=""+disRes;
                    document.getElementById("petKind").value=disRes+"("+value+")";
                    document.getElementById("petCharacter").value=kindCharacter;
                },
                id: 'showPetKindPicker'
            });
        });
    },
    error : function(){
        showErrorTips("出现网络错误");
    }

});

/**
 * 根据出发点获得目的地点
 * @param startPlaceCode
 */

function getDist(startPlaceCode) {
    var param = {};
    param.startPlaceCode=startPlaceCode;
    $.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url : "/pet/ticket/destPlace/getDstAll",
        data : param,
        success : function(data) {
            placeDataStr = data;
        },
        error : function(){
            showErrorTips("出现网络错误");
        }

    });
}

/**
 * 显示目的地
 * @param placeData
 */
function showDst(data){
    if(data == "{}"){
        //当前城市暂未开通目的地点
        showErrorTips("当前城市暂未开通目的地点");
        return;
    }

    //console.log(data);
    var dataArray = [];
    var placeData = JSON.parse(data);
    /**
     * 注意这里访问不能用.访问，也不能加引号，否则就代表访问的是json里面名称为attr的值了
     */
    for( var attr in placeData)
    {
        var array = placeData[attr];
        var newArray = [];
        for(var i=0;i<array.length;i++){
            var tmp = {
                label:array[i].destinationPlaceName,
                value:array[i].destinationPlaceCode+";"+array[i].destinationPlaceName
            };
            newArray.push(tmp);
        }
        var tmp = {
            label:attr,
            value:attr,
            children:newArray
        };
        dataArray.push(tmp);
    }

    weui.picker(dataArray, {
        onChange: function (result) {
            var start = result[1];
            var startVal = start.split(";");
            $("#destinationPlaceCode").val(startVal[0]);
            $("#destinationPlaceName").val(startVal[1]);


        },
        onConfirm: function (result) {
            var start = result[1];
            var startVal = start.split(";");
            $("#destinationPlaceCode").val(startVal[0]);
            $("#destinationPlaceName").val(startVal[1]);
        }
    });
    //$('#destinationPlaceName').on('click', function () {});
}

$(function(){

    $('#dialogs').on('click', '.weui-dialog__btn', function(){
        $(this).parents('.js_dialog').fadeOut(200);
    });

    /**
     * 监听联系客服方法
     */
    $('#customservice').on('click', function () {
        var dialog = $('#showServiceTel');
        dialog.fadeIn(200);
    });
    /**
     * 监听费用预估按钮
     */
    $('#price_count_sumbit').on('click', function () {
        //未执行完之前禁止点击第二次
        $('#price_count_sumbit').addClass("weui-btn_disabled");
        var startPlaceCode = $("#startPlaceCode").val();
        if(startPlaceCode==undefined || startPlaceCode==""){
            $('#price_count_sumbit').removeClass("weui-btn_disabled");
            showErrorTips("请选择出发地");
            return;
        }
        var destinationPlaceCode = $("#destinationPlaceCode").val();
        if(destinationPlaceCode==undefined || destinationPlaceCode==""){
            $('#price_count_sumbit').removeClass("weui-btn_disabled");
            showErrorTips("请选择目的地");
            return;
        }
        var selectDate = $("#selectDate").val();
        if(selectDate==undefined || selectDate==""){
            $('#price_count_sumbit').removeClass("weui-btn_disabled");
            showErrorTips("请选择航班时间");
            return;
        }
        var pets=[];

        var pet={};
        var petName = $("#petName").val();
        pet.petName=petName;
        var petKind = $("#petKind").val();
        pet.petKind=petKind;
        var petWeight = $("#petWeight").val();
        if(petWeight==undefined || petWeight==""){
            $('#price_count_sumbit').removeClass("weui-btn_disabled");
            showErrorTips("请填写宠物重量");
            return;
        }
        pet.petWeight=petWeight;
        var petHeight = $("#petHeight").val();
        pet.petHeight=petHeight;

        var petAge = $("#petAge").val();
        pet.petAge=petAge;
        var petCharacter = $("#petCharacter").val();
        pet.petCharacter=petCharacter;
        var petSex = $("input[name='petSex']:checked").val();;
        pet.petSex=petSex;

        var selDWJYHGInput = $("#selDWJYHGInput").is(':checked')+"";
        pet.selDWJYHG=selDWJYHGInput;
        var selHkxInput = $("#selHkx").is(':checked')+"";
        pet.selHkx=selHkxInput;
        pets.push(pet);

        $("#petAddDiv .pet-container").each(function(){
            var pet={};
            var petName =$("#petName",this).val();
            pet.petName=petName;
            var petKind =$("#petKind",this).val();
            pet.petKind=petKind;
            var petWeight =$("#petWeight",this).val();
            pet.petWeight=petWeight;
            var petHeight =$("#petHeight",this).val();
            pet.petHeight=petHeight;
            var selDWJYHGInput =$("#selDWJYHGInput",this).val()+"";
            pet.selDWJYHG=selDWJYHGInput;
            var selHkxInput =$("#selHkxInput",this).val()+"";
            pet.selHkx=selHkxInput;
            pets.push(pet);
        });
        if(pets.length==0){
            $('#price_count_sumbit').removeClass("weui-btn_disabled");
            showErrorTips("请添加宠物信息");
            return;
        }
        var placeDistance = $("#placeDistance").val();
        if($("#selSmjc").is(":checked") ==true){
            //选择了上门取宠 才进行校验
           var  receiptPlace = $("#receiptPlace").val();
            if(receiptPlace==undefined || receiptPlace==""){
                $('#price_count_sumbit').removeClass("weui-btn_disabled");
                showErrorTips("请选择接宠地点");
                return;
            }
            if(placeDistance==undefined || placeDistance==""){
                showErrorTips("请选择接宠地点");
                $('#price_count_sumbit').removeClass("weui-btn_disabled");
                return;
            }
            if(placeDistance>65){
                $('#price_count_sumbit').removeClass("weui-btn_disabled");
                showErrorTips("由于接送地点距离机场超过65公里，本司无法提供上门取宠服务");
                return;
            }

        }
        var declarePrice = $("#declarePrice").val();
        if($("#selBjfw").is(":checked") ==true){
            //选择了保价服务 才进行校验
            if(declarePrice==undefined || declarePrice==""){
                $('#price_count_sumbit').removeClass("weui-btn_disabled");
                showErrorTips("请填写声明价值");
                return;
            }
        }

        var otherPrice = $("#otherPrice").val();
        if(!otherPrice){
            otherPrice="0";
        }
        if($("#weuiAgree").is(":checked") !=true){
            $('#price_count_sumbit').removeClass("weui-btn_disabled");
            showErrorTips("请阅读并同意《宠物运输协议》");
            return;
        }

        var param = {};
        var petstr = JSON.stringify(pets);
        param.pets=petstr;
        param.startPlaceCode=startPlaceCode;
        param.destinationPlaceCode=destinationPlaceCode;
        param.placeDistance=placeDistance;
        param.transDate=selectDate;
        param.selSmjc=$("#selSmjc").is(":checked");
        param.selBjfw=$("#selBjfw").is(":checked");
        param.declarePrice=declarePrice;
        param.insuredPrice=declarePrice*5/100;
        param.otherPrice=otherPrice;
        $.ajax({
            type : "POST",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            url : "/pet/ticket/price/countCost",
            data : param,
            success : function(data) {
                $('#price_count_sumbit').removeClass("weui-btn_disabled");
                var placeData = JSON.parse(data);
                var ticketPrice=placeData.ticketPrice;
                var petBoxPrice=placeData.petBoxPrice;
                var placePrice=placeData.placePrice;
                var insuredPrice=placeData.insuredPrice;
                var totalPrice=placeData.totalPrice;
                $("#ticketPrice").val(ticketPrice);
                $("#petBoxPrice").val(petBoxPrice);
                $("#placePrice").val(placePrice);
                $("#insuredPrice").val(insuredPrice);
                $("#totalPrice").val(totalPrice);

                var html = "机票价格：" +ticketPrice +"<br/>"
                    +"航空箱价格：" +petBoxPrice +"<br/>"
                    +"上门接宠价格：" +placePrice +"<br/>"
                    +"保价：" +insuredPrice +"<br/>"
                    +"总价格：" +totalPrice;
                $("#countPriceInfo").html(html);
                var dialog = $('#showPrice');
                dialog.fadeIn(200);
            },
            error : function(){
                $('#price_count_sumbit').removeClass("weui-btn_disabled");
                showErrorTips("出现网络错误");
            }

        });
    });
    /**
     * 监听提交按钮
     */
    $('#submitOrder').on('click', function (e) {
        e.preventDefault();
        subMitOrder($('#submitOrder'));
    });
    /**
     * 监听查看航空箱适用规则
     */
    $('#ckhkxsygz').on('click', function () {
        //TODO 航空箱适用规则需要美化 图片更改为字 但是字体不要太大
        $('#sumbitSuccess,#sumbitFail').fadeOut();
        $('#hkxsysm').fadeIn();
    });
    /**
     * 监听动物检疫
     */
    $('#DWJYHG').on('click', function () {
        //TODO 航空箱适用规则需要美化 图片更改为字 但是字体不要太大
        $('#sumbitSuccess,#sumbitFail,#hkxsysm').fadeOut();
        $('#dwjyhgzmkjfs').fadeIn();
    });

    /**
     * selSmjc
     */
    $('#selSmjc').on('click', function () {
        //出发地是否选择 如果没有 先选择出发地
        var startPlaceCode = $("#startPlaceCode").val();
        if(startPlaceCode==undefined || startPlaceCode==""){
            showErrorTips("请选择出发地");
            $('#selSmjc').prop("checked","");
            return;
        }
        if($("#selSmjc").is(":checked") ==true){
            //点击上门取宠后 加载相关信息
            getPlaceDetail(startPlaceCode);
        }
        //如果选择了上门接宠 显示相关信息
        $('#jieChongInfo').toggle();
    });
    $('#selBjfw').on('click', function () {
        if($("#selBjfw").is(":checked") ==true){
            $('#bjfwInfo').show();
        }else{
            $('#declarePrice').val("");
            $('#bjfwInfo').hide();
        }
    });
    $('#selQtfy').on('click', function () {
        if($("#selQtfy").is(":checked") ==true){
            $('#qtfyInfo').show();
        }else{
            $('#otherPrice').val("0 ");
            $('#qtfyInfo').hide();
        }
    });
    /**
     * 线上支付监听
     */
    $('#toPay').on('click', function () {
        //$('#hkxsysm,#sumbitFail').fadeOut();
        //$('#sumbitSuccess').fadeOut();
        var param = {};
        param.orderId=$('#orderId').val();
        $.ajax({
            type : "POST",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            url : "/pet/ticket/order/pay4OrderByWx",
            data : param,
            success : function(data) {
                // var retData = JSON.parse(data);
                /*if(retData.status =="success"){
                    $('#hkxsysm,#sumbitFail').fadeOut();
                    $('#sumbitSuccess').fadeIn();
                }else{
                    $('#hkxsysm,#sumbitSuccess').fadeOut();
                    $('#sumbitFail').fadeIn();
                }*/
                console.log(data);
                var retData = JSON.parse(data);
                var payData = JSON.parse(retData.data);
                /**
                 * {
                                    "appId":"wx2421b1c4370ec43b",     //公众号名称，由商户传入
                                    "timeStamp":"1395712654",         //时间戳，自1970年以来的秒数
                                    "nonceStr":"e61463f8efa94090b1f366cccfbbb444", //随机串
                                    "package":"prepay_id=u802345jgfjsdfgsdg888",
                                    "signType":"MD5",         //微信签名方式：
                                    "paySign":"70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
                                }=
                 */
                var bridgeParam ={};
                bridgeParam.appId=payData.appId;
                bridgeParam.timeStamp=payData.timeStamp;
                bridgeParam.nonceStr=payData.nonceStr;
                bridgeParam.package=payData.package;
                bridgeParam.signType="MD5";
                bridgeParam.paySign=payData.sign;
                onBridgeReady(bridgeParam);
            },
            error : function(){
                showErrorTips("出现网络错误");
            }

        });
    });
    /**
     * 线下支付监听
     */
    $('#offlinePay').on('click', function () {
        //$('#hkxsysm,#sumbitFail').fadeOut();
        //$('#sumbitSuccess').fadeOut();
        //TODO 调用线下支付按钮修改线下支付状态
        location.href="/pet/ticket/order/mine";
        //跳转我的订单
    });

    /**
     * 重新提交监听 关闭所有的框，重新提交
     */
    $('#reCommit').on('click', function () {
        $('#hkxsysm,#sumbitFail').fadeOut();
        $('#sumbitSuccess').fadeOut();
    });

    /**
     * 残忍放弃 关闭
     */
    $('#commitGiveUp').on('click', function () {
        $('#hkxsysm,#sumbitFail').fadeOut();
        $('#sumbitSuccess').fadeOut();
        //残忍放弃直接跳转订单页面
        location.href="/pet/ticket/order/mine";
    });
});

function getPlaceDetail(startPlaceCode){
    var param = {};
    param.startPlaceCode=startPlaceCode;
    $.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url : "/pet/ticket/startPlaceDetail/getAllDetail",
        data : param,
        success : function(data) {
            console.log(data);
            if(data == "[]"){
                showErrorTips("当前城市未开通上门接宠服务，敬请期待");
                $('#selSmjc').prop("checked","");
                $('#jieChongInfo').hide();
                return;
            }
            //
            var obj = $("#detailId");
            var placeData = JSON.parse(data);
            for (var i = 0; i < placeData.length; i++) {
                var detail =  placeData[i];
                //obj.options.add(new Option(detail.get("name"),detail.get("code"))); //这个兼容IE与firefox
                $("#detailId").append("<option value='"+detail.code+"'>"+detail.name+"</option>");
            }
        },
        error : function(){
            showErrorTips("出现网络错误");
        }

    });
}