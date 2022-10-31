package proxyrest.advice.response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface ExpectedResponse {

	String responseHandler() default "";
	
	String contentType() default "";
	
	int status() default 200;
		
}
