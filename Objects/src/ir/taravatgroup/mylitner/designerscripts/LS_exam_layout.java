package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_exam_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel2").vw.setLeft((int)((50d / 100 * width) - (views.get("panel2").vw.getWidth() / 2)));
views.get("btn_a1").vw.setLeft((int)((7d / 100 * width)));
views.get("btn_a1").vw.setWidth((int)((49d / 100 * width) - ((7d / 100 * width))));
views.get("btn_a3").vw.setLeft((int)((7d / 100 * width)));
views.get("btn_a3").vw.setWidth((int)((49d / 100 * width) - ((7d / 100 * width))));
views.get("btn_a2").vw.setLeft((int)((51d / 100 * width)));
views.get("btn_a2").vw.setWidth((int)((93d / 100 * width) - ((51d / 100 * width))));
views.get("btn_a4").vw.setLeft((int)((51d / 100 * width)));
views.get("btn_a4").vw.setWidth((int)((93d / 100 * width) - ((51d / 100 * width))));
views.get("btn_next").vw.setLeft((int)((50d / 100 * width) - (views.get("btn_next").vw.getWidth() / 2)));

}
}