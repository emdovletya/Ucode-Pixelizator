parcelRequire=function(e,r,t,n){var i,o="function"==typeof parcelRequire&&parcelRequire,u="function"==typeof require&&require;function f(t,n){if(!r[t]){if(!e[t]){var i="function"==typeof parcelRequire&&parcelRequire;if(!n&&i)return i(t,!0);if(o)return o(t,!0);if(u&&"string"==typeof t)return u(t);var c=new Error("Cannot find module '"+t+"'");throw c.code="MODULE_NOT_FOUND",c}p.resolve=function(r){return e[t][1][r]||r},p.cache={};var l=r[t]=new f.Module(t);e[t][0].call(l.exports,p,l,l.exports,this)}return r[t].exports;function p(e){return f(p.resolve(e))}}f.isParcelRequire=!0,f.Module=function(e){this.id=e,this.bundle=f,this.exports={}},f.modules=e,f.cache=r,f.parent=o,f.register=function(r,t){e[r]=[function(e,r){r.exports=t},{}]};for(var c=0;c<t.length;c++)try{f(t[c])}catch(e){i||(i=e)}if(t.length){var l=f(t[t.length-1]);"object"==typeof exports&&"undefined"!=typeof module?module.exports=l:"function"==typeof define&&define.amd?define(function(){return l}):n&&(this[n]=l)}if(parcelRequire=f,i)throw i;return f}({"uTSW":[function(require,module,exports) {

},{}],"jExt":[function(require,module,exports) {

},{"./config/colors.css":"uTSW","./vendors/normalize.css":"uTSW","./generic/generic.css":"uTSW","./generic/button.css":"uTSW","./generic/form.css":"uTSW","./elements/page.css":"uTSW","./elements/range.css":"uTSW","./object/container.css":"uTSW","./components/header.css":"uTSW","./components/content.css":"uTSW","./components/upload.css":"uTSW","./components/image.css":"uTSW","./components/footer.css":"uTSW","./components/main.css":"uTSW","./components/nav.css":"uTSW","./components/info.css":"uTSW"}],"p7kT":[function(require,module,exports) {
var e=document.getElementById("drag-drop-upload"),t=document.getElementById("drag-drop-upload-wrapper"),n=document.getElementById("upload-input"),a=document.getElementById("js-image-wrapper"),d=document.getElementById("image"),i=document.getElementById("js-image"),o=document.getElementById("js-loading"),s=document.getElementById("js-main"),c=document.getElementById("js-range-input"),r=document.getElementById("js-range-value"),l=document.getElementById("js-image-link-jpeg"),m=document.getElementById("js-image-link-png"),u=document.getElementById("js-image-link-gif"),g=document.getElementById("js-image-link-bmp"),p=null;window.addEventListener("DOMContentLoaded",function(){e.addEventListener("drag",function(e){e.target.classList.add("ondrag")}),e.addEventListener("dragleave",function(e){e.target.classList.remove("ondrag")}),e.addEventListener("drop",function(e){e.target.classList.remove("ondrag")}),n.addEventListener("change",function(e){var n=e.target,a=new FileReader;a.addEventListener("load",function(){var e=new FormData;n.files[0]&&e.append("file",n.files[0]),fetch("http://localhost:8080/pxlztr_war/upload",{method:"POST",body:e,enctype:"multipart/form-data"}).then(function(e){return t.classList.add("hidden"),s.classList.remove("hidden"),e.json()}).then(function(e){p=e.id,fetch("http://localhost:8080/pxlztr_war/pixelate?id=".concat(e.id,"&range=1"),{method:"GET",enctype:"application/json"}).then(function(e){return e.json()}).then(function(e){o.classList.remove("hidden"),i.classList.add("hidden"),setTimeout(function(){o.classList.add("hidden"),i.classList.remove("hidden"),console.log(e),d.src=e.path},1e3)}).catch(function(e){console.log(e)})})}),a.readAsDataURL(n.files[0])}),e.addEventListener("submit",function(e){e.preventDefault()}),c.addEventListener("input",function(e){var t=e.target;r.innerText="".concat(t.value," %")}),c.addEventListener("change",function(e){var t=e.target;try{fetch("http://localhost:8080/pxlztr_war/pixelate?id=".concat(p,"&range=").concat(t.value),{method:"GET",enctype:"application/json"}).then(function(e){return e.json()}).then(function(e){o.classList.remove("hidden"),i.classList.add("hidden"),d.src="https://www.google.com/url?sa=i&url=https%3A%2F%2Fgiphy.com%2Fgifs%2Fcrearecreativita-loading-load-creare-creativita-L05HgB2h6qICDs5Sms&psig=AOvVaw1o7AvpPUwPtaGLECH2UQIs&ust=1619195382982000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCLDSnP-ikvACFQAAAAAdAAAAABAf",setTimeout(function(){o.classList.add("hidden"),i.classList.remove("hidden"),d.src=e.path},1e3)})}catch(n){console.err(n)}})});
},{}],"Focm":[function(require,module,exports) {
"use strict";require("./styles/main.css"),require("./scripts/script.js");
},{"./styles/main.css":"jExt","./scripts/script.js":"p7kT"}]},{},["Focm"], null)