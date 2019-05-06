package smartspace.infra;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smartspace.dao.EnhancedActionDao;
import smartspace.dao.EnhancedUserDao;
import smartspace.data.ActionEntity;
import smartspace.data.UserRole;

@Service
public class ActionServiceImpl implements ActionService {

	private EnhancedActionDao actionDao;
	private EnhancedUserDao<String> userDao;

	@Autowired
	public ActionServiceImpl(EnhancedActionDao actionDao, EnhancedUserDao<String> userDao) {
		this.actionDao = actionDao;
		this.userDao = userDao;
	}

	@Override
	public ActionEntity newAction(ActionEntity entity, String adminSmartspace, String adminEmail) {

		if (!(userDao.readById(adminEmail + "=" + adminSmartspace)
				.orElseThrow(() -> new RuntimeException("user doesn't exist")).getRole().equals(UserRole.ADMIN)))
			throw new RuntimeException("You are not an ADMIN!");
		if (valiadate(entity)) {
			entity.setCreationTimestamp(new Date());
			return this.actionDao.create(entity);
		} else {
			throw new RuntimeException("invalid action");
		}
	}

	private boolean valiadate(ActionEntity entity) {
		return entity.getPlayerEmail() != null && !entity.getPlayerEmail().trim().isEmpty()
				&& entity.getPlayerSmartspace() != null && !entity.getPlayerSmartspace().trim().isEmpty()
				&& entity.getActionSmartspace() != null && !entity.getActionSmartspace().trim().isEmpty()
				&& entity.getActionId() != null && !entity.getActionId().trim().isEmpty()
				&& entity.getElementSmartspace() != null && !entity.getElementSmartspace().trim().isEmpty()
				&& entity.getElementId() != null && !entity.getElementId().trim().isEmpty() && entity.getKey() != null
				&& !entity.getKey().trim().isEmpty() && entity.getActionType() != null
				&& !entity.getActionType().trim().isEmpty();
	}

	@Override
	public List<ActionEntity> getUsingPagination(int size, int page, String adminSmartspace, String adminEmail) {
		if (!(userDao.readById(adminEmail + "=" + adminSmartspace)
				.orElseThrow(() -> new RuntimeException("user doesn't exist"))
				.getRole().equals(UserRole.ADMIN)))
			throw new RuntimeException("You are not an ADMIN!");

		return this.actionDao
				.readAll("key", size, page);
	}
}
