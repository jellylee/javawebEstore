var text_height=0;
var swf_height = focus_height+text_height;

var pics='';
var links='';
var texts='';

pics=picPath+sp;
links=linkUrl+sp;				

var convertedString = links.split("&");
convertedString = convertedString.join("^");
links = convertedString;
document.write('<embed id="changePlayer" src='+swfPath+' wmode="opaque" flashvars="pics='+pics+'&amp;links='+links+'&amp;texts=&amp;pic_width='+focus_width+'&amp;pic_height='+focus_height+'&amp;show_text=0&amp;txtcolor=000000&amp;bgcolor=FFFFFF&amp;button_pos=4&amp;stop_time=4000" menu="false" bgcolor="#ffffff" quality="high" width="'+ focus_width +'" height="'+ focus_height +'" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />'); 