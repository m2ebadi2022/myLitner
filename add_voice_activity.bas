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

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim sr As SpeechRecognitionNoUI
	Dim p As Phone
	Dim index As Int
	Dim count_added As Int=0
	
	Private btn_qustion_v As Button
	Private btn_answer_v As Button
	Private btn_save_next As Button
	Private ProgBar1 As ProgressBar
	Private ProgBar2 As ProgressBar
	Private et_ques As EditText
	Private et_answ As EditText
	Private btn_edit_save As Button
	Private pan_all As Panel
	Private sp_lang_ques As Spinner
	Private sp_lang_answ As Spinner
	Private lbl_count_added As Label
	Private Panel1 As Panel
	Private lbl_edit_pan As Label
	Private btn_synonym_v As Button
	Private btn_codeing_v As Button
	Private sp_lang_codeing As Spinner
	Private sp_lang_syno As Spinner
	Private ProgBar4 As ProgressBar
	Private ProgBar3 As ProgressBar
	Private et_synonym As EditText
	Private et_coding As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("add_voice_layout")
	sr.Initialize("sr",Me)
	sr.NoRecognizerBeep = True
	check_internet
	
	sp_lang_ques.AddAll(Array As Object("en","fa","ar","tr"))
	sp_lang_answ.AddAll(Array As Object("fa","en","ar","tr"))
	sp_lang_syno.AddAll(Array As Object("en","fa","ar","tr"))
	sp_lang_codeing.AddAll(Array As Object("fa","en","ar","tr"))
	
	
	sp_lang_ques.SelectedIndex=0
	sp_lang_answ.SelectedIndex=0
	sp_lang_syno.SelectedIndex=0
	sp_lang_codeing.SelectedIndex=0
	
	''===============color veiws ------------------
	Panel1.Color=Main.app_color
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
	btn_qustion_v.Typeface=Main.app_font
	btn_answer_v.Typeface=Main.app_font
	btn_synonym_v.Typeface=Main.app_font
	btn_codeing_v.Typeface=Main.app_font
	
	btn_save_next.Typeface=Main.app_font
	btn_edit_save.Typeface=Main.app_font
	
	lbl_edit_pan.Typeface=Typeface.MATERIALICONS
	lbl_edit_pan.TextSize=20
End Sub

Sub Activity_Resume
	Dim rp As RuntimePermissions
	rp.CheckAndRequest(rp.PERMISSION_RECORD_AUDIO)
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Activity_PermissionResult (Permission As String, Result As Boolean)
	Dim rp As RuntimePermissions
	If Permission = rp.PERMISSION_RECORD_AUDIO Then
		If Result Then
			'btn_qustion_v.Text = Chr(0xE02A)
			'btn_qustion_v.Enabled = True
			
			'btn_answer_v.Text = Chr(0xE02A)
			'btn_answer_v.Enabled = True
		Else
			ToastMessageShow("خطا در دسترسی به میکرفن ",True)
			'btn_qustion_v.Text = Chr(0xE02B)
			'btn_qustion_v.Enabled = False
			
			'btn_answer_v.Text = Chr(0xE02B)
			'btn_answer_v.Enabled = False
		End If
	End If
End Sub

Private Sub btn_save_next_Click
	
	If(btn_qustion_v.Text.Trim=="" Or btn_answer_v.Text.Trim=="") Then
		ToastMessageShow(Main.loc.Localize("level-12"),False)
	Else
		
		Main.sq_db.ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)", Array As Object(btn_qustion_v.Text,btn_answer_v.Text, Main.book_name, Main.lessen_name, btn_synonym_v.Text , btn_codeing_v.Text))
		
		btn_qustion_v.Text=""
		btn_answer_v.Text=""
		btn_synonym_v.Text=""
		btn_codeing_v.Text=""
		
		count_added=count_added+1
		lbl_count_added.Text=count_added
		End If
		
End Sub


Private Sub btn_qustion_v_Click
	If sr.IsInitialized Then
		sr.StartListening(sp_lang_ques.SelectedItem,True,False) 'زبان مورد نظر == الان انگلیسی هست
		index=1
	End If
End Sub

Private Sub btn_answer_v_Click
	If sr.IsInitialized Then
		sr.StartListening(sp_lang_answ.SelectedItem,True,False) 'زبان مورد نظر == الان فارسی هست
		index=2
	End If
End Sub

Private Sub btn_synonym_v_Click
	If sr.IsInitialized Then
		sr.StartListening(sp_lang_syno.SelectedItem,True,False) 'زبان مورد نظر == الان فارسی هست
		index=3
	End If
End Sub

Private Sub btn_codeing_v_Click
	If sr.IsInitialized Then
		sr.StartListening(sp_lang_codeing.SelectedItem,True,False) 'زبان مورد نظر == الان فارسی هست
		index=4
	End If
End Sub




Sub sr_Results(Texts As List)
	
	Dim Text As String = ""
	For Each t As String In Texts
		Text = Text & t 
	Next
	If(index==1)Then
		btn_qustion_v.Text=Text
	Else If (index==2)Then	
		btn_answer_v.Text=Text
	Else If (index==3)Then
		btn_synonym_v.Text=Text
	Else If (index==4)Then
		btn_codeing_v.Text=Text
	End If
End Sub

Sub sr_BeginningOfSpeech
	
	'btn_qustion_v.Enabled = False
	'btn_answer_v.Enabled = False
	
	If(index==1)Then
		ProgBar1.Visible=True
	Else If (index==2)Then
		ProgBar2.Visible=True
	Else If (index==3)Then
		ProgBar3.Visible=True
	Else If (index==4)Then
		ProgBar4.Visible=True
	End If
	
	
End Sub

Sub sr_EndOfSpeech
	
	ProgBar1.Visible=False
	ProgBar2.Visible=False
	ProgBar3.Visible=False
	ProgBar4.Visible=False
	
End Sub

Sub sr_ReadyForSpeech
	If(index==1)Then
		ProgBar1.Visible=True
	Else If (index==2)Then
		ProgBar2.Visible=True
	Else If (index==3)Then
		ProgBar3.Visible=True
	Else If (index==4)Then
		ProgBar4.Visible=True
	End If
End Sub

Sub sr_Error(Msg As String)
	
	ProgBar1.Visible=False
	ProgBar2.Visible=False
	ProgBar3.Visible=False
	ProgBar4.Visible=False
End Sub


Private Sub pan_all_Click
	pan_all.Visible=False
End Sub

Private Sub btn_edit_save_Click
	btn_qustion_v.Text=et_ques.Text
	btn_answer_v.Text=et_answ.Text
	btn_synonym_v.Text=et_synonym.Text
	btn_codeing_v.Text=et_coding.Text
	
	pan_all.Visible=False
End Sub

Private Sub lbl_edit_pan_Click
	et_ques.Text=btn_qustion_v.Text
	et_answ.Text=btn_answer_v.Text
	et_synonym.Text=btn_synonym_v.Text
	et_coding.Text=btn_codeing_v.Text
	
	pan_all.Visible=True
End Sub

Private Sub Panel2_Click
	
End Sub

Private Sub btn_back_Click
	StartActivity(level_activity)
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


Sub check_internet 
	
	Dim connected As Boolean
	If p.GetDataState="CONNECTED" Then
		connected=True
	Else If p.GetSettings("wifi_on")=1 Then
		connected=True
	End If
	If(connected=False)Then
		ProgBar1.Visible=False
		ProgBar2.Visible=False
		ProgBar3.Visible=False
		ProgBar4.Visible=False
		ToastMessageShow("اینترنت قطع است",True)
	End If
End Sub

Private Sub btn_close_Click
	pan_all_Click
End Sub
