var fs = require('fs');
var Chance = require('chance');
var chance = new Chance();

var grupo = {
  '_class': 'org.scoutsfev.cudu.domain.Grupo',
  'id': 'AK',
  'nombre': 'Ain-Karen',
  'asociacion': '0',
  'comarca': 'Comarca Norte',
  'direccion': chance.address(),
  'codigoPostal': chance.integer({min: 0, max: 99999}),
  'municipio': chance.city(),
  'telefono1': chance.phone(),
  'telefono2': chance.phone(),
  'email': chance.email(),
  'web': chance.domain(),
  'aniversario': chance.birthday().getTime(),
  'entidadPatrocinadora': 'Parroquia'
};

var establecerRama = function(asociado) {
  if (asociado.rama == "C") { asociado.ramaColonia = true; }
  if (asociado.rama == "M") { asociado.ramaManada = true; }
  if (asociado.rama == "E") { asociado.ramaExploradores = true; }
  if (asociado.rama == "P") { asociado.ramaExpedicion = true; }
  if (asociado.rama == "R") { asociado.ramaRuta = true; }
};

var asociadosDePrueba = [];
for (var i = 0; i < 200; i++) {
  var nuevoAsociado = {
    '_class': 'org.scoutsfev.cudu.domain.Asociado',
    'id': i + 1,
    'grupo_id': 'AK',
    'nombre': chance.first(),
    'apellidos': chance.last() + ' ' + chance.last(),
    'rama': chance.character({pool:'CMEPR'}),
    'tipo': chance.character({pool:'JKC'}),
    'ambitoEdicion': 'G',
    'sexo': chance.character({pool:'MF'}),
    'fechaNacimiento': chance.birthday().getTime(),
    'direccion': chance.street(),
    'codigoPostal': chance.zip(),
    'municipio': chance.city(),
    'telefono': chance.phone(),
    'email': chance.email(),
    'actualizado': chance.date().getTime(),
    'activo': chance.bool({likelihood: 80}),
    'ramaColonia': false,
    'ramaManada': false,
    'ramaExploradores': false,
    'ramaExpedicion': false,
    'ramaRuta': false
  };
  if (nuevoAsociado.activo)
    nuevoAsociado.usuarioActivo = chance.bool({likelihood: 30});
  if (i == 5)
    nuevoAsociado.activo = false;
  if (nuevoAsociado.tipo == 'C')
    nuevoAsociado.rama = null;
  establecerRama(nuevoAsociado);
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
