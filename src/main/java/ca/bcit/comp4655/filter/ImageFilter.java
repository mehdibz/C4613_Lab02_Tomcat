package ca.bcit.comp4655.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebFilter;


@WebFilter ( filterName= "ImageFilter", urlPatterns="/*", asyncSupported=true )
@ServletSecurity(@HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL))
public class ImageFilter implements Filter
{
	
	private final static String DEFAUL_IMG = "duke.waving.gif";
	@Override
	public void destroy()
	{
		System.out.println( "Good bye from ImageFilter" );	
	}

	@Override
	public void doFilter(final ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException
	{
		request.setAttribute( "img", DEFAUL_IMG );
		
		
		final AsyncContext asyncContext = request.startAsync();
		asyncContext.addListener(new AsyncListener() {
			
			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				System.out.println("Took too long to search the directory for the requested image!");
			}
			
			@Override
			public void onStartAsync(AsyncEvent event) throws IOException {
				System.out.println("Finding image process started!");
			}
			
			@Override
			public void onError(AsyncEvent event) throws IOException {
				System.out.println("Error: " + event.getThrowable().getMessage() );
			}
			
			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				System.out.println("Process of finding image is completed");
			}
		} );
		
		final String userName = request.getParameter( "userName" );
		
		if (  userName!=null )
		{
			asyncContext.start( new Runnable() {
				
				@Override
				public void run() {
						String path = request.getServletContext().getRealPath( "/" + userName + ".gif" );
						if ( path!=null && new File(path).exists() )
						{
							request.setAttribute( "img",  userName + ".gif" );
							request.setAttribute("path", path);
						}
					}
				});
		}
		asyncContext.complete();
		filterChain.doFilter(request, response);
	}

}
