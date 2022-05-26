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

public class lessen_activity extends Activity implements B4AActivity{
	public static lessen_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.lessen_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (lessen_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.lessen_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.lessen_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (lessen_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (lessen_activity) Resume **");
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
		return lessen_activity.class;
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
            BA.LogInfo("** Activity (lessen_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (lessen_activity) Pause event (activity is not paused). **");
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
            lessen_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (lessen_activity) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _pan_add = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_add_book = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_add_lessen = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_add = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_path = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public b4a.example3.customlistview _custom_ls = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_avail_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_var5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_yes_add = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_no_add = null;
public static int _hidbtn = 0;
public static int _edit_index = 0;
public static int _current_id = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_del = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_edit = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_pup = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.mylitner.main _main = null;
public ir.taravatgroup.mylitner.book_activity _book_activity = null;
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
anywheresoftware.b4a.objects.drawable.BitmapDrawable _pic_lessen = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur1 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur2 = null;
int _i = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _avail_count_lessen = 0;
int _k = 0;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="Activity.LoadLayout(\"lessen_layout\")";
mostCurrent._activity.LoadLayout("lessen_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 49;BA.debugLine="hidBtn=0";
_hidbtn = (int) (0);
 //BA.debugLineNum = 50;BA.debugLine="edit_index=0";
_edit_index = (int) (0);
 //BA.debugLineNum = 51;BA.debugLine="pan_add.Visible=True";
mostCurrent._pan_add.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 53;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 55;BA.debugLine="btn_add.TextColor=Main.app_color";
mostCurrent._btn_add.setTextColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 57;BA.debugLine="Dim pic_lessen As BitmapDrawable";
_pic_lessen = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 58;BA.debugLine="pic_lessen.Initialize(LoadBitmap(File.DirAssets,";
_pic_lessen.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"lessen1.png").getObject()));
 //BA.debugLineNum = 59;BA.debugLine="pic_lessen.Gravity = Gravity.FILL";
_pic_lessen.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 62;BA.debugLine="lbl_path.Text=Main.book_name";
mostCurrent._lbl_path.setText(BA.ObjectToCharSequence(mostCurrent._main._book_name /*String*/ ));
 //BA.debugLineNum = 63;BA.debugLine="Dim cur1 As Cursor";
_cur1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Dim cur2 As Cursor";
_cur2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Main.query=\"SELECT * FROM lessen WHERE book='\"& M";
mostCurrent._main._query /*String*/  = "SELECT * FROM lessen WHERE book='"+mostCurrent._main._book_name /*String*/ +"'";
 //BA.debugLineNum = 67;BA.debugLine="cur1 = Main.sq_db.ExecQuery(Main.query)";
_cur1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 68;BA.debugLine="For i=0 To cur1.RowCount-1";
{
final int step15 = 1;
final int limit15 = (int) (_cur1.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit15 ;_i = _i + step15 ) {
 //BA.debugLineNum = 69;BA.debugLine="cur1.Position=i";
_cur1.setPosition(_i);
 //BA.debugLineNum = 71;BA.debugLine="Main.query=\"\"";
mostCurrent._main._query /*String*/  = "";
 //BA.debugLineNum = 72;BA.debugLine="Main.query=\"SELECT COUNT(id) FROM words WHERE bo";
mostCurrent._main._query /*String*/  = "SELECT COUNT(id) FROM words WHERE book='"+mostCurrent._main._book_name /*String*/ +"' AND lessen='"+_cur1.GetString("name")+"'";
 //BA.debugLineNum = 74;BA.debugLine="cur2 = Main.sq_db.ExecQuery(Main.query)";
_cur2 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 75;BA.debugLine="cur2.Position=0";
_cur2.setPosition((int) (0));
 //BA.debugLineNum = 78;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 79;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, 100%x, 86dip)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (86)));
 //BA.debugLineNum = 81;BA.debugLine="p.LoadLayout(\"item_level\")";
_p.LoadLayout("item_level",mostCurrent.activityBA);
 //BA.debugLineNum = 83;BA.debugLine="btn_del.Tag=cur1.GetInt(\"id\")";
mostCurrent._btn_del.setTag((Object)(_cur1.GetInt("id")));
 //BA.debugLineNum = 84;BA.debugLine="btn_edit.Tag=cur1.GetInt(\"id\")";
mostCurrent._btn_edit.setTag((Object)(_cur1.GetInt("id")));
 //BA.debugLineNum = 86;BA.debugLine="lbl_level.Background=pic_lessen";
mostCurrent._lbl_level.setBackground((android.graphics.drawable.Drawable)(_pic_lessen.getObject()));
 //BA.debugLineNum = 88;BA.debugLine="lbl_avail_level.Text= cur1.GetString(\"name\")";
mostCurrent._lbl_avail_level.setText(BA.ObjectToCharSequence(_cur1.GetString("name")));
 //BA.debugLineNum = 89;BA.debugLine="lbl_count_level.Text=Main.loc.Localize(\"main-4\")";
mostCurrent._lbl_count_level.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-4")+" : "+BA.NumberToString(_cur2.GetInt("COUNT(id)"))));
 //BA.debugLineNum = 90;BA.debugLine="custom_LS.Add(p, cur1.GetString(\"name\"))";
mostCurrent._custom_ls._add(_p,(Object)(_cur1.GetString("name")));
 //BA.debugLineNum = 92;BA.debugLine="Main.lessen_name=cur1.GetString(\"name\")";
mostCurrent._main._lessen_name /*String*/  = _cur1.GetString("name");
 //BA.debugLineNum = 93;BA.debugLine="Dim avail_count_lessen As Int=0";
_avail_count_lessen = (int) (0);
 //BA.debugLineNum = 94;BA.debugLine="For k=0 To Main.level_list_name.Size-1";
{
final int step32 = 1;
final int limit32 = (int) (mostCurrent._main._level_list_name /*anywheresoftware.b4a.objects.collections.List*/ .getSize()-1);
_k = (int) (0) ;
for (;_k <= limit32 ;_k = _k + step32 ) {
 //BA.debugLineNum = 96;BA.debugLine="avail_count_lessen=avail_count_lessen+available";
_avail_count_lessen = (int) (_avail_count_lessen+_available_words((int) (_k+1)));
 }
};
 //BA.debugLineNum = 99;BA.debugLine="lbl_var5.Text=Main.loc.Localize(\"book-12\")&\" : \"";
mostCurrent._lbl_var5.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-12")+" : "+BA.NumberToString(_avail_count_lessen)));
 }
};
 //BA.debugLineNum = 110;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group37 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen37 = group37.getSize()
;int index37 = 0;
;
for (; index37 < groupLen37;index37++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group37.Get(index37)));
 //BA.debugLineNum = 111;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 112;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 113;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 115;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 116;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 117;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 123;BA.debugLine="Label5.TextSize=18";
mostCurrent._label5.setTextSize((float) (18));
 //BA.debugLineNum = 124;BA.debugLine="lbl_path.TextSize=12";
mostCurrent._lbl_path.setTextSize((float) (12));
 //BA.debugLineNum = 126;BA.debugLine="Label5.Text=Main.loc.Localize(\"main-3\")";
mostCurrent._label5.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-3")));
 //BA.debugLineNum = 127;BA.debugLine="Label2.Text=Main.loc.Localize(\"lessen-2\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("lessen-2")));
 //BA.debugLineNum = 128;BA.debugLine="et_add_lessen.Hint=Main.loc.Localize(\"lessen-1\")";
mostCurrent._et_add_lessen.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("lessen-1"));
 //BA.debugLineNum = 129;BA.debugLine="btn_yes_add.Text=Main.loc.Localize(\"book-5\")";
mostCurrent._btn_yes_add.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-5")));
 //BA.debugLineNum = 130;BA.debugLine="btn_no_add.Text=Main.loc.Localize(\"book-6\")";
mostCurrent._btn_no_add.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-6")));
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 224;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 225;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 226;BA.debugLine="If(pan_all.Visible=True)Then";
if ((mostCurrent._pan_all.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 227;BA.debugLine="pan_all_Click";
_pan_all_click();
 }else {
 //BA.debugLineNum = 229;BA.debugLine="If (hidBtn=1)Then";
if ((_hidbtn==1)) { 
 //BA.debugLineNum = 230;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 233;BA.debugLine="btn_back_Click";
_btn_back_click();
 };
 };
 //BA.debugLineNum = 236;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 238;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static int  _available_words(int _level) throws Exception{
int _count_wa = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur2 = null;
long _t1_now = 0L;
long _t2_word = 0L;
int _i = 0;
 //BA.debugLineNum = 143;BA.debugLine="Sub  available_words (level As Int) As Int";
 //BA.debugLineNum = 146;BA.debugLine="Dim count_wa As Int=0 '--تعداد لغات در دسترس";
_count_wa = (int) (0);
 //BA.debugLineNum = 147;BA.debugLine="Dim cur2 As Cursor";
_cur2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 148;BA.debugLine="Main.query=\"SELECT * FROM words WHERE book='\"& Ma";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE book='"+mostCurrent._main._book_name /*String*/ +"' AND lessen='"+mostCurrent._main._lessen_name /*String*/ +"' AND level="+BA.NumberToString((_level));
 //BA.debugLineNum = 149;BA.debugLine="cur2 = Main.sq_db.ExecQuery(Main.query)";
_cur2 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 151;BA.debugLine="Dim t1_now As Long = DateTime.DateTimeParse(DateT";
_t1_now = anywheresoftware.b4a.keywords.Common.DateTime.DateTimeParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 152;BA.debugLine="Dim t2_word As Long";
_t2_word = 0L;
 //BA.debugLineNum = 154;BA.debugLine="For i=0 To cur2.RowCount-1";
{
final int step7 = 1;
final int limit7 = (int) (_cur2.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 155;BA.debugLine="cur2.Position=i";
_cur2.setPosition(_i);
 //BA.debugLineNum = 156;BA.debugLine="t2_word=cur2.GetString(\"time_enter\")";
_t2_word = (long)(Double.parseDouble(_cur2.GetString("time_enter")));
 //BA.debugLineNum = 158;BA.debugLine="If ((t1_now-t2_word) > Main.time_list_level.get(";
if (((_t1_now-_t2_word)>(double)(BA.ObjectToNumber(mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .Get((int) (_level-1)))))) { 
 //BA.debugLineNum = 159;BA.debugLine="count_wa=count_wa+1";
_count_wa = (int) (_count_wa+1);
 };
 }
};
 //BA.debugLineNum = 163;BA.debugLine="Return count_wa";
if (true) return _count_wa;
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return 0;
}
public static String  _btn_add_click() throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Private Sub btn_add_Click";
 //BA.debugLineNum = 173;BA.debugLine="et_add_book.Enabled=False";
mostCurrent._et_add_book.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 174;BA.debugLine="et_add_book.Text=Main.book_name";
mostCurrent._et_add_book.setText(BA.ObjectToCharSequence(mostCurrent._main._book_name /*String*/ ));
 //BA.debugLineNum = 176;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 177;BA.debugLine="btn_add.Visible=False";
mostCurrent._btn_add.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 168;BA.debugLine="StartActivity(book_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._book_activity.getObject()));
 //BA.debugLineNum = 169;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static void  _btn_del_click() throws Exception{
ResumableSub_btn_del_Click rsub = new ResumableSub_btn_del_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btn_del_Click extends BA.ResumableSub {
public ResumableSub_btn_del_Click(ir.taravatgroup.mylitner.lessen_activity parent) {
this.parent = parent;
}
ir.taravatgroup.mylitner.lessen_activity parent;
anywheresoftware.b4a.objects.LabelWrapper _sender_tag = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _lessen = null;
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
 //BA.debugLineNum = 302;BA.debugLine="Dim sender_tag As Label";
_sender_tag = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 303;BA.debugLine="sender_tag=Sender";
_sender_tag = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 304;BA.debugLine="Dim lessen As Cursor = get_lessen_id(sender_tag.T";
_lessen = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_lessen = _get_lessen_id((int)(BA.ObjectToNumber(_sender_tag.getTag())));
 //BA.debugLineNum = 307;BA.debugLine="Msgbox2Async(Main.loc.Localize(\"lessen-4\"), Main.";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence(parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("lessen-4")),BA.ObjectToCharSequence(parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-10")),parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-14"),"",parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("main-15"),(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 308;BA.debugLine="Wait For Msgbox_Result (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 309;BA.debugLine="If Result = DialogResponse.POSITIVE Then";
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
 //BA.debugLineNum = 311;BA.debugLine="Main.query=\"DELETE FROM lessen WHERE name='\"&les";
parent.mostCurrent._main._query /*String*/  = "DELETE FROM lessen WHERE name='"+_lessen.GetString("name")+"'";
 //BA.debugLineNum = 312;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
parent.mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(parent.mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 313;BA.debugLine="Main.query=\"DELETE FROM words WHERE lessen='\"&le";
parent.mostCurrent._main._query /*String*/  = "DELETE FROM words WHERE lessen='"+_lessen.GetString("name")+"'";
 //BA.debugLineNum = 314;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
parent.mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(parent.mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 315;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"book-11\"),Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(parent.mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-11")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 316;BA.debugLine="custom_LS.Clear";
parent.mostCurrent._custom_ls._clear();
 //BA.debugLineNum = 317;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 320;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _result) throws Exception{
}
public static String  _btn_edit_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _sender_tag = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _lessen = null;
 //BA.debugLineNum = 288;BA.debugLine="Private Sub btn_edit_Click";
 //BA.debugLineNum = 289;BA.debugLine="Dim sender_tag As Label";
_sender_tag = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 290;BA.debugLine="sender_tag=Sender";
_sender_tag = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 291;BA.debugLine="current_id=sender_tag.Tag";
_current_id = (int)(BA.ObjectToNumber(_sender_tag.getTag()));
 //BA.debugLineNum = 292;BA.debugLine="Dim lessen As Cursor = get_lessen_id(sender_tag.T";
_lessen = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_lessen = _get_lessen_id((int)(BA.ObjectToNumber(_sender_tag.getTag())));
 //BA.debugLineNum = 294;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 295;BA.debugLine="et_add_lessen.Text=lessen.GetString(\"name\")";
mostCurrent._et_add_lessen.setText(BA.ObjectToCharSequence(_lessen.GetString("name")));
 //BA.debugLineNum = 296;BA.debugLine="et_add_book.Text=lessen.GetString(\"book\")";
mostCurrent._et_add_book.setText(BA.ObjectToCharSequence(_lessen.GetString("book")));
 //BA.debugLineNum = 298;BA.debugLine="edit_index=1";
_edit_index = (int) (1);
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _btn_no_add_click() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Private Sub btn_no_add_Click";
 //BA.debugLineNum = 213;BA.debugLine="pan_add.Visible=False";
mostCurrent._pan_add.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 214;BA.debugLine="btn_add.Visible=True";
mostCurrent._btn_add.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 215;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 216;BA.debugLine="End Sub";
return "";
}
public static String  _btn_yes_add_click() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _lessen = null;
 //BA.debugLineNum = 180;BA.debugLine="Private Sub btn_yes_add_Click";
 //BA.debugLineNum = 182;BA.debugLine="If(et_add_lessen.Text.Trim==\"\") Then";
if (((mostCurrent._et_add_lessen.getText().trim()).equals(""))) { 
 //BA.debugLineNum = 183;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"lessen-3\"),F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("lessen-3")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 189;BA.debugLine="If (edit_index==1)Then";
if ((_edit_index==1)) { 
 //BA.debugLineNum = 190;BA.debugLine="Dim lessen As Cursor = get_lessen_id(current_id";
_lessen = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
_lessen = _get_lessen_id(_current_id);
 //BA.debugLineNum = 193;BA.debugLine="Main.sq_db.ExecNonQuery2(\"UPDATE lessen SET nam";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("UPDATE lessen SET name = ? WHERE name= ? ",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._et_add_lessen.getText()),(Object)(_lessen.GetString("name"))}));
 //BA.debugLineNum = 194;BA.debugLine="Main.sq_db.ExecNonQuery2(\"UPDATE words SET less";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("UPDATE words SET lessen = ? WHERE lessen= ? ",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._et_add_lessen.getText()),(Object)(_lessen.GetString("name"))}));
 }else {
 //BA.debugLineNum = 199;BA.debugLine="Main.query=\"INSERT INTO lessen (name, book) VAL";
mostCurrent._main._query /*String*/  = "INSERT INTO lessen (name, book) VALUES ('"+mostCurrent._et_add_lessen.getText()+"','"+mostCurrent._et_add_book.getText()+"')";
 //BA.debugLineNum = 200;BA.debugLine="Main.sq_db.ExecNonQuery(Main.query)";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._main._query /*String*/ );
 //BA.debugLineNum = 201;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"book-8\"),Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-8")),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 205;BA.debugLine="btn_no_add_Click";
_btn_no_add_click();
 //BA.debugLineNum = 206;BA.debugLine="custom_LS.Clear";
mostCurrent._custom_ls._clear();
 //BA.debugLineNum = 207;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _custom_ls_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 248;BA.debugLine="Private Sub custom_LS_ItemClick (Index As Int, Val";
 //BA.debugLineNum = 249;BA.debugLine="Main.lessen_name= Value";
mostCurrent._main._lessen_name /*String*/  = BA.ObjectToString(_value);
 //BA.debugLineNum = 250;BA.debugLine="StartActivity(level_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._level_activity.getObject()));
 //BA.debugLineNum = 251;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _custom_ls_itemlongclick(int _index,Object _value) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _pp = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _vs = null;
anywheresoftware.b4a.objects.PanelWrapper _lbl2 = null;
 //BA.debugLineNum = 254;BA.debugLine="Private Sub custom_LS_ItemLongClick (Index As Int,";
 //BA.debugLineNum = 256;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 257;BA.debugLine="Dim pp As B4XView";
_pp = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 258;BA.debugLine="pp=custom_LS.GetPanel(Index)";
_pp = mostCurrent._custom_ls._getpanel(_index);
 //BA.debugLineNum = 261;BA.debugLine="For Each vs As View In pp.GetAllViewsRecursive";
_vs = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group4 = _pp.GetAllViewsRecursive();
final int groupLen4 = group4.getSize()
;int index4 = 0;
;
for (; index4 < groupLen4;index4++){
_vs = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group4.Get(index4)));
 //BA.debugLineNum = 262;BA.debugLine="If vs Is Panel Then";
if (_vs.getObjectOrNull() instanceof android.view.ViewGroup) { 
 //BA.debugLineNum = 263;BA.debugLine="Dim lbl2 As Panel = vs";
_lbl2 = new anywheresoftware.b4a.objects.PanelWrapper();
_lbl2 = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_vs.getObject()));
 //BA.debugLineNum = 265;BA.debugLine="lbl2.Visible=True";
_lbl2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 266;BA.debugLine="hidBtn=1";
_hidbtn = (int) (1);
 };
 }
};
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.sql.SQL.CursorWrapper  _get_lessen_id(int _id) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur3 = null;
 //BA.debugLineNum = 281;BA.debugLine="Sub get_lessen_id(id As Int) As Cursor";
 //BA.debugLineNum = 282;BA.debugLine="Dim cur3 As Cursor";
_cur3 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 283;BA.debugLine="cur3 = Main.sq_db.ExecQuery(\"SELECT * FROM lessen";
_cur3 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT * FROM lessen WHERE id="+BA.NumberToString(_id))));
 //BA.debugLineNum = 284;BA.debugLine="cur3.Position=0";
_cur3.setPosition((int) (0));
 //BA.debugLineNum = 285;BA.debugLine="Return cur3";
if (true) return _cur3;
 //BA.debugLineNum = 286;BA.debugLine="End Sub";
return null;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 18;BA.debugLine="Private pan_add As Panel";
mostCurrent._pan_add = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private et_add_book As EditText";
mostCurrent._et_add_book = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private et_add_lessen As EditText";
mostCurrent._et_add_lessen = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btn_add As Button";
mostCurrent._btn_add = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lbl_path As Label";
mostCurrent._lbl_path = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private custom_LS As CustomListView";
mostCurrent._custom_ls = new b4a.example3.customlistview();
 //BA.debugLineNum = 29;BA.debugLine="Private lbl_level As Label";
mostCurrent._lbl_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lbl_avail_level As Label";
mostCurrent._lbl_avail_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lbl_count_level As Label";
mostCurrent._lbl_count_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lbl_var5 As Label";
mostCurrent._lbl_var5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private btn_yes_add As Button";
mostCurrent._btn_yes_add = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btn_no_add As Button";
mostCurrent._btn_no_add = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim hidBtn As Int=0";
_hidbtn = (int) (0);
 //BA.debugLineNum = 39;BA.debugLine="Dim edit_index As Int=0";
_edit_index = (int) (0);
 //BA.debugLineNum = 40;BA.debugLine="Dim current_id As Int";
_current_id = 0;
 //BA.debugLineNum = 41;BA.debugLine="Private btn_del As Button";
mostCurrent._btn_del = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btn_edit As Button";
mostCurrent._btn_edit = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private pan_pup As Panel";
mostCurrent._pan_pup = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _pan_add_click() throws Exception{
 //BA.debugLineNum = 244;BA.debugLine="Private Sub pan_add_Click";
 //BA.debugLineNum = 246;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 221;BA.debugLine="btn_no_add_Click";
_btn_no_add_click();
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public static String  _pan_pup_click() throws Exception{
 //BA.debugLineNum = 276;BA.debugLine="Private Sub pan_pup_Click";
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
