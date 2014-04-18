package com.github.aiderpmsi.pimsdriver.vaadin;

import java.util.Date;
import java.util.Locale;

import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.LazyQueryDefinition;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PmsiProcessWindow extends Window {

	/** Generated serial Id */
	private static final long serialVersionUID = -7803472921198470202L;

	public PmsiProcessWindow() {
		// TITLE
		super("Fichiers pmsi en cours de traitement");
		
		// SET VISUAL ASPECT
        setWidth("650px");
        setHeight("80%");
        setClosable(true);
        setResizable(true);
        setModal(true);
        setStyleName("processpmsi");
        center();

        // SELECT LAYOUT
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        // ADDS DATATABLE
        Table processtable = new Table();
        processtable.setLocale(Locale.FRANCE);
        LazyQueryContainer lqc = new LazyQueryContainer(
        		new LazyQueryDefinition(false, 1000, "recordId"),
        		new PmsiProcessQueryFactory());
        lqc.addContainerProperty("finess", String.class, "", true, true);
        lqc.addContainerProperty("year", Integer.class, null, true, true);
        lqc.addContainerProperty("month", Integer.class, null, true, true);
        lqc.addContainerProperty("dateenvoi", Date.class, new Date(), true, true);
        lqc.addContainerProperty("comment", String.class, "", true, true);
        processtable.setContainerDataSource(lqc);
        processtable.setVisibleColumns(new Object[] {"finess", "year", "month", "dateenvoi", "comment"});
        processtable.setColumnHeaders(new String[] {"Finess", "Année", "Mois", "Date d'envoi", "Commentaire"} );
        processtable.setSelectable(true);
        processtable.setSizeFull();
        layout.addComponent(processtable);
        
	}

}
