package wasdev.sample.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().print("Hello World! Mi primera colonia, chispas...");
        
        //Buscar servicio Language Translator
        LanguageTranslator servicioTraduccion = buscarServicioTranslator();
        
        //Traducir texto
        
        
        //Buscar servicio Text to Speech
        
        
        //Invocar servicio
        
        
    }

}

private LanguageTranslator buscarServicioTranslator() throws Exception{
	String username = "";
	String password = "";
	String url = "";
    System.out.println("VCAP_SERVICES " + System.getenv("VCAP_SERVICES") + "*************");
        if (System.getenv("VCAP_SERVICES") == null || System.getenv("VCAP_SERVICES").equals("{}")){
        username = "a7e3f18d-4c1b-41f2-b3ee-15d97f4800ef";
        password = "nOSLjlfwrexb";
        url = "https://gateway.watsonplatform.net/language-translator/api";
    }else{
    JsonObject vcap = new JsonParser().parse(System.getenv("VCAP_SERVICES")).getAsJsonObject();
    System.out.println("vcal " + vcap.toString()); 
    JsonObject language = vcap.getAsJsonArray("language_translator").get(0).getAsJsonObject();
    System.out.println("language " + vcap.toString());
    JsonObject credentials = language.getAsJsonObject("credentials");
    System.out.println("credentials " + vcap.toString());
                
    username = credentials.get("username").getAsString();
    password = credentials.get("password").getAsString();
    url = credentials.get("url").getAsString();
    }

        LanguageTranslator service = new LanguageTranslator();
    service.setEndPoint(url);
    service.setUsernameAndPassword(username, password);  
}

private String 

private metodoPedro (void, void) throws Exception {
    
    String username = "";
    String password = "";
    String url = "";
    String word = "This is my test application";
    System.out.println("VCAP_SERVICES " + System.getenv("VCAP_SERVICES") + "*************");
        if (System.getenv("VCAP_SERVICES") == null || System.getenv("VCAP_SERVICES").equals("{}")){
        username = "a7e3f18d-4c1b-41f2-b3ee-15d97f4800ef";
        password = "nOSLjlfwrexb";
        url = "https://gateway.watsonplatform.net/language-translator/api";
    }else{
    JsonObject vcap = new JsonParser().parse(System.getenv("VCAP_SERVICES")).getAsJsonObject();
    System.out.println("vcal " + vcap.toString()); 
    JsonObject language = vcap.getAsJsonArray("language_translator").get(0).getAsJsonObject();
    System.out.println("language " + vcap.toString());
    JsonObject credentials = language.getAsJsonObject("credentials");
    System.out.println("credentials " + vcap.toString());
                
    username = credentials.get("username").getAsString();
    password = credentials.get("password").getAsString();
    url = credentials.get("url").getAsString();
    }

        LanguageTranslator service = new LanguageTranslator();
    service.setEndPoint(url);
    service.setUsernameAndPassword(username, password);     

        TranslationResult translationResult = service.translate(word, Language.ENGLISH, Language.SPANISH).execute();
        
        System.out.println(translationResult.getFirstTranslation());
        response.setContentType("text/html");
        response.getWriter().print(translationResult.getFirstTranslation());
    }
