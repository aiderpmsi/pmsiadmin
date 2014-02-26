package com.github.aiderpmsi.pimsdriver.processor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

@Provider
public class ProcessListener implements ContainerLifecycleListener {

	Logger log = Logger.getLogger(ProcessListener.class.toString());
	
	Future<Boolean> threadResult = null;
	
	@Override
	public void onStartup(Container container) {
		// INITIALIZE RUNNING PROCESSOR
		ExecutorService execute = Executors.newSingleThreadExecutor();
		threadResult = execute.submit(new ProcessTask());
	}

	@Override
	public void onReload(Container container) {
		// STOP AND START
		onShutdown(container);
		onStartup(container);
	}

	@Override
	public void onShutdown(Container container) {
		try {
			threadResult.get();
		} catch (InterruptedException | ExecutionException e) {
			log.warning(e.getMessage());
		}
		threadResult.cancel(true);
	}
}