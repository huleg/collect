/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package org.openforis.collect.model.proxy {
	import org.openforis.collect.model.FieldSymbol;
	import org.openforis.collect.util.ArrayUtil;
	import org.openforis.collect.util.StringUtil;

	
	/**
	 * @author S. Ricci
	 */
    [Bindable]
    [RemoteClass(alias="org.openforis.collect.model.proxy.FieldProxy")]
    public class FieldProxy extends FieldProxyBase {
		
		public static const REASON_BLANK_SYMBOLS:Array = [
			FieldSymbol.BLANK_ON_FORM, 
			FieldSymbol.DASH_ON_FORM, 
			FieldSymbol.ILLEGIBLE
		];
		public static const REASON_BLANK_SHORTCUTS:Array = [
			SHORTCUT_BLANK_ON_FORM, 
			SHORTCUT_DASH_ON_FORM, 
			SHORTCUT_ILLEGIBLE
		];
		public static const SHORTCUT_BLANK_ON_FORM:String = "*";
		public static const SHORTCUT_DASH_ON_FORM:String = "-";
		public static const SHORTCUT_ILLEGIBLE:String = "?";
		
		private var _parent:AttributeProxy;
		
		public static function getShortCutForReasonBlank(symbol:FieldSymbol):String {
			switch(symbol) {
				case FieldSymbol.BLANK_ON_FORM:
					return SHORTCUT_BLANK_ON_FORM;
				case FieldSymbol.DASH_ON_FORM:
					return SHORTCUT_DASH_ON_FORM;
				case FieldSymbol.ILLEGIBLE:
					return SHORTCUT_ILLEGIBLE;
				default:
					return null;
			}
		}
		
		public static function parseShortCutForReasonBlank(text:String):FieldSymbol {
			switch(text) {
				case SHORTCUT_BLANK_ON_FORM:
					return FieldSymbol.BLANK_ON_FORM;
				case SHORTCUT_DASH_ON_FORM:
					return FieldSymbol.DASH_ON_FORM;
				case SHORTCUT_ILLEGIBLE:
					return FieldSymbol.ILLEGIBLE;
				default:
					return null;
			}
		}
		
		public static function isShortCutForReasonBlank(text:String):Boolean {
			return ArrayUtil.isIn(REASON_BLANK_SHORTCUTS, text);
		}
		
		public static function isReasonBlankSymbol(symbol:FieldSymbol):Boolean {
			return ArrayUtil.isIn(REASON_BLANK_SYMBOLS, symbol);
		}
		
		public function hasReasonBlankSpecified():Boolean {
			var result:Boolean = ArrayUtil.isIn(REASON_BLANK_SYMBOLS, symbol);
			return result;
		}
		
		public function hasValue():Boolean {
			return value != null && StringUtil.isNotEmpty(value.toString());
		}
		
		public function getValueAsText():String {
			if(symbol != null) {
				var shortKey:String = getShortCutForReasonBlank(symbol);
				if(shortKey != null) {
					return shortKey;
				}
			}
			if(value != null && StringUtil.isNotBlank(value.toString())) {
				return value.toString();
			}
			return "";
		}

		public function get parent():AttributeProxy {
			return _parent;
		}

		public function set parent(value:AttributeProxy):void {
			_parent = value;
		}
		
		public function get index():int {
			if ( _parent != null ) {
				return _parent.fields.getItemIndex(this);
			} else {
				return -1;
			}
		}
		
    }
}