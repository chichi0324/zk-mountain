package com.mountain.springdemo.viewModel;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
//提示及確認訊息 元件
public class ViewModelHelper {
	
	public static void showConfirmBox(final String message, final EventListener<Event> eventListener) { //
		Messagebox.show(message //
				, "使用者確認" //
				, Messagebox.OK | Messagebox.CANCEL //
				, Messagebox.QUESTION //
				, eventListener);
	}

	public static void showMessage(final String message) {
		Messagebox.show(message);
	}
}
