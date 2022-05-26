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

public class setting_activity extends Activity implements B4AActivity{
	public static setting_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.setting_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (setting_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.setting_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.setting_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (setting_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (setting_activity) Resume **");
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
		return setting_activity.class;
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
            BA.LogInfo("** Activity (setting_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (setting_activity) Pause event (activity is not paused). **");
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
            setting_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (setting_activity) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _btn_color = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_font_app = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_size_font_app = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_times_levels = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_notification = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_lang_app = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_lang = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.collections.List _font_list = null;
public anywheresoftware.b4a.objects.collections.List _font_name_list = null;
public anywheresoftware.b4a.objects.collections.List _lang_list = null;
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
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 42;BA.debugLine="Activity.LoadLayout(\"setting_layout\")";
mostCurrent._activity.LoadLayout("setting_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 48;BA.debugLine="Label1.Text=Main.loc.Localize(\"s-1\")";
mostCurrent._label1.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-1")));
 //BA.debugLineNum = 49;BA.debugLine="lbl_times_levels.Text=Main.loc.Localize(\"s-2\")";
mostCurrent._lbl_times_levels.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-2")));
 //BA.debugLineNum = 50;BA.debugLine="lbl_notification.Text=Main.loc.Localize(\"s-3\")";
mostCurrent._lbl_notification.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-3")));
 //BA.debugLineNum = 51;BA.debugLine="lbl_lang.Text=Main.loc.Localize(\"s-4\")";
mostCurrent._lbl_lang.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-4")));
 //BA.debugLineNum = 52;BA.debugLine="Label3.Text=Main.loc.Localize(\"s-5\")";
mostCurrent._label3.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-5")));
 //BA.debugLineNum = 53;BA.debugLine="Label4.Text=Main.loc.Localize(\"s-6\")";
mostCurrent._label4.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-6")));
 //BA.debugLineNum = 54;BA.debugLine="Label5.Text=Main.loc.Localize(\"s-7\")";
mostCurrent._label5.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("s-7")));
 //BA.debugLineNum = 57;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 58;BA.debugLine="btn_color.Color=Main.app_color";
mostCurrent._btn_color.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 60;BA.debugLine="font_list.Initialize";
mostCurrent._font_list.Initialize();
 //BA.debugLineNum = 61;BA.debugLine="font_name_list.Initialize";
mostCurrent._font_name_list.Initialize();
 //BA.debugLineNum = 62;BA.debugLine="font_list.AddAll(Array As Object(\"ADastNevis.ttf\"";
mostCurrent._font_list.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("ADastNevis.ttf"),(Object)("BYekan.ttf"),(Object)("BNazanin.ttf"),(Object)("BKoodkBd.ttf"),(Object)("Ghalam-2_MRT.TTF"),(Object)("Jaleh_MRT.ttf")}));
 //BA.debugLineNum = 63;BA.debugLine="font_name_list.AddAll(Array As Object(\"DastNevis\"";
mostCurrent._font_name_list.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("DastNevis"),(Object)("Yekan"),(Object)("Nazanin"),(Object)("Koodak"),(Object)("Ghalam"),(Object)("Jaleh")}));
 //BA.debugLineNum = 65;BA.debugLine="sp_font_app.AddAll(font_name_list)";
mostCurrent._sp_font_app.AddAll(mostCurrent._font_name_list);
 //BA.debugLineNum = 66;BA.debugLine="sp_font_app.SelectedIndex=font_list.IndexOf(Main.";
mostCurrent._sp_font_app.setSelectedIndex(mostCurrent._font_list.IndexOf((Object)(mostCurrent._main._app_font_name /*String*/ )));
 //BA.debugLineNum = 69;BA.debugLine="sp_size_font_app.AddAll(Array As Object(\"12\",\"14\"";
mostCurrent._sp_size_font_app.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("12"),(Object)("14"),(Object)("16"),(Object)("18"),(Object)("20"),(Object)("22"),(Object)("24"),(Object)("28")}));
 //BA.debugLineNum = 70;BA.debugLine="sp_size_font_app.SelectedIndex=sp_size_font_app.I";
mostCurrent._sp_size_font_app.setSelectedIndex(mostCurrent._sp_size_font_app.IndexOf(BA.NumberToString(mostCurrent._main._app_font_size /*int*/ )));
 //BA.debugLineNum = 72;BA.debugLine="lang_list.Initialize";
mostCurrent._lang_list.Initialize();
 //BA.debugLineNum = 73;BA.debugLine="lang_list.AddAll(Array As Object(\"fa\",\"en\",\"ar\",\"";
mostCurrent._lang_list.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("fa"),(Object)("en"),(Object)("ar"),(Object)("tr"),(Object)("ind"),(Object)("far"),(Object)("ch")}));
 //BA.debugLineNum = 74;BA.debugLine="sp_lang_app.AddAll(Array As Object(\"فارسی\",\"Engli";
mostCurrent._sp_lang_app.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("فارسی"),(Object)("English"),(Object)("Arabic"),(Object)("Turkish"),(Object)("Hindi"),(Object)("French"),(Object)("Chinese")}));
 //BA.debugLineNum = 75;BA.debugLine="sp_lang_app.SelectedIndex=lang_list.IndexOf(Main.";
mostCurrent._sp_lang_app.setSelectedIndex(mostCurrent._lang_list.IndexOf((Object)(mostCurrent._main._app_lang /*String*/ )));
 //BA.debugLineNum = 78;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group23 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen23 = group23.getSize()
;int index23 = 0;
;
for (; index23 < groupLen23;index23++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group23.Get(index23)));
 //BA.debugLineNum = 79;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 80;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 81;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 83;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 85;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 88;BA.debugLine="Label1.TextSize=18";
mostCurrent._label1.setTextSize((float) (18));
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 107;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 108;BA.debugLine="If (pan_all.Visible=True)Then";
if ((mostCurrent._pan_all.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 109;BA.debugLine="pan_all_Click";
_pan_all_click();
 }else {
 //BA.debugLineNum = 111;BA.debugLine="btn_back_Click";
_btn_back_click();
 };
 //BA.debugLineNum = 114;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 116;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 102;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 103;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _btn_color_click() throws Exception{
 //BA.debugLineNum = 124;BA.debugLine="Private Sub btn_color_Click";
 //BA.debugLineNum = 125;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public static String  _color_1_click() throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Private Sub color_1_Click";
 //BA.debugLineNum = 185;BA.debugLine="set_app_color_db(0xFFE91D62)";
_set_app_color_db(BA.NumberToString(((int)0xffe91d62)));
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return "";
}
public static String  _color_10_click() throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Private Sub color_10_Click";
 //BA.debugLineNum = 149;BA.debugLine="set_app_color_db(0xFFE9B5AF)";
_set_app_color_db(BA.NumberToString(((int)0xffe9b5af)));
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
return "";
}
public static String  _color_11_click() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Private Sub color_11_Click";
 //BA.debugLineNum = 145;BA.debugLine="set_app_color_db(0xFF5AB5FF)";
_set_app_color_db(BA.NumberToString(((int)0xff5ab5ff)));
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _color_12_click() throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Private Sub color_12_Click";
 //BA.debugLineNum = 141;BA.debugLine="set_app_color_db(0xFFC37A61)";
_set_app_color_db(BA.NumberToString(((int)0xffc37a61)));
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _color_2_click() throws Exception{
 //BA.debugLineNum = 180;BA.debugLine="Private Sub color_2_Click";
 //BA.debugLineNum = 181;BA.debugLine="set_app_color_db(0xFF9C26B1)";
_set_app_color_db(BA.NumberToString(((int)0xff9c26b1)));
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public static String  _color_3_click() throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Private Sub color_3_Click";
 //BA.debugLineNum = 177;BA.debugLine="set_app_color_db(0xFF7686DF)";
_set_app_color_db(BA.NumberToString(((int)0xff7686df)));
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static String  _color_4_click() throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Private Sub color_4_Click";
 //BA.debugLineNum = 173;BA.debugLine="set_app_color_db(0xFF00C7E4)";
_set_app_color_db(BA.NumberToString(((int)0xff00c7e4)));
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
public static String  _color_5_click() throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Private Sub color_5_Click";
 //BA.debugLineNum = 169;BA.debugLine="set_app_color_db(0xFF069186)";
_set_app_color_db(BA.NumberToString(((int)0xff069186)));
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _color_6_click() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Private Sub color_6_Click";
 //BA.debugLineNum = 165;BA.debugLine="set_app_color_db(0xFF85E236)";
_set_app_color_db(BA.NumberToString(((int)0xff85e236)));
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _color_7_click() throws Exception{
 //BA.debugLineNum = 160;BA.debugLine="Private Sub color_7_Click";
 //BA.debugLineNum = 161;BA.debugLine="set_app_color_db(0xFFE4CB5F)";
_set_app_color_db(BA.NumberToString(((int)0xffe4cb5f)));
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _color_8_click() throws Exception{
 //BA.debugLineNum = 156;BA.debugLine="Private Sub color_8_Click";
 //BA.debugLineNum = 157;BA.debugLine="set_app_color_db(0xFFFF9800)";
_set_app_color_db(BA.NumberToString(((int)0xffff9800)));
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public static String  _color_9_click() throws Exception{
 //BA.debugLineNum = 152;BA.debugLine="Private Sub color_9_Click";
 //BA.debugLineNum = 153;BA.debugLine="set_app_color_db(0xFFFF5A50)";
_set_app_color_db(BA.NumberToString(((int)0xffff5a50)));
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btn_color As Panel";
mostCurrent._btn_color = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private sp_font_app As Spinner";
mostCurrent._sp_font_app = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private sp_size_font_app As Spinner";
mostCurrent._sp_size_font_app = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lbl_times_levels As Label";
mostCurrent._lbl_times_levels = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lbl_notification As Label";
mostCurrent._lbl_notification = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private sp_lang_app As Spinner";
mostCurrent._sp_lang_app = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lbl_lang As Label";
mostCurrent._lbl_lang = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim font_list As List";
mostCurrent._font_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 34;BA.debugLine="Dim font_name_list As List";
mostCurrent._font_name_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 35;BA.debugLine="Dim lang_list As List";
mostCurrent._lang_list = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_notification_click() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Private Sub lbl_notification_Click";
 //BA.debugLineNum = 230;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"main-21\"),Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-21")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_times_levels_click() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Private Sub lbl_times_levels_Click";
 //BA.debugLineNum = 213;BA.debugLine="StartActivity(setting_time_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._setting_time_activity.getObject()));
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 189;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return "";
}
public static String  _pan_colorpicker_click() throws Exception{
 //BA.debugLineNum = 192;BA.debugLine="Private Sub pan_colorpicker_Click";
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _set_app_color_db(String _color) throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub set_app_color_db (color As String)";
 //BA.debugLineNum = 129;BA.debugLine="Main.query=\" UPDATE setting SET option_value =\"&c";
mostCurrent._main._query /*String*/  = " UPDATE setting SET option_value ="+_color+" WHERE option_name= 'app_color' ";
 //BA.debugLineNum = 130;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 131;BA.debugLine="btn_color.Color=color";
mostCurrent._btn_color.setColor((int)(Double.parseDouble(_color)));
 //BA.debugLineNum = 132;BA.debugLine="Main.app_color=color";
mostCurrent._main._app_color /*int*/  = (int)(Double.parseDouble(_color));
 //BA.debugLineNum = 133;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 135;BA.debugLine="sp_lang_app.Clear";
mostCurrent._sp_lang_app.Clear();
 //BA.debugLineNum = 136;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return "";
}
public static String  _sp_font_app_itemclick(int _position,Object _value) throws Exception{
String _font = "";
 //BA.debugLineNum = 196;BA.debugLine="Private Sub sp_font_app_ItemClick (Position As Int";
 //BA.debugLineNum = 198;BA.debugLine="Dim font As String=font_list.Get(Position)";
_font = BA.ObjectToString(mostCurrent._font_list.Get(_position));
 //BA.debugLineNum = 200;BA.debugLine="Main.query=\" UPDATE setting SET option_value ='\"&";
mostCurrent._main._query /*String*/  = " UPDATE setting SET option_value ='"+_font+"' WHERE option_name= 'font' ";
 //BA.debugLineNum = 201;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 203;BA.debugLine="Main.app_font= Typeface.LoadFromAssets(font)";
mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/  = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets(_font)));
 //BA.debugLineNum = 204;BA.debugLine="Main.app_font_name=font";
mostCurrent._main._app_font_name /*String*/  = _font;
 //BA.debugLineNum = 206;BA.debugLine="sp_lang_app.Clear";
mostCurrent._sp_lang_app.Clear();
 //BA.debugLineNum = 207;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _sp_lang_app_itemclick(int _position,Object _value) throws Exception{
String _lang = "";
 //BA.debugLineNum = 235;BA.debugLine="Private Sub sp_lang_app_ItemClick (Position As Int";
 //BA.debugLineNum = 237;BA.debugLine="Dim lang As String=lang_list.Get(Position)";
_lang = BA.ObjectToString(mostCurrent._lang_list.Get(_position));
 //BA.debugLineNum = 239;BA.debugLine="Main.query=\" UPDATE setting SET option_value ='\"&";
mostCurrent._main._query /*String*/  = " UPDATE setting SET option_value ='"+_lang+"' WHERE option_name= 'app_lang' ";
 //BA.debugLineNum = 240;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 242;BA.debugLine="Main.app_lang= lang";
mostCurrent._main._app_lang /*String*/  = _lang;
 //BA.debugLineNum = 243;BA.debugLine="Main.loc.ForceLocale(lang)";
mostCurrent._main._loc /*b4a.example.localizator*/ ._forcelocale(_lang);
 //BA.debugLineNum = 244;BA.debugLine="If(lang <> \"fa\")Then";
if (((_lang).equals("fa") == false)) { 
 //BA.debugLineNum = 245;BA.debugLine="sp_size_font_app_ItemClick(1,\"14\")";
_sp_size_font_app_itemclick((int) (1),(Object)("14"));
 };
 //BA.debugLineNum = 248;BA.debugLine="sp_lang_app.Clear";
mostCurrent._sp_lang_app.Clear();
 //BA.debugLineNum = 249;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 250;BA.debugLine="End Sub";
return "";
}
public static String  _sp_size_font_app_itemclick(int _position,Object _value) throws Exception{
int _size = 0;
 //BA.debugLineNum = 216;BA.debugLine="Private Sub sp_size_font_app_ItemClick (Position A";
 //BA.debugLineNum = 217;BA.debugLine="Dim size As Int=Value";
_size = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 219;BA.debugLine="Main.query=\" UPDATE setting SET option_value =\"&s";
mostCurrent._main._query /*String*/  = " UPDATE setting SET option_value ="+BA.NumberToString(_size)+" WHERE option_name= 'font_size' ";
 //BA.debugLineNum = 220;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 222;BA.debugLine="Main.app_font_size= size";
mostCurrent._main._app_font_size /*int*/  = _size;
 //BA.debugLineNum = 224;BA.debugLine="sp_lang_app.Clear";
mostCurrent._sp_lang_app.Clear();
 //BA.debugLineNum = 225;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
}
