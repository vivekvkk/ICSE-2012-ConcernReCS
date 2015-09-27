/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 11/19/2011
 */

/**An exception to indicate IO problems*/

package ufmg.crcs.smells.io;

import java.io.IOException;

public class CodeSmellsIOException extends IOException
{
	private static final long serialVersionUID = 1L;

	public CodeSmellsIOException(String message) 
	{
		super(message);
	}
}