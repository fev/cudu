/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function prepareTargetBlank(){
   var className = 'external';
   var as = document.getElementsByTagName('a');
   for(i=0;i<as.length;i++){
      var a = as[i];
      r=new RegExp("(^| )"+className+"($| )");
      if(r.test(a.className)){
         a.onclick = function(){
            window.open(this.href);
            return false;
         }
      }
    }
}
window.onload = prepareTargetBlank;

