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
$.ajax({
    type : "POST",
    contentType: "application/x-www-form-urlencoded;charset=UTF-8",
    url : "/pet/ticket/startPlace/getAll",
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
            /*// 注意这里访问不能用.访问，也不能加引号，否则就代表访问的是json里面名称为attr的值了
            */

        }
        $('#startPlaceName').on('click', function () {
            weui.picker(dataArray, {
                onChange: function (result) {
                    var start = result[1];
                    var startVal = start.split(";");
                    $("#startPlaceCode").val(startVal[0]);
                    $("#startPlaceName").val(startVal[1]);


                },
                onConfirm: function (result) {
                    var start = result[1];
                    var startVal = start.split(";");
                    $("#startPlaceCode").val(startVal[0]);
                    $("#startPlaceName").val(startVal[1]);
                }
            });
        });
    },
    error : function(){
        //alert("错误");
    }

});


function getDist(startPlaceCode) {

}