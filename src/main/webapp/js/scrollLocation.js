//新增位置至最底部
function scrollToButtom() {
    var divs = document.getElementsByClassName('z-listbox-body');
    for (var i = 0; i < divs.length; i++) {
        divs[i].scrollTop = divs[i].scrollHeight;
    }
}
//刪除後的位置
function scrollTo(index) {
    var divs = document.getElementsByClassName('z-listbox-body');
    console.log('divs='+divs.length);
    for (var i = 0; i < divs.length; i++) {
        var divChilds = divs[i].getElementsByClassName('z-listitem');
        console.log(index);
        if (divChilds.length>0 && divChilds.length>index && index>=0) {
            divChilds[index].scrollIntoView();
        }
    }
}
