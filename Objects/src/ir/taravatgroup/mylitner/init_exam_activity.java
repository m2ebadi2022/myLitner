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

public class init_exam_activity extends Activity implements B4AActivity{
	public static init_exam_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.init_exam_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (init_exam_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.init_exam_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.init_exam_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (init_exam_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (init_exam_activity) Resume **");
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
		return init_exam_activity.class;
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
            BA.LogInfo("** Activity (init_exam_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (init_exam_activity) Pause event (activity is not paused). **");
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
            init_exam_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (init_exam_activity) Resume **");
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
public static anywheresoftware.b4a.objects.collections.List _list_exam_ques = null;
public anywheresoftware.b4a.objects.collections.List _list_exam_ques_all = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_back = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb_all = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rb_selection = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_book = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sp_lessen = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_ques = null;
public anywheresoftware.b4a.objects.SeekBarWrapper _sk_count_ques = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_start = null;
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
 //BA.debugLineNum = 28;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 30;BA.debugLine="Activity.LoadLayout(\"init_exam_layout\")";
mostCurrent._activity.LoadLayout("init_exam_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 31;BA.debugLine="sp_book.Enabled=False";
mostCurrent._sp_book.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 32;BA.debugLine="sp_lessen.Enabled=False";
mostCurrent._sp_lessen.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 33;BA.debugLine="list_exam_ques_all.Initialize";
mostCurrent._list_exam_ques_all.Initialize();
 //BA.debugLineNum = 34;BA.debugLine="list_exam_ques.Initialize";
_list_exam_ques.Initialize();
 //BA.debugLineNum = 38;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group6 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen6 = group6.getSize()
;int index6 = 0;
;
for (; index6 < groupLen6;index6++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group6.Get(index6)));
 //BA.debugLineNum = 39;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 40;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 41;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 43;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 44;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 45;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 48;BA.debugLine="btn_start.Typeface = Main.app_font";
mostCurrent._btn_start.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 49;BA.debugLine="rb_all.Typeface = Main.app_font";
mostCurrent._rb_all.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 50;BA.debugLine="rb_selection.Typeface = Main.app_font";
mostCurrent._rb_selection.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 167;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 168;BA.debugLine="btn_back_Click";
_btn_back_click();
 //BA.debugLineNum = 169;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 171;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 163;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 164;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _btn_start_click() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur2 = null;
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 63;BA.debugLine="Private Sub btn_start_Click";
 //BA.debugLineNum = 70;BA.debugLine="list_exam_ques_all.Clear";
mostCurrent._list_exam_ques_all.Clear();
 //BA.debugLineNum = 71;BA.debugLine="list_exam_ques.Clear";
_list_exam_ques.Clear();
 //BA.debugLineNum = 73;BA.debugLine="If(rb_all.Checked)Then";
if ((mostCurrent._rb_all.getChecked())) { 
 //BA.debugLineNum = 74;BA.debugLine="Main.query=\"SELECT * FROM words\"";
mostCurrent._main._query /*String*/  = "SELECT * FROM words";
 }else if((mostCurrent._rb_selection.getChecked())) { 
 //BA.debugLineNum = 76;BA.debugLine="If(sp_lessen.SelectedItem==\"همه درسها\")Then";
if (((mostCurrent._sp_lessen.getSelectedItem()).equals("همه درسها"))) { 
 //BA.debugLineNum = 77;BA.debugLine="Main.query=\"SELECT * FROM words WHERE book='\"&";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE book='"+mostCurrent._sp_book.getSelectedItem()+"'";
 }else {
 //BA.debugLineNum = 79;BA.debugLine="Main.query=\"SELECT * FROM words WHERE book='\"&";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE book='"+mostCurrent._sp_book.getSelectedItem()+"' AND lessen='"+mostCurrent._sp_lessen.getSelectedItem()+"'";
 };
 };
 //BA.debugLineNum = 83;BA.debugLine="Dim cur2 As Cursor";
_cur2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 85;BA.debugLine="cur2 = Main.sq_db.ExecQuery(Main.query)";
_cur2 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 86;BA.debugLine="For i=0 To cur2.RowCount-1";
{
final int step14 = 1;
final int limit14 = (int) (_cur2.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit14 ;_i = _i + step14 ) {
 //BA.debugLineNum = 87;BA.debugLine="cur2.Position=i";
_cur2.setPosition(_i);
 //BA.debugLineNum = 88;BA.debugLine="list_exam_ques_all.Add(cur2.GetInt(\"id\"))";
mostCurrent._list_exam_ques_all.Add((Object)(_cur2.GetInt("id")));
 }
};
 //BA.debugLineNum = 91;BA.debugLine="list_exam_ques_all=ShuffleList(list_exam_ques_all";
mostCurrent._list_exam_ques_all = _shufflelist(mostCurrent._list_exam_ques_all);
 //BA.debugLineNum = 92;BA.debugLine="For j=0 To list_exam_ques_all.Size-1";
{
final int step19 = 1;
final int limit19 = (int) (mostCurrent._list_exam_ques_all.getSize()-1);
_j = (int) (0) ;
for (;_j <= limit19 ;_j = _j + step19 ) {
 //BA.debugLineNum = 95;BA.debugLine="If (j==sk_count_ques.Value)Then";
if ((_j==mostCurrent._sk_count_ques.getValue())) { 
 //BA.debugLineNum = 96;BA.debugLine="Exit";
if (true) break;
 };
 //BA.debugLineNum = 98;BA.debugLine="list_exam_ques.Add(list_exam_ques_all.Get(j))";
_list_exam_ques.Add(mostCurrent._list_exam_ques_all.Get(_j));
 }
};
 //BA.debugLineNum = 101;BA.debugLine="StartActivity(exam_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._exam_activity.getObject()));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim list_exam_ques_all As List";
mostCurrent._list_exam_ques_all = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Private btn_back As Button";
mostCurrent._btn_back = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private rb_all As RadioButton";
mostCurrent._rb_all = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private rb_selection As RadioButton";
mostCurrent._rb_selection = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private sp_book As Spinner";
mostCurrent._sp_book = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private sp_lessen As Spinner";
mostCurrent._sp_lessen = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lbl_count_ques As Label";
mostCurrent._lbl_count_ques = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private sk_count_ques As SeekBar";
mostCurrent._sk_count_ques = new anywheresoftware.b4a.objects.SeekBarWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btn_start As Button";
mostCurrent._btn_start = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim list_exam_ques As List";
_list_exam_ques = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _rb_all_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 154;BA.debugLine="Private Sub rb_all_CheckedChange(Checked As Boolea";
 //BA.debugLineNum = 155;BA.debugLine="sp_book.Enabled=False";
mostCurrent._sp_book.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 156;BA.debugLine="sp_lessen.Enabled=False";
mostCurrent._sp_lessen.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 158;BA.debugLine="sp_book.Clear";
mostCurrent._sp_book.Clear();
 //BA.debugLineNum = 159;BA.debugLine="sp_lessen.Clear";
mostCurrent._sp_lessen.Clear();
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return "";
}
public static String  _rb_selection_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur3 = null;
int _i = 0;
 //BA.debugLineNum = 137;BA.debugLine="Private Sub rb_selection_CheckedChange(Checked As";
 //BA.debugLineNum = 138;BA.debugLine="sp_book.Enabled=True";
mostCurrent._sp_book.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 139;BA.debugLine="sp_lessen.Enabled=True";
mostCurrent._sp_lessen.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 140;BA.debugLine="sp_book.Clear";
mostCurrent._sp_book.Clear();
 //BA.debugLineNum = 142;BA.debugLine="Dim cur3 As Cursor";
_cur3 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 143;BA.debugLine="cur3 = Main.sq_db.ExecQuery(\"SELECT * FROM books\"";
_cur3 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM books")));
 //BA.debugLineNum = 144;BA.debugLine="For i=0 To cur3.RowCount-1";
{
final int step6 = 1;
final int limit6 = (int) (_cur3.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 145;BA.debugLine="cur3.Position=i";
_cur3.setPosition(_i);
 //BA.debugLineNum = 146;BA.debugLine="sp_book.Add(cur3.GetString(\"name\"))";
mostCurrent._sp_book.Add(_cur3.GetString("name"));
 }
};
 //BA.debugLineNum = 149;BA.debugLine="sp_book_ItemClick(0,sp_book.GetItem(0))";
_sp_book_itemclick((int) (0),(Object)(mostCurrent._sp_book.GetItem((int) (0))));
 //BA.debugLineNum = 150;BA.debugLine="sp_lessen_ItemClick(0,sp_book.GetItem(0))";
_sp_lessen_itemclick((int) (0),(Object)(mostCurrent._sp_book.GetItem((int) (0))));
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _shufflelist(anywheresoftware.b4a.objects.collections.List _pl) throws Exception{
int _i = 0;
int _j = 0;
Object _k = null;
 //BA.debugLineNum = 105;BA.debugLine="Sub ShuffleList(pl As List) As List";
 //BA.debugLineNum = 106;BA.debugLine="For i = pl.Size - 1 To 0 Step -1";
{
final int step1 = -1;
final int limit1 = (int) (0);
_i = (int) (_pl.getSize()-1) ;
for (;_i >= limit1 ;_i = _i + step1 ) {
 //BA.debugLineNum = 107;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 108;BA.debugLine="Dim k As Object";
_k = new Object();
 //BA.debugLineNum = 109;BA.debugLine="j = Rnd(0, i + 1)";
_j = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (_i+1));
 //BA.debugLineNum = 110;BA.debugLine="k = pl.Get(j)";
_k = _pl.Get(_j);
 //BA.debugLineNum = 111;BA.debugLine="pl.Set(j,pl.Get(i))";
_pl.Set(_j,_pl.Get(_i));
 //BA.debugLineNum = 112;BA.debugLine="pl.Set(i,k)";
_pl.Set(_i,_k);
 }
};
 //BA.debugLineNum = 114;BA.debugLine="Return pl";
if (true) return _pl;
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return null;
}
public static String  _sk_count_ques_valuechanged(int _value,boolean _userchanged) throws Exception{
 //BA.debugLineNum = 117;BA.debugLine="Private Sub sk_count_ques_ValueChanged (Value As I";
 //BA.debugLineNum = 118;BA.debugLine="lbl_count_ques.Text=Value";
mostCurrent._lbl_count_ques.setText(BA.ObjectToCharSequence(_value));
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return "";
}
public static String  _sp_book_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur4 = null;
int _i = 0;
 //BA.debugLineNum = 125;BA.debugLine="Private Sub sp_book_ItemClick (Position As Int, Va";
 //BA.debugLineNum = 126;BA.debugLine="sp_lessen.Clear";
mostCurrent._sp_lessen.Clear();
 //BA.debugLineNum = 127;BA.debugLine="Dim cur4 As Cursor";
_cur4 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 128;BA.debugLine="cur4 = Main.sq_db.ExecQuery(\"SELECT * FROM lessen";
_cur4 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM lessen WHERE book='"+BA.ObjectToString(_value)+"'")));
 //BA.debugLineNum = 129;BA.debugLine="sp_lessen.Add(\"همه درسها\")";
mostCurrent._sp_lessen.Add("همه درسها");
 //BA.debugLineNum = 130;BA.debugLine="For i=0 To cur4.RowCount-1";
{
final int step5 = 1;
final int limit5 = (int) (_cur4.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 131;BA.debugLine="cur4.Position=i";
_cur4.setPosition(_i);
 //BA.debugLineNum = 132;BA.debugLine="sp_lessen.Add(cur4.GetString(\"name\"))";
mostCurrent._sp_lessen.Add(_cur4.GetString("name"));
 }
};
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public static String  _sp_lessen_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 121;BA.debugLine="Private Sub sp_lessen_ItemClick (Position As Int,";
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
}
