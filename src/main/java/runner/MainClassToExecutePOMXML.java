package runner;

import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import reporting.Logging;

public class MainClassToExecutePOMXML {
	   public static void main(String[] args) {
	        InvocationRequest request = new DefaultInvocationRequest();
	        Logging.logger1.info(System.getProperty("user.dir")+File.separator+"pom.xml" );
	        request.setPomFile( new File( System.getProperty("user.dir")+File.separator+"pom.xml"  ));
	        request.setGoals( Collections.singletonList( "test" ));
	        Invoker invoker = new DefaultInvoker();
	        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
	        try {
	            invoker.execute( request );
	        } catch (MavenInvocationException e) {
	            e.printStackTrace();
	        } 
	    } 
}
