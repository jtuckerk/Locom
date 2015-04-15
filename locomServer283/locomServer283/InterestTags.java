package locomServer283;

import java.util.HashSet;
import java.util.Set;

public class InterestTags {

	private Set<String> tags = new HashSet<String>();
	
	public InterestTags(String[] arrTags){
		for (String tag: arrTags){
			this.tags.add(tag);
		}
	}
	
	public Boolean hasInterest(String singleTag){
		
		return this.tags.contains(singleTag);	
	}
	
	public Boolean hasInterests(String[] tags){
		
		for (String tag: tags){
			if (this.tags.contains(tag)){
				return true;
			}
		}
		return false;
	}
}
