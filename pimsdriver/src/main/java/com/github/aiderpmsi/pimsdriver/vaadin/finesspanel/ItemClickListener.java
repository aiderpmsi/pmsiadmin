package com.github.aiderpmsi.pimsdriver.vaadin.finesspanel;

import com.github.aiderpmsi.pimsdriver.model.PmsiUploadedElementModel;
import com.github.aiderpmsi.pimsdriver.vaadin.main.RootWindow;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.ItemClickEvent;

public class ItemClickListener implements ItemClickEvent.ItemClickListener {

	private static final long serialVersionUID = 822165023770852409L;

	@SuppressWarnings("unused")
	private ItemClickListener() {};
	
	private RootWindow rootElement;
	
	private HierarchicalContainer hc;
	
	public ItemClickListener(RootWindow rootEement, HierarchicalContainer hc) {
		this.rootElement = rootEement;
		this.hc = hc;
	}

	@Override
	public void itemClick(ItemClickEvent event) {

		// GETS THE EVENT NODE DEPTH
		Integer eventDepth =
				(Integer) hc.getContainerProperty(
						event.getItemId(), "depth").getValue();
		// GETS THE STATUS OF THE NODE (SUCCESSED OR FAILED)
		PmsiUploadedElementModel.Status eventStatus =
				(PmsiUploadedElementModel.Status) hc.getContainerProperty(
						event.getItemId(), "status").getValue();

		// DEPTH AT 3 MEANS AN UPLOAD HAS BEEN SELECTED
		if (eventDepth == 3) {
			//  PREVENT GUIUI THAT AN UPLOAD HAS BEEN SELECTED
			rootElement.fireFinessSelected(
					(PmsiUploadedElementModel) hc.getContainerProperty(event.getItemId(), "model").getValue(),
					eventStatus);
		}
	}

}
