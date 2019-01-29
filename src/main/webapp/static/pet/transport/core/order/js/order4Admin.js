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
    });

}
function adminSubmitOrder(){
    var param = orgParam();
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
                //location.href="/pet/ticket/orderAdmin/adminSupplyPerson?orderId="+retData.orderId;
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