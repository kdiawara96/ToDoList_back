package com.codesign.ToDoList.Others;

import java.util.Map;
import java.util.HashMap;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//1
@Configuration
@Slf4j
@SuppressWarnings("null")
public class WebMvcConfig implements WebMvcConfigurer {

    private final String serverFolder = "/ToDo_back/";
    private final String apiStarter  = "/todo/";
    private final String apiFileStarter  = "/todoFile/";
    

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ewaatiFile/home/app_ressources/todo/**")
                .addResourceLocations("file:/home/app_ressources/todo/");
                
        registry.addResourceHandler("/ewaatiFile/src/main/resources/files/**")
                .addResourceLocations("classpath:/files/");
    }
    //6
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Ajouter l'intercepteur personnalisé pour la correspondance des noms de ressource aléatoires
        registry.addInterceptor(new ResourceNameHandlerInterceptor()).addPathPatterns("/todo/**");
        registry.addInterceptor(new ResourceFileNameHandlerInterceptor()).addPathPatterns("/todoFile/**");
    }
        //2
    public class ResourceNameHandlerInterceptor implements HandlerInterceptor {
        //3
        private Map<String, String> resourceNames = new HashMap<>();
        //4
        public ResourceNameHandlerInterceptor() {
            //UTILISATEURS
            resourceNames.put("utilisateurs/changeProfil", "ffdhhhfhfjhfhhhfddddffhhfdfhghhhrhrrhhrhhfgfd");
            resourceNames.put("utilisateurs/updateDroitsForUser", "iojdojosjdoidjdjodkjdijfoifdjofdkcsksjksnsndddsdsddd");
        }
       
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            String path = request.getRequestURI().replace(serverFolder, "/");
            for (String resourceName : resourceNames.keySet()) {
                if (path.startsWith(apiStarter + resourceNames.get(resourceName))) {
                    String newPath = path.replace(apiStarter + resourceNames.get(resourceName), apiStarter + resourceName);
                    request.getRequestDispatcher(newPath).forward(request, response);
                    return false;
                }
            }
            return true;
        }
    }

    public class ResourceFileNameHandlerInterceptor implements HandlerInterceptor {
        //3
        private Map<String, String> resourceFileNames = new HashMap<>();
        //4
        public ResourceFileNameHandlerInterceptor() {
            //FILES
            resourceFileNames.put("home/app_ressources/todo", "qkjsisjiudsjsdlkqjldsdiuihdijdsqlkksdslqdsdsjjdsjdsqdsqqs");
            resourceFileNames.put("src/main/resources/files", "kdjsjdolklejmelkezdkdpzkezdkdmlzkeuijdddlkjddkjddkjddkdzz");
        }
    
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            String path = request.getRequestURI().replace(serverFolder, "/");
            for (String resourceFileName : resourceFileNames.keySet()) {
                if (path.startsWith(apiFileStarter + resourceFileNames.get(resourceFileName))) {
                    String newPath = path.replace(apiFileStarter + resourceFileNames.get(resourceFileName), apiFileStarter + resourceFileName);
                    log.info("newPath : " + newPath);
                    request.getRequestDispatcher(newPath).forward(request, response);
                    return false;
                }
            }
            return true;
        }
    }

}
