(function() {
	'use strict';
	
	var rama = angular.module('cuduRamas', []);
	
	rama.factory('Ramas', function(Traducciones) {
		return {
			get : function(a, fallback) {
      			var rama = [];
			    if (a.ramaColonia || a.colonia) { rama.push(Traducciones.texto('rama.colonia')); }
			    if (a.ramaManada || a.manada) { rama.push(Traducciones.texto('rama.manada')); }
			    if (a.ramaExploradores || a.exploradores) { rama.push(Traducciones.texto('rama.exploradores')); }
			    if (a.ramaExpedicion || a.expedicion) { rama.push(Traducciones.texto('rama.expedicion')); }
			    if (a.ramaRuta || a.ruta) { rama.push(Traducciones.texto('rama.ruta')); }
			      
			    if (rama.length === 0) {
      				var clave = fallback || 'rama.ninguna';
      				return Traducciones.texto(clave);
    			}
    			return rama.join(', ');
    		}
		};
	});
})();