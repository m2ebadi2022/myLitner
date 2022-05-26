B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10.7
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: true
	#IncludeTitle: false
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private Panel1 As Panel
	
	
	Private btn_color As Panel
	
	Private pan_all As Panel
	Private sp_font_app As Spinner
	Private sp_size_font_app As Spinner
	Private Label1 As Label
	Private lbl_times_levels As Label
	Private lbl_notification As Label
	Private sp_lang_app As Spinner
	Private lbl_lang As Label
	Private Label3 As Label
	Private Label4 As Label
	Private Label5 As Label
	
	Dim font_list As List
	Dim font_name_list As List
	Dim lang_list As List
	
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("setting_layout")
	
	
	
	''-------setting
	''-------str
	Label1.Text=Main.loc.Localize("s-1")
	lbl_times_levels.Text=Main.loc.Localize("s-2")
	lbl_notification.Text=Main.loc.Localize("s-3")
	lbl_lang.Text=Main.loc.Localize("s-4")
	Label3.Text=Main.loc.Localize("s-5")
	Label4.Text=Main.loc.Localize("s-6")
	Label5.Text=Main.loc.Localize("s-7")
	
	''----------
	Panel1.Color=Main.app_color
	btn_color.Color=Main.app_color
	
	font_list.Initialize
	font_name_list.Initialize
	font_list.AddAll(Array As Object("ADastNevis.ttf","BYekan.ttf","BNazanin.ttf","BKoodkBd.ttf","Ghalam-2_MRT.TTF","Jaleh_MRT.ttf"))
	font_name_list.AddAll(Array As Object("DastNevis","Yekan","Nazanin","Koodak","Ghalam","Jaleh"))
	
	sp_font_app.AddAll(font_name_list)
	sp_font_app.SelectedIndex=font_list.IndexOf(Main.app_font_name)
	
	
	sp_size_font_app.AddAll(Array As Object("12","14","16","18","20","22","24","28"))
	sp_size_font_app.SelectedIndex=sp_size_font_app.IndexOf(Main.app_font_size)
	
	lang_list.Initialize
	lang_list.AddAll(Array As Object("fa","en","ar","tr","ind","far","ch"))
	sp_lang_app.AddAll(Array As Object("فارسی","English","Arabic","Turkish","Hindi","French","Chinese"))
	sp_lang_app.SelectedIndex=lang_list.IndexOf(Main.app_lang)
	''-------------
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
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub btn_back_Click
	StartActivity(Main)
	Activity.finish
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		If (pan_all.Visible=True)Then
			pan_all_Click
		Else
			btn_back_Click	
		End If
		
		Return True
	Else
		Return False
	End If
End Sub





Private Sub btn_color_Click
	pan_all.Visible=True
End Sub

Sub set_app_color_db (color As String)
	Main.query=" UPDATE setting SET option_value ="&color&" WHERE option_name= 'app_color' "
	Main.sq_db.ExecNonQuery(Main.query)
	btn_color.Color=color
	Main.app_color=color
	pan_all.Visible=False
	
	sp_lang_app.Clear
	Activity_Create(True)
End Sub


Private Sub color_12_Click
	set_app_color_db(0xFFC37A61)
End Sub

Private Sub color_11_Click
	set_app_color_db(0xFF5AB5FF)
End Sub

Private Sub color_10_Click
	set_app_color_db(0xFFE9B5AF)
End Sub

Private Sub color_9_Click
	set_app_color_db(0xFFFF5A50)
End Sub

Private Sub color_8_Click
	set_app_color_db(0xFFFF9800)
End Sub

Private Sub color_7_Click
	set_app_color_db(0xFFE4CB5F)
End Sub

Private Sub color_6_Click
	set_app_color_db(0xFF85E236)
End Sub

Private Sub color_5_Click
	set_app_color_db(0xFF069186)
End Sub

Private Sub color_4_Click
	set_app_color_db(0xFF00C7E4)
End Sub

Private Sub color_3_Click
	set_app_color_db(0xFF7686DF)
End Sub

Private Sub color_2_Click
	set_app_color_db(0xFF9C26B1)
End Sub

Private Sub color_1_Click
	set_app_color_db(0xFFE91D62)
End Sub

Private Sub pan_all_Click
	pan_all.Visible=False
End Sub

Private Sub pan_colorpicker_Click
	
End Sub

Private Sub sp_font_app_ItemClick (Position As Int, Value As Object)
	
	Dim font As String=font_list.Get(Position)
	
	Main.query=" UPDATE setting SET option_value ='"&font&"' WHERE option_name= 'font' "
	Main.sq_db.ExecNonQuery(Main.query)
	
	Main.app_font= Typeface.LoadFromAssets(font)
	Main.app_font_name=font
	
	sp_lang_app.Clear
	Activity_Create(True)
	
	
End Sub

Private Sub lbl_times_levels_Click
	StartActivity(setting_time_activity)
End Sub

Private Sub sp_size_font_app_ItemClick (Position As Int, Value As Object)
	Dim size As Int=Value
	
	Main.query=" UPDATE setting SET option_value ="&size&" WHERE option_name= 'font_size' "
	Main.sq_db.ExecNonQuery(Main.query)
	
	Main.app_font_size= size
	
	sp_lang_app.Clear
	Activity_Create(True)
End Sub

Private Sub lbl_notification_Click
	
	ToastMessageShow(Main.loc.Localize("main-21"),False)
End Sub



Private Sub sp_lang_app_ItemClick (Position As Int, Value As Object)
	
	Dim lang As String=lang_list.Get(Position)

	Main.query=" UPDATE setting SET option_value ='"&lang&"' WHERE option_name= 'app_lang' "
	Main.sq_db.ExecNonQuery(Main.query)
	
	Main.app_lang= lang
	Main.loc.ForceLocale(lang)
	If(lang <> "fa")Then
		sp_size_font_app_ItemClick(1,"14")
	End If
	
	sp_lang_app.Clear
	Activity_Create(True)
End Sub