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
        //adminSubmitOrder();
        adminPrice();
    });

});
function adminSubmitOrder(){
    var param = orgParam(false);
    $.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url : "/pet/ticket/order/saveAndShouHuo",
        data : param,
        success : function(data) {
            var retData = JSON.parse(data);
            if(retData.status =="success"){
                $('#orderId').val(retData.orderId);
                //跳转到另外一个页面
                location.href="/pet/ticket/orderaAdmin/adminOrderSumbit";
            }else{
                $('#hkxsysm,#sumbitSuccess').fadeOut();
                $('#sumbitFail').fadeIn();
            }/**/

            // console.log(data);
        },
        error : function(){
            showErrorTips("出现网络错误");
        }

    });
}
function adminPrice(){
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
    var pets=[];

    var pet={};
    /*var petName = $("#petName").val();
    pet.petName=petName;
    var petKind = $("#petKind").val();
    pet.petKind=petKind;
    var petWeight = $("#petWeight").val();
    if(petWeight==undefined || petWeight==""){
        showErrorTips("请填写宠物重量");
        return;
    }
    pet.petWeight=petWeight;
    var petHeight = $("#petHeight").val();
    pet.petHeight=petHeight;
    var selDWJYHGInput = $("#selDWJYHGInput").is(':checked')+"";
    pet.selDWJYHG=selDWJYHGInput;
    var selHkxInput = $("#selHkx").is(':checked')+"";
    pet.selHkx=selHkxInput;
    pets.push(pet);*/

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
        var selDWJYHGInput =$("#selDWJYHGInput",this).val()+"";
        pet.selDWJYHG=selDWJYHGInput;
        var selHkxInput =$("#selHkxInput",this).val()+"";
        pet.selHkx=selHkxInput;
        pets.push(pet);
    });
    if(pets.length==0){
        showErrorTips("请添加宠物信息");
        return;
    }
    var placeDistance = $("#placeDistance").val();
    if($("#selSmjc").is(":checked") ==true){
        //选择了上门取宠 才进行校验
        var  receiptPlace = $("#receiptPlace").val();
        if(receiptPlace==undefined || receiptPlace==""){
            showErrorTips("请选择接宠地点");
            return;
        }
        if(placeDistance==undefined || placeDistance==""){
            showErrorTips("请选择接宠地点");
            return;
        }
        if(placeDistance>50){
            showErrorTips("由于接送地点距离机场超过50公里，本司无法提供上门取宠服务");
            return;
        }

    }
    var declarePrice = $("#declarePrice").val();
    if($("#selBjfw").is(":checked") ==true){
        //选择了保价服务 才进行校验
        if(declarePrice==undefined || declarePrice==""){
            showErrorTips("请填写声明价值");
            return;
        }
    }
    var param = {};
    var petstr = JSON.stringify(pets);
    param.pets=petstr;
    var id = $('#orderId').val();
    param.id=id;
    //param.userMobile=userMobile;
    param.startPlaceCode=startPlaceCode;
    param.destinationPlaceCode=destinationPlaceCode;
    param.placeDistance=placeDistance;
    param.transDate=selectDate;
    //param.petKind=petKind;
    //param.petWeight=petWeight;
    //param.selHkx=$("#selHkx").is(":checked");
    param.selSmjc=$("#selSmjc").is(":checked");
    param.selBjfw=$("#selBjfw").is(":checked");
    param.declarePrice=declarePrice;
    param.insuredPrice=declarePrice*2/100;
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
}