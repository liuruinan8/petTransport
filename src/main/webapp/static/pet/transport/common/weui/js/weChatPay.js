//统一下单
unifiedorder: function(){
    var self = this;
    var userInfo = localStorage.getItem('ssqf_h5_wxUserInfo');

    CRAMIX.POST({
        baseUrl: URL.BASE + '/api/wechat/pay/h5',
        data: {
            'openid': JSON.parse(userInfo).openid,
            'body':'书身祈福支付',
            'orderSn': $('#orderSn').val() || 20 ,
            'amount': $('#money').val() || '0.01'
        },
        success: function(data){
            self.options.unifiedorderData = data;
        }
    })
},

//支付
pay: function(){
    var self = this;

    var appId = self.options.unifiedorderData.appId;
    var timeStamp = self.options.unifiedorderData.timeStamp;
    var nonceStr = self.options.unifiedorderData.nonceStr;
    var package1 = self.options.unifiedorderData.package;
    var paySign = self.options.unifiedorderData.paySign;

    WeixinJSBridge.invoke(
        'getBrandWCPayRequest', {
            "appId":appId,     //公众号名称，由商户传入
            "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数
            "nonceStr":nonceStr, //随机串
            "package":package1,
            "signType":"MD5",         //微信签名方式：
            "paySign":paySign //微信签名
        },
        function(res){
            WeixinJSBridge.log(res.err_msg);
            if(res.err_msg == "get_brand_wcpay_request:ok"){
                window.location.href = '/weixin/pay/pay-success.html';//去支付成功页面
            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
                $.toast("用户取消", "text");
            }else{
                $.toast("支付失败", "forbidden", function() {
                    window.location.reload();//刷新页面
                });
            }
        }
    );
},