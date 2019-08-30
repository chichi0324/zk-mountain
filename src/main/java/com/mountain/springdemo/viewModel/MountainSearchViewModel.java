package com.mountain.springdemo.viewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import com.mountain.springdemo.entity.Mountain;
import com.mountain.springdemo.service.MountainService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@AfterCompose(superclass = true)
public class MountainSearchViewModel extends BaseViewModel {

	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MountainSearchViewModel.class);
	private static final String PAGE_QUERY = "search.zul";
	private static final String ADD_EDIT_PAGE = "addEditDialog.zul";

	private String keyword;
	private List<Mountain> mountainList;
	private Mountain selectedMountain;
	private String note;
	
	private String zuPage = PAGE_QUERY;

	@WireVariable("MountainService")
	private transient MountainService mountainService;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<Mountain> getMountainList() {
		return mountainList;
	}

	public void setMountainList(List<Mountain> mountainList) {
		this.mountainList = mountainList;
	}

	public Mountain getSelectedMountain() {
		return selectedMountain;
	}

	public void setSelectedMountain(Mountain selectedMountain) {
		this.selectedMountain = selectedMountain;
	}

	public String getZuPage() {
		return zuPage;
	}

	public void setZuPage(String zuPage) {
		this.zuPage = zuPage;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	//=======================================================
	// --------------------  search  ------------------------
	//=======================================================


	//模糊查詢山岳活動資料
	@Command
	@NotifyChange("mountainList")
	public void search() {
		this.mountainList = this.mountainService.search(this.keyword);
	}
	
	//顯示"活動明細"
	@Command
	@NotifyChange({"selectedMountain","note"})
	public void showMountainDetail(@ContextParam(ContextType.VIEW) final Component view,
			@BindingParam("theMountain") final Mountain theMountain) {

		this.selectedMountain=theMountain;
		this.note="提醒：請背小背包，穿著運動服裝，運動鞋，現金、健保卡、悠遊卡加值、午餐、行動水、行動糧、雨衣、禦寒衣物、帽子 等等";
	}

	//======================================================
	// --------------------  add  --------------------------
	//======================================================
	
	//開啟"新增活動視窗"
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void openAddMountainDialog(@ContextParam(ContextType.VIEW) final Component view) {

		final Map args = new HashMap();
		args.put("view", view);
		args.put("theMountain", null);
		Executions.getCurrent().createComponents(ADD_EDIT_PAGE, view, args);
	}
	
	//"新增活動視窗"驗證後帶回的資料做"新增"
	@Command
	public void doAddMountain(@ContextParam(ContextType.VIEW) final Component view,
			@BindingParam("theMountain") final Mountain theMountain) {
		
		theMountain.setPreview("/img/noPic.png");
		this.mountainService.addMountain(theMountain);
		this.search();
		
		//同@NotifyChangem("mountainList")
		BindUtils.postNotifyChange(null, null, this, "mountainList");

		// 新增完資料出現在畫面最下面
		viewScrollToButtom(view);

		ViewModelHelper.showMessage("新增成功");
		
	}

	//=======================================================
	// --------------------  update  ------------------------
	//=======================================================
	
	//開啟"編輯活動視窗"
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void openEditMountainDialog(@ContextParam(ContextType.VIEW) final Component view,
			@BindingParam("theMountain") final Mountain theMountain) {

		final Map args = new HashMap();
		args.put("view", view);
		args.put("theMountain", theMountain);
		Executions.getCurrent().createComponents(ADD_EDIT_PAGE, view, args);
	}

	//"編輯活動視窗"驗證後帶回的資料做"編輯"
	@Command
	public void doEditMountain(@BindingParam("view") final Component view,
			@BindingParam("theMountain") final Mountain theMountain) {
		
		int mountainIndex = this.mountainList.indexOf(theMountain);

		this.mountainService.updateMountain(theMountain);
		this.search();
		
		//同@NotifyChangem("mountainList")
		BindUtils.postNotifyChange(null, null, this, "mountainList");

		// 新增完資料出現在畫面最下面
		viewScrollTo(view,mountainIndex);

		ViewModelHelper.showMessage("編輯成功");
	}

	//=======================================================
	// --------------------  delete  ------------------------
	//=======================================================
	
	//刪除開始視窗做確認，Events.ON_OK才刪除活動資料
	@Command
	public void doDeleteMountain(@ContextParam(ContextType.VIEW) final Component view,
			@BindingParam("mountainDetail") final Mountain theMountain) {
		ViewModelHelper.showConfirmBox("確認刪除?", new EventListener<Event>() {
			@Override
			public void onEvent(final Event event) throws Exception {
				if (Objects.equals(Events.ON_OK, event.getName())) {
					goDelCheck(view, theMountain);
				}
			}
		});
	}
	
	//刪除活動資料
	private void goDelCheck(@ContextParam(ContextType.VIEW) final Component view, final Mountain theMountain) {
		mountainService.deleteMountain(theMountain);

		int mountainIndex = this.mountainList.indexOf(theMountain) - 1;
		if (this.mountainList == null || this.mountainList.size() == 1) {
			mountainIndex = 0;
		}

		mountainList.remove(theMountain);
		BindUtils.postNotifyChange(null, null, this, "mountainList");

		// 刪除完資料出現在畫面當比前一筆
		viewScrollTo(view, mountainIndex);

	}


}
