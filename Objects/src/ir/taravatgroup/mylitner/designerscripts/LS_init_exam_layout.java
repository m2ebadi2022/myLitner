package ir.taravatgroup.mylitner.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_init_exam_layout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("rb_all").vw.setLeft((int)((65d / 100 * width)));
views.get("rb_all").vw.setWidth((int)((95d / 100 * width) - ((65d / 100 * width))));
views.get("rb_selection").vw.setLeft((int)((65d / 100 * width)));
views.get("rb_selection").vw.setWidth((int)((95d / 100 * width) - ((65d / 100 * width))));
views.get("sp_lessen").vw.setLeft((int)((5d / 100 * width)));
views.get("sp_lessen").vw.setWidth((int)((30d / 100 * width) - ((5d / 100 * width))));
views.get("sp_book").vw.setLeft((int)((35d / 100 * width)));
views.get("sp_book").vw.setWidth((int)((60d / 100 * width) - ((35d / 100 * width))));
views.get("label4").vw.setLeft((int)((50d / 100 * width) - (views.get("label4").vw.getWidth() / 2)));
views.get("sk_count_ques").vw.setLeft((int)((7d / 100 * width)));
views.get("sk_count_ques").vw.setWidth((int)((80d / 100 * width) - ((7d / 100 * width))));
views.get("lbl_count_ques").vw.setLeft((int)((81d / 100 * width)));
views.get("lbl_count_ques").vw.setWidth((int)((93d / 100 * width) - ((81d / 100 * width))));
views.get("btn_start").vw.setLeft((int)((50d / 100 * width) - (views.get("btn_start").vw.getWidth() / 2)));

}
}