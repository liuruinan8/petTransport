$(function(){
    /**
     * 监听提交按钮
     */
    $('#adminSubmitOrder').on('click', function (e) {
        e.preventDefault();
        adminSubmitOrder();
    });
    $('#priceAdminSumbit').on('click', function (e) {
        e.preventDefault();
        adminPrice();
    });

    $.ajax({
        url: '/pet/ticket/airBox/getAirBoxAll',
        datatype: "json",
        success: function(data) {
            if (data) {
                var datalist = new Array;
                var retData = JSON.parse(data);
                for (var i = 0; i < retData.length; i++) {
                    var info = { "title": retData[i].boxTypeName, "value": retData[i].boxTypeId };
                    datalist.push(info);
                }
                //$("#petAirBoxIDTemplate").select("update", { items: datalist });
                $("#petAirBoxIDTemplate").select({
                    title: "选择航空箱",
                    items: ["法官", "医生", "猎人", "学生", "记者", "其他"]
                })
                /*$("#petAirBoxIDTemplate").select({
                    title: "",
                    multi: true,
                    items: datalist,
                    beforeClose: function (values, titles) {
                        /!*if (限制条件) {
                            $.toast("错误提示", "cancel");
                            return false;
                        }*!/
                        return true;
                    }
                });*/
            }
        }
    });

});
function adminSubmitOrder(){
    $('#adminSubmitOrder').addClass("weui-btn_disabled");
    var param = orgParam($('#adminSubmitOrder'),false);
    $.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url : "/pet/ticket/order/saveAndShouHuo",
        data : param,
        success : function(data) {
            $('#adminSubmitOrder').removeClass("weui-btn_disabled");
            var retData = JSON.parse(data);
            if(retData.status =="success"){
                $('#orderId').val(retData.orderId);
                //跳转到另外一个页面
                //location.href="/pet/ticket/orderAdmin/adminOrderSumbit";
            }else{
                $('#hkxsysm,#sumbitSuccess').fadeOut();
                $('#sumbitFail').fadeIn();
            }
        },
        error : function(){
            $('#adminSubmitOrder').removeClass("weui-btn_disabled");
            showErrorTips("出现网络错误");
        }

    });
}
function adminPrice(){
    $('#priceAdminSumbit').addClass("weui-btn_disabled");
    var startPlaceCode = $("#startPlaceCode").val();
    if(startPlaceCode==undefined || startPlaceCode==""){
        $('#priceAdminSumbit').removeClass("weui-btn_disabled");
        showErrorTips("请选择出发地");
        return;
    }
    var destinationPlaceCode = $("#destinationPlaceCode").val();
    if(destinationPlaceCode==undefined || destinationPlaceCode==""){
        $('#priceAdminSumbit').removeClass("weui-btn_disabled");
        showErrorTips("请选择目的地");
        return;
    }
    var selectDate = $("#selectDate").val();
    if(selectDate==undefined || selectDate==""){
        $('#priceAdminSumbit').removeClass("weui-btn_disabled");
        showErrorTips("请选择航班时间");
        return;
    }
    var pets=[];
    $("#petAddDiv .pet-container").each(function(){
        var pet={};
        var petId =$("#petId",this).val();
        pet.petId=petId;
        var petName =$("#petName",this).val();
        pet.petName=petName;
        var petKind =$("#petKind",this).val();
        pet.petKind=petKind;
        var petWeight =$("#petWeight",this).val();
        pet.petWeight=petWeight;
        var petHeight =$("#petHeight",this).val();
        pet.petHeight=petHeight;

        var petAge = $("#petAge",this).val();
        pet.petAge=petAge;
        var petCharacter = $("#petCharacter",this).val();
        pet.petCharacter=petCharacter;
        var petSex = $("#petSex",this).val();
        pet.petSex=petSex;

        var selDWJYHGInput =$("#selDWJYHGInput",this).val()+"";
        pet.selDWJYHG=selDWJYHGInput;
        var selHkxInput =$("#selHkxInput",this).val()+"";
        pet.selHkx=selHkxInput;
        pets.push(pet);
    });
    if(pets.length==0){
        $('#priceAdminSumbit').removeClass("weui-btn_disabled");
        showErrorTips("请添加宠物信息");
        return;
    }
    var placeDistance = $("#placeDistance").val();
    if($("#selSmjc").is(":checked") ==true){
        //选择了上门取宠 才进行校验
        var  receiptPlace = $("#receiptPlace").val();
        if(receiptPlace==undefined || receiptPlace==""){
            $('#priceAdminSumbit').removeClass("weui-btn_disabled");
            showErrorTips("请选择接宠地点");
            return;
        }
        if(placeDistance==undefined || placeDistance==""){
            $('#priceAdminSumbit').removeClass("weui-btn_disabled");
            showErrorTips("请选择接宠地点");
            return;
        }
        if(placeDistance>50){
            $('#priceAdminSumbit').removeClass("weui-btn_disabled");
            showErrorTips("由于接送地点距离机场超过50公里，本司无法提供上门取宠服务");
            return;
        }

    }
    var declarePrice = $("#declarePrice").val();
    if($("#selBjfw").is(":checked") ==true){
        //选择了保价服务 才进行校验
        if(declarePrice==undefined || declarePrice==""){
            $('#priceAdminSumbit').removeClass("weui-btn_disabled");
            showErrorTips("请填写声明价值");
            return;
        }
    }
    var otherPrice = $("#otherPrice").val();
    if(!otherPrice){
        otherPrice="0";
    }
    var param = {};
    var petstr = JSON.stringify(pets);
    param.pets=petstr;
    var id = $('#orderId').val();
    param.id=id;
    param.startPlaceCode=startPlaceCode;
    param.destinationPlaceCode=destinationPlaceCode;
    param.placeDistance=placeDistance;
    param.transDate=selectDate;
    param.selSmjc=$("#selSmjc").is(":checked");
    param.selBjfw=$("#selBjfw").is(":checked");
    param.declarePrice=declarePrice;
    param.insuredPrice=declarePrice*2/100;
    param.otherPrice=otherPrice;

    $.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url : "/pet/ticket/price/countCost",
        data : param,
        success : function(data) {
            $('#priceAdminSumbit').removeClass("weui-btn_disabled");
            var placeData = JSON.parse(data);
            var ticketPrice=placeData.ticketPrice;
            var petBoxPrice=placeData.petBoxPrice;
            var placePrice=placeData.placePrice;
            var insuredPrice=placeData.insuredPrice;
            var otherPrice=placeData.otherPrice;
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
                +"其他服务费用：" +otherPrice +"<br/>"
                +"总价格：" +totalPrice;
            $("#countPriceInfo").html(html);
            var dialog = $('#showPrice');
            dialog.fadeIn(200);
        },
        error : function(){
            $('#priceAdminSumbit').removeClass("weui-btn_disabled");
            showErrorTips("出现网络错误");
        }

    });
}