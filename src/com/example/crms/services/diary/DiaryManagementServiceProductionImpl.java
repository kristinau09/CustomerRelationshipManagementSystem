package com.example.crms.services.diary;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.crms.dao.ActionDao;
import com.example.crms.domain.Action;

@Transactional
public class DiaryManagementServiceProductionImpl implements DiaryManagementService {

	private ActionDao actionDao;

	//injecting dependency through constructor
	public DiaryManagementServiceProductionImpl(ActionDao actionDao) {
		this.actionDao=actionDao;
	}


	@Override
	public void recordAction(Action action) {
		actionDao.create(action);
	}

	@Override
	public List<Action> getAllIncompleteActions(String requiredUser) {		
		return actionDao.getIncompleteActions(requiredUser);
	}

}
