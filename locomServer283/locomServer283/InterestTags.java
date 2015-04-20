package locomServer283;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


//class to hold a set of interests a user has and provide a simple 
// interface for checking whether a user has matching interest of a broadcast
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
	
	public Boolean hasInterests(InterestTags tagsCheck){
		
		String[] tagArray = tagsCheck.getTagsSet().toArray(new String[tagsCheck.getTagsSet().size()]);
		
		for (String tag: tagArray){
			System.out.println("iterating through tags: " + tag);
			if (this.tags.contains(tag)){
				return true;
			}
		}
		return false;
	}
	public Set<String> getTagsSet(){
		return this.tags;
	}
	public void printUserInterests(){
		String[] tagArrayUser = this.tags.toArray(new String[this.tags.size()]);
		System.out.println("User Interests: "+ Arrays.toString(tagArrayUser));
	}
}
