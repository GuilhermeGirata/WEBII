package com.web2.model.util;

import com.web2.model.beans.Endereco;
import com.web2.model.exceptions.ViaCepException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViaCep {

    private static String connectAPI(String cep) throws ViaCepException{
        String json;

        try {
            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();
            System.out.println(json);

        } catch (Exception e) {
            throw new ViaCepException("Falha na conexão da API ViaCEP", e);
        }

        return json;
    }
    
    public static Map<String,String> idkMyCep(String estado,String cidade,String logradouro) throws ViaCepException{
        logradouro = logradouro.replaceAll(" ","%20");
        String json = connectAPI(estado + "/" + cidade + "/" + logradouro);
        Map<String,String> mapa = new HashMap<>();

        Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
        while (matcher.find()) {
            String[] group = matcher.group().split(":");
            mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
        } 
        
        return mapa;
    }

    
    public static Endereco findCep(String CEP) throws ViaCepException{
        
        String json = "";
        
        json = connectAPI(CEP);
        
        Map<String,String> mapa = new HashMap<>();

        Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
        while (matcher.find()) {
            String[] group = matcher.group().split(":");
            mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
        } 
        
        Endereco end = new Endereco(mapa.get("uf"), mapa.get("localidade"), mapa.get("cep"), mapa.get("logradouro"));
        return end;
   }
    
    /*
    public static void main(String[] args) throws ViaCepException {
        Endereco DadosCep = findCep("81670070");
        //Map<String,String> DadosCep = idkMyCep("PR","Curitiba","Rua Jose Laurindo de Souza");
        
        System.out.println(DadosCep.getRua());

    }
   */

}

