package ir.taravatgroup.mylitner;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class preoptimizedclv extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "ir.taravatgroup.mylitner.preoptimizedclv");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", ir.taravatgroup.mylitner.preoptimizedclv.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example3.customlistview _mclv = null;
public anywheresoftware.b4a.objects.collections.List _items = null;
public anywheresoftware.b4a.objects.collections.List _panelscache = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _stubpanel = null;
public boolean _horizontal = false;
public ir.taravatgroup.mylitner.b4xset[] _assigneditems = null;
public int _assigneditemsasindex = 0;
public anywheresoftware.b4j.object.JavaObject _jclv = null;
public int _extraitems = 0;
public anywheresoftware.b4a.objects.collections.List _listofitemsthatshouldbeupdated = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public boolean _showscrollbar = false;
public ir.taravatgroup.mylitner.b4xseekbar _b4xseekbar1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _pnloverlay = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _lblhint = null;
public int _lastuserchangeindex = 0;
public int _numberofsteps = 0;
public int _delaybeforehidingoverlay = 0;
public Object _mcallback = null;
public String _meventname = "";
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
public String  _additem(int _size,int _clr,Object _value) throws Exception{
b4a.example3.customlistview._clvitem _newitem = null;
 //BA.debugLineNum = 53;BA.debugLine="Public Sub AddItem (Size As Int, Clr As Int, Value";
 //BA.debugLineNum = 54;BA.debugLine="Dim NewItem As CLVItem";
_newitem = new b4a.example3.customlistview._clvitem();
 //BA.debugLineNum = 55;BA.debugLine="NewItem.Color = Clr";
_newitem.Color = _clr;
 //BA.debugLineNum = 56;BA.debugLine="NewItem.Panel = StubPanel";
_newitem.Panel = _stubpanel;
 //BA.debugLineNum = 57;BA.debugLine="NewItem.Value = Value";
_newitem.Value = _value;
 //BA.debugLineNum = 58;BA.debugLine="NewItem.Size = Size";
_newitem.Size = _size;
 //BA.debugLineNum = 59;BA.debugLine="items.Add(NewItem)";
_items.Add((Object)(_newitem));
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public void  _b4xseekbar1_touchstatechanged(boolean _pressed) throws Exception{
ResumableSub_B4XSeekBar1_TouchStateChanged rsub = new ResumableSub_B4XSeekBar1_TouchStateChanged(this,_pressed);
rsub.resume(ba, null);
}
public static class ResumableSub_B4XSeekBar1_TouchStateChanged extends BA.ResumableSub {
public ResumableSub_B4XSeekBar1_TouchStateChanged(ir.taravatgroup.mylitner.preoptimizedclv parent,boolean _pressed) {
this.parent = parent;
this._pressed = _pressed;
}
ir.taravatgroup.mylitner.preoptimizedclv parent;
boolean _pressed;
anywheresoftware.b4j.object.JavaObject _jo = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 164;BA.debugLine="If Pressed = False Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_pressed==parent.__c.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 165;BA.debugLine="mCLV.JumpToItem(LastUserChangeIndex)";
parent._mclv._jumptoitem(parent._lastuserchangeindex);
 //BA.debugLineNum = 166;BA.debugLine="Sleep(DelayBeforeHidingOverlay)";
parent.__c.Sleep(ba,this,parent._delaybeforehidingoverlay);
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 //BA.debugLineNum = 167;BA.debugLine="pnlOverlay.Visible = False";
parent._pnloverlay.setVisible(parent.__c.False);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 169;BA.debugLine="pnlOverlay.Visible = True";
parent._pnloverlay.setVisible(parent.__c.True);
 //BA.debugLineNum = 171;BA.debugLine="Dim jo As JavaObject = mCLV.sv";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(parent._mclv._sv.getObject()));
 //BA.debugLineNum = 172;BA.debugLine="jo.RunMethod(\"fling\", Array(0))";
_jo.RunMethod("fling",new Object[]{(Object)(0)});
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _b4xseekbar1_valuechanged(int _value) throws Exception{
Object _t = null;
 //BA.debugLineNum = 177;BA.debugLine="Sub B4XSeekBar1_ValueChanged (Value As Int)";
 //BA.debugLineNum = 178;BA.debugLine="LastUserChangeIndex = Max(0, items.Size - 1 - Val";
_lastuserchangeindex = (int) (__c.Max(0,_items.getSize()-1-_value));
 //BA.debugLineNum = 179;BA.debugLine="If LastUserChangeIndex < B4XSeekBar1.Interval The";
if (_lastuserchangeindex<_b4xseekbar1._interval /*int*/ ) { 
_lastuserchangeindex = (int) (0);};
 //BA.debugLineNum = 180;BA.debugLine="lblHint.Text = \"\"";
_lblhint.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 181;BA.debugLine="If xui.SubExists(mCallback, mEventName & \"_HintRe";
if (_xui.SubExists(ba,_mcallback,_meventname+"_HintRequested",(int) (1))) { 
 //BA.debugLineNum = 182;BA.debugLine="Dim t As Object = CallSub2(mCallback, mEventName";
_t = __c.CallSubNew2(ba,_mcallback,_meventname+"_HintRequested",(Object)(_lastuserchangeindex));
 //BA.debugLineNum = 183;BA.debugLine="If t <> Null Then";
if (_t!= null) { 
 //BA.debugLineNum = 184;BA.debugLine="InternalSetTextOrCSBuilderToLabel(lblHint, t)";
_internalsettextorcsbuildertolabel(_lblhint,_t);
 };
 };
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private mCLV As CustomListView";
_mclv = new b4a.example3.customlistview();
 //BA.debugLineNum = 4;BA.debugLine="Private items As List";
_items = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 5;BA.debugLine="Private PanelsCache As List";
_panelscache = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 6;BA.debugLine="Private StubPanel As B4XView";
_stubpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Private horizontal As Boolean";
_horizontal = false;
 //BA.debugLineNum = 8;BA.debugLine="Private AssignedItems() As B4XSet";
_assigneditems = new ir.taravatgroup.mylitner.b4xset[(int) (0)];
{
int d0 = _assigneditems.length;
for (int i0 = 0;i0 < d0;i0++) {
_assigneditems[i0] = new ir.taravatgroup.mylitner.b4xset();
}
}
;
 //BA.debugLineNum = 9;BA.debugLine="Private AssignedItemsAsIndex As Int";
_assigneditemsasindex = 0;
 //BA.debugLineNum = 13;BA.debugLine="Private jclv As JavaObject";
_jclv = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 15;BA.debugLine="Public ExtraItems As Int = 3";
_extraitems = (int) (3);
 //BA.debugLineNum = 16;BA.debugLine="Private ListOfItemsThatShouldBeUpdated As List";
_listofitemsthatshouldbeupdated = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 18;BA.debugLine="Public ShowScrollBar As Boolean = True";
_showscrollbar = __c.True;
 //BA.debugLineNum = 19;BA.debugLine="Public B4XSeekBar1 As B4XSeekBar";
_b4xseekbar1 = new ir.taravatgroup.mylitner.b4xseekbar();
 //BA.debugLineNum = 20;BA.debugLine="Public pnlOverlay As B4XView";
_pnloverlay = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Public lblHint As B4XView";
_lblhint = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private LastUserChangeIndex As Int";
_lastuserchangeindex = 0;
 //BA.debugLineNum = 23;BA.debugLine="Public NumberOfSteps As Int = 20";
_numberofsteps = (int) (20);
 //BA.debugLineNum = 24;BA.debugLine="Public DelayBeforeHidingOverlay As Int = 50";
_delaybeforehidingoverlay = (int) (50);
 //BA.debugLineNum = 25;BA.debugLine="Private mCallback As Object";
_mcallback = new Object();
 //BA.debugLineNum = 26;BA.debugLine="Private mEventName As String";
_meventname = "";
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public String  _clearassigneditems() throws Exception{
ir.taravatgroup.mylitner.b4xset _s = null;
 //BA.debugLineNum = 151;BA.debugLine="Private Sub ClearAssignedItems";
 //BA.debugLineNum = 152;BA.debugLine="For Each s As B4XSet In AssignedItems";
{
final ir.taravatgroup.mylitner.b4xset[] group1 = _assigneditems;
final int groupLen1 = group1.length
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_s = group1[index1];
 //BA.debugLineNum = 153;BA.debugLine="s.Clear";
_s._clear /*String*/ ();
 }
};
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public String  _commit() throws Exception{
int _dividersize = 0;
int _totalsize = 0;
int _i = 0;
b4a.example3.customlistview._clvitem _it = null;
 //BA.debugLineNum = 62;BA.debugLine="Public Sub Commit";
 //BA.debugLineNum = 63;BA.debugLine="ClearAssignedItems";
_clearassigneditems();
 //BA.debugLineNum = 64;BA.debugLine="Dim DividerSize As Int = mCLV.DividerSize";
_dividersize = (int) (_mclv._getdividersize());
 //BA.debugLineNum = 65;BA.debugLine="Dim TotalSize As Int = DividerSize";
_totalsize = _dividersize;
 //BA.debugLineNum = 66;BA.debugLine="For i = 0 To items.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_items.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 67;BA.debugLine="Dim it As CLVItem = items.Get(i)";
_it = (b4a.example3.customlistview._clvitem)(_items.Get(_i));
 //BA.debugLineNum = 68;BA.debugLine="it.Offset = TotalSize";
_it.Offset = _totalsize;
 //BA.debugLineNum = 69;BA.debugLine="TotalSize = TotalSize + it.Size + DividerSize";
_totalsize = (int) (_totalsize+_it.Size+_dividersize);
 }
};
 //BA.debugLineNum = 71;BA.debugLine="If horizontal Then";
if (_horizontal) { 
 //BA.debugLineNum = 72;BA.debugLine="mCLV.sv.ScrollViewContentWidth = TotalSize";
_mclv._sv.setScrollViewContentWidth(_totalsize);
 }else {
 //BA.debugLineNum = 74;BA.debugLine="mCLV.sv.ScrollViewContentHeight = TotalSize";
_mclv._sv.setScrollViewContentHeight(_totalsize);
 };
 //BA.debugLineNum = 76;BA.debugLine="B4XSeekBar1.mBase.Visible = ShowScrollBar";
_b4xseekbar1._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .setVisible(_showscrollbar);
 //BA.debugLineNum = 77;BA.debugLine="If ShowScrollBar Then";
if (_showscrollbar) { 
 //BA.debugLineNum = 78;BA.debugLine="B4XSeekBar1.MaxValue = items.Size";
_b4xseekbar1._maxvalue /*int*/  = _items.getSize();
 //BA.debugLineNum = 79;BA.debugLine="B4XSeekBar1.Interval = items.Size / NumberOfStep";
_b4xseekbar1._interval /*int*/  = (int) (_items.getSize()/(double)_numberofsteps);
 };
 //BA.debugLineNum = 81;BA.debugLine="RaiseVisibleRangeEvent";
_raisevisiblerangeevent();
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _createpanel() throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Private Sub CreatePanel As B4XView";
 //BA.debugLineNum = 94;BA.debugLine="Return jclv.RunMethodJO(\"_createpanel\", Array(\"Pa";
if (true) return (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_jclv.RunMethodJO("_createpanel",new Object[]{(Object)("Panel")}).RunMethod("getObject",(Object[])(__c.Null))));
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.B4XViewWrapper  _getpanel() throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 144;BA.debugLine="Private Sub GetPanel As B4XView";
 //BA.debugLineNum = 145;BA.debugLine="If PanelsCache.Size = 0 Then Return CreatePanel";
if (_panelscache.getSize()==0) { 
if (true) return _createpanel();};
 //BA.debugLineNum = 146;BA.debugLine="Dim p As B4XView = PanelsCache.Get(PanelsCache.Si";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_panelscache.Get((int) (_panelscache.getSize()-1))));
 //BA.debugLineNum = 147;BA.debugLine="PanelsCache.RemoveAt(PanelsCache.Size - 1)";
_panelscache.RemoveAt((int) (_panelscache.getSize()-1));
 //BA.debugLineNum = 148;BA.debugLine="Return p";
if (true) return _p;
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return null;
}
public String  _handlescrollbar(int _firstvisible) throws Exception{
 //BA.debugLineNum = 139;BA.debugLine="Private Sub HandleScrollBar (FirstVisible As Int)";
 //BA.debugLineNum = 140;BA.debugLine="If ShowScrollBar = False Then Return";
if (_showscrollbar==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 141;BA.debugLine="B4XSeekBar1.Value = items.Size - FirstVisible";
_b4xseekbar1._setvalue /*int*/ ((int) (_items.getSize()-_firstvisible));
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname,b4a.example3.customlistview _clv) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 29;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 30;BA.debugLine="mCLV = CLV";
_mclv = _clv;
 //BA.debugLineNum = 31;BA.debugLine="jclv = mCLV 'ignore";
_jclv = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_mclv));
 //BA.debugLineNum = 36;BA.debugLine="items = jclv.GetFieldJO(\"_items\").RunMethod(\"getO";
_items = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_jclv.GetFieldJO("_items").RunMethod("getObject",(Object[])(__c.Null))));
 //BA.debugLineNum = 37;BA.debugLine="horizontal = jclv.GetField(\"_horizontal\")";
_horizontal = BA.ObjectToBoolean(_jclv.GetField("_horizontal"));
 //BA.debugLineNum = 39;BA.debugLine="PanelsCache.Initialize";
_panelscache.Initialize();
 //BA.debugLineNum = 40;BA.debugLine="StubPanel = CreatePanel";
_stubpanel = _createpanel();
 //BA.debugLineNum = 41;BA.debugLine="StubPanel.AddView(xui.CreatePanel(\"\"), 0, 0, 10di";
_stubpanel.AddView((android.view.View)(_xui.CreatePanel(ba,"").getObject()),(int) (0),(int) (0),__c.DipToCurrent((int) (10)),__c.DipToCurrent((int) (10)));
 //BA.debugLineNum = 42;BA.debugLine="AssignedItems = Array As B4XSet(B4XCollections.Cr";
_assigneditems = new ir.taravatgroup.mylitner.b4xset[]{_b4xcollections._createset /*ir.taravatgroup.mylitner.b4xset*/ (ba),_b4xcollections._createset /*ir.taravatgroup.mylitner.b4xset*/ (ba)};
 //BA.debugLineNum = 43;BA.debugLine="ListOfItemsThatShouldBeUpdated.Initialize";
_listofitemsthatshouldbeupdated.Initialize();
 //BA.debugLineNum = 44;BA.debugLine="mCLV.AsView.LoadLayout(\"PCLVSeekBar\")";
_mclv._asview().LoadLayout("PCLVSeekBar",ba);
 //BA.debugLineNum = 45;BA.debugLine="B4XSeekBar1.Size1 = 1dip";
_b4xseekbar1._size1 /*int*/  = __c.DipToCurrent((int) (1));
 //BA.debugLineNum = 46;BA.debugLine="B4XSeekBar1.Size2 = 1dip";
_b4xseekbar1._size2 /*int*/  = __c.DipToCurrent((int) (1));
 //BA.debugLineNum = 47;BA.debugLine="B4XSeekBar1.Radius1 = 8dip";
_b4xseekbar1._radius1 /*int*/  = __c.DipToCurrent((int) (8));
 //BA.debugLineNum = 48;BA.debugLine="B4XSeekBar1.Update";
_b4xseekbar1._update /*String*/ ();
 //BA.debugLineNum = 49;BA.debugLine="mCallback = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 50;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public String  _internalsettextorcsbuildertolabel(anywheresoftware.b4a.objects.B4XViewWrapper _xlbl,Object _text) throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Private Sub InternalSetTextOrCSBuilderToLabel(xlbl";
 //BA.debugLineNum = 191;BA.debugLine="xlbl.Text = Text";
_xlbl.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public void  _listchangedexternally() throws Exception{
ResumableSub_ListChangedExternally rsub = new ResumableSub_ListChangedExternally(this);
rsub.resume(ba, null);
}
public static class ResumableSub_ListChangedExternally extends BA.ResumableSub {
public ResumableSub_ListChangedExternally(ir.taravatgroup.mylitner.preoptimizedclv parent) {
this.parent = parent;
}
ir.taravatgroup.mylitner.preoptimizedclv parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 158;BA.debugLine="ClearAssignedItems";
parent._clearassigneditems();
 //BA.debugLineNum = 159;BA.debugLine="Sleep(0)";
parent.__c.Sleep(ba,this,(int) (0));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 160;BA.debugLine="RaiseVisibleRangeEvent";
parent._raisevisiblerangeevent();
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _raisevisiblerangeevent() throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Private Sub RaiseVisibleRangeEvent";
 //BA.debugLineNum = 86;BA.debugLine="jclv.RunMethod(\"_resetvisibles\", Null)";
_jclv.RunMethod("_resetvisibles",(Object[])(__c.Null));
 //BA.debugLineNum = 87;BA.debugLine="jclv.RunMethod(\"_updatevisiblerange\", Null)";
_jclv.RunMethod("_updatevisiblerange",(Object[])(__c.Null));
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.collections.List  _visiblerangechanged(int _firstindex,int _lastindex) throws Exception{
int _fromindex = 0;
int _toindex = 0;
ir.taravatgroup.mylitner.b4xset _prevset = null;
ir.taravatgroup.mylitner.b4xset _nextset = null;
int _i = 0;
b4a.example3.customlistview._clvitem _it = null;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
 //BA.debugLineNum = 98;BA.debugLine="Public Sub VisibleRangeChanged (FirstIndex As Int,";
 //BA.debugLineNum = 99;BA.debugLine="Dim FromIndex As Int = Max(0, FirstIndex - ExtraI";
_fromindex = (int) (__c.Max(0,_firstindex-_extraitems));
 //BA.debugLineNum = 100;BA.debugLine="Dim ToIndex As Int = Min(mCLV.Size - 1, LastIndex";
_toindex = (int) (__c.Min(_mclv._getsize()-1,_lastindex+_extraitems));
 //BA.debugLineNum = 101;BA.debugLine="Dim PrevSet As B4XSet = AssignedItems(AssignedIte";
_prevset = _assigneditems[_assigneditemsasindex];
 //BA.debugLineNum = 102;BA.debugLine="AssignedItemsAsIndex = (AssignedItemsAsIndex + 1)";
_assigneditemsasindex = (int) ((_assigneditemsasindex+1)%2);
 //BA.debugLineNum = 103;BA.debugLine="Dim NextSet As B4XSet = AssignedItems(AssignedIte";
_nextset = _assigneditems[_assigneditemsasindex];
 //BA.debugLineNum = 104;BA.debugLine="NextSet.Clear";
_nextset._clear /*String*/ ();
 //BA.debugLineNum = 105;BA.debugLine="ListOfItemsThatShouldBeUpdated.Initialize";
_listofitemsthatshouldbeupdated.Initialize();
 //BA.debugLineNum = 106;BA.debugLine="For i = FromIndex To ToIndex";
{
final int step8 = 1;
final int limit8 = _toindex;
_i = _fromindex ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 107;BA.debugLine="Dim it As CLVItem = items.Get(i)";
_it = (b4a.example3.customlistview._clvitem)(_items.Get(_i));
 //BA.debugLineNum = 108;BA.debugLine="If it.Panel = StubPanel Then";
if ((_it.Panel).equals(_stubpanel)) { 
 //BA.debugLineNum = 109;BA.debugLine="it.Panel = GetPanel";
_it.Panel = _getpanel();
 //BA.debugLineNum = 110;BA.debugLine="it.Panel.Tag = i";
_it.Panel.setTag((Object)(_i));
 //BA.debugLineNum = 111;BA.debugLine="it.Panel.Color = it.Color";
_it.Panel.setColor(_it.Color);
 //BA.debugLineNum = 112;BA.debugLine="If horizontal Then";
if (_horizontal) { 
 //BA.debugLineNum = 113;BA.debugLine="mCLV.sv.ScrollViewInnerPanel.AddView(it.Panel,";
_mclv._sv.getScrollViewInnerPanel().AddView((android.view.View)(_it.Panel.getObject()),_it.Offset,(int) (0),_it.Size,_mclv._sv.getHeight());
 }else {
 //BA.debugLineNum = 115;BA.debugLine="mCLV.sv.ScrollViewInnerPanel.AddView(it.Panel,";
_mclv._sv.getScrollViewInnerPanel().AddView((android.view.View)(_it.Panel.getObject()),(int) (0),_it.Offset,_mclv._sv.getWidth(),_it.Size);
 };
 //BA.debugLineNum = 117;BA.debugLine="NextSet.Add(it.Panel)";
_nextset._add /*String*/ ((Object)(_it.Panel.getObject()));
 //BA.debugLineNum = 118;BA.debugLine="ListOfItemsThatShouldBeUpdated.Add(i)";
_listofitemsthatshouldbeupdated.Add((Object)(_i));
 }else if(_prevset._contains /*boolean*/ ((Object)(_it.Panel.getObject()))) { 
 //BA.debugLineNum = 120;BA.debugLine="NextSet.Add(it.Panel)";
_nextset._add /*String*/ ((Object)(_it.Panel.getObject()));
 };
 }
};
 //BA.debugLineNum = 123;BA.debugLine="For Each pnl As B4XView In PrevSet.AsList";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group25 = _prevset._aslist /*anywheresoftware.b4a.objects.collections.List*/ ();
final int groupLen25 = group25.getSize()
;int index25 = 0;
;
for (; index25 < groupLen25;index25++){
_pnl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(group25.Get(index25)));
 //BA.debugLineNum = 124;BA.debugLine="If NextSet.Contains(pnl) = False Then";
if (_nextset._contains /*boolean*/ ((Object)(_pnl.getObject()))==__c.False) { 
 //BA.debugLineNum = 125;BA.debugLine="If pnl.Parent.IsInitialized Then";
if (_pnl.getParent().IsInitialized()) { 
 //BA.debugLineNum = 126;BA.debugLine="pnl.RemoveViewFromParent";
_pnl.RemoveViewFromParent();
 //BA.debugLineNum = 127;BA.debugLine="pnl.GetView(0).RemoveAllViews";
_pnl.GetView((int) (0)).RemoveAllViews();
 //BA.debugLineNum = 128;BA.debugLine="pnl.RemoveAllViews";
_pnl.RemoveAllViews();
 //BA.debugLineNum = 129;BA.debugLine="PanelsCache.Add(pnl)";
_panelscache.Add((Object)(_pnl.getObject()));
 //BA.debugLineNum = 130;BA.debugLine="Dim it As CLVItem = items.Get(pnl.Tag)";
_it = (b4a.example3.customlistview._clvitem)(_items.Get((int)(BA.ObjectToNumber(_pnl.getTag()))));
 //BA.debugLineNum = 131;BA.debugLine="it.Panel = StubPanel";
_it.Panel = _stubpanel;
 };
 };
 }
};
 //BA.debugLineNum = 135;BA.debugLine="HandleScrollBar (FirstIndex)";
_handlescrollbar(_firstindex);
 //BA.debugLineNum = 136;BA.debugLine="Return ListOfItemsThatShouldBeUpdated";
if (true) return _listofitemsthatshouldbeupdated;
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return null;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
if (BA.fastSubCompare(sub, "GETPANEL"))
	return _getpanel();
return BA.SubDelegator.SubNotFound;
}
}
