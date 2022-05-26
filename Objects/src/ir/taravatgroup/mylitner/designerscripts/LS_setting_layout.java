package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_setting_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pan_colorpicker").vw.setLeft((int)((50d / 100 * width) - (views.get("pan_colorpicker").vw.getWidth() / 2)));
views.get("pan_colorpicker").vw.setTop((int)((50d / 100 * height) - (views.get("pan_colorpicker").vw.getHeight() / 2)));

}
}