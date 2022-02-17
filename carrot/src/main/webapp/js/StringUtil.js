function getLastToken(str, delim) {
	const words = str.split(delim);
	return words[words.length-1];
}

function getTimeSince(date) {
	const seconds = Math.floor((new Date() - new Date(date)) / 1000);
	
	let interval = seconds / (60*60*24*365);
	if(interval>=2) return Math.floor(interval) + "년 전";
	if(interval>=1) return "작년";
	
	interval = seconds / (60*60*24*30);
	if(interval>=3) return Math.floor(interval) + "달 전";
	if(interval>=2) return "두 달 전";
	if(interval>=1) return "한 달 전";
	
	interval = seconds / (60*60*24);
	if(interval>=16) return Math.floor(interval) + "일 전";
	if(interval>=15) return "보름 전";
	if(interval>=8) return Math.floor(interval) + "일 전";
	if(interval>=7) return "일주일 전";
	if(interval>=5) return Math.floor(interval) + "일 전";
	if(interval>=4) return "나흘 전";
	if(interval>=3) return "사흘 전";
	if(interval>=2) return "이틀 전";
	if(interval>=1) return "어제";
	
	interval = seconds / (60*60);
	if(interval>=1) return Math.floor(interval) + "시간 전";
	
	interval = seconds / 60;
	if(interval>=1) return Math.floor(interval) + "분 전";		
	if(seconds<0) return "몇 초 후";
	if(seconds<5) return "몇 초 전";
	return seconds + "초 전";
}