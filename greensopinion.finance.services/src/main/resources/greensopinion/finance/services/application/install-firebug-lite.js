var script = document.createElement("script");
script.setAttribute("type", "text/javascript");
script.setAttribute("src", "https://getfirebug.com/firebug-lite.js#startOpened,enableTrace,overrideConsole=false");

var head = document.getElementsByTagName("head")[0];
head.appendChild(script);

console.log('firebug lite installed');
