/**
 * Generated by Gas3 v2.3.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR.
 */

package org.openforis.collect.metamodel.ui {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openforis.collect.metamodel.ui.UIOptions$CoordinateAttributeFieldsOrder")]
    public class UIOptions$CoordinateAttributeFieldsOrder extends Enum {

        public static const SRS_X_Y:UIOptions$CoordinateAttributeFieldsOrder = new UIOptions$CoordinateAttributeFieldsOrder("SRS_X_Y", _);
        public static const SRS_Y_X:UIOptions$CoordinateAttributeFieldsOrder = new UIOptions$CoordinateAttributeFieldsOrder("SRS_Y_X", _);

        function UIOptions$CoordinateAttributeFieldsOrder(value:String = null, restrictor:* = null) {
            super((value || SRS_X_Y.name), restrictor);
        }

        protected override function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [SRS_X_Y, SRS_Y_X];
        }

        public static function valueOf(name:String):UIOptions$CoordinateAttributeFieldsOrder {
            return UIOptions$CoordinateAttributeFieldsOrder(SRS_X_Y.constantOf(name));
        }
    }
}