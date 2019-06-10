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


function subMitOrder(ele){

    var param = orgParam(ele,true);

    $.ajax({
        type : "POST",
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        url : "/pet/ticket/order/sumbitOrder",
        data : param,
        success : function(data) {
            var retData = JSON.parse(data);
            if(retData.status =="success"){
               $('#orderId').val(retData.orderId);
                //跳转到另外一个页面
                location.href="/pet/ticket/order/supplyPerson?orderId="+retData.orderId;
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
function orgParam(ele,haveMain){
    if(haveMain == undefined){
        haveMain = true;
    }
    var param = {};
    var startPlaceCode = $("#startPlaceCode").val();
    if(startPlaceCode==undefined || startPlaceCode==""){
        if(ele){
            ele.removeClass("weui-btn_disabled");
        }
        showErrorTips("请选择出发地");
        return;
    }
    var startPlaceName = $("#startPlaceName").val();

    var destinationPlaceCode = $("#destinationPlaceCode").val();
    if(destinationPlaceCode==undefined || destinationPlaceCode==""){
        if(ele){
            ele.removeClass("weui-btn_disabled");
        }
        showErrorTips("请选择目的地");
        return;
    }
    var destinationPlaceName = $("#destinationPlaceName").val();

    var selectDate = $("#selectDate").val();
    if(selectDate==undefined || selectDate==""){
        if(ele){
            ele.removeClass("weui-btn_disabled");
        }
        showErrorTips("请选择航班时间");
        return;
    }
    var pets=[];
    var pet={};
    if(haveMain){
        var petName = $("#petName").val();
        pet.petName=petName;
        var petKind = $("#petKind").val();
        pet.petKind=petKind;
        var petWeight = $("#petWeight").val();
        if(petWeight==undefined || petWeight==""){
            if(ele){
                ele.removeClass("weui-btn_disabled");
            }
            showErrorTips("请填写宠物重量");
            return;
        }
        pet.petWeight=petWeight;

        var petAge = $("#petAge").val();
        pet.petAge=petAge;
        var petCharacter = $("#petCharacter").val();
        pet.petCharacter=petCharacter;
        var petSex = $("input[name='petSex']:checked").val();
        pet.petSex=petSex;

        var selDWJYHGInput = $("#selDWJYHGInput").is(':checked')+"";
        pet.selDWJYHG=selDWJYHGInput;
        var selHkxInput = $("#selHkx").is(':checked')+"";
        pet.selHkx=selHkxInput;
        pets.push(pet);
    }

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
        var selHkxInput =$("#selHkxInput",this).val() +"";
        pet.selHkx=selHkxInput;
        pets.push(pet);
    });
    if(pets.length==0){
        if(ele){
            ele.removeClass("weui-btn_disabled");
        }
        showErrorTips("请添加宠物信息");
        return;
    }
    var receiptPlace = $("#receiptPlace").val();

    var placeDistance = $("#placeDistance").val();
    if($("#selSmjc").is(":checked") ==true){
        //选择了上门取宠 才进行校验
        var  receiptPlace = $("#receiptPlace").val();
        if(receiptPlace==undefined || receiptPlace==""){
            if(ele){
                ele.removeClass("weui-btn_disabled");
            }
            showErrorTips("请选择接宠地点");
            return;
        }
        if(placeDistance==undefined || placeDistance==""){
            if(ele){
                ele.removeClass("weui-btn_disabled");
            }
            showErrorTips("请选择接宠地点");
            return;
        }
        if(placeDistance>65){
            if(ele){
                ele.removeClass("weui-btn_disabled");
            }
            showErrorTips("由于接送地点距离机场超过65公里，本司无法提供上门取宠服务");
            return;
        }
    }
    var declarePrice = $("#declarePrice").val();
    if($("#selBjfw").is(":checked") ==true){
        //选择了保价服务 才进行校验
        if(declarePrice==undefined || declarePrice==""){
            if(ele){
                ele.removeClass("weui-btn_disabled");
            }
            showErrorTips("请填写声明价值");
            return;
        }
    }
    var id = $('#orderId').val();
    var otherPrice = $("#otherPrice").val();
    if(!otherPrice){
        otherPrice="0";
    }
    var petstr = JSON.stringify(pets);
    param.id=id;
    param.pets=petstr;
    param.startPlaceCode=startPlaceCode;
    param.startPlaceName=startPlaceName;
    param.destinationPlaceCode=destinationPlaceCode;
    param.destinationPlaceName=destinationPlaceName;
    param.placeDetail=receiptPlace;
    param.placeDistance=placeDistance;
    param.transDate=selectDate;
    param.selSmjc=$("#selSmjc").is(":checked");
    param.selBjfw=$("#selBjfw").is(":checked");
    param.declarePrice=declarePrice;
    param.insuredPrice=declarePrice*5/100;
    param.otherPrice=otherPrice;
    return param;
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


/**
 * 监听增加宠物点击方法
 */
// language=JQuery-CSS
$('#addPet').on('click', function () {
    $('#petIdTemplate').val('')
    $('#showPetKindPickerTemplate').val('');
    var petKindTemplate = $('#petKindTemplate').val('');
    var petNameTemplate = $('#petNameTemplate').val('');
    var petHeightTemplate = $('#petHeightTemplate').val('');
    var petWeightTemplate = $('#petWeightTemplate').val('');
    $('#petAgeTemplate').val('');
    $('#petCharacterTemplate').val('');
    //$('#petWeightTemplate').val('');
    //弹出窗口
    $('#petInfo').fadeIn();
});


function editPetInfo(ele){
    var petKind =  $('#petKind',$(ele).parent().parent()).val();
    if(petKind!=""){
        var petKindName = petKind.split("(")[0];
        $('#showPetKindPickerTemplate').val(petKindName);
    }else{
        $('#showPetKindPickerTemplate').val("");
    }

    //var petKindId = petKind.split("(")[1].split(")")[0];
    $('#petKindTemplate').val(petKind);
    $('#petNameTemplate').val($('#petName',$(ele).parent().parent()).val());
    $('#petHeightTemplate').val($('#petHeight',$(ele).parent().parent()).val());
    $('#petWeightTemplate').val($('#petWeight',$(ele).parent().parent()).val());
    $('#petIdTemplate').val($('#petId',$(ele).parent().parent()).val());
    $('#petAgeTemplate').val($('#petAge',$(ele).parent().parent()).val());
    $('#petCharacterTemplate').val($('#petCharacter',$(ele).parent().parent()).val());
    var petSex = $('#petSex',$(ele).parent().parent()).val();
    $("input[name='petSexTemplate'][value='"+petSex+"']").attr('checked','true');
    $(ele).parent().parent().attr("isthischange","true");
    //弹出窗口
    $('#petInfo').fadeIn();
}

function removePetDiv(ele){
    $(ele).parent().parent().remove()
}
$('#petAddConfirm').on('click', function () {
    var petKindTemplate = $('#petKindTemplate').val();
    var petKindTemplateShow ="";
    if(petKindTemplate!=""){
        petKindTemplateShow = petKindTemplate.split("(")[0];
    }
    var petNameTemplate = $('#petNameTemplate').val();
    var petHeightTemplate = $('#petHeightTemplate').val();
    var petWeightTemplate = $('#petWeightTemplate').val();
    if(petWeightTemplate==undefined || petWeightTemplate==""){
        showErrorTips("请填写宠物重量");
        return;
    }

    var petAgeTemplate = $("#petAgeTemplate").val();
    var petCharacterTemplate = $("#petCharacterTemplate").val();
    var petSexTemplate = $("input[name='petSexTemplate']:checked").val();

    var selDWJYHG=$("#selDWJYHG").is(":checked")+"";
    var selHkx =$("#selHkxTemplate").is(":checked")+"";
    var petId = $('#petIdTemplate').val();
    var  singleBoxPrice ='0';
    if(selHkx!=undefined && (selHkx=="on" ||selHkx=="true"  ||selHkx==true)){
        //showErrorTips("请选择"); 使用提示的方式进行提示没有选择航空箱
        if(petWeightTemplate>25){
            singleBoxPrice ='280'
        }else  if(petWeightTemplate>20){
            singleBoxPrice ='260'
        }else  if(petWeightTemplate>15){
            singleBoxPrice ='180'
        }else  if(petWeightTemplate>10){
            singleBoxPrice ='150'
        }else if(petWeightTemplate>5){
            singleBoxPrice ='90'
        }else{
            singleBoxPrice ='60'
        }
    }
    if($(".pet-container[isThisChange=true]").length>0){
        $('#petKind',$(".pet-container[isThisChange=true]")).val(petKindTemplate);
        $('#petKindSpan',$(".pet-container[isThisChange=true]")).text(petKindTemplateShow);

        $('#petName',$(".pet-container[isThisChange=true]")).val(petNameTemplate);
        $('#petNameSpan',$(".pet-container[isThisChange=true]")).text(petNameTemplate);

        $('#petHeight',$(".pet-container[isThisChange=true]")).val(petHeightTemplate);

        $('#petWeight',$(".pet-container[isThisChange=true]")).val(petWeightTemplate);
        $('#petWeightSpan',$(".pet-container[isThisChange=true]")).text(petWeightTemplate);

        $('#petAge',$(".pet-container[isThisChange=true]")).val(petAgeTemplate);
        $('#petAgeSpan',$(".pet-container[isThisChange=true]")).text(petAgeTemplate);

        $('#petCharacter',$(".pet-container[isThisChange=true]")).val(petCharacterTemplate);

        $('#petSex',$(".pet-container[isThisChange=true]")).val(petSexTemplate);
        $('#petSexSpan',$(".pet-container[isThisChange=true]")).text(petSexTemplate);

        $('#selHkxInput',$(".pet-container[isThisChange=true]")).val(selHkx);
        $('#selDWJYHG',$(".pet-container[isThisChange=true]")).val(selDWJYHG);

        $('#singleBoxPriceSpan',$(".pet-container[isThisChange=true]")).text(singleBoxPrice);

        //$('#petSpan',$(".pet-container[isThisChange=true]")).html(" 重量："+petWeightTemplate+" 航空箱价格："+singleBoxPrice+"<a href=\"javascript:;\" onclick=\"editPetInfo(this)\" class=\"weui-btn weui-btn_mini weui-btn_warn\">编辑</a>" )

        $(".pet-container[isThisChange=true]").attr("isThisChange","false");
    }else{
        var tpl = document.getElementById('petTpl').innerHTML;
        var html =template(tpl, {
            pet: {
                petId:petId,
                petKind: petKindTemplate,
                petKindShow: petKindTemplateShow,
                petName: petNameTemplate
                ,petHeight: petHeightTemplate
                ,petWeight: petWeightTemplate
                ,petAge:petAgeTemplate
                ,petCharacter:petCharacterTemplate
                ,petSex:petSexTemplate
                ,selHkx: selHkx
                ,selDWJYHG: selDWJYHG
                ,singleBoxPrice:singleBoxPrice
            }});
        //console.log(html);
        $('#petAddDiv').append(html);
    }

    /*var userMobile = $("#userMobile").val();
    if(userMobile==undefined || userMobile==""){
        showErrorTips("请填写联系人手机号码");
        return;
    }*/

    $('#petInfo').fadeOut(200);
});
$('#petAddCancel').on('click', function () {
    $(".pet-container[isThisChange=true]").attr("isThisChange","false");
    $('#petInfo').fadeOut(200);
});

$('#markMap').on('click', function () {
    $('#mapInfo').fadeIn();
});
function mapFadeOut(){
    $('#mapInfo').fadeOut(200);
}

