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