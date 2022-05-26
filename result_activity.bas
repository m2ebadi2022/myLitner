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

	Private Label1 As Label
	Private btn_back As Button
	Private lbl_correct_present As Label
	Private lbl_cor As Label
	Private lbl_incor As Label
	Private btn_back_home As Button
	Private btn_all_exam As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("result_layout")


	lbl_correct_present.Text=Round2((exam_activity.count_correct/(init_exam_activity.list_exam_ques.Size))*100,2) & " % "

	lbl_cor.Text="درست : "&exam_activity.count_correct
	lbl_incor.Text="غلط : "&exam_activity.count_incorrect

	Main.sq_db.ExecNonQuery("INSERT INTO exam (date, all_qust, correct, incorrect) VALUES ('"&DateTime.Date(DateTime.Now)&"',"&init_exam_activity.list_exam_ques.Size&","&exam_activity.count_correct&","&exam_activity.count_incorrect&")")
	ToastMessageShow(Main.loc.Localize("book-8"),False)
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
	btn_back_home.Typeface = Main.app_font
	btn_all_exam.Typeface = Main.app_font
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub btn_all_exam_Click
	StartActivity(all_exam_activity)
	Activity.finish
End Sub

Private Sub btn_back_home_Click
	btn_back_Click
End Sub

Private Sub btn_back_Click
	StartActivity(Main)
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