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
	
	Private Panel1 As Panel
	Private Label3 As Label
	
	Dim rp As RuntimePermissions
	Private Label1 As Label
	Private btn_save_file As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("help_layout")
	''-------setting
	Panel1.Color=Main.app_color
	
	
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
	Label3.TextSize=18
	''=============string=================
	Label3.Text=Main.loc.Localize("main-11")
	Label1.Text=Main.loc.Localize("h-1")
	btn_save_file.Text=Main.loc.Localize("h-2")
	
	''==================================
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
	If KeyCode = KeyCodes.KEYCODE_BACK Then                           ' Hardware-Zurück Taste gedrückt
		btn_back_Click                                       ' Hardware-Zurück Taste deaktiviert
	Else
		Return False
	End If
End Sub

Private Sub btn_save_file_Click
'	
'	Dim apath As String= File.Combine(File.DirRootExternal,"Download/")
'	rp.CheckAndRequest(rp.PERMISSION_WRITE_EXTERNAL_STORAGE)
'	
'	If (rp.Check(rp.PERMISSION_WRITE_EXTERNAL_STORAGE)) Then
'		
'		File.Copy(File.DirAssets,"test.xls",apath,"test.xls")
'	ToastMessageShow("فایل نمونه در پوشه دانلود ها ذخیره شد",True)
'	End If
	
	
End Sub