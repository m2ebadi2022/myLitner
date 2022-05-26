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

public class singl_word_activity extends Activity implements B4AActivity{
	public static singl_word_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.singl_word_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (singl_word_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.singl_word_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.singl_word_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (singl_word_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (singl_word_activity) Resume **");
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
		return singl_word_activity.class;
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
            BA.LogInfo("** Activity (singl_word_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (singl_word_activity) Pause event (activity is not paused). **");
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
            singl_word_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (singl_word_activity) Resume **");
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
public anywheresoftware.b4a.obejcts.TTS _tts1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_qustion = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_answer = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_nop = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_ok = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_progres = null;
public anywheresoftware.b4a.objects.LabelWrapper _nop_count = null;
public anywheresoftware.b4a.objects.LabelWrapper _ok_count = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_menu = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_edite_word = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_edite_question = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_edite_answer = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_tts = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_path = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_synonim = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_codeing = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_edite_synonim = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_edite_codeing = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_star = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scroll_veiw = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_edite_word = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_delete_word = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_no_edite = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_yes_edite = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_next = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_prev = null;
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
public ir.taravatgroup.mylitner.setting_time_activity _setting_time_activity = null;
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
int _state = 0;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 53;BA.debugLine="Activity.LoadLayout(\"word_layout\")";
mostCurrent._activity.LoadLayout("word_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 55;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 58;BA.debugLine="scroll_veiw.Panel.LoadLayout(\"scroll_item_word\")";
mostCurrent._scroll_veiw.getPanel().LoadLayout("scroll_item_word",mostCurrent.activityBA);
 //BA.debugLineNum = 59;BA.debugLine="scroll_veiw.Height=500dip";
mostCurrent._scroll_veiw.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 60;BA.debugLine="btn_nop.Visible=False";
mostCurrent._btn_nop.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 61;BA.debugLine="btn_ok.Visible=False";
mostCurrent._btn_ok.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 62;BA.debugLine="ProgressBar1.Visible=False";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 63;BA.debugLine="lbl_progres.Visible=False";
mostCurrent._lbl_progres.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 64;BA.debugLine="nop_count.Visible=False";
mostCurrent._nop_count.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 65;BA.debugLine="ok_count.Visible=False";
mostCurrent._ok_count.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 66;BA.debugLine="lbl_codeing.Visible=True";
mostCurrent._lbl_codeing.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 67;BA.debugLine="lbl_synonim.Visible=True";
mostCurrent._lbl_synonim.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 68;BA.debugLine="lbl_next.Visible=False";
mostCurrent._lbl_next.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 69;BA.debugLine="lbl_prev.Visible=False";
mostCurrent._lbl_prev.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 72;BA.debugLine="If tts1.IsInitialized = False Then";
if (mostCurrent._tts1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 73;BA.debugLine="tts1.Initialize(\"TTS1\")";
mostCurrent._tts1.Initialize(processBA,"TTS1");
 };
 //BA.debugLineNum = 75;BA.debugLine="tts1.SpeechRate=0.5";
mostCurrent._tts1.setSpeechRate((float) (0.5));
 //BA.debugLineNum = 79;BA.debugLine="Dim cur1 As Cursor";
_cur1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Main.query=\"Select * FROM words WHERE id=\"&Main.";
mostCurrent._main._query /*String*/  = "Select * FROM words WHERE id="+BA.NumberToString(mostCurrent._main._current_word_id /*int*/ );
 //BA.debugLineNum = 81;BA.debugLine="cur1 = Main.sq_db.ExecQuery(Main.query)";
_cur1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 82;BA.debugLine="cur1.Position=0";
_cur1.setPosition((int) (0));
 //BA.debugLineNum = 83;BA.debugLine="lbl_qustion.Text=cur1.GetString(\"question\")";
mostCurrent._lbl_qustion.setText(BA.ObjectToCharSequence(_cur1.GetString("question")));
 //BA.debugLineNum = 84;BA.debugLine="lbl_answer.Text=cur1.GetString(\"answer\")";
mostCurrent._lbl_answer.setText(BA.ObjectToCharSequence(_cur1.GetString("answer")));
 //BA.debugLineNum = 86;BA.debugLine="Dim state As Int=cur1.GetString(\"state\")";
_state = (int)(Double.parseDouble(_cur1.GetString("state")));
 //BA.debugLineNum = 87;BA.debugLine="If(state==0)Then";
if ((_state==0)) { 
 //BA.debugLineNum = 89;BA.debugLine="btn_star.Text=Chr(0xF006)";
mostCurrent._btn_star.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf006))));
 //BA.debugLineNum = 90;BA.debugLine="btn_star.TextColor=Colors.Gray";
mostCurrent._btn_star.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 }else {
 //BA.debugLineNum = 92;BA.debugLine="btn_star.Text=Chr(0xF005)";
mostCurrent._btn_star.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf005))));
 //BA.debugLineNum = 93;BA.debugLine="btn_star.TextColor=0xFFE4BA00";
mostCurrent._btn_star.setTextColor(((int)0xffe4ba00));
 };
 //BA.debugLineNum = 97;BA.debugLine="If (cur1.GetString(\"codeing\")==Null) Then";
if ((_cur1.GetString("codeing")== null)) { 
 //BA.debugLineNum = 98;BA.debugLine="lbl_codeing.Text=\"\"";
mostCurrent._lbl_codeing.setText(BA.ObjectToCharSequence(""));
 }else {
 //BA.debugLineNum = 100;BA.debugLine="lbl_codeing.Text=cur1.GetString(\"codeing\")";
mostCurrent._lbl_codeing.setText(BA.ObjectToCharSequence(_cur1.GetString("codeing")));
 };
 //BA.debugLineNum = 103;BA.debugLine="If(cur1.GetString(\"synonym\")==Null) Then";
if ((_cur1.GetString("synonym")== null)) { 
 //BA.debugLineNum = 104;BA.debugLine="lbl_synonim.Text=\"\"";
mostCurrent._lbl_synonim.setText(BA.ObjectToCharSequence(""));
 }else {
 //BA.debugLineNum = 106;BA.debugLine="lbl_synonim.Text=cur1.GetString(\"synonym\")";
mostCurrent._lbl_synonim.setText(BA.ObjectToCharSequence(_cur1.GetString("synonym")));
 };
 //BA.debugLineNum = 109;BA.debugLine="Main.current_word=lbl_qustion.Text";
mostCurrent._main._current_word /*String*/  = mostCurrent._lbl_qustion.getText();
 //BA.debugLineNum = 111;BA.debugLine="lbl_path.Text=cur1.GetString(\"book\")&\" / \"&cur1.G";
mostCurrent._lbl_path.setText(BA.ObjectToCharSequence(_cur1.GetString("book")+" / "+_cur1.GetString("lessen")+" / "+"level "+_cur1.GetString("level")));
 //BA.debugLineNum = 114;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group45 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen45 = group45.getSize()
;int index45 = 0;
;
for (; index45 < groupLen45;index45++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group45.Get(index45)));
 //BA.debugLineNum = 115;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 116;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 117;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 119;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 120;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 121;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 124;BA.debugLine="btn_edite_word.Typeface=Main.app_font";
mostCurrent._btn_edite_word.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 125;BA.debugLine="btn_delete_word.Typeface=Main.app_font";
mostCurrent._btn_delete_word.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 127;BA.debugLine="lbl_qustion.TextSize=Main.app_font_size*2";
mostCurrent._lbl_qustion.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ *2));
 //BA.debugLineNum = 128;BA.debugLine="lbl_answer.TextSize=Main.app_font_size*2";
mostCurrent._lbl_answer.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ *2));
 //BA.debugLineNum = 129;BA.debugLine="lbl_synonim.TextSize=Main.app_font_size/1.5";
mostCurrent._lbl_synonim.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ /(double)1.5));
 //BA.debugLineNum = 130;BA.debugLine="lbl_codeing.TextSize=Main.app_font_size/1.5";
mostCurrent._lbl_codeing.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ /(double)1.5));
 //BA.debugLineNum = 131;BA.debugLine="Label3.TextSize=Main.app_font_size/1.5";
mostCurrent._label3.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ /(double)1.5));
 //BA.debugLineNum = 132;BA.debugLine="Label1.TextSize=Main.app_font_size/1.5";
mostCurrent._label1.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ /(double)1.5));
 //BA.debugLineNum = 134;BA.debugLine="Label5.TextSize=20";
mostCurrent._label5.setTextSize((float) (20));
 //BA.debugLineNum = 135;BA.debugLine="lbl_path.TextSize=13";
mostCurrent._lbl_path.setTextSize((float) (13));
 //BA.debugLineNum = 138;BA.debugLine="Label5.Text=Main.loc.Localize(\"word-1\")";
mostCurrent._label5.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("word-1")));
 //BA.debugLineNum = 139;BA.debugLine="Label2.Text=Main.loc.Localize(\"word-3\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("word-3")));
 //BA.debugLineNum = 140;BA.debugLine="btn_edite_word.Text=Main.loc.Localize(\"word-2\")";
mostCurrent._btn_edite_word.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("word-2")));
 //BA.debugLineNum = 141;BA.debugLine="btn_delete_word.Text=Main.loc.Localize(\"book-10\")";
mostCurrent._btn_delete_word.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-10")));
 //BA.debugLineNum = 142;BA.debugLine="btn_yes_edite.Text=Main.loc.Localize(\"book-5\")";
mostCurrent._btn_yes_edite.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-5")));
 //BA.debugLineNum = 143;BA.debugLine="btn_no_edite.Text=Main.loc.Localize(\"book-6\")";
mostCurrent._btn_no_edite.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-6")));
 //BA.debugLineNum = 144;BA.debugLine="et_edite_question.Hint=Main.loc.Localize(\"level-7";
mostCurrent._et_edite_question.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-7"));
 //BA.debugLineNum = 145;BA.debugLine="et_edite_answer.Hint=Main.loc.Localize(\"level-8\")";
mostCurrent._et_edite_answer.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-8"));
 //BA.debugLineNum = 146;BA.debugLine="et_edite_synonim.Hint=Main.loc.Localize(\"level-9\"";
mostCurrent._et_edite_synonim.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-9"));
 //BA.debugLineNum = 147;BA.debugLine="et_edite_codeing.Hint=Main.loc.Localize(\"level-10";
mostCurrent._et_edite_codeing.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-10"));
 //BA.debugLineNum = 149;BA.debugLine="Label1.Text=Main.loc.Localize(\"level-9\")";
mostCurrent._label1.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-9")));
 //BA.debugLineNum = 150;BA.debugLine="Label3.Text=Main.loc.Localize(\"level-10\")";
mostCurrent._label3.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-10")));
 //BA.debugLineNum = 153;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 237;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 238;BA.debugLine="If(pan_all.Visible=True)Then";
if ((mostCurrent._pan_all.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 239;BA.debugLine="pan_all_Click";
_pan_all_click();
 }else {
 //BA.debugLineNum = 241;BA.debugLine="btn_back_Click";
_btn_back_click();
 };
 //BA.debugLineNum = 243;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 245;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 159;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _brn_dictionary_click() throws Exception{
 //BA.debugLineNum = 249;BA.debugLine="Private Sub brn_dictionary_Click";
 //BA.debugLineNum = 250;BA.debugLine="StartActivity(dictionary_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._dictionary_activity.getObject()));
 //BA.debugLineNum = 251;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 232;BA.debugLine="StartActivity(all_words_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._all_words_activity.getObject()));
 //BA.debugLineNum = 233;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public static void  _btn_delete_word_click() throws Exception{
ResumableSub_btn_delete_word_Click rsub = new ResumableSub_btn_delete_word_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btn_delete_word_Click extends BA.ResumableSub {
public ResumableSub_btn_delete_word_Click(ir.taravatgroup.mylitner.singl_word_activity parent) {
this.parent = parent;
}
ir.taravatgroup.mylitner.singl_word_activity parent;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 198;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 199;BA.debugLine="Msgbox2Async(Main.loc.Localize(\"word-9\"), Main.lo";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence(parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("word-9")),BA.ObjectToCharSequence(parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-10")),parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-14"),"",parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-15"),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 200;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 201;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_result==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 203;BA.debugLine="Main.query=\"DELETE FROM words WHERE id=\"&Main.cu";
parent.mostCurrent._main._query /*String*/  = "DELETE FROM words WHERE id="+BA.NumberToString(parent.mostCurrent._main._current_word_id /*int*/ );
 //BA.debugLineNum = 204;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
parent.mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(parent.mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 205;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"book-11\"),Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-11")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 206;BA.debugLine="btn_back_Click";
_btn_back_click();
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btn_edite_word_click() throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Private Sub btn_edite_word_Click";
 //BA.debugLineNum = 211;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 212;BA.debugLine="pan_edite_word.Visible=True";
mostCurrent._pan_edite_word.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 213;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 214;BA.debugLine="et_edite_question.Text=lbl_qustion.Text";
mostCurrent._et_edite_question.setText(BA.ObjectToCharSequence(mostCurrent._lbl_qustion.getText()));
 //BA.debugLineNum = 215;BA.debugLine="et_edite_answer.Text=lbl_answer.Text";
mostCurrent._et_edite_answer.setText(BA.ObjectToCharSequence(mostCurrent._lbl_answer.getText()));
 //BA.debugLineNum = 216;BA.debugLine="et_edite_synonim.Text=lbl_synonim.Text";
mostCurrent._et_edite_synonim.setText(BA.ObjectToCharSequence(mostCurrent._lbl_synonim.getText()));
 //BA.debugLineNum = 217;BA.debugLine="et_edite_codeing.Text=lbl_codeing.Text";
mostCurrent._et_edite_codeing.setText(BA.ObjectToCharSequence(mostCurrent._lbl_codeing.getText()));
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public static String  _btn_menu_click() throws Exception{
 //BA.debugLineNum = 226;BA.debugLine="Private Sub btn_menu_Click";
 //BA.debugLineNum = 227;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 228;BA.debugLine="pan_menu.Visible=True";
mostCurrent._pan_menu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _btn_no_edite_click() throws Exception{
 //BA.debugLineNum = 193;BA.debugLine="Private Sub btn_no_edite_Click";
 //BA.debugLineNum = 194;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _btn_star_click() throws Exception{
 //BA.debugLineNum = 253;BA.debugLine="Private Sub btn_star_Click";
 //BA.debugLineNum = 255;BA.debugLine="If(btn_star.Text==Chr(0xF006))Then  ' is unlike";
if (((mostCurrent._btn_star.getText()).equals(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf006)))))) { 
 //BA.debugLineNum = 256;BA.debugLine="btn_star.TextColor=0xFFE4BA00";
mostCurrent._btn_star.setTextColor(((int)0xffe4ba00));
 //BA.debugLineNum = 257;BA.debugLine="btn_star.Text=Chr(0xF005)";
mostCurrent._btn_star.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf005))));
 //BA.debugLineNum = 259;BA.debugLine="Main.query=\" UPDATE words SET state =1 WHERE id=";
mostCurrent._main._query /*String*/  = " UPDATE words SET state =1 WHERE id="+BA.NumberToString(mostCurrent._main._current_word_id /*int*/ );
 //BA.debugLineNum = 260;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 }else {
 //BA.debugLineNum = 264;BA.debugLine="Main.query=\" UPDATE words SET state =0 WHERE id=";
mostCurrent._main._query /*String*/  = " UPDATE words SET state =0 WHERE id="+BA.NumberToString(mostCurrent._main._current_word_id /*int*/ );
 //BA.debugLineNum = 265;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 267;BA.debugLine="btn_star.TextColor=Colors.Gray";
mostCurrent._btn_star.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 268;BA.debugLine="btn_star.Text=Chr(0xF006)";
mostCurrent._btn_star.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr(((int)0xf006))));
 };
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public static String  _btn_tts_click() throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Private Sub btn_tts_Click";
 //BA.debugLineNum = 163;BA.debugLine="tts1.Speak(lbl_qustion.Text, True)";
mostCurrent._tts1.Speak(mostCurrent._lbl_qustion.getText(),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _btn_yes_edite_click() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Private Sub btn_yes_edite_Click";
 //BA.debugLineNum = 175;BA.debugLine="If(et_edite_question.Text.Trim==\"\" Or et_edite_an";
if (((mostCurrent._et_edite_question.getText().trim()).equals("") || (mostCurrent._et_edite_answer.getText().trim()).equals(""))) { 
 //BA.debugLineNum = 176;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-12\"),F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-12")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 180;BA.debugLine="Main.query=\" UPDATE words SET question = ? ,answ";
mostCurrent._main._query /*String*/  = " UPDATE words SET question = ? ,answer = ? ,synonym = ? , codeing= ? WHERE id= ? ";
 //BA.debugLineNum = 182;BA.debugLine="Main.sq_db.ExecNonQuery2(Main.query,Array As Obj";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._main._query /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._et_edite_question.getText()),(Object)(mostCurrent._et_edite_answer.getText()),(Object)(mostCurrent._et_edite_synonim.getText()),(Object)(mostCurrent._et_edite_codeing.getText()),(Object)(mostCurrent._main._current_word_id /*int*/ )}));
 //BA.debugLineNum = 184;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 185;BA.debugLine="lbl_qustion.Text=et_edite_question.Text";
mostCurrent._lbl_qustion.setText(BA.ObjectToCharSequence(mostCurrent._et_edite_question.getText()));
 //BA.debugLineNum = 186;BA.debugLine="lbl_answer.Text=et_edite_answer.Text";
mostCurrent._lbl_answer.setText(BA.ObjectToCharSequence(mostCurrent._et_edite_answer.getText()));
 //BA.debugLineNum = 187;BA.debugLine="lbl_synonim.Text=et_edite_synonim.Text";
mostCurrent._lbl_synonim.setText(BA.ObjectToCharSequence(mostCurrent._et_edite_synonim.getText()));
 //BA.debugLineNum = 188;BA.debugLine="lbl_codeing.Text=et_edite_codeing.Text";
mostCurrent._lbl_codeing.setText(BA.ObjectToCharSequence(mostCurrent._et_edite_codeing.getText()));
 };
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim tts1 As TTS";
mostCurrent._tts1 = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 16;BA.debugLine="Private lbl_qustion As Label";
mostCurrent._lbl_qustion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private lbl_answer As Label";
mostCurrent._lbl_answer = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btn_nop As Button";
mostCurrent._btn_nop = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btn_ok As Button";
mostCurrent._btn_ok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lbl_progres As Label";
mostCurrent._lbl_progres = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private nop_count As Label";
mostCurrent._nop_count = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private ok_count As Label";
mostCurrent._ok_count = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private pan_menu As Panel";
mostCurrent._pan_menu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private pan_edite_word As Panel";
mostCurrent._pan_edite_word = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private et_edite_question As EditText";
mostCurrent._et_edite_question = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private et_edite_answer As EditText";
mostCurrent._et_edite_answer = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private btn_tts As Button";
mostCurrent._btn_tts = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lbl_path As Label";
mostCurrent._lbl_path = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lbl_synonim As Label";
mostCurrent._lbl_synonim = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lbl_codeing As Label";
mostCurrent._lbl_codeing = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private et_edite_synonim As EditText";
mostCurrent._et_edite_synonim = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private et_edite_codeing As EditText";
mostCurrent._et_edite_codeing = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btn_star As Button";
mostCurrent._btn_star = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private scroll_veiw As ScrollView";
mostCurrent._scroll_veiw = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btn_edite_word As Button";
mostCurrent._btn_edite_word = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btn_delete_word As Button";
mostCurrent._btn_delete_word = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btn_no_edite As Button";
mostCurrent._btn_no_edite = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btn_yes_edite As Button";
mostCurrent._btn_yes_edite = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lbl_next As Label";
mostCurrent._lbl_next = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lbl_prev As Label";
mostCurrent._lbl_prev = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 221;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 222;BA.debugLine="pan_menu.Visible=False";
mostCurrent._pan_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="pan_edite_word.Visible=False";
mostCurrent._pan_edite_word.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _pan_edite_word_click() throws Exception{
 //BA.debugLineNum = 273;BA.debugLine="Private Sub pan_edite_word_Click";
 //BA.debugLineNum = 275;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _tts1_ready(boolean _success) throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Sub TTS1_Ready (Success As Boolean)";
 //BA.debugLineNum = 166;BA.debugLine="If Success Then";
if (_success) { 
 }else {
 //BA.debugLineNum = 169;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"word-7\"), Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("word-7")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 170;BA.debugLine="btn_tts.Enabled=False";
mostCurrent._btn_tts.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
}
