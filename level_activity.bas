B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10.7
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: false
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim CC As ContentChooser
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	
	Dim xui As XUI
	Dim count_w As List
	Dim count_avail_all As Int =0
	Dim count_ended As Int =0
	
	Private btn_menu As Button
	Private pan_menu As Panel
	Private pan_all As Panel
	Private pan_add_word As Panel
	Private et_add_answer As EditText
	Private et_add_question As EditText
	
	Private Workbook1 As ReadableWorkbook
	Private Sheet1 As ReadableSheet
	
	Private lbl_path As Label
	Private et_add_synonim As EditText
	Private et_add_codeing As EditText
	Private lbl_all As Label
	Private lbl_available_all As Label
	Private Custom_LV_level As CustomListView
	Private lbl_ended As Label
	Private lbl_count_level As Label
	Private lbl_avail_level As Label
	Private lbl_level As Label
	
	Private Panel1 As Panel
	Private Label3 As Label
	Private pan_ended As Panel
	Private btn_add_word As Button
	Private btn_add_excel As Button
	
	Private Label2 As Label
	Private btn_no_add As Button
	Private btn_yes_add As Button
	
	
	Private btn_add_by_voice As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("level_layout")
	''-------setting
	Panel1.Color=Main.app_color
	'pan_add_word.Color=Main.app_color
	btn_menu.TextColor=Main.app_color
	
	''-------------
	
	lbl_path.Text=Main.book_name&" / "&Main.lessen_name

	Main.end_page="level"
	
	Dim pic_level_1 As BitmapDrawable
	pic_level_1.Initialize(LoadBitmap(File.DirAssets, "box.png"))
	pic_level_1.Gravity = Gravity.FILL
	Dim pic_level_2 As BitmapDrawable
	pic_level_2.Initialize(LoadBitmap(File.DirAssets, "box2.png"))
	pic_level_2.Gravity = Gravity.FILL
	
	
	count_w.Initialize
	
	Main.query="SELECT * FROM level"
	Dim cur1 As Cursor
	Dim cur3 As Cursor
	cur1 = Main.sq_db.ExecQuery(Main.query)
	For i=0 To cur1.RowCount-1
		cur1.Position=i
		
		
		Main.query="SELECT COUNT(id) FROM words WHERE book='"& Main.book_name &"' AND lessen='"&Main.lessen_name&"' AND level='"& cur1.GetInt("name")&"'"
		
		cur3 = Main.sq_db.ExecQuery(Main.query)
		cur3.Position=0
		
		
		Dim p As B4XView = xui.CreatePanel("")
		p.SetLayoutAnimated(0, 0, 0, 100%x, 86dip)
		
		p.LoadLayout("item_level")
		
		lbl_level.Text=cur1.GetString("name")
		lbl_avail_level.Text= available_words(i)
		lbl_count_level.Text=cur3.GetInt("COUNT(id)")
		If(lbl_avail_level.Text==0)Then
			lbl_level.Background=pic_level_2
		Else
			lbl_level.Background=pic_level_1
		End If
		Custom_LV_level.Add(p, i)
		
		
		count_avail_all=count_avail_all+available_words(i)
		count_w.Add(cur3.GetInt("COUNT(id)"))
		
	Next
	
	
	lbl_all.Text=Main.loc.Localize("level-5")&" : "&count_words_lessen
	lbl_available_all.Text=Main.loc.Localize("level-4")&" : "&count_avail_all
	
	'' --- بدست آوردن تعداد لغات یادگرفته شده -مرحله 100 
	Dim cur5 As Cursor
	cur5 = Main.sq_db.ExecQuery("SELECT COUNT(id) FROM words WHERE book='"& Main.book_name &"' AND lessen='"&Main.lessen_name&"' AND level=100")
	cur5.Position=0
	count_ended=cur5.GetInt("COUNT(id)")
	lbl_ended.Text=Main.loc.Localize("level-6")&" : "&count_ended
	
	
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
	btn_add_word.Typeface=Main.app_font
	btn_add_excel.Typeface=Main.app_font
	btn_add_by_voice.Typeface=Main.app_font
	Label3.TextSize=18
	lbl_path.TextSize=12
	
	''----------string-----------
	Label3.Text=Main.loc.Localize("level-1")
	Label2.Text=Main.loc.Localize("level-2")
	btn_add_word.Text=Main.loc.Localize("level-2")
	btn_add_excel.Text=Main.loc.Localize("level-3")
	btn_yes_add.Text=Main.loc.Localize("book-5")
	btn_no_add.Text=Main.loc.Localize("book-6")
	et_add_question.Hint=Main.loc.Localize("level-7")
	et_add_answer.Hint=Main.loc.Localize("level-8")
	et_add_synonim.Hint=Main.loc.Localize("level-9")
	et_add_codeing.Hint=Main.loc.Localize("level-10")
	
	
	
	
	
	''------------------------------
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
Sub count_words_lessen As Int
	
	Dim cur4 As Cursor
		
	cur4 = Main.sq_db.ExecQuery("SELECT COUNT(id) FROM words WHERE book='"& Main.book_name &"' AND lessen='"&Main.lessen_name&"'")
	cur4.Position=0
	Return cur4.GetInt("COUNT(id)")
	
End Sub

Private Sub btn_menu_Click
	
	If(btn_menu.Text==Chr(0xE5C9)) Then
		
		
	pan_menu.Visible=False
		pan_all.Visible=False
		btn_menu.Text=Chr(0xE147)
	Else
		
		
		pan_menu.Visible=True
		pan_all.Visible=True
		btn_menu.Text=Chr(0xE5C9)		
	End If
	
	
End Sub


 Sub  available_words (id As Int) As Int
	
	
	Dim count_wa As Int=0 '--تعداد لغات در دسترس
	Dim cur2 As Cursor
	Main.query="SELECT * FROM words WHERE book='"& Main.book_name &"' AND lessen='"&Main.lessen_name&"' AND level="&(id+1)
	cur2 = Main.sq_db.ExecQuery(Main.query)

	Dim t1_now As Long = DateTime.DateTimeParse(DateTime.Date(DateTime.Now),DateTime.Time(DateTime.Now))
	Dim t2_word As Long
	
	For i=0 To cur2.RowCount-1
		cur2.Position=i
		t2_word=cur2.GetString("time_enter")

		If ((t1_now-t2_word) > Main.time_list_level.get(id)) Then
			count_wa=count_wa+1

		End If	
	Next
	
	Return count_wa
	
End Sub

Private Sub btn_add_word_Click
	pan_all_Click
	
	pan_all.Visible=True
	btn_menu.Visible=False
	pan_add_word.Visible=True
	
End Sub

Private Sub btn_add_excel_Click
	pan_all_Click
	CC.Initialize("CC")
	CC.Show("*/*", "Choose excel file")
	
End Sub
Sub CC_Result (Success As Boolean, Dir As String, FileName As String)
	
	If Success = True Then
		Try
			Workbook1.Initialize(Dir, FileName)
			Sheet1 = Workbook1.GetSheet(0)
			
			For row = 1 To Sheet1.RowsCount - 1
				If (Sheet1.GetCellValue(0, row).Trim=="") Then
					Continue
				End If
				
				Main.sq_db.ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)", Array As Object(Sheet1.GetCellValue(0, row), Sheet1.GetCellValue(1, row), Main.book_name, Main.lessen_name,Sheet1.GetCellValue(2, row),Sheet1.GetCellValue(3, row)))
			Next
		Catch
			ToastMessageShow(Main.loc.Localize("level-11")&LastException,True)
			
		End Try
		
		
		Custom_LV_level.Clear
		Activity_Create(True)
		ToastMessageShow(Main.loc.Localize("book-8"),False)
		
	Else
		ToastMessageShow(Main.loc.Localize("level-11"),False)
	End If
		
End Sub

Private Sub pan_all_Click
	
	pan_menu.Visible=False
	
	pan_all.Visible=False
	
	pan_add_word.Visible=False
	btn_menu.Visible=True
	btn_menu.Text=Chr(0xE147)
End Sub

Private Sub btn_yes_add_Click
	
	If(et_add_question.Text.Trim=="" Or et_add_answer.Text.Trim=="") Then
		ToastMessageShow(Main.loc.Localize("level-12"),False)
	Else
		
		Main.sq_db.ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)", Array As Object(et_add_question.Text,et_add_answer.Text, Main.book_name, Main.lessen_name,et_add_synonim.Text,et_add_codeing.Text))
		
		
		pan_all_Click
		
		Custom_LV_level.Clear
		Activity_Create(True)
		ToastMessageShow(Main.loc.Localize("book-8"),False)
	
	End If
	
End Sub

Private Sub btn_no_add_Click
	pan_all_Click
End Sub

Private Sub btn_back_Click
	StartActivity(lessen_activity)
	Activity.finish
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		If(pan_all.Visible=True)Then
			pan_all_Click
		Else
			btn_back_Click	                        
		End If
		
		Return True                                   
	Else
		Return False
	End If
End Sub

Private Sub Custom_LV_level_ItemClick (Index As Int, Value As Object)
	
	
	
	Main.level_name= Index+1
	If(available_words(Index)==0) Then
		ToastMessageShow(Main.loc.Localize("level-13"), False)
	Else
		StartActivity(word_activity)
		Activity.finish
	End If
	
	
End Sub

Private Sub pan_ended_Click
	If(count_ended==0)Then
		ToastMessageShow(Main.loc.Localize("level-14"),False)
	Else
		StartActivity(all_words_activity)
	End If
	
End Sub

Private Sub pan_add_word_Click
	
End Sub


Private Sub btn_add_by_voice_Click
	StartActivity(add_voice_activity)
	Activity.finish
End Sub