(function() {
"use strict";

var colores = ["#E91E63", "#E91E63", "#795548", "#2E7D32", "#689F38"];

var calcular = function(textos, medir) {
  var password = $("#password").val();
  var calidad = medir(password);
  $("#calidad").text(textos[calidad]).css("color", colores[calidad]);
  $("#score").val(calidad);
};

var minimo = function () {
  var password = $("#password").val();
  if (password.length < 6) {
    $("#minimo").show();
    return false;
  }
  $("#minimo").hide();
  return true;
};

var coinciden = function() {
  var original = $("#password").val();
  var copia = $("#password2").val();
  if (original !== copia) {
    $("#coinciden").show();
    return false;
  }
  $("#coinciden").hide();
  return true;
};

var retrasar = (function() {
  var timer = 0;
  return function(callback, ms) {
    clearTimeout (timer);
    timer = setTimeout(callback, ms);
  };
})();

window.ResetCtrlFactory = function(textos, fncMedicion, dom) {
  $("#password").keyup(function() {
    retrasar(function() {
      calcular(textos, fncMedicion);
    }, 150);
  });
  $("#password").blur(minimo);
  $("#password2").blur(coinciden);
  $("#formulario").submit(function(e) {
    if (!minimo() || !coinciden()) {
      e.preventDefault();
    }
  });
  $("#password").focus();
};

}());
