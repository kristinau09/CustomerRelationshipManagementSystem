package com.example.crms.services.diary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crms.dao.ActionDao;
import com.example.crms.domain.Action;

@Transactional
@Service
public class DiaryManagementServiceProductionImpl implements DiaryManagementService {

	@Autowired
	private ActionDao actionDao;

	public DiaryManagementServiceProductionImpl(ActionDao actionDao) {
		this.actionDao = actionDao;
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
