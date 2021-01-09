package com.example.crms.services.diary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.crms.domain.Action;

public class DiaryManagementServiceMockImpl implements DiaryManagementService {
	//we are not using map because action do not have id
	private Set<Action> allActions = new HashSet<Action>();

	@Override
	public void recordAction(Action action) {
		allActions.add(action); //adding action to set

	}

	@Override
	public List<Action> getAllIncompleteActions(String requiredUser) {
		//list to store the result of action
		List<Action> results = new ArrayList<Action>();
		for(Action next: allActions) {
			//taking the next action in the set if the owning user is the one that's required and the action is not complete
			//then we found an action that we want to work with so we'll add it to the list of results
			if(next.getOwningUser().equals(requiredUser) && !next.isComplete()) {
				results.add(next);
			}
		}
		return results;
	}

}
