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
	Private Chart As Amir_ChartView
	Private btn_start As Button
	Private Label1 As Label
	Private Panel1 As Panel
	Private ListView1 As ListView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("all_exam_layout")
	Dim list_date As List
	Dim list_res As List
	list_date.Initialize
	list_res.Initialize
	
	ListView1.TwoLinesLayout.Label.TextColor=Colors.Black
	
	
	ListView1.TwoLinesLayout.Label.Width=90%x
	ListView1.TwoLinesLayout.SecondLabel.Width=90%x
	Dim cur1 As Cursor
	
	cur1 = Main.sq_db.ExecQuery("SELECT * FROM exam")
	For i=0 To cur1.RowCount-1
	cur1.Position=i
		ListView1.AddTwoLines("ردیف : "&(i+1)&"  مورخ : "&cur1.GetString("date"),"سوالات : "&cur1.GetInt("all_qust")&"     درست :"&cur1.GetInt("correct")&"     غلط :"&cur1.GetInt("incorrect"))
		list_date.Add(cur1.GetString("id"))
		list_res.Add((cur1.GetInt("correct")/cur1.GetInt("all_qust"))*100)
	Next
	
	chart_generator(list_date,list_res)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub chart_generator(data_x As List , data_y As List)
	'Chart Date
	For i=0 To data_y.Size-1 
		Chart.Data.AddData(data_y.Get(i))
	Next
	
	Chart.Data.setData=Chart.Data.ExportData

	'Chart Down Item Text
	Dim ListValue As List
	ListValue.Initialize
	ListValue.AddAll(data_x)
	Chart.XValues=ListValue
	Chart.XValuesEnabled=True ' Fasle : Set Number Of Item  1 , 2, 3, 4, 5 ,6 ,...
	
	
	'Chart Settings 1
	Chart.ShowTable=True
	Chart.BezierLine=True
	Chart.CubePoint=False
	Chart.RulerYSpace=10
	Chart.PointWidth=30
	Chart.StepSpace=50
	
	'Chart Settings 2
	Chart.rulerTextColor=Colors.Black
	Chart.pointTextColor=Colors.Black
	Chart.pointColor=Colors.Red
	Chart.lineColor=Colors.Blue
	Chart.lineWidthDP=2
	
	'Animation Setting
	Dim Ex As List = Chart.Data.ExportData
	Chart.AnimDuration=Ex.Size*300
	Chart.StartDelay=500
	
	'Start
	Chart.setSettingsNow
	Chart.playAnim
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

Private Sub btn_start_Click
	StartActivity(init_exam_activity)
	Activity.finish
End Sub