import { BaseEntity } from './../../shared';

export class WaterTest implements BaseEntity {
    constructor(
        public id?: number,
        public ph?: number,
        public chlorine?: number,
        public totalAlkalinity?: number,
        public calciumHardness?: number,
        public cyanuricAcid?: number,
        public totalDissolvedSolids?: number,
        public dateTime?: any,
        public note?: string,
        public appointment?: BaseEntity,
    ) {
    }
}
