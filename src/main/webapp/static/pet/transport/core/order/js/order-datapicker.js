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
/**
 * 显示当前日期之后的一个月数据
 * @type {Array}
 */
var lastMonth = [];
for(var i = 29;i>=0;i--){
    var obj ={};
    var dateStr =new Date(new Date()
        .setDate(new Date().getDate()+i))
        .toLocaleString().substring(0,9);
    obj.label=dateStr;
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
    weui.picker(lastMonth, {
        className: 'custom-classname',
        defaultValue: [new Date()
            .toLocaleString().substring(0,9), new Date()
            .toLocaleString().substring(0,9)],
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

        var param = {};
        param.startPlaceCode=startPlaceCode;
        $.ajax({
            type : "POST",
            contentType: "application/x-www-form-urlencoded;charset=UTF-8",
            url : "/pet/ticket/price/countCost",
            data : param,
            success : function(data) {
                console.log(data);
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
                showErrorTips("出现网络错误");
            }

        });
    });
    /**
     * 监听提交按钮
     */
    $('#submitOrder').on('click', function () {
        subMitOrder();
    });

});

