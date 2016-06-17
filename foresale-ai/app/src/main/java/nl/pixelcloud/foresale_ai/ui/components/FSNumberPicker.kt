package nl.pixelcloud.foresale_ai.ui.components

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
/**
 * Created by Rob Peek on 17/06/16.
 */
class FSNumberPicker : NumberPicker {

    constructor(ctx:Context) : super(ctx)
    constructor(ctx:Context, attrs:AttributeSet) : super(ctx, attrs) {
        processAttributeSet(attrs)
    }
    constructor(ctx:Context, attrs:AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle) {
        processAttributeSet(attrs)
    }

    fun processAttributeSet(attrs:AttributeSet) {
        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 0));
    }
}