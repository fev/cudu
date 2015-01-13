var fs = require('fs');
var Chance = require('chance');
var chance = new Chance();

var grupo = {
  'id': 'AK',
  'nombre': 'Ain-Karen',
  'asociacion': 'Moviment Escolta de València (MEV)',
  'comarca': 'Comarca Norte',
  'direccion': chance.address(),
  'codigoPostal': chance.postal(),
  'municipio': chance.city(),
  'telefono1': chance.phone(),
  'telefono2': chance.phone(),
  'email': chance.email(),
  'web': chance.domain(),
  'aniversario': chance.birthday({ string: true, american: false }),
  'entidadPatrocinadora': 'Parroquia'
};

var asociadosDePrueba = [];
for (var i = 0; i < 200; i++) {
  var nuevoAsociado = {
    'id': i + 1,
    'idGrupo': 'AK',
    'nombre': chance.first(),
    'apellidos': chance.last() + ' ' + chance.last(),
    'rama': chance.character({pool:'CMEPR'}),
    'tipo': chance.character({pool:'JKC'}),
    'sexo': chance.character({pool:'MF'}),
    'fechaNacimiento': chance.birthday({ string: true, american: false }),
    'direccion': chance.street(),
    'codigoPostal': chance.zip(),
    'municipio': chance.city(),
    'telefono': chance.phone(),
    'email': chance.email(),
    'actualizado': chance.date({ string: true, american: false }),
    'activo': chance.bool({likelihood: 80})
  };
  if (nuevoAsociado.activo)
    nuevoAsociado.usuarioActivo = chance.bool({likelihood: 30});
  if (i == 5)
    nuevoAsociado.activo = false;
  if (nuevoAsociado.tipo == 'C')
    nuevoAsociado.rama = '';
  if (chance.bool({likelihood: 40}))
    nuevoAsociado.dni = chance.integer({min: 00000000, max: 99999999}) + chance.character({ alpha: true, casing: 'upper' })
  asociadosDePrueba[i] = nuevoAsociado;
}

var handleWriteError = function(e) {
  if (e) {
    console.log(e);
  }
};

fs.writeFile('asociados.json', JSON.stringify(asociadosDePrueba, null, 2), handleWriteError);
fs.writeFile('grupo.json', JSON.stringify(grupo, null, 2), handleWriteError);

