package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_result_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("lbl_correct_present").vw.setLeft((int)((50d / 100 * width) - (views.get("lbl_correct_present").vw.getWidth() / 2)));
views.get("panel2").vw.setLeft((int)((50d / 100 * width) - (views.get("panel2").vw.getWidth() / 2)));
views.get("btn_back_home").vw.setLeft((int)((15d / 100 * width)));
views.get("btn_back_home").vw.setWidth((int)((45d / 100 * width) - ((15d / 100 * width))));
views.get("btn_all_exam").vw.setLeft((int)((55d / 100 * width)));
views.get("btn_all_exam").vw.setWidth((int)((85d / 100 * width) - ((55d / 100 * width))));

}
}