/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 11/19/2011
 */

/**Class responsible for write the Code Smells in an XML file*/

package ufmg.crcs.smells.io;

import ufmg.crcs.smells.CodeSmell;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeSmellsWriter 
{
	private ArrayList<CodeSmell> code_smells; //Code Smells to be written in the file

	public CodeSmellsWriter(ArrayList<CodeSmell> code_smells)
	{
		this.code_smells=code_smells;
	}
	
	/**
	 * Writes the set of Code Smells in the given file
	 */
	public void write(IFile output_file) throws CodeSmellsIOException
	{
		/**
		 * Creates a file concurrently
		 */
		class CreateFile extends Thread
    	{
    		private IFile file;
    		private PipedInputStream in_stream;
    		    		
    		/**
    		 * Creates a new file to be written
    		 */
    		public CreateFile( IFile file, PipedInputStream in_stream )
    		{
    			this.file = file;
    			this.in_stream = in_stream;
    			
    			start();
    		}
    			
    		public void run()
    		{
    			try
    			{
    			    if( file.exists() )
    			    {
    			        file.setContents( in_stream, true, false, null );
    			    }
    			    else
    			    {
    			        file.create( in_stream, true, null );
    			    }
    			}
    			catch( CoreException exception )
    			{
    				throw new RuntimeException( "Exception during file creation", exception );
    			}
    			finally
    			{
    			    try
    			    {
    			        in_stream.close();
    			    }
    			    catch( IOException exception )
    			    {
    			        throw new RuntimeException( "Exception during file creation", exception );
    			    }
    			}
    		}
    	}
        
        try
        {
        	PipedOutputStream out_stream = new PipedOutputStream();
            PipedInputStream in_stream = new PipedInputStream( out_stream );
            Thread thread = new CreateFile( output_file, in_stream );
            Source source = new DOMSource( createDocument() );
     		Result result = new StreamResult( out_stream );
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform( source, result );
            out_stream.flush();
    	    out_stream.close();
    	    
    	    try
    	    {
    	    	thread.join();
    	    }
    	    catch( InterruptedException exception )
    	    {
    	    	throw new CodeSmellsIOException( exception.getMessage() );
    	    }
        }
        catch( TransformerConfigurationException exception )
        {
            throw new CodeSmellsIOException( exception.getMessage() );
        }
        catch( TransformerException exception )
        {
            throw new CodeSmellsIOException( exception.getMessage() );
        }
        catch( IOException exception )
        {
            throw new CodeSmellsIOException( exception.getMessage() );
        }
	}

	/**
	 * Creates the XML document
	 */
	private Document createDocument() throws CodeSmellsIOException
    {
        Document document = null;
        
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            Element code_smells_set = document.createElement("codesmellsset");
           
            document.appendChild(code_smells_set);
            
            for(CodeSmell code_smell:code_smells)
            {
                code_smells_set.appendChild(createCodeSmellNode(document,code_smell));
            }
        }
        catch( ParserConfigurationException exception )
        {
            throw new CodeSmellsIOException( exception.getMessage() );
        }
        return document;
    }
    
	/**
	 * Creates a XML element for the give Code Smell
	 */
    private Element createCodeSmellNode( Document document,CodeSmell code_smell) throws CodeSmellsIOException
    {
        Element returned_element = document.createElement("codesmell");
        
        //Insert each of the Code Smell attributes as an XML attribute
        returned_element.setAttribute("name",code_smell.getName());
        returned_element.setAttribute("mistake",code_smell.getMistake());
        returned_element.setAttribute("concern",code_smell.getConcern());
        returned_element.setAttribute("errorproneness",code_smell.getErrorProneness());
        returned_element.setAttribute("source",code_smell.getSource());
        returned_element.setAttribute("where",code_smell.getWhere());
        
        return returned_element;
    }
}
