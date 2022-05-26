package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_all_words_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("sp_book").vw.setLeft((int)((60d / 100 * width)));
views.get("sp_book").vw.setWidth((int)((90d / 100 * width) - ((60d / 100 * width))));
views.get("sp_lessen").vw.setLeft((int)((30d / 100 * width)));
views.get("sp_lessen").vw.setWidth((int)((60d / 100 * width) - ((30d / 100 * width))));
views.get("sp_level").vw.setLeft((int)((5d / 100 * width)));
views.get("sp_level").vw.setWidth((int)((30d / 100 * width) - ((5d / 100 * width))));

}
}