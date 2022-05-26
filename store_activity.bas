B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11.2
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
	Dim xui As XUI
	Private scw_horiz As HorizontalScrollView
	Private custom_LS As CustomListView
	Private img_book_s As ImageView
	Private lbl_name_s As Label
	Private lbl_count_s As Label
	Private lbl_price_s As Label
	Private btn_download_s As Button
	
	Private pan_all As Panel
	Dim list1 As List
	Dim strfunc As StringFunctions
	

	Private lsv_view_s As ListView
	Private pan_mota_cat As Panel
	Private pan_ebte_cat As Panel
	Private pan_fave_cat As Panel
	Private pan_poli_cat As Panel
	Private pan_free_cat As Panel
	
	
	Private Workbook1 As ReadableWorkbook
	Private Sheet1 As ReadableSheet
	
	Private btn_view_s As Button
	
	
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("store_layout")
	
	
	
	scw_horiz.Panel.LoadLayout("scw_h_items")
list1.Initialize

strfunc.Initialize

If (File.Exists(File.DirAssets,"store_list.xls"))Then
	
		Try
		
			Workbook1.Initialize(File.DirAssets,"store_list.xls")
			Sheet1 = Workbook1.GetSheet(0)
			
			For row = 1 To Sheet1.RowsCount - 1
				Dim s As String=Sheet1.GetCellValue(0, row)&"%"&Sheet1.GetCellValue(1, row)&"%"&Sheet1.GetCellValue(2, row)&"%"&Sheet1.GetCellValue(3, row)&"%"&Sheet1.GetCellValue(4, row)&"%"&Sheet1.GetCellValue(5, row)&"%"&Sheet1.GetCellValue(6, row)&"%"&Sheet1.GetCellValue(7, row)
				list1.Add(s)
			
			Next	
		
	Catch
		Log(LastException)
	End Try
	
	
	
		'list1=File.ReadList(File.DirAssets,"store_list.txt")
End If
	
	pan_free_cat_Click
	
	For i=0 To list1.Size-1
		Log (list1.Get(i))
	Next
End Sub


Sub fill_list_view(type1 As Int)
	
	custom_LS.Clear
	
	If(list1.Size>0)Then
		

	For i=0 To list1.Size-1
		
		
		Dim lin As String=list1.Get(i)
		Dim item As List
		item.Initialize
		item=strfunc.Split(lin,"%")
		
			
		
			Dim aa As Int=item.Get(0)
			If(aa=type1)Then
				
			
		
		
			Dim p As B4XView = xui.CreatePanel(item.Get(1))
		p.SetLayoutAnimated(0, 0, 0, 100%x, 190dip)
		p.LoadLayout("item_store")
		
		
			lbl_name_s.Text=item.Get(2)
				lbl_count_s.Text=item.Get(5)
			lbl_price_s.Text= item.Get(3)
				btn_view_s.Tag=i
				btn_download_s.Tag=i
				
			If(type1=1)Then
				btn_download_s.Text="افزودن"
			Else
				btn_download_s.Text="خرید"
			End If		
			
				If(File.Exists(File.DirInternal,get_item_in_listBook(i).Get(7)))Then
					btn_download_s.Text="دانلود شده"
					btn_download_s.TextColor=Colors.Green
				End If
		
			img_book_s.Bitmap=LoadBitmap(File.DirAssets,item.Get(4))
			custom_LS.Add(p, item.Get(1))
			
			End If
			
			
	Next
	
	End If
		
	
	lsv_view_s.TwoLinesLayout.Label.TextColor=Colors.Black
	
	lsv_view_s.TwoLinesLayout.Label.Width=lsv_view_s.Width-20
	lsv_view_s.TwoLinesLayout.SecondLabel.Width=lsv_view_s.Width-20
	
	lsv_view_s.TwoLinesLayout.SecondLabel.Gravity=Gravity.RIGHT
	lsv_view_s.TwoLinesLayout.Label.Gravity=Gravity.RIGHT
	
	
End Sub
Sub get_item_in_listBook (id As Int)As List
	
	Dim lin As String=list1.Get(id)
	Dim item As List
	item.Initialize
	item=strfunc.Split(lin,"%")
	
	Return item
End Sub


Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub custom_LS_ItemClick (Index As Int, Value As Object)
	
End Sub



Private Sub btn_download_s_Click
	Dim sender_tag As Button
	sender_tag=Sender
	
	
	
	
	If(btn_download_s.Text=="افزودن") Then
		If(File.Exists(File.DirInternal,get_item_in_listBook(sender_tag.Tag).Get(7))==False)Then
		
				func_file_rw(sender_tag.Tag,1)
		End If
		
		
		
		Else
			
	End If
	
	
End Sub

Private Sub btn_view_s_Click
	Dim sender_tag As Button
	sender_tag=Sender
	
	pan_all.Visible=True
	lsv_view_s.Clear
	
	func_file_rw(sender_tag.Tag,0)
	
End Sub
Sub func_file_rw(id As Int , act As Int)
	
	
	
	If(act=0)Then
		' preview
		
		
		Try
			Workbook1.Initialize(File.DirAssets, get_item_in_listBook(id).Get(7)&".xls")
			Sheet1 = Workbook1.GetSheet(0)
			
			For row = 1 To 20  'Sheet1.RowsCount - 1
				If (Sheet1.GetCellValue(0, row).Trim=="") Then
					Continue
				End If
				
				
				
				lsv_view_s.AddTwoLines(row&"-"&Sheet1.GetCellValue(0, row)&" : "&Sheet1.GetCellValue(1, row),Sheet1.GetCellValue(2, row)&" -- "&Sheet1.GetCellValue(3, row))
				
				'Main.sq_db.ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)", Array As Object(Sheet1.GetCellValue(0, row), Sheet1.GetCellValue(1, row), Main.book_name, Main.lessen_name,Sheet1.GetCellValue(2, row),Sheet1.GetCellValue(3, row)))
			Next
		Catch
			ToastMessageShow(Main.loc.Localize("level-11")&LastException,True)
			
		End Try
	
	
		
		Else If (act=1)Then
		' افزودن
			
		Try
			
			Workbook1.Initialize(File.DirAssets, get_item_in_listBook(id).Get(7)&".xls")
			Sheet1 = Workbook1.GetSheet(0)
			
			For row = 1 To Sheet1.RowsCount - 1
				If (Sheet1.GetCellValue(0, row).Trim=="") Then
					Continue
				End If
				
				
				
				lsv_view_s.AddTwoLines("1-"&Sheet1.GetCellValue(0, row)&" : "&Sheet1.GetCellValue(1, row),Sheet1.GetCellValue(2, row)&" -- "&Sheet1.GetCellValue(3, row))
				
				Main.sq_db.ExecNonQuery2("INSERT INTO words (question, answer, book, lessen, level, time_enter, synonym, codeing, state) VALUES (?, ?, ?, ?,1,0,?,?,0)", Array As Object(Sheet1.GetCellValue(0, row), Sheet1.GetCellValue(1, row), get_item_in_listBook(id).Get(2), "درس1",Sheet1.GetCellValue(2, row),Sheet1.GetCellValue(3, row)))
			Next
			
			Main.sq_db.ExecNonQuery("INSERT INTO books (name, tozihat) VALUES ('"& get_item_in_listBook(id).Get(2)&"','"& get_item_in_listBook(id).Get(5)&" لغات ')")
			
			
			Main.sq_db.ExecNonQuery("INSERT INTO lessen (name, book) VALUES ('"&"درس1"&"','"&get_item_in_listBook(id).Get(2)&"')")
			
			File.WriteString(File.DirInternal,get_item_in_listBook(id).Get(7),"")
			
			
			ToastMessageShow(Main.loc.Localize("book-8"),False)
		Catch
			ToastMessageShow(Main.loc.Localize("level-11")&LastException,True)
			
		End Try
			
	End If
	
	
	
	
	
	
	
	
	
	
	
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
			
				btn_back_Click
			
			
		End If
		Return True
	Else
		Return False
	End If
End Sub

Private Sub pan_all_Click
	
	pan_all.Visible=False
End Sub


Private Sub btn_close_Click
	pan_all_Click
End Sub

Private Sub pan_free_cat_Click
	pan_color_rest
	fill_list_view(1)
	pan_free_cat.Color=0xFF5DB0FF
End Sub

Private Sub pan_poli_cat_Click
	pan_color_rest
	fill_list_view(2)
	pan_poli_cat.Color=0xFF5DB0FF
End Sub

Private Sub pan_fave_cat_Click
	pan_color_rest
	fill_list_view(3)
	pan_fave_cat.Color=0xFF5DB0FF
End Sub

Private Sub pan_ebte_cat_Click
	pan_color_rest
	fill_list_view(4)
	pan_ebte_cat.Color=0xFF5DB0FF
End Sub

Private Sub pan_mota_cat_Click
	pan_color_rest
	fill_list_view(5)
	pan_mota_cat.Color=0xFF5DB0FF
End Sub

Sub pan_color_rest
	pan_free_cat.Color=0xFFFFFFFF
	pan_poli_cat.Color=0xFFFFFFFF
	pan_fave_cat.Color=0xFFFFFFFF
	pan_ebte_cat.Color=0xFFFFFFFF
	pan_mota_cat.Color=0xFFFFFFFF
End Sub