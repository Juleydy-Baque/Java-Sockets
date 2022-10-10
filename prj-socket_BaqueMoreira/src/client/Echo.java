package client;

import java.io.*;
import java.net.*;


public class Echo 
{
	
	private static EchoObjectStub eo;
	
	public static void main( String[] args )
	{
		
		//EJERCICIO: crear una instancia del stub
		eo = new EchoObjectStub();
		BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
		PrintWriter stdOut = new PrintWriter( System.out );
		String input, output;
		try
		{
			//Bucle que lee de teclado, invoca el eco y escribe respuesta en la pantalla:
			while( true )
			{
				stdOut.println("Escriba cadena para invocar su eco");
				input = stdIn.readLine();//Lee cadena introducida por teclado
				//EJERCICIO: Invocar para la cadena leida el método echo del stub
				output = eo.echo( input );
				//Escribe la respuesta del eco en la pantalla
				stdOut.println( output );
			}
		} catch( UnknownHostException e ) {
			System.err.println( "Don't know about host: " + args[0] );
		} catch( IOException e ) {
			System.err.println( "I/O failed for connection to host: " + args[0] );
		}
	}
}
