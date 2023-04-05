
function manPeca(acao){
    let input = document.getElementById("manPecaId");

    if(isNaN(parseInt(input.value))){
        input.value = 0;
    }else if(acao === '+'){
        input.value = parseInt(input.value) + 1;
    }else if(acao === '-' && parseInt(input.value) > 1){
        input.value = parseInt(input.value) - 1;
    }
}

function openDrop(id){
    drop = id;
    //Os childNodes server para pegar as tags dentro de uma
    if(drop.childNodes[7].className === "dropdown-lol-oculto"){
        //muda a classe para o dropDown abrir
        drop.childNodes[7].className = "dropdown-lol";
        //muda o icone da flecha
        drop.childNodes[3].childNodes[1].childNodes[1].className = "fa-sharp fa-solid fa-chevron-down";
    }else{
        drop.childNodes[7].className = "dropdown-lol-oculto";
        drop.childNodes[3].childNodes[1].childNodes[1].className = "fa-sharp fa-solid fa-chevron-up";
    }
}

function addRemPecaRoupa(acao){
    let lista = document.getElementById("listaPeca");

    if(acao === 'add'){
        let clone = lista.childNodes[1].cloneNode(true);
        let num = lista.childElementCount + 1;
        clone.id = "peca" + num;

        clone.childNodes[1].childNodes[1].childNodes[1].textContent = 
            lista.childNodes[1].childNodes[1].childNodes[1].childNodes[1].textContent.replace(1,num);

        let idpeca = clone.childNodes[1].childNodes[3].childNodes[1];
        let qtdepeca = clone.childNodes[3].childNodes[3].childNodes[3];
        
        idpeca.setAttribute("id", "id"+num);
        qtdepeca.setAttribute("id", "qtde"+num);

        idpeca.setAttribute("onchange", "atualizaPreco('id"+ num + "', 'qtde"+num + "')");
        qtdepeca.setAttribute("onchange", "atualizaPreco('id"+ num + "', 'qtde"+num + "')");

        qtdepeca.value = "1";

        let buttonMenos = clone.childNodes[3].childNodes[1];
        let buttonMais = clone.childNodes[3].childNodes[5];

        buttonMais.setAttribute("onclick", "qtdePecaRoupa('+', 'peca"+num+"')");
        buttonMenos.setAttribute("onclick", "qtdePecaRoupa('-', 'peca"+num+"')");
        
        lista.appendChild(clone);
        atualizaPreco("id"+ num, "qtde"+num);
    }else if(acao === 'rem' && lista.childElementCount > 1){
        remArrayRoupa(lista.lastChild);
        lista.removeChild(lista.lastChild);
        return;
    }
}

function qtdePecaRoupa(acao, id){
    let peca = document.getElementById(id);
    let input = peca.childNodes[3].childNodes[3].childNodes[3];
    
    if(isNaN(parseInt(input.value))){
        input.value = 0;
        return;
    }else if(acao === '+'){
        input.value = parseInt(input.value) + 1;
    }else if(acao === '-' && parseInt(input.value) > 1){
        input.value = parseInt(input.value) - 1;
    }

    let num = id.slice(-1);
    atualizaPreco("id"+ num, "qtde"+num);
}

function mascara(t, mask){
        
    var i = t.value.length;
    var saida = mask.substring(1,0);
    var texto = mask.substring(i);

    if (texto.substring(0,1) !== saida){
       t.value += texto.substring(0,1);
    }
 }

 let listaPedidoPreco = [];
 let listaPedidoPrazo = [];
 listaPedidoPreco[1] = 1;
 listaPedidoPrazo[1] = 1;

 function remArrayRoupa(id){
    let num = id.id.slice(-1);
    listaPedidoPreco[num] = 0;
    listaPedidoPrazo[num] = 0;
    atualizaPreco(null, null);
 }



 function atualizaPreco(id, qtde){
    if(typeof rp === 'undefined'){
        return;
    }
    if(id != null){
        let num = id.slice(-1);

        let idPeca = document.getElementById(id).value;
        let qtdePeca = document.getElementById(qtde).value;

        console.log(idPeca);
        listaPedidoPreco[parseInt(num)] = parseInt(qtdePeca) * Number(rp['id'+idPeca].preco);
        listaPedidoPrazo[parseInt(num)] = parseInt(rp['id'+idPeca].limiteDias);     

    }
    atualizaPrecoTela();
 }

 function atualizaPrecoTela(){
    elPrazo = document.getElementById("prazoRoupa");
    elPreco = document.getElementById("precoTotal");
    
    let preco = listaPedidoPreco.reduce((a, b) => a + b, 0);
    let prazo = listaPedidoPrazo.reduce((a, b) => Math.max(a,b), 0);
    elPreco.value = "R$" + preco.toFixed(2);
    elPrazo.innerHTML = prazo;
 }
 
