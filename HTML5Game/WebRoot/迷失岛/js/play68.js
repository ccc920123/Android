function play68_submitScore(score) {
	updateShareScore(score);
	Play68.shareFriend();
	//setTimeout( function() { Play68.shareFriend(); }, 1000 )
}
function updateShare(score) {
	var descContent = "��ʧ";
	if(score > 0)
		shareTitle = '#��ʧ#�Ҵ����˵�' + score +'�أ�����ʧ�Ļĵ�Ϊ�����ս�ɣ�';
	else
		shareTitle = "#��ʧ#����ʧ�Ļĵ�Ϊ�����ս�ɣ�";
	appid = '';
	Play68.setShareInfo(shareTitle,descContent);
	// document.title = shareTitle;
}
