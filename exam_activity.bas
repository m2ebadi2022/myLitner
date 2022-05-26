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
	Dim count_correct As Int=0
	Dim count_incorrect As Int=0
	
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btn_back As Button

	
	Private Label3 As Label
	Private lbl_quse As Label
	Private btn_a2 As Button
	Private btn_a1 As Button
	Private btn_a3 As Button
	Private btn_a4 As Button
	Private btn_next As Button
	Private Label1 As Label
	Private lbl_current_quse As Label
	
	Dim index As Int=0
	Private Panel1 As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("exam_layout")
	Panel1.Color=Main.app_color
	Activity.Color=Main.app_color
	
	
	ques_generator(index)
	
	lbl_current_quse.Text=(index+1)&" از "&init_exam_activity.list_exam_ques.Size
	count_correct=0
	count_incorrect=0
	
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
	btn_a1.Typeface = Main.app_font
	btn_a2.Typeface = Main.app_font
	btn_a3.Typeface = Main.app_font
	btn_a4.Typeface = Main.app_font
	btn_next.Typeface = Main.app_font
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
Sub ques_generator (id As Int)
	If(id<= init_exam_activity.list_exam_ques.Size-1 )Then
		lbl_current_quse.Text=id+1
		btn_a1.Enabled=True
		btn_a2.Enabled=True
		btn_a3.Enabled=True
		btn_a4.Enabled=True
		btn_a1.Color=Colors.White
		btn_a2.Color=Colors.White
		btn_a3.Color=Colors.White
		btn_a4.Color=Colors.White
	
		Main.query="SELECT * FROM words WHERE id="&init_exam_activity.list_exam_ques.Get(id)
		Dim cur1 As Cursor
		cur1 = Main.sq_db.ExecQuery(Main.query)
		cur1.Position=0
		lbl_quse.Text=cur1.GetString("question")
		Dim other_answ As List
		other_answ.Initialize
		Dim cur2 As Cursor
		cur2 = Main.sq_db.ExecQuery("Select * FROM words WHERE id IN (Select id FROM words ORDER BY RANDOM() LIMIT 3)")
		For p=0 To cur2.RowCount-1
			cur2.Position=p
			If(cur2.GetString("answer")==cur1.GetString("answer"))Then
				other_answ.Add("دیگر")
				Continue
		End If
			other_answ.Add(cur2.GetString("answer"))
		Next
	
	
		Dim rand_bt As Int
		rand_bt= Rnd(1,5)
		Select rand_bt
			Case 1
				btn_a1.Text=cur1.GetString("answer")
				btn_a2.Text=other_answ.Get(0)
				btn_a3.Text=other_answ.Get(1)
				btn_a4.Text=other_answ.Get(2)
				btn_a1.Tag=cur1.GetString("answer")
			Case 2
				btn_a2.Text=cur1.GetString("answer")
				btn_a1.Text=other_answ.Get(0)
				btn_a3.Text=other_answ.Get(1)
				btn_a4.Text=other_answ.Get(2)
				btn_a2.Tag=cur1.GetString("answer")
			Case 3
				btn_a3.Text=cur1.GetString("answer")
				btn_a2.Text=other_answ.Get(0)
				btn_a1.Text=other_answ.Get(1)
				btn_a4.Text=other_answ.Get(2)
				btn_a3.Tag=cur1.GetString("answer")
			Case 4
				btn_a4.Text=cur1.GetString("answer")
				btn_a2.Text=other_answ.Get(0)
				btn_a3.Text=other_answ.Get(1)
				btn_a1.Text=other_answ.Get(2)
				btn_a4.Tag=cur1.GetString("answer")
			 			
		End Select

		If(id==init_exam_activity.list_exam_ques.Size-1)Then
			ToastMessageShow("آخرین سوال",False)
		End If
	Else
		StartActivity(result_activity)
		Activity.finish
	End If
	
End Sub

Private Sub btn_next_Click
	index=index+1
	ques_generator(index)
	
	
	
End Sub

Private Sub btn_a4_Click
	If(btn_a4.Text==btn_a4.Tag)Then
		btn_a4.Color=0xFF33FF00  '' green
		count_correct=count_correct+1
	Else
		btn_a4.Color=0xFFFF1500 '' red
		count_incorrect=count_incorrect+1
	End If
	chek_true
End Sub

Private Sub btn_a3_Click
	If(btn_a3.Text==btn_a3.Tag)Then
		btn_a3.Color=0xFF33FF00
		count_correct=count_correct+1
	Else
		btn_a3.Color=0xFFFF1500
		count_incorrect=count_incorrect+1
	End If
	chek_true
End Sub

Private Sub btn_a1_Click
	If(btn_a1.Text==btn_a1.Tag)Then
		btn_a1.Color=0xFF33FF00
		count_correct=count_correct+1
	Else
		btn_a1.Color=0xFFFF1500
		count_incorrect=count_incorrect+1
	End If
	chek_true
End Sub

Private Sub btn_a2_Click
	If(btn_a2.Text==btn_a2.Tag)Then
		btn_a2.Color=0xFF33FF00
		count_correct=count_correct+1
	Else
		btn_a2.Color=0xFFFF1500
		count_incorrect=count_incorrect+1
	End If
	chek_true
End Sub
Sub chek_true 
	If(btn_a1.Text==btn_a1.Tag)Then
		btn_a1.Color=0xFF33FF00
	Else If (btn_a2.Text==btn_a2.Tag)Then
		btn_a2.Color=0xFF33FF00
	Else If (btn_a3.Text==btn_a3.Tag)Then
		btn_a3.Color=0xFF33FF00
	Else If (btn_a4.Text==btn_a4.Tag)Then
		btn_a4.Color=0xFF33FF00
	End If
	btn_a1.Enabled=False
	btn_a2.Enabled=False
	btn_a3.Enabled=False
	btn_a4.Enabled=False
End Sub

Private Sub btn_back_Click
	StartActivity(init_exam_activity)
	Activity.finish
End Sub
Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		btn_back_Click
		Return True
	Else
		Return False
	End If
End Sub