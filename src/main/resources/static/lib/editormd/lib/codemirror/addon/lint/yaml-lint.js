// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

<<<<<<< HEAD
(function(mod) {
  if (typeof exports == "object" && typeof module == "object") // CommonJS
    mod(require("../../lib/codemirror"));
  else if (typeof define == "function" && define.amd) // AMD
    define(["../../lib/codemirror"], mod);
  else // Plain browser env
    mod(CodeMirror);
})(function(CodeMirror) {
"use strict";
=======
(function (mod) {
    if (typeof exports == "object" && typeof module == "object") // CommonJS
        mod(require("../../lib/codemirror"));
    else if (typeof define == "function" && define.amd) // AMD
        define(["../../lib/codemirror"], mod);
    else // Plain browser env
        mod(CodeMirror);
})(function (CodeMirror) {
    "use strict";
>>>>>>> d30a2ee (项目第一次提交)

// Depends on js-yaml.js from https://github.com/nodeca/js-yaml

// declare global: jsyaml

<<<<<<< HEAD
CodeMirror.registerHelper("lint", "yaml", function(text) {
  var found = [];
  try { jsyaml.load(text); }
  catch(e) {
      var loc = e.mark;
      found.push({ from: CodeMirror.Pos(loc.line, loc.column), to: CodeMirror.Pos(loc.line, loc.column), message: e.message });
  }
  return found;
});
=======
    CodeMirror.registerHelper("lint", "yaml", function (text) {
        var found = [];
        try {
            jsyaml.load(text);
        } catch (e) {
            var loc = e.mark;
            found.push({
                from: CodeMirror.Pos(loc.line, loc.column),
                to: CodeMirror.Pos(loc.line, loc.column),
                message: e.message
            });
        }
        return found;
    });
>>>>>>> d30a2ee (项目第一次提交)

});
