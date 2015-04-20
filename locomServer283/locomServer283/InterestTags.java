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
	
	public Boolean hasInterests(InterestTags tags){
		
		String[] tagArray = tags.tags.toArray(new String[tags.tags.size()]);
		
		System.out.println("checking interests");
		System.out.println("interests: "+ tagArray.toString());
		for (String tag: tagArray){
			System.out.println("iterating through tags: " + tag);
			if (this.tags.contains(tag)){
				return true;
			}
		}
		return false;
	}
}
