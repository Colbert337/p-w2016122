$(function(){

	$('.station-special-more').on('click', function(){
		if($('.station-special').hasClass('more')) {
			return true;
		} else {
			$('.station-special').addClass('more');
		}
	});
	
});