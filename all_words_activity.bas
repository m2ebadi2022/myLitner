B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10.7
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen:  True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim words_list As List
	Dim words_list_id As List
	Dim xui As XUI
	Private pre_custom_lv As PreoptimizedCLV
	
	Private custom_lv As CustomListView
	Private lbl_right As Label
	Private lbl_wrong As Label
	Private lbl_qustion As Label
	Private lbl_count As Label
	Private lbl_path As Label
	Private et_search As EditText
	Private btn_close_search As Button
	Private lbl_count_all As Label
	Private Panel2 As Panel
	Private Panel1 As Panel
	
	Private sp_book As Spinner
	Private sp_lessen As Spinner
	Private pan_all As Panel
	Private sp_level As Spinner
	Private lbl_title_page As Label
	Private Label1 As Label
	Private lbl_filter_not As Label
	Private lbl_filter_yes As Label
	Private btn_myliked As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	
	Activity.LoadLayout("all_words_layout")
	''-------setting
	Panel1.Color=Main.app_color
	Panel2.Color=Main.app_color
	
	''-------------
	
	pre_custom_lv.Initialize(Me,"pre_custom_lv",custom_lv)
	pre_custom_lv.B4XSeekBar1.ThumbColor=xui.Color_LightGray
	pre_custom_lv.lblHint.TextColor=xui.Color_Blue

	
		words_list_id.Initialize
		words_list.Initialize
		Dim cur2 As Cursor
		
		If(Main.end_page=="home")Then  '--از صفحه هوم آمده باشد یا لول
			Main.query="SELECT * FROM words"
		
		lbl_title_page.Text=Main.loc.Localize("main-7")
		Else If (Main.end_page=="level")Then
		Main.query="SELECT * FROM words WHERE book='"& Main.book_name &"' AND lessen='"&Main.lessen_name&"' AND level=100"
		
		lbl_title_page.Text=Main.loc.Localize("all-5")
	Else If (Main.end_page=="stared")Then
		Main.query="SELECT * FROM words WHERE state =1"
		btn_myliked.Text="همه"
		lbl_title_page.Text=Main.loc.Localize("all-6")
		End If
		
	cur2 = Main.sq_db.ExecQuery(Main.query)
		lbl_count_all.Text=cur2.RowCount
		
		For j=0 To cur2.RowCount-1
			cur2.Position=j
			
			words_list.Add(cur2.GetString("question"))
			words_list_id.Add(cur2.Getint("id"))
			
			pre_custom_lv.AddItem(130dip, xui.Color_ARGB(255,213,213,213),cur2.Getint("id"))
		
		Next
		
		
	pre_custom_lv.Commit
		
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
	lbl_title_page.TextSize=18
	''===========string======
	Label1.Text=Main.loc.Localize("all-1")
	et_search.Hint=Main.loc.Localize("all-2")
	lbl_filter_yes.Text=Main.loc.Localize("all-3")
	lbl_filter_not.Text=Main.loc.Localize("all-4")
	
	
	''===================
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



Private Sub custom_lv_ItemClick (Index As Int, Value As Object)
	
	Main.current_word_id=Value
	StartActivity(singl_word_activity)
	'Activity.finish
End Sub

Private Sub btn_search
	

	custom_lv.Clear
	
	btn_close_search.Visible=True
	Dim cur1 As Cursor
	
	For i=0 To words_list_id.Size-1 
		Dim tt As String=words_list.Get(i)
		tt=tt.ToLowerCase
		
		If ( tt.Contains(et_search.Text.Trim) ) Then
			cur1 = Main.sq_db.ExecQuery("SELECT * FROM words WHERE id="&words_list_id.Get(i))
			cur1.Position=0
			pre_custom_lv.AddItem(140dip, xui.Color_ARGB(255,213,213,213),words_list_id.Get(i))
			

		End If
		
		
	Next
	pre_custom_lv.Commit
	custom_lv.Refresh
End Sub

Private Sub et_search_TextChanged (Old As String, New As String)
	btn_search
	
End Sub

Private Sub btn_close_search_Click
	et_search.Text=""
	btn_close_search.Visible=False
End Sub

Private Sub custom_lv_VisibleRangeChanged (FirstIndex As Int, LastIndex As Int)
	
	
	For Each i As Int In pre_custom_lv.VisibleRangeChanged(FirstIndex,LastIndex)
		Dim item As CLVItem=custom_lv.GetRawListItem(i)
		Dim p As B4XView=xui.CreatePanel("")
		item.Panel.AddView(p,0,0,item.Panel.Width,item.Panel.Height)
		p.LoadLayout("item_words")
		
		Dim id As Int =item.Value
		Dim cur3 As Cursor
		cur3 = Main.sq_db.ExecQuery("SELECT * FROM words WHERE id="&id)
		cur3.Position=0
		
		lbl_right.Text=cur3.GetInt("count_right")
		lbl_wrong.Text=cur3.GetInt("count_wrong")
			
		lbl_qustion.Text=cur3.GetString("question")
		lbl_path.Text=cur3.GetString("id")
		lbl_count.Text=i+1
		lbl_path.Text=cur3.GetString("book")&" / "&cur3.GetString("lessen")&" / "&"level "&cur3.Getint("level")
		
	Next 
End Sub
Sub pre_custom_lv_HintRequested (Index As Int) As Object
	
	Return words_list.Get(Index)
End Sub

Private Sub btn_filter_Click
	sp_book.Clear
	
	Dim cur3 As Cursor
	cur3 = Main.sq_db.ExecQuery("SELECT * FROM books")
	For i=0 To cur3.RowCount-1
	cur3.Position=i
	sp_book.Add(cur3.GetString("name"))
	Next
	
	sp_book_ItemClick(0,sp_book.GetItem(0))
	sp_lessen_ItemClick(0,sp_book.GetItem(0))
	
	
	pan_all.Visible=True
End Sub



Private Sub pan_all_Click
	pan_all.Visible=False
End Sub

Private Sub lbl_filter_yes_Click
	Main.end_page="filter"
	Main.query="SELECT * FROM words WHERE book='"& sp_book.SelectedItem &"' AND lessen='"&sp_lessen.SelectedItem&"' AND level='"&sp_level.SelectedItem&"'"
	
	pan_all.Visible=False
	custom_lv.Clear
	Activity_Create(True)
End Sub

Private Sub sp_book_ItemClick (Position As Int, Value As Object)
	
	sp_lessen.Clear
	Dim cur4 As Cursor
	cur4 = Main.sq_db.ExecQuery("SELECT * FROM lessen WHERE book='"& Value &"'")
	For i=0 To cur4.RowCount-1
		cur4.Position=i
		sp_lessen.Add(cur4.GetString("name"))
	Next
	
End Sub

Private Sub sp_lessen_ItemClick (Position As Int, Value As Object)
	
	sp_level.Clear
	Dim cur5 As Cursor
	cur5 = Main.sq_db.ExecQuery("SELECT * FROM level")
	For i=0 To cur5.RowCount-1
		cur5.Position=i
		sp_level.Add(cur5.GetString("name"))
	Next
	
End Sub

Private Sub lbl_filter_not_Click
	Main.end_page="home"
	pan_all.Visible=False
	custom_lv.Clear
	Activity_Create(True)
End Sub

Private Sub btn_myliked_Click
	If(btn_myliked.Text="همه")Then
		Main.end_page="home"
		btn_myliked.Text="برگزیده"
		
		Else
		Main.end_page="stared"
		
	End If
	
	custom_lv.Clear
	Activity_Create(True)
	
End Sub