package ir.ac.iust.dml.kg.knowledge.store.services;

import io.swagger.annotations.Api;

import javax.jws.WebService;

@Api("/sayHello")
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.store.services.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
}