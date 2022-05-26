B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10.7
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private pan_all As Panel
	Private level_current As Label
	Dim current_level_name As Int
	Private et_curent_level_h As EditText
	Private et_curent_level_d As EditText
	Private et_curent_level_m As EditText
	Private et_curent_level_y As EditText
	Private lbl_show_time As Label
	
	Private custom_lv As CustomListView
	
	
	Dim xui As XUI
	
	Private lbl_level As Label
	Private lbl_avail_level As Label
	Private lbl_count_level As Label
	Private lbl_count_levels As Label
	Private skb_curent_level_y As SeekBar
	Private skb_curent_level_m As SeekBar
	Private skb_curent_level_d As SeekBar
	Private skb_curent_level_h As SeekBar
	Private Panel1 As Panel
	Private Label1 As Label
	Private pan_set_time As Panel
	Private Label6 As Label
	Private Label5 As Label
	Private Label4 As Label
	Private Label3 As Label
	Private btn_save_count_level As Button
	Private Label2 As Label
	Private btn_save As Button
	Private btn_cancel As Button
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("setting_time_layout")
	''-------setting
	Panel1.Color=Main.app_color
	'pan_set_time.Color=Main.app_color
	''-------------
	
	Main.time_list_level.Clear
	Main.level_list_name.Clear
	

	Dim cur1 As Cursor
	cur1 = Main.sq_db.ExecQuery("SELECT * FROM level")
	lbl_count_levels.Text=cur1.RowCount
	lbl_count_levels.Tag=cur1.RowCount
	
	For i=0 To cur1.RowCount-1
		cur1.Position=i
		
		
		Dim p As B4XView = xui.CreatePanel("")
		p.SetLayoutAnimated(0, 0, 0, custom_lv.AsView.Width, 80dip)
		
	
		p.LoadLayout("item_level")
		lbl_count_level.Width=200dip
		
		lbl_level.Text=cur1.GetString("name")
		'lbl_avail_level.Text= cur1.GetString("time")
		lbl_count_level.Text=cur1.GetString("tozihat")
		custom_lv.Add(p, cur1.GetString("name") )
		
		Main.time_list_level.Add(cur1.GetString("time"))
		Main.level_list_name.Add(cur1.GetString("name"))
		
	Next
	
	skb_curent_level_h.Max=23
	skb_curent_level_d.Max=29
	skb_curent_level_m.Max=11
	skb_curent_level_y.Max=10
	

	''--------- set font for all views ----------------
	For Each v As View In Activity.GetAllViewsRecursive
		If v Is Label Then
			If (v Is Button) Then
				Continue
			End If
			Dim lbl As Label = v
			lbl.Typeface = Main.app_font
			lbl.TextSize=Main.app_font_size
		End If
	Next
	Label1.TextSize=18
	Label3.TextSize=14
	Label4.TextSize=14
	Label5.TextSize=14
	Label6.TextSize=14
	et_curent_level_h.TextSize=14
	et_curent_level_d.TextSize=14
	et_curent_level_m.TextSize=14
	et_curent_level_y.TextSize=14
	
	Label1.Text=Main.loc.Localize("s-t-1")
	Label2.Text=Main.loc.Localize("s-t-2")
	Label3.Text=Main.loc.Localize("s-t-3")
	Label4.Text=Main.loc.Localize("s-t-4")
	Label5.Text=Main.loc.Localize("s-t-5")
	Label6.Text=Main.loc.Localize("s-t-6")
	
	btn_save.Text=Main.loc.Localize("book-5")
	btn_cancel.Text=Main.loc.Localize("book-6")
	btn_save_count_level.Text=Main.loc.Localize("book-5")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub btn_back_Click
	StartActivity(setting_activity)
	Activity.finish
End Sub

Private Sub pan_all_Click
	btn_cancel_Click
End Sub

Private Sub btn_save_Click
	
	Dim tim_h As Int=et_curent_level_h.Text
	Dim tim_d As Int=et_curent_level_d.Text
	Dim tim_m As Int=et_curent_level_m.Text
	Dim tim_y As Int=et_curent_level_y.Text
	
	Dim tim1 As Long=DateUtils.SetDateAndTime(2021,1,1,0,0,0)
	Dim tim2 As Long=DateUtils.SetDateAndTime(2021+tim_y,1+tim_m,1+tim_d,tim_h,0,0)
	Dim end_tim As Long=tim2-tim1
	
	Dim show_time As String=""
	If (tim_y <> 0) Then
		show_time=tim_y& Main.loc.Localize("s-t-6")
	End If
	If (tim_m <> 0) Then
		show_time=show_time &tim_m&Main.loc.Localize("s-t-5")
	End If
	If (tim_d <> 0) Then
		show_time=show_time &tim_d&Main.loc.Localize("s-t-4")
	End If
	If (tim_h <> 0) Then
		show_time=show_time &tim_h&Main.loc.Localize("s-t-3")
	End If
	
	Dim dublicat_time As Boolean=False
	For j=0 To Main.time_list_level.Size-1 
		If(end_tim==Main.time_list_level.Get(j))Then
			dublicat_time=True
		End If
	Next
	
	If(dublicat_time==True) Then
		ToastMessageShow(Main.loc.Localize("s-t-8"),False)
	Else
		Main.query=" UPDATE level SET time = ? ,tozihat = ? WHERE name= ?"
		Main.sq_db.ExecNonQuery2(Main.query,Array As Object(end_tim,show_time,current_level_name))
		pan_all.Visible=False
		Activity_Create(True)
	End If
	
	
	
End Sub

Private Sub btn_cancel_Click
	pan_all.Visible=False
End Sub

Private Sub btn_save_count_level_Click
	'lbl_count_levels.Text   user chenge levels
	'lbl_count_levels.Tag   database levels
	Dim tedad As Int=0
	Dim num1 As Int=lbl_count_levels.Tag
	Dim num2 As Int=lbl_count_levels.Text
	Dim last_tim As Long=Main.time_list_level.Get(num1-1)
	
	
	tedad=num1-num2
	
	If (tedad>0)Then
		' delete query
		For i=1 To tedad
			Main.query="DELETE FROM level WHERE id = (SELECT MAX(id) FROM level)"
			Main.sq_db.ExecNonQuery(Main.query)
		Next
		ToastMessageShow(Main.loc.Localize("s-t-9"),False)
		
		
	Else If (tedad<0) Then
		' insert query
		tedad=tedad*(-1)
		
		For i=1 To tedad
			Main.query="INSERT INTO level (name, time, tozihat) VALUES ('"& (num1+i)&"','"&(last_tim*2*i)&"','')"
			Main.sq_db.ExecNonQuery(Main.query)
		Next
		ToastMessageShow(Main.loc.Localize("s-t-9"),False)
	End If
	
	Main.last_level=Main.level_list_name.Get(Main.level_list_name.Size-1)
	Activity_Create(True)
	
End Sub

Private Sub custom_lv_ItemClick (Index As Int, Value As Object)
	pan_all.Visible=True
	current_level_name=Value
	
	level_current.Text=Main.loc.Localize("s-t-7")&" : "&Value
	
	
	Dim base As Long=DateUtils.SetDateAndTime(2021,1,1,0,0,0)
	'Dim tt2 As Long=DateUtils.SetDateAndTime(2021,1,11,0,0,0)
	
	
	Dim hh As Long=base+ (Main.time_list_level.Get(Index))
	Dim p As Period= DateUtils.PeriodBetween(base,hh)
	
	et_curent_level_h.Text=p.Hours
	et_curent_level_d.Text=p.Days
	et_curent_level_m.Text=p.Months
	et_curent_level_y.Text=p.Years
	
	skb_curent_level_h.Value=p.Hours
	skb_curent_level_d.Value=p.Days
	skb_curent_level_m.Value=p.Months
	skb_curent_level_y.Value=p.Years
	
	show_labl_time_update
	If (current_level_name==1)Then
		btn_cancel_Click
		ToastMessageShow(Main.loc.Localize("s-t-10"),False)
		
	
		Main.sq_db.ExecNonQuery(" UPDATE level SET tozihat ='"&Main.loc.Localize("s-t-12")&"' WHERE name= 1")
		
	End If
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		btn_back_Click
		Return True
	Else
		Return False
	End If
End Sub

Private Sub pan_set_time_Click As Boolean
	Return False
End Sub

Private Sub btn_down_arrow_Click
	Dim b As Int=lbl_count_levels.Text
	If (b <=3) Then
		  	lbl_count_levels.Text=3
		ToastMessageShow(Main.loc.Localize("s-t-11"),False)
	Else
			lbl_count_levels.Text=b-1
	End If
	
End Sub

Private Sub btn_up_arrow_Click
	Dim a As Int= lbl_count_levels.Text
	lbl_count_levels.Text=a+1
End Sub

Private Sub et_curent_level_y_TextChanged (Old As String, New As String)
	show_labl_time_update
End Sub

Private Sub et_curent_level_m_TextChanged (Old As String, New As String)
	show_labl_time_update
End Sub

Private Sub et_curent_level_d_TextChanged (Old As String, New As String)
	show_labl_time_update
End Sub

Private Sub et_curent_level_h_TextChanged (Old As String, New As String)
	show_labl_time_update
End Sub
Sub show_labl_time_update
	
	Dim tim_h As Int=0
	Dim tim_d As Int=0
	Dim tim_m As Int=0
	Dim tim_y As Int=0
	
	
	If(et_curent_level_h.Text==Null)Then
		tim_h=0
	Else
		tim_h = et_curent_level_h.Text
	End If
	If(et_curent_level_d.Text==Null)Then
		tim_d=0
	Else
		tim_d = et_curent_level_d.Text
	End If
	If(et_curent_level_m.Text==Null)Then
		tim_m=0
	Else
		tim_m = et_curent_level_m.Text
	End If
	If(et_curent_level_y.Text==Null)Then
		tim_y=0
	Else
		tim_y = et_curent_level_y.Text
	End If
	
	
	
	Dim show_time As String=""
	If (tim_y <> 0) Then
		show_time=tim_y&" "& Main.loc.Localize("s-t-6")&"-"
	End If
	If (tim_m <> 0) Then
		show_time=show_time &tim_m&" "&Main.loc.Localize("s-t-5")&"-"
	End If
	If (tim_d <> 0) Then
		show_time=show_time &tim_d&" "&Main.loc.Localize("s-t-4")&"-"
	End If
	If (tim_h <> 0) Then
		show_time=show_time &tim_h&" "&Main.loc.Localize("s-t-3")&" "
	End If
	lbl_show_time.Text=show_time
	
End Sub

Private Sub skb_curent_level_h_ValueChanged (Value As Int, UserChanged As Boolean)
	et_curent_level_h.Text=Value
	show_labl_time_update
End Sub

Private Sub skb_curent_level_d_ValueChanged (Value As Int, UserChanged As Boolean)
	et_curent_level_d.Text=Value
	show_labl_time_update
End Sub

Private Sub skb_curent_level_m_ValueChanged (Value As Int, UserChanged As Boolean)
	et_curent_level_m.Text=Value
	show_labl_time_update
End Sub

Private Sub skb_curent_level_y_ValueChanged (Value As Int, UserChanged As Boolean)
	et_curent_level_y.Text=Value
	show_labl_time_update
End Sub