/* cached on: Tue, 12 Jun 2012 05:06:40 GMT */ 
(function(ad){function C(){aa||(aa=!0,J(Z,function(b){O(b)}))}function D(f,b){var a=ad.createElement("script");a.type="text/"+(f.type||"javascript"),a.src=f.src||f,a.async=!1,a.onreadystatechange=a.onload=function(){var c=a.readyState;!b.done&&(!c||/loaded|complete/.test(c))&&(b.done=!0,b())},(ad.body||ac).appendChild(a)}function E(d,c){if(d.state==P){return c&&c()}if(d.state==Q){return T.ready(d.name,c)}if(d.state==R){return d.onpreload.push(function(){E(d,c)})}d.state=Q,D(d.url,function(){d.state=P,c&&c(),J(X[d.name],function(b){O(b)}),H()&&aa&&J(X.ALL,function(b){O(b)})})}function F(d,c){d.state===undefined&&(d.state=R,d.onpreload=[],D({src:d.url,type:"cache"},function(){G(d)}))}function G(b){b.state=S,J(b.onpreload,function(c){c.call()})}function H(e){e=e||W;var d;for(var f in e){if(e.hasOwnProperty(f)&&e[f].state!=P){return !1}d=!0}return d}function I(b){return Object.prototype.toString.call(b)=="[object Function]"}function J(e,d){if(!!e){typeof e=="object"&&(e=[].slice.call(e));for(var f=0;f<e.length;f++){d.call(e,e[f],f)}}}function K(f){var e;if(typeof f=="object"){for(var h in f){f[h]&&(e={name:h,url:f[h]})}}else{e={name:M(f),url:f}}var g=W[e.name];if(g&&g.url===e.url){return g}W[e.name]=e;return e}function M(f){var e=f.split("/"),h=e[e.length-1],g=h.indexOf("?");return g!=-1?h.substring(0,g):h}function O(b){b._done||(b(),b._done=1)}var ac=ad.documentElement,ab,aa,Z=[],Y=[],X={},W={},V=ad.createElement("script").async===!0||"MozAppearance" in ad.documentElement.style||window.opera,U=window.head_conf&&head_conf.head||"head",T=window[U]=window[U]||function(){T.ready.apply(null,arguments)},S=1,R=2,Q=3,P=4;V?T.js=function(){var e=arguments,d=e[e.length-1],f={};I(d)||(d=null),J(e,function(b,a){b!=d&&(b=K(b),f[b.name]=b,E(b,d&&a==e.length-2?function(){H(f)&&O(d)}:null))});return T}:T.js=function(){var e=arguments,c=[].slice.call(e,1),f=c[0];if(!ab){Y.push(function(){T.js.apply(null,e)});return T}f?(J(c,function(b){I(b)||F(K(b))}),E(K(e[0]),I(f)?f:function(){T.js.apply(null,c)})):E(K(e[0]));return T},T.ready=function(a,g){if(a==ad){aa?O(g):Z.push(g);return T}I(a)&&(g=a,a="ALL");if(typeof a!="string"||!I(g)){return T}var e=W[a];if(e&&e.state==P||a=="ALL"&&H()&&aa){O(g);return T}var d=X[a];d?d.push(g):d=X[a]=[g];return T},T.ready(ad,function(){H()&&J(X.ALL,function(b){O(b)}),T.feature&&T.feature("domloaded",!0)});if(window.addEventListener){ad.addEventListener("DOMContentLoaded",C,!1),window.addEventListener("load",C,!1)}else{if(window.attachEvent){ad.attachEvent("onreadystatechange",function(){ad.readyState==="complete"&&C()});var N=1;try{N=window.frameElement}catch(L){}!N&&ac.doScroll&&function(){try{ac.doScroll("left"),C()}catch(b){setTimeout(arguments.callee,1);return}}(),window.attachEvent("onload",C)}}!ad.readyState&&ad.addEventListener&&(ad.readyState="loading",ad.addEventListener("DOMContentLoaded",handler=function(){ad.removeEventListener("DOMContentLoaded",handler,!1),ad.readyState="complete"},!1)),setTimeout(function(){ab=!0,J(Y,function(b){b()})},300)})(document);var _SG={},SpilGames=SpilGames||{},_spque=[],_sgdef={Cookies:["set"],Cache:["set"],Events:["publish","subscribe","subscribeOnce","unsubscribe"],Debug:["log","warn","error","subscribe","unsubscribe","publish","report"],Users:["get","getCurrentUser","getFriends","getAvatar"],Auth:["authenticate","login","logout"],Net:["post","get"],Highscores:["insert","getFriendScores","getMyScores","getGameScores"],Gameplay:["insert","getMyPlays","getFriendPlays"],Activities:["post","getActivityFeed"],Payments:["pay"],Portal:["getInformation","adjustHeight","showInviteFriends","showScoreboard","gotoPage","getLocale"]},_sgpush=function(a,b){return function(){_spque.push([a,b,arguments])}};for(var x in _sgdef){if(_sgdef.hasOwnProperty(x)){SpilGames[x]={};for(var y in _sgdef[x]){if(_sgdef[x].hasOwnProperty(y)){_qobj="_SG."+x;_qcall=_qobj+"."+_sgdef[x][y];SpilGames[x][_sgdef[x][y]]=_sgpush(_qcall,_qobj)}}}}SpilGames.Settings=new function(){_SG.Settings=this;var c={};var b={};var a=function(d){return d.toLowerCase()};this.postInit=function(){a=_SG.Deprecation.checkRenamed;var d=function(f){for(old in f){if(f.hasOwnProperty(old)){var e=a(old);if(e!=old){f[e]=f[old];delete f[old]}}}};d(c);d(b)};this.set=function(d,e,g){var f=g?c:b;if(typeof d=="object"){for(x in d){if(d.hasOwnProperty(x)){f[a(x)]=d[x]}}}else{f[a(d)]=e}};this.setDefault=function(d,e){this.set(d,e,true)};this.get=function(d){d=a(d);return b[d]||c[d]||null}};if(typeof spilgames_api.apibase=="undefined"){throw"Please specify the base path of the API."}if(typeof spilgames_api.channel_id!="undefined"){SpilGames.Settings.set("portal.channel_id",spilgames_api.channel_id)}if(typeof spilgames_api.site_id!="undefined"){SpilGames.Settings.set("portal.site_id",spilgames_api.site_id)}SpilGames.Head=new function(){_SG.Head=this;var a=spilgames_api.apibase;this.api_loaded=false;this.load=head.js;this.ready=head.ready;this.loadApi=function(){this.include('api',function(){_SG.Head.api_loaded=true})};this.include=function(){var e=[];for(i=0;i<arguments.length;i++){var c=arguments[i];if(typeof c=="string"){if(c=="plugin.integration"){var b=_SG.Settings.get("currentPortalInfo")||{},g=b.lang||"en_US",d=b.geoip||{},f=d.country_code||null;e.push("http://static1.spilcdn.com/vda/pb/1/16/88?locale="+g+"&country="+f)}else{if(/^http:\/\//.test(c)){e.push(c)}else{e.push(a+"spilgames."+c+".js")}}}else{e.push(c)}}this.load.apply(this,e)}};(function(b){var a=null,c=function(d){if(a==(d.domain||d.origin)){(function(e){var g=(function(){try{return JSON.parse(e.data)}catch(q){return{call:[null,null],id:null}}}()),p=g.call[0]||"",j=g.id||"",n=p.split("."),f=g.call[1]||{},h=[],m=/^CALLBACK:/,o=function(r){var q={id:j,call:{id:r||0,data:null}};return function(s){q.call.data=s;if(e&&e.source&&e.source.postMessage){e.source.postMessage(JSON.stringify(q),(e.domain||e.origin))}}};for(var l in f){if(typeof f[l]=="object"){for(var k in f[l]){if(m.test(f[l][k])){f[l][k]=o(f[l][k])}}}if(m.test(f[l])){f[l]=o(f[l])}h.push(f[l])}switch(n.length){case 1:if(n[0]=="SpilGames"){SpilGames.apply(this,h)}break;case 2:SpilGames[n[1]].apply(this,h);break;case 3:SpilGames[n[1]][n[2]].apply(SpilGames,h);break}}(d))}if(a===null&&d.data=="SpilGames.registerDomain"){a=d.domain||d.origin}};if(b.attachEvent){b.attachEvent("onmessage",c,false)}else{if(b.addEventListener){b.addEventListener("message",c,false)}}}(window));var _zpQueue=[];window.ZapapaJSApi=function(d,b,a,c){_zpQueue.push({a:d,d:b,c:a,o:c});if(_zpQueue.length==1){var e=function(){_SG.Head.include("plugin.zapapa",function(){while(_zpQueue.length){var f=_zpQueue.shift();ZapapaJSApi(f.a,f.d,f.c,f.o)}})};if(_SG.Head.api_loaded){e()}else{SpilGames.Events.subscribeOnce("api.initialized",e)}}};