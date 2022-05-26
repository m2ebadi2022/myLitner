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

public class all_words_activity extends Activity implements B4AActivity{
	public static all_words_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.all_words_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (all_words_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.all_words_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.all_words_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (all_words_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (all_words_activity) Resume **");
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
		return all_words_activity.class;
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
            BA.LogInfo("** Activity (all_words_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (all_words_activity) Pause event (activity is not paused). **");
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
            all_words_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (all_words_activity) Resume **");
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
public anywheresoftware.b4a.objects.collections.List _words_list = null;
public anywheresoftware.b4a.objects.collections.List _words_list_id = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public ir.taravatgroup.mylitner.preoptimizedclv _pre_custom_lv = null;
public b4a.example3.customlistview _custom_lv = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_right = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_wrong = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_qustion = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_path = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_search = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_close_search = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_all = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_book = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_lessen = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_title_page = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_filter_not = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_filter_yes = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_myliked = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.mylitner.main _main = null;
public ir.taravatgroup.mylitner.book_activity _book_activity = null;
public ir.taravatgroup.mylitner.lessen_activity _lessen_activity = null;
public ir.taravatgroup.mylitner.level_activity _level_activity = null;
public ir.taravatgroup.mylitner.store_activity _store_activity = null;
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
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur2 = null;
int _j = 0;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 47;BA.debugLine="Activity.LoadLayout(\"all_words_layout\")";
mostCurrent._activity.LoadLayout("all_words_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 49;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 50;BA.debugLine="Panel2.Color=Main.app_color";
mostCurrent._panel2.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 54;BA.debugLine="pre_custom_lv.Initialize(Me,\"pre_custom_lv\",custo";
mostCurrent._pre_custom_lv._initialize /*String*/ (mostCurrent.activityBA,all_words_activity.getObject(),"pre_custom_lv",mostCurrent._custom_lv);
 //BA.debugLineNum = 55;BA.debugLine="pre_custom_lv.B4XSeekBar1.ThumbColor=xui.Color_Li";
mostCurrent._pre_custom_lv._b4xseekbar1 /*ir.taravatgroup.mylitner.b4xseekbar*/ ._thumbcolor /*int*/  = mostCurrent._xui.Color_LightGray;
 //BA.debugLineNum = 56;BA.debugLine="pre_custom_lv.lblHint.TextColor=xui.Color_Blue";
mostCurrent._pre_custom_lv._lblhint /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setTextColor(mostCurrent._xui.Color_Blue);
 //BA.debugLineNum = 59;BA.debugLine="words_list_id.Initialize";
mostCurrent._words_list_id.Initialize();
 //BA.debugLineNum = 60;BA.debugLine="words_list.Initialize";
mostCurrent._words_list.Initialize();
 //BA.debugLineNum = 61;BA.debugLine="Dim cur2 As Cursor";
_cur2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 63;BA.debugLine="If(Main.end_page==\"home\")Then  '--از صفحه هوم آم";
if (((mostCurrent._main._end_page /*String*/ ).equals("home"))) { 
 //BA.debugLineNum = 64;BA.debugLine="Main.query=\"SELECT * FROM words\"";
mostCurrent._main._query /*String*/  = "SELECT * FROM words";
 //BA.debugLineNum = 66;BA.debugLine="lbl_title_page.Text=Main.loc.Localize(\"main-7\")";
mostCurrent._lbl_title_page.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-7")));
 }else if(((mostCurrent._main._end_page /*String*/ ).equals("level"))) { 
 //BA.debugLineNum = 68;BA.debugLine="Main.query=\"SELECT * FROM words WHERE book='\"& M";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE book='"+mostCurrent._main._book_name /*String*/ +"' AND lessen='"+mostCurrent._main._lessen_name /*String*/ +"' AND level=100";
 //BA.debugLineNum = 70;BA.debugLine="lbl_title_page.Text=Main.loc.Localize(\"all-5\")";
mostCurrent._lbl_title_page.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("all-5")));
 }else if(((mostCurrent._main._end_page /*String*/ ).equals("stared"))) { 
 //BA.debugLineNum = 72;BA.debugLine="Main.query=\"SELECT * FROM words WHERE state =1\"";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE state =1";
 //BA.debugLineNum = 73;BA.debugLine="btn_myliked.Text=\"همه\"";
mostCurrent._btn_myliked.setText(BA.ObjectToCharSequence("همه"));
 //BA.debugLineNum = 74;BA.debugLine="lbl_title_page.Text=Main.loc.Localize(\"all-6\")";
mostCurrent._lbl_title_page.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("all-6")));
 };
 //BA.debugLineNum = 77;BA.debugLine="cur2 = Main.sq_db.ExecQuery(Main.query)";
_cur2 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 78;BA.debugLine="lbl_count_all.Text=cur2.RowCount";
mostCurrent._lbl_count_all.setText(BA.ObjectToCharSequence(_cur2.getRowCount()));
 //BA.debugLineNum = 80;BA.debugLine="For j=0 To cur2.RowCount-1";
{
final int step23 = 1;
final int limit23 = (int) (_cur2.getRowCount()-1);
_j = (int) (0) ;
for (;_j <= limit23 ;_j = _j + step23 ) {
 //BA.debugLineNum = 81;BA.debugLine="cur2.Position=j";
_cur2.setPosition(_j);
 //BA.debugLineNum = 83;BA.debugLine="words_list.Add(cur2.GetString(\"question\"))";
mostCurrent._words_list.Add((Object)(_cur2.GetString("question")));
 //BA.debugLineNum = 84;BA.debugLine="words_list_id.Add(cur2.Getint(\"id\"))";
mostCurrent._words_list_id.Add((Object)(_cur2.GetInt("id")));
 //BA.debugLineNum = 86;BA.debugLine="pre_custom_lv.AddItem(130dip, xui.Color_ARGB(25";
mostCurrent._pre_custom_lv._additem /*String*/ (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)),mostCurrent._xui.Color_ARGB((int) (255),(int) (213),(int) (213),(int) (213)),(Object)(_cur2.GetInt("id")));
 }
};
 //BA.debugLineNum = 91;BA.debugLine="pre_custom_lv.Commit";
mostCurrent._pre_custom_lv._commit /*String*/ ();
 //BA.debugLineNum = 94;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group30 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen30 = group30.getSize()
;int index30 = 0;
;
for (; index30 < groupLen30;index30++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group30.Get(index30)));
 //BA.debugLineNum = 95;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 96;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 97;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 99;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 100;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 101;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 104;BA.debugLine="lbl_title_page.TextSize=18";
mostCurrent._lbl_title_page.setTextSize((float) (18));
 //BA.debugLineNum = 106;BA.debugLine="Label1.Text=Main.loc.Localize(\"all-1\")";
mostCurrent._label1.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("all-1")));
 //BA.debugLineNum = 107;BA.debugLine="et_search.Hint=Main.loc.Localize(\"all-2\")";
mostCurrent._et_search.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("all-2"));
 //BA.debugLineNum = 108;BA.debugLine="lbl_filter_yes.Text=Main.loc.Localize(\"all-3\")";
mostCurrent._lbl_filter_yes.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("all-3")));
 //BA.debugLineNum = 109;BA.debugLine="lbl_filter_not.Text=Main.loc.Localize(\"all-4\")";
mostCurrent._lbl_filter_not.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("all-4")));
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 133;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 134;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 135;BA.debugLine="If(pan_all.Visible=True)Then";
if ((mostCurrent._pan_all.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 136;BA.debugLine="pan_all_Click";
_pan_all_click();
 }else {
 //BA.debugLineNum = 138;BA.debugLine="btn_back_Click";
_btn_back_click();
 };
 //BA.debugLineNum = 140;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 142;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 131;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _btn_close_search_click() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Private Sub btn_close_search_Click";
 //BA.debugLineNum = 187;BA.debugLine="et_search.Text=\"\"";
mostCurrent._et_search.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 188;BA.debugLine="btn_close_search.Visible=False";
mostCurrent._btn_close_search.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _btn_filter_click() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur3 = null;
int _i = 0;
 //BA.debugLineNum = 220;BA.debugLine="Private Sub btn_filter_Click";
 //BA.debugLineNum = 221;BA.debugLine="sp_book.Clear";
mostCurrent._sp_book.Clear();
 //BA.debugLineNum = 223;BA.debugLine="Dim cur3 As Cursor";
_cur3 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 224;BA.debugLine="cur3 = Main.sq_db.ExecQuery(\"SELECT * FROM books\"";
_cur3 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM books")));
 //BA.debugLineNum = 225;BA.debugLine="For i=0 To cur3.RowCount-1";
{
final int step4 = 1;
final int limit4 = (int) (_cur3.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 226;BA.debugLine="cur3.Position=i";
_cur3.setPosition(_i);
 //BA.debugLineNum = 227;BA.debugLine="sp_book.Add(cur3.GetString(\"name\"))";
mostCurrent._sp_book.Add(_cur3.GetString("name"));
 }
};
 //BA.debugLineNum = 230;BA.debugLine="sp_book_ItemClick(0,sp_book.GetItem(0))";
_sp_book_itemclick((int) (0),(Object)(mostCurrent._sp_book.GetItem((int) (0))));
 //BA.debugLineNum = 231;BA.debugLine="sp_lessen_ItemClick(0,sp_book.GetItem(0))";
_sp_lessen_itemclick((int) (0),(Object)(mostCurrent._sp_book.GetItem((int) (0))));
 //BA.debugLineNum = 234;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public static String  _btn_myliked_click() throws Exception{
 //BA.debugLineNum = 283;BA.debugLine="Private Sub btn_myliked_Click";
 //BA.debugLineNum = 284;BA.debugLine="If(btn_myliked.Text=\"همه\")Then";
if (((mostCurrent._btn_myliked.getText()).equals("همه"))) { 
 //BA.debugLineNum = 285;BA.debugLine="Main.end_page=\"home\"";
mostCurrent._main._end_page /*String*/  = "home";
 //BA.debugLineNum = 286;BA.debugLine="btn_myliked.Text=\"برگزیده\"";
mostCurrent._btn_myliked.setText(BA.ObjectToCharSequence("برگزیده"));
 }else {
 //BA.debugLineNum = 289;BA.debugLine="Main.end_page=\"stared\"";
mostCurrent._main._end_page /*String*/  = "stared";
 };
 //BA.debugLineNum = 293;BA.debugLine="custom_lv.Clear";
mostCurrent._custom_lv._clear();
 //BA.debugLineNum = 294;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _btn_search() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur1 = null;
int _i = 0;
String _tt = "";
 //BA.debugLineNum = 155;BA.debugLine="Private Sub btn_search";
 //BA.debugLineNum = 158;BA.debugLine="custom_lv.Clear";
mostCurrent._custom_lv._clear();
 //BA.debugLineNum = 160;BA.debugLine="btn_close_search.Visible=True";
mostCurrent._btn_close_search.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 161;BA.debugLine="Dim cur1 As Cursor";
_cur1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 163;BA.debugLine="For i=0 To words_list_id.Size-1";
{
final int step4 = 1;
final int limit4 = (int) (mostCurrent._words_list_id.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 164;BA.debugLine="Dim tt As String=words_list.Get(i)";
_tt = BA.ObjectToString(mostCurrent._words_list.Get(_i));
 //BA.debugLineNum = 165;BA.debugLine="tt=tt.ToLowerCase";
_tt = _tt.toLowerCase();
 //BA.debugLineNum = 167;BA.debugLine="If ( tt.Contains(et_search.Text.Trim) ) Then";
if ((_tt.contains(mostCurrent._et_search.getText().trim()))) { 
 //BA.debugLineNum = 168;BA.debugLine="cur1 = Main.sq_db.ExecQuery(\"SELECT * FROM word";
_cur1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM words WHERE id="+BA.ObjectToString(mostCurrent._words_list_id.Get(_i)))));
 //BA.debugLineNum = 169;BA.debugLine="cur1.Position=0";
_cur1.setPosition((int) (0));
 //BA.debugLineNum = 170;BA.debugLine="pre_custom_lv.AddItem(140dip, xui.Color_ARGB(25";
mostCurrent._pre_custom_lv._additem /*String*/ (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)),mostCurrent._xui.Color_ARGB((int) (255),(int) (213),(int) (213),(int) (213)),mostCurrent._words_list_id.Get(_i));
 };
 }
};
 //BA.debugLineNum = 177;BA.debugLine="pre_custom_lv.Commit";
mostCurrent._pre_custom_lv._commit /*String*/ ();
 //BA.debugLineNum = 178;BA.debugLine="custom_lv.Refresh";
mostCurrent._custom_lv._refresh();
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _custom_lv_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Private Sub custom_lv_ItemClick (Index As Int, Val";
 //BA.debugLineNum = 150;BA.debugLine="Main.current_word_id=Value";
mostCurrent._main._current_word_id /*int*/  = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 151;BA.debugLine="StartActivity(singl_word_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._singl_word_activity.getObject()));
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static String  _custom_lv_visiblerangechanged(int _firstindex,int _lastindex) throws Exception{
int _i = 0;
b4a.example3.customlistview._clvitem _item = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _id = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur3 = null;
 //BA.debugLineNum = 191;BA.debugLine="Private Sub custom_lv_VisibleRangeChanged (FirstIn";
 //BA.debugLineNum = 194;BA.debugLine="For Each i As Int In pre_custom_lv.VisibleRangeCh";
{
final anywheresoftware.b4a.BA.IterableList group1 = mostCurrent._pre_custom_lv._visiblerangechanged /*anywheresoftware.b4a.objects.collections.List*/ (_firstindex,_lastindex);
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_i = (int)(BA.ObjectToNumber(group1.Get(index1)));
 //BA.debugLineNum = 195;BA.debugLine="Dim item As CLVItem=custom_lv.GetRawListItem(i)";
_item = mostCurrent._custom_lv._getrawlistitem(_i);
 //BA.debugLineNum = 196;BA.debugLine="Dim p As B4XView=xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 197;BA.debugLine="item.Panel.AddView(p,0,0,item.Panel.Width,item.P";
_item.Panel.AddView((android.view.View)(_p.getObject()),(int) (0),(int) (0),_item.Panel.getWidth(),_item.Panel.getHeight());
 //BA.debugLineNum = 198;BA.debugLine="p.LoadLayout(\"item_words\")";
_p.LoadLayout("item_words",mostCurrent.activityBA);
 //BA.debugLineNum = 200;BA.debugLine="Dim id As Int =item.Value";
_id = (int)(BA.ObjectToNumber(_item.Value));
 //BA.debugLineNum = 201;BA.debugLine="Dim cur3 As Cursor";
_cur3 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 202;BA.debugLine="cur3 = Main.sq_db.ExecQuery(\"SELECT * FROM words";
_cur3 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM words WHERE id="+BA.NumberToString(_id))));
 //BA.debugLineNum = 203;BA.debugLine="cur3.Position=0";
_cur3.setPosition((int) (0));
 //BA.debugLineNum = 205;BA.debugLine="lbl_right.Text=cur3.GetInt(\"count_right\")";
mostCurrent._lbl_right.setText(BA.ObjectToCharSequence(_cur3.GetInt("count_right")));
 //BA.debugLineNum = 206;BA.debugLine="lbl_wrong.Text=cur3.GetInt(\"count_wrong\")";
mostCurrent._lbl_wrong.setText(BA.ObjectToCharSequence(_cur3.GetInt("count_wrong")));
 //BA.debugLineNum = 208;BA.debugLine="lbl_qustion.Text=cur3.GetString(\"question\")";
mostCurrent._lbl_qustion.setText(BA.ObjectToCharSequence(_cur3.GetString("question")));
 //BA.debugLineNum = 209;BA.debugLine="lbl_path.Text=cur3.GetString(\"id\")";
mostCurrent._lbl_path.setText(BA.ObjectToCharSequence(_cur3.GetString("id")));
 //BA.debugLineNum = 210;BA.debugLine="lbl_count.Text=i+1";
mostCurrent._lbl_count.setText(BA.ObjectToCharSequence(_i+1));
 //BA.debugLineNum = 211;BA.debugLine="lbl_path.Text=cur3.GetString(\"book\")&\" / \"&cur3.";
mostCurrent._lbl_path.setText(BA.ObjectToCharSequence(_cur3.GetString("book")+" / "+_cur3.GetString("lessen")+" / "+"level "+BA.NumberToString(_cur3.GetInt("level"))));
 }
};
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _et_search_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Private Sub et_search_TextChanged (Old As String,";
 //BA.debugLineNum = 182;BA.debugLine="btn_search";
_btn_search();
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim words_list As List";
mostCurrent._words_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Dim words_list_id As List";
mostCurrent._words_list_id = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 18;BA.debugLine="Dim xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 19;BA.debugLine="Private pre_custom_lv As PreoptimizedCLV";
mostCurrent._pre_custom_lv = new ir.taravatgroup.mylitner.preoptimizedclv();
 //BA.debugLineNum = 21;BA.debugLine="Private custom_lv As CustomListView";
mostCurrent._custom_lv = new b4a.example3.customlistview();
 //BA.debugLineNum = 22;BA.debugLine="Private lbl_right As Label";
mostCurrent._lbl_right = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lbl_wrong As Label";
mostCurrent._lbl_wrong = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lbl_qustion As Label";
mostCurrent._lbl_qustion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lbl_count As Label";
mostCurrent._lbl_count = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lbl_path As Label";
mostCurrent._lbl_path = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private et_search As EditText";
mostCurrent._et_search = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btn_close_search As Button";
mostCurrent._btn_close_search = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lbl_count_all As Label";
mostCurrent._lbl_count_all = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private sp_book As Spinner";
mostCurrent._sp_book = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private sp_lessen As Spinner";
mostCurrent._sp_lessen = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private sp_level As Spinner";
mostCurrent._sp_level = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private lbl_title_page As Label";
mostCurrent._lbl_title_page = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lbl_filter_not As Label";
mostCurrent._lbl_filter_not = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lbl_filter_yes As Label";
mostCurrent._lbl_filter_yes = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btn_myliked As Button";
mostCurrent._btn_myliked = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_filter_not_click() throws Exception{
 //BA.debugLineNum = 276;BA.debugLine="Private Sub lbl_filter_not_Click";
 //BA.debugLineNum = 277;BA.debugLine="Main.end_page=\"home\"";
mostCurrent._main._end_page /*String*/  = "home";
 //BA.debugLineNum = 278;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="custom_lv.Clear";
mostCurrent._custom_lv._clear();
 //BA.debugLineNum = 280;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 281;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_filter_yes_click() throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Private Sub lbl_filter_yes_Click";
 //BA.debugLineNum = 244;BA.debugLine="Main.end_page=\"filter\"";
mostCurrent._main._end_page /*String*/  = "filter";
 //BA.debugLineNum = 245;BA.debugLine="Main.query=\"SELECT * FROM words WHERE book='\"& sp";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE book='"+mostCurrent._sp_book.getSelectedItem()+"' AND lessen='"+mostCurrent._sp_lessen.getSelectedItem()+"' AND level='"+mostCurrent._sp_level.getSelectedItem()+"'";
 //BA.debugLineNum = 247;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 248;BA.debugLine="custom_lv.Clear";
mostCurrent._custom_lv._clear();
 //BA.debugLineNum = 249;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 250;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 239;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 240;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static Object  _pre_custom_lv_hintrequested(int _index) throws Exception{
 //BA.debugLineNum = 215;BA.debugLine="Sub pre_custom_lv_HintRequested (Index As Int) As";
 //BA.debugLineNum = 217;BA.debugLine="Return words_list.Get(Index)";
if (true) return mostCurrent._words_list.Get(_index);
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _sp_book_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur4 = null;
int _i = 0;
 //BA.debugLineNum = 252;BA.debugLine="Private Sub sp_book_ItemClick (Position As Int, Va";
 //BA.debugLineNum = 254;BA.debugLine="sp_lessen.Clear";
mostCurrent._sp_lessen.Clear();
 //BA.debugLineNum = 255;BA.debugLine="Dim cur4 As Cursor";
_cur4 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 256;BA.debugLine="cur4 = Main.sq_db.ExecQuery(\"SELECT * FROM lessen";
_cur4 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM lessen WHERE book='"+BA.ObjectToString(_value)+"'")));
 //BA.debugLineNum = 257;BA.debugLine="For i=0 To cur4.RowCount-1";
{
final int step4 = 1;
final int limit4 = (int) (_cur4.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 258;BA.debugLine="cur4.Position=i";
_cur4.setPosition(_i);
 //BA.debugLineNum = 259;BA.debugLine="sp_lessen.Add(cur4.GetString(\"name\"))";
mostCurrent._sp_lessen.Add(_cur4.GetString("name"));
 }
};
 //BA.debugLineNum = 262;BA.debugLine="End Sub";
return "";
}
public static String  _sp_lessen_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur5 = null;
int _i = 0;
 //BA.debugLineNum = 264;BA.debugLine="Private Sub sp_lessen_ItemClick (Position As Int,";
 //BA.debugLineNum = 266;BA.debugLine="sp_level.Clear";
mostCurrent._sp_level.Clear();
 //BA.debugLineNum = 267;BA.debugLine="Dim cur5 As Cursor";
_cur5 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 268;BA.debugLine="cur5 = Main.sq_db.ExecQuery(\"SELECT * FROM level\"";
_cur5 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM level")));
 //BA.debugLineNum = 269;BA.debugLine="For i=0 To cur5.RowCount-1";
{
final int step4 = 1;
final int limit4 = (int) (_cur5.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 270;BA.debugLine="cur5.Position=i";
_cur5.setPosition(_i);
 //BA.debugLineNum = 271;BA.debugLine="sp_level.Add(cur5.GetString(\"name\"))";
mostCurrent._sp_level.Add(_cur5.GetString("name"));
 }
};
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
}
