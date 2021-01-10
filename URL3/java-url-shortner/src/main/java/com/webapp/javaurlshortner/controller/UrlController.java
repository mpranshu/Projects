package com.webapp.javaurlshortner.controller;

import com.webapp.javaurlshortner.dto.UrlLongRequest;
import com.webapp.javaurlshortner.service.UrlService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.net.*;


import java.net.URI;

// https://www.javatpoint.com/spring-vs-spring-boot-vs-spring-mvc
/*
@Controller annotation is an annotation used in Spring MVC framework (the component of Spring Framework used to implement Web Application).
The @Controller annotation indicates that a particular class serves the role of a controller.
The @Controller annotation acts as a stereotype for the annotated class, indicating its role.
The dispatcher scans such annotated classes for mapped methods and detects @RequestMapping annotations
 */

// A controller contains the business logic of an application.

// It's a convenience annotation that combines @Controller and @ResponseBody –
// which eliminates the need to annotate every request handling method of the controller class with the @ResponseBody annotation.

// https://www.baeldung.com/spring-controller-vs-restcontroller

//@RequestMapping is one of the most common annotation used in Spring Web applications.
// This annotation maps HTTP requests to handler methods of MVC and REST controllers.
// The @RequestMapping annotation can be applied to class-level and/or method-level in a controller
//
@RestController
@RequestMapping(path="/")
public class UrlController {

    // reference to object of urlService that can convertToShortUrl(called with dto) or getOriginalUrl (called with String shortUrl)
    private final UrlService urlService;

    // Parameterized constructor to initialize urlService reference and has base conversion, url Repository objects
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // https://www.programcreek.com/java-api-examples/?api=io.swagger.annotations.ApiOperation

    /*
    @PostMapping is specialized version of @RequestMapping annotation that acts as a shortcut for :
    @RequestMapping(method = RequestMethod.POST).
    @PostMapping annotated methods handle the HTTP POST requests matched with given URI expression.
     */

    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping(path="create-short")
    public String convertToShortUrl(@RequestBody UrlLongRequest request) throws Exception{
        /*
         @RequestBody annotation maps the HttpRequest body to a transfer or domain object,
         enabling automatic deserialization of the inbound HttpRequest body onto a Java object.
         Thus values passed from inbound HttpRequest gets converted to DTO UrlLongRequest request
         https://www.baeldung.com/spring-request-response-body
         */
        var shortUrl = urlService.convertToShortUrl(request); // Convert to shortUrl using UrlService
        return shortUrl;
    }

    /*
    @GetMapping is specialized version of @RequestMapping annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).
    @GetMapping annotated methods handle the HTTP GET requests matched with given URI expression
    https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/
     */

    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public RedirectView getAndRedirect(@PathVariable String shortUrl) throws URISyntaxException{
        /*
        the @PathVariable annotation can be used to handle template variables in the request URI mapping,
        and use them as method parameters.
        https://www.baeldung.com/spring-pathvariable
         */
        var url = "create-short";
        RedirectView redirectView = new RedirectView();
        System.out.println("shortUrl: "+shortUrl+" type:"+shortUrl.getClass());
        if(shortUrl==null || shortUrl.length()==0 || shortUrl.startsWith(" ")){
            redirectView.setUrl(url);
            return redirectView;
        }
        url = urlService.getOriginalUrl(shortUrl);
        redirectView.setUrl(url);
        return redirectView;

    }


}

/*
Here is an example of the @RequestMapping annotation applied to both class and methods.

@RestController
@RequestMapping("/home")
public class IndexController {
  @RequestMapping("/")
  String get(){
    //mapped to hostname:port/home/
    return "Hello from get";
  }
  @RequestMapping("/index")
  String index(){
    //mapped to hostname:port/home/index/
    return "Hello from index";
  }
}
With the preceding code, requests to /home will be handled by get() while request to /home/index will be handled by index().
Reference: https://springframework.guru/spring-requestmapping-annotation/
 */

