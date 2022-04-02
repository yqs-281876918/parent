function submitSubject() {
    let form = jQuery('#subject').serializeObject();
    let msg = form.description;
    msg = msg.replace(/<(p|div)[^>]*>(<br\/?>|&nbsp;)<\/\1>/gi, '\n').replace(/<br\/?>/gi, '\n').replace(/<[^>/]+>/g, '').replace(/(\n)?<\/([^>]+)>/g, '').replace(/\u00a0/g, ' ').replace(/&nbsp;/g, ' ').replace(/<\/?(img)[^>]*>/gi, '').replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&#39;/g, "\'").replace(/&quot;/g, "\"").replace(/<\/?.+?>/g, "")
    if (msg.length > 15) {
        msg = msg.slice(0, 15)
        msg = msg + "..."
    } else {
        msg = msg.slice(0, msg.length)
    }
    form.introduction = msg
    jQuery.ajax({
        url: '/bank/addSubject/'+form.type,
        data: form,
        method: 'post',
        traditional: true,
        success: function (rst) {
            alert(rst)
        }
    })
}