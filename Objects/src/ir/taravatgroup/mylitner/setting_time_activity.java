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

public class setting_time_activity extends Activity implements B4AActivity{
	public static setting_time_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.setting_time_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (setting_time_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.setting_time_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.setting_time_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (setting_time_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (setting_time_activity) Resume **");
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
		return setting_time_activity.class;
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
            BA.LogInfo("** Activity (setting_time_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (setting_time_activity) Pause event (activity is not paused). **");
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
            setting_time_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (setting_time_activity) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.LabelWrapper _level_current = null;
public static int _current_level_name = 0;
public anywheresoftware.b4a.objects.EditTextWrapper _et_curent_level_h = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_curent_level_d = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_curent_level_m = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_curent_level_y = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_show_time = null;
public b4a.example3.customlistview _custom_lv = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_avail_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_levels = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _skb_curent_level_y = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _skb_curent_level_m = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _skb_curent_level_d = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _skb_curent_level_h = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_set_time = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_save_count_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_save = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_cancel = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.mylitner.main _main = null;
public ir.taravatgroup.mylitner.book_activity _book_activity = null;
public ir.taravatgroup.mylitner.lessen_activity _lessen_activity = null;
public ir.taravatgroup.mylitner.level_activity _level_activity = null;
public ir.taravatgroup.mylitner.all_words_activity _all_words_activity = null;
public ir.taravatgroup.mylitner.store_activity _store_activity = null;
public ir.taravatgroup.mylitner.add_voice_activity _add_voice_activity = null;
public ir.taravatgroup.mylitner.all_exam_activity _all_exam_activity = null;
public ir.taravatgroup.mylitner.dictionary_activity _dictionary_activity = null;
public ir.taravatgroup.mylitner.exam_activity _exam_activity = null;
public ir.taravatgroup.mylitner.help_activity _help_activity = null;
public ir.taravatgroup.mylitner.init_exam_activity _init_exam_activity = null;
public ir.taravatgroup.mylitner.result_activity _result_activity = null;
public ir.taravatgroup.mylitner.setting_activity _setting_activity = null;
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
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur1 = null;
int _i = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 53;BA.debugLine="Activity.LoadLayout(\"setting_time_layout\")";
mostCurrent._activity.LoadLayout("setting_time_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 55;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 59;BA.debugLine="Main.time_list_level.Clear";
mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .Clear();
 //BA.debugLineNum = 60;BA.debugLine="Main.level_list_name.Clear";
mostCurrent._main._level_list_name /*anywheresoftware.b4a.objects.collections.List*/ .Clear();
 //BA.debugLineNum = 63;BA.debugLine="Dim cur1 As Cursor";
_cur1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 64;BA.debugLine="cur1 = Main.sq_db.ExecQuery(\"SELECT * FROM level\"";
_cur1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM level")));
 //BA.debugLineNum = 65;BA.debugLine="lbl_count_levels.Text=cur1.RowCount";
mostCurrent._lbl_count_levels.setText(BA.ObjectToCharSequence(_cur1.getRowCount()));
 //BA.debugLineNum = 66;BA.debugLine="lbl_count_levels.Tag=cur1.RowCount";
mostCurrent._lbl_count_levels.setTag((Object)(_cur1.getRowCount()));
 //BA.debugLineNum = 68;BA.debugLine="For i=0 To cur1.RowCount-1";
{
final int step9 = 1;
final int limit9 = (int) (_cur1.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
 //BA.debugLineNum = 69;BA.debugLine="cur1.Position=i";
_cur1.setPosition(_i);
 //BA.debugLineNum = 72;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 73;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, custom_lv.AsView.Wi";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),mostCurrent._custom_lv._asview().getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 76;BA.debugLine="p.LoadLayout(\"item_level\")";
_p.LoadLayout("item_level",mostCurrent.activityBA);
 //BA.debugLineNum = 77;BA.debugLine="lbl_count_level.Width=200dip";
mostCurrent._lbl_count_level.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200)));
 //BA.debugLineNum = 79;BA.debugLine="lbl_level.Text=cur1.GetString(\"name\")";
mostCurrent._lbl_level.setText(BA.ObjectToCharSequence(_cur1.GetString("name")));
 //BA.debugLineNum = 81;BA.debugLine="lbl_count_level.Text=cur1.GetString(\"tozihat\")";
mostCurrent._lbl_count_level.setText(BA.ObjectToCharSequence(_cur1.GetString("tozihat")));
 //BA.debugLineNum = 82;BA.debugLine="custom_lv.Add(p, cur1.GetString(\"name\") )";
mostCurrent._custom_lv._add(_p,(Object)(_cur1.GetString("name")));
 //BA.debugLineNum = 84;BA.debugLine="Main.time_list_level.Add(cur1.GetString(\"time\"))";
mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .Add((Object)(_cur1.GetString("time")));
 //BA.debugLineNum = 85;BA.debugLine="Main.level_list_name.Add(cur1.GetString(\"name\"))";
mostCurrent._main._level_list_name /*anywheresoftware.b4a.objects.collections.List*/ .Add((Object)(_cur1.GetString("name")));
 }
};
 //BA.debugLineNum = 89;BA.debugLine="skb_curent_level_h.Max=23";
mostCurrent._skb_curent_level_h.setMax((int) (23));
 //BA.debugLineNum = 90;BA.debugLine="skb_curent_level_d.Max=29";
mostCurrent._skb_curent_level_d.setMax((int) (29));
 //BA.debugLineNum = 91;BA.debugLine="skb_curent_level_m.Max=11";
mostCurrent._skb_curent_level_m.setMax((int) (11));
 //BA.debugLineNum = 92;BA.debugLine="skb_curent_level_y.Max=10";
mostCurrent._skb_curent_level_y.setMax((int) (10));
 //BA.debugLineNum = 96;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group25 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen25 = group25.getSize()
;int index25 = 0;
;
for (; index25 < groupLen25;index25++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group25.Get(index25)));
 //BA.debugLineNum = 97;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 98;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 99;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 101;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 102;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 103;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 106;BA.debugLine="Label1.TextSize=18";
mostCurrent._label1.setTextSize((float) (18));
 //BA.debugLineNum = 107;BA.debugLine="Label3.TextSize=14";
mostCurrent._label3.setTextSize((float) (14));
 //BA.debugLineNum = 108;BA.debugLine="Label4.TextSize=14";
mostCurrent._label4.setTextSize((float) (14));
 //BA.debugLineNum = 109;BA.debugLine="Label5.TextSize=14";
mostCurrent._label5.setTextSize((float) (14));
 //BA.debugLineNum = 110;BA.debugLine="Label6.TextSize=14";
mostCurrent._label6.setTextSize((float) (14));
 //BA.debugLineNum = 111;BA.debugLine="et_curent_level_h.TextSize=14";
mostCurrent._et_curent_level_h.setTextSize((float) (14));
 //BA.debugLineNum = 112;BA.debugLine="et_curent_level_d.TextSize=14";
mostCurrent._et_curent_level_d.setTextSize((float) (14));
 //BA.debugLineNum = 113;BA.debugLine="et_curent_level_m.TextSize=14";
mostCurrent._et_curent_level_m.setTextSize((float) (14));
 //BA.debugLineNum = 114;BA.debugLine="et_curent_level_y.TextSize=14";
mostCurrent._et_curent_level_y.setTextSize((float) (14));
 //BA.debugLineNum = 116;BA.debugLine="Label1.Text=Main.loc.Localize(\"s-t-1\")";
mostCurrent._label1.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-1")));
 //BA.debugLineNum = 117;BA.debugLine="Label2.Text=Main.loc.Localize(\"s-t-2\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-2")));
 //BA.debugLineNum = 118;BA.debugLine="Label3.Text=Main.loc.Localize(\"s-t-3\")";
mostCurrent._label3.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-3")));
 //BA.debugLineNum = 119;BA.debugLine="Label4.Text=Main.loc.Localize(\"s-t-4\")";
mostCurrent._label4.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-4")));
 //BA.debugLineNum = 120;BA.debugLine="Label5.Text=Main.loc.Localize(\"s-t-5\")";
mostCurrent._label5.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-5")));
 //BA.debugLineNum = 121;BA.debugLine="Label6.Text=Main.loc.Localize(\"s-t-6\")";
mostCurrent._label6.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-6")));
 //BA.debugLineNum = 123;BA.debugLine="btn_save.Text=Main.loc.Localize(\"book-5\")";
mostCurrent._btn_save.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-5")));
 //BA.debugLineNum = 124;BA.debugLine="btn_cancel.Text=Main.loc.Localize(\"book-6\")";
mostCurrent._btn_cancel.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-6")));
 //BA.debugLineNum = 125;BA.debugLine="btn_save_count_level.Text=Main.loc.Localize(\"book";
mostCurrent._btn_save_count_level.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-5")));
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 266;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 267;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 268;BA.debugLine="btn_back_Click";
_btn_back_click();
 //BA.debugLineNum = 269;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 271;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 137;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 138;BA.debugLine="StartActivity(setting_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._setting_activity.getObject()));
 //BA.debugLineNum = 139;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _btn_cancel_click() throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Private Sub btn_cancel_Click";
 //BA.debugLineNum = 192;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _btn_down_arrow_click() throws Exception{
int _b = 0;
 //BA.debugLineNum = 279;BA.debugLine="Private Sub btn_down_arrow_Click";
 //BA.debugLineNum = 280;BA.debugLine="Dim b As Int=lbl_count_levels.Text";
_b = (int)(Double.parseDouble(mostCurrent._lbl_count_levels.getText()));
 //BA.debugLineNum = 281;BA.debugLine="If (b <=3) Then";
if ((_b<=3)) { 
 //BA.debugLineNum = 282;BA.debugLine="lbl_count_levels.Text=3";
mostCurrent._lbl_count_levels.setText(BA.ObjectToCharSequence(3));
 //BA.debugLineNum = 283;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"s-t-11\"),Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-11")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 285;BA.debugLine="lbl_count_levels.Text=b-1";
mostCurrent._lbl_count_levels.setText(BA.ObjectToCharSequence(_b-1));
 };
 //BA.debugLineNum = 288;BA.debugLine="End Sub";
return "";
}
public static String  _btn_save_click() throws Exception{
int _tim_h = 0;
int _tim_d = 0;
int _tim_m = 0;
int _tim_y = 0;
long _tim1 = 0L;
long _tim2 = 0L;
long _end_tim = 0L;
String _show_time = "";
boolean _dublicat_time = false;
int _j = 0;
 //BA.debugLineNum = 146;BA.debugLine="Private Sub btn_save_Click";
 //BA.debugLineNum = 148;BA.debugLine="Dim tim_h As Int=et_curent_level_h.Text";
_tim_h = (int)(Double.parseDouble(mostCurrent._et_curent_level_h.getText()));
 //BA.debugLineNum = 149;BA.debugLine="Dim tim_d As Int=et_curent_level_d.Text";
_tim_d = (int)(Double.parseDouble(mostCurrent._et_curent_level_d.getText()));
 //BA.debugLineNum = 150;BA.debugLine="Dim tim_m As Int=et_curent_level_m.Text";
_tim_m = (int)(Double.parseDouble(mostCurrent._et_curent_level_m.getText()));
 //BA.debugLineNum = 151;BA.debugLine="Dim tim_y As Int=et_curent_level_y.Text";
_tim_y = (int)(Double.parseDouble(mostCurrent._et_curent_level_y.getText()));
 //BA.debugLineNum = 153;BA.debugLine="Dim tim1 As Long=DateUtils.SetDateAndTime(2021,1,";
_tim1 = mostCurrent._dateutils._setdateandtime(mostCurrent.activityBA,(int) (2021),(int) (1),(int) (1),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 154;BA.debugLine="Dim tim2 As Long=DateUtils.SetDateAndTime(2021+ti";
_tim2 = mostCurrent._dateutils._setdateandtime(mostCurrent.activityBA,(int) (2021+_tim_y),(int) (1+_tim_m),(int) (1+_tim_d),_tim_h,(int) (0),(int) (0));
 //BA.debugLineNum = 155;BA.debugLine="Dim end_tim As Long=tim2-tim1";
_end_tim = (long) (_tim2-_tim1);
 //BA.debugLineNum = 157;BA.debugLine="Dim show_time As String=\"\"";
_show_time = "";
 //BA.debugLineNum = 158;BA.debugLine="If (tim_y <> 0) Then";
if ((_tim_y!=0)) { 
 //BA.debugLineNum = 159;BA.debugLine="show_time=tim_y& Main.loc.Localize(\"s-t-6\")";
_show_time = BA.NumberToString(_tim_y)+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-6");
 };
 //BA.debugLineNum = 161;BA.debugLine="If (tim_m <> 0) Then";
if ((_tim_m!=0)) { 
 //BA.debugLineNum = 162;BA.debugLine="show_time=show_time &tim_m&Main.loc.Localize(\"s-";
_show_time = _show_time+BA.NumberToString(_tim_m)+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-5");
 };
 //BA.debugLineNum = 164;BA.debugLine="If (tim_d <> 0) Then";
if ((_tim_d!=0)) { 
 //BA.debugLineNum = 165;BA.debugLine="show_time=show_time &tim_d&Main.loc.Localize(\"s-";
_show_time = _show_time+BA.NumberToString(_tim_d)+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-4");
 };
 //BA.debugLineNum = 167;BA.debugLine="If (tim_h <> 0) Then";
if ((_tim_h!=0)) { 
 //BA.debugLineNum = 168;BA.debugLine="show_time=show_time &tim_h&Main.loc.Localize(\"s-";
_show_time = _show_time+BA.NumberToString(_tim_h)+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-3");
 };
 //BA.debugLineNum = 171;BA.debugLine="Dim dublicat_time As Boolean=False";
_dublicat_time = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 172;BA.debugLine="For j=0 To Main.time_list_level.Size-1";
{
final int step22 = 1;
final int limit22 = (int) (mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .getSize()-1);
_j = (int) (0) ;
for (;_j <= limit22 ;_j = _j + step22 ) {
 //BA.debugLineNum = 173;BA.debugLine="If(end_tim==Main.time_list_level.Get(j))Then";
if ((_end_tim==(double)(BA.ObjectToNumber(mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .Get(_j))))) { 
 //BA.debugLineNum = 174;BA.debugLine="dublicat_time=True";
_dublicat_time = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 178;BA.debugLine="If(dublicat_time==True) Then";
if ((_dublicat_time==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 179;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"s-t-8\"),Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-8")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 181;BA.debugLine="Main.query=\" UPDATE level SET time = ? ,tozihat";
mostCurrent._main._query /*String*/  = " UPDATE level SET time = ? ,tozihat = ? WHERE name= ?";
 //BA.debugLineNum = 182;BA.debugLine="Main.sq_db.ExecNonQuery2(Main.query,Array As Obj";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._main._query /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_end_tim),(Object)(_show_time),(Object)(_current_level_name)}));
 //BA.debugLineNum = 183;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 184;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _btn_save_count_level_click() throws Exception{
int _tedad = 0;
int _num1 = 0;
int _num2 = 0;
long _last_tim = 0L;
int _i = 0;
 //BA.debugLineNum = 195;BA.debugLine="Private Sub btn_save_count_level_Click";
 //BA.debugLineNum = 198;BA.debugLine="Dim tedad As Int=0";
_tedad = (int) (0);
 //BA.debugLineNum = 199;BA.debugLine="Dim num1 As Int=lbl_count_levels.Tag";
_num1 = (int)(BA.ObjectToNumber(mostCurrent._lbl_count_levels.getTag()));
 //BA.debugLineNum = 200;BA.debugLine="Dim num2 As Int=lbl_count_levels.Text";
_num2 = (int)(Double.parseDouble(mostCurrent._lbl_count_levels.getText()));
 //BA.debugLineNum = 201;BA.debugLine="Dim last_tim As Long=Main.time_list_level.Get(num";
_last_tim = BA.ObjectToLongNumber(mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .Get((int) (_num1-1)));
 //BA.debugLineNum = 204;BA.debugLine="tedad=num1-num2";
_tedad = (int) (_num1-_num2);
 //BA.debugLineNum = 206;BA.debugLine="If (tedad>0)Then";
if ((_tedad>0)) { 
 //BA.debugLineNum = 208;BA.debugLine="For i=1 To tedad";
{
final int step7 = 1;
final int limit7 = _tedad;
_i = (int) (1) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 209;BA.debugLine="Main.query=\"DELETE FROM level WHERE id = (SELEC";
mostCurrent._main._query /*String*/  = "DELETE FROM level WHERE id = (SELECT MAX(id) FROM level)";
 //BA.debugLineNum = 210;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 }
};
 //BA.debugLineNum = 212;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"s-t-9\"),Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-9")),anywheresoftware.b4a.keywords.Common.False);
 }else if((_tedad<0)) { 
 //BA.debugLineNum = 217;BA.debugLine="tedad=tedad*(-1)";
_tedad = (int) (_tedad*(-1));
 //BA.debugLineNum = 219;BA.debugLine="For i=1 To tedad";
{
final int step14 = 1;
final int limit14 = _tedad;
_i = (int) (1) ;
for (;_i <= limit14 ;_i = _i + step14 ) {
 //BA.debugLineNum = 220;BA.debugLine="Main.query=\"INSERT INTO level (name, time, tozi";
mostCurrent._main._query /*String*/  = "INSERT INTO level (name, time, tozihat) VALUES ('"+BA.NumberToString((_num1+_i))+"','"+BA.NumberToString((_last_tim*2*_i))+"','')";
 //BA.debugLineNum = 221;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 }
};
 //BA.debugLineNum = 223;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"s-t-9\"),Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-9")),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 226;BA.debugLine="Main.last_level=Main.level_list_name.Get(Main.lev";
mostCurrent._main._last_level /*int*/  = (int)(BA.ObjectToNumber(mostCurrent._main._level_list_name /*anywheresoftware.b4a.objects.collections.List*/ .Get((int) (mostCurrent._main._level_list_name /*anywheresoftware.b4a.objects.collections.List*/ .getSize()-1))));
 //BA.debugLineNum = 227;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _btn_up_arrow_click() throws Exception{
int _a = 0;
 //BA.debugLineNum = 290;BA.debugLine="Private Sub btn_up_arrow_Click";
 //BA.debugLineNum = 291;BA.debugLine="Dim a As Int= lbl_count_levels.Text";
_a = (int)(Double.parseDouble(mostCurrent._lbl_count_levels.getText()));
 //BA.debugLineNum = 292;BA.debugLine="lbl_count_levels.Text=a+1";
mostCurrent._lbl_count_levels.setText(BA.ObjectToCharSequence(_a+1));
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _custom_lv_itemclick(int _index,Object _value) throws Exception{
long _base = 0L;
long _hh = 0L;
b4a.example.dateutils._period _p = null;
 //BA.debugLineNum = 231;BA.debugLine="Private Sub custom_lv_ItemClick (Index As Int, Val";
 //BA.debugLineNum = 232;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 233;BA.debugLine="current_level_name=Value";
_current_level_name = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 235;BA.debugLine="level_current.Text=Main.loc.Localize(\"s-t-7\")&\" :";
mostCurrent._level_current.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-7")+" : "+BA.ObjectToString(_value)));
 //BA.debugLineNum = 238;BA.debugLine="Dim base As Long=DateUtils.SetDateAndTime(2021,1,";
_base = mostCurrent._dateutils._setdateandtime(mostCurrent.activityBA,(int) (2021),(int) (1),(int) (1),(int) (0),(int) (0),(int) (0));
 //BA.debugLineNum = 242;BA.debugLine="Dim hh As Long=base+ (Main.time_list_level.Get(In";
_hh = (long) (_base+(double)(BA.ObjectToNumber((mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .Get(_index)))));
 //BA.debugLineNum = 243;BA.debugLine="Dim p As Period= DateUtils.PeriodBetween(base,hh)";
_p = mostCurrent._dateutils._periodbetween(mostCurrent.activityBA,_base,_hh);
 //BA.debugLineNum = 245;BA.debugLine="et_curent_level_h.Text=p.Hours";
mostCurrent._et_curent_level_h.setText(BA.ObjectToCharSequence(_p.Hours));
 //BA.debugLineNum = 246;BA.debugLine="et_curent_level_d.Text=p.Days";
mostCurrent._et_curent_level_d.setText(BA.ObjectToCharSequence(_p.Days));
 //BA.debugLineNum = 247;BA.debugLine="et_curent_level_m.Text=p.Months";
mostCurrent._et_curent_level_m.setText(BA.ObjectToCharSequence(_p.Months));
 //BA.debugLineNum = 248;BA.debugLine="et_curent_level_y.Text=p.Years";
mostCurrent._et_curent_level_y.setText(BA.ObjectToCharSequence(_p.Years));
 //BA.debugLineNum = 250;BA.debugLine="skb_curent_level_h.Value=p.Hours";
mostCurrent._skb_curent_level_h.setValue(_p.Hours);
 //BA.debugLineNum = 251;BA.debugLine="skb_curent_level_d.Value=p.Days";
mostCurrent._skb_curent_level_d.setValue(_p.Days);
 //BA.debugLineNum = 252;BA.debugLine="skb_curent_level_m.Value=p.Months";
mostCurrent._skb_curent_level_m.setValue(_p.Months);
 //BA.debugLineNum = 253;BA.debugLine="skb_curent_level_y.Value=p.Years";
mostCurrent._skb_curent_level_y.setValue(_p.Years);
 //BA.debugLineNum = 255;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 256;BA.debugLine="If (current_level_name==1)Then";
if ((_current_level_name==1)) { 
 //BA.debugLineNum = 257;BA.debugLine="btn_cancel_Click";
_btn_cancel_click();
 //BA.debugLineNum = 258;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"s-t-10\"),Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-10")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 261;BA.debugLine="Main.sq_db.ExecNonQuery(\" UPDATE level SET tozih";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(" UPDATE level SET tozihat ='"+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-12")+"' WHERE name= 1");
 };
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _et_curent_level_d_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 303;BA.debugLine="Private Sub et_curent_level_d_TextChanged (Old As";
 //BA.debugLineNum = 304;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 305;BA.debugLine="End Sub";
return "";
}
public static String  _et_curent_level_h_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 307;BA.debugLine="Private Sub et_curent_level_h_TextChanged (Old As";
 //BA.debugLineNum = 308;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _et_curent_level_m_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 299;BA.debugLine="Private Sub et_curent_level_m_TextChanged (Old As";
 //BA.debugLineNum = 300;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _et_curent_level_y_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Private Sub et_curent_level_y_TextChanged (Old As";
 //BA.debugLineNum = 296;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private level_current As Label";
mostCurrent._level_current = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim current_level_name As Int";
_current_level_name = 0;
 //BA.debugLineNum = 19;BA.debugLine="Private et_curent_level_h As EditText";
mostCurrent._et_curent_level_h = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private et_curent_level_d As EditText";
mostCurrent._et_curent_level_d = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private et_curent_level_m As EditText";
mostCurrent._et_curent_level_m = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private et_curent_level_y As EditText";
mostCurrent._et_curent_level_y = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lbl_show_time As Label";
mostCurrent._lbl_show_time = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private custom_lv As CustomListView";
mostCurrent._custom_lv = new b4a.example3.customlistview();
 //BA.debugLineNum = 28;BA.debugLine="Dim xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 30;BA.debugLine="Private lbl_level As Label";
mostCurrent._lbl_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lbl_avail_level As Label";
mostCurrent._lbl_avail_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lbl_count_level As Label";
mostCurrent._lbl_count_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lbl_count_levels As Label";
mostCurrent._lbl_count_levels = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private skb_curent_level_y As SeekBar";
mostCurrent._skb_curent_level_y = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private skb_curent_level_m As SeekBar";
mostCurrent._skb_curent_level_m = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private skb_curent_level_d As SeekBar";
mostCurrent._skb_curent_level_d = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private skb_curent_level_h As SeekBar";
mostCurrent._skb_curent_level_h = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private pan_set_time As Panel";
mostCurrent._pan_set_time = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btn_save_count_level As Button";
mostCurrent._btn_save_count_level = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private btn_save As Button";
mostCurrent._btn_save = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private btn_cancel As Button";
mostCurrent._btn_cancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 143;BA.debugLine="btn_cancel_Click";
_btn_cancel_click();
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static boolean  _pan_set_time_click() throws Exception{
 //BA.debugLineNum = 275;BA.debugLine="Private Sub pan_set_time_Click As Boolean";
 //BA.debugLineNum = 276;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 277;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _show_labl_time_update() throws Exception{
int _tim_h = 0;
int _tim_d = 0;
int _tim_m = 0;
int _tim_y = 0;
String _show_time = "";
 //BA.debugLineNum = 310;BA.debugLine="Sub show_labl_time_update";
 //BA.debugLineNum = 312;BA.debugLine="Dim tim_h As Int=0";
_tim_h = (int) (0);
 //BA.debugLineNum = 313;BA.debugLine="Dim tim_d As Int=0";
_tim_d = (int) (0);
 //BA.debugLineNum = 314;BA.debugLine="Dim tim_m As Int=0";
_tim_m = (int) (0);
 //BA.debugLineNum = 315;BA.debugLine="Dim tim_y As Int=0";
_tim_y = (int) (0);
 //BA.debugLineNum = 318;BA.debugLine="If(et_curent_level_h.Text==Null)Then";
if ((mostCurrent._et_curent_level_h.getText()== null)) { 
 //BA.debugLineNum = 319;BA.debugLine="tim_h=0";
_tim_h = (int) (0);
 }else {
 //BA.debugLineNum = 321;BA.debugLine="tim_h = et_curent_level_h.Text";
_tim_h = (int)(Double.parseDouble(mostCurrent._et_curent_level_h.getText()));
 };
 //BA.debugLineNum = 323;BA.debugLine="If(et_curent_level_d.Text==Null)Then";
if ((mostCurrent._et_curent_level_d.getText()== null)) { 
 //BA.debugLineNum = 324;BA.debugLine="tim_d=0";
_tim_d = (int) (0);
 }else {
 //BA.debugLineNum = 326;BA.debugLine="tim_d = et_curent_level_d.Text";
_tim_d = (int)(Double.parseDouble(mostCurrent._et_curent_level_d.getText()));
 };
 //BA.debugLineNum = 328;BA.debugLine="If(et_curent_level_m.Text==Null)Then";
if ((mostCurrent._et_curent_level_m.getText()== null)) { 
 //BA.debugLineNum = 329;BA.debugLine="tim_m=0";
_tim_m = (int) (0);
 }else {
 //BA.debugLineNum = 331;BA.debugLine="tim_m = et_curent_level_m.Text";
_tim_m = (int)(Double.parseDouble(mostCurrent._et_curent_level_m.getText()));
 };
 //BA.debugLineNum = 333;BA.debugLine="If(et_curent_level_y.Text==Null)Then";
if ((mostCurrent._et_curent_level_y.getText()== null)) { 
 //BA.debugLineNum = 334;BA.debugLine="tim_y=0";
_tim_y = (int) (0);
 }else {
 //BA.debugLineNum = 336;BA.debugLine="tim_y = et_curent_level_y.Text";
_tim_y = (int)(Double.parseDouble(mostCurrent._et_curent_level_y.getText()));
 };
 //BA.debugLineNum = 341;BA.debugLine="Dim show_time As String=\"\"";
_show_time = "";
 //BA.debugLineNum = 342;BA.debugLine="If (tim_y <> 0) Then";
if ((_tim_y!=0)) { 
 //BA.debugLineNum = 343;BA.debugLine="show_time=tim_y&\" \"& Main.loc.Localize(\"s-t-6\")&";
_show_time = BA.NumberToString(_tim_y)+" "+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-6")+"-";
 };
 //BA.debugLineNum = 345;BA.debugLine="If (tim_m <> 0) Then";
if ((_tim_m!=0)) { 
 //BA.debugLineNum = 346;BA.debugLine="show_time=show_time &tim_m&\" \"&Main.loc.Localize";
_show_time = _show_time+BA.NumberToString(_tim_m)+" "+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-5")+"-";
 };
 //BA.debugLineNum = 348;BA.debugLine="If (tim_d <> 0) Then";
if ((_tim_d!=0)) { 
 //BA.debugLineNum = 349;BA.debugLine="show_time=show_time &tim_d&\" \"&Main.loc.Localize";
_show_time = _show_time+BA.NumberToString(_tim_d)+" "+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-4")+"-";
 };
 //BA.debugLineNum = 351;BA.debugLine="If (tim_h <> 0) Then";
if ((_tim_h!=0)) { 
 //BA.debugLineNum = 352;BA.debugLine="show_time=show_time &tim_h&\" \"&Main.loc.Localize";
_show_time = _show_time+BA.NumberToString(_tim_h)+" "+mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-t-3")+" ";
 };
 //BA.debugLineNum = 354;BA.debugLine="lbl_show_time.Text=show_time";
mostCurrent._lbl_show_time.setText(BA.ObjectToCharSequence(_show_time));
 //BA.debugLineNum = 356;BA.debugLine="End Sub";
return "";
}
public static String  _skb_curent_level_d_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 363;BA.debugLine="Private Sub skb_curent_level_d_ValueChanged (Value";
 //BA.debugLineNum = 364;BA.debugLine="et_curent_level_d.Text=Value";
mostCurrent._et_curent_level_d.setText(BA.ObjectToCharSequence(_value));
 //BA.debugLineNum = 365;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static String  _skb_curent_level_h_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 358;BA.debugLine="Private Sub skb_curent_level_h_ValueChanged (Value";
 //BA.debugLineNum = 359;BA.debugLine="et_curent_level_h.Text=Value";
mostCurrent._et_curent_level_h.setText(BA.ObjectToCharSequence(_value));
 //BA.debugLineNum = 360;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 361;BA.debugLine="End Sub";
return "";
}
public static String  _skb_curent_level_m_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 368;BA.debugLine="Private Sub skb_curent_level_m_ValueChanged (Value";
 //BA.debugLineNum = 369;BA.debugLine="et_curent_level_m.Text=Value";
mostCurrent._et_curent_level_m.setText(BA.ObjectToCharSequence(_value));
 //BA.debugLineNum = 370;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 371;BA.debugLine="End Sub";
return "";
}
public static String  _skb_curent_level_y_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 373;BA.debugLine="Private Sub skb_curent_level_y_ValueChanged (Value";
 //BA.debugLineNum = 374;BA.debugLine="et_curent_level_y.Text=Value";
mostCurrent._et_curent_level_y.setText(BA.ObjectToCharSequence(_value));
 //BA.debugLineNum = 375;BA.debugLine="show_labl_time_update";
_show_labl_time_update();
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return "";
}
}
