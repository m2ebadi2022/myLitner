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
	Dim tts1 As TTS
	Private lbl_qustion As Label
	Private lbl_answer As Label
	Private btn_nop As Button
	Private btn_ok As Button
	Private ProgressBar1 As ProgressBar
	Private lbl_progres As Label
	Private nop_count As Label
	Private ok_count As Label
	Private pan_all As Panel
	Private pan_menu As Panel
	Private pan_edite_word As Panel
	Private et_edite_question As EditText
	Private et_edite_answer As EditText
	Private btn_tts As Button
	Private lbl_path As Label
	Private lbl_synonim As Label
	Private lbl_codeing As Label
	Private Label1 As Label
	Private Label3 As Label
	Private Label5 As Label
	
	Private et_edite_synonim As EditText
	Private et_edite_codeing As EditText
	Private Panel1 As Panel
	Private btn_star As Button
	Private scroll_veiw As ScrollView
	Private btn_edite_word As Button
	Private btn_delete_word As Button
	Private Label2 As Label
	Private btn_no_edite As Button
	Private btn_yes_edite As Button
	Private lbl_next As Label
	Private lbl_prev As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("word_layout")
	''-------setting
	Panel1.Color=Main.app_color
	'pan_edite_word.Color=Main.app_color
	''-------------
	scroll_veiw.Panel.LoadLayout("scroll_item_word")
	scroll_veiw.Height=500dip
	btn_nop.Visible=False
	btn_ok.Visible=False
	ProgressBar1.Visible=False
	lbl_progres.Visible=False
	nop_count.Visible=False
	ok_count.Visible=False
	lbl_codeing.Visible=True
	lbl_synonim.Visible=True
	lbl_next.Visible=False
	lbl_prev.Visible=False
	
	
	If tts1.IsInitialized = False Then
		tts1.Initialize("TTS1")
	End If
	tts1.SpeechRate=0.5
	
	
	
		Dim cur1 As Cursor
		Main.query="Select * FROM words WHERE id="&Main.current_word_id
		cur1 = Main.sq_db.ExecQuery(Main.query)
		cur1.Position=0
		lbl_qustion.Text=cur1.GetString("question")
		lbl_answer.Text=cur1.GetString("answer")
		
		Dim state As Int=cur1.GetString("state")
		If(state==0)Then
				
			btn_star.Text=Chr(0xF006)
			btn_star.TextColor=Colors.Gray
		Else
			btn_star.Text=Chr(0xF005)
			btn_star.TextColor=0xFFE4BA00
				
		End If
		
		If (cur1.GetString("codeing")==Null) Then
			lbl_codeing.Text=""
		Else
			lbl_codeing.Text=cur1.GetString("codeing")
		End If
		
		If(cur1.GetString("synonym")==Null) Then
			lbl_synonim.Text=""
		Else
			lbl_synonim.Text=cur1.GetString("synonym")
		End If
	
		Main.current_word=lbl_qustion.Text
	
	lbl_path.Text=cur1.GetString("book")&" / "&cur1.GetString("lessen")&" / "&"level "&cur1.GetString("level")
		
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
	
	Label1.Text=Main.loc.Localize("level-9")
	Label3.Text=Main.loc.Localize("level-10")
	
	''==========================================
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
Private Sub btn_tts_Click
	tts1.Speak(lbl_qustion.Text, True)
End Sub
Sub TTS1_Ready (Success As Boolean)
	If Success Then
		
	Else
		ToastMessageShow(Main.loc.Localize("word-7"), False)
		btn_tts.Enabled=False
	End If
End Sub

Private Sub btn_yes_edite_Click
	If(et_edite_question.Text.Trim=="" Or et_edite_answer.Text.Trim=="") Then
		ToastMessageShow(Main.loc.Localize("level-12"),False)
	Else
		
		
		Main.query=" UPDATE words SET question = ? ,answer = ? ,synonym = ? , codeing= ? WHERE id= ? "
	
		Main.sq_db.ExecNonQuery2(Main.query,Array As Object(et_edite_question.Text,et_edite_answer.Text,et_edite_synonim.Text,et_edite_codeing.Text,Main.current_word_id))
		
		pan_all_Click
		lbl_qustion.Text=et_edite_question.Text
		lbl_answer.Text=et_edite_answer.Text
		lbl_synonim.Text=et_edite_synonim.Text
		lbl_codeing.Text=et_edite_codeing.Text
	End If
	
End Sub

Private Sub btn_no_edite_Click
	pan_all_Click
End Sub

Private Sub btn_delete_word_Click
	pan_all_Click
	Msgbox2Async(Main.loc.Localize("word-9"), Main.loc.Localize("book-10"), Main.loc.Localize("main-14"), "", Main.loc.Localize("main-15"), Null, False)
	Wait For Msgbox_Result (Result As Int)
	If Result = DialogResponse.POSITIVE Then
		
		Main.query="DELETE FROM words WHERE id="&Main.current_word_id
		Main.sq_db.ExecNonQuery(Main.query)
		ToastMessageShow(Main.loc.Localize("book-11"),False)
		btn_back_Click
	End If
End Sub

Private Sub btn_edite_word_Click
	pan_all_Click
	pan_edite_word.Visible=True
	pan_all.Visible=True
	et_edite_question.Text=lbl_qustion.Text
	et_edite_answer.Text=lbl_answer.Text
	et_edite_synonim.Text=lbl_synonim.Text
	et_edite_codeing.Text=lbl_codeing.Text
End Sub

Private Sub pan_all_Click
	pan_all.Visible=False
	pan_menu.Visible=False
	pan_edite_word.Visible=False
End Sub

Private Sub btn_menu_Click
	pan_all.Visible=True
	pan_menu.Visible=True
End Sub

Private Sub btn_back_Click
	StartActivity(all_words_activity)
	Activity.finish
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then                           ' Hardware-Zurück Taste gedrückt
		If(pan_all.Visible=True)Then
			pan_all_Click
		Else
			btn_back_Click
		End If  
		Return True                                     ' Hardware-Zurück Taste deaktiviert
	Else
		Return False
	End If
End Sub

Private Sub brn_dictionary_Click
	StartActivity(dictionary_activity)
End Sub

Private Sub btn_star_Click
	
	If(btn_star.Text==Chr(0xF006))Then  ' is unlike
		btn_star.TextColor=0xFFE4BA00
		btn_star.Text=Chr(0xF005)
		
		Main.query=" UPDATE words SET state =1 WHERE id="& Main.current_word_id
		Main.sq_db.ExecNonQuery(Main.query)
		
	Else
		
		Main.query=" UPDATE words SET state =0 WHERE id="& Main.current_word_id
		Main.sq_db.ExecNonQuery(Main.query)
		
		btn_star.TextColor=Colors.Gray
		btn_star.Text=Chr(0xF006)
		
	End If
End Sub

Private Sub pan_edite_word_Click
	
End Sub