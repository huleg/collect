/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR.
 */

package org.openforis.idm.metamodel {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openforis.idm.metamodel.ComparisonCheck$OPERATION")]
    public class ComparisonCheck$OPERATION extends Enum {

        public static const LT:ComparisonCheck$OPERATION = new ComparisonCheck$OPERATION("LT", _);
        public static const LTE:ComparisonCheck$OPERATION = new ComparisonCheck$OPERATION("LTE", _);
        public static const GT:ComparisonCheck$OPERATION = new ComparisonCheck$OPERATION("GT", _);
        public static const GTE:ComparisonCheck$OPERATION = new ComparisonCheck$OPERATION("GTE", _);
        public static const EQ:ComparisonCheck$OPERATION = new ComparisonCheck$OPERATION("EQ", _);

        function ComparisonCheck$OPERATION(value:String = null, restrictor:* = null) {
            super((value || LT.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [LT, LTE, GT, GTE, EQ];
        }

        public static function valueOf(name:String):ComparisonCheck$OPERATION {
            return ComparisonCheck$OPERATION(LT.constantOf(name));
        }
    }
}