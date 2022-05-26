package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_add_voice_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 5;BA.debugLine="sp_lang_ques.Right=50%x"[add_voice_layout/General script]
views.get("sp_lang_ques").vw.setLeft((int)((50d / 100 * width) - (views.get("sp_lang_ques").vw.getWidth())));
//BA.debugLineNum = 6;BA.debugLine="Label2.Left=sp_lang_ques.Right"[add_voice_layout/General script]
views.get("label2").vw.setLeft((int)((views.get("sp_lang_ques").vw.getLeft() + views.get("sp_lang_ques").vw.getWidth())));
//BA.debugLineNum = 8;BA.debugLine="sp_lang_answ.Right=50%x"[add_voice_layout/General script]
views.get("sp_lang_answ").vw.setLeft((int)((50d / 100 * width) - (views.get("sp_lang_answ").vw.getWidth())));
//BA.debugLineNum = 9;BA.debugLine="Label3.Left=sp_lang_answ.Right"[add_voice_layout/General script]
views.get("label3").vw.setLeft((int)((views.get("sp_lang_answ").vw.getLeft() + views.get("sp_lang_answ").vw.getWidth())));
//BA.debugLineNum = 11;BA.debugLine="sp_lang_syno.Right=50%x"[add_voice_layout/General script]
views.get("sp_lang_syno").vw.setLeft((int)((50d / 100 * width) - (views.get("sp_lang_syno").vw.getWidth())));
//BA.debugLineNum = 12;BA.debugLine="Label4.Left=sp_lang_syno.Right"[add_voice_layout/General script]
views.get("label4").vw.setLeft((int)((views.get("sp_lang_syno").vw.getLeft() + views.get("sp_lang_syno").vw.getWidth())));
//BA.debugLineNum = 14;BA.debugLine="sp_lang_codeing.Right=50%x"[add_voice_layout/General script]
views.get("sp_lang_codeing").vw.setLeft((int)((50d / 100 * width) - (views.get("sp_lang_codeing").vw.getWidth())));
//BA.debugLineNum = 15;BA.debugLine="Label5.Left=sp_lang_codeing.Right"[add_voice_layout/General script]
views.get("label5").vw.setLeft((int)((views.get("sp_lang_codeing").vw.getLeft() + views.get("sp_lang_codeing").vw.getWidth())));
//BA.debugLineNum = 17;BA.debugLine="lbl_edit_pan.SetLeftAndRight(40%x,60%x)"[add_voice_layout/General script]
views.get("lbl_edit_pan").vw.setLeft((int)((40d / 100 * width)));
views.get("lbl_edit_pan").vw.setWidth((int)((60d / 100 * width) - ((40d / 100 * width))));
//BA.debugLineNum = 20;BA.debugLine="Panel7.SetLeftAndRight(15%x,85%x)"[add_voice_layout/General script]
views.get("panel7").vw.setLeft((int)((15d / 100 * width)));
views.get("panel7").vw.setWidth((int)((85d / 100 * width) - ((15d / 100 * width))));
//BA.debugLineNum = 22;BA.debugLine="lbl_count_added.Right=btn_save_next.Right-20"[add_voice_layout/General script]
views.get("lbl_count_added").vw.setLeft((int)((views.get("btn_save_next").vw.getLeft() + views.get("btn_save_next").vw.getWidth())-20d - (views.get("lbl_count_added").vw.getWidth())));
//BA.debugLineNum = 26;BA.debugLine="Panel2.HorizontalCenter=50%x"[add_voice_layout/General script]
views.get("panel2").vw.setLeft((int)((50d / 100 * width) - (views.get("panel2").vw.getWidth() / 2)));
//BA.debugLineNum = 27;BA.debugLine="Panel2.VerticalCenter=30%y"[add_voice_layout/General script]
views.get("panel2").vw.setTop((int)((30d / 100 * height) - (views.get("panel2").vw.getHeight() / 2)));

}
}