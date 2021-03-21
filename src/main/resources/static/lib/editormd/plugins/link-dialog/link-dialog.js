/*!
 * Link dialog plugin for Editor.md
 *
 * @file        link-dialog.js
 * @author      pandao
 * @version     1.2.1
 * @updateTime  2015-06-09
 * {@link       https://github.com/pandao/editor.md}
 * @license     MIT
 */

<<<<<<< HEAD
(function() {

    var factory = function (exports) {

		var pluginName   = "link-dialog";

		exports.fn.linkDialog = function() {

			var _this       = this;
			var cm          = this.cm;
            var editor      = this.editor;
            var settings    = this.settings;
            var selection   = cm.getSelection();
            var lang        = this.lang;
            var linkLang    = lang.dialog.link;
            var classPrefix = this.classPrefix;
			var dialogName  = classPrefix + pluginName, dialog;

			cm.focus();

            if (editor.find("." + dialogName).length > 0)
            {
=======
(function () {

    var factory = function (exports) {

        var pluginName = "link-dialog";

        exports.fn.linkDialog = function () {

            var _this = this;
            var cm = this.cm;
            var editor = this.editor;
            var settings = this.settings;
            var selection = cm.getSelection();
            var lang = this.lang;
            var linkLang = lang.dialog.link;
            var classPrefix = this.classPrefix;
            var dialogName = classPrefix + pluginName, dialog;

            cm.focus();

            if (editor.find("." + dialogName).length > 0) {
>>>>>>> d30a2ee (项目第一次提交)
                dialog = editor.find("." + dialogName);
                dialog.find("[data-url]").val("http://");
                dialog.find("[data-title]").val(selection);

                this.dialogShowMask(dialog);
                this.dialogLockScreen();
                dialog.show();
<<<<<<< HEAD
            }
            else
            {
                var dialogHTML = "<div class=\"" + classPrefix + "form\">" + 
                                        "<label>" + linkLang.url + "</label>" + 
                                        "<input type=\"text\" value=\"http://\" data-url />" +
                                        "<br/>" + 
                                        "<label>" + linkLang.urlTitle + "</label>" + 
                                        "<input type=\"text\" value=\"" + selection + "\" data-title />" + 
                                        "<br/>" +
                                    "</div>";

                dialog = this.createDialog({
                    title      : linkLang.title,
                    width      : 380,
                    height     : 211,
                    content    : dialogHTML,
                    mask       : settings.dialogShowMask,
                    drag       : settings.dialogDraggable,
                    lockScreen : settings.dialogLockScreen,
                    maskStyle  : {
                        opacity         : settings.dialogMaskOpacity,
                        backgroundColor : settings.dialogMaskBgColor
                    },
                    buttons    : {
                        enter  : [lang.buttons.enter, function() {
                            var url   = this.find("[data-url]").val();
                            var title = this.find("[data-title]").val();

                            if (url === "http://" || url === "")
                            {
=======
            } else {
                var dialogHTML = "<div class=\"" + classPrefix + "form\">" +
                    "<label>" + linkLang.url + "</label>" +
                    "<input type=\"text\" value=\"http://\" data-url />" +
                    "<br/>" +
                    "<label>" + linkLang.urlTitle + "</label>" +
                    "<input type=\"text\" value=\"" + selection + "\" data-title />" +
                    "<br/>" +
                    "</div>";

                dialog = this.createDialog({
                    title: linkLang.title,
                    width: 380,
                    height: 211,
                    content: dialogHTML,
                    mask: settings.dialogShowMask,
                    drag: settings.dialogDraggable,
                    lockScreen: settings.dialogLockScreen,
                    maskStyle: {
                        opacity: settings.dialogMaskOpacity,
                        backgroundColor: settings.dialogMaskBgColor
                    },
                    buttons: {
                        enter: [lang.buttons.enter, function () {
                            var url = this.find("[data-url]").val();
                            var title = this.find("[data-title]").val();

                            if (url === "http://" || url === "") {
>>>>>>> d30a2ee (项目第一次提交)
                                alert(linkLang.urlEmpty);
                                return false;
                            }

                            /*if (title === "")
                            {
                                alert(linkLang.titleEmpty);
                                return false;
                            }*/
<<<<<<< HEAD
                            
                            var str = "[" + title + "](" + url + " \"" + title + "\")";
                            
                            if (title == "")
                            {
                                str = "[" + url + "](" + url + ")";
                            }                                
=======

                            var str = "[" + title + "](" + url + " \"" + title + "\")";

                            if (title == "") {
                                str = "[" + url + "](" + url + ")";
                            }
>>>>>>> d30a2ee (项目第一次提交)

                            cm.replaceSelection(str);

                            this.hide().lockScreen(false).hideMask();

                            return false;
                        }],

<<<<<<< HEAD
                        cancel : [lang.buttons.cancel, function() {                                   
=======
                        cancel: [lang.buttons.cancel, function () {
>>>>>>> d30a2ee (项目第一次提交)
                            this.hide().lockScreen(false).hideMask();

                            return false;
                        }]
                    }
                });
<<<<<<< HEAD
			}
		};

	};
    
	// CommonJS/Node.js
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object")
    { 
        module.exports = factory;
    }
	else if (typeof define === "function")  // AMD/CMD/Sea.js
    {
		if (define.amd) { // for Require.js

			define(["editormd"], function(editormd) {
                factory(editormd);
            });

		} else { // for Sea.js
			define(function(require) {
                var editormd = require("./../../editormd");
                factory(editormd);
            });
		}
	} 
	else
	{
        factory(window.editormd);
	}
=======
            }
        };

    };

    // CommonJS/Node.js
    if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
        module.exports = factory;
    } else if (typeof define === "function")  // AMD/CMD/Sea.js
    {
        if (define.amd) { // for Require.js

            define(["editormd"], function (editormd) {
                factory(editormd);
            });

        } else { // for Sea.js
            define(function (require) {
                var editormd = require("./../../editormd");
                factory(editormd);
            });
        }
    } else {
        factory(window.editormd);
    }
>>>>>>> d30a2ee (项目第一次提交)

})();
