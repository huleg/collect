/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (UniquenessCheck.as).
 */

package org.openforis.idm.metamodel {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;

    [Bindable]
    public class UniquenessCheckBase extends Check {

        private var _expression:String;

        [Bindable(event="unused")]
        public function get expression():String {
            return _expression;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _expression = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_expression);
        }
    }
}