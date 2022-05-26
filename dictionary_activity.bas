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
	Dim p As Phone
	Private WebView1 As WebView
	Private Panel1 As Panel
	Private Label5 As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("dictionary_layout")
	''-------setting
	Panel1.Color=Main.app_color
	
	''-------------
	''==============string==================
	Label5.Text=Main.loc.Localize("d-1")
	
	
	''=====================================
	'WebView1.LoadUrl("https://en.glosbe.com/en/fa/"&Main.current_word)
	If (check_internet=True) Then
		If (Main.current_word=="") Then
				WebView1.LoadUrl("	https://translate.google.com/?sl=auto&tl=fa&text=&op=translate")
		Else
				WebView1.LoadUrl("	https://translate.google.com/?sl=auto&tl=fa&text="&Main.current_word&"&op=translate")
		End If
		
	Else
		WebView1.LoadHtml("<html><body dir='rtl'><br><br><br><br><br>"&Main.loc.Localize("d-2")&" <hr>"&Main.loc.Localize("d-3")&"</body></html>")
	End If
	
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
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub btn_back_Click
	Activity.finish
	
End Sub
Sub Activity_KeyPress (KeyCode As Int) As Boolean
	
	If KeyCode = KeyCodes.KEYCODE_BACK Then                           ' Hardware-Zurück Taste gedrückt
		Activity.finish             ' Hardware-Zurück Taste deaktiviert
	Else
		Return False
	End If
End Sub

Sub check_internet As Boolean
	
	Dim connected As Boolean
	If p.GetDataState="CONNECTED" Then
		connected=True
	Else If p.GetSettings("wifi_on")=1 Then
		connected=True
	End If
	Return connected
End Sub