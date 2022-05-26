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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.sql.SQL _sq_db = null;
public static String _query = "";
public static int _current_word_id = 0;
public static String _current_word = "";
public static String _book_name = "";
public static String _lessen_name = "";
public static int _level_name = 0;
public static anywheresoftware.b4a.objects.collections.List _time_list_level = null;
public static anywheresoftware.b4a.objects.collections.List _level_list_name = null;
public static int _last_level = 0;
public static String _end_page = "";
public static int _app_color = 0;
public static anywheresoftware.b4a.keywords.constants.TypefaceWrapper _app_font = null;
public static String _app_font_name = "";
public static int _app_font_size = 0;
public static String _app_lang = "";
public static String _app_version = "";
public static b4a.example.localizator _loc = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_books = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_lessens = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_words = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_msg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_matn_msg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_title_msg = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_info = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_setting = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scroll_view = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_items = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label16 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label14 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public b4a.example.dateutils _dateutils = null;
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
public ir.taravatgroup.mylitner.singl_word_activity _singl_word_activity = null;
public ir.taravatgroup.mylitner.starter _starter = null;
public ir.taravatgroup.mylitner.word_activity _word_activity = null;
public ir.taravatgroup.mylitner.b4xcollections _b4xcollections = null;
public ir.taravatgroup.mylitner.xuiviewsutils _xuiviewsutils = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (book_activity.mostCurrent != null);
vis = vis | (lessen_activity.mostCurrent != null);
vis = vis | (level_activity.mostCurrent != null);
vis = vis | (all_words_activity.mostCurrent != null);
vis = vis | (store_activity.mostCurrent != null);
vis = vis | (add_voice_activity.mostCurrent != null);
vis = vis | (all_exam_activity.mostCurrent != null);
vis = vis | (dictionary_activity.mostCurrent != null);
vis = vis | (exam_activity.mostCurrent != null);
vis = vis | (help_activity.mostCurrent != null);
vis = vis | (init_exam_activity.mostCurrent != null);
vis = vis | (result_activity.mostCurrent != null);
vis = vis | (setting_activity.mostCurrent != null);
vis = vis | (setting_time_activity.mostCurrent != null);
vis = vis | (singl_word_activity.mostCurrent != null);
vis = vis | (word_activity.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur_chek = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur_setting = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur4 = null;
int _k = 0;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 73;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 74;BA.debugLine="Activity.LoadLayout(\"home_layout\")";
mostCurrent._activity.LoadLayout("home_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 75;BA.debugLine="scroll_view.Panel.LoadLayout(\"scroll_item_hom\")";
mostCurrent._scroll_view.getPanel().LoadLayout("scroll_item_hom",mostCurrent.activityBA);
 //BA.debugLineNum = 76;BA.debugLine="If(File.Exists(File.DirInternal,\"db3.db\")) Then";
if ((anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"db3.db"))) { 
 //BA.debugLineNum = 77;BA.debugLine="sq_db.Initialize(File.DirInternal,\"db3.db\",True)";
_sq_db.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"db3.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 79;BA.debugLine="Dim cur_chek As Cursor";
_cur_chek = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Try";
try { //BA.debugLineNum = 81;BA.debugLine="cur_chek=sq_db.ExecQuery(\"SELECT * FROM exam\")";
_cur_chek = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sq_db.ExecQuery("SELECT * FROM exam")));
 //BA.debugLineNum = 82;BA.debugLine="cur_chek.Position=0";
_cur_chek.setPosition((int) (0));
 } 
       catch (Exception e10) {
			processBA.setLastException(e10); //BA.debugLineNum = 85;BA.debugLine="sq_db.ExecNonQuery(\"CREATE TABLE 'exam' ('id'	I";
_sq_db.ExecNonQuery("CREATE TABLE 'exam' ('id'	INTEGER Not Null PRIMARY KEY AUTOINCREMENT UNIQUE,'date'	TEXT,'all_qust'	INTEGER,'correct'	INTEGER,'incorrect'	INTEGER,'state'	INTEGER);");
 //BA.debugLineNum = 86;BA.debugLine="ToastMessageShow(\"creat exam db\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("creat exam db"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 95;BA.debugLine="File.Copy(File.DirAssets,\"db3.db\",File.DirIntern";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"db3.db",anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"db3.db");
 //BA.debugLineNum = 96;BA.debugLine="sq_db.Initialize(File.DirInternal,\"db3.db\",True)";
_sq_db.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"db3.db",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 99;BA.debugLine="time_list_level.Initialize";
_time_list_level.Initialize();
 //BA.debugLineNum = 100;BA.debugLine="level_list_name.Initialize";
_level_list_name.Initialize();
 //BA.debugLineNum = 102;BA.debugLine="end_page=\"home\"";
_end_page = "home";
 //BA.debugLineNum = 105;BA.debugLine="Dim cur_setting As Cursor";
_cur_setting = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 106;BA.debugLine="cur_setting=sq_db.ExecQuery(\"SELECT * FROM settin";
_cur_setting = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sq_db.ExecQuery("SELECT * FROM setting")));
 //BA.debugLineNum = 107;BA.debugLine="cur_setting.Position=0 '--font";
_cur_setting.setPosition((int) (0));
 //BA.debugLineNum = 108;BA.debugLine="app_font_name=cur_setting.GetString(\"option_value";
_app_font_name = _cur_setting.GetString("option_value");
 //BA.debugLineNum = 112;BA.debugLine="cur_setting.Position=1 '--app_color";
_cur_setting.setPosition((int) (1));
 //BA.debugLineNum = 113;BA.debugLine="app_color=cur_setting.Getint(\"option_value\")";
_app_color = _cur_setting.GetInt("option_value");
 //BA.debugLineNum = 116;BA.debugLine="cur_setting.Position=2 '--app_version";
_cur_setting.setPosition((int) (2));
 //BA.debugLineNum = 118;BA.debugLine="app_version=Application.VersionName";
_app_version = anywheresoftware.b4a.keywords.Common.Application.getVersionName();
 //BA.debugLineNum = 124;BA.debugLine="cur_setting.Position=5 '--font_size";
_cur_setting.setPosition((int) (5));
 //BA.debugLineNum = 125;BA.debugLine="app_font_size=cur_setting.Getint(\"option_value\")";
_app_font_size = _cur_setting.GetInt("option_value");
 //BA.debugLineNum = 127;BA.debugLine="cur_setting.Position=6 '--lang";
_cur_setting.setPosition((int) (6));
 //BA.debugLineNum = 128;BA.debugLine="app_lang=cur_setting.GetString(\"option_value\")";
_app_lang = _cur_setting.GetString("option_value");
 //BA.debugLineNum = 129;BA.debugLine="loc.Initialize(File.DirAssets,\"lang.db\")";
_loc._initialize(processBA,anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"lang.db");
 //BA.debugLineNum = 130;BA.debugLine="loc.ForceLocale(app_lang)";
_loc._forcelocale(_app_lang);
 //BA.debugLineNum = 133;BA.debugLine="If (app_lang==\"fa\")Then";
if (((_app_lang).equals("fa"))) { 
 //BA.debugLineNum = 134;BA.debugLine="app_font = Typeface.LoadFromAssets(app_font_name";
_app_font = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets(_app_font_name)));
 }else {
 //BA.debugLineNum = 136;BA.debugLine="app_font = Typeface.DEFAULT";
_app_font = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT));
 };
 //BA.debugLineNum = 140;BA.debugLine="Activity.Color=app_color";
mostCurrent._activity.setColor(_app_color);
 //BA.debugLineNum = 146;BA.debugLine="tab_updat";
_tab_updat();
 //BA.debugLineNum = 149;BA.debugLine="Dim cur4 As Cursor";
_cur4 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 150;BA.debugLine="query=\"SELECT * FROM level\"";
_query = "SELECT * FROM level";
 //BA.debugLineNum = 151;BA.debugLine="cur4 = sq_db.ExecQuery(query)";
_cur4 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sq_db.ExecQuery(_query)));
 //BA.debugLineNum = 152;BA.debugLine="For k=0 To cur4.RowCount-1";
{
final int step44 = 1;
final int limit44 = (int) (_cur4.getRowCount()-1);
_k = (int) (0) ;
for (;_k <= limit44 ;_k = _k + step44 ) {
 //BA.debugLineNum = 153;BA.debugLine="cur4.Position=k";
_cur4.setPosition(_k);
 //BA.debugLineNum = 154;BA.debugLine="time_list_level.Add(cur4.GetString(\"time\"))";
_time_list_level.Add((Object)(_cur4.GetString("time")));
 //BA.debugLineNum = 155;BA.debugLine="level_list_name.Add(cur4.GetString(\"name\"))";
_level_list_name.Add((Object)(_cur4.GetString("name")));
 }
};
 //BA.debugLineNum = 158;BA.debugLine="last_level=level_list_name.Get(level_list_name.Si";
_last_level = (int)(BA.ObjectToNumber(_level_list_name.Get((int) (_level_list_name.getSize()-1))));
 //BA.debugLineNum = 160;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group50 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen50 = group50.getSize()
;int index50 = 0;
;
for (; index50 < groupLen50;index50++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group50.Get(index50)));
 //BA.debugLineNum = 162;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 163;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 164;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 166;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 167;BA.debugLine="lbl.Typeface = app_font";
_lbl.setTypeface((android.graphics.Typeface)(_app_font.getObject()));
 //BA.debugLineNum = 168;BA.debugLine="lbl.TextSize=app_font_size";
_lbl.setTextSize((float) (_app_font_size));
 };
 }
};
 //BA.debugLineNum = 172;BA.debugLine="Label1.Text=loc.Localize(\"main-1\")";
mostCurrent._label1.setText(BA.ObjectToCharSequence(_loc._localize("main-1")));
 //BA.debugLineNum = 173;BA.debugLine="Label2.Text=loc.Localize(\"main-2\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(_loc._localize("main-2")));
 //BA.debugLineNum = 174;BA.debugLine="Label4.Text=loc.Localize(\"main-3\")";
mostCurrent._label4.setText(BA.ObjectToCharSequence(_loc._localize("main-3")));
 //BA.debugLineNum = 175;BA.debugLine="Label6.Text=loc.Localize(\"main-4\")";
mostCurrent._label6.setText(BA.ObjectToCharSequence(_loc._localize("main-4")));
 //BA.debugLineNum = 176;BA.debugLine="lbl_title_msg.Text=loc.Localize(\"main-5\")";
mostCurrent._lbl_title_msg.setText(BA.ObjectToCharSequence(_loc._localize("main-5")));
 //BA.debugLineNum = 177;BA.debugLine="lbl_matn_msg.Text=loc.Localize(\"main-6\")";
mostCurrent._lbl_matn_msg.setText(BA.ObjectToCharSequence(_loc._localize("main-6")));
 //BA.debugLineNum = 178;BA.debugLine="Label8.Text=loc.Localize(\"main-2\")";
mostCurrent._label8.setText(BA.ObjectToCharSequence(_loc._localize("main-2")));
 //BA.debugLineNum = 179;BA.debugLine="Label16.Text=loc.Localize(\"main-7\")";
mostCurrent._label16.setText(BA.ObjectToCharSequence(_loc._localize("main-7")));
 //BA.debugLineNum = 180;BA.debugLine="Label14.Text=loc.Localize(\"main-8\")";
mostCurrent._label14.setText(BA.ObjectToCharSequence(_loc._localize("main-8")));
 //BA.debugLineNum = 181;BA.debugLine="Label12.Text= \"کتابدونی\"  ''loc.Localize(\"main-9\"";
mostCurrent._label12.setText(BA.ObjectToCharSequence("کتابدونی"));
 //BA.debugLineNum = 182;BA.debugLine="Label10.Text=loc.Localize(\"main-10\")";
mostCurrent._label10.setText(BA.ObjectToCharSequence(_loc._localize("main-10")));
 //BA.debugLineNum = 183;BA.debugLine="Label3.Text=loc.Localize(\"main-11\")";
mostCurrent._label3.setText(BA.ObjectToCharSequence(_loc._localize("main-11")));
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 255;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 256;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 257;BA.debugLine="exit_sub";
_exit_sub();
 //BA.debugLineNum = 258;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 260;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 262;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 204;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 197;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _btn_close_msg_click() throws Exception{
 //BA.debugLineNum = 280;BA.debugLine="Private Sub btn_close_msg_Click";
 //BA.debugLineNum = 281;BA.debugLine="pan_msg.Visible=False";
mostCurrent._pan_msg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="End Sub";
return "";
}
public static String  _btn_info_click() throws Exception{
 //BA.debugLineNum = 271;BA.debugLine="Private Sub btn_info_Click";
 //BA.debugLineNum = 272;BA.debugLine="lbl_title_msg.Text=loc.Localize(\"main-5\")";
mostCurrent._lbl_title_msg.setText(BA.ObjectToCharSequence(_loc._localize("main-5")));
 //BA.debugLineNum = 273;BA.debugLine="lbl_matn_msg.Text=loc.Localize(\"main-16\")&CRLF &l";
mostCurrent._lbl_matn_msg.setText(BA.ObjectToCharSequence(_loc._localize("main-16")+anywheresoftware.b4a.keywords.Common.CRLF+_loc._localize("main-17")+anywheresoftware.b4a.keywords.Common.CRLF+_loc._localize("main-18")+_app_version+anywheresoftware.b4a.keywords.Common.CRLF+_loc._localize("main-19")));
 //BA.debugLineNum = 275;BA.debugLine="pan_msg.Visible=True";
mostCurrent._pan_msg.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
return "";
}
public static String  _btn_setting_click() throws Exception{
 //BA.debugLineNum = 297;BA.debugLine="Private Sub btn_setting_Click";
 //BA.debugLineNum = 298;BA.debugLine="StartActivity(setting_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._setting_activity.getObject()));
 //BA.debugLineNum = 299;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public static boolean  _exit_sub() throws Exception{
int _ext = 0;
 //BA.debugLineNum = 242;BA.debugLine="Private Sub exit_sub As Boolean";
 //BA.debugLineNum = 243;BA.debugLine="Dim ext As Int";
_ext = 0;
 //BA.debugLineNum = 244;BA.debugLine="ext = Msgbox2 (loc.Localize(\"main-13\"), loc.Local";
_ext = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_loc._localize("main-13")),BA.ObjectToCharSequence(_loc._localize("main-12")),_loc._localize("main-14"),_loc._localize("main-15"),"",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 //BA.debugLineNum = 245;BA.debugLine="If ext = DialogResponse.POSITIVE Then";
if (_ext==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 246;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 247;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 249;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 48;BA.debugLine="Private lbl_books As Label";
mostCurrent._lbl_books = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lbl_lessens As Label";
mostCurrent._lbl_lessens = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lbl_words As Label";
mostCurrent._lbl_words = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private pan_msg As Panel";
mostCurrent._pan_msg = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private lbl_matn_msg As Label";
mostCurrent._lbl_matn_msg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private lbl_title_msg As Label";
mostCurrent._lbl_title_msg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private btn_info As Button";
mostCurrent._btn_info = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private btn_setting As Button";
mostCurrent._btn_setting = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private scroll_view As ScrollView";
mostCurrent._scroll_view = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private pan_items As Panel";
mostCurrent._pan_items = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private Label16 As Label";
mostCurrent._label16 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private Label14 As Label";
mostCurrent._label14 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private Label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private Label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_books_click() throws Exception{
 //BA.debugLineNum = 312;BA.debugLine="Private Sub lbl_books_Click";
 //BA.debugLineNum = 313;BA.debugLine="pan_book_Click";
_pan_book_click();
 //BA.debugLineNum = 314;BA.debugLine="End Sub";
return "";
}
public static String  _lbl_words_click() throws Exception{
 //BA.debugLineNum = 308;BA.debugLine="Private Sub lbl_words_Click";
 //BA.debugLineNum = 309;BA.debugLine="pan_words_Click";
_pan_words_click();
 //BA.debugLineNum = 310;BA.debugLine="End Sub";
return "";
}
public static String  _pan_book_click() throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Private Sub pan_book_Click";
 //BA.debugLineNum = 236;BA.debugLine="StartActivity(book_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._book_activity.getObject()));
 //BA.debugLineNum = 237;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _pan_dictionary_click() throws Exception{
 //BA.debugLineNum = 302;BA.debugLine="Private Sub pan_dictionary_Click";
 //BA.debugLineNum = 303;BA.debugLine="current_word=\"\"";
_current_word = "";
 //BA.debugLineNum = 304;BA.debugLine="StartActivity(dictionary_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._dictionary_activity.getObject()));
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public static String  _pan_exam_click() throws Exception{
 //BA.debugLineNum = 284;BA.debugLine="Private Sub pan_exam_Click";
 //BA.debugLineNum = 285;BA.debugLine="StartActivity(all_exam_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._all_exam_activity.getObject()));
 //BA.debugLineNum = 286;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _pan_help_click() throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Private Sub pan_help_Click";
 //BA.debugLineNum = 231;BA.debugLine="StartActivity(help_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._help_activity.getObject()));
 //BA.debugLineNum = 232;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static String  _pan_msg_click() throws Exception{
 //BA.debugLineNum = 293;BA.debugLine="Private Sub pan_msg_Click";
 //BA.debugLineNum = 294;BA.debugLine="btn_close_msg_Click";
_btn_close_msg_click();
 //BA.debugLineNum = 295;BA.debugLine="End Sub";
return "";
}
public static String  _pan_msg_show_click() throws Exception{
 //BA.debugLineNum = 316;BA.debugLine="Private Sub pan_msg_show_Click";
 //BA.debugLineNum = 318;BA.debugLine="End Sub";
return "";
}
public static String  _pan_store_click() throws Exception{
 //BA.debugLineNum = 320;BA.debugLine="Private Sub pan_store_Click";
 //BA.debugLineNum = 322;BA.debugLine="StartActivity(store_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._store_activity.getObject()));
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return "";
}
public static String  _pan_words_click() throws Exception{
 //BA.debugLineNum = 265;BA.debugLine="Private Sub pan_words_Click";
 //BA.debugLineNum = 266;BA.debugLine="end_page=\"home\"";
_end_page = "home";
 //BA.debugLineNum = 267;BA.debugLine="StartActivity(all_words_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._all_words_activity.getObject()));
 //BA.debugLineNum = 269;BA.debugLine="End Sub";
return "";
}
public static String  _phonelanguage() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 191;BA.debugLine="Sub PhoneLanguage As String";
 //BA.debugLineNum = 192;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 193;BA.debugLine="r.Target = r.RunStaticMethod(\"java.util.Locale\",";
_r.Target = _r.RunStaticMethod("java.util.Locale","getDefault",(Object[])(anywheresoftware.b4a.keywords.Common.Null),(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 194;BA.debugLine="Return r.RunMethod(\"getDisplayName\")";
if (true) return BA.ObjectToString(_r.RunMethod("getDisplayName"));
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.dateutils._process_globals();
main._process_globals();
book_activity._process_globals();
lessen_activity._process_globals();
level_activity._process_globals();
all_words_activity._process_globals();
store_activity._process_globals();
add_voice_activity._process_globals();
all_exam_activity._process_globals();
dictionary_activity._process_globals();
exam_activity._process_globals();
help_activity._process_globals();
init_exam_activity._process_globals();
result_activity._process_globals();
setting_activity._process_globals();
setting_time_activity._process_globals();
singl_word_activity._process_globals();
starter._process_globals();
word_activity._process_globals();
b4xcollections._process_globals();
xuiviewsutils._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim sq_db As SQL";
_sq_db = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 19;BA.debugLine="Dim query As String";
_query = "";
 //BA.debugLineNum = 20;BA.debugLine="Dim current_word_id As Int";
_current_word_id = 0;
 //BA.debugLineNum = 21;BA.debugLine="Dim current_word As String";
_current_word = "";
 //BA.debugLineNum = 22;BA.debugLine="Dim book_name As String = \"\"";
_book_name = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim lessen_name As String = \"\"";
_lessen_name = "";
 //BA.debugLineNum = 24;BA.debugLine="Dim level_name As Int =0";
_level_name = (int) (0);
 //BA.debugLineNum = 26;BA.debugLine="Dim time_list_level As List";
_time_list_level = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 27;BA.debugLine="Dim level_list_name As List";
_level_list_name = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 28;BA.debugLine="Dim last_level As Int";
_last_level = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim end_page As String";
_end_page = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim app_color As Int";
_app_color = 0;
 //BA.debugLineNum = 34;BA.debugLine="Dim app_font As Typeface";
_app_font = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim app_font_name As String";
_app_font_name = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim app_font_size As Int";
_app_font_size = 0;
 //BA.debugLineNum = 37;BA.debugLine="Dim app_lang As String";
_app_lang = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim app_version As String";
_app_version = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim loc As Localizator";
_loc = new b4a.example.localizator();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _tab_updat() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur1 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur3 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur2 = null;
 //BA.debugLineNum = 206;BA.debugLine="Sub tab_updat";
 //BA.debugLineNum = 207;BA.debugLine="Dim cur1 As Cursor";
_cur1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 208;BA.debugLine="cur1 = sq_db.ExecQuery(\"SELECT * FROM books\")";
_cur1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sq_db.ExecQuery("SELECT * FROM books")));
 //BA.debugLineNum = 210;BA.debugLine="Dim cur3 As Cursor";
_cur3 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 211;BA.debugLine="query=\"SELECT * FROM lessen\"";
_query = "SELECT * FROM lessen";
 //BA.debugLineNum = 213;BA.debugLine="cur3 = sq_db.ExecQuery(query)";
_cur3 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sq_db.ExecQuery(_query)));
 //BA.debugLineNum = 218;BA.debugLine="Dim cur2 As Cursor";
_cur2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 219;BA.debugLine="cur2 = sq_db.ExecQuery(\"SELECT * FROM words\")";
_cur2 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sq_db.ExecQuery("SELECT * FROM words")));
 //BA.debugLineNum = 220;BA.debugLine="lbl_books.Text=cur1.RowCount";
mostCurrent._lbl_books.setText(BA.ObjectToCharSequence(_cur1.getRowCount()));
 //BA.debugLineNum = 221;BA.debugLine="lbl_lessens.Text=cur3.RowCount";
mostCurrent._lbl_lessens.setText(BA.ObjectToCharSequence(_cur3.getRowCount()));
 //BA.debugLineNum = 222;BA.debugLine="lbl_words.Text=cur2.RowCount";
mostCurrent._lbl_words.setText(BA.ObjectToCharSequence(_cur2.getRowCount()));
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
}
