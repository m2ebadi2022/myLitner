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

public class exam_activity extends Activity implements B4AActivity{
	public static exam_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.exam_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (exam_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.exam_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.exam_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (exam_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (exam_activity) Resume **");
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
		return exam_activity.class;
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
            BA.LogInfo("** Activity (exam_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (exam_activity) Pause event (activity is not paused). **");
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
            exam_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (exam_activity) Resume **");
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
public static int _count_correct = 0;
public static int _count_incorrect = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_back = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_quse = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_a2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_a1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_a3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_a4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_next = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_current_quse = null;
public static int _index = 0;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
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
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 37;BA.debugLine="Activity.LoadLayout(\"exam_layout\")";
mostCurrent._activity.LoadLayout("exam_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 38;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 39;BA.debugLine="Activity.Color=Main.app_color";
mostCurrent._activity.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 42;BA.debugLine="ques_generator(index)";
_ques_generator(_index);
 //BA.debugLineNum = 44;BA.debugLine="lbl_current_quse.Text=(index+1)&\" از \"&init_exam_";
mostCurrent._lbl_current_quse.setText(BA.ObjectToCharSequence(BA.NumberToString((_index+1))+" از "+BA.NumberToString(mostCurrent._init_exam_activity._list_exam_ques /*anywheresoftware.b4a.objects.collections.List*/ .getSize())));
 //BA.debugLineNum = 45;BA.debugLine="count_correct=0";
_count_correct = (int) (0);
 //BA.debugLineNum = 46;BA.debugLine="count_incorrect=0";
_count_incorrect = (int) (0);
 //BA.debugLineNum = 49;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group8 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen8 = group8.getSize()
;int index8 = 0;
;
for (; index8 < groupLen8;index8++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group8.Get(index8)));
 //BA.debugLineNum = 50;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 51;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 52;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 54;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 55;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 56;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 59;BA.debugLine="btn_a1.Typeface = Main.app_font";
mostCurrent._btn_a1.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 60;BA.debugLine="btn_a2.Typeface = Main.app_font";
mostCurrent._btn_a2.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 61;BA.debugLine="btn_a3.Typeface = Main.app_font";
mostCurrent._btn_a3.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 62;BA.debugLine="btn_a4.Typeface = Main.app_font";
mostCurrent._btn_a4.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 63;BA.debugLine="btn_next.Typeface = Main.app_font";
mostCurrent._btn_next.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 216;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 217;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 218;BA.debugLine="btn_back_Click";
_btn_back_click();
 //BA.debugLineNum = 219;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 221;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _btn_a1_click() throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Private Sub btn_a1_Click";
 //BA.debugLineNum = 176;BA.debugLine="If(btn_a1.Text==btn_a1.Tag)Then";
if (((mostCurrent._btn_a1.getText()).equals(BA.ObjectToString(mostCurrent._btn_a1.getTag())))) { 
 //BA.debugLineNum = 177;BA.debugLine="btn_a1.Color=0xFF33FF00";
mostCurrent._btn_a1.setColor(((int)0xff33ff00));
 //BA.debugLineNum = 178;BA.debugLine="count_correct=count_correct+1";
_count_correct = (int) (_count_correct+1);
 }else {
 //BA.debugLineNum = 180;BA.debugLine="btn_a1.Color=0xFFFF1500";
mostCurrent._btn_a1.setColor(((int)0xffff1500));
 //BA.debugLineNum = 181;BA.debugLine="count_incorrect=count_incorrect+1";
_count_incorrect = (int) (_count_incorrect+1);
 };
 //BA.debugLineNum = 183;BA.debugLine="chek_true";
_chek_true();
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _btn_a2_click() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Private Sub btn_a2_Click";
 //BA.debugLineNum = 187;BA.debugLine="If(btn_a2.Text==btn_a2.Tag)Then";
if (((mostCurrent._btn_a2.getText()).equals(BA.ObjectToString(mostCurrent._btn_a2.getTag())))) { 
 //BA.debugLineNum = 188;BA.debugLine="btn_a2.Color=0xFF33FF00";
mostCurrent._btn_a2.setColor(((int)0xff33ff00));
 //BA.debugLineNum = 189;BA.debugLine="count_correct=count_correct+1";
_count_correct = (int) (_count_correct+1);
 }else {
 //BA.debugLineNum = 191;BA.debugLine="btn_a2.Color=0xFFFF1500";
mostCurrent._btn_a2.setColor(((int)0xffff1500));
 //BA.debugLineNum = 192;BA.debugLine="count_incorrect=count_incorrect+1";
_count_incorrect = (int) (_count_incorrect+1);
 };
 //BA.debugLineNum = 194;BA.debugLine="chek_true";
_chek_true();
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _btn_a3_click() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Private Sub btn_a3_Click";
 //BA.debugLineNum = 165;BA.debugLine="If(btn_a3.Text==btn_a3.Tag)Then";
if (((mostCurrent._btn_a3.getText()).equals(BA.ObjectToString(mostCurrent._btn_a3.getTag())))) { 
 //BA.debugLineNum = 166;BA.debugLine="btn_a3.Color=0xFF33FF00";
mostCurrent._btn_a3.setColor(((int)0xff33ff00));
 //BA.debugLineNum = 167;BA.debugLine="count_correct=count_correct+1";
_count_correct = (int) (_count_correct+1);
 }else {
 //BA.debugLineNum = 169;BA.debugLine="btn_a3.Color=0xFFFF1500";
mostCurrent._btn_a3.setColor(((int)0xffff1500));
 //BA.debugLineNum = 170;BA.debugLine="count_incorrect=count_incorrect+1";
_count_incorrect = (int) (_count_incorrect+1);
 };
 //BA.debugLineNum = 172;BA.debugLine="chek_true";
_chek_true();
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _btn_a4_click() throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Private Sub btn_a4_Click";
 //BA.debugLineNum = 154;BA.debugLine="If(btn_a4.Text==btn_a4.Tag)Then";
if (((mostCurrent._btn_a4.getText()).equals(BA.ObjectToString(mostCurrent._btn_a4.getTag())))) { 
 //BA.debugLineNum = 155;BA.debugLine="btn_a4.Color=0xFF33FF00  '' green";
mostCurrent._btn_a4.setColor(((int)0xff33ff00));
 //BA.debugLineNum = 156;BA.debugLine="count_correct=count_correct+1";
_count_correct = (int) (_count_correct+1);
 }else {
 //BA.debugLineNum = 158;BA.debugLine="btn_a4.Color=0xFFFF1500 '' red";
mostCurrent._btn_a4.setColor(((int)0xffff1500));
 //BA.debugLineNum = 159;BA.debugLine="count_incorrect=count_incorrect+1";
_count_incorrect = (int) (_count_incorrect+1);
 };
 //BA.debugLineNum = 161;BA.debugLine="chek_true";
_chek_true();
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 213;BA.debugLine="StartActivity(init_exam_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._init_exam_activity.getObject()));
 //BA.debugLineNum = 214;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _btn_next_click() throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Private Sub btn_next_Click";
 //BA.debugLineNum = 146;BA.debugLine="index=index+1";
_index = (int) (_index+1);
 //BA.debugLineNum = 147;BA.debugLine="ques_generator(index)";
_ques_generator(_index);
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _chek_true() throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub chek_true";
 //BA.debugLineNum = 197;BA.debugLine="If(btn_a1.Text==btn_a1.Tag)Then";
if (((mostCurrent._btn_a1.getText()).equals(BA.ObjectToString(mostCurrent._btn_a1.getTag())))) { 
 //BA.debugLineNum = 198;BA.debugLine="btn_a1.Color=0xFF33FF00";
mostCurrent._btn_a1.setColor(((int)0xff33ff00));
 }else if(((mostCurrent._btn_a2.getText()).equals(BA.ObjectToString(mostCurrent._btn_a2.getTag())))) { 
 //BA.debugLineNum = 200;BA.debugLine="btn_a2.Color=0xFF33FF00";
mostCurrent._btn_a2.setColor(((int)0xff33ff00));
 }else if(((mostCurrent._btn_a3.getText()).equals(BA.ObjectToString(mostCurrent._btn_a3.getTag())))) { 
 //BA.debugLineNum = 202;BA.debugLine="btn_a3.Color=0xFF33FF00";
mostCurrent._btn_a3.setColor(((int)0xff33ff00));
 }else if(((mostCurrent._btn_a4.getText()).equals(BA.ObjectToString(mostCurrent._btn_a4.getTag())))) { 
 //BA.debugLineNum = 204;BA.debugLine="btn_a4.Color=0xFF33FF00";
mostCurrent._btn_a4.setColor(((int)0xff33ff00));
 };
 //BA.debugLineNum = 206;BA.debugLine="btn_a1.Enabled=False";
mostCurrent._btn_a1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 207;BA.debugLine="btn_a2.Enabled=False";
mostCurrent._btn_a2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 208;BA.debugLine="btn_a3.Enabled=False";
mostCurrent._btn_a3.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 209;BA.debugLine="btn_a4.Enabled=False";
mostCurrent._btn_a4.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private btn_back As Button";
mostCurrent._btn_back = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lbl_quse As Label";
mostCurrent._lbl_quse = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btn_a2 As Button";
mostCurrent._btn_a2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btn_a1 As Button";
mostCurrent._btn_a1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btn_a3 As Button";
mostCurrent._btn_a3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private btn_a4 As Button";
mostCurrent._btn_a4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btn_next As Button";
mostCurrent._btn_next = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lbl_current_quse As Label";
mostCurrent._lbl_current_quse = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim index As Int=0";
_index = (int) (0);
 //BA.debugLineNum = 32;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim count_correct As Int=0";
_count_correct = (int) (0);
 //BA.debugLineNum = 10;BA.debugLine="Dim count_incorrect As Int=0";
_count_incorrect = (int) (0);
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _ques_generator(int _id) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur1 = null;
anywheresoftware.b4a.objects.collections.List _other_answ = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur2 = null;
int _p = 0;
int _rand_bt = 0;
 //BA.debugLineNum = 74;BA.debugLine="Sub ques_generator (id As Int)";
 //BA.debugLineNum = 75;BA.debugLine="If(id<= init_exam_activity.list_exam_ques.Size-1";
if ((_id<=mostCurrent._init_exam_activity._list_exam_ques /*anywheresoftware.b4a.objects.collections.List*/ .getSize()-1)) { 
 //BA.debugLineNum = 76;BA.debugLine="lbl_current_quse.Text=id+1";
mostCurrent._lbl_current_quse.setText(BA.ObjectToCharSequence(_id+1));
 //BA.debugLineNum = 77;BA.debugLine="btn_a1.Enabled=True";
mostCurrent._btn_a1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="btn_a2.Enabled=True";
mostCurrent._btn_a2.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 79;BA.debugLine="btn_a3.Enabled=True";
mostCurrent._btn_a3.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="btn_a4.Enabled=True";
mostCurrent._btn_a4.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 81;BA.debugLine="btn_a1.Color=Colors.White";
mostCurrent._btn_a1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 82;BA.debugLine="btn_a2.Color=Colors.White";
mostCurrent._btn_a2.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 83;BA.debugLine="btn_a3.Color=Colors.White";
mostCurrent._btn_a3.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 84;BA.debugLine="btn_a4.Color=Colors.White";
mostCurrent._btn_a4.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 86;BA.debugLine="Main.query=\"SELECT * FROM words WHERE id=\"&init_";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE id="+BA.ObjectToString(mostCurrent._init_exam_activity._list_exam_ques /*anywheresoftware.b4a.objects.collections.List*/ .Get(_id));
 //BA.debugLineNum = 87;BA.debugLine="Dim cur1 As Cursor";
_cur1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 88;BA.debugLine="cur1 = Main.sq_db.ExecQuery(Main.query)";
_cur1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 89;BA.debugLine="cur1.Position=0";
_cur1.setPosition((int) (0));
 //BA.debugLineNum = 90;BA.debugLine="lbl_quse.Text=cur1.GetString(\"question\")";
mostCurrent._lbl_quse.setText(BA.ObjectToCharSequence(_cur1.GetString("question")));
 //BA.debugLineNum = 91;BA.debugLine="Dim other_answ As List";
_other_answ = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 92;BA.debugLine="other_answ.Initialize";
_other_answ.Initialize();
 //BA.debugLineNum = 93;BA.debugLine="Dim cur2 As Cursor";
_cur2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 94;BA.debugLine="cur2 = Main.sq_db.ExecQuery(\"Select * FROM words";
_cur2 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("Select * FROM words WHERE id IN (Select id FROM words ORDER BY RANDOM() LIMIT 3)")));
 //BA.debugLineNum = 95;BA.debugLine="For p=0 To cur2.RowCount-1";
{
final int step20 = 1;
final int limit20 = (int) (_cur2.getRowCount()-1);
_p = (int) (0) ;
for (;_p <= limit20 ;_p = _p + step20 ) {
 //BA.debugLineNum = 96;BA.debugLine="cur2.Position=p";
_cur2.setPosition(_p);
 //BA.debugLineNum = 97;BA.debugLine="If(cur2.GetString(\"answer\")==cur1.GetString(\"an";
if (((_cur2.GetString("answer")).equals(_cur1.GetString("answer")))) { 
 //BA.debugLineNum = 98;BA.debugLine="other_answ.Add(\"دیگر\")";
_other_answ.Add((Object)("دیگر"));
 //BA.debugLineNum = 99;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 101;BA.debugLine="other_answ.Add(cur2.GetString(\"answer\"))";
_other_answ.Add((Object)(_cur2.GetString("answer")));
 }
};
 //BA.debugLineNum = 105;BA.debugLine="Dim rand_bt As Int";
_rand_bt = 0;
 //BA.debugLineNum = 106;BA.debugLine="rand_bt= Rnd(1,5)";
_rand_bt = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5));
 //BA.debugLineNum = 107;BA.debugLine="Select rand_bt";
switch (_rand_bt) {
case 1: {
 //BA.debugLineNum = 109;BA.debugLine="btn_a1.Text=cur1.GetString(\"answer\")";
mostCurrent._btn_a1.setText(BA.ObjectToCharSequence(_cur1.GetString("answer")));
 //BA.debugLineNum = 110;BA.debugLine="btn_a2.Text=other_answ.Get(0)";
mostCurrent._btn_a2.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (0))));
 //BA.debugLineNum = 111;BA.debugLine="btn_a3.Text=other_answ.Get(1)";
mostCurrent._btn_a3.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (1))));
 //BA.debugLineNum = 112;BA.debugLine="btn_a4.Text=other_answ.Get(2)";
mostCurrent._btn_a4.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (2))));
 //BA.debugLineNum = 113;BA.debugLine="btn_a1.Tag=cur1.GetString(\"answer\")";
mostCurrent._btn_a1.setTag((Object)(_cur1.GetString("answer")));
 break; }
case 2: {
 //BA.debugLineNum = 115;BA.debugLine="btn_a2.Text=cur1.GetString(\"answer\")";
mostCurrent._btn_a2.setText(BA.ObjectToCharSequence(_cur1.GetString("answer")));
 //BA.debugLineNum = 116;BA.debugLine="btn_a1.Text=other_answ.Get(0)";
mostCurrent._btn_a1.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (0))));
 //BA.debugLineNum = 117;BA.debugLine="btn_a3.Text=other_answ.Get(1)";
mostCurrent._btn_a3.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (1))));
 //BA.debugLineNum = 118;BA.debugLine="btn_a4.Text=other_answ.Get(2)";
mostCurrent._btn_a4.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (2))));
 //BA.debugLineNum = 119;BA.debugLine="btn_a2.Tag=cur1.GetString(\"answer\")";
mostCurrent._btn_a2.setTag((Object)(_cur1.GetString("answer")));
 break; }
case 3: {
 //BA.debugLineNum = 121;BA.debugLine="btn_a3.Text=cur1.GetString(\"answer\")";
mostCurrent._btn_a3.setText(BA.ObjectToCharSequence(_cur1.GetString("answer")));
 //BA.debugLineNum = 122;BA.debugLine="btn_a2.Text=other_answ.Get(0)";
mostCurrent._btn_a2.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (0))));
 //BA.debugLineNum = 123;BA.debugLine="btn_a1.Text=other_answ.Get(1)";
mostCurrent._btn_a1.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (1))));
 //BA.debugLineNum = 124;BA.debugLine="btn_a4.Text=other_answ.Get(2)";
mostCurrent._btn_a4.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (2))));
 //BA.debugLineNum = 125;BA.debugLine="btn_a3.Tag=cur1.GetString(\"answer\")";
mostCurrent._btn_a3.setTag((Object)(_cur1.GetString("answer")));
 break; }
case 4: {
 //BA.debugLineNum = 127;BA.debugLine="btn_a4.Text=cur1.GetString(\"answer\")";
mostCurrent._btn_a4.setText(BA.ObjectToCharSequence(_cur1.GetString("answer")));
 //BA.debugLineNum = 128;BA.debugLine="btn_a2.Text=other_answ.Get(0)";
mostCurrent._btn_a2.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (0))));
 //BA.debugLineNum = 129;BA.debugLine="btn_a3.Text=other_answ.Get(1)";
mostCurrent._btn_a3.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (1))));
 //BA.debugLineNum = 130;BA.debugLine="btn_a1.Text=other_answ.Get(2)";
mostCurrent._btn_a1.setText(BA.ObjectToCharSequence(_other_answ.Get((int) (2))));
 //BA.debugLineNum = 131;BA.debugLine="btn_a4.Tag=cur1.GetString(\"answer\")";
mostCurrent._btn_a4.setTag((Object)(_cur1.GetString("answer")));
 break; }
}
;
 //BA.debugLineNum = 135;BA.debugLine="If(id==init_exam_activity.list_exam_ques.Size-1)";
if ((_id==mostCurrent._init_exam_activity._list_exam_ques /*anywheresoftware.b4a.objects.collections.List*/ .getSize()-1)) { 
 //BA.debugLineNum = 136;BA.debugLine="ToastMessageShow(\"آخرین سوال\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("آخرین سوال"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 139;BA.debugLine="StartActivity(result_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._result_activity.getObject()));
 //BA.debugLineNum = 140;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
}
