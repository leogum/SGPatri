/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.Modelo;

import br.com.Utilitario.Upload;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


/**java servlet pathinfo
 *
 * @author d732229
 */
public class ArquivoUpload implements Logica{
    @Override    
     public String executa(HttpServletRequest req,
                     HttpServletResponse res) throws Exception {
        
        Upload up = new Upload(); 
        AutoCessaoValidacaoDAO autoVaDAO = new AutoCessaoValidacaoDAO();
        ArquivoDAO arDAO = new ArquivoDAO();
        Arquivo ar = new Arquivo();
        HttpSession session = req.getSession();

        String nomeDoArquivo = "", caminhoArquivo="";
        int pkArquivo;
        InputStream arquivoCarregado;
                
        
        String loginSessio =(String) session.getAttribute("sessionLogin");
        String tipoArquivo = req.getParameter("tipoArquivo");
        String origem = req.getParameter("Origem");
        int pkAutoStage = Integer.parseInt(req.getParameter("pkAutoStage"));
        pkArquivo = Integer.parseInt(req.getParameter("pkArquivo"));
        

        String pasta = "/Arquivo";
        String pastaArquivar = req.getServletContext().getRealPath(pasta);


        switch(tipoArquivo){
            case "planta":
                pastaArquivar+="\\Planta";
                Part uploadPlanta = req.getPart("UploadPlanta");
                nomeDoArquivo = uploadPlanta.getSubmittedFileName();
                arquivoCarregado = uploadPlanta.getInputStream();
                caminhoArquivo = up.upload(pastaArquivar, nomeDoArquivo, arquivoCarregado);
            break;

            case "AC":
                pastaArquivar+="\\AC";
                Part uploadAC = req.getPart("UploadAC");
                nomeDoArquivo = uploadAC.getSubmittedFileName();
                arquivoCarregado = uploadAC.getInputStream();
                caminhoArquivo = up.upload(pastaArquivar, nomeDoArquivo, arquivoCarregado);
            break;
            case "Deletar":
            break;
            default:
            break;
            }
            
            if(pkArquivo != 0){
                ar = new Arquivo(pkArquivo, pkAutoStage, origem, tipoArquivo, nomeDoArquivo, caminhoArquivo, loginSessio);
                arDAO.upArquivo(ar);
            }else{
                boolean duplicidade = arDAO.duplicidade(origem,tipoArquivo,caminhoArquivo);
                if(duplicidade == false){
                    ar = new Arquivo(pkAutoStage, origem, tipoArquivo, nomeDoArquivo, caminhoArquivo, loginSessio);
                    arDAO.cArquivo(ar);
                }    
            }
            AutoCessaoValidacao auto = autoVaDAO.detalheAutoCessao(pkAutoStage);
            req.setAttribute("auto", auto);
            return "AutoCessaoValidacao.jsp";
    }   

   
    
}