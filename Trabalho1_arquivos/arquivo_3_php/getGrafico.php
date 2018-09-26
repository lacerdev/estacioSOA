<?php

function idadePessoa(){
// requisição da classe PHPlot
require_once 'phplot-6.2.0/phplot.php';

$arquivo = "estatisticas.json";

// Atribui o conteúdo do arquivo para variável $arquivo
$info = file_get_contents($arquivo);

//faz o parsing da string, criando o array
$data2 = json_decode($info, true);
//$data = var_dump($data2);
sort($data2);
$data = array(
             array('MULHERES', $data2[0]), 
             array('HOMENS', $data2[1]),
             //array($data2[2])
             );
# Cria um novo objeto do tipo PHPlot com 500px de largura x 350px de altura                 
$plot = new PHPlot(500 , 350);     
  
// Organiza Gráfico -----------------------------
$plot->SetTitle('Quantidade X Genero');
# Precisão de nenhuma casa decimal
$plot->SetPrecisionY(1);
# tipo de Gráfico em barras (poderia ser linepoints por exemplo)
$plot->SetPlotType("bars");
# Tipo de dados que preencherão o Gráfico
$plot->SetDataType("text-data");
# Adiciona ao gráfico os valores do array
$plot->SetDataValues($data);
// -----------------------------------------------
  
// Organiza eixo X ------------------------------
# Seta os traços (grid) do eixo X para invisível
$plot->SetXTickPos('none');
# Texto abaixo do eixo X
$plot->SetXLabel("Genero");
# Texto abaixo do eixo Y
$plot->SetYLabel("Quantidade");
# Tamanho da fonte que varia de 1-5
$plot->SetXLabelFontSize(2);
$plot->SetAxisFontSize(2);
// -----------------------------------------------
// Organiza eixo Y -------------------------------
# Coloca nos pontos os valores de Y
$plot->SetYDataLabelPos('plotin');
// -----------------------------------------------
  
// Desenha o Gráfico -----------------------------
$plot->DrawGraph();
// -----------------------------------------------
}

idadePessoa();