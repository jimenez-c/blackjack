$(function(){
	$('.selectpicker').selectpicker();


	$(".table tr").click(function(){
		if($(this).attr("data-game-id")) {
			location.href="index.php?p=game&id=" + $(this).attr("data-game-id");
		}
	});
})