package smartspace.infra;

import java.util.List;

import smartspace.data.ElementEntity;

public interface RESTElementService {
	
	public void updateElement(ElementEntity elementEntity, String managerSmartspace,  String managerEmail, String elementSmartspace, String elementId);
	
	public ElementEntity createNewElement(ElementEntity elementEntity, String managerSmartspace,  String managerEmail);
		
	public List<ElementEntity> getUsingPagination(int size, int page, String userSmartspace, String userEmail);

}