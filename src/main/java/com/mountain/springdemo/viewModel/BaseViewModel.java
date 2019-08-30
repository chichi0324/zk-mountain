package com.mountain.springdemo.viewModel;

import java.util.HashMap;

import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Clients;

//執行javascript
//執行scrollLocation.js控制ScrollBar
public class BaseViewModel {

	public void viewScrollToButtom(@ContextParam(ContextType.VIEW) final Component view) {
		final Binder binder = (Binder) view.getAttribute("binder");
		binder.postCommand("doScrollToButtom", null);
	}

	// 呼叫scrollLocation.js的scrollToButtom()
	// scroll位置在div的底部
	@Command
	public void doScrollToButtom() {
		Clients.evalJavaScript("scrollToButtom()");
	}

	public void viewScrollTo(@ContextParam(ContextType.VIEW) final Component view, int mountainIndex) {
		final Map<String, Object> params = new HashMap<>();
		params.put("mountainIndex", mountainIndex);

		final Binder binder = (Binder) view.getAttribute("binder");
		binder.postCommand("doAfterDeleteScrollTo", params);
	}

	// 呼叫scrollLocation.js的doAfterDeleteScrollTo()
	// scroll位置在index的位置
	@Command
	public void doAfterDeleteScrollTo(@BindingParam("mountainIndex") final int mountainIndex) {
		Clients.evalJavaScript("scrollTo(" + mountainIndex + ")");
	}
}
