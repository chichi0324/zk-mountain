package com.mountain.springdemo.viewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.Clients;

import com.mountain.springdemo.entity.Mountain;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@AfterCompose(superclass = true)
public class MountainAddEditDialogViewModel {
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MountainAddEditDialogViewModel.class);

	//視窗標題
	private String windowTitle = "";

	//當下視窗的view
	private Component moutainWindow;

	private String addOrEdit = "";

	//待 新增/修改 郊山活動
	private Mountain mountain = new Mountain();

	//"類型"下拉式選單項目
	private List<String> mountainTypes = new ArrayList<String>();

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public Mountain getMountain() {
		return mountain;
	}

	public void setMountain(Mountain mountain) {
		this.mountain = mountain;
	}

	public List<String> getMountainTypes() {
		return mountainTypes;
	}

	public void setMountainTypes(List<String> mountainTypes) {
		this.mountainTypes = mountainTypes;
	}

	public String getAddOrEdit() {
		return addOrEdit;
	}

	public void setAddOrEdit(String addOrEdit) {
		this.addOrEdit = addOrEdit;
	}

	public Component getMoutainWindow() {
		return moutainWindow;
	}

	public void setMoutainWindow(Component moutainWindow) {
		this.moutainWindow = moutainWindow;
	}


	@Init
	public void init(@ExecutionArgParam("view") final Component view,
			@ExecutionArgParam("theMountain") final Mountain theMountain) {

		this.moutainWindow = view;

		//theMountain為空值，為新增。有值則為修改		
		if (theMountain == null) {
			this.windowTitle = "新增登山活動";
			this.addOrEdit = "add";
		} else {
			this.windowTitle = "編輯登山活動";
			this.mountain = theMountain;
			this.addOrEdit = "edit";
		}

		//"類型"帶入(下拉式選單資料)
		mountainTypes.add("小百岳");
		mountainTypes.add("中級山");
		mountainTypes.add("其他山岳");
		
	}

	//  新增/修改 存擋前驗證
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void preCheck(@ContextParam(ContextType.VIEW) final Component view) {
		if (this.mountain.getM_date() == null) {
			this.mountain.setM_date(new java.sql.Timestamp(System.currentTimeMillis()));
		}
		if (this.mountain.getName() == null || "".equals(this.mountain.getName().trim())) {
			ViewModelHelper.showMessage("請輸入 活動名稱");
			return;
		}
		if (this.mountain.getLocation() == null || "".equals(this.mountain.getLocation().trim())) {
			ViewModelHelper.showMessage("請輸入 集合地點");
			return;
		}
		doSave(view);
	}

	private void doSave(@ContextParam(ContextType.VIEW) final Component view) {
		
		final Map<String, Object> params = new HashMap<>();
		params.put("view", view.getParent());
		params.put("theMountain", this.mountain);

		final Binder binder = (Binder) view.getParent().getAttribute("binder");
		if (binder == null)
			return;

		if ("add".equals(this.addOrEdit)) {
			// 執行上一個VM的doAddMountain command
			binder.postCommand("doAddMountain", params);
		} else {
			// 執行上一個VM的doEditMountain command
			binder.postCommand("doEditMountain", params);
		}

		this.doClose(view);
	}


	@Command
	public void doClose(@ContextParam(ContextType.VIEW) final Component view) {
		LOGGER.debug("onClose~");
		this.moutainWindow.getLastChild().detach();
	}

	//"新增"資料測試用
	@Command
	@NotifyChange("mountain")
	public void doTest() {
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
		this.mountain.setM_date(sqlTimestamp);
		this.mountain.setName("滿月圓森林步道");
		this.mountain.setLocation("三峽老街");
		this.mountain.setType("其他山岳");
		this.mountain.setDescription(
				"滿月圓森林遊樂區位於台北縣三峽鎮有木里，海拔高度介於300與1700公尺之間，四周群山環繞，流水飛瀑淙淙，為台北近郊瀑布最多的森林浴場，可賞覽滿月圓瀑布、處女瀑布、銀簾瀑布、小妮瀑布，很適合從事登山、賞楓、賞瀑、賞鳥等活動。");	
	}
}
