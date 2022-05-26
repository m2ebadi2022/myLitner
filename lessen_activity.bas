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

	Dim xui As XUI
	
	Private pan_add As Panel
	Private et_add_book As EditText
	Private et_add_lessen As EditText
	Private btn_add As Button
	Private pan_all As Panel
	Private lbl_path As Label
	
	
	Private Panel1 As Panel
	Private Label5 As Label
	Private custom_LS As CustomListView
	Private lbl_level As Label
	Private lbl_avail_level As Label
	Private lbl_count_level As Label
	Private lbl_var5 As Label
	
	Private Label2 As Label
	Private btn_yes_add As Button
	Private btn_no_add As Button
	
	Dim hidBtn As Int=0
	Dim edit_index As Int=0
	Dim current_id As Int
	Private btn_del As Button
	Private btn_edit As Button
	Private pan_pup As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("lessen_layout")
	hidBtn=0
	edit_index=0
	pan_add.Visible=True
	''-------setting
	Panel1.Color=Main.app_color
	'pan_add.Color=Main.app_color
	btn_add.TextColor=Main.app_color
	''-------------
	Dim pic_lessen As BitmapDrawable
	pic_lessen.Initialize(LoadBitmap(File.DirAssets, "lessen1.png"))
	pic_lessen.Gravity = Gravity.FILL
	
	
	lbl_path.Text=Main.book_name
	Dim cur1 As Cursor
	Dim cur2 As Cursor
	Main.query="SELECT * FROM lessen WHERE book='"& Main.book_name &"'"
	
	cur1 = Main.sq_db.ExecQuery(Main.query)
	For i=0 To cur1.RowCount-1
		cur1.Position=i
		
		Main.query=""
		Main.query="SELECT COUNT(id) FROM words WHERE book='"& Main.book_name &"' AND lessen='"&cur1.GetString("name")&"'"
		
		cur2 = Main.sq_db.ExecQuery(Main.query)
		cur2.Position=0
		
		
		Dim p As B4XView = xui.CreatePanel("")
		p.SetLayoutAnimated(0, 0, 0, 100%x, 86dip)
		
		p.LoadLayout("item_level")
		
		btn_del.Tag=cur1.GetInt("id")
		btn_edit.Tag=cur1.GetInt("id")
		
		lbl_level.Background=pic_lessen
		'lbl_level.Text=i+1
		lbl_avail_level.Text= cur1.GetString("name")
		lbl_count_level.Text=Main.loc.Localize("main-4")&" : "&cur2.GetInt("COUNT(id)")
		custom_LS.Add(p, cur1.GetString("name"))
		
		Main.lessen_name=cur1.GetString("name")
		Dim avail_count_lessen As Int=0
		For k=0 To Main.level_list_name.Size-1
			
			avail_count_lessen=avail_count_lessen+available_words(k+1)
		Next
		
		lbl_var5.Text=Main.loc.Localize("book-12")&" : "&avail_count_lessen
		
		
		
	Next





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
	Label5.TextSize=18
	lbl_path.TextSize=12

	Label5.Text=Main.loc.Localize("main-3")
	Label2.Text=Main.loc.Localize("lessen-2")
	et_add_lessen.Hint=Main.loc.Localize("lessen-1")
	btn_yes_add.Text=Main.loc.Localize("book-5")
	btn_no_add.Text=Main.loc.Localize("book-6")
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub  available_words (level As Int) As Int
	
	
	Dim count_wa As Int=0 '--تعداد لغات در دسترس
	Dim cur2 As Cursor
	Main.query="SELECT * FROM words WHERE book='"& Main.book_name &"' AND lessen='"&Main.lessen_name&"' AND level="&(level)
	cur2 = Main.sq_db.ExecQuery(Main.query)

	Dim t1_now As Long = DateTime.DateTimeParse(DateTime.Date(DateTime.Now),DateTime.Time(DateTime.Now))
	Dim t2_word As Long
	
	For i=0 To cur2.RowCount-1
		cur2.Position=i
		t2_word=cur2.GetString("time_enter")

		If ((t1_now-t2_word) > Main.time_list_level.get(level-1)) Then
			count_wa=count_wa+1

		End If
	Next
	Return count_wa
	
End Sub

Private Sub btn_back_Click
	StartActivity(book_activity)
	Activity.finish
End Sub

Private Sub btn_add_Click
	et_add_book.Enabled=False
	et_add_book.Text=Main.book_name
	
	pan_all.Visible=True
	btn_add.Visible=False
End Sub

Private Sub btn_yes_add_Click
	
	If(et_add_lessen.Text.Trim=="") Then
		ToastMessageShow(Main.loc.Localize("lessen-3"),False)
	Else
		

		
		
		If (edit_index==1)Then
			Dim lessen As Cursor = get_lessen_id(current_id)
			
			
			Main.sq_db.ExecNonQuery2("UPDATE lessen SET name = ? WHERE name= ? ",Array As Object(et_add_lessen.Text,lessen.GetString("name")))
			Main.sq_db.ExecNonQuery2("UPDATE words SET lessen = ? WHERE lessen= ? ",Array As Object(et_add_lessen.Text,lessen.GetString("name")))
			
		
			
		Else
			Main.query="INSERT INTO lessen (name, book) VALUES ('"&et_add_lessen.Text&"','"&et_add_book.Text&"')"
			Main.sq_db.ExecNonQuery(Main.query)
		ToastMessageShow(Main.loc.Localize("book-8"),False)
	
		End If
		
		btn_no_add_Click
		custom_LS.Clear
		Activity_Create(True)
	End If
	
End Sub

Private Sub btn_no_add_Click
	pan_add.Visible=False
	btn_add.Visible=True
	pan_all.Visible=False
End Sub



Private Sub pan_all_Click
	btn_no_add_Click
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		If(pan_all.Visible=True)Then
			pan_all_Click
		Else
			If (hidBtn=1)Then
				Activity_Create(True)
				
			Else
				btn_back_Click
			End If
		End If
		Return True
	Else
		Return False
	End If
End Sub



Private Sub pan_add_Click
	
End Sub

Private Sub custom_LS_ItemClick (Index As Int, Value As Object)
	Main.lessen_name= Value
	StartActivity(level_activity)
	Activity.finish
End Sub

Private Sub custom_LS_ItemLongClick (Index As Int, Value As Object)
	
	Activity_Create(True)
	Dim pp As B4XView
	pp=custom_LS.GetPanel(Index)
	
	
	For Each vs As View In pp.GetAllViewsRecursive
		If vs Is Panel Then
			Dim lbl2 As Panel = vs
			
			lbl2.Visible=True
			hidBtn=1
			
		End If
	Next
	
	
	
End Sub


Private Sub pan_pup_Click
	
End Sub


Sub get_lessen_id(id As Int) As Cursor
	Dim cur3 As Cursor
	cur3 = Main.sq_db.ExecQuery("SELECT * FROM lessen WHERE id="&id)
	cur3.Position=0
	Return cur3
End Sub

Private Sub btn_edit_Click
	Dim sender_tag As Label
	sender_tag=Sender
	current_id=sender_tag.Tag
	Dim lessen As Cursor = get_lessen_id(sender_tag.Tag)
	
	pan_all.Visible=True
	et_add_lessen.Text=lessen.GetString("name")
	et_add_book.Text=lessen.GetString("book")
	
	edit_index=1
End Sub

Private Sub btn_del_Click
	Dim sender_tag As Label
	sender_tag=Sender
	Dim lessen As Cursor = get_lessen_id(sender_tag.Tag)
	
	
	Msgbox2Async(Main.loc.Localize("lessen-4"), Main.loc.Localize("book-10"), Main.loc.Localize("main-14"), "", Main.loc.Localize("main-15"), Null, False)
	Wait For Msgbox_Result (Result As Int)
	If Result = DialogResponse.POSITIVE Then
		
		Main.query="DELETE FROM lessen WHERE name='"&lessen.GetString("name")&"'"
		Main.sq_db.ExecNonQuery(Main.query)
		Main.query="DELETE FROM words WHERE lessen='"&lessen.GetString("name")&"'"
		Main.sq_db.ExecNonQuery(Main.query)
		ToastMessageShow(Main.loc.Localize("book-11"),False)
		custom_LS.Clear
		Activity_Create(True)
	End If
	
End Sub