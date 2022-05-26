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

public class level_activity extends Activity implements B4AActivity{
	public static level_activity mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.level_activity");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (level_activity).");
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
		activityBA = new BA(this, layout, processBA, "ir.taravatgroup.mylitner", "ir.taravatgroup.mylitner.level_activity");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ir.taravatgroup.mylitner.level_activity", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (level_activity) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (level_activity) Resume **");
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
		return level_activity.class;
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
            BA.LogInfo("** Activity (level_activity) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (level_activity) Pause event (activity is not paused). **");
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
            level_activity mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (level_activity) Resume **");
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
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.collections.List _count_w = null;
public static int _count_avail_all = 0;
public static int _count_ended = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_menu = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_menu = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_all = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_add_word = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_add_answer = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_add_question = null;
public anywheresoftware.b4a.objects.WorkbookWrapper _workbook1 = null;
public anywheresoftware.b4a.objects.WorkbookWrapper.SheetWrapper _sheet1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_path = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_add_synonim = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et_add_codeing = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_all = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_available_all = null;
public b4a.example3.customlistview _custom_lv_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_ended = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_count_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_avail_level = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbl_level = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pan_ended = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_add_word = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_add_excel = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_no_add = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_yes_add = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btn_add_by_voice = null;
public b4a.example.dateutils _dateutils = null;
public ir.taravatgroup.mylitner.main _main = null;
public ir.taravatgroup.mylitner.book_activity _book_activity = null;
public ir.taravatgroup.mylitner.lessen_activity _lessen_activity = null;
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
anywheresoftware.b4a.objects.drawable.BitmapDrawable _pic_level_1 = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _pic_level_2 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur1 = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur3 = null;
int _i = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur5 = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 59;BA.debugLine="Activity.LoadLayout(\"level_layout\")";
mostCurrent._activity.LoadLayout("level_layout",mostCurrent.activityBA);
 //BA.debugLineNum = 61;BA.debugLine="Panel1.Color=Main.app_color";
mostCurrent._panel1.setColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 63;BA.debugLine="btn_menu.TextColor=Main.app_color";
mostCurrent._btn_menu.setTextColor(mostCurrent._main._app_color /*int*/ );
 //BA.debugLineNum = 67;BA.debugLine="lbl_path.Text=Main.book_name&\" / \"&Main.lessen_na";
mostCurrent._lbl_path.setText(BA.ObjectToCharSequence(mostCurrent._main._book_name /*String*/ +" / "+mostCurrent._main._lessen_name /*String*/ ));
 //BA.debugLineNum = 69;BA.debugLine="Main.end_page=\"level\"";
mostCurrent._main._end_page /*String*/  = "level";
 //BA.debugLineNum = 71;BA.debugLine="Dim pic_level_1 As BitmapDrawable";
_pic_level_1 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 72;BA.debugLine="pic_level_1.Initialize(LoadBitmap(File.DirAssets,";
_pic_level_1.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"box.png").getObject()));
 //BA.debugLineNum = 73;BA.debugLine="pic_level_1.Gravity = Gravity.FILL";
_pic_level_1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 74;BA.debugLine="Dim pic_level_2 As BitmapDrawable";
_pic_level_2 = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 75;BA.debugLine="pic_level_2.Initialize(LoadBitmap(File.DirAssets,";
_pic_level_2.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"box2.png").getObject()));
 //BA.debugLineNum = 76;BA.debugLine="pic_level_2.Gravity = Gravity.FILL";
_pic_level_2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 79;BA.debugLine="count_w.Initialize";
mostCurrent._count_w.Initialize();
 //BA.debugLineNum = 81;BA.debugLine="Main.query=\"SELECT * FROM level\"";
mostCurrent._main._query /*String*/  = "SELECT * FROM level";
 //BA.debugLineNum = 82;BA.debugLine="Dim cur1 As Cursor";
_cur1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Dim cur3 As Cursor";
_cur3 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 84;BA.debugLine="cur1 = Main.sq_db.ExecQuery(Main.query)";
_cur1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 85;BA.debugLine="For i=0 To cur1.RowCount-1";
{
final int step17 = 1;
final int limit17 = (int) (_cur1.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit17 ;_i = _i + step17 ) {
 //BA.debugLineNum = 86;BA.debugLine="cur1.Position=i";
_cur1.setPosition(_i);
 //BA.debugLineNum = 89;BA.debugLine="Main.query=\"SELECT COUNT(id) FROM words WHERE bo";
mostCurrent._main._query /*String*/  = "SELECT COUNT(id) FROM words WHERE book='"+mostCurrent._main._book_name /*String*/ +"' AND lessen='"+mostCurrent._main._lessen_name /*String*/ +"' AND level='"+BA.NumberToString(_cur1.GetInt("name"))+"'";
 //BA.debugLineNum = 91;BA.debugLine="cur3 = Main.sq_db.ExecQuery(Main.query)";
_cur3 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 92;BA.debugLine="cur3.Position=0";
_cur3.setPosition((int) (0));
 //BA.debugLineNum = 95;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = mostCurrent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 96;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, 100%x, 86dip)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (86)));
 //BA.debugLineNum = 98;BA.debugLine="p.LoadLayout(\"item_level\")";
_p.LoadLayout("item_level",mostCurrent.activityBA);
 //BA.debugLineNum = 100;BA.debugLine="lbl_level.Text=cur1.GetString(\"name\")";
mostCurrent._lbl_level.setText(BA.ObjectToCharSequence(_cur1.GetString("name")));
 //BA.debugLineNum = 101;BA.debugLine="lbl_avail_level.Text= available_words(i)";
mostCurrent._lbl_avail_level.setText(BA.ObjectToCharSequence(_available_words(_i)));
 //BA.debugLineNum = 102;BA.debugLine="lbl_count_level.Text=cur3.GetInt(\"COUNT(id)\")";
mostCurrent._lbl_count_level.setText(BA.ObjectToCharSequence(_cur3.GetInt("COUNT(id)")));
 //BA.debugLineNum = 103;BA.debugLine="If(lbl_avail_level.Text==0)Then";
if (((mostCurrent._lbl_avail_level.getText()).equals(BA.NumberToString(0)))) { 
 //BA.debugLineNum = 104;BA.debugLine="lbl_level.Background=pic_level_2";
mostCurrent._lbl_level.setBackground((android.graphics.drawable.Drawable)(_pic_level_2.getObject()));
 }else {
 //BA.debugLineNum = 106;BA.debugLine="lbl_level.Background=pic_level_1";
mostCurrent._lbl_level.setBackground((android.graphics.drawable.Drawable)(_pic_level_1.getObject()));
 };
 //BA.debugLineNum = 108;BA.debugLine="Custom_LV_level.Add(p, i)";
mostCurrent._custom_lv_level._add(_p,(Object)(_i));
 //BA.debugLineNum = 111;BA.debugLine="count_avail_all=count_avail_all+available_words(";
_count_avail_all = (int) (_count_avail_all+_available_words(_i));
 //BA.debugLineNum = 112;BA.debugLine="count_w.Add(cur3.GetInt(\"COUNT(id)\"))";
mostCurrent._count_w.Add((Object)(_cur3.GetInt("COUNT(id)")));
 }
};
 //BA.debugLineNum = 117;BA.debugLine="lbl_all.Text=Main.loc.Localize(\"level-5\")&\" : \"&c";
mostCurrent._lbl_all.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-5")+" : "+BA.NumberToString(_count_words_lessen())));
 //BA.debugLineNum = 118;BA.debugLine="lbl_available_all.Text=Main.loc.Localize(\"level-4";
mostCurrent._lbl_available_all.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-4")+" : "+BA.NumberToString(_count_avail_all)));
 //BA.debugLineNum = 121;BA.debugLine="Dim cur5 As Cursor";
_cur5 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 122;BA.debugLine="cur5 = Main.sq_db.ExecQuery(\"SELECT COUNT(id) FRO";
_cur5 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT COUNT(id) FROM words WHERE book='"+mostCurrent._main._book_name /*String*/ +"' AND lessen='"+mostCurrent._main._lessen_name /*String*/ +"' AND level=100")));
 //BA.debugLineNum = 123;BA.debugLine="cur5.Position=0";
_cur5.setPosition((int) (0));
 //BA.debugLineNum = 124;BA.debugLine="count_ended=cur5.GetInt(\"COUNT(id)\")";
_count_ended = _cur5.GetInt("COUNT(id)");
 //BA.debugLineNum = 125;BA.debugLine="lbl_ended.Text=Main.loc.Localize(\"level-6\")&\" : \"";
mostCurrent._lbl_ended.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-6")+" : "+BA.NumberToString(_count_ended)));
 //BA.debugLineNum = 129;BA.debugLine="For Each v As View In Activity.GetAllViewsRecursi";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group44 = mostCurrent._activity.GetAllViewsRecursive();
final int groupLen44 = group44.getSize()
;int index44 = 0;
;
for (; index44 < groupLen44;index44++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group44.Get(index44)));
 //BA.debugLineNum = 130;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 131;BA.debugLine="If (v Is Button) Then";
if ((_v.getObjectOrNull() instanceof android.widget.Button)) { 
 //BA.debugLineNum = 132;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 134;BA.debugLine="Dim lbl As Label = v";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 135;BA.debugLine="lbl.Typeface = Main.app_font";
_lbl.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 136;BA.debugLine="lbl.TextSize=Main.app_font_size";
_lbl.setTextSize((float) (mostCurrent._main._app_font_size /*int*/ ));
 };
 }
};
 //BA.debugLineNum = 139;BA.debugLine="btn_add_word.Typeface=Main.app_font";
mostCurrent._btn_add_word.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 140;BA.debugLine="btn_add_excel.Typeface=Main.app_font";
mostCurrent._btn_add_excel.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 141;BA.debugLine="btn_add_by_voice.Typeface=Main.app_font";
mostCurrent._btn_add_by_voice.setTypeface((android.graphics.Typeface)(mostCurrent._main._app_font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 142;BA.debugLine="Label3.TextSize=18";
mostCurrent._label3.setTextSize((float) (18));
 //BA.debugLineNum = 143;BA.debugLine="lbl_path.TextSize=12";
mostCurrent._lbl_path.setTextSize((float) (12));
 //BA.debugLineNum = 146;BA.debugLine="Label3.Text=Main.loc.Localize(\"level-1\")";
mostCurrent._label3.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-1")));
 //BA.debugLineNum = 147;BA.debugLine="Label2.Text=Main.loc.Localize(\"level-2\")";
mostCurrent._label2.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-2")));
 //BA.debugLineNum = 148;BA.debugLine="btn_add_word.Text=Main.loc.Localize(\"level-2\")";
mostCurrent._btn_add_word.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-2")));
 //BA.debugLineNum = 149;BA.debugLine="btn_add_excel.Text=Main.loc.Localize(\"level-3\")";
mostCurrent._btn_add_excel.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-3")));
 //BA.debugLineNum = 150;BA.debugLine="btn_yes_add.Text=Main.loc.Localize(\"book-5\")";
mostCurrent._btn_yes_add.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-5")));
 //BA.debugLineNum = 151;BA.debugLine="btn_no_add.Text=Main.loc.Localize(\"book-6\")";
mostCurrent._btn_no_add.setText(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-6")));
 //BA.debugLineNum = 152;BA.debugLine="et_add_question.Hint=Main.loc.Localize(\"level-7\")";
mostCurrent._et_add_question.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-7"));
 //BA.debugLineNum = 153;BA.debugLine="et_add_answer.Hint=Main.loc.Localize(\"level-8\")";
mostCurrent._et_add_answer.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-8"));
 //BA.debugLineNum = 154;BA.debugLine="et_add_synonim.Hint=Main.loc.Localize(\"level-9\")";
mostCurrent._et_add_synonim.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-9"));
 //BA.debugLineNum = 155;BA.debugLine="et_add_codeing.Hint=Main.loc.Localize(\"level-10\")";
mostCurrent._et_add_codeing.setHint(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-10"));
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 311;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 312;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 313;BA.debugLine="If(pan_all.Visible=True)Then";
if ((mostCurrent._pan_all.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 314;BA.debugLine="pan_all_Click";
_pan_all_click();
 }else {
 //BA.debugLineNum = 316;BA.debugLine="btn_back_Click";
_btn_back_click();
 };
 //BA.debugLineNum = 319;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 321;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 169;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return "";
}
public static int  _available_words(int _id) throws Exception{
int _count_wa = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur2 = null;
long _t1_now = 0L;
long _t2_word = 0L;
int _i = 0;
 //BA.debugLineNum = 202;BA.debugLine="Sub  available_words (id As Int) As Int";
 //BA.debugLineNum = 205;BA.debugLine="Dim count_wa As Int=0 '--تعداد لغات در دسترس";
_count_wa = (int) (0);
 //BA.debugLineNum = 206;BA.debugLine="Dim cur2 As Cursor";
_cur2 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 207;BA.debugLine="Main.query=\"SELECT * FROM words WHERE book='\"& Ma";
mostCurrent._main._query /*String*/  = "SELECT * FROM words WHERE book='"+mostCurrent._main._book_name /*String*/ +"' AND lessen='"+mostCurrent._main._lessen_name /*String*/ +"' AND level="+BA.NumberToString((_id+1));
 //BA.debugLineNum = 208;BA.debugLine="cur2 = Main.sq_db.ExecQuery(Main.query)";
_cur2 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._main._query /*String*/ )));
 //BA.debugLineNum = 210;BA.debugLine="Dim t1_now As Long = DateTime.DateTimeParse(DateT";
_t1_now = anywheresoftware.b4a.keywords.Common.DateTime.DateTimeParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()),anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 211;BA.debugLine="Dim t2_word As Long";
_t2_word = 0L;
 //BA.debugLineNum = 213;BA.debugLine="For i=0 To cur2.RowCount-1";
{
final int step7 = 1;
final int limit7 = (int) (_cur2.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 214;BA.debugLine="cur2.Position=i";
_cur2.setPosition(_i);
 //BA.debugLineNum = 215;BA.debugLine="t2_word=cur2.GetString(\"time_enter\")";
_t2_word = (long)(Double.parseDouble(_cur2.GetString("time_enter")));
 //BA.debugLineNum = 217;BA.debugLine="If ((t1_now-t2_word) > Main.time_list_level.get(";
if (((_t1_now-_t2_word)>(double)(BA.ObjectToNumber(mostCurrent._main._time_list_level /*anywheresoftware.b4a.objects.collections.List*/ .Get(_id))))) { 
 //BA.debugLineNum = 218;BA.debugLine="count_wa=count_wa+1";
_count_wa = (int) (_count_wa+1);
 };
 }
};
 //BA.debugLineNum = 223;BA.debugLine="Return count_wa";
if (true) return _count_wa;
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return 0;
}
public static String  _btn_add_by_voice_click() throws Exception{
 //BA.debugLineNum = 354;BA.debugLine="Private Sub btn_add_by_voice_Click";
 //BA.debugLineNum = 355;BA.debugLine="StartActivity(add_voice_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._add_voice_activity.getObject()));
 //BA.debugLineNum = 356;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return "";
}
public static String  _btn_add_excel_click() throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Private Sub btn_add_excel_Click";
 //BA.debugLineNum = 237;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 238;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 239;BA.debugLine="CC.Show(\"*/*\", \"Choose excel file\")";
_cc.Show(processBA,"*/*","Choose excel file");
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _btn_add_word_click() throws Exception{
 //BA.debugLineNum = 227;BA.debugLine="Private Sub btn_add_word_Click";
 //BA.debugLineNum = 228;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 230;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 231;BA.debugLine="btn_menu.Visible=False";
mostCurrent._btn_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 232;BA.debugLine="pan_add_word.Visible=True";
mostCurrent._pan_add_word.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public static String  _btn_back_click() throws Exception{
 //BA.debugLineNum = 306;BA.debugLine="Private Sub btn_back_Click";
 //BA.debugLineNum = 307;BA.debugLine="StartActivity(lessen_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._lessen_activity.getObject()));
 //BA.debugLineNum = 308;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _btn_menu_click() throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Private Sub btn_menu_Click";
 //BA.debugLineNum = 184;BA.debugLine="If(btn_menu.Text==Chr(0xE5C9)) Then";
if (((mostCurrent._btn_menu.getText()).equals(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr(((int)0xe5c9)))))) { 
 //BA.debugLineNum = 187;BA.debugLine="pan_menu.Visible=False";
mostCurrent._pan_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 188;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 189;BA.debugLine="btn_menu.Text=Chr(0xE147)";
mostCurrent._btn_menu.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr(((int)0xe147))));
 }else {
 //BA.debugLineNum = 193;BA.debugLine="pan_menu.Visible=True";
mostCurrent._pan_menu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 194;BA.debugLine="pan_all.Visible=True";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 195;BA.debugLine="btn_menu.Text=Chr(0xE5C9)";
mostCurrent._btn_menu.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr(((int)0xe5c9))));
 };
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _btn_no_add_click() throws Exception{
 //BA.debugLineNum = 302;BA.debugLine="Private Sub btn_no_add_Click";
 //BA.debugLineNum = 303;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}
public static String  _btn_yes_add_click() throws Exception{
 //BA.debugLineNum = 283;BA.debugLine="Private Sub btn_yes_add_Click";
 //BA.debugLineNum = 285;BA.debugLine="If(et_add_question.Text.Trim==\"\" Or et_add_answer";
if (((mostCurrent._et_add_question.getText().trim()).equals("") || (mostCurrent._et_add_answer.getText().trim()).equals(""))) { 
 //BA.debugLineNum = 286;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-12\"),F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-12")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 289;BA.debugLine="Main.sq_db.ExecNonQuery2(\"INSERT INTO words (que";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._et_add_question.getText()),(Object)(mostCurrent._et_add_answer.getText()),(Object)(mostCurrent._main._book_name /*String*/ ),(Object)(mostCurrent._main._lessen_name /*String*/ ),(Object)(mostCurrent._et_add_synonim.getText()),(Object)(mostCurrent._et_add_codeing.getText())}));
 //BA.debugLineNum = 292;BA.debugLine="pan_all_Click";
_pan_all_click();
 //BA.debugLineNum = 294;BA.debugLine="Custom_LV_level.Clear";
mostCurrent._custom_lv_level._clear();
 //BA.debugLineNum = 295;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 296;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"book-8\"),Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-8")),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
int _row = 0;
 //BA.debugLineNum = 242;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 244;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 245;BA.debugLine="Try";
try { //BA.debugLineNum = 246;BA.debugLine="Workbook1.Initialize(Dir, FileName)";
mostCurrent._workbook1.Initialize(_dir,_filename);
 //BA.debugLineNum = 247;BA.debugLine="Sheet1 = Workbook1.GetSheet(0)";
mostCurrent._sheet1 = mostCurrent._workbook1.GetSheet((int) (0));
 //BA.debugLineNum = 249;BA.debugLine="For row = 1 To Sheet1.RowsCount - 1";
{
final int step5 = 1;
final int limit5 = (int) (mostCurrent._sheet1.getRowsCount()-1);
_row = (int) (1) ;
for (;_row <= limit5 ;_row = _row + step5 ) {
 //BA.debugLineNum = 250;BA.debugLine="If (Sheet1.GetCellValue(0, row).Trim==\"\") Then";
if (((mostCurrent._sheet1.GetCellValue((int) (0),_row).trim()).equals(""))) { 
 //BA.debugLineNum = 251;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 254;BA.debugLine="Main.sq_db.ExecNonQuery2(\"INSERT INTO words (q";
mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._sheet1.GetCellValue((int) (0),_row)),(Object)(mostCurrent._sheet1.GetCellValue((int) (1),_row)),(Object)(mostCurrent._main._book_name /*String*/ ),(Object)(mostCurrent._main._lessen_name /*String*/ ),(Object)(mostCurrent._sheet1.GetCellValue((int) (2),_row)),(Object)(mostCurrent._sheet1.GetCellValue((int) (3),_row))}));
 }
};
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 257;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-11\")&";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-11")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 262;BA.debugLine="Custom_LV_level.Clear";
mostCurrent._custom_lv_level._clear();
 //BA.debugLineNum = 263;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 264;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"book-8\"),Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("book-8")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 267;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-11\"),F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-11")),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 270;BA.debugLine="End Sub";
return "";
}
public static int  _count_words_lessen() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur4 = null;
 //BA.debugLineNum = 172;BA.debugLine="Sub count_words_lessen As Int";
 //BA.debugLineNum = 174;BA.debugLine="Dim cur4 As Cursor";
_cur4 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 176;BA.debugLine="cur4 = Main.sq_db.ExecQuery(\"SELECT COUNT(id) FRO";
_cur4 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._main._sq_db /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery("SELECT COUNT(id) FROM words WHERE book='"+mostCurrent._main._book_name /*String*/ +"' AND lessen='"+mostCurrent._main._lessen_name /*String*/ +"'")));
 //BA.debugLineNum = 177;BA.debugLine="cur4.Position=0";
_cur4.setPosition((int) (0));
 //BA.debugLineNum = 178;BA.debugLine="Return cur4.GetInt(\"COUNT(id)\")";
if (true) return _cur4.GetInt("COUNT(id)");
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return 0;
}
public static String  _custom_lv_level_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 325;BA.debugLine="Private Sub Custom_LV_level_ItemClick (Index As In";
 //BA.debugLineNum = 329;BA.debugLine="Main.level_name= Index+1";
mostCurrent._main._level_name /*int*/  = (int) (_index+1);
 //BA.debugLineNum = 330;BA.debugLine="If(available_words(Index)==0) Then";
if ((_available_words(_index)==0)) { 
 //BA.debugLineNum = 331;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-13\"),";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-13")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 333;BA.debugLine="StartActivity(word_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._word_activity.getObject()));
 //BA.debugLineNum = 334;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 338;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 18;BA.debugLine="Dim count_w As List";
mostCurrent._count_w = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 19;BA.debugLine="Dim count_avail_all As Int =0";
_count_avail_all = (int) (0);
 //BA.debugLineNum = 20;BA.debugLine="Dim count_ended As Int =0";
_count_ended = (int) (0);
 //BA.debugLineNum = 22;BA.debugLine="Private btn_menu As Button";
mostCurrent._btn_menu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private pan_menu As Panel";
mostCurrent._pan_menu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private pan_all As Panel";
mostCurrent._pan_all = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private pan_add_word As Panel";
mostCurrent._pan_add_word = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private et_add_answer As EditText";
mostCurrent._et_add_answer = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private et_add_question As EditText";
mostCurrent._et_add_question = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Workbook1 As ReadableWorkbook";
mostCurrent._workbook1 = new anywheresoftware.b4a.objects.WorkbookWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Sheet1 As ReadableSheet";
mostCurrent._sheet1 = new anywheresoftware.b4a.objects.WorkbookWrapper.SheetWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lbl_path As Label";
mostCurrent._lbl_path = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private et_add_synonim As EditText";
mostCurrent._et_add_synonim = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private et_add_codeing As EditText";
mostCurrent._et_add_codeing = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lbl_all As Label";
mostCurrent._lbl_all = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private lbl_available_all As Label";
mostCurrent._lbl_available_all = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Custom_LV_level As CustomListView";
mostCurrent._custom_lv_level = new b4a.example3.customlistview();
 //BA.debugLineNum = 38;BA.debugLine="Private lbl_ended As Label";
mostCurrent._lbl_ended = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lbl_count_level As Label";
mostCurrent._lbl_count_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lbl_avail_level As Label";
mostCurrent._lbl_avail_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private lbl_level As Label";
mostCurrent._lbl_level = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private pan_ended As Panel";
mostCurrent._pan_ended = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btn_add_word As Button";
mostCurrent._btn_add_word = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private btn_add_excel As Button";
mostCurrent._btn_add_excel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private btn_no_add As Button";
mostCurrent._btn_no_add = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private btn_yes_add As Button";
mostCurrent._btn_yes_add = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private btn_add_by_voice As Button";
mostCurrent._btn_add_by_voice = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _pan_add_word_click() throws Exception{
 //BA.debugLineNum = 349;BA.debugLine="Private Sub pan_add_word_Click";
 //BA.debugLineNum = 351;BA.debugLine="End Sub";
return "";
}
public static String  _pan_all_click() throws Exception{
 //BA.debugLineNum = 272;BA.debugLine="Private Sub pan_all_Click";
 //BA.debugLineNum = 274;BA.debugLine="pan_menu.Visible=False";
mostCurrent._pan_menu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 276;BA.debugLine="pan_all.Visible=False";
mostCurrent._pan_all.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 278;BA.debugLine="pan_add_word.Visible=False";
mostCurrent._pan_add_word.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="btn_menu.Visible=True";
mostCurrent._btn_menu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 280;BA.debugLine="btn_menu.Text=Chr(0xE147)";
mostCurrent._btn_menu.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr(((int)0xe147))));
 //BA.debugLineNum = 281;BA.debugLine="End Sub";
return "";
}
public static String  _pan_ended_click() throws Exception{
 //BA.debugLineNum = 340;BA.debugLine="Private Sub pan_ended_Click";
 //BA.debugLineNum = 341;BA.debugLine="If(count_ended==0)Then";
if ((_count_ended==0)) { 
 //BA.debugLineNum = 342;BA.debugLine="ToastMessageShow(Main.loc.Localize(\"level-14\"),F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(mostCurrent._main._loc /*b4a.example.localizator*/ ._localize("level-14")),anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 344;BA.debugLine="StartActivity(all_words_activity)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._all_words_activity.getObject()));
 };
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim CC As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
