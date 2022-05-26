package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_home_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel3").vw.setLeft((int)((34d / 100 * width)));
views.get("panel3").vw.setWidth((int)((96d / 100 * width) - ((34d / 100 * width))));
views.get("label6").vw.setLeft((int)(0d));
views.get("label6").vw.setWidth((int)((20d / 100 * width) - (0d)));
views.get("label4").vw.setLeft((int)((21d / 100 * width)));
views.get("label4").vw.setWidth((int)((41d / 100 * width) - ((21d / 100 * width))));
views.get("label2").vw.setLeft((int)((42d / 100 * width)));
views.get("label2").vw.setWidth((int)((62d / 100 * width) - ((42d / 100 * width))));
views.get("lbl_words").vw.setLeft((int)(0d));
views.get("lbl_words").vw.setWidth((int)((20d / 100 * width) - (0d)));
views.get("lbl_lessens").vw.setLeft((int)((21d / 100 * width)));
views.get("lbl_lessens").vw.setWidth((int)((41d / 100 * width) - ((21d / 100 * width))));
views.get("lbl_books").vw.setLeft((int)((42d / 100 * width)));
views.get("lbl_books").vw.setWidth((int)((62d / 100 * width) - ((42d / 100 * width))));

}
}