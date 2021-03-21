// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

<<<<<<< HEAD
(function() {
  CodeMirror.defineMode("markdown_with_stex", function(){
    var inner = CodeMirror.getMode({}, "stex");
    var outer = CodeMirror.getMode({}, "markdown");

    var innerOptions = {
      open: '$',
      close: '$',
      mode: inner,
      delimStyle: 'delim',
      innerStyle: 'inner'
    };

    return CodeMirror.multiplexingMode(outer, innerOptions);
  });

  var mode = CodeMirror.getMode({}, "markdown_with_stex");

  function MT(name) {
    test.mode(
      name,
      mode,
      Array.prototype.slice.call(arguments, 1),
      'multiplexing');
  }

  MT(
    "stexInsideMarkdown",
    "[strong **Equation:**] [delim $][inner&tag \\pi][delim $]");
=======
(function () {
    CodeMirror.defineMode("markdown_with_stex", function () {
        var inner = CodeMirror.getMode({}, "stex");
        var outer = CodeMirror.getMode({}, "markdown");

        var innerOptions = {
            open: '$',
            close: '$',
            mode: inner,
            delimStyle: 'delim',
            innerStyle: 'inner'
        };

        return CodeMirror.multiplexingMode(outer, innerOptions);
    });

    var mode = CodeMirror.getMode({}, "markdown_with_stex");

    function MT(name) {
        test.mode(
            name,
            mode,
            Array.prototype.slice.call(arguments, 1),
            'multiplexing');
    }

    MT(
        "stexInsideMarkdown",
        "[strong **Equation:**] [delim $][inner&tag \\pi][delim $]");
>>>>>>> d30a2ee (项目第一次提交)
})();
