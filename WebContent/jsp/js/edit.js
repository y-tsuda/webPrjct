$(function() {

	var del_flg = $("#del_flg").val();
	if (del_flg == "1") {
		$('#del_flg').prop('checked', true);
	}

	$("#del_flg").change(function() {
		if ($("#del_flg").prop('checked')) {
			$("#del_flg").val("1");
		} else {
			$("#del_flg").val("0");
		}
	})
})