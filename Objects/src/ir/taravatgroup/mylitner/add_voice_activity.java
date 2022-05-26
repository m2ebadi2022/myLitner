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

public class add_voice_activity extends Activity implements B4AActivity{
	public static add_voice_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.add_voice_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (add_voice_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.add_voice_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.add_voice_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (add_voice_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (add_voice_activity) Resume **");
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
		return add_voice_activity.class;
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
            BA.LogInfo("** Activity (add_voice_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (add_voice_activity) Pause event (activity is not paused). **");
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
            add_voice_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (add_voice_activity) Resume **");
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
public com.brandsum.speechrecognitionnoui _sr = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public static int _index = 0;
public static int _count_added = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_qustion_v = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_answer_v = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_save_next = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progbar1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progbar2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_ques = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_answ = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_edit_save = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_lang_ques = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_lang_answ = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_added = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_edit_pan = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_synonym_v = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_codeing_v = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_lang_codeing = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_lang_syno = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progbar4 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progbar3 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_synonym = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_coding = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.mylitner.main _main = null;
public ir.taravatgroup.mylitner.book_activity _book_activity = null;
public ir.taravatgroup.mylitner.lessen_activity _lessen_activity = null;
public ir.taravatgroup.mylitner.level_activity _level_activity = null;
public ir.taravatgroup.mylitner.all_words_activity _all_words_activity = null;
public ir.taravatgroup.mylitner.store_activity _store_activity = null;
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
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 46;BA.debugLine="Activity.LoadLayout(\"add_voice_layout\")";
mostCurrent._activity.LoadLayout("add_voice_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 47;BA.debugLine="sr.Initialize(\"sr\",Me)";
mostCurrent._sr._initialize(processBA,"sr",add_voice_activity.getObject());
 //BA.debugLineNum = 48;BA.debugLine="sr.NoRecognizerBeep = True";
mostCurrent._sr._setnorecognizerbeep(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 49;BA.debugLine="check_internet";
_check_internet();
 //BA.debugLineNum = 51;BA.debugLine="sp_lang_ques.AddAll(Array As Object(\"en\",\"fa\",\"ar";
mostCurrent._sp_lang_ques.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("en"),(Object)("fa"),(Object)("ar"),(Object)("tr")}));
 //BA.debugLineNum = 52;BA.debugLine="sp_lang_answ.AddAll(Array As Object(\"fa\",\"en\",\"ar";
mostCurrent._sp_lang_answ.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("fa"),(Object)("en"),(Object)("ar"),(Object)("tr")}));
 //BA.debugLineNum = 53;BA.debugLine="sp_lang_syno.AddAll(Array As Object(\"en\",\"fa\",\"ar";
mostCurrent._sp_lang_syno.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("en"),(Object)("fa"),(Object)("ar"),(Object)("tr")}));
 //BA.debugLineNum = 54;BA.debugLine="sp_lang_codeing.AddAll(Array As Object(\"fa\",\"en\",";
mostCurrent._sp_lang_codeing.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("fa"),(Object)("en"),(Object)("ar"),(Object)("tr")}));
 //BA.debugLineNum = 57;BA.debugLine="sp_lang_ques.SelectedIndex=0";
mostCurrent._sp_lang_ques.setSelectedIndex((int) (0));
 //BA.debugLineNum = 58;BA.debugLine="sp_lang_answ.SelectedIndex=0";
mostCurrent._sp_lang_answ.setSelectedIndex((int) (0));
 //BA.debugLineNum = 59;BA.debugLine="sp_lang_syno.SelectedIndex=0";
mostCurrent._sp_lang_syno.setSelectedIndex((int) (0));
 //BA.debugLineNum = 60;BA.debugLine="sp_lang_codeing.SelectedIndex=0";
mostCurrent._sp_lang_codeing.setSelectedIndex((int) (0));
 //BA.debugLineNum = 63;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 65;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group14 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen14 = group14.getSize()
;int index14 = 0;
;
for (; index14 < groupLen14;index14++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group14.Get(index14)));
 //BA.debugLineNum = 66;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 67;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 68;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 70;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 71;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 72;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 75;BA.debugLine="btn_qustion_v.Typeface=Main.app_font";
mostCurrent._btn_qustion_v.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 76;BA.debugLine="btn_answer_v.Typeface=Main.app_font";
mostCurrent._btn_answer_v.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 77;BA.debugLine="btn_synonym_v.Typeface=Main.app_font";
mostCurrent._btn_synonym_v.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 78;BA.debugLine="btn_codeing_v.Typeface=Main.app_font";
mostCurrent._btn_codeing_v.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 80;BA.debugLine="btn_save_next.Typeface=Main.app_font";
mostCurrent._btn_save_next.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 81;BA.debugLine="btn_edit_save.Typeface=Main.app_font";
mostCurrent._btn_edit_save.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 83;BA.debugLine="lbl_edit_pan.Typeface=Typeface.MATERIALICONS";
mostCurrent._lbl_edit_pan.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS());
 //BA.debugLineNum = 84;BA.debugLine="lbl_edit_pan.TextSize=20";
mostCurrent._lbl_edit_pan.setTextSize((float) (20));
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 262;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 263;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 264;BA.debugLine="If(pan_all.Visible=True)Then";
if ((mostCurrent._pan_all.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 265;BA.debugLine="pan_all_Click";
_pan_all_click();
 }else {
 //BA.debugLineNum = 267;BA.debugLine="btn_back_Click";
_btn_back_click();
 };
 //BA.debugLineNum = 270;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 272;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
 //BA.debugLineNum = 96;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 97;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 98;BA.debugLine="If Permission = rp.PERMISSION_RECORD_AUDIO Then";
if ((_permission).equals(_rp.PERMISSION_RECORD_AUDIO)) { 
 //BA.debugLineNum = 99;BA.debugLine="If Result Then";
if (_result) { 
 }else {
 //BA.debugLineNum = 106;BA.debugLine="ToastMessageShow(\"خطا در دسترسی به میکرفن \",Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("خطا در دسترسی به میکرفن "),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 88;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 89;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_RECORD_AUDIO)";
_rp.CheckAndRequest(processBA,_rp.PERMISSION_RECORD_AUDIO);
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _btn_answer_v_click() throws Exception{
 //BA.debugLineNum = 143;BA.debugLine="Private Sub btn_answer_v_Click";
 //BA.debugLineNum = 144;BA.debugLine="If sr.IsInitialized Then";
if (mostCurrent._sr._isinitialized()) { 
 //BA.debugLineNum = 145;BA.debugLine="sr.StartListening(sp_lang_answ.SelectedItem,True";
mostCurrent._sr._startlistening(mostCurrent._sp_lang_answ.getSelectedItem(),anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 146;BA.debugLine="index=2";
_index = (int) (2);
 };
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 258;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 259;BA.debugLine="StartActivity(level_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._level_activity.getObject()));
 //BA.debugLineNum = 260;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return "";
}
public static String  _btn_close_click() throws Exception{
 //BA.debugLineNum = 294;BA.debugLine="Private Sub btn_close_Click";
 //BA.debugLineNum = 295;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _btn_codeing_v_click() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Private Sub btn_codeing_v_Click";
 //BA.debugLineNum = 158;BA.debugLine="If sr.IsInitialized Then";
if (mostCurrent._sr._isinitialized()) { 
 //BA.debugLineNum = 159;BA.debugLine="sr.StartListening(sp_lang_codeing.SelectedItem,T";
mostCurrent._sr._startlistening(mostCurrent._sp_lang_codeing.getSelectedItem(),anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 160;BA.debugLine="index=4";
_index = (int) (4);
 };
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _btn_edit_save_click() throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Private Sub btn_edit_save_Click";
 //BA.debugLineNum = 237;BA.debugLine="btn_qustion_v.Text=et_ques.Text";
mostCurrent._btn_qustion_v.setText(BA.ObjectToCharSequence(mostCurrent._et_ques.getText()));
 //BA.debugLineNum = 238;BA.debugLine="btn_answer_v.Text=et_answ.Text";
mostCurrent._btn_answer_v.setText(BA.ObjectToCharSequence(mostCurrent._et_answ.getText()));
 //BA.debugLineNum = 239;BA.debugLine="btn_synonym_v.Text=et_synonym.Text";
mostCurrent._btn_synonym_v.setText(BA.ObjectToCharSequence(mostCurrent._et_synonym.getText()));
 //BA.debugLineNum = 240;BA.debugLine="btn_codeing_v.Text=et_coding.Text";
mostCurrent._btn_codeing_v.setText(BA.ObjectToCharSequence(mostCurrent._et_coding.getText()));
 //BA.debugLineNum = 242;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _btn_qustion_v_click() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Private Sub btn_qustion_v_Click";
 //BA.debugLineNum = 137;BA.debugLine="If sr.IsInitialized Then";
if (mostCurrent._sr._isinitialized()) { 
 //BA.debugLineNum = 138;BA.debugLine="sr.StartListening(sp_lang_ques.SelectedItem,True";
mostCurrent._sr._startlistening(mostCurrent._sp_lang_ques.getSelectedItem(),anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="index=1";
_index = (int) (1);
 };
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public static String  _btn_save_next_click() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Private Sub btn_save_next_Click";
 //BA.debugLineNum = 118;BA.debugLine="If(btn_qustion_v.Text.Trim==\"\" Or btn_answer_v.Te";
if (((mostCurrent._btn_qustion_v.getText().trim()).equals("") || (mostCurrent._btn_answer_v.getText().trim()).equals(""))) { 
 //BA.debugLineNum = 119;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-12\"),F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-12")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 122;BA.debugLine="Main.sq_db.ExecNonQuery2(\"INSERT INTO words (que";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._btn_qustion_v.getText()),(Object)(mostCurrent._btn_answer_v.getText()),(Object)(mostCurrent._main._book_name /*String*/ ),(Object)(mostCurrent._main._lessen_name /*String*/ ),(Object)(mostCurrent._btn_synonym_v.getText()),(Object)(mostCurrent._btn_codeing_v.getText())}));
 //BA.debugLineNum = 124;BA.debugLine="btn_qustion_v.Text=\"\"";
mostCurrent._btn_qustion_v.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 125;BA.debugLine="btn_answer_v.Text=\"\"";
mostCurrent._btn_answer_v.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 126;BA.debugLine="btn_synonym_v.Text=\"\"";
mostCurrent._btn_synonym_v.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 127;BA.debugLine="btn_codeing_v.Text=\"\"";
mostCurrent._btn_codeing_v.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 129;BA.debugLine="count_added=count_added+1";
_count_added = (int) (_count_added+1);
 //BA.debugLineNum = 130;BA.debugLine="lbl_count_added.Text=count_added";
mostCurrent._lbl_count_added.setText(BA.ObjectToCharSequence(_count_added));
 };
 //BA.debugLineNum = 133;BA.debugLine="End Sub";
return "";
}
public static String  _btn_synonym_v_click() throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Private Sub btn_synonym_v_Click";
 //BA.debugLineNum = 151;BA.debugLine="If sr.IsInitialized Then";
if (mostCurrent._sr._isinitialized()) { 
 //BA.debugLineNum = 152;BA.debugLine="sr.StartListening(sp_lang_syno.SelectedItem,True";
mostCurrent._sr._startlistening(mostCurrent._sp_lang_syno.getSelectedItem(),anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 153;BA.debugLine="index=3";
_index = (int) (3);
 };
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _check_internet() throws Exception{
boolean _connected = false;
 //BA.debugLineNum = 277;BA.debugLine="Sub check_internet";
 //BA.debugLineNum = 279;BA.debugLine="Dim connected As Boolean";
_connected = false;
 //BA.debugLineNum = 280;BA.debugLine="If p.GetDataState=\"CONNECTED\" Then";
if ((mostCurrent._p.GetDataState()).equals("CONNECTED")) { 
 //BA.debugLineNum = 281;BA.debugLine="connected=True";
_connected = anywheresoftware.b4a.keywords.Common.True;
 }else if((mostCurrent._p.GetSettings("wifi_on")).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 283;BA.debugLine="connected=True";
_connected = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 285;BA.debugLine="If(connected=False)Then";
if ((_connected==anywheresoftware.b4a.keywords.Common.False)) { 
 //BA.debugLineNum = 286;BA.debugLine="ProgBar1.Visible=False";
mostCurrent._progbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 287;BA.debugLine="ProgBar2.Visible=False";
mostCurrent._progbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 288;BA.debugLine="ProgBar3.Visible=False";
mostCurrent._progbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="ProgBar4.Visible=False";
mostCurrent._progbar4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 290;BA.debugLine="ToastMessageShow(\"اینترنت قطع است\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("اینترنت قطع است"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 292;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim sr As SpeechRecognitionNoUI";
mostCurrent._sr = new com.brandsum.speechrecognitionnoui();
 //BA.debugLineNum = 16;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 17;BA.debugLine="Dim index As Int";
_index = 0;
 //BA.debugLineNum = 18;BA.debugLine="Dim count_added As Int=0";
_count_added = (int) (0);
 //BA.debugLineNum = 20;BA.debugLine="Private btn_qustion_v As Button";
mostCurrent._btn_qustion_v = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btn_answer_v As Button";
mostCurrent._btn_answer_v = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btn_save_next As Button";
mostCurrent._btn_save_next = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private ProgBar1 As ProgressBar";
mostCurrent._progbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private ProgBar2 As ProgressBar";
mostCurrent._progbar2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private et_ques As EditText";
mostCurrent._et_ques = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private et_answ As EditText";
mostCurrent._et_answ = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btn_edit_save As Button";
mostCurrent._btn_edit_save = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private sp_lang_ques As Spinner";
mostCurrent._sp_lang_ques = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private sp_lang_answ As Spinner";
mostCurrent._sp_lang_answ = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lbl_count_added As Label";
mostCurrent._lbl_count_added = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lbl_edit_pan As Label";
mostCurrent._lbl_edit_pan = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btn_synonym_v As Button";
mostCurrent._btn_synonym_v = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private btn_codeing_v As Button";
mostCurrent._btn_codeing_v = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private sp_lang_codeing As Spinner";
mostCurrent._sp_lang_codeing = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private sp_lang_syno As Spinner";
mostCurrent._sp_lang_syno = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private ProgBar4 As ProgressBar";
mostCurrent._progbar4 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private ProgBar3 As ProgressBar";
mostCurrent._progbar3 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private et_synonym As EditText";
mostCurrent._et_synonym = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private et_coding As EditText";
mostCurrent._et_coding = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_edit_pan_click() throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Private Sub lbl_edit_pan_Click";
 //BA.debugLineNum = 246;BA.debugLine="et_ques.Text=btn_qustion_v.Text";
mostCurrent._et_ques.setText(BA.ObjectToCharSequence(mostCurrent._btn_qustion_v.getText()));
 //BA.debugLineNum = 247;BA.debugLine="et_answ.Text=btn_answer_v.Text";
mostCurrent._et_answ.setText(BA.ObjectToCharSequence(mostCurrent._btn_answer_v.getText()));
 //BA.debugLineNum = 248;BA.debugLine="et_synonym.Text=btn_synonym_v.Text";
mostCurrent._et_synonym.setText(BA.ObjectToCharSequence(mostCurrent._btn_synonym_v.getText()));
 //BA.debugLineNum = 249;BA.debugLine="et_coding.Text=btn_codeing_v.Text";
mostCurrent._et_coding.setText(BA.ObjectToCharSequence(mostCurrent._btn_codeing_v.getText()));
 //BA.debugLineNum = 251;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 232;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 233;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public static String  _panel2_click() throws Exception{
 //BA.debugLineNum = 254;BA.debugLine="Private Sub Panel2_Click";
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _sr_beginningofspeech() throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Sub sr_BeginningOfSpeech";
 //BA.debugLineNum = 189;BA.debugLine="If(index==1)Then";
if ((_index==1)) { 
 //BA.debugLineNum = 190;BA.debugLine="ProgBar1.Visible=True";
mostCurrent._progbar1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((_index==2)) { 
 //BA.debugLineNum = 192;BA.debugLine="ProgBar2.Visible=True";
mostCurrent._progbar2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((_index==3)) { 
 //BA.debugLineNum = 194;BA.debugLine="ProgBar3.Visible=True";
mostCurrent._progbar3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((_index==4)) { 
 //BA.debugLineNum = 196;BA.debugLine="ProgBar4.Visible=True";
mostCurrent._progbar4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _sr_endofspeech() throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Sub sr_EndOfSpeech";
 //BA.debugLineNum = 204;BA.debugLine="ProgBar1.Visible=False";
mostCurrent._progbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 205;BA.debugLine="ProgBar2.Visible=False";
mostCurrent._progbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 206;BA.debugLine="ProgBar3.Visible=False";
mostCurrent._progbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 207;BA.debugLine="ProgBar4.Visible=False";
mostCurrent._progbar4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 209;BA.debugLine="End Sub";
return "";
}
public static String  _sr_error(String _msg) throws Exception{
 //BA.debugLineNum = 223;BA.debugLine="Sub sr_Error(Msg As String)";
 //BA.debugLineNum = 225;BA.debugLine="ProgBar1.Visible=False";
mostCurrent._progbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 226;BA.debugLine="ProgBar2.Visible=False";
mostCurrent._progbar2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 227;BA.debugLine="ProgBar3.Visible=False";
mostCurrent._progbar3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 228;BA.debugLine="ProgBar4.Visible=False";
mostCurrent._progbar4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _sr_readyforspeech() throws Exception{
 //BA.debugLineNum = 211;BA.debugLine="Sub sr_ReadyForSpeech";
 //BA.debugLineNum = 212;BA.debugLine="If(index==1)Then";
if ((_index==1)) { 
 //BA.debugLineNum = 213;BA.debugLine="ProgBar1.Visible=True";
mostCurrent._progbar1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((_index==2)) { 
 //BA.debugLineNum = 215;BA.debugLine="ProgBar2.Visible=True";
mostCurrent._progbar2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((_index==3)) { 
 //BA.debugLineNum = 217;BA.debugLine="ProgBar3.Visible=True";
mostCurrent._progbar3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if((_index==4)) { 
 //BA.debugLineNum = 219;BA.debugLine="ProgBar4.Visible=True";
mostCurrent._progbar4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
return "";
}
public static String  _sr_results(anywheresoftware.b4a.objects.collections.List _texts) throws Exception{
String _text = "";
String _t = "";
 //BA.debugLineNum = 167;BA.debugLine="Sub sr_Results(Texts As List)";
 //BA.debugLineNum = 169;BA.debugLine="Dim Text As String = \"\"";
_text = "";
 //BA.debugLineNum = 170;BA.debugLine="For Each t As String In Texts";
{
final anywheresoftware.b4a.BA.IterableList group2 = _texts;
final int groupLen2 = group2.getSize()
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_t = BA.ObjectToString(group2.Get(index2));
 //BA.debugLineNum = 171;BA.debugLine="Text = Text & t";
_text = _text+_t;
 }
};
 //BA.debugLineNum = 173;BA.debugLine="If(index==1)Then";
if ((_index==1)) { 
 //BA.debugLineNum = 174;BA.debugLine="btn_qustion_v.Text=Text";
mostCurrent._btn_qustion_v.setText(BA.ObjectToCharSequence(_text));
 }else if((_index==2)) { 
 //BA.debugLineNum = 176;BA.debugLine="btn_answer_v.Text=Text";
mostCurrent._btn_answer_v.setText(BA.ObjectToCharSequence(_text));
 }else if((_index==3)) { 
 //BA.debugLineNum = 178;BA.debugLine="btn_synonym_v.Text=Text";
mostCurrent._btn_synonym_v.setText(BA.ObjectToCharSequence(_text));
 }else if((_index==4)) { 
 //BA.debugLineNum = 180;BA.debugLine="btn_codeing_v.Text=Text";
mostCurrent._btn_codeing_v.setText(BA.ObjectToCharSequence(_text));
 };
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
}
