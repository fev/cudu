if (typeof curso == "undefined" || !curso) {
    var curso = {
		dom: {},
		i8n: {},
		ui: { codeGenerator: {} }
	};
}



function(){
     model.addAttribute("monograficos",monograficosPorBloque);
       
                
                var monograficosPorBloque;
                for(var i = 0 ; i < ((ArrayList<List<Monografico>>)monograficosPorBloque).size(); i++)
                {
                    List<Monografico> monograficsDeBloque = monograficosPorBloque.get(i);
                    System.out.println("----Bloque:  "+monograficsDeBloque.get(0).getBloque());
                    for(int j = 0 ; j < monograficsDeBloque.size(); j++)
                    {
                        Monografico monografic = monograficsDeBloque.get(j);
                        System.out.println("            monografic::  "+monografic.getNombre());
                    }
                }
}

