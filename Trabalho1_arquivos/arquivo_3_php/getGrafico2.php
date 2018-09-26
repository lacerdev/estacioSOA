<?php


function graf(){
    require_once 'phplot-6.2.0/phplot.php';
    
    $arquivo = "estatisticas.json";

    // Atribui o conteúdo do arquivo para variável $arquivo
    $info = file_get_contents($arquivo);

    //faz o parsing da string, criando o array
    $data2 = json_decode($info, true);
    //$data = var_dump($data2);
    sort($data2);
    $data = array(
             array('MULHERES', $data2[2]), 
             array('HOMENS', $data2[3]),
             //array($data2[2])
             );
    
    $plot = new PHPlot(500 , 350);     

    $plot->SetTitle('Idade media x Genero');
    $plot->SetPrecisionY(1);
    $plot->SetPlotType("bars");
    $plot->SetDataType("text-data");
    $plot->SetDataValues($data);
    
    $plot->SetXTickPos('none');
    $plot->SetXLabel("Genero");
    $plot->SetYLabel("Idade media");
    $plot->SetXLabelFontSize(2);
    $plot->SetAxisFontSize(2);
    
    $plot->SetYDataLabelPos('plotin');
    
    $plot->DrawGraph();
}

graf();
