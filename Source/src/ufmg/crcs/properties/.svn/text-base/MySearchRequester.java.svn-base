/**
 * Federal University of Minas Gerais 
 * Department of Computer Science
 * ConcernReCS Project
 *
 * Created by Pericles Alves
 * Date: 09/20/2011
 */

/**class responsible to request search matches*/

package ufmg.crcs.properties;

import java.util.*;

import org.eclipse.jdt.core.search.*;

class MySearchRequester extends SearchRequestor
{
	ArrayList<SearchMatch> searchmatches; //Search matches
	
	/**
	 * Initializes the search matches ArrayList when the search begins
	 */
	public void beginReporting()
	{
		searchmatches=new ArrayList<SearchMatch>();
	}
	
	/**
	 * @return the search matches
	 */
	public ArrayList<SearchMatch> getSearchMatches()
	{
		return searchmatches;
	}
	
	/**
	 * Collect an accurate search match
	 */
	public void acceptSearchMatch(SearchMatch match)
	{
		//Verifies if an accepted search match is accurate and has an known source, if so, the search match is stored
		if((match.getAccuracy()==SearchMatch.A_ACCURATE)&&(match.getOffset()!=-1)&&(match.getLength()!=-1))
			searchmatches.add(match);
	}
}
