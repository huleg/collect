/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (ModelVersion.as).
 */

package org.openforis.idm.metamodel {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import mx.collections.ListCollectionView;

    [Bindable]
    public class ModelVersionBase implements IExternalizable {

        private var _date:String;
        private var _descriptions:ListCollectionView;
        private var _labels:ListCollectionView;
        private var _name:String;

        [Bindable(event="unused")]
        public function get date():String {
            return _date;
        }

        [Bindable(event="unused")]
        public function get descriptions():ListCollectionView {
            return _descriptions;
        }

        [Bindable(event="unused")]
        public function get labels():ListCollectionView {
            return _labels;
        }

        [Bindable(event="unused")]
        public function get name():String {
            return _name;
        }

        public function readExternal(input:IDataInput):void {
            _date = input.readObject() as String;
            _descriptions = input.readObject() as ListCollectionView;
            _labels = input.readObject() as ListCollectionView;
            _name = input.readObject() as String;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_date);
            output.writeObject(_descriptions);
            output.writeObject(_labels);
            output.writeObject(_name);
        }
    }
}