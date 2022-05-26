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
	Dim list_exam_ques As List
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim list_exam_ques_all As List
	Private btn_back As Button
	Private Label1 As Label
	Private rb_all As RadioButton
	Private rb_selection As RadioButton
	Private sp_book As Spinner
	Private sp_lessen As Spinner
	Private Label4 As Label
	Private lbl_count_ques As Label
	Private sk_count_ques As SeekBar
	Private btn_start As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("init_exam_layout")
	sp_book.Enabled=False
	sp_lessen.Enabled=False
	list_exam_ques_all.Initialize
	list_exam_ques.Initialize


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
	btn_start.Typeface = Main.app_font
	rb_all.Typeface = Main.app_font
	rb_selection.Typeface = Main.app_font
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub btn_start_Click
'	Log(rb_all.Checked)
'	Log(rb_selection.Checked)
'	Log(sp_book.SelectedItem)
'	Log(sp_lessen.SelectedItem)
'	Log(sk_count_ques.Value)
'	
	list_exam_ques_all.Clear
	list_exam_ques.Clear
	
	If(rb_all.Checked)Then
		Main.query="SELECT * FROM words"
	Else If (rb_selection.Checked)Then
		If(sp_lessen.SelectedItem=="همه درسها")Then
			Main.query="SELECT * FROM words WHERE book='"& sp_book.SelectedItem &"'" 
		Else
			Main.query="SELECT * FROM words WHERE book='"& sp_book.SelectedItem &"' AND lessen='"&sp_lessen.SelectedItem&"'"	
		End If
		
	End If
	Dim cur2 As Cursor
	
	cur2 = Main.sq_db.ExecQuery(Main.query)
	For i=0 To cur2.RowCount-1
		cur2.Position=i
		list_exam_ques_all.Add(cur2.GetInt("id"))
	Next
	
	list_exam_ques_all=ShuffleList(list_exam_ques_all)
	For j=0 To list_exam_ques_all.Size-1
		
	
		If (j==sk_count_ques.Value)Then
			Exit
		End If
		list_exam_ques.Add(list_exam_ques_all.Get(j))
		
	Next
	StartActivity(exam_activity)

End Sub

Sub ShuffleList(pl As List) As List
	For i = pl.Size - 1 To 0 Step -1
		Dim j As Int
		Dim k As Object
		j = Rnd(0, i + 1)
		k = pl.Get(j)
		pl.Set(j,pl.Get(i))
		pl.Set(i,k)
	Next
	Return pl
End Sub

Private Sub sk_count_ques_ValueChanged (Value As Int, UserChanged As Boolean)
	lbl_count_ques.Text=Value
End Sub

Private Sub sp_lessen_ItemClick (Position As Int, Value As Object)
	
End Sub

Private Sub sp_book_ItemClick (Position As Int, Value As Object)
	sp_lessen.Clear
	Dim cur4 As Cursor
	cur4 = Main.sq_db.ExecQuery("SELECT * FROM lessen WHERE book='"& Value &"'")
	sp_lessen.Add("همه درسها")
	For i=0 To cur4.RowCount-1
		cur4.Position=i
		sp_lessen.Add(cur4.GetString("name"))
	Next
	
End Sub

Private Sub rb_selection_CheckedChange(Checked As Boolean)
	sp_book.Enabled=True
	sp_lessen.Enabled=True
	sp_book.Clear
	
	Dim cur3 As Cursor
	cur3 = Main.sq_db.ExecQuery("SELECT * FROM books")
	For i=0 To cur3.RowCount-1
		cur3.Position=i
		sp_book.Add(cur3.GetString("name"))
	Next
	
	sp_book_ItemClick(0,sp_book.GetItem(0))
	sp_lessen_ItemClick(0,sp_book.GetItem(0))
	
End Sub

Private Sub rb_all_CheckedChange(Checked As Boolean)
	sp_book.Enabled=False
	sp_lessen.Enabled=False
	
	sp_book.Clear
	sp_lessen.Clear
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