package wasdev.sample.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;
import com.ibm.watson.developer_cloud.language_translator.v2.model.Language;
import com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;


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
        response.getWriter().println("Hello World! Mi primera colonia, chispas...");
        
      	try{
	        //Buscar servicio Language Translator
	        LanguageTranslator servicioTraduccion = buscarServicioTranslator();
	        
	        //Traducir texto
	        String cadenaATraducir = "The rain in Spain stays mainly on the plain";
	        response.getWriter().println(cadenaATraducir);
	        String resultado = traduce( servicioTraduccion, cadenaATraducir );
	        response.setContentType("text/html");
	        response.getWriter().println(resultado);

	        //Buscar servicio Text to Speech
	        String urlServicioVoz = "https://stream.watsonplatform.net/text-to-speech/api";
	        String passwordServicioVoz = "TVeaxrFKR35N";
	        String userServicioVoz = "64210083-c962-4ef1-8627-74a5dbbc3b84";
	        
	        
	        TextToSpeech servicioVoz = new TextToSpeech();
	        servicioVoz.setUsernameAndPassword(userServicioVoz, passwordServicioVoz);

	        List<Voice> voices = servicioVoz.getVoices().execute();
	        System.out.println(voices);
	        //TODO-Establecer voz
	        
	        //Definir audio
	        
	        //Sintentizar voz
	        servicioVoz.synthesize(resultado, voices.get(1));
	        response.getWriter().println("Ha sonado la voz");
	        
	        
    	}catch( Exception ex){
    		System.out.println(ex);
    	}

        
    }

    private LanguageTranslator buscarServicioTranslator() throws Exception{
    	String username = "";
    	String password = "";
    	String url = "";
    	System.out.println("VCAP_SERVICES " + System.getenv("VCAP_SERVICES") + "*************");
    	if (System.getenv("VCAP_SERVICES") == null || System.getenv("VCAP_SERVICES").equals("{}")){
    		username = "43f7d029-7b34-4b75-854e-661e10d2a766";
    		password = "QL5niDv8BYvh";
    		url = "https://gateway.watsonplatform.net/language-translator/api";
    	}else{
    		JsonObject vcap = new JsonParser().parse(System.getenv("VCAP_SERVICES")).getAsJsonObject();
    		System.out.println("vcal " + vcap.toString()); 
    		JsonObject language = vcap.getAsJsonArray("language_translator").get(0).getAsJsonObject();
    		System.out.println("language " + vcap.toString());
    		JsonObject credentials = language.getAsJsonObject("credentials");
    		System.out.println("credentials " + vcap.toString());

    		username = credentials.get("username").getAsString();
    		password = credentials.get("password").getAsString();
    		url = credentials.get("url").getAsString();
    	}

    	LanguageTranslator service = new LanguageTranslator();
    	service.setEndPoint(url);
    	service.setUsernameAndPassword(username, password);

    	return service;
    }
    
    private String traduce (LanguageTranslator servicio, String input) throws Exception{
    	TranslationResult translationResult = servicio.translate(input, Language.ENGLISH, Language.SPANISH).execute();

    	System.out.println(translationResult.getFirstTranslation());

    	return translationResult.getFirstTranslation();
    }



}
