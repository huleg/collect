/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (SpatialReferenceSystem.as).
 */

package org.openforis.idm.metamodel {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import mx.collections.ListCollectionView;

    [Bindable]
    public class SpatialReferenceSystemBase implements IExternalizable {

        private var _descriptions:ListCollectionView;
        private var _id:String;
        private var _labels:ListCollectionView;
        private var _wellKnownText:String;

        [Bindable(event="unused")]
        public function get descriptions():ListCollectionView {
            return _descriptions;
        }

        [Bindable(event="unused")]
        public function get id():String {
            return _id;
        }

        [Bindable(event="unused")]
        public function get labels():ListCollectionView {
            return _labels;
        }

        [Bindable(event="unused")]
        public function get wellKnownText():String {
            return _wellKnownText;
        }

        public function readExternal(input:IDataInput):void {
            _descriptions = input.readObject() as ListCollectionView;
            _id = input.readObject() as String;
            _labels = input.readObject() as ListCollectionView;
            _wellKnownText = input.readObject() as String;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_descriptions);
            output.writeObject(_id);
            output.writeObject(_labels);
            output.writeObject(_wellKnownText);
        }
    }
}