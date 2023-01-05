# Simple ProxyREST
REST wrapper based on annotations with automatic data-binding

## What it does
**Simple ProxyREST** abstracts all the complexity of consuming an external REST API, using annotations on a interface to describe the behaviour of each route (instead of implementing).

All it needs to work is annotations to describe:
 - What route is being mapped (URL)
 - What is being passed (query and path params, headers, request body)
 - What will be returned and how to handle it (response body and headers)
 
This is done by combining the use of proxies, reflection and decorator patterns to implement the desired behaviour on an underlying implementation.


## How it works
Did you ever wondered *how **Spring** dependency injection works*, simply by using annotations to describe what will be injected? Or *how **Hibernate** can pull up data from databases* from interface methods that don't have any code implemented on it?

Like many other languages, Java has reflection, which allows the program to examine, inspect, access and modify its own objects and class structure at runtime.
Also, Java reflection allows the using of **proxies**, allowing the program to create a concrete implementation of an interface at runtime. This is done by intercepting the method call and delegating it to a callback, which decides what object will be returned.

What **Simple ProxyREST** does is:
 1. A proxy implementation is created in runtime, implementing the annotated interface.
 2. When a method of that interface is called, Java redirects the call to this proxy implementation, describing all the method call information: what method was called, its returning type, the passed arguments, the parameter types and more.
 3. Through reflection, the program collects all the annotations left on the method head, its returning type and parameters and the parent interface.
 4. The annotations and its properties are processed, to create an HTTP request.
 5. After building the HTTP request and obtaining a response, the data is parsed and returned to the original method call.

## How to use
This is an example of how GitHub `GET /users` could be implemented:
```java
@JsonIgnoreProperties(ignoreUnknown = true)
public static record User(int id, String name){
}

@Endpoint(value = "https://api.github.com")
public static interface GithubAPI extends RestAPI {
	
	@GetMapping(mapping = "/users")
	public User getUser(@PathParam String username);

}

public static void main(String[] args){
	final GithubAPI api = RestAPI.createAPI(GithubAPI.class);
	System.out.println(api.getUser("Eb3rM5").name());
}
```
Here's what is being done on this code:
 1. A model object is created to represent the `User` data. Here we are using `record` as an example, but it could be any type of structure supported by Jackson.
 2. The interface `GithubAPI` is created to represent the desired methods available on the real GitHub's API.
	 1. The `@Endpoint` annotation describes the base URL.
	 2. The method `getUser` is annotated with `@GetMapping`, indicating that the path of the route is `/users`.
	 3. The `username` parameter is annotated with `@PathParam` to indicate that its value should be included on the URL.
3. Finally, the GithubAPI is instantiated on the `main` method.
4. Calling `api.getUser("Eb3rM5")` will result on a `User` object. Since it holds a `name` property, we can print it.

Note that on this example the data is parsed into an `User` object, and only after parsing it we collect the `name` attribute.
To simplify things, we could return the `name` directly on the method, by adding a new method on the interface:
```java
@Endpoint(value = "https://api.github.com")
public static interface GithubAPI extends RestAPI {	

	@GetMapping(mapping = "/users")
	public User getUser(@PathParam String username);
	
	@JsonPropertyMapping(property = "name", mapping = "/users", method = "GET")
	public String getUserName(@PathParam String name);

}
```
Annotating the method with `@JsonPropertyMapping` will make the underlying implementation parse the JSON data and return the specified property. Note that if the specified property is another complex object like `User`, a model object can be created, so it can be parsed as one.
