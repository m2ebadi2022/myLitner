package ir.taravatgroup.mylitner;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class store_activity extends Activity implements B4AActivity{
	public static store_activity mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.store_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (store_activity).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.store_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.store_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (store_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (store_activity) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return store_activity.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (store_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (store_activity) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            store_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (store_activity) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _scw_horiz = null;
public b4a.example3.customlistview _custom_ls = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _img_book_s = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_name_s = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_s = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_price_s = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_download_s = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.collections.List _list1 = null;
public adr.stringfunctions.stringfunctions _strfunc = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lsv_view_s = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_mota_cat = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_ebte_cat = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_fave_cat = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_poli_cat = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_free_cat = null;
public anywheresoftware.b4a.objects.WorkbookWrapper _workbook1 = null;
public anywheresoftware.b4a.objects.WorkbookWrapper.SheetWrapper _sheet1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_view_s = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.mylitner.main _main = null;
public ir.taravatgroup.mylitner.book_activity _book_activity = null;
public ir.taravatgroup.mylitner.lessen_activity _lessen_activity = null;
public ir.taravatgroup.mylitner.level_activity _level_activity = null;
public ir.taravatgroup.mylitner.all_words_activity _all_words_activity = null;
public ir.taravatgroup.mylitner.add_voice_activity _add_voice_activity = null;
public ir.taravatgroup.mylitner.all_exam_activity _all_exam_activity = null;
public ir.taravatgroup.mylitner.dictionary_activity _dictionary_activity = null;
public ir.taravatgroup.mylitner.exam_activity _exam_activity = null;
public ir.taravatgroup.mylitner.help_activity _help_activity = null;
public ir.taravatgroup.mylitner.init_exam_activity _init_exam_activity = null;
public ir.taravatgroup.mylitner.result_activity _result_activity = null;
public ir.taravatgroup.mylitner.setting_activity _setting_activity = null;
public ir.taravatgroup.mylitner.setting_time_activity _setting_time_activity = null;
public ir.taravatgroup.mylitner.singl_word_activity _singl_word_activity = null;
public ir.taravatgroup.mylitner.starter _starter = null;
public ir.taravatgroup.mylitner.word_activity _word_activity = null;
public ir.taravatgroup.mylitner.b4xcollections _b4xcollections = null;
public ir.taravatgroup.mylitner.xuiviewsutils _xuiviewsutils = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _row = 0;
String _s = "";
int _i = 0;
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="Activity.LoadLayout(\"store_layout\")";
mostCurrent._activity.LoadLayout("store_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 52;BA.debugLine="scw_horiz.Panel.LoadLayout(\"scw_h_items\")";
mostCurrent._scw_horiz.getPanel().LoadLayout("scw_h_items",mostCurrent.activityBA);
 //BA.debugLineNum = 53;BA.debugLine="list1.Initialize";
mostCurrent._list1.Initialize();
 //BA.debugLineNum = 55;BA.debugLine="strfunc.Initialize";
mostCurrent._strfunc._initialize(processBA);
 //BA.debugLineNum = 57;BA.debugLine="If (File.Exists(File.DirAssets,\"store_list.xls\"))T";
if ((anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"store_list.xls"))) { 
 //BA.debugLineNum = 59;BA.debugLine="Try";
try { //BA.debugLineNum = 61;BA.debugLine="Workbook1.Initialize(File.DirAssets,\"store_list";
mostCurrent._workbook1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"store_list.xls");
 //BA.debugLineNum = 62;BA.debugLine="Sheet1 = Workbook1.GetSheet(0)";
mostCurrent._sheet1 = mostCurrent._workbook1.GetSheet((int) (0));
 //BA.debugLineNum = 64;BA.debugLine="For row = 1 To Sheet1.RowsCount - 1";
{
final int step9 = 1;
final int limit9 = (int) (mostCurrent._sheet1.getRowsCount()-1);
_row = (int) (1) ;
for (;_row <= limit9 ;_row = _row + step9 ) {
 //BA.debugLineNum = 65;BA.debugLine="Dim s As String=Sheet1.GetCellValue(0, row)&\"%";
_s = mostCurrent._sheet1.GetCellValue((int) (0),_row)+"%"+mostCurrent._sheet1.GetCellValue((int) (1),_row)+"%"+mostCurrent._sheet1.GetCellValue((int) (2),_row)+"%"+mostCurrent._sheet1.GetCellValue((int) (3),_row)+"%"+mostCurrent._sheet1.GetCellValue((int) (4),_row)+"%"+mostCurrent._sheet1.GetCellValue((int) (5),_row)+"%"+mostCurrent._sheet1.GetCellValue((int) (6),_row)+"%"+mostCurrent._sheet1.GetCellValue((int) (7),_row);
 //BA.debugLineNum = 66;BA.debugLine="list1.Add(s)";
mostCurrent._list1.Add((Object)(_s));
 }
};
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 71;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("36619161",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 };
 //BA.debugLineNum = 79;BA.debugLine="pan_free_cat_Click";
_pan_free_cat_click();
 //BA.debugLineNum = 81;BA.debugLine="For i=0 To list1.Size-1";
{
final int step18 = 1;
final int limit18 = (int) (mostCurrent._list1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit18 ;_i = _i + step18 ) {
 //BA.debugLineNum = 82;BA.debugLine="Log (list1.Get(i))";
anywheresoftware.b4a.keywords.Common.LogImpl("36619172",BA.ObjectToString(mostCurrent._list1.Get(_i)),0);
 }
};
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 294;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 295;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 296;BA.debugLine="If(pan_all.Visible=True  )Then";
if ((mostCurrent._pan_all.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 297;BA.debugLine="pan_all_Click";
_pan_all_click();
 }else {
 //BA.debugLineNum = 300;BA.debugLine="btn_back_Click";
_btn_back_click();
 };
 //BA.debugLineNum = 304;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 306;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 308;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 291;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 292;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _btn_close_click() throws Exception{
 //BA.debugLineNum = 316;BA.debugLine="Private Sub btn_close_Click";
 //BA.debugLineNum = 317;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 318;BA.debugLine="End Sub";
return "";
}
public static String  _btn_download_s_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _sender_tag = null;
 //BA.debugLineNum = 179;BA.debugLine="Private Sub btn_download_s_Click";
 //BA.debugLineNum = 180;BA.debugLine="Dim sender_tag As Button";
_sender_tag = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 181;BA.debugLine="sender_tag=Sender";
_sender_tag = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 186;BA.debugLine="If(btn_download_s.Text==\"افزودن\") Then";
if (((mostCurrent._btn_download_s.getText()).equals("افزودن"))) { 
 //BA.debugLineNum = 187;BA.debugLine="If(File.Exists(File.DirInternal,get_item_in_list";
if ((anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),BA.ObjectToString(_get_item_in_listbook((int)(BA.ObjectToNumber(_sender_tag.getTag()))).Get((int) (7))))==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 189;BA.debugLine="func_file_rw(sender_tag.Tag,1)";
_func_file_rw((int)(BA.ObjectToNumber(_sender_tag.getTag())),(int) (1));
 };
 }else {
 };
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _btn_view_s_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _sender_tag = null;
 //BA.debugLineNum = 201;BA.debugLine="Private Sub btn_view_s_Click";
 //BA.debugLineNum = 202;BA.debugLine="Dim sender_tag As Button";
_sender_tag = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 203;BA.debugLine="sender_tag=Sender";
_sender_tag = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 205;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 206;BA.debugLine="lsv_view_s.Clear";
mostCurrent._lsv_view_s.Clear();
 //BA.debugLineNum = 208;BA.debugLine="func_file_rw(sender_tag.Tag,0)";
_func_file_rw((int)(BA.ObjectToNumber(_sender_tag.getTag())),(int) (0));
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _custom_ls_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Private Sub custom_LS_ItemClick (Index As Int, Val";
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _fill_list_view(int _type1) throws Exception{
int _i = 0;
String _lin = "";
anywheresoftware.b4a.objects.collections.List _item = null;
int _aa = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 87;BA.debugLine="Sub fill_list_view(type1 As Int)";
 //BA.debugLineNum = 89;BA.debugLine="custom_LS.Clear";
mostCurrent._custom_ls._clear();
 //BA.debugLineNum = 91;BA.debugLine="If(list1.Size>0)Then";
if ((mostCurrent._list1.getSize()>0)) { 
 //BA.debugLineNum = 94;BA.debugLine="For i=0 To list1.Size-1";
{
final int step3 = 1;
final int limit3 = (int) (mostCurrent._list1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 97;BA.debugLine="Dim lin As String=list1.Get(i)";
_lin = BA.ObjectToString(mostCurrent._list1.Get(_i));
 //BA.debugLineNum = 98;BA.debugLine="Dim item As List";
_item = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 99;BA.debugLine="item.Initialize";
_item.Initialize();
 //BA.debugLineNum = 100;BA.debugLine="item=strfunc.Split(lin,\"%\")";
_item = mostCurrent._strfunc._vvvvvv5(_lin,"%");
 //BA.debugLineNum = 104;BA.debugLine="Dim aa As Int=item.Get(0)";
_aa = (int)(BA.ObjectToNumber(_item.Get((int) (0))));
 //BA.debugLineNum = 105;BA.debugLine="If(aa=type1)Then";
if ((_aa==_type1)) { 
 //BA.debugLineNum = 110;BA.debugLine="Dim p As B4XView = xui.CreatePanel(item.Get(1))";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,BA.ObjectToString(_item.Get((int) (1))));
 //BA.debugLineNum = 111;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, 100%x, 190dip)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190)));
 //BA.debugLineNum = 112;BA.debugLine="p.LoadLayout(\"item_store\")";
_p.LoadLayout("item_store",mostCurrent.activityBA);
 //BA.debugLineNum = 115;BA.debugLine="lbl_name_s.Text=item.Get(2)";
mostCurrent._lbl_name_s.setText(BA.ObjectToCharSequence(_item.Get((int) (2))));
 //BA.debugLineNum = 116;BA.debugLine="lbl_count_s.Text=item.Get(5)";
mostCurrent._lbl_count_s.setText(BA.ObjectToCharSequence(_item.Get((int) (5))));
 //BA.debugLineNum = 117;BA.debugLine="lbl_price_s.Text= item.Get(3)";
mostCurrent._lbl_price_s.setText(BA.ObjectToCharSequence(_item.Get((int) (3))));
 //BA.debugLineNum = 118;BA.debugLine="btn_view_s.Tag=i";
mostCurrent._btn_view_s.setTag((Object)(_i));
 //BA.debugLineNum = 119;BA.debugLine="btn_download_s.Tag=i";
mostCurrent._btn_download_s.setTag((Object)(_i));
 //BA.debugLineNum = 121;BA.debugLine="If(type1=1)Then";
if ((_type1==1)) { 
 //BA.debugLineNum = 122;BA.debugLine="btn_download_s.Text=\"افزودن\"";
mostCurrent._btn_download_s.setText(BA.ObjectToCharSequence("افزودن"));
 }else {
 //BA.debugLineNum = 124;BA.debugLine="btn_download_s.Text=\"خرید\"";
mostCurrent._btn_download_s.setText(BA.ObjectToCharSequence("خرید"));
 };
 //BA.debugLineNum = 127;BA.debugLine="If(File.Exists(File.DirInternal,get_item_in_li";
if ((anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),BA.ObjectToString(_get_item_in_listbook(_i).Get((int) (7)))))) { 
 //BA.debugLineNum = 128;BA.debugLine="btn_download_s.Text=\"دانلود شده\"";
mostCurrent._btn_download_s.setText(BA.ObjectToCharSequence("دانلود شده"));
 //BA.debugLineNum = 129;BA.debugLine="btn_download_s.TextColor=Colors.Green";
mostCurrent._btn_download_s.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 };
 //BA.debugLineNum = 132;BA.debugLine="img_book_s.Bitmap=LoadBitmap(File.DirAssets,ite";
mostCurrent._img_book_s.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_item.Get((int) (4)))).getObject()));
 //BA.debugLineNum = 133;BA.debugLine="custom_LS.Add(p, item.Get(1))";
mostCurrent._custom_ls._add(_p,_item.Get((int) (1)));
 };
 }
};
 };
 //BA.debugLineNum = 143;BA.debugLine="lsv_view_s.TwoLinesLayout.Label.TextColor=Colors.";
mostCurrent._lsv_view_s.getTwoLinesLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 145;BA.debugLine="lsv_view_s.TwoLinesLayout.Label.Width=lsv_view_s.";
mostCurrent._lsv_view_s.getTwoLinesLayout().Label.setWidth((int) (mostCurrent._lsv_view_s.getWidth()-20));
 //BA.debugLineNum = 146;BA.debugLine="lsv_view_s.TwoLinesLayout.SecondLabel.Width=lsv_v";
mostCurrent._lsv_view_s.getTwoLinesLayout().SecondLabel.setWidth((int) (mostCurrent._lsv_view_s.getWidth()-20));
 //BA.debugLineNum = 148;BA.debugLine="lsv_view_s.TwoLinesLayout.SecondLabel.Gravity=Gra";
mostCurrent._lsv_view_s.getTwoLinesLayout().SecondLabel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 149;BA.debugLine="lsv_view_s.TwoLinesLayout.Label.Gravity=Gravity.R";
mostCurrent._lsv_view_s.getTwoLinesLayout().Label.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.RIGHT);
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _func_file_rw(int _id,int _act) throws Exception{
int _row = 0;
 //BA.debugLineNum = 211;BA.debugLine="Sub func_file_rw(id As Int , act As Int)";
 //BA.debugLineNum = 215;BA.debugLine="If(act=0)Then";
if ((_act==0)) { 
 //BA.debugLineNum = 219;BA.debugLine="Try";
try { //BA.debugLineNum = 220;BA.debugLine="Workbook1.Initialize(File.DirAssets, get_item_i";
mostCurrent._workbook1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_get_item_in_listbook(_id).Get((int) (7)))+".xls");
 //BA.debugLineNum = 221;BA.debugLine="Sheet1 = Workbook1.GetSheet(0)";
mostCurrent._sheet1 = mostCurrent._workbook1.GetSheet((int) (0));
 //BA.debugLineNum = 223;BA.debugLine="For row = 1 To 20  'Sheet1.RowsCount - 1";
{
final int step5 = 1;
final int limit5 = (int) (20);
_row = (int) (1) ;
for (;_row <= limit5 ;_row = _row + step5 ) {
 //BA.debugLineNum = 224;BA.debugLine="If (Sheet1.GetCellValue(0, row).Trim==\"\") Then";
if (((mostCurrent._sheet1.GetCellValue((int) (0),_row).trim()).equals(""))) { 
 //BA.debugLineNum = 225;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 230;BA.debugLine="lsv_view_s.AddTwoLines(row&\"-\"&Sheet1.GetCellV";
mostCurrent._lsv_view_s.AddTwoLines(BA.ObjectToCharSequence(BA.NumberToString(_row)+"-"+mostCurrent._sheet1.GetCellValue((int) (0),_row)+" : "+mostCurrent._sheet1.GetCellValue((int) (1),_row)),BA.ObjectToCharSequence(mostCurrent._sheet1.GetCellValue((int) (2),_row)+" -- "+mostCurrent._sheet1.GetCellValue((int) (3),_row)));
 }
};
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 235;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-11\")&";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-11")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act==1)) { 
 //BA.debugLineNum = 244;BA.debugLine="Try";
try { //BA.debugLineNum = 246;BA.debugLine="Workbook1.Initialize(File.DirAssets, get_item_i";
mostCurrent._workbook1.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_get_item_in_listbook(_id).Get((int) (7)))+".xls");
 //BA.debugLineNum = 247;BA.debugLine="Sheet1 = Workbook1.GetSheet(0)";
mostCurrent._sheet1 = mostCurrent._workbook1.GetSheet((int) (0));
 //BA.debugLineNum = 249;BA.debugLine="For row = 1 To Sheet1.RowsCount - 1";
{
final int step18 = 1;
final int limit18 = (int) (mostCurrent._sheet1.getRowsCount()-1);
_row = (int) (1) ;
for (;_row <= limit18 ;_row = _row + step18 ) {
 //BA.debugLineNum = 250;BA.debugLine="If (Sheet1.GetCellValue(0, row).Trim==\"\") Then";
if (((mostCurrent._sheet1.GetCellValue((int) (0),_row).trim()).equals(""))) { 
 //BA.debugLineNum = 251;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 256;BA.debugLine="lsv_view_s.AddTwoLines(\"1-\"&Sheet1.GetCellValu";
mostCurrent._lsv_view_s.AddTwoLines(BA.ObjectToCharSequence("1-"+mostCurrent._sheet1.GetCellValue((int) (0),_row)+" : "+mostCurrent._sheet1.GetCellValue((int) (1),_row)),BA.ObjectToCharSequence(mostCurrent._sheet1.GetCellValue((int) (2),_row)+" -- "+mostCurrent._sheet1.GetCellValue((int) (3),_row)));
 //BA.debugLineNum = 258;BA.debugLine="Main.sq_db.ExecNonQuery2(\"INSERT INTO words (q";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._sheet1.GetCellValue((int) (0),_row)),(Object)(mostCurrent._sheet1.GetCellValue((int) (1),_row)),_get_item_in_listbook(_id).Get((int) (2)),(Object)("درس1"),(Object)(mostCurrent._sheet1.GetCellValue((int) (2),_row)),(Object)(mostCurrent._sheet1.GetCellValue((int) (3),_row))}));
 }
};
 //BA.debugLineNum = 261;BA.debugLine="Main.sq_db.ExecNonQuery(\"INSERT INTO books (nam";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery("INSERT INTO books (name, tozihat) VALUES ('"+BA.ObjectToString(_get_item_in_listbook(_id).Get((int) (2)))+"','"+BA.ObjectToString(_get_item_in_listbook(_id).Get((int) (5)))+" لغات ')");
 //BA.debugLineNum = 264;BA.debugLine="Main.sq_db.ExecNonQuery(\"INSERT INTO lessen (na";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery("INSERT INTO lessen (name, book) VALUES ('"+"درس1"+"','"+BA.ObjectToString(_get_item_in_listbook(_id).Get((int) (2)))+"')");
 //BA.debugLineNum = 266;BA.debugLine="File.WriteString(File.DirInternal,get_item_in_l";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),BA.ObjectToString(_get_item_in_listbook(_id).Get((int) (7))),"");
 //BA.debugLineNum = 269;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"book-8\"),Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-8")),anywheresoftware.b4a.keywords.Common.False);
 } 
       catch (Exception e30) {
			processBA.setLastException(e30); //BA.debugLineNum = 271;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-11\")&";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-11")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 287;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _get_item_in_listbook(int _id) throws Exception{
String _lin = "";
anywheresoftware.b4a.objects.collections.List _item = null;
 //BA.debugLineNum = 153;BA.debugLine="Sub get_item_in_listBook (id As Int)As List";
 //BA.debugLineNum = 155;BA.debugLine="Dim lin As String=list1.Get(id)";
_lin = BA.ObjectToString(mostCurrent._list1.Get(_id));
 //BA.debugLineNum = 156;BA.debugLine="Dim item As List";
_item = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 157;BA.debugLine="item.Initialize";
_item.Initialize();
 //BA.debugLineNum = 158;BA.debugLine="item=strfunc.Split(lin,\"%\")";
_item = mostCurrent._strfunc._vvvvvv5(_lin,"%");
 //BA.debugLineNum = 160;BA.debugLine="Return item";
if (true) return _item;
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return null;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 16;BA.debugLine="Private scw_horiz As HorizontalScrollView";
mostCurrent._scw_horiz = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private custom_LS As CustomListView";
mostCurrent._custom_ls = new b4a.example3.customlistview();
 //BA.debugLineNum = 18;BA.debugLine="Private img_book_s As ImageView";
mostCurrent._img_book_s = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lbl_name_s As Label";
mostCurrent._lbl_name_s = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lbl_count_s As Label";
mostCurrent._lbl_count_s = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lbl_price_s As Label";
mostCurrent._lbl_price_s = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btn_download_s As Button";
mostCurrent._btn_download_s = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim list1 As List";
mostCurrent._list1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 26;BA.debugLine="Dim strfunc As StringFunctions";
mostCurrent._strfunc = new adr.stringfunctions.stringfunctions();
 //BA.debugLineNum = 29;BA.debugLine="Private lsv_view_s As ListView";
mostCurrent._lsv_view_s = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private pan_mota_cat As Panel";
mostCurrent._pan_mota_cat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private pan_ebte_cat As Panel";
mostCurrent._pan_ebte_cat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private pan_fave_cat As Panel";
mostCurrent._pan_fave_cat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private pan_poli_cat As Panel";
mostCurrent._pan_poli_cat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private pan_free_cat As Panel";
mostCurrent._pan_free_cat = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Workbook1 As ReadableWorkbook";
mostCurrent._workbook1 = new anywheresoftware.b4a.objects.WorkbookWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Sheet1 As ReadableSheet";
mostCurrent._sheet1 = new anywheresoftware.b4a.objects.WorkbookWrapper.SheetWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btn_view_s As Button";
mostCurrent._btn_view_s = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 310;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 312;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static String  _pan_color_rest() throws Exception{
 //BA.debugLineNum = 350;BA.debugLine="Sub pan_color_rest";
 //BA.debugLineNum = 351;BA.debugLine="pan_free_cat.Color=0xFFFFFFFF";
mostCurrent._pan_free_cat.setColor(((int)0xffffffff));
 //BA.debugLineNum = 352;BA.debugLine="pan_poli_cat.Color=0xFFFFFFFF";
mostCurrent._pan_poli_cat.setColor(((int)0xffffffff));
 //BA.debugLineNum = 353;BA.debugLine="pan_fave_cat.Color=0xFFFFFFFF";
mostCurrent._pan_fave_cat.setColor(((int)0xffffffff));
 //BA.debugLineNum = 354;BA.debugLine="pan_ebte_cat.Color=0xFFFFFFFF";
mostCurrent._pan_ebte_cat.setColor(((int)0xffffffff));
 //BA.debugLineNum = 355;BA.debugLine="pan_mota_cat.Color=0xFFFFFFFF";
mostCurrent._pan_mota_cat.setColor(((int)0xffffffff));
 //BA.debugLineNum = 356;BA.debugLine="End Sub";
return "";
}
public static String  _pan_ebte_cat_click() throws Exception{
 //BA.debugLineNum = 338;BA.debugLine="Private Sub pan_ebte_cat_Click";
 //BA.debugLineNum = 339;BA.debugLine="pan_color_rest";
_pan_color_rest();
 //BA.debugLineNum = 340;BA.debugLine="fill_list_view(4)";
_fill_list_view((int) (4));
 //BA.debugLineNum = 341;BA.debugLine="pan_ebte_cat.Color=0xFF5DB0FF";
mostCurrent._pan_ebte_cat.setColor(((int)0xff5db0ff));
 //BA.debugLineNum = 342;BA.debugLine="End Sub";
return "";
}
public static String  _pan_fave_cat_click() throws Exception{
 //BA.debugLineNum = 332;BA.debugLine="Private Sub pan_fave_cat_Click";
 //BA.debugLineNum = 333;BA.debugLine="pan_color_rest";
_pan_color_rest();
 //BA.debugLineNum = 334;BA.debugLine="fill_list_view(3)";
_fill_list_view((int) (3));
 //BA.debugLineNum = 335;BA.debugLine="pan_fave_cat.Color=0xFF5DB0FF";
mostCurrent._pan_fave_cat.setColor(((int)0xff5db0ff));
 //BA.debugLineNum = 336;BA.debugLine="End Sub";
return "";
}
public static String  _pan_free_cat_click() throws Exception{
 //BA.debugLineNum = 320;BA.debugLine="Private Sub pan_free_cat_Click";
 //BA.debugLineNum = 321;BA.debugLine="pan_color_rest";
_pan_color_rest();
 //BA.debugLineNum = 322;BA.debugLine="fill_list_view(1)";
_fill_list_view((int) (1));
 //BA.debugLineNum = 323;BA.debugLine="pan_free_cat.Color=0xFF5DB0FF";
mostCurrent._pan_free_cat.setColor(((int)0xff5db0ff));
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static String  _pan_mota_cat_click() throws Exception{
 //BA.debugLineNum = 344;BA.debugLine="Private Sub pan_mota_cat_Click";
 //BA.debugLineNum = 345;BA.debugLine="pan_color_rest";
_pan_color_rest();
 //BA.debugLineNum = 346;BA.debugLine="fill_list_view(5)";
_fill_list_view((int) (5));
 //BA.debugLineNum = 347;BA.debugLine="pan_mota_cat.Color=0xFF5DB0FF";
mostCurrent._pan_mota_cat.setColor(((int)0xff5db0ff));
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _pan_poli_cat_click() throws Exception{
 //BA.debugLineNum = 326;BA.debugLine="Private Sub pan_poli_cat_Click";
 //BA.debugLineNum = 327;BA.debugLine="pan_color_rest";
_pan_color_rest();
 //BA.debugLineNum = 328;BA.debugLine="fill_list_view(2)";
_fill_list_view((int) (2));
 //BA.debugLineNum = 329;BA.debugLine="pan_poli_cat.Color=0xFF5DB0FF";
mostCurrent._pan_poli_cat.setColor(((int)0xff5db0ff));
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
