package com.github.aiderpmsi.pimsdriver.vaadin.main.contentpanel;

import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;

import com.github.aiderpmsi.pimsdriver.vaadin.main.contentpanel.factdetails.FactsDetailsWindow;
import com.github.aiderpmsi.pimsdriver.vaadin.main.contentpanel.pmsisource.PmsiSourceWindow;
import com.vaadin.event.Action;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class FactureSelectedHandler implements Action.Handler {
	
	private static final long serialVersionUID = 1L;

	static final Action ACTION_DETAILS = new Action("détails");
	static final Action ACTION_SOURCE = new Action("source");
	static final Action[] ACTIONS = new Action[] { ACTION_DETAILS, ACTION_SOURCE };
	static final Action[] NO_ACTION = new Action[] {};
	
	private LazyQueryContainer lzq;
	
	@SuppressWarnings("unused")
	private FactureSelectedHandler() {}
	
	public FactureSelectedHandler(LazyQueryContainer lzq) {
		this.lzq = lzq;
	}
	
	public Action[] getActions(Object target, Object sender) {

		if (target != null) {
			return ACTIONS;
		} else {
			return NO_ACTION;
		}
	}
	
	public void handleAction(Action action, Object sender, Object target) {
		if (target != null) {
			// GETS THE ROOT ID AND POSITION ID
			Long pmel_root = (Long) lzq.getContainerProperty(target, "pmel_root").getValue();
			Long pmel_position = (Long) lzq.getContainerProperty(target, "pmel_position").getValue();
			String numfacture = (String) lzq.getContainerProperty(target, "numfacture").getValue();

			if (action.equals(ACTION_DETAILS)) {
				// SHOWS FACT DETAILS
				final Window wProcess = new FactsDetailsWindow(pmel_root, pmel_position, numfacture);
				UI.getCurrent().addWindow(wProcess);
			} else if (action.equals(ACTION_SOURCE)) {
				// SHOWS FACT DETAILS
				final Window wProcess = new PmsiSourceWindow(pmel_root, pmel_position, numfacture);
				UI.getCurrent().addWindow(wProcess);
			}
		}
	}

}