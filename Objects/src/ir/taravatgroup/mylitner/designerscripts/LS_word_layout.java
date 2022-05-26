package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_word_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("btn_ok").vw.setLeft((int)((70d / 100 * width) - (views.get("btn_ok").vw.getWidth() / 2)));
views.get("btn_nop").vw.setLeft((int)((27d / 100 * width) - (views.get("btn_nop").vw.getWidth() / 2)));
views.get("nop_count").vw.setLeft((int)((44d / 100 * width) - (views.get("nop_count").vw.getWidth() / 2)));
views.get("ok_count").vw.setLeft((int)((55d / 100 * width) - (views.get("ok_count").vw.getWidth() / 2)));
views.get("lbl_next").vw.setLeft((int)((75d / 100 * width) - (views.get("lbl_next").vw.getWidth() / 2)));
views.get("lbl_prev").vw.setLeft((int)((25d / 100 * width) - (views.get("lbl_prev").vw.getWidth() / 2)));

}
}