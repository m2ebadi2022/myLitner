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

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim xui As XUI
	
	
	Private et_add_tozihat As EditText
	Private et_add_book As EditText
	Private pan_add As Panel
	Private pan_all As Panel
	Private btn_add As Button
	Private Panel1 As Panel
	Private Label5 As Label
	Private custom_LS As CustomListView
	Private lbl_level As Label
	Private lbl_avail_level As Label
	Private lbl_count_level As Label
	Private Label2 As Label
	Private btn_yes_add As Button
	Private btn_no_add As Button
	Private lbl_var4 As Label
	Dim hidBtn As Int=0
	Dim edit_index As Int=0
	Dim current_id As Int
	Private btn_del As Button
	Private btn_edit As Button
	Private pan_pup As Panel
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("books_layout")
	hidBtn=0
	edit_index=0
	''-------setting
	Panel1.Color=Main.app_color
	'pan_add.Color=Main.app_color
	btn_add.TextColor=Main.app_color
	''-------------
	
	
	Dim pic_book As BitmapDrawable
	pic_book.Initialize(LoadBitmap(File.DirAssets, "book1.png"))
	pic_book.Gravity = Gravity.FILL
	
	
	Dim cur1 As Cursor
	cur1 = Main.sq_db.ExecQuery("SELECT * FROM books")
	For i=0 To cur1.RowCount-1
		cur1.Position=i
		
		
		Dim p As B4XView = xui.CreatePanel(cur1.GetInt("id"))
		p.SetLayoutAnimated(0, 0, 0, 100%x, 86dip)
		p.LoadLayout("item_level")
		
		
		btn_del.Tag=cur1.GetInt("id")
		btn_edit.Tag=cur1.GetInt("id")
	
		
		lbl_level.Background=pic_book
		'lbl_level.Text=i+1
		lbl_avail_level.Text= cur1.GetString("name")
		lbl_count_level.Text=cur1.GetString("tozihat")
		custom_LS.Add(p, cur1.GetString("name"))
		
		
		
	
	Next
	
	
	
	
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
	
	
	''------------str----------
	Label5.Text=Main.loc.Localize("book-1")
	Label2.Text=Main.loc.Localize("book-2")
	et_add_book.Hint=Main.loc.Localize("book-3")
	et_add_tozihat.Hint=Main.loc.Localize("book-4")
	btn_yes_add.Text=Main.loc.Localize("book-5")
	btn_no_add.Text=Main.loc.Localize("book-6")
	
	''-------------------------
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub



Private Sub btn_add_Click
	pan_add.Visible=True
	btn_add.Visible=False
	pan_all.Visible=True
End Sub


Private Sub btn_yes_add_Click
	If(et_add_book.Text.Trim=="") Then
		ToastMessageShow(Main.loc.Localize("book-7"),False)
	Else
		If (edit_index==1)Then
			Dim book As Cursor = get_book_id(current_id)
			
			Main.sq_db.ExecNonQuery2("UPDATE books SET name = ? , tozihat= ? WHERE id=?",Array As Object(et_add_book.Text,et_add_tozihat.Text,current_id))
			Main.sq_db.ExecNonQuery2("UPDATE lessen SET book = ? WHERE book= ? ",Array As Object(et_add_book.Text,book.GetString("name")))
			Main.sq_db.ExecNonQuery2("UPDATE words SET book = ? WHERE book= ? ",Array As Object(et_add_book.Text,book.GetString("name")))
			
		
			
			Else
			Main.sq_db.ExecNonQuery("INSERT INTO books (name, tozihat) VALUES ('"&et_add_book.Text&"','"&et_add_tozihat.Text&"')")
		ToastMessageShow(Main.loc.Localize("book-8"),False)
	
		End If
		
		pan_all_Click
		custom_LS.Clear
		Activity_Create(True)
	End If
End Sub

Private Sub btn_no_add_Click
	pan_all_Click
End Sub


Private Sub pan_all_Click
	
	pan_add.Visible=False
	btn_add.Visible=True
	pan_all.Visible=False
	
End Sub


Private Sub btn_back_Click
	StartActivity(Main)
	Activity.finish
End Sub
Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		If(pan_all.Visible=True  )Then
			pan_all_Click
		Else
			If (hidBtn=1)Then
				Activity_Create(True)
				
				Else
					btn_back_Click
			End If
			
		End If
		Return True
	Else
		Return False
	End If
End Sub

Private Sub pan_add_Click
	
End Sub

Private Sub custom_LS_ItemClick (Index As Int, Value As Object)
	Main.book_name= Value
	
	StartActivity(lessen_activity)
	Activity.finish
End Sub


Private Sub custom_LS_ItemLongClick (Index As Int, Value As Object)
	Activity_Create(True)
	Dim pp As B4XView
	pp=custom_LS.GetPanel(Index)
	
	
	For Each vs As View In pp.GetAllViewsRecursive
		If vs Is Panel Then
			Dim lbl2 As Panel = vs
			
			lbl2.Visible=True
			hidBtn=1
			
		End If
	Next
	

End Sub

Private Sub pan_pup_Click
	
End Sub


Sub get_book_id(id As Int) As Cursor
	Dim cur3 As Cursor
	cur3 = Main.sq_db.ExecQuery("SELECT * FROM books WHERE id="&id)
	cur3.Position=0
	Return cur3
End Sub

Private Sub btn_edit_Click
	Dim sender_tag As Label
	sender_tag=Sender
	current_id=sender_tag.Tag
	Dim book As Cursor = get_book_id(sender_tag.Tag)
	
	pan_all.Visible=True
	et_add_book.Text=book.GetString("name")
	et_add_tozihat.Text=book.GetString("tozihat")
	edit_index=1
End Sub

Private Sub btn_del_Click
	Dim sender_tag As Label
	sender_tag=Sender
	Dim book As Cursor = get_book_id(sender_tag.Tag)
	
	
	
	Msgbox2Async(Main.loc.Localize("book-9"), Main.loc.Localize("book-10"), Main.loc.Localize("main-14"), "", "main-15", Null, False)
	Wait For Msgbox_Result (Result As Int)
	If Result = DialogResponse.POSITIVE Then
		
		
		Main.sq_db.ExecNonQuery("DELETE FROM books WHERE id='"&sender_tag.Tag&"'")
		Main.sq_db.ExecNonQuery("DELETE FROM lessen WHERE book='"&book.GetString("name")&"'")
		Main.sq_db.ExecNonQuery("DELETE FROM words WHERE book='"&book.GetString("name")&"'")
		
		ToastMessageShow(Main.loc.Localize("book-11"),False)
		custom_LS.Clear
		Activity_Create(True)
		
	End If
End Sub
