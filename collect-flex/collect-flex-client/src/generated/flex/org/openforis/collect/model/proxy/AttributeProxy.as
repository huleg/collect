/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package org.openforis.collect.model.proxy {
	import org.openforis.collect.util.StringUtil;

    [Bindable]
    [RemoteClass(alias="org.openforis.collect.model.proxy.AttributeProxy")]
    public class AttributeProxy extends AttributeProxyBase {
		
		public function get empty():Boolean {
			var result:Boolean = (value == null || StringUtil.isBlank(value.toString())) && symbol == null;
			return result;
		}
		
    }
}