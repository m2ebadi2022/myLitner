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

	
	Private lbl_qustion As Label
	Private lbl_answer As Label
	Private ProgressBar1 As ProgressBar
	Private lbl_progres As Label
	Private nop_count As Label
	Private ok_count As Label
	Private btn_ok As Button
	Private btn_nop As Button
	
	Dim id_list As List
	Dim ques_list As List
	Dim answ_list As List
	Dim synonim_list As List
	Dim codeing_list As List
	Dim state_list As List  '--- state is like stared
	Dim index As Int
	Dim ok_index As Int =0
	Dim nop_index As Int =0
	Dim tts1 As TTS
	
	
	
	Private pan_all As Panel
	Private pan_menu As Panel
	Private pan_edite_word As Panel
	Private et_edite_answer As EditText
	Private et_edite_question As EditText
	Private btn_tts As Button
	Private lbl_path As Label
	Private lbl_synonim As Label
	Private lbl_codeing As Label
	Private et_edite_synonim As EditText
	Private et_edite_codeing As EditText
	Private Label3 As Label  'مترادف
	Private Label1 As Label  'کدینگ
	Private Panel1 As Panel
	Private Label5 As Label
	Private btn_star As Button
	Private scroll_veiw As ScrollView
	Private btn_edite_word As Button
	Private btn_delete_word As Button
	Private Label2 As Label
	Private btn_no_edite As Button
	Private btn_yes_edite As Button
	Private lbl_prev As Label
	Private lbl_next As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("word_layout")
	''-------setting
	Panel1.Color=Main.app_color
	'lbl_next.Color=Main.app_color
	'lbl_prev.Color=Main.app_color
	
	''-------------
	scroll_veiw.Panel.LoadLayout("scroll_item_word")
	lbl_answer.TextColor=0xFF8C8C8C
	lbl_answer.Text="برای دیدن معنی کلیک کنید"
	
	
	lbl_path.Text=Main.book_name&" / "&Main.lessen_name&" / "&"level "&Main.level_name
	
	If tts1.IsInitialized = False Then
		tts1.Initialize("TTS1")
	End If
	tts1.SpeechRate=0.5
	'--------------------------------------
	
	' -- محاسبه اختلاف زمان ها------------
'	Dim t1 As Long = DateTime.DateTimeParse("01/01/2021","00:00:00")
'	Dim t2 As Long = DateTime.DateTimeParse("01/01/2022","00:00:00")
'	Dim p As Period = DateUtils.PeriodBetween(t1 , t2)
'	Log(p.Days)
	
' --------- اختلاف زمانها ----------
	'7200000 => 2h
	'28800000 => 8h
	'57600000 => 16h
	'86400000 => 1d
	'259200000 => 3d
	'432000000 => 5d
	'864000000 =>10d
	'2592000000 =>30d
	'7772400000 =>3m
	'15634800000 =>6m
	'31536000000 =>1y


	
	'---------------------------------------
	
	
	
	
	Dim cur1 As Cursor
	Main.query="SELECT * FROM words WHERE book='"& Main.book_name &"' AND lessen='"&Main.lessen_name&"' AND level="&Main.level_name
	cur1 = Main.sq_db.ExecQuery(Main.query)
	
	
	id_list.Initialize
	ques_list.Initialize
	answ_list.Initialize
	synonim_list.Initialize
	codeing_list.Initialize
	state_list.Initialize
	
	Dim t1_now As Long = DateTime.DateTimeParse(DateTime.Date(DateTime.Now),DateTime.Time(DateTime.Now))
	Dim t2_word As Long
	
	For i=0 To cur1.RowCount-1
		cur1.Position=i
		t2_word=cur1.GetString("time_enter")
		
		If (Main.level_name==1) Then
			id_list.Add(cur1.GetInt("id"))
			ques_list.Add(cur1.GetString("question"))
			answ_list.Add(cur1.GetString("answer"))
			synonim_list.Add(cur1.GetString("synonym"))
			codeing_list.Add(cur1.GetString("codeing"))
			state_list.Add(cur1.GetString("state"))
			
		Else If ((t1_now-t2_word) > Main.time_list_level.get(Main.level_name-1)) Then
			id_list.Add(cur1.GetInt("id"))
			ques_list.Add(cur1.GetString("question"))
			answ_list.Add(cur1.GetString("answer"))
			synonim_list.Add(cur1.GetString("synonym"))
			codeing_list.Add(cur1.GetString("codeing"))
			state_list.Add(cur1.GetInt("state"))
		End If
		
		
	Next
	
	lbl_progres.Text="1/"&id_list.Size
	
	index=0
	lbl_next_generator(index)
	
	
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
	btn_ok.Typeface=Main.app_font
	btn_nop.Typeface=Main.app_font
	btn_edite_word.Typeface=Main.app_font
	btn_delete_word.Typeface=Main.app_font
	
	lbl_qustion.TextSize=Main.app_font_size*2
	lbl_answer.TextSize=Main.app_font_size*2
	lbl_synonim.TextSize=Main.app_font_size/1.5
	lbl_codeing.TextSize=Main.app_font_size/1.5
	Label3.TextSize=Main.app_font_size/1.5
	Label1.TextSize=Main.app_font_size/1.5
	
	Label5.TextSize=20
	lbl_path.TextSize=13
	''==============string==================
	Label5.Text=Main.loc.Localize("word-1")
	Label2.Text=Main.loc.Localize("word-3")
	btn_edite_word.Text=Main.loc.Localize("word-2")
	btn_delete_word.Text=Main.loc.Localize("book-10")
	btn_yes_edite.Text=Main.loc.Localize("book-5")
	btn_no_edite.Text=Main.loc.Localize("book-6")
	et_edite_question.Hint=Main.loc.Localize("level-7")
	et_edite_answer.Hint=Main.loc.Localize("level-8")
	et_edite_synonim.Hint=Main.loc.Localize("level-9")
	et_edite_codeing.Hint=Main.loc.Localize("level-10")
	btn_ok.Text=Main.loc.Localize("word-4")
	btn_nop.Text=Main.loc.Localize("word-5")
	Label1.Text=Main.loc.Localize("level-9")
	Label3.Text=Main.loc.Localize("level-10")
	lbl_next.Text=Main.loc.Localize("word-10")
	lbl_prev.Text=Main.loc.Localize("word-11")
	''==========================================
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub lbl_answer_Click
	lbl_answer.TextColor=Colors.Blue
	lbl_answer.Text=lbl_answer.Tag
	
	If (index >= id_list.Size) Then
		btn_ok.Enabled=False
		btn_nop.Enabled=False
		Label1.Visible=True
		Label3.Visible=True
		lbl_synonim.Visible=True
		lbl_codeing.Visible=True
		ToastMessageShow(Main.loc.Localize("word-6"), False)
	Else
		btn_ok.Enabled=True
		btn_nop.Enabled=True
		Label1.Visible=True
		Label3.Visible=True	
		lbl_synonim.Visible=True
		lbl_codeing.Visible=True
	End If
	
End Sub

Private Sub pan_answer_Click
	lbl_answer_Click
End Sub

Private Sub btn_tts_Click
		tts1.Speak(lbl_qustion.Text, True)
End Sub
Sub TTS1_Ready (Success As Boolean)
	If Success Then
		
	Else
		ToastMessageShow(Main.loc.Localize("word-7"), True)
		btn_tts.Enabled=False
	End If
End Sub

Private Sub btn_nop_Click
	Dim cur2 As Cursor
	Dim count As Int
	cur2= Main.sq_db.ExecQuery("SELECT * FROM words WHERE id="& id_list.Get(index))
	cur2.Position=0
	count=cur2.GetInt("count_wrong")
	count=count+1
	
	
	
	Main.query=" UPDATE words SET level =1 ,count_wrong="&count&" WHERE id="& id_list.Get(index)
	Main.sq_db.ExecNonQuery(Main.query)
	
	index=index+1
	lbl_next_generator(index)
	nop_index=nop_index+1
	nop_count.Text=nop_index
	
	btn_ok.Enabled=False
	btn_nop.Enabled=False
	
	lbl_prev.Enabled=True
	lbl_prev.Color=Main.app_color
End Sub

Private Sub btn_ok_Click
	Dim cur2 As Cursor
	Dim count As Int
	cur2= Main.sq_db.ExecQuery("SELECT * FROM words WHERE id="& id_list.Get(index))
	cur2.Position=0
	count=cur2.GetInt("count_right")
	count=count+1
	If (count==Main.last_level) Then
		count=100
	End If

	
	
	If (Main.level_name == Main.level_list_name.Size) Then
		Main.query=" UPDATE words SET level = 100,time_enter ="& DateTime.DateTimeParse(DateTime.Date(DateTime.Now),DateTime.Time(DateTime.Now)) &",count_right="&count&" WHERE id="& id_list.Get(index)
	Else
		Main.query=" UPDATE words SET level = " & (Main.level_name+1) & ",time_enter ="& DateTime.DateTimeParse(DateTime.Date(DateTime.Now),DateTime.Time(DateTime.Now)) &",count_right="&count&" WHERE id="& id_list.Get(index)
	End If
	Main.sq_db.ExecNonQuery(Main.query)
	
	
	
	index=index+1
	lbl_next_generator(index)
	ok_index=ok_index+1
	ok_count.Text=ok_index
	
	btn_ok.Enabled=False
	btn_nop.Enabled=False
	
	lbl_prev.Enabled=True
	lbl_prev.Color=Main.app_color
End Sub

Sub lbl_next_generator(id As Int)
	If(id==0)Then
		lbl_prev.Enabled=False
		lbl_prev.Color=0xFF686868
	Else If (index == id_list.Size-1)Then
		lbl_next.Enabled=False
		lbl_next.Color=0xFF686868
	End If
	
If (id < id_list.Size) Then
		btn_ok.Enabled=False
		btn_nop.Enabled=False
		Label1.Visible=False
		Label3.Visible=False
		lbl_codeing.Visible=False
		lbl_synonim.Visible=False
		
		lbl_answer.TextColor=0xFF8C8C8C
		lbl_answer.Text=Main.loc.Localize("word-8")
		
		lbl_progres.Text= (id+1)&"/"&id_list.Size
		Dim div As Double
		div=100/id_list.Size
		ProgressBar1.Progress=(id+1)*div
		
		lbl_qustion.Text=ques_list.Get(id)
		lbl_answer.Tag=answ_list.Get(id)
		
		Dim state As Int=state_list.Get(id)
		If(state==0)Then
			
			btn_star.Text=Chr(0xF006)
			btn_star.TextColor=Colors.Gray	
		Else
			btn_star.Text=Chr(0xF005)
			btn_star.TextColor=0xFFE4BA00
			
		End If
		
	
		If (codeing_list.Get(id)==Null) Then
			lbl_codeing.Text=""
		Else
			lbl_codeing.Text=codeing_list.Get(id)
		End If
		
		If(synonim_list.Get(id)==Null) Then
			lbl_synonim.Text=""
		Else
			lbl_synonim.Text=synonim_list.Get(id)
		End If
		
		
		
		
	Main.current_word=lbl_qustion.Text
	Else
		If(id_list.Size==0) Then
			ToastMessageShow(Main.loc.Localize("level-13"), False)
			StartActivity(level_activity)
			Activity.finish
		Else
			btn_ok.Enabled=False
			btn_nop.Enabled=False
			ToastMessageShow(Main.loc.Localize("word-6"), False)
			'StartActivity(level_activity)
			'Activity.finish
		End If
		
		
	End If
	
	
End Sub

Private Sub btn_menu_Click
	pan_all.Visible=True
	pan_menu.Visible=True
End Sub

Private Sub btn_back_Click
	StartActivity(level_activity)
	Activity.finish
End Sub

Private Sub btn_edite_word_Click
	pan_all_Click
	pan_edite_word.Visible=True
	pan_all.Visible=True
	et_edite_question.Text=lbl_qustion.Text
	et_edite_answer.Text=lbl_answer.Tag
	et_edite_synonim.Text=lbl_synonim.Text
	et_edite_codeing.Text=lbl_codeing.Text
End Sub

Private Sub btn_delete_word_Click
	If (index==id_list.Size) Then
		index=index-1
	End If
	pan_all_Click
	
	Msgbox2Async(Main.loc.Localize("word-9"), Main.loc.Localize("book-10"), Main.loc.Localize("main-14"), "", Main.loc.Localize("main-15"), Null, False)
	Wait For Msgbox_Result (Result As Int)
	If Result = DialogResponse.POSITIVE Then
		
		Main.query="DELETE FROM words WHERE id='"&id_list.Get(index)&"'"
		Main.sq_db.ExecNonQuery(Main.query)
		lbl_progres.Text=""
		ProgressBar1.Progress=0
		ToastMessageShow(Main.loc.Localize("book-11"),False)
		Activity_Create(True)
	End If
End Sub

Private Sub pan_all_Click
	pan_all.Visible=False
	pan_menu.Visible=False
	pan_edite_word.Visible=False
End Sub

Private Sub btn_no_edite_Click
	pan_all_Click
End Sub

Private Sub btn_yes_edite_Click
	If (index==id_list.Size) Then
		index=index-1
	End If
	If(et_edite_question.Text.Trim=="" Or et_edite_answer.Text.Trim=="") Then
		ToastMessageShow(Main.loc.Localize("level-12"),False)
	Else
		Main.query=" UPDATE words SET question = ? ,answer = ? ,synonym = ? , codeing= ? WHERE id= ?"
		
		Main.sq_db.ExecNonQuery2(Main.query, Array As Object(et_edite_question.Text,et_edite_answer.Text, et_edite_synonim.Text, et_edite_codeing.Text , id_list.Get(index) ))
	pan_all_Click
	lbl_qustion.Text=et_edite_question.Text
	lbl_answer.Text=et_edite_answer.Text
	lbl_answer.Tag=et_edite_answer.Text
	lbl_synonim.Text=et_edite_synonim.Text
	lbl_codeing.Text=et_edite_codeing.Text
	End If	
	
	
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

Private Sub brn_dictionary_Click
	StartActivity(dictionary_activity)
End Sub


Private Sub pan_edite_word_Click
	
End Sub

Private Sub btn_star_Click
	Dim indd As Int=index
	If (index==id_list.Size) Then
		indd=index-1
	End If
	
	If(btn_star.Text==Chr(0xF006))Then  ' is unlike
		btn_star.TextColor=0xFFE4BA00
		btn_star.Text=Chr(0xF005)
		
		Main.query=" UPDATE words SET state =1 WHERE id="& id_list.Get(indd)
		Main.sq_db.ExecNonQuery(Main.query)
		
	Else
		
		Main.query=" UPDATE words SET state =0 WHERE id="& id_list.Get(indd)
		Main.sq_db.ExecNonQuery(Main.query)
		
		btn_star.TextColor=Colors.Gray
		btn_star.Text=Chr(0xF006)
		
	End If
	
End Sub

Private Sub lbl_next_Click
	
	lbl_prev.Enabled=True
	lbl_prev.Color=Main.app_color
	
		index=index+1
		lbl_next_generator(index)	
		If(index == id_list.Size-1)Then
			lbl_next.Enabled=False
			lbl_next.Color=0xFF686868
		End If
	
	
End Sub

Private Sub lbl_prev_Click
	
	lbl_next.Enabled=True
	lbl_next.Color=Main.app_color
	
	
		index=index-1
		lbl_next_generator(index)
		If(index==0)Then
			lbl_prev.Enabled=False
			lbl_prev.Color=0xFF686868
		End If
	
	
	
End Sub