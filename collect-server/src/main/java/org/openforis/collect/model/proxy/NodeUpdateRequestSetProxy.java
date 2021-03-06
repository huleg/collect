/**
 * 
 */
package org.openforis.collect.model.proxy;

import java.util.ArrayList;
import java.util.List;

import org.openforis.collect.Proxy;
import org.openforis.collect.manager.CodeListManager;
import org.openforis.collect.manager.RecordSessionManager;
import org.openforis.collect.model.CollectRecord;
import org.openforis.collect.remoting.service.NodeUpdateRequest;
import org.openforis.collect.remoting.service.NodeUpdateRequestSet;

/**
 * @author S. Ricci
 *
 */
public class NodeUpdateRequestSetProxy implements Proxy {

	private List<NodeUpdateRequestProxy<?>> requests;
	private boolean autoSave;

	public NodeUpdateRequestSet toNodeUpdateRequestSet(CodeListManager codeListManager, 
			RecordSessionManager sessionManager, CollectRecord record) {
		NodeUpdateRequestSet result = new NodeUpdateRequestSet();
		List<NodeUpdateRequest> convertedRequests = new ArrayList<NodeUpdateRequest>();
		for (NodeUpdateRequestProxy<?> proxy : requests) {
			NodeUpdateRequest converted;
			if ( proxy instanceof BaseAttributeUpdateRequestProxy<?> ) {
				converted = ((BaseAttributeUpdateRequestProxy<?>) proxy).toAttributeUpdateRequest(
						codeListManager, sessionManager, record);
			} else {
				converted = proxy.toNodeUpdateRequest(record);
			}
			convertedRequests.add(converted);
		}
		result.setRequests(convertedRequests);
		return result;
	}
	
	public List<NodeUpdateRequestProxy<?>> getRequests() {
		return requests;
	}
	
	public void setRequests(List<NodeUpdateRequestProxy<?>> requests) {
		this.requests = requests;
	}

	public boolean isAutoSave() {
		return autoSave;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

}
