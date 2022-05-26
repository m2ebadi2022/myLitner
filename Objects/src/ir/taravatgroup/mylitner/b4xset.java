package ir.taravatgroup.mylitner;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class b4xset extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "ir.taravatgroup.mylitner.b4xset");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", ir.taravatgroup.mylitner.b4xset.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public ir.taravatgroup.mylitner.b4xorderedmap _map = null;
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
public ir.taravatgroup.mylitner.singl_word_activity _singl_word_activity = null;
public ir.taravatgroup.mylitner.starter _starter = null;
public ir.taravatgroup.mylitner.word_activity _word_activity = null;
public ir.taravatgroup.mylitner.b4xcollections _b4xcollections = null;
public ir.taravatgroup.mylitner.xuiviewsutils _xuiviewsutils = null;
public String  _add(Object _value) throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Public Sub Add(Value As Object)";
 //BA.debugLineNum = 10;BA.debugLine="map.Put(Value, \"\")";
_map._put /*String*/ (_value,(Object)(""));
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _aslist() throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Public Sub AsList As List";
 //BA.debugLineNum = 30;BA.debugLine="Return map.Keys";
if (true) return _map._getkeys /*anywheresoftware.b4a.objects.collections.List*/ ();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return null;
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Private map As B4XOrderedMap";
_map = new ir.taravatgroup.mylitner.b4xorderedmap();
 //BA.debugLineNum = 3;BA.debugLine="End Sub";
return "";
}
public String  _clear() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Public Sub Clear";
 //BA.debugLineNum = 26;BA.debugLine="map.Clear";
_map._clear /*String*/ ();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public boolean  _contains(Object _value) throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Public Sub Contains (Value As Object) As Boolean";
 //BA.debugLineNum = 18;BA.debugLine="Return map.ContainsKey(Value)";
if (true) return _map._containskey /*boolean*/ (_value);
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return false;
}
public int  _getsize() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Public Sub getSize As Int";
 //BA.debugLineNum = 22;BA.debugLine="Return map.Size";
if (true) return _map._getsize /*int*/ ();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 5;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 6;BA.debugLine="map.Initialize";
_map._initialize /*String*/ (ba);
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public String  _remove(Object _value) throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Public Sub Remove(Value As Object)";
 //BA.debugLineNum = 14;BA.debugLine="map.Remove(Value)";
_map._remove /*String*/ (_value);
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
