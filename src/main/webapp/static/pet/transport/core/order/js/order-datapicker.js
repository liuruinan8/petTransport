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
var startPlaceData ="";
$.ajax({
    type : "POST",
    url : "getStartPlace.shtml",
    data : "",
    success : function(data) {
        //alert(data);
        startPlaceData=data;
    },
    error : function(){
        //alert("错误");
    }

});
